package com.console.draw.assessment.domain

import com.console.draw.assessment.outline.Shape


private const val LINE_MARKER = 'x'

class Line(
    private val start: Point,
    private val end: Point
) : Shape {

    override fun drawOn(canvas: Canvas) {
        require(fitsIn(canvas)) { "Requested line does not fit on canvas. Please try a smaller line!" }
        require(isStraightLine()) { "Feature not supported. Please try a straight line!" }
        if (isAcross()) {
            val length = end.x - start.x
            for (i in 0..length) canvas.drawLine(Point(start.x + i, start.y), LINE_MARKER)
        } else {
            val length = end.y - start.y
            for (i in 0..length) canvas.drawLine(Point(start.x, start.y + i), LINE_MARKER)
        }
    }

    private fun isAcross(): Boolean = start.y == end.y

    private fun fitsIn(canvas: Canvas): Boolean {
        // check if line fits on canvas
        return !(start.x < 0 || start.y < 0 || end.x > canvas.width() || end.y > canvas.height())
    }

    private fun isStraightLine() = (start.x == end.x || start.y == end.y)
}
