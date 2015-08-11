package data;

import java.sql.Statement;

import log.LogWriter;

import system.SysData;

public class Evento {

	public int codigo;

	public String fechaHora;

	public String codigoAgente;
	
	public long numero;
	
	public int codigoCliente;

	// ==================================================================
	public Evento()
	// ==================================================================
	{
	}

	// ==================================================================
	public Evento(int cod, String codAg, long num, int codCli)
	// ==================================================================
	{
		codigo = cod;
		// fechaHora = new Date();
		codigoAgente = codAg;
		numero = num;
		codigoCliente = codCli;
	}

	// ==================================================================
	public boolean insertarEvento(Statement sPropio )
	// ==================================================================
	{
		boolean out = false;

		try {
			String query = "INSERT INTO cola_eventos ("
					+ "CODIGO,FECHA_HORA,EXTRAS,NUMERO,CLIENTE" + ") VALUES (";
			query += codigo + ",";
			query += "'now',";
			query += SysData.formatear(codigoAgente) + ",";
			query += SysData.formatear(numero) + ",";
			query += SysData.formatear(codigoCliente);
			query += ")";
			System.out.println(query);
			if (sPropio.executeUpdate(query) == 1) {
				out = true;
			} else {
				out = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

}
