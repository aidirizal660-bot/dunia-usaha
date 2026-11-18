package com.example

import com.example.model.AppRating
import com.example.model.SlideDestination
import com.example.model.TransitionStyle
import com.example.viewmodel.PresentationViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PresentationViewModelTest {

    @Test
    fun `next and previous slide navigate sequentially across 26 slides`() {
        val vm = PresentationViewModel()
        assertEquals(SlideDestination.COVER, vm.uiState.value.currentSlide)

        vm.nextSlide()
        assertEquals(SlideDestination.HALLO, vm.uiState.value.currentSlide)
        assertTrue(vm.uiState.value.isForward)

        vm.previousSlide()
        assertEquals(SlideDestination.COVER, vm.uiState.value.currentSlide)
        assertFalse(vm.uiState.value.isForward)
    }

    @Test
    fun `quiz answers compute accurate score`() {
        val vm = PresentationViewModel()
        vm.answerQuiz(1, "B")
        vm.answerQuiz(2, "C")
        vm.answerQuiz(3, "B")
        vm.answerQuiz(4, "D")

        assertEquals(4, vm.uiState.value.quizCorrectCount)
        assertEquals(100, vm.uiState.value.quizScorePercent)

        vm.resetQuiz()
        assertEquals(0, vm.uiState.value.quizCorrectCount)
        assertEquals(SlideDestination.SOAL_1, vm.uiState.value.currentSlide)
    }

    @Test
    fun `transition style and rating selection update state`() {
        val vm = PresentationViewModel()
        vm.setTransitionStyle(TransitionStyle.BOOK_FLIP)
        vm.selectAppRating(AppRating.SERU)

        assertEquals(TransitionStyle.BOOK_FLIP, vm.uiState.value.transitionStyle)
        assertEquals(AppRating.SERU, vm.uiState.value.selectedRating)
    }

    @Test
    fun `toggleMusic flips backsound enabled state`() {
        val vm = PresentationViewModel()
        assertTrue(vm.uiState.value.isMusicEnabled)

        vm.toggleMusic()
        assertFalse(vm.uiState.value.isMusicEnabled)

        vm.toggleMusic()
        assertTrue(vm.uiState.value.isMusicEnabled)
    }

    @Test
    fun `custom audio name and audio dialog visibility update state`() {
        val vm = PresentationViewModel()
        assertEquals(null, vm.uiState.value.customAudioName)
        assertFalse(vm.uiState.value.showAudioDialog)

        vm.setAudioDialogVisible(true)
        vm.setCustomAudioName("lagu_lain.mp3")

        assertTrue(vm.uiState.value.showAudioDialog)
        assertEquals("lagu_lain.mp3", vm.uiState.value.customAudioName)

        vm.setCustomAudioName(null)
        vm.setAudioDialogVisible(false)
        assertEquals(null, vm.uiState.value.customAudioName)
        assertFalse(vm.uiState.value.showAudioDialog)
    }
}
