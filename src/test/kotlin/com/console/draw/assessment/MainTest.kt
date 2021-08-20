package com.console.draw.assessment

import com.console.draw.assessment.util.displayManual
import com.console.draw.assessment.util.readCommands
import com.github.stefanbirkner.systemlambda.SystemLambda
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.test.assertEquals


internal class MainTest {

    @BeforeEach
    fun setUp() {
        mockkStatic("com.console.draw.assessment.util.ConsoleUtilKt")
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }


    @Test
    fun `should build a canvas when command for create is invoked`() {
        val expectedOutput = consoleCanvas().toString()
        every { readCommands(any()) } returns listOf("C", "20", "4") andThen listOf("Q")
        val consoleOutput = SystemLambda.tapSystemOut {
            main()
        }
        assertEquals(expectedOutput, consoleOutput)
        verify(exactly = 2) { readCommands(any()) }
    }


    @Test
    fun `should add a Line to canvas when command for line is invoked`() {
        val expectedOutput = consoleCanvasWithLine().toString()
        every { readCommands(any()) } returns listOf("C", "20", "4") andThen listOf(
            "L", "1", "2", "6", "2"
        ) andThen listOf("Q")
        val consoleOutput = SystemLambda.tapSystemOut {
            main()
        }
        assertEquals(expectedOutput, consoleOutput)
        verify(exactly = 3) { readCommands(any()) }
    }

    @Test
    fun `should add a vertical Line to canvas when command for line is invoked`() {
        val expectedOutput = consoleCanvasWithVerticalLine().toString()
        every { readCommands(any()) } returns listOf("C", "20", "4") andThen listOf(
            "L", "6", "3", "6", "4"
        ) andThen listOf("Q")
        val consoleOutput = SystemLambda.tapSystemOut {
            main()
        }
        assertEquals(expectedOutput, consoleOutput)
        verify(exactly = 3) { readCommands(any()) }
    }

    @Test
    fun `should add a Rectangle to canvas when command for Rectangle is invoked`() {
        val expectedOutput = rectangleOnCanvas().toString()
        every { readCommands(any()) } returns listOf("C", "20", "4") andThen listOf(
            "R", "14", "1", "18", "3"
        ) andThen listOf("Q")
        val consoleOutput = SystemLambda.tapSystemOut {
            main()
        }
        assertEquals(expectedOutput, consoleOutput)
        verify(exactly = 3) { readCommands(any()) }
    }

    @Test
    fun `should not add a Rectangle to canvas when it does not fit on canvas`() {
        val expectedOutput = rectangleOnCanvasNotFitting().toString()
        every { readCommands(any()) } returns listOf("C", "20", "4") andThen listOf(
            "R", "140", "10", "108", "30") andThen listOf("Q")
        val consoleOutput = SystemLambda.tapSystemOut {
            main()
        }
        assertEquals(expectedOutput, consoleOutput)
        verify(exactly = 3) { readCommands(any()) }
    }

    @Test
    fun `should not add a Rectangle to canvas when its height does not fit on canvas`() {
        val expectedOutput = rectangleOnCanvasNotFitting().toString()
        every { readCommands(any()) } returns listOf("C", "20", "4") andThen listOf(
            "R", "1", "10", "12", "30") andThen listOf("Q")
        val consoleOutput = SystemLambda.tapSystemOut {
            main()
        }
        assertEquals(expectedOutput, consoleOutput)
        verify(exactly = 3) { readCommands(any()) }
    }

    @Test
    fun `should not add a Rectangle to canvas when its height less than zero`() {
        val expectedOutput = rectangleOnCanvasNotFitting().toString()
        every { readCommands(any()) } returns listOf("C", "20", "4") andThen listOf(
            "R", "-1", "10", "12", "30") andThen listOf("Q")
        val consoleOutput = SystemLambda.tapSystemOut {
            main()
        }
        assertEquals(expectedOutput, consoleOutput)
        verify(exactly = 3) { readCommands(any()) }
    }

    @Test
    fun `should not add a Rectangle to canvas when its width less than zero`() {
        val expectedOutput = rectangleOnCanvasNotFitting().toString()
        every { readCommands(any()) } returns listOf("C", "20", "4") andThen listOf(
            "R", "1", "-1", "12", "30") andThen listOf("Q")
        val consoleOutput = SystemLambda.tapSystemOut {
            main()
        }
        assertEquals(expectedOutput, consoleOutput)
        verify(exactly = 3) { readCommands(any()) }
    }

    @Test
    fun `should bucket fill canvas when command for fill is invoked`() {
        val expectedOutput = fillOnCanvas().toString()
        every { readCommands(any()) } returns listOf(
            "C", "20", "4"
        ) andThen listOf("R", "14", "1", "18", "3") andThen listOf(
            "B", "10", "3", "o"
        ) andThen listOf("Q")
        val consoleOutput = SystemLambda.tapSystemOut {
            main()
        }
        assertEquals(expectedOutput, consoleOutput)
        verify(exactly = 4) { readCommands(any()) }
    }

    @Test
    fun `should not build a canvas when command for create is invoked with un recognized value`() {
        every { readCommands(any()) } returns listOf("F") andThen listOf("Q")
        every { displayManual() } just runs
        main()
        verify(exactly = 1) { displayManual() }
        verify(exactly = 2) { readCommands(any()) }
    }

    @Test
    fun `should throw exception when command for is invoked with un recognized value`() {
        every { readCommands(any()) } returns listOf("C", "E", "R") andThen listOf("Q")
        every { displayManual() } just runs
        main()
        verify(exactly = 1) { displayManual() }
        verify(exactly = 2) { readCommands(any()) }
    }

    private fun consoleCanvas(): StringWriter {
        val out = StringWriter()
        val printWriter = PrintWriter(out)
        canvas(printWriter)
        return out
    }

    private fun consoleCanvasWithLine(): StringWriter {
        val out = StringWriter()
        val printWriter = PrintWriter(out)
        canvas(printWriter)
        lineOnCanvas(printWriter)
        return out
    }

    private fun consoleCanvasWithVerticalLine(): StringWriter {
        val out = StringWriter()
        val printWriter = PrintWriter(out)
        canvas(printWriter)
        verticallineOnCanvas(printWriter)
        return out
    }

    private fun rectangleOnCanvas(): StringWriter {
        val out = StringWriter()
        val printWriter = PrintWriter(out)
        canvas(printWriter)
        rectangleOnCanvas(printWriter)
        return out
    }

    private fun rectangleOnCanvasNotFitting(): StringWriter {
        val out = StringWriter()
        val printWriter = PrintWriter(out)
        canvas(printWriter)
        constructManual(printWriter)
        printWriter.print("enter command:")
        return out
    }


    private fun fillOnCanvas(): StringWriter {
        val out = StringWriter()
        val printWriter = PrintWriter(out)
        canvas(printWriter)
        rectangleOnCanvas(printWriter)
        fillOnCanvas(printWriter)
        return out
    }


    private fun canvas(printWriter: PrintWriter) {
        printWriter.print("enter command:")
        printWriter.println("----------------------")
        printWriter.println("|                    |")
        printWriter.println("|                    |")
        printWriter.println("|                    |")
        printWriter.println("|                    |")
        printWriter.println("----------------------")
        printWriter.print("enter command:")
    }

    private fun lineOnCanvas(printWriter: PrintWriter) {
        printWriter.println("----------------------")
        printWriter.println("|                    |")
        printWriter.println("|xxxxxx              |")
        printWriter.println("|                    |")
        printWriter.println("|                    |")
        printWriter.println("----------------------")
        printWriter.print("enter command:")
    }

    private fun verticallineOnCanvas(printWriter: PrintWriter) {
        printWriter.println("----------------------")
        printWriter.println("|                    |")
        printWriter.println("|                    |")
        printWriter.println("|     x              |")
        printWriter.println("|     x              |")
        printWriter.println("----------------------")
        printWriter.print("enter command:")
    }

    private fun rectangleOnCanvas(printWriter: PrintWriter) {
        printWriter.println("----------------------")
        printWriter.println("|             xxxxx  |")
        printWriter.println("|             x   x  |")
        printWriter.println("|             xxxxx  |")
        printWriter.println("|                    |")
        printWriter.println("----------------------")
        printWriter.print("enter command:")
    }

    private fun fillOnCanvas(printWriter: PrintWriter) {
        printWriter.println("----------------------")
        printWriter.println("|oooooooooooooxxxxxoo|")
        printWriter.println("|ooooooooooooox   xoo|")
        printWriter.println("|oooooooooooooxxxxxoo|")
        printWriter.println("|oooooooooooooooooooo|")
        printWriter.println("----------------------")
        printWriter.print("enter command:")
    }

    private fun constructManual(printWriter: PrintWriter) {
        printWriter.println("Unknown command entered. Please refer to help manual for assistance")
        printWriter.println("")
        printWriter.println("Command \t\tDescription")
        printWriter.println("C w h           Creates a new canvas of width w and height h.")
        printWriter.println("L x1 y1 x2 y2   Creates a new line from (x1,y1) to (x2,y2). Currently only")
        printWriter.println("                horizontal or vertical lines are supported.")
        printWriter.println("R x1 y1 x2 y2   Creates a new rectangle, whose upper left corner is (x1,y1) and")
        printWriter.println("                lower right corner is (x2,y2).")
        printWriter.println("B x y c         fills the entire area connected to (x,y) with \"colour\" c. The")
        printWriter.println("                behaviour of this is the same as that of the \"bucket fill\" tool in paint")
        printWriter.println("                programs.")
        printWriter.println("Q               Quits the program.")
    }
}