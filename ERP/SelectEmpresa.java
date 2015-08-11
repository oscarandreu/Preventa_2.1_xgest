package ERP;

import java.awt.Component;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import system.Configuracion;
import Gui.system.ErrorPane;

public class SelectEmpresa extends JOptionPane {
	
	private static final long serialVersionUID = 1L;
	private Vector nombres = new Vector();
	private Object[] ficheros;
	//private JFrame frame;
	protected int select = 0;

	/** 
	 * Método que nos permite seleccionar una empresa entre varias */
//	==================================================================
	@SuppressWarnings("unchecked")
	public SelectEmpresa( ResultSet rs, Configuracion conf ) 
//	==================================================================
	{		
		//this.frame = frame;
		this.setVisible(true);
		
		try {
			while( rs.next() ){
				if( rs.getString("Tables_in_x").contains("fccli") ){
					nombres.add ( rs.getString("Tables_in_x").substring(5,8) );
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			Vector v = new Vector();
			for ( int i=0; i < nombres.size() ;i++){
				v.add("Empresa "+nombres.get(i));  
			}
			ficheros = v.toArray();
	}
	
//	==================================================================
	public String getEmpresa() 
//	==================================================================
	{
		String out = null;
		
		String s = (String)JOptionPane.showInputDialog(
				null,
				"Seleccione la empresa a importar",
				"Importación desde Xgest",
				JOptionPane.PLAIN_MESSAGE,
				getIcono(""),
				ficheros,
				ficheros[select]
				);
		
		for (int i=0; i<ficheros.length; i++){
			if(ficheros[i].equals(s)){
				System.out.println(ficheros[i]);
				out = (String)nombres.get(i);
			}
		}
		//
		return out;
	}
	
	
//	==================================================================
	protected ImageIcon getIcono(String nombre) 
//	==================================================================
	{
		String out = new String();
		File descriptor = new File(".");
		String rutaTmp = new String(descriptor.getAbsolutePath());
		out = rutaTmp.substring(0, rutaTmp.length() - 1);
		return new ImageIcon(out+"img"+File.separatorChar+nombre);
	}
} 
