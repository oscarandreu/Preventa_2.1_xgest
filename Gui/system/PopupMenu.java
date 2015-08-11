package Gui.system;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import log.LogWriter;
import ERP.DatosGeneralesErp;

public class PopupMenu extends JOptionPane {
	
	private JFrame frame;
	private Vector valores = new Vector();
	protected int seleccion = 0;
	protected String valor;
	
	public PopupMenu( JFrame frame , Connection c, int indTabla, String tabla ) {
		this.frame = frame;
		this.setVisible(true);
		
		switch (indTabla){
		case 0:
			DatosGeneralesErp.crearArrayGeneral(c,tabla,valores);
			break;
		case 1:
			DatosGeneralesErp.crearArrayFormaPago(c,valores);
			break;
		case 2:
			DatosGeneralesErp.crearArrayTipoIva(c,valores);
			break;
		}
		
		setValor(getValorSeleccionado());
	}
	
	public String getValorSeleccionado(){
//		this.frame = frame;
//		this.setVisible(true);
		Object val[] = valores.toArray();
		String s = (String)JOptionPane.showInputDialog(
				frame,
				"Elija una opcion",null,
				JOptionPane.PLAIN_MESSAGE,null,val,val[seleccion]);
		//System.out.println("Opcion:" + s);
		return s;
	}
	
	public void setValor( String v ){
		valor = v;
	}
	
	public String getValor(){
		return valor;
	}
	
}
