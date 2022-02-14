package ru.htr.ui.painting

import java.awt.*

class ParamFunctionPainter(val plane: CartesianPlane, var t_min: Double, var t_max: Double) : Painter {
    var funColor: Color = Color.RED
    var funcX: (Double) -> Double = {t -> t}
    var funcY: (Double) -> Double = {t -> t}
    val steps = 2000

    override fun paint(g: Graphics) {
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
            val step = (t_max - t_min) / steps
            with(plane) {
                for(i in 0 until steps) {
                    val t = t_min + i * step
                    drawLine(xCrt2Scr(funcX(t)),yCrt2Scr(funcY(t)),xCrt2Scr(funcX(t+step)),yCrt2Scr(funcY(t+step)))
                }
            }
        }
    }
}