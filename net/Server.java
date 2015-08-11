package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import log.LogWriter;
import system.Cargador;
import Gui.system.ErrorPane;

public class Server extends Thread {

	protected static int PUERTO = 10088;

	protected ServerSocket ss = null;

	protected Socket s = null;

	protected Cargador cargador;

	// ==================================================================
	public Server(Cargador cargador)
	// ==================================================================
	{

		this.cargador = cargador;
		// Creo el server socket
		try {
			// El servidor se pone a la escucha en el puerto de la máquina
			ss = new ServerSocket(PUERTO);
			// this.run();
			// Bloque que controla posibles fallos de la transmision de los
			// datos.
		} catch (IOException e) {
			e.printStackTrace();
			new ErrorPane("Error al crear ServerSocket.\nError:S1");
			new LogWriter(e.getStackTrace());
		}
	}

	// ==================================================================
	public void run()
	// ==================================================================
	{

		// escucho las peticiones y creo los nuevos sockets
		while (true) {
			try {
				s = ss.accept();
				// s.setSoTimeout(4000); //time out del sistema operativo
				System.out
						.println("Nuevo cliente conectandose... creando ServerThread");
				new ServerThread(s, cargador).start();
				s = null;
			} catch (IOException e) {
				e.printStackTrace();
				new ErrorPane("Error al crear Hilos.\nError:S2");
				new LogWriter(e.getStackTrace());
			} finally {
				if (s != null)
					try {
						s.close();
					} catch (IOException e) {
						e.printStackTrace();
						new ErrorPane("Error al cerrarSocket.\nError:S3");
						new LogWriter(e.getStackTrace());
					}
			}
		}

	}

	// fin de la clase
}