package com.console.draw.assessment.domain


private const val EMPTY = ' '
private const val ENDS = '-'
private const val SIDES = '|'

class Canvas(
    private val width: Int,
    private val height: Int
) {
    private val drawingArea: Array<CharArray> = Array(width) { CharArray(height) }

    init {
        //initialize drawing area with blank space
        (0 until width).forEach { i ->
            (0 until height).forEach { j ->
                drawingArea[i][j] = EMPTY
            }
        }
    }

    fun width() = width
    fun height() = height

    fun drawLine(point: Point, filler: Char) {
        drawingArea[point.x - 1][point.y - 1] = filler
    }

    fun render() {
        //header border line
        (0 until width + 2).forEach { _ ->
            print(ENDS)
        }
        println()
        //side borders
        (0 until height).forEach { i ->
            print(SIDES)
            (0 until width)
                .forEach { j ->
                    print(drawingArea[j][i])
                }
            print(SIDES)
            println()
        }
        //footer border line
        (0 until width + 2).forEach { _ ->
            print(ENDS)
        }
        println()
    }

    fun bucketFill(point: Point, filler: Char) {
        val column = point.x - 1
        val row = point.y - 1
        if (point.x <= 0 || point.x > width || point.y <= 0 || point.y > height || drawingArea[column][row] != EMPTY)
            return
        drawingArea[column][row] = filler

        bucketFill(Point(point.x + 1, point.y), filler)
        bucketFill(Point(point.x - 1, point.y), filler)
        bucketFill(Point(point.x, point.y + 1), filler)
        bucketFill(Point(point.x, point.y - 1), filler)
    }
}