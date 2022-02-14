package ru.htr.ui

import ru.htr.ui.painting.*
import ru.htr.ui.painting.Painter
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.lang.Math.cos
import java.lang.Math.sin
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
    private val tMinLbl: JLabel
    private val tMaxLbl: JLabel
    private val xMinM: SpinnerNumberModel
    private val xMaxM: SpinnerNumberModel
    private val yMinM: SpinnerNumberModel
    private val yMaxM: SpinnerNumberModel
    private val tMinM: SpinnerNumberModel
    private val tMaxM: SpinnerNumberModel
    private val xMin: JSpinner
    private val xMax: JSpinner
    private val yMin: JSpinner
    private val yMax: JSpinner
    private val tMin: JSpinner
    private val tMax: JSpinner


    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = minDim

        xMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        xMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        yMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        yMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        tMinM = SpinnerNumberModel(0.0, -100.0, 0.0, 0.1)
        tMaxM = SpinnerNumberModel(1.0, 0.0, 100.0, 0.1)
        xMin = JSpinner(xMinM)
        xMax = JSpinner(xMaxM)
        yMin = JSpinner(yMinM)
        yMax = JSpinner(yMaxM)
        tMin = JSpinner(tMinM)
        tMax = JSpinner(tMaxM)

        val plane = CartesianPlane(
            xMin.value as Double,
            xMax.value as Double,
            yMin.value as Double,
            yMax.value as Double
        )

        val cartesianPainter = CartesianPainter(plane)
        val functionPainter = FunctionPainter(plane)
        functionPainter.function = {x : Double -> x + 1 / x}
        val paramFunctionPainter = ParamFunctionPainter(plane, tMin.value as Double, tMax.value as Double)
        paramFunctionPainter.funcX = {t: Double -> 16 * sin(t) * sin(t) * sin(t)}
        paramFunctionPainter.funcY = {t: Double -> 13 * cos(t) - 5 * cos(2 * t) - 2 * cos(3 * t) - cos(4 * t)}
        val painters = mutableListOf(cartesianPainter, functionPainter, paramFunctionPainter)

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
        tMinLbl =JLabel().apply {
            text = "Tmin: "
        }
        tMaxLbl =JLabel().apply {
            text = "Tmax: "
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
        tMin.addChangeListener{
            tMaxM.minimum = tMin.value as Double + 0.1
            paramFunctionPainter.t_min = tMin.value as Double
            mainPanel.repaint()
        }
        tMax.addChangeListener{
            tMinM.maximum = tMax.value as Double - 0.1
            paramFunctionPainter.t_max = tMax.value as Double
            mainPanel.repaint()
        }

        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(4)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(
                                mainPanel,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE
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
                        mainPanel,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE
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
                            .addComponent(xMinLbl,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addComponent(yMinLbl,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                    .addGap(4)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(xMin, 100,100,GroupLayout.PREFERRED_SIZE)
                            .addComponent(yMin, 100,100,GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(30)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(xMaxLbl,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addComponent(yMaxLbl,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                    .addGap(4)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(xMax, 100,100,GroupLayout.PREFERRED_SIZE)
                            .addComponent(yMax, 100,100,GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(30)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(tMaxLbl,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                            .addComponent(tMinLbl,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE)
                    )
                    .addGap(4)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(tMax, 100,100,GroupLayout.PREFERRED_SIZE)
                            .addComponent(tMin, 100,100,GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(4,4, Int.MAX_VALUE)
            )
            setVerticalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(xMinLbl,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(xMin,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(xMaxLbl,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(xMax,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(tMaxLbl,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(tMax,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(8)
                    .addGroup(
                        createParallelGroup()
                            .addComponent(yMinLbl,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(yMin,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(yMaxLbl,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(yMax,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(tMinLbl,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                            .addComponent(tMin,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
                    )
                    .addGap(8)
            )
        }
        pack()
        plane.width = mainPanel.width
        plane.height = mainPanel.height
    }
}