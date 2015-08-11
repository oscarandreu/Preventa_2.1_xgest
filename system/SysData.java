package system;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import log.LogWriter;
import Gui.system.ErrorPane;
import bbdd.BbddConnector;

public class SysData {

	protected Configuracion conf;

	protected BbddConnector bbddConector;
	
	protected static String empresa;
	
	// variable que indica si se accede a la base de datos por primera vez por falta del fichero de configuracion
	protected boolean inicio = false;

	/** Clase encargada de realizar la conexion a la base de datos */
	// ==================================================================
	public SysData() throws Exception
	// ==================================================================
	{
		// primero se carga el archivo de configuracion
		File arch = new File(".\\configuracion.xml");
		if (arch.exists()) {
			XMLDecoder d;
			d = new XMLDecoder(new BufferedInputStream(new FileInputStream(
					"configuracion.xml")));
			conf = (Configuracion) d.readObject();
			d.close();
		} else {
			conf = new Configuracion();
			setConfiguracion( conf );
		}
		
		empresa = conf.getEmpresa();
		// Conecto con las bases de datos
		bbddConector = new BbddConnector( this );
		bbddConector.crearConexionPropia();
		bbddConector.crearConexionErp();
	}
	
	/** Método que guarda la configuración de la aplicacion en un fichero XML */
	// ==================================================================
	public boolean setConfiguracion( Configuracion config )
	// ==================================================================
	{
		boolean out = false;

		try {
			XMLEncoder e;
			e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(
					"configuracion.xml")));
			e.writeObject(config);
			e.close();
			out = true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			new LogWriter( e1.getStackTrace() );
		}
		return out;
	}
	/** Método que devuelve una conexión a nuestra base de datos local */
	// ==================================================================
	public Connection getConexionPropia()
	// ==================================================================
	{
		Connection out = null;
		
		try {
			out =  bbddConector.getConexionPropia();
		} catch (SQLException e) {
			new LogWriter( e.getStackTrace() );
			e.printStackTrace();
		}
		return out;
	}

	/** Método que devuelve una conexión a la base de datos ERP */
	// ==================================================================
	public Connection getConexionErp()
	// ==================================================================
	{
		Connection out = null;
		
		try {
			out =  bbddConector.getConexionErp();
		} catch (SQLException e) {
			new LogWriter( e.getStackTrace() );
			e.printStackTrace();
		}
		return out;
	}


	/**Sustituye el string s en f*/
	// ===================================================================
	public static String stringReplace(String s, String f, String r)
	// ===================================================================
	{
		if (s == null)
			return s;
		if (f == null)
			return s;
		if (r == null)
			r = "";

		int index01 = s.indexOf(f);
		// Returns the index within this string of the first occurrence of the
		// specified character.
		while (index01 != -1) {
			s = s.substring(0, index01) + r + s.substring(index01 + f.length());
			index01 += r.length();
			index01 = s.indexOf(f, index01);
		}
		return s;
	}

	/** devuelve un int al llegarle un objeto, se utiliza con los daots que 
	 * vienen de la pda 
	 * @param in
	 * @return
	 */ 

	// ==================================================================
	public static int setInt(Object in)
	// ==================================================================
	{
		if (in != null && !"".equals(in))
			return new Integer((String) in).intValue();

		return 0;
	}

	/** devuelve un long al llegarle un objeto, se utiliza con los daots que 
	 * vienen de la pda 
	 * @param in
	 * @return
	 */ 

	// ==================================================================
	public static long setLong(Object in)
	// ==================================================================
	{
		if (in != null && !"".equals(in))
			return new Long((String) in).longValue();

		return 0;
	}
	
	/** devuelve un double al llegarle un objeto, se utiliza con los datos que
	 * vienen de la pda
	 * @param in
	 * @return
	 */
	// ==================================================================
	public static double setDouble(Object in)
	// ==================================================================
	{
		if (in != null && !"".equals(in))
			return new Double((String) in).doubleValue();

		return 0D;
	}

	/** Tokeniza las recepciones de datos desde la pda
	 * 
	 * @param in
	 * @return
	 */
	// ==================================================================
	@SuppressWarnings("unchecked")
	public static Iterator tokenizar(String in)
	// ==================================================================
	{
		Vector v = new Vector();
		String tmp = new String();
		boolean flag = true;

		for (int i = 0; i < in.length(); i++) {
			if (in.charAt(i) != '#') {
				tmp += in.charAt(i);
				flag = false;
			} else {
				if (flag) {
					v.add(null);
				} else {
					v.add(tmp);
				}
				tmp = new String();
			}
		}
		// Si el ultimo elemento era nulo lo ajusto
		if (in.charAt(in.length() - 1) == '#')
			v.add(null);
		else
			v.add(tmp);

		return v.iterator();
	}

	// ==================================================================
	public Configuracion getConfiguracion()
	// ==================================================================
	{
		return conf;
	}
	
	/**
	 * Metodo que convierte un decimal, dado por una cadena, en un entero
	 * @param in
	 * @return
	 */
	// ==================================================================
	public static int formatFloatToInteger(String in)
	// ==================================================================
	{
		int index = in.indexOf(".");
		return new Integer( in.substring(0,index));
	}
	
	/**
	 * Metodo que nos devuelve el codigo de agente de una serie 
	 * @param in
	 * @return
	 */
	// ==================================================================
	public static String formatSerieToAgente(String in)
	// ==================================================================
	{
		if (in == null) {
			return "null";
		}
		String out = in.substring(1,in.length());
		return "'" + out + "'";
	}
	
	/**
	 * Metodo que nos convierte las series del programa base, 
	 * en series para la base de datos ERP (en mayusculas)
	 * @param in
	 * @return
	 */
	// ==================================================================
	public static String formatSerieToTecnopolis(String in)
	// ==================================================================
	{
		if (in == null) {
			return "null";
		}
		return "'" + in.toUpperCase() + "'";
	}
	
	// ==================================================================
	public static String formatTimestampToFirebird(Timestamp in)
	// ==================================================================
	{
		if (in == null) {
			return "null";
		}
		String st = in.toString();
		int index = st.indexOf(".");
		String out = st.substring(0, index);
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd") ;
		return "'" + out + "'";
	}
	
	// ==================================================================
	public static String formatYearToFirebird (Timestamp in)
	// ==================================================================
	{
		if ( in == null ){
			return "null";
		}
		String st = in.toString();
		int index = st.indexOf("-");
		String out = st.substring(0,index);
		return "'" + out + "'";
	}
	
//	 ==================================================================
	public static String formatYearToFirebird (String in)
// ==================================================================
	{
		if ( in == null ){
			return "null";
		}
		int index = in.indexOf("-");
		String out = in.substring(0,index);
		return "'" + out + "'";
	}
	

	// ==================================================================
	public static String formatDateToFirebird(Timestamp in)
	// ==================================================================
	{
		if (in == null) {
			return "null";
		}
		// long l = in.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return "'" + df.format(in) + "'";
	}
	
	// ==================================================================
	public static String formatDateToFirebird(String in)
	// ==================================================================
	{
		if (in == null) {
			return "null";
		}
		int index = in.indexOf(" ");
		String out = in.substring(0,index);
		return "'" + out + "'";
	}

	// ==================================================================
	public static String formatTimeToFirebird(Timestamp in)
	// ==================================================================
	{
		if (in == null) {
			return "null";
		}
		long l = in.getTime();
		return "'" + new Time(l).toString() + "'";
	}
	
	// ==================================================================
	public static String formatTimeToFirebird(String in)
	// ==================================================================
	{
		if (in == null) {
			return "null";
		}
		int index = in.indexOf(" ");
		String out = in.substring(index+1,in.length());
		return "'" + out + "'";
		
	}

	/** 
	 * Metodo que limpia las bases de datos locales de cualquier contenido, para un reset del sistema
	 * */
	// ==================================================================
	public void limpiarBaseDatos(int bd)
	// ==================================================================
	{
		switch (bd){
		case 0:	// toda la base de datos
			try {
				Connection conect = getConexionPropia();
				Statement sta = conect.createStatement();
				sta.executeUpdate("delete from agentes");
				sta.executeUpdate("delete from tipos_incidencias where codigo <> '0'");
				sta.executeUpdate("delete from dpersonales");
				sta.executeUpdate("delete from dbancarios");
				sta.executeUpdate("delete from cabecera_pedido");
				sta.executeUpdate("delete from linea_pedido");
				sta.executeUpdate("delete from incidencias");
				sta.executeUpdate("delete from cola_eventos");
				sta.executeUpdate("delete from ppc");
				new ErrorPane("todos los campos de las tablas han sido borrados");

				setConfiguracion(conf);
				sta.close();
				conect.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				new LogWriter( e1.getStackTrace() );
			}
			break;
		case 1:			// las tablas de datos de pedidos
			try {
				Connection conect = getConexionPropia();
				Statement sta = conect.createStatement();
				sta.executeUpdate("delete from linea_pedido ");
				sta.executeUpdate("delete from cabecera_pedido ");
				
				new ErrorPane("todos los campos de las tablas han sido borrados");
				
				//conf.setActualizacionArticulos(null);
				//conf.setActualizacionClientes(null);
				//setConfiguracion(conf);
				sta.close();
				conect.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				new LogWriter( e1.getStackTrace() );
			}
			break;
		case 2: 		// la tabla de incidencias
			try {
				Connection conect = getConexionPropia();
				Statement sta = conect.createStatement();
				sta.executeUpdate("delete from incidencias");
				
				new ErrorPane("todos los campos de las tablas han sido borrados");
				
				//conf.setActualizacionArticulos(null);
				//conf.setActualizacionClientes(null);
				//setConfiguracion(conf);
				sta.close();
				conect.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				new LogWriter( e1.getStackTrace() );
			}
			break;
		}
	}

	/**
	 * Metodo que devuelve una cadena con 2 decimales dado una cadena de un Double dado,
	 * calculando el redondeo correspondiente
	 * 
	 * @param String
	 *            valor del double en tipo String
	 * @return String
	 * 			  valor del double en tipo String con solo 2 decimales
	 */
	// ==================================================================
	public static String formatearDouble2Dec(String in) 
	// ==================================================================
	{
		Double d = new Double(in);
		d = Math.rint( d*100 )/100;
		return d.toString();
	}
	// ==================================================================
	public static String formatearDouble4Dec(String in) 
	// ==================================================================
	{
		Double d = new Double(in);
		d = Math.rint( d*10000 )/10000;
		return d.toString();
	}
	
	// ==================================================================
	public static double formatearDoubleFromPda(String in) throws Exception
	// ==================================================================
	{
		return Double.parseDouble(in.replace(',', '.'));
	}
	
	// ==================================================================
	public static String formatearDoubleToPda(double in) 
	// ==================================================================
	{
		return Double.toString(in).replace('.', ',');
	}

	/**
	 * El string 'null' hace que la bbdd falle por lo que si el valor es nulo lo
	 * devuelvo sin ' en caso contrario pongo los '
	 */
	// ==================================================================
	public static String formatear(String in)
	// ==================================================================
	{
		if (in == null)
			return "null";

		return "'" + in + "'";
	}
	
	// ==================================================================
	public static String formatearZeroToNull(String in)
	// ==================================================================
	{
		
		if( in.equals("0")) 
			return "null";
		return "'" + in + "'";
	}


	// ==================================================================
	public static String formatear(float in)
	// ==================================================================
	{
		/*
		 * if( in == null) return "null";
		 */

		return "'" + in + "'";
	}

	// ==================================================================
	public static String formatear(Date in)
	// ==================================================================
	{
		if (in == null)
			return "null";

		return "'" + in + "'";
	}

	/**
	 * Devuelve un icono al especificarle la ruta del mismo
	 * 
	 * @param String
	 *            ruta del icono
	 * @return ImageIcon icono
	 */
	// ==================================================================
	public static ImageIcon getIcono(String nombre)
	// ==================================================================
	{
		String out = new String();
		File descriptor = new File(".");
		String rutaTmp = new String(descriptor.getAbsolutePath());
		out = rutaTmp.substring(0, rutaTmp.length() - 1);
		return new ImageIcon(out + "img" + File.separatorChar + nombre);
	}

	// ==================================================================
	public static BufferedImage createTransparentImage(final int width,
			final int height)
	// ==================================================================
	{
		final BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		final int[] data = img.getRGB(0, 0, width, height, null, 0, width);
		Arrays.fill(data, 0x00000000);
		img.setRGB(0, 0, width, height, data, 0, width);
		return img;
	}

	/**
	 * Creates a transparent icon. The Icon can be used for aligning menu items.
	 * 
	 * @param width
	 *            the width of the new icon
	 * @param height
	 *            the height of the new icon
	 * @return the created transparent icon.
	 */
	// ==================================================================
	public static Icon createTransparentIcon(final int width, final int height)
	// ==================================================================
	{
		return new ImageIcon(createTransparentImage(width, height));
	}
	
//	 ==============================================================
	public boolean isInicio()
//	 ==============================================================
	{
		return inicio;
	}

//	 ==============================================================
	public void setInicio(boolean inicio)
//	 ==============================================================
	{
		this.inicio = inicio;
	}
	
	/**
	 * Metodo que nos devuelve un String con la Empresa seleccionada y almacenada en Configuracion
	 * @param conf
	 * @return
	 */
//	 ==============================================================
	public static String getEmpresa()
//	 ==============================================================
	{
		return empresa;
	}
	
//	 ==============================================================
	public static void setEmpresa(String empr)
//	 ==============================================================
	{
		empresa = empr;
	}
	
	
	/**
	 * Devuelve un objeto de tipo BufferedImage de un objeto Image dado
	 * @param img
	 * @return
	 */
//	==================================================================
	public static BufferedImage getBufferedImage(Image img)
//	==================================================================
	{
		BufferedImage bi = null;
		int width = -1;
		int height = -1;
		
		// Crea una buffered image con un formato que es compatible con la ventana
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determina el tipo de transparencia de la nueva biffered image
			int transparency = Transparency.OPAQUE;
			
			// Crea la buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			width = img.getWidth(null);
			height = img.getHeight(null);
			if (width >= 0 && height >= 0){
				bi = gc.createCompatibleImage( width, height, transparency );
			}
		} catch (HeadlessException e) {
			// El sistema no tiene una pantalla
			new LogWriter( e.getStackTrace() );
		}
		
		if (bi == null && width >=0 && height >=0 ) {
			// Crea una buffered image usando el modelo de color por defecto
			int type = BufferedImage.TYPE_INT_RGB;
			bi = new BufferedImage(img.getWidth(null), img.getHeight(null), type);
		}
		
		// Copia image a buffered image
		Graphics g = bi.createGraphics();
		
		// Pinta la image en la buffered image
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		return bi;
	}
	/**Comprueba que el string continene la cadena  jpg,gif*/
	public boolean esFormatoFoto(String str){
		int index = str.indexOf(".");
		String tipo = str.substring(index+1,str.length());
		if( tipo.equals("jpg") || tipo.equals("gif") ){
			return true;
		}
		return false;
		
	}


}