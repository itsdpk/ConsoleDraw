package com.console.draw.assessment.domain

import com.console.draw.assessment.outline.Shape

private const val LINE_MARKER = 'x'

class Rectangle(private val start: Point, private val end: Point) : Shape {

    override fun drawOn(canvas: Canvas) {
        require(fitsIn(canvas)) { "Requested shape does not fit on canvas!" }
        val width = end.x - start.x
        val height = end.y - start.y

        for (i in 0..width) canvas.drawLine(Point(start.x + i, start.y), LINE_MARKER)
        for (i in 0..width) canvas.drawLine(Point(start.x + i, end.y), LINE_MARKER)
        for (i in 0..height) canvas.drawLine(Point(start.x, start.y + i), LINE_MARKER)
        for (i in 0..height) canvas.drawLine(Point(end.x, start.y + i), LINE_MARKER)

    }

    // check if line fits on canvas
    private fun fitsIn(canvas: Canvas) =
        (start.x >= 0 || start.y >= 0 || end.x <= canvas.width() || end.y <= canvas.height())

}
