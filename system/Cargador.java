package system;


import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.crypto.Cipher;


import log.LogWriter;

import net.Server;
import net.https;
import ERP.ActualizarDatosFromErp;
import Gui.GuiInternalFrame;
import Gui.MainGui;
import Gui.PiePrincipal;
import Gui.system.ErrorPane;
import Gui.system.Loader;


public class Cargador {

	public static Loader loader = null;

	public MainGui gui = null;

	public SysData sys = null;

	protected Server server = null;

	protected ActualizarDatosFromErp actualizador = null;

	protected https conexserv = null;

	public PiePrincipal pie = null;

	protected int contadorFallosHttp = 0;

	/** Vector que guarda la referencia a las ventanas */
	protected Vector ventanas = new Vector();

	/** Vector que guarda los codigos asignados a cada ventana */
	protected Vector codigos = new Vector();

	/** Contador de ventanas */
	protected int numVentanas;

	static {
		System.loadLibrary("kernel");
	}

	/** devuelve el Cipher preparado para descifrar la licencia */
	private native Cipher a1();

	/** Devuelve la licencia en byte[] */
	@SuppressWarnings("unused")
	private native byte[] a2();

	/** Carga los codigos del cliente etc... en esta clase */
	private native void a3();

	/** Comprueba la validez del codigo de activacion */
	private native void a4();

	/** Comprueba la fecha de la licencia */
	private native void a5();

	/**
	 * Devuelve los datos de la licencia segun se lo indiquemos en el int , la
	 * funcion hace modulo 6 del int y nos deuelve a1 a a5 segun el resultado
	 */
	public native String get(int i);

	/** Propiedades exclusivas para la licencia */
	/** Codigo Activación */
	public double a1;

	/** Fecha de validez de la licencia */
	public long a2;

	/** Codigo de cliente */
	public String a3;

	/** Contraseña del cliente */
	public String a4;

	/** Numero de pdas */
	public String a5;

	/** Fecha de alta de la licencia */
	public long a6;

	/** Unidad de la aplicacion */
	public String unidad;

	/**
	 * Esta clase va ha ser el que se encargue de hacer de puente entre los
	 * distintos componentes en busca de eventos/cambios que necesiten de alguna
	 * acción especifica
	 */
	// ==================================================================
	public Cargador()
	// ==================================================================
	{
		try {
//			a3();
//			a4();
//			a5();

			loader = new Loader();
			server = new Server(this);
			sys = new SysData();								
			pie = new PiePrincipal(this);
			gui = new MainGui(this);
			pie.setJDesktop( gui.jDesktopPane );
			conexserv = new https(this);
			actualizador = new ActualizarDatosFromErp(this);
			conexserv.start();
			server.start();

			actualizador.start();

		} catch (Exception e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
			new ErrorPane(
					"Se han generado demasiados errores\nLa aplicación no puede continuar." +
					"\nCompruebe que la configuración es la correcta y seleccione una empresa." +
					"\nSi la aplicación sigue fallando contacte con su disctribuidor.");
			if (loader != null)
				loader.dispose();
		}

	}

	// ==================================================================
	@SuppressWarnings("unused")
	private String getPath()
	// ==================================================================
	{
		return (new File(".").getAbsolutePath().charAt(0) + ":\\");
	}

	// ==================================================================
	@SuppressWarnings("unused")
	private String getData() throws Exception
	// ==================================================================
	{
		// /*
		FileInputStream fr = new FileInputStream("licencia.lic");
		byte[] buffer = new byte[128];
		fr.read(buffer);
		// Coger dato desde C
		fr.close();
		return new String(a1().doFinal(buffer), "8859_1");
		// */
		// return new String( a1().doFinal( a2() ), "8859_1");
	}

	// ==================================================================
	public void eliminarVentana(Integer codigo)
	// ==================================================================
	{
		Iterator it = codigos.iterator();
		boolean done = false;
		Integer oldCode;
		int i = 0;
		while (it.hasNext() && !done) {
			oldCode = (Integer) it.next();
			if (oldCode.equals(codigo)) {
				codigos.remove(i);
				ventanas.remove(i);
				done = true;
			}
			i++;
		}
	}

	// ==================================================================
	@SuppressWarnings("unchecked")
	public void añadirVentana(Integer codigo, GuiInternalFrame ventana)
	// ==================================================================
	{
		codigos.add(codigo);
		ventanas.add(ventana);
	}

	// ==================================================================
	public int getCodigoVentana()
	// ==================================================================
	{
		numVentanas++;
		return numVentanas;
	}

	// ==================================================================
	public void actualizarVentanas()
	// ==================================================================
	{
		Iterator it = ventanas.iterator();
		while (it.hasNext()) {
			GuiInternalFrame gui = (GuiInternalFrame) it.next();
			gui.actualizar();
		}
	}

	/*
	 * PARA ACTUALIAR LAS VENTANAS : JInternalFrame[] frames =
	 * jMDIDesktopPane.getAllFrames(); CONSIGO UN ARRAY DE LAS VENTANAS, TOMO
	 * LAS QUE ME INTERESE Y YA ESTA.
	 */
	/**
	 * Realiza los cambios en el gui para indicar que la conexion no esta activa
	 * o viceversa
	 */
	// ==================================================================
	public void estadoConexionHttp(boolean activa)
	// ==================================================================
	{
		// RECUERDA QUE AQUI SE VUELVE A COMPROBAR LA LICENCIA
		a4();
		a5();
		if (activa) {
			pie.cambiarSemaforo(1);
			contadorFallosHttp = 0;
		} else {
			contadorFallosHttp++;
			if (contadorFallosHttp >= 5) // Si al minuto no hemos logrado
											// conexion lo ponemos en rojo
				pie.cambiarSemaforo(3);
			else
				pie.cambiarSemaforo(2);
		}
	}

	// ==============================================================
	public void setContadorEventosCargador(int cantidad )
	// ==============================================================
	{
		pie.setContadorEventosPie( cantidad );
	}

	// ==============================================================	
	public SysData getSys()
	// ==============================================================
	{
		return sys;
	}

	public Server getServer() {
		return server;
	}
}