package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.model.AppRating
import com.example.model.DuniaUsahaContent
import com.example.model.SlideDestination
import com.example.model.TransitionStyle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PresentationUiState(
    val currentSlide: SlideDestination = SlideDestination.COVER,
    val isForward: Boolean = true,
    val transitionStyle: TransitionStyle = TransitionStyle.SLIDE_SCALE,
    val isFullscreen: Boolean = false,
    val showSlideOverview: Boolean = false,
    val isMusicEnabled: Boolean = true,
    val customAudioName: String? = null,
    val showAudioDialog: Boolean = false,
    // Interactive learning states
    val highlightedAttitudeIndex: Int = 0,
    val selectedVocabIndex: Int? = null,
    val selectedIdiomIndex: Int? = null,
    val highlightMainIdeaInParagraph: Boolean = false,
    // Quiz evaluation states (question number 1..4 -> chosen letter)
    val quizAnswers: Map<Int, String> = emptyMap(),
    // Reflection & QR states
    val selectedRating: AppRating? = null,
    val showQrMissionDialog: Boolean = false,
    // Mini-game states
    val gameModeIndex: Int = 0, // 0 = Tebak Idiom, 1 = Simulasi Warung
    val idiomRoundIndex: Int = 0,
    val idiomGameChoice: String? = null,
    val idiomGameScore: Int = 0,
    val warungCapital: Int = 20000,
    val warungStep: Int = 0,
    val warungFeedback: String? = null,
    val warungStars: Int = 0
) {
    val quizCorrectCount: Int
        get() = DuniaUsahaContent.quizQuestions.count { q ->
            quizAnswers[q.number] == q.correctLetter
        }

    val quizScorePercent: Int
        get() = (quizCorrectCount * 100) / DuniaUsahaContent.quizQuestions.size
}

class PresentationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PresentationUiState())
    val uiState: StateFlow<PresentationUiState> = _uiState.asStateFlow()

    private val orderedSlides = SlideDestination.presentationSlides

    fun navigateTo(destination: SlideDestination) {
        _uiState.update { current ->
            val currentIdx = current.currentSlide.pageNumber
            val targetIdx = destination.pageNumber
            current.copy(
                currentSlide = destination,
                isForward = targetIdx >= currentIdx,
                showSlideOverview = false
            )
        }
    }

    fun nextSlide() {
        _uiState.update { current ->
            if (current.currentSlide == SlideDestination.GAME_ARENA) {
                return@update current.copy(
                    currentSlide = SlideDestination.MENU,
                    isForward = false
                )
            }
            val idx = orderedSlides.indexOf(current.currentSlide)
            if (idx in 0 until orderedSlides.lastIndex) {
                current.copy(
                    currentSlide = orderedSlides[idx + 1],
                    isForward = true
                )
            } else {
                current
            }
        }
    }

    fun previousSlide() {
        _uiState.update { current ->
            if (current.currentSlide == SlideDestination.GAME_ARENA) {
                return@update current.copy(
                    currentSlide = SlideDestination.MENU,
                    isForward = false
                )
            }
            val idx = orderedSlides.indexOf(current.currentSlide)
            if (idx > 0) {
                current.copy(
                    currentSlide = orderedSlides[idx - 1],
                    isForward = false
                )
            } else {
                current
            }
        }
    }

    fun setTransitionStyle(style: TransitionStyle) {
        _uiState.update { it.copy(transitionStyle = style) }
    }

    fun toggleFullscreen() {
        _uiState.update { it.copy(isFullscreen = !it.isFullscreen) }
    }

    fun toggleSlideOverview(show: Boolean? = null) {
        _uiState.update { it.copy(showSlideOverview = show ?: !it.showSlideOverview) }
    }

    fun toggleMusic() {
        _uiState.update { it.copy(isMusicEnabled = !it.isMusicEnabled) }
    }

    fun setCustomAudioName(name: String?) {
        _uiState.update { it.copy(customAudioName = name) }
    }

    fun setAudioDialogVisible(visible: Boolean) {
        _uiState.update { it.copy(showAudioDialog = visible) }
    }

    fun selectAttitude(index: Int) {
        _uiState.update { it.copy(highlightedAttitudeIndex = index) }
    }

    fun selectVocab(index: Int) {
        _uiState.update {
            it.copy(selectedVocabIndex = if (it.selectedVocabIndex == index) null else index)
        }
    }

    fun selectIdiom(index: Int) {
        _uiState.update {
            it.copy(selectedIdiomIndex = if (it.selectedIdiomIndex == index) null else index)
        }
    }

    fun toggleMainIdeaHighlight() {
        _uiState.update { it.copy(highlightMainIdeaInParagraph = !it.highlightMainIdeaInParagraph) }
    }

    fun answerQuiz(questionNumber: Int, letter: String) {
        _uiState.update { current ->
            current.copy(quizAnswers = current.quizAnswers + (questionNumber to letter))
        }
    }

    fun resetQuiz() {
        _uiState.update { current ->
            current.copy(
                quizAnswers = emptyMap(),
                currentSlide = SlideDestination.SOAL_1,
                isForward = false
            )
        }
    }

    fun selectAppRating(rating: AppRating) {
        _uiState.update { it.copy(selectedRating = rating) }
    }

    fun setQrMissionDialogVisible(visible: Boolean) {
        _uiState.update { it.copy(showQrMissionDialog = visible) }
    }

    // Mini-game actions
    fun setGameMode(modeIndex: Int) {
        _uiState.update { it.copy(gameModeIndex = modeIndex) }
    }

    fun answerIdiomGame(choice: String) {
        _uiState.update { current ->
            if (current.idiomGameChoice != null) return@update current
            val round = DuniaUsahaContent.idiomGameRounds[current.idiomRoundIndex]
            val isCorrect = choice == round.correctMeaning
            current.copy(
                idiomGameChoice = choice,
                idiomGameScore = if (isCorrect) current.idiomGameScore + 20 else current.idiomGameScore
            )
        }
    }

    fun nextIdiomRound() {
        _uiState.update { current ->
            val nextIdx = current.idiomRoundIndex + 1
            if (nextIdx < DuniaUsahaContent.idiomGameRounds.size) {
                current.copy(idiomRoundIndex = nextIdx, idiomGameChoice = null)
            } else {
                current.copy(idiomRoundIndex = 0, idiomGameChoice = null, idiomGameScore = 0)
            }
        }
    }

    fun chooseWarungAction(optionIndex: Int) {
        _uiState.update { current ->
            when (current.warungStep) {
                0 -> { // Step 0: Modal Rp20.000
                    when (optionIndex) {
                        0 -> current.copy(
                            warungStep = 1,
                            warungCapital = 32000,
                            warungStars = current.warungStars + 1,
                            warungFeedback = "Pilihan cerdas! Modal Rp20.000 dibelikan bahan kue klepon dan terjual Rp32.000 (+Rp12.000 untung!)."
                        )
                        else -> current.copy(
                            warungStep = 1,
                            warungCapital = 12000,
                            warungFeedback = "Ups, uang jajan habis tanpa produk! Untung masih tersisa semangat belajar wirausaha."
                        )
                    }
                }
                1 -> { // Step 1: Pembeli beri Rp50.000 untuk belanja Rp35.000
                    when (optionIndex) {
                        0 -> current.copy(
                            warungStep = 2,
                            warungCapital = current.warungCapital + 15000,
                            warungStars = current.warungStars + 1,
                            warungFeedback = "Hebat! Kamu jujur mengembalikan Rp15.000 dengan ramah. Pelanggan pun makin percaya!"
                        )
                        else -> current.copy(
                            warungStep = 2,
                            warungFeedback = "Pembeli kecewa jika kembalian kurang atau kurang ramah. Kejujuran adalah kunci!"
                        )
                    }
                }
                2 -> { // Step 2: Promosi usaha
                    when (optionIndex) {
                        0 -> current.copy(
                            warungStep = 3,
                            warungCapital = current.warungCapital + 18000,
                            warungStars = current.warungStars + 1,
                            warungFeedback = "Keren! Poster kreatif dan kemasan bersih membuat produkmu naik daun!"
                        )
                        else -> current.copy(
                            warungStep = 3,
                            warungFeedback = "Tanpa promosi dan menjaga kualitas, konsumen belum mengenal produkmu."
                        )
                    }
                }
                else -> current
            }
        }
    }

    fun resetWarungGame() {
        _uiState.update {
            it.copy(
                warungCapital = 20000,
                warungStep = 0,
                warungFeedback = null,
                warungStars = 0
            )
        }
    }
}
