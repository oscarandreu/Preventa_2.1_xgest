package log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import Gui.system.ErrorPane;

public class LogWriter {

	public PrintWriter writer;

	public LogWriter(StackTraceElement[] log) {
		File f = new File("./bbdd/err.log");
		try {
			writer = new PrintWriter(
					new BufferedWriter(new FileWriter(f, true)));
			writer.println(new Date().toString()
					+ "-------------------------------------------------");
			for (int i = 0; i < log.length; i++) {
				writer.println(log[i].toString());
			}
			writer
					.println("-----------------------------------------------------------------------------");
			writer.println(" ");
			writer.close();
		} catch (IOException e) {
			new ErrorPane("Error en la generación del archivo de errores");
		}
	}
	
	public LogWriter(StackTraceElement[] log, String anotaciones) {
		File f = new File("./bbdd/err.log");
		try {
			writer = new PrintWriter(
					new BufferedWriter(new FileWriter(f, true)));
			writer.println(new Date().toString()
					+ "-------------------------------------------------");
			for (int i = 0; i < log.length; i++) {
				writer.println(log[i].toString());
			}
			writer.println(anotaciones);
			writer.println("-----------------------------------------------------------------------------");
			writer.println(" ");
			writer.close();
		} catch (IOException e) {
			new ErrorPane("Error en la generación del archivo de errores");
		}
	}
	
	public LogWriter(String anotaciones) {
		File f = new File("./bbdd/err.log");
		try {
			writer = new PrintWriter(
					new BufferedWriter(new FileWriter(f, true)));
			writer.println(new Date().toString()
					+ "-------------------------------------------------");
			writer.println(anotaciones);
			writer.println("-----------------------------------------------------------------------------");
			writer.println(" ");
			writer.close();
		} catch (IOException e) {
			new ErrorPane("Error en la generación del archivo de errores");
		}
	}
	
	

}
