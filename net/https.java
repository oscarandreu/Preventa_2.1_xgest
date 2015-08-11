package net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Security;

import log.LogWriter;

import system.Cargador;
import system.Configuracion;
import system.SysData;

/**
 * es la clase que se encanga de comunicarse con el servidor web en princio en
 * https
 * 
 * @author Elena
 * 
 */
public class https implements Runnable {

	private boolean threadSuspended;

	private volatile Thread blinker;

	/** Retraso en el hilo en milisegundos */
	protected static final int DELAY = 10000;

	protected Cargador cargador;

	protected SysData sys;

	// ==================================================================
	public https(Cargador cargador)
	// ==================================================================
	{
		this.sys = cargador.sys;
		this.cargador = cargador;
	}

	// ==================================================================
	public void start()
	// ==================================================================
	{
		blinker = new Thread(this);
		blinker.start();
	}

	// ==================================================================
	public void run()
	// ==================================================================
	{
		Thread thisThread = Thread.currentThread();
		while (blinker == thisThread) {
			try {
				Thread.sleep(DELAY);
				conectar();
				synchronized (this) {
					while (threadSuspended && blinker == thisThread)
						wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				new LogWriter( e.getStackTrace() );
			}
		}
	}

	// ==================================================================
	public synchronized void stop()
	// ==================================================================
	{
		blinker = null;
		notify();
	}

	// ==================================================================
	public void conectar()
	// ==================================================================
	{
		try {
			System.setProperty("java.protocol.handler.pkgs",
					"com.sun.net.ssl.internal.www.protocol");
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			Configuracion conf = sys.getConfiguracion();
			URL url = new URL(conf.getServidor() + "/dynDns.php");
			URLConnection k = url.openConnection();
			k.addRequestProperty("charset", "ISO-8859-1");
			k.setDoOutput(true); // Conexión para salida
			k.setUseCaches(false); // El navegador no usa caché
			DataOutputStream salida = new DataOutputStream(k.getOutputStream());

			String content = "d1=" + cargador.a3 + "&d2=" + cargador.a4;
			salida.writeBytes(content);
			salida.flush();
			salida.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(k.getInputStream()));
			String inputLine = " ";

			while ( ( inputLine = in.readLine() ) != null ) {
				//System.out.println("http -> "+inputLine);
				if ( inputLine.equals("err1") || 
					 inputLine.equals("err2") || 
					 inputLine.equals("err3") || 
					 inputLine.equals("err4"))
					cargador.estadoConexionHttp(false);
				else
					cargador.estadoConexionHttp(true);
				inputLine = " ";
			}
			in.close();

		} catch (Exception e) {
			//e.printStackTrace();
			cargador.estadoConexionHttp(false);
			new LogWriter( e.getStackTrace() );
		}

	}

}
