package com.console.draw.assessment.util

import com.console.draw.assessment.domain.Canvas
import com.console.draw.assessment.domain.Point
import java.io.*

private const val CANVAS_REQUIRED = "Please create a Canvas to start drawing"
private const val UNEXPECTED_INPUT = "Unexpect Input value, Please try again"

fun parseCoordinates(args: List<String>, canvas: Canvas): List<Point> {
    checkCanvasExists(canvas)
    if (args.size != 4) throw IllegalArgumentException(UNEXPECTED_INPUT)

    val inputs = parseCoordinates(args)
    var points = emptyList<Point>()
    points += Point(inputs[0], inputs[1])
    points += Point(inputs[2], inputs[3])

    return points
}

fun parseCoordinates(args: List<String>): List<Int> {
    var inputs = emptyList<Int>()
    if (args.size < 2) throw IllegalArgumentException(UNEXPECTED_INPUT)
    args.slice(args.indices).map {
        runCatching {
            it.toInt()
        }.onSuccess {
            inputs += it
        }.onFailure {
            throw IllegalArgumentException(UNEXPECTED_INPUT)
        }
    }
    return inputs
}

fun parseBucketFillInput(args: List<String>, canvas: Canvas): Pair<Point, Char> {
    checkCanvasExists(canvas)
    if (args.size != 3 || args[2].length != 1)
        throw IllegalArgumentException(UNEXPECTED_INPUT)

    val filler: Char = args[2].first()
    val inputs =
        parseCoordinates(args.dropLast(1)) //ignore filler char to parse coordinates
    return Pair(Point(inputs.first(), inputs.last()), filler)
}

private fun checkCanvasExists(canvas: Canvas) {
    require((canvas.width() > 0 && canvas.height() > 0)) { CANVAS_REQUIRED }
}

fun readCommands(inputStream: InputStream): List<String> {
    val args = BufferedReader(InputStreamReader(inputStream)).readLine()
    return args.trim().split(" ").filter { it.isNotBlank() }
}

fun displayManual() {
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
    println(out)
}