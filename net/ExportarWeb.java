package net;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.jibble.simpleftp.SimpleFTP;

import log.LogWriter;
import system.SysData;
import Gui.system.BarraProgreso;
import Gui.system.InfoPane;

public class ExportarWeb extends Thread{
	
	protected SysData sys = null;
	protected Connection con = null;
	protected Statement st = null;
	protected ResultSet rs = null;
	
	private String unidad = null;
	private String[] archivos = null;
	private List lista = new ArrayList();
	private JFrame padre = null;
	
	private String servidor = null;
	private String usuario = null;
	private String password = null;
	
	public ExportarWeb(SysData s,JFrame p,String ser,String us,String pass){
		sys = s;
		padre = p;
		servidor = ser;
		usuario = us;
		password = pass;
	}
	
	public void run(){
		
		// gestión de las fotos que vamos a exportar a la WEB
		if (gestionFotos()){
			
			// creamos una barra para mostrar el progreso de la exportación 
			BarraProgreso barra = new BarraProgreso("FTP",lista.size() + 1);
			
			// creamos la bdd con los datos de artículos a exportar a la web
			crearConexion();
			borrarBDD();
			crearBDD();
			cerrarConexion();
			
			// exportamos la bdd de articulos y las fotos a la web
			try{
				// ********** Creamos la conexión FTP **********
				SimpleFTP ftpclient = new SimpleFTP();
				
				System.out.println( "FTP:" + servidor + " User:" + usuario + " Pass:" + password );
				ftpclient.connect( servidor, 21, usuario, password );
				//	ftpclient.connect("ftp.mobisys.es",21,"mobisys","xlbstnih");
				ftpclient.bin();
				
				// ********** Subimos la BDD **********
				String out = new String();
				File descriptor = new File(".");
				String rutaTmp = new String(descriptor.getAbsolutePath());
				out = rutaTmp.substring(0, rutaTmp.length() - 1);
				
				ftpclient.cwd("db");
				
				barra.setJProgress(1);
				
				ftpclient.stor(new File( out + "\\web\\db\\productos.mdb" ));
				ftpclient.cwd("..");
				
				// ********** Subimos las fotos *********
				ftpclient.cwd("www");
				ftpclient.cwd("images");
				
				
				// Subimos cada foto de la lista creada en gestionFotos()
				for (int i=0;i<lista.size();i++){
					barra.setJProgress(i+2); 
					System.out.println( lista.get(i).toString() );
					
					ftpclient.stor(new File(unidad + "\\" + lista.get(i).toString()));
					
					System.out.println(unidad + "\\" + lista.get(i).toString());
				}
				barra.dispose();
				
				// Cerramos la sesión FTP
				ftpclient.disconnect();
				
			}catch(IOException ex){
				ex.printStackTrace();
				new LogWriter( ex.getStackTrace() );
				new InfoPane("ERROR","Error en la conexión FTP");
			}
		}
	}
	
	public void crearConexion(){
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String path = "jdbc:odbc:preventa";
			con = DriverManager.getConnection(path, "", "");
		}catch(SQLException ex){
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
		}catch(Exception ex){
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
		}
		
	}
	
	public void borrarBDD(){
		try{
			st = con.createStatement();
			st.executeUpdate("DELETE FROM tcproductos");
			st.close();
		}catch(SQLException ex){
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
		}
	}
	
	public void crearBDD(){
		try{
			Statement s = sys.getConexionPropia().createStatement();
			ResultSet rs = s.executeQuery("SELECT codigo,familia,nombre,observaciones,pvp_1 FROM articulos");
			st = con.createStatement();
			
			while (rs.next()){
				//Insertamos todos los valores deseados en nuestra base de datos vacia
				String query = "INSERT INTO tcproductos (ref,tipo,modelo,precioeu) " +
				"VALUES " +
				"(" +
				"'" + rs.getString("codigo") + "'," +
				SysData.formatear(rs.getString("familia")) + "," +
				SysData.formatear(rs.getString("nombre")) + "," +
				"'" + rs.getString("pvp_1") + "'" +
				")";
				System.out.println( query );
				st.executeUpdate( query );
			}
			rs.close();
			s.close();
			st.close();
		}catch(SQLException ex){
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
		}
	}
	
	public void cerrarConexion(){
		try{
			con.close();
		}catch(SQLException ex){
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
		}
	}
	
	public URLConnection crearConexionFTP(String str){
		try{
			URL url = new URL(str);
			URLConnection urlc = url.openConnection();
			return urlc;
		}catch(MalformedURLException e1){
			e1.printStackTrace();
			new LogWriter( e1.getStackTrace() );
		}catch(IOException e2){
			e2.printStackTrace();
			new LogWriter( e2.getStackTrace() );
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public boolean gestionFotos(){
		// Introducimos la carpeta donde debemos buscar las fotos
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Seleccione el directorio donde están almacenadas las fotos");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showDialog(padre,"Aceptar");
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			unidad = chooser.getSelectedFile().getAbsolutePath();				    	
			System.out.println("Directorio elegido: " + unidad );
		}else{
			return false;
		}
		
		// Listamos todas las fotos del directorio elegido
		if ( unidad != null ) {
			File dir = new File(unidad);
			if (dir.isDirectory()) {
				if (dir.exists()) {
					//tomamos los archivos contenidos en la URL dada
					archivos = dir.list();
					if(archivos.length>0){
						//agregamos cada fichero de tipo .JPG en una lista
						for(int i=0;i<archivos.length;i++){
							if( esFormatoFoto(archivos[i]) ){
								lista.add(archivos[i]);
							}
						}
					}else{
						new InfoPane("ATENCIÓN","El directorio elegido está vacio");
					}
					
				}
				
			}
			
		}
		return true;
		
	}
	
	public boolean esFormatoFoto(String str){
		int index = str.indexOf(".");
		if(str.substring(index+1,str.length()).equals("jpg")){
			return true;
		}
		return false;
		
	}
	
	

}
