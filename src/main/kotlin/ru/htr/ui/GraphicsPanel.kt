package ru.htr.ui

import ru.htr.ui.painting.Painter
import java.awt.Graphics
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import javax.swing.JPanel

open class GraphicsPanel(val painters: List<Painter>) : JPanel() {
    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.let {painters.forEach{p ->
            p.paint(it)
        }
        }
    }

    init {
        painters.forEach{
            it.width = this.width
            it.height = this.height
        }

        addComponentListener(object : ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                painters.forEach{
                    it.width = width
                    it.height = height
                }
                repaint()
            }
        })
    }

    final override fun addComponentListener(l: ComponentListener?) {
        super.addComponentListener(l)
    }

}