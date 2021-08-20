package com.console.draw.assessment.outline

import com.console.draw.assessment.domain.Canvas

interface Shape{
  fun drawOn(canvas: Canvas)
}