package com.console.draw.assessment

import com.console.draw.assessment.domain.Canvas
import com.console.draw.assessment.domain.Line
import com.console.draw.assessment.domain.Rectangle
import com.console.draw.assessment.domain.enums.Command
import com.console.draw.assessment.domain.enums.Command.NOOP
import com.console.draw.assessment.domain.enums.Command.CREATE
import com.console.draw.assessment.domain.enums.Command.LINE
import com.console.draw.assessment.domain.enums.Command.RECTANGLE
import com.console.draw.assessment.domain.enums.Command.BUCKET_FILL
import com.console.draw.assessment.domain.enums.Command.QUIT
import com.console.draw.assessment.util.displayManual
import com.console.draw.assessment.util.parseBucketFillInput
import com.console.draw.assessment.util.parseCoordinates
import com.console.draw.assessment.util.readCommands
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.system.exitProcess

fun main() {
    var canvas = Canvas(0, 0)
    var cmd = NOOP
    do {
        print("enter command:")
        val args = readCommands(System.`in`)
        try {
            val operator = args.first()
            val values = args.drop(1)
            cmd = Command.find(operator)
            when (cmd) {
                CREATE -> {
                    val coordinates = parseCoordinates(values)
                    canvas = Canvas(coordinates.first(), coordinates.last())
                }
                LINE -> {
                    val coordinates = parseCoordinates(values, canvas)
                    Line(coordinates.first(), coordinates.last()).drawOn(canvas)
                }
                RECTANGLE -> {
                    val coordinates = parseCoordinates(values, canvas)
                    Rectangle(coordinates.first(), coordinates.last()).drawOn(canvas)
                }
                BUCKET_FILL -> {
                    val (coordinate, filler) = parseBucketFillInput(values, canvas)
                    canvas.bucketFill(coordinate, filler)
                }
                QUIT -> {
                    break
                }
                else -> {
                    displayManual()
                    continue
                }
            }
            canvas.render()
        } catch (e: Exception) {
            displayManual()
        }
    } while (cmd != QUIT)
}
