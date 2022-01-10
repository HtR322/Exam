package ru.htr.ui.painting

import java.awt.*

class FunctionPainter(private val plane: CartesianPlane) : Painter
{

    var funColor : Color = Color.BLUE
    var polynom: (Double) -> Double = {x: Double -> x + 1/x}


    override  fun paint(g: Graphics) {
        with(g as Graphics2D) {
            color = funColor
            stroke = BasicStroke(4F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)

            val rh = mapOf(
                RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
                RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
                RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
                RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE
            )
            setRenderingHints(rh)

            with(plane) {
                    for (i in 0 until width) {
                        setRenderingHints(rh)
                        for (i in 0..width) {
                            drawLine(i, yCrt2Scr(polynom(xScr2Crt(i))), i + 1, yCrt2Scr(polynom(xScr2Crt(i))))
                        }
                    }
                }
        }
    }

}