package Gui;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import log.LogWriter;

import system.Cargador;
import system.SysData;
import bbdd.InternalFrameTable;

public class GuiInternalFrame {
	
	protected JInternalFrame frame = null;  //  @jve:decl-index=0:visual-constraint="23,12"
	public SysData sys;
	protected	InternalFrameTable model = null;
	protected Connection conect = null;
	
	//propiedades especificas de cada ventana
	protected String icono = "";
	protected String toolTip = null;
	protected String titulo = null;
	protected boolean tamMaximo = true;
	protected boolean maximizable = true;
	protected boolean iconifiable = true;
	protected JLabel filasImportadas = new JLabel();
	
	/**Codigo que se le asigna al frame durante su tiempo de vida*/
	protected Integer codigo;
	protected Cargador cargador;
	protected String where;
	
//	===========================================================================================
	public GuiInternalFrame( Cargador cargador ) 
//	===========================================================================================
	{
		this.cargador = cargador;
		this.sys = cargador.sys;
		conect = sys.getConexionPropia();
		this.codigo = new Integer( cargador.getCodigoVentana() );
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
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
			new LogWriter( e1.getStackTrace() );
		}
		
		frame.setClosable(true);
		frame.setToolTipText(toolTip);
		frame.setTitle(titulo);
		frame.setResizable(true);
		frame.setIconifiable(true);
		frame.setBounds(new java.awt.Rectangle(50,50,806,507));
		frame.setContentPane( getPanel() );
		frame.setFrameIcon( SysData.getIcono(icono));
		frame.addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosed( InternalFrameEvent e ) {
				cargador.eliminarVentana( codigo );
			}
		});
		
		cargador.añadirVentana( codigo, this );
		return frame;
	}
	/**Metodo que muestra los datos seleccionados en la tabla en los campos que corresponda*/
//	===========================================================================================
	public void mostrarDatos( String where ){}
//	===========================================================================================
	
	/**Metodo que actualiza la tabla con los datos*/
//	===========================================================================================
	public void actualizar() 
//	===========================================================================================
	{
		try {
			model.setData( false );
			filasImportadas.setText("Numero de filas : "+model.numeroFilas);
			filasImportadas.repaint();
			frame.repaint();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	/**Metodo que cierra la ventana*/
//	===========================================================================================
	public void cerrar() 
//	===========================================================================================
	{
		cargador.eliminarVentana( codigo );
		frame.dispose();
	}
	/**Metodo donde se coloca el codigo que construira la ventana*/
//	===========================================================================================
	protected JPanel getPanel() 
//	===========================================================================================
	{ return null; }
}
