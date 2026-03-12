package com.example.app.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.model.GameRecord
import com.example.app.storage.LocalStorageManager
import kotlin.time.Clock
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.random.Random

private const val RECORDS_KEY = "game_records"

class GameViewModel() : ViewModel() {

    private val localStorageManager = LocalStorageManager()

    enum class GameState { PLAYING, PAUSED, FINISHED }


    private val wheelSectors = listOf(
        WheelSector(5, "+5"),
        WheelSector(-5, "-5"),
        WheelSector(10, "+10"),
        WheelSector(-10, "-10"),
        WheelSector(15, "+15"),
        WheelSector(-15, "-15"),
        WheelSector(-20, "-20"),
        WheelSector(20, "+20"),
        WheelSector(25, "+25"),
        WheelSector(-25, "-25")
    )

    data class WheelSector(val value: Int, val label: String)

    // Состояния
    private val _gameState = MutableStateFlow(GameState.PLAYING)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val _balance = MutableStateFlow(100)
    val balance: StateFlow<Int> = _balance.asStateFlow()

    private val _spinsLeft = MutableStateFlow(10)
    val spinsLeft: StateFlow<Int> = _spinsLeft.asStateFlow()

    private val _isSpinning = MutableStateFlow(false)
    val isSpinning: StateFlow<Boolean> = _isSpinning.asStateFlow()

    private val _currentSector = MutableStateFlow<WheelSector?>(null)


    private val _wheelRotation = MutableStateFlow(0f)
    val wheelRotation: StateFlow<Float> = _wheelRotation.asStateFlow()


    private var savedTotalRotation: Float = 0f
    private var savedCurrentRotation: Float = 0f
    private var savedElapsedTime: Long = 0L
    private var savedTargetSector: WheelSector? = null
    private var isAnimationPaused: Boolean = false
    private var animationStartTime: Long = 0L

    private var spinJob: Job? = null
    private var shouldSaveResult = true

    fun handleBackPressedWithExit() {
        when (_gameState.value) {
            GameState.PLAYING -> togglePause()
            GameState.PAUSED -> togglePause()
            else -> {}
        }
    }

    fun startSpin() {
        if (_isSpinning.value || _spinsLeft.value <= 0) return

        _isSpinning.value = true
        _currentSector.value = null
        isAnimationPaused = false


        val selectedSector = wheelSectors.random()
        val targetIndex = wheelSectors.indexOf(selectedSector)
        val targetAngle = targetIndex * 36f

        savedTargetSector = selectedSector
        animationStartTime = Clock.System.now().toEpochMilliseconds()

        spinJob = viewModelScope.launch {
            try {
                _wheelRotation.value = 0f

                val fullSpins = Random.nextInt(3, 6)
                savedTotalRotation = (fullSpins * 360) + targetAngle
                savedCurrentRotation = 0f
                savedElapsedTime = 0L

                val startTime = Clock.System.now().toEpochMilliseconds()
                val duration = 3000L // 3 секунды

                // Анимация пока не прошло 3 секунды
                while (Clock.System.now().toEpochMilliseconds() - startTime < duration) {
                    // Проверка паузы
                    if (_gameState.value == GameState.PAUSED) {
                        savedCurrentRotation = _wheelRotation.value
                        savedElapsedTime = Clock.System.now().toEpochMilliseconds() - startTime
                        isAnimationPaused = true
                        return@launch
                    }

                    // Прогресс от 0 до 1
                    val elapsed = Clock.System.now().toEpochMilliseconds() - startTime
                    val progress = elapsed.toFloat() / duration

                    // Ease-out эффект (замедление в конце)
                    val easedProgress = 1 - (1 - progress) * (1 - progress)

                    // Текущий угол поворота
                    val currentRotation = savedTotalRotation * easedProgress
                    _wheelRotation.value = currentRotation
                    savedCurrentRotation = currentRotation

                    // Ждем 16мс (60fps)
                    delay(16)
                }

                // Финальная позиція — БЕЗ ЗАЙВИХ ЗАТРИМОК
                if (_gameState.value == GameState.PLAYING) {
                    _wheelRotation.value = targetAngle % 360

                    // Видалено delay(500) та delay(1000)
                    _currentSector.value = selectedSector
                    applySectorResult(selectedSector) // результат застосовується одразу
                }

            } catch (e: Exception) {
                _isSpinning.value = false
                _currentSector.value = null
            }
        }
    }

    private fun applySectorResult(sector: WheelSector) {
        _balance.value += sector.value
        _spinsLeft.value -= 1
        _isSpinning.value = false

        if (_spinsLeft.value <= 0) {
            finishGame()
        }
    }

    fun togglePause() {
        when (_gameState.value) {
            GameState.PLAYING -> {

                if (_isSpinning.value) {
                    isAnimationPaused = true
                }
                _gameState.value = GameState.PAUSED
            }
            GameState.PAUSED -> {
                _gameState.value = GameState.PLAYING

                if (_isSpinning.value && isAnimationPaused) {
                    continueSpinAnimation()
                }
            }
            else -> {}
        }
    }

    private fun continueSpinAnimation() {
        if (savedTargetSector == null || savedTotalRotation == 0f) return

        spinJob?.cancel()
        spinJob = viewModelScope.launch {
            try {

                val remainingRotation = savedTotalRotation - savedCurrentRotation
                val remainingTime = (3000L * (remainingRotation / savedTotalRotation)).toLong()

                if (remainingTime <= 0) {

                    val targetIndex = wheelSectors.indexOf(savedTargetSector!!)
                    val targetAngle = targetIndex * 36f
                    _wheelRotation.value = targetAngle % 360

                    // delay(500) - видалено
                    _currentSector.value = savedTargetSector
                    // delay(1000) - видалено
                    applySectorResult(savedTargetSector!!)
                    return@launch
                }

                val steps = 60
                val stepTime = remainingTime / steps
                val stepRotation = remainingRotation / steps

                for (step in 1..steps) {
                    delay(stepTime)

                    if (_gameState.value == GameState.PAUSED) {
                        // Опять пауза
                        savedCurrentRotation = _wheelRotation.value
                        isAnimationPaused = true
                        return@launch
                    }

                    val currentRotation = savedCurrentRotation + (stepRotation * step)
                    _wheelRotation.value = currentRotation
                }

                // Завершаем анимацию
                val targetIndex = wheelSectors.indexOf(savedTargetSector!!)
                val targetAngle = targetIndex * 36f
                _wheelRotation.value = targetAngle % 360

                // delay(500) - видалено
                _currentSector.value = savedTargetSector

                // delay(1000) - видалено
                applySectorResult(savedTargetSector!!)

                isAnimationPaused = false

            } catch (e: Exception) {
                _isSpinning.value = false
                _currentSector.value = null
            }
        }
    }

    private fun finishGame() {
        _gameState.value = GameState.FINISHED

        if (_spinsLeft.value == 0 && shouldSaveResult) {
            viewModelScope.launch {
                saveResult(_balance.value)
            }
        }
    }

    private suspend fun saveResult(score: Int) {
        val recordsJson = localStorageManager.getItem(RECORDS_KEY)
        val records = if (recordsJson.isEmpty()) {
            mutableListOf()
        } else {
            Json.decodeFromString<List<GameRecord>>(recordsJson).toMutableList()
        }

        val now = Clock.System.now().toEpochMilliseconds()
        val id = records.maxOfOrNull { it.id }?.plus(1) ?: 0L
        records.add(GameRecord(id = id, score = score, date = now))
        localStorageManager.saveItem(Json.encodeToString(records), RECORDS_KEY)
    }


    fun restartGame() {
        _balance.value = 100
        _spinsLeft.value = 10
        _gameState.value = GameState.PLAYING
        _wheelRotation.value = 0f
        _isSpinning.value = false
        _currentSector.value = null
        shouldSaveResult = true
    }

    fun exitGame() {
        shouldSaveResult = false
        spinJob?.cancel()
        _gameState.value = GameState.FINISHED
    }

    override fun onCleared() {
        super.onCleared()
        spinJob?.cancel()
    }
}