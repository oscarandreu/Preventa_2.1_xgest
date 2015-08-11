package Gui;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import log.LogWriter;

import system.SysData;
import bbdd.JPanelTable;

public class GuiJPanel {
	
	protected JSplitPane frame = null;  
	public SysData sys;
	protected JPanelTable model = null;
	protected Connection conect = null;
	
	protected JLabel filasImportadas = new JLabel();
	
//	===========================================================================================
	public GuiJPanel( SysData sys) 
//	===========================================================================================
	{
		this.sys = sys;
		conect = sys.getConexionPropia();
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
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	/**Metodo donde se coloca el codigo que construira la ventana*/
//	===========================================================================================
	protected JSplitPane getPanel() 
//	===========================================================================================
	{ return null; }
	
}

	
