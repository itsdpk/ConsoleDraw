package com.console.draw.assessment.util

import com.console.draw.assessment.domain.Canvas
import com.console.draw.assessment.domain.Point
import com.github.stefanbirkner.systemlambda.SystemLambda
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.test.assertTrue

internal class ConsoleUtilTest {

    @Test
    fun `coordinates should be parsed correctly to draw line on proper canvas`() {
        val args = listOf("1", "2", "3", "4")
        val expectedList = listOf(Point(x = 1, y = 2), Point(x = 3, y = 4))
        val actualList = parseCoordinates(args, Canvas(5, 5))
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `coordinates should throw exception on illegal args`() {
        val args = listOf("a", "2", "3", "4")
        assertThrows(IllegalArgumentException::class.java)
        {
            parseCoordinates(args, Canvas(5, 5))
        }
    }

    @Test
    fun `coordinates should throw exception on illegal args when list is not of expected size`() {
        val args = listOf("1", "2", "3")
        assertThrows(IllegalArgumentException::class.java)
        {
            parseCoordinates(args, Canvas(5, 5))
        }
    }

    @Test
    fun `coordinates should throw exception when canvas is not of expected height`() {
        val args = listOf("1", "2", "3")
        assertThrows(IllegalArgumentException::class.java)
        {
            parseCoordinates(args, Canvas(5, 0))
        }
    }

    @Test
    fun `coordinates should throw exception when canvas is not of expected width`() {
        val args = listOf("1", "2", "3")
        assertThrows(IllegalArgumentException::class.java)
        {
            parseCoordinates(args, Canvas(0, 5))
        }
    }

    @Test
    fun `coordinates should throw exception on illegal args when size requirement is not met`() {
        val args = listOf("1")
        assertThrows(IllegalArgumentException::class.java)
        {
            parseCoordinates(args)
        }
    }

    @Test
    fun `should fill canvas coordinates with filler`() {
        val args = listOf("1", "2", "c")
        val expectedList = Pair(Point(x = 1, y = 2), "c".first())
        val actualList = parseBucketFillInput(args, Canvas(5, 5))
        assertEquals(expectedList, actualList)
    }

    @Test
    fun `coordinates should throw exception on args if size requirement is not met`() {
        val args = listOf("a")
        assertThrows(IllegalArgumentException::class.java)
        {
            parseBucketFillInput(args, Canvas(5, 5))
        }
    }

    @Test
    fun `coordinates should throw exception on args if filler is not a single char`() {
        val args = listOf("1", "2", "abcd")
        assertThrows(IllegalArgumentException::class.java)
        {
            parseBucketFillInput(args, Canvas(5, 5))
        }
    }

    @Test
    fun `read from command line`() {
        val inputStream = "C 3 4".byteInputStream()
        val outcome = readCommands(inputStream)
        assertEquals(3,outcome.size)
    }

    @Test
    fun `read from command line with empty spaces`() {
        val inputStream = "C   3   4".byteInputStream()
        val outcome = readCommands(inputStream)
        assertEquals(3,outcome.size)
    }

    @Test
    fun `read from command line filter blank string`() {
        val inputStream = " ".byteInputStream()
        val outcome = readCommands(inputStream)
        assertEquals(0,outcome.size)
    }

    @Test
    fun `read from command line filter empty string`() {
        val inputStream = "".byteInputStream()
        assertThrows(NullPointerException::class.java)
        {
            readCommands(inputStream)
        }
    }

    @Test
    fun `should display help manual`() {
        val expectedMessage = constructManual()
        val actualMessage = SystemLambda.tapSystemOut { displayManual() }.trim()
        assertEquals(expectedMessage, actualMessage)
    }

    private fun constructManual(): String {
        val out = StringWriter()
        val printWriter = PrintWriter(out)
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
        printWriter.print("Q               Quits the program.")
        return out.toString()
    }
}