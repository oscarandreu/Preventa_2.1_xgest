package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

import log.LogWriter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
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

import system.SysData;
import ERP.AgenteErp;
import data.Agente;

/**
 * es la gui de gestion del servidor sonde se le meteria la direccion del servidor
 * @author Elena
 *
 */
public class GuiEstadisticas {

	public JInternalFrame frame = null;  //  @jve:decl-index=0:visual-constraint="79,11"
	protected SysData sys;
	private JTabbedPane jTabbedPane = null;

//	==================================================================
	public GuiEstadisticas( SysData sys) 
//	==================================================================
	{
		this.sys = sys;
	}
//	==================================================================
	public JInternalFrame initialize( JDesktopPane jDesktopPane ) 
//	==================================================================
	{
		frame = new JInternalFrame();		
		jDesktopPane.add( frame );
		frame.setVisible(true);
		try {
			frame.setSelected(true);
			frame.setMaximum(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
			new LogWriter( e1.getStackTrace() );
		}
        frame.setClosable(true);
        frame.setToolTipText("Configuracióon del servidor web de enlace con las PDA's");
        frame.setTitle("Estadisticas");

        //frame.setContentPane(getPanel());
        frame.setFrameIcon(SysData.getIcono("data.gif"));
        frame.setContentPane(getJTabbedPane());
        	

        
        return frame;
	}
//	==================================================================
    private JFreeChart createChart(PieDataset piedataset, String titulo)
//	==================================================================
    {
        JFreeChart jfreechart = ChartFactory.createPieChart3D(titulo, piedataset, true, true, false);
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
	private ChartPanel getPanelClientes() 
//	==================================================================
	{
		  Agente ag = new AgenteErp();
	        PieDataset piedataset = ag.getEstadisticaClientesAgente(sys) ;
	        JFreeChart jfreechart = createChart(piedataset,"Clientes por agente");
	        ChartPanel chartpanel = new ChartPanel(jfreechart);
	        chartpanel.setPreferredSize(new Dimension(500, 270));
	        chartpanel.setLayout(null);
		return chartpanel;
	}
//	==================================================================
	private ChartPanel getPanelVentas() 
//	==================================================================
	{
		  Agente ag = new AgenteErp();
	        PieDataset piedataset = ag.getEstadisticaVentasAgente(sys) ;
	        JFreeChart jfreechart = createChart(piedataset,"Ventas por agente (en €)");
	        ChartPanel chartpanel = new ChartPanel(jfreechart);
	        chartpanel.setPreferredSize(new Dimension(500, 270));
	        chartpanel.setLayout(null);
		return chartpanel;
	}
//	==================================================================
	private ChartPanel getPanelIncidencias() 
//	==================================================================
	{
		  Agente ag = new AgenteErp();
	        PieDataset piedataset = ag.getEstadisticaIncidenciasCliente(sys) ;
	        JFreeChart jfreechart = createChart(piedataset,"Incidencias por cliente");
	        ChartPanel chartpanel = new ChartPanel(jfreechart);
	        chartpanel.setPreferredSize(new Dimension(500, 270));
	        chartpanel.setLayout(null);
		return chartpanel;
	}
//	==================================================================
	private ChartPanel getPanelVentasTiempo() 
//	==================================================================
	{
		  Agente ag = new AgenteErp();
		  XYDataset piedataset = ag.getEstadisticaVentasTiempo(sys) ;
	        JFreeChart jfreechart = createChart(piedataset,"Ventas por agente");
	        ChartPanel chartpanel = new ChartPanel(jfreechart);
	        chartpanel.setPreferredSize(new Dimension(500, 270));
	        chartpanel.setLayout(null);
		return chartpanel;
	}
//	==================================================================
	private ChartPanel getPanelClientesTiempo() 
//	==================================================================
	{
		  Agente ag = new AgenteErp();
		  XYDataset piedataset = ag.getEstadisticaClientesTiempo(sys) ;
	        JFreeChart jfreechart = createChart(piedataset,"Clientes por agente");
	        ChartPanel chartpanel = new ChartPanel(jfreechart);
	        chartpanel.setPreferredSize(new Dimension(500, 270));
	        chartpanel.setLayout(null);
		return chartpanel;
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Clientes", null, getPanelClientes() , null);
			jTabbedPane.addTab("Ventas", null, getPanelVentas() , null);
			jTabbedPane.addTab("Incidencias", null, getPanelIncidencias() , null);
			jTabbedPane.addTab("Ventas por fecha", null, getPanelVentasTiempo() , null);
			jTabbedPane.addTab("Clientes por fecha", null, getPanelClientesTiempo() , null);
		}
		return jTabbedPane;
	}

	   private static JFreeChart createChart(XYDataset dataset,String titulo) {

	        JFreeChart chart = ChartFactory.createTimeSeriesChart(
	            titulo,  // title
	            "Fecha",             // x-axis label
	            "Ventas",   // y-axis label
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
	        plot.setBackgroundAlpha( (float).5 );
	        plot.setWeight( 10 );
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
	
		
}  //  @jve:decl-index=0:visual-constraint="2,21"
