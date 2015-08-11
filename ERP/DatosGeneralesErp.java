package ERP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import log.LogWriter;
import system.SysData;
import Gui.system.BarraProgreso;
import Gui.system.InfoPane;

public class DatosGeneralesErp {
	
//	 ==================================================================	
	public DatosGeneralesErp()
//	 ==================================================================
	{
		
	}
	
	/**
	 * Metodo que devuelve la descripción de una Zona, dado su codigo
	 * @param codigoZona
	 * @param stErp
	 * @return la descripcion de la zona dada, y la cadena vacia si la tabla Zonas no existe en el ERP 
	 * 
	 */
//	 ==================================================================
	public static String getDescripcionZona(String codigoZona, Statement stErp)
//	 ==================================================================
	{
		String descr = "";
		try{
			ResultSet rs = stErp.executeQuery("SELECT zdes descripcion FROM fczon" + SysData.getEmpresa() + " WHERE zcod='" + codigoZona + "'");
			if (rs.next()){
				descr = rs.getString("descripcion");
			}
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
		}
		
		return descr;
	}
	
	/**
	 * Metodo que devuelve la descripcion de una Ruta de venta, dado su codigo
	 * @param codigoRuta
	 * @param stErp
	 * @return
	 */ 
//	 ==================================================================
	public static String getDescripcionRuta(String codigoRuta, Statement stErp)
//	 ==================================================================
	{
		String descr = "";
		/*
		try{
			ResultSet rs = stErp.executeQuery("SELECT descripcion FROM rutas WHERE codigo='" + codigoRuta + "'");
			if (rs.next()){
				descr = rs.getString("descripcion");
			}
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
		}
		*/
		return descr;
	}
	
	/**
	 * Metodo que devuelve la descricion de un Tipo, dado su codigo
	 * @param codigoTipo
	 * @param stErp
	 * @return
	 */
//	 ==================================================================
	public static String getDescripcionTipo(String codigoTipo, Statement stErp)
//	 ==================================================================
	{
		String descr = "";
		/*
		try{
			ResultSet rs = stErp.executeQuery("SELECT descripcion FROM tipos WHERE codigo='" + codigoTipo + "'");
			if (rs.next()){
				descr = rs.getString("descripcion");
			}
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
		}
		*/
		return descr;
	}
	
	/**
	 * Metodo que devuelve la descripcion de una Actividad, dado su codigo
	 * @param codigoActividad
	 * @param stErp
	 * @return
	 */
//	 ==================================================================
	public static String getDescripcionActividad(String codigoActividad, Statement stErp)
//	 ==================================================================
	{
		String descr = "";
		/*
		try{
			ResultSet rs = stErp.executeQuery("SELECT descripcion FROM actividad WHERE codigo='" + codigoActividad + "'");
			if (rs.next()){
				descr = rs.getString("descripcion");
			}
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
		}
		*/
		return descr;
	}
	
	/**
	 * Metodo que devuelve la descripcion de una Forma de Pago, dado su codigo
	 * @param nombreFormaPago
	 * @param stErp
	 * @return
	 */
//	 ==================================================================
	public static String getNombreFormasPago(String nombreFormaPago, Statement stErp)
//	 ==================================================================
	{
		String descr = "";
		try{
			ResultSet rs = stErp.executeQuery("SELECT gdesfp descripcion FROM fcfpg" + SysData.getEmpresa() + " WHERE gcodfp='" + nombreFormaPago + "'");
			if (rs.next()){
				descr = rs.getString("descripcion");
			}
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
		}
		
		return descr;
	}
	
	/**
	 * Metodo que devuelve la descripcion de un Tipo de Iva, dado su codigo
	 * @param codigoIva
	 * @param stErp
	 * @return
	 */
//	 ==================================================================
	public static String getDescripcionTiposIva(String codigoIva, Statement stErp)
//	 ==================================================================
	{
		String descr = "";
		int codIvaTmp;
		if (codigoIva != null){
			codIvaTmp = new Integer(codigoIva);
			if (codIvaTmp>0){
				try{
					ResultSet rs = stErp.executeQuery("SELECT giva" + codIvaTmp + " descripcion FROM fcgen" + SysData.getEmpresa());
					//+ " WHERE codigo='" + codigoIva + "'");
					if (rs.next()){
						descr = rs.getString("descripcion");
					}
				}catch(SQLException e){
					e.printStackTrace();
					new LogWriter(e.getStackTrace());
				}
			}
		}
		return descr;
	}
	
	/**
	 * Metodo que crea im vector con los distintos tipos generales (par codigo/descripcion) existentes en el ERP
	 * @param c
	 * @param tabla
	 * @param v
	 */
//	 ==================================================================
	public static void crearArrayGeneral(Connection c,String tabla,Vector v)
//	 ==================================================================
	{
		try{
			Statement st = c.createStatement();
			String query = "SELECT codigo, descripcion FROM "+tabla;
			ResultSet rs = st.executeQuery( query );
			while ( rs.next() ){
				v.add(rs.getString("codigo")+"-"+rs.getString("descripcion"));
			}
			
			rs.close();
		}catch ( SQLException ex ) {
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
			
		}
		
	}
	
	/**
	 * Metodo que crea un vector con los distintos Tipos de IVa (par codigo/descripcion) en el ERP
	 * @param c
	 * @param v
	 */
//	 ==================================================================
	public static void crearArrayTipoIva(Connection c,Vector v)
//	 ==================================================================
	{
		try{
			String query = "SELECT codigo, descripcion FROM tipos_iva";
			Statement sta = c.createStatement();
			ResultSet rs = sta.executeQuery( query );
			while ( rs.next() ){
				v.add(rs.getString("codigo")+"-"+rs.getString("descripcion"));
			}
			
			rs.close();
			sta.close();
			
		}catch ( SQLException ex ) {
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
			
		}
	}
	
	/**
	 * Metodo que crea un vector con las distintas Formas de Pago (par codigo/nombre) existentes en el ERP
	 * @param c
	 * @param v
	 */
//	 ==================================================================
	public static void crearArrayFormaPago(Connection c,Vector v)
//	 ==================================================================
	{
		try{
			String query = "SELECT gcodfp codigo, gdesfp nombre FROM fcfpg" + SysData.getEmpresa();
			Statement sta = c.createStatement();
			ResultSet rs = sta.executeQuery( query );
			while ( rs.next() ){
				v.add(rs.getString("codigo")+"-"+rs.getString("nombre"));
			}
			
			rs.close();
			sta.close();
			
		}catch ( SQLException ex ) {
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
			
		}
	}

	public static boolean copiarFotosSd(SysData sys, String rutaDestino) {
		boolean out = false;
		String rutaFotos;
		
		try{
//		 Seleccionamos todas las imagenes alamacenadas en XGEST
		Statement sErp = sys.getConexionErp().createStatement();
		ResultSet rErp = sErp.executeQuery("select grutafotos from fcgen" + sys.getEmpresa());
		if(rErp.next()){
			rutaFotos = rErp.getString("grutafotos");
			
			File directorio = new File(rutaFotos);
			if(directorio.isDirectory()){
				
				String[] archivos = directorio.list();
				BarraProgreso barra = new BarraProgreso( "Fotos", archivos.length );
				barra.setJProgress( 0 );
				
				int indice = 1;
				for(int i=0;i<archivos.length;i++){
					barra.setJProgress( indice );
					indice ++ ;
					
					String codigo = archivos[i];
					if(codigo.toLowerCase().startsWith("art_") && codigo.toLowerCase().endsWith("_1.jpg")){
						//Cambiamos el nombre de la imagen
						File fileFuente = new File(rutaFotos + "//" + codigo);
						
						String nom_codigo = codigo.substring(4,codigo.length());
						nom_codigo = nom_codigo.substring(0,nom_codigo.length()-6);
						nom_codigo = nom_codigo + ".jpg";
						
						new File(rutaDestino + "//Images").mkdir();
						
						File fichero =new File(rutaFotos + "//" + codigo);
						File direc = new File(rutaDestino + "//Images" + "//" + nom_codigo);

						InputStream in = new FileInputStream(fichero);
						OutputStream out2 = new FileOutputStream(direc);
						
//						Transfer bytes from in to out
						byte[] buf = new byte[1024];
						int len;
						while ((len = in.read(buf)) > 0) {
							out2.write(buf, 0, len);
						}
						in.close();
						out2.close();
						
						out = true;
						
						//fileFuente.renameTo(new File(rutaDestino + "//Images" + "//" + nom_codigo));
						
						/*
						String nom_codigo = codigo.substring(4,codigo.length());
						nom_codigo = nom_codigo.substring(0,nom_codigo.length()-6);
						nom_codigo = nom_codigo + ".jpg";
						System.out.println(codigo + ":" + nom_codigo);
						File fileDestino = new File(rutaFotos + "//" + nom_codigo);
						
						FileReader fr = new FileReader(fileFuente); 
                        FileWriter fw = new FileWriter(fileDestino); 
                        BufferedReader br = new BufferedReader(fr); 
                        BufferedWriter bw = new BufferedWriter(fw);
                        int fileLength = (int)fileFuente.length(); 
                        char charBuff[] = new char[fileLength]; 
                        while (br.read(charBuff,0,fileLength) != -1){
                       	  bw.write(charBuff,0,fileLength); 
                        } 
*/

						
						/*
						if ( img.getWidth( null ) > 0 && img.getHeight( null ) > 0 ){
							System.out.println("1.5");
							BufferedImage bi = SysData.getBufferedImage(img);
							System.out.println("2");
							// Escalamos la imagen a 238 x 210
							double scaleX = (double)238 / (double) bi.getWidth(null);
							double scaleY = (double)210 / (double) bi.getHeight(null);
							double fScale = Math.min(scaleX, scaleY);
							System.out.println("3");
							if (fScale != 1 ) {
								AffineTransformOp op = new AffineTransformOp
								(AffineTransform.getScaleInstance(fScale, fScale), null);
								bi = op.filter(bi, null);
							}
							System.out.println("4");

							// Guardamos cada foto en fichero con nombre = codigo del producto
							try {
								new File(rutaDestino + "/Images").mkdir();
								//ImageIO.write( bi, "JPG", new File( "C:/Tecnopolis/Images/"+ rst.getString( "codigo" ) + ".jpg" ) );
								ImageIO.write( bi, "JPG", new File( rutaDestino + "/Images/" + nom_codigo) );
								out = true;
							} catch (Exception ex){
								ex.printStackTrace();
								new LogWriter( ex.getStackTrace() );
								out = false;
								barra.dispose();
							}
						}*/
					}
				}
				barra.dispose();
				
				
			}else{
				new InfoPane("ATENCION","La ruta elegida no es un directorio válido");
			}
		}
		
		}catch (Exception ex1){
			ex1.printStackTrace();
			new LogWriter( ex1.getStackTrace() );
		}

		return out;
	}


}
