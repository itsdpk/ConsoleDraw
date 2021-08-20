package com.console.draw.assessment.domain

import com.github.stefanbirkner.systemlambda.SystemLambda
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CanvasTest {

    @Test
    fun `should get the width of canvas`() {
        val canvas = Canvas(5,5)
        assertEquals(5, canvas.width())
    }

    @Test
    fun `should get the height of canvas`() {
        val canvas = Canvas(5,5)
        assertEquals(5, canvas.height())
    }

    @Test
    fun `should render canvas on output stream`() {
        val canvas = Canvas(5,5)
        val actualCanvas = SystemLambda.tapSystemOut { canvas.render() }.trim()
        assertTrue(actualCanvas.contains("-"))
        assertTrue(actualCanvas.contains("|"))

    }

}