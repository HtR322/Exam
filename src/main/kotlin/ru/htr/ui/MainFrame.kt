package ru.htr.ui

import ru.htr.ui.painting.CartesianPainter
import ru.htr.ui.painting.CartesianPlane
import ru.htr.ui.painting.Painter
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.*

class MainFrame: JFrame() {
    private val minDim = Dimension(600,400)
    private val painters = mutableListOf<Painter>()
    private val mainPanel: GraphicsPanel
    private val controlPanel: JPanel

    private val xMinLbl: JLabel
    private val xMaxLbl: JLabel
    private val yMinLbl: JLabel
    private val yMaxLbl: JLabel
    private val xMinM: SpinnerNumberModel
    private val xMaxM: SpinnerNumberModel
    private val yMinM: SpinnerNumberModel
    private val yMaxM: SpinnerNumberModel
    private val xMin: JSpinner
    private val xMax: JSpinner
    private val yMin: JSpinner
    private val yMax: JSpinner


    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = minDim

        xMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        xMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        yMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        yMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        xMin = JSpinner(xMinM)
        xMax = JSpinner(xMaxM)
        yMin = JSpinner(yMinM)
        yMax = JSpinner(yMaxM)

        val plane = CartesianPlane(
            xMin.value as Double,
            xMax.value as Double,
            yMin.value as Double,
            yMax.value as Double
        )

        val cartesianPainter = CartesianPainter(plane)
        val painters = mutableListOf(cartesianPainter)



        mainPanel = GraphicsPanel(painters).apply {
            background = Color.WHITE
        }

        mainPanel.addComponentListener(object : ComponentAdapter(){
            override fun componentResized(e: ComponentEvent?) {
                plane.width = mainPanel.width
                plane.height = mainPanel.height
                mainPanel.repaint()
            }
        })

        controlPanel = JPanel()

        xMinLbl =JLabel().apply {
            text = "Xmin: "
        }
        xMaxLbl =JLabel().apply {
            text = "Xmax: "
        }
        yMinLbl =JLabel().apply {
            text = "Ymin: "
        }
        yMaxLbl =JLabel().apply {
            text = "Ymax: "
        }

        xMin.addChangeListener{
            xMaxM.minimum = xMin.value as Double + 0.1
            plane.xSegment = Pair(xMin.value as Double, xMax.value as Double)
            mainPanel.repaint()
        }
        xMax.addChangeListener{
            xMinM.maximum = xMax.value as Double - 0.1
            plane.xSegment = Pair(xMin.value as Double, xMax.value as Double)
            mainPanel.repaint()
        }
        yMin.addChangeListener{
            yMaxM.minimum = yMin.value as Double + 0.1
            plane.ySegment = Pair(yMin.value as Double, yMax.value as Double)
            mainPanel.repaint()
        }
        yMax.addChangeListener{
            yMinM.maximum = yMax.value as Double - 0.1
            plane.ySegment = Pair(yMin.value as Double, yMax.value as Double)
            mainPanel.repaint()
        }

        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createParallelGroup()
                    .addGap(4)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(
                                mainPanel
                            )
                            .addComponent(
                                controlPanel,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE
                            )
                    )
                    .addGap(4)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addComponent(
                        mainPanel
                    )
                    .addGap(4)
                    .addComponent(
                        controlPanel,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.PREFERRED_SIZE,
                        GroupLayout.PREFERRED_SIZE
                    )
                    .addGap(4)
            )
        }
        controlPanel.layout = GroupLayout(controlPanel).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(20,20, Int.MAX_VALUE)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(
                                xMinLbl,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE
                            )
                            .addComponent(
                                yMinLbl,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE
                            )
                    )
                    .addGap(4)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(
                                xMin,
                                100,
                                100,
                                GroupLayout.PREFERRED_SIZE
                            )
                            .addComponent(
                                yMin,
                                100,
                                100,
                                GroupLayout.PREFERRED_SIZE
                            )
                    )
                    .addGap(40)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(
                                xMaxLbl,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE
                            )
                            .addComponent(
                                yMaxLbl,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE
                            )
                    )
                    .addGap(4)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(
                                xMax,
                                100,
                                100,
                                GroupLayout.PREFERRED_SIZE
                            )
                            .addComponent(
                                yMax,
                                100,
                                100,
                                GroupLayout.PREFERRED_SIZE
                            )
                    )
                    .addGap(20,20, Int.MAX_VALUE)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(
                                xMinLbl,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                            .addComponent(
                                xMin,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                            .addComponent(
                                xMaxLbl,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                            .addComponent(
                                xMax,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                    )
                    .addGap(8)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(
                                xMaxLbl,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                            .addComponent(
                                xMax,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                            .addComponent(
                                yMaxLbl,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                            .addComponent(
                                yMax,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                    )
                    .addGap(8)
            )
        }
    }
}