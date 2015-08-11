package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import log.LogWriter;
import system.SysData;

public class NetCodec {

	protected Socket clientSock;

	protected BufferedReader entrada;

	protected PrintWriter salida;
	
	//protected SysData sys;
	
	// ==================================================================
	public NetCodec( Socket clientSock ) throws java.io.IOException
	// ==================================================================
	{
		this.clientSock = clientSock;
		entrada = new BufferedReader(new InputStreamReader(clientSock
				.getInputStream()));
		salida = new PrintWriter(clientSock.getOutputStream());
	}
//XGEST
//	 ==================================================================
	public void enviar(String in)
	// ==================================================================
	{
		in = SysData.stringReplace(in, "null", "");
		System.out.println("out-> " + in);
		in = this.eweEncode(in);
		//salida.println(in.getBytes());
		salida.println(byteArrayToHexString(in.getBytes()));
		// System.out.println("-> "+byteArrayToHexString(in.getBytes()));
		salida.flush();
	}
//	XGEST
	// ==================================================================
	protected String recibir() throws IOException
	// ==================================================================
	{
		String out = "";
		out = entrada.readLine();
		//System.out.println("in<- " + out);
		// System.out.println("<- "+out);
		out = this.eweDecode(out);
		out = new String(hexStringToByteArray(out) );
		
		return out;
	}
	
	/** Metodo que cierra todos las propiedades abiertas de la conexion */
	// ==================================================================
	protected void cerrarConexion()
	// ==================================================================
	{
		if (entrada != null) {
			try {
				entrada.close();
			} catch (IOException e) {
				e.printStackTrace();
				new LogWriter( e.getStackTrace() );
			}
			entrada = null;
		}
		if (salida != null) {
			salida.close();
			salida = null;
		}
		if (clientSock != null) {
			try {
				clientSock.close();

			} catch (IOException e) {
				e.printStackTrace();
				new LogWriter( e.getStackTrace() );
			}
			clientSock = null;
		}
	}
	
	// ==================================================================
	public String byteArrayToHexString(byte[] b)
	// ==================================================================
	{
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	// ==================================================================
	public byte[] hexStringToByteArray(String s)
	// ==================================================================
	{
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}
	
	// ==================================================================
	public String eweEncode(String linea)
	// ==================================================================
	{
		String out = new String();
		char temp;
		String sTemp;
		for (int i = 0; i < linea.length(); i++) {
			sTemp = "";
			temp = linea.charAt(i);
			if (temp == '�')
				sTemp += "ñ";
			if (temp == '�')
				sTemp += "Ñ";
			if (temp == '�')
				sTemp += "á";
			if (temp == '�')
				sTemp += "é";
			if (temp == '�')
				sTemp += "í";
			if (temp == '�')
				sTemp += "ó";
			if (temp == '�')
				sTemp += "ú";
			if (temp == '�')
				sTemp += "´";
			if (temp == '�')
				sTemp += "º";
			if (sTemp == "")
				sTemp += temp;
			out += sTemp;
		}
		return out;
	}

	// ==================================================================
	public String eweDecode(String linea)
	// ==================================================================
	{
		String out = new String();
		for (int i = 0; i < linea.length(); i++) {
			if (linea.charAt(i) == '�') {
				i++;
				if (linea.charAt(i) == '�') {
					out += "�";
				} else if (linea.charAt(i) == '�') {
					out += "�";
				} else if (linea.charAt(i) == '�') {
					out += "�";
				} else if (linea.charAt(i) == '�') {
					out += "�";
				} else if (linea.charAt(i) == '�') {
					out += "�";
				} else if (linea.charAt(i) == '�') {
					out += "�";
				} else if (linea.charAt(i) == '�') {
					out += "�";
				}
			} else if (linea.charAt(i) == '�') {
				i++;
				if (linea.charAt(i) == '�') {
					out += "�";
				}
			} else if (linea.charAt(i) == '�') {
				i++;
				if (linea.charAt(i) == '�') {
					out += "�";
				} else if (linea.charAt(i) == '�') {
					out += "�";
				}
			} else {
				out += linea.charAt(i);
			}
		}
		return out;
	}
	
	// ==================================================================
	protected boolean isPeticionHttp(String datos)
	// ==================================================================
	{
		boolean out = false;
		if (datos.contains("GET") || datos.contains("http")
				|| datos.contains("://")) {

			// Devuelve una respuesta simulando un servidor web
			enviar("\n");
			enviar("HTTP/1.1 200 OK");
			enviar("Server: Apache/1.3.29 (Win32) PHP/4.3.4");
			enviar("Connection: close");
			enviar("Content-Type: text/html; charset=iso-8859-1");
			enviar("\n");
			enviar("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">"
					+ "<HTML><HEAD>" + "<TITLE>Pagina en construcci�n</TITLE>"
					+ "</HEAD><BODY>" + "<H1>Pagina en construcci�n</H1>"
					+ "<HR>" + "<p>Lamentamos las molestias.</p>"
					+ "</BODY></HTML>");
			out = true;
		}
		return out;
	}

	
	
}
