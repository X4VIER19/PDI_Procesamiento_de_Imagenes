package com.mycompany.PDI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author XAV
 */

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

public class HistogramaRenderer {
    public static void render(JPanel panelDestino, int[] frecuencias, Color colorBarra, String titulo) {
        XYSeries serie = new XYSeries(titulo);
        for (int i = 0; i < frecuencias.length; i++) {
            serie.add(i, frecuencias[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(serie);

        JFreeChart chart = ChartFactory.createXYBarChart(
                null, "Nivel", false, "Píxeles", dataset,
                PlotOrientation.    VERTICAL, false, false, false
        );

        chart.setBackgroundPaint(new Color(12, 12, 12));
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(12, 12, 12));

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, colorBarra);
        renderer.setBarAlignmentFactor(0.5);
        renderer.setShadowVisible(false);

        ChartPanel chartPanel = new ChartPanel(chart);
        
        panelDestino.removeAll();
        panelDestino.setLayout(new BorderLayout());
        panelDestino.add(chartPanel, BorderLayout.CENTER);
        panelDestino.revalidate();
        panelDestino.repaint();
    }
}