package Gui;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.Rotation;

public class GeneradorEstadisticas {
	
//	==================================================================
	public GeneradorEstadisticas() {}
//	==================================================================

//	==================================================================
	public JFreeChart crearEstadisticaCircular(PieDataset piedataset,String title)
//	==================================================================
	{
		JFreeChart jfreechart = ChartFactory.createPieChart3D(title, piedataset, true, true, false);
		jfreechart.setBackgroundPaint(Color.WHITE);
		PiePlot3D pie3dplot = (PiePlot3D)jfreechart.getPlot();
		pie3dplot.setStartAngle(270D);
		//pie3dplot.setBackgroundImage( sys.getIcono("logo mobisys.jpg").getImage() );
		//pie3dplot.setBackgroundAlpha(0.1F);
		pie3dplot.setLabelFont( new Font("LucidaTypewriter", Font.BOLD, 12) );
		pie3dplot.setDirection(Rotation.CLOCKWISE);
		pie3dplot.setForegroundAlpha(0.7F);
		pie3dplot.setNoDataMessageFont( new Font("LucidaTypewriter", Font.BOLD, 50) );
		pie3dplot.setNoDataMessage("No hay datos \nque mostrar.");
		return jfreechart;
	}
//	==================================================================
	public JFreeChart crearEstadisticaLineal(XYDataset dataset,String titulo,String x,String y)
//	==================================================================
	{
		
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				titulo,  // title
				y,             // x-axis label
				x,   // y-axis label
				dataset,            // data
				true,               // create legend?
				true,               // generate tooltips?
				false               // generate URLs?
		);
		
		chart.setBackgroundPaint(Color.white);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		
		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}
		
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		
		return chart;
		
	}
}
