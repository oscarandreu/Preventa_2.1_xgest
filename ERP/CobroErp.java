package ERP;

import java.sql.Statement;

import system.SysData;
import data.Cobro;

public class CobroErp extends Cobro{
	
	/**
	 * Metodo que inserta un Cobro en la base de datos ERP
	 */
//	==================================================================
	public boolean insertarCobroErp(Statement s,Statement sErp)
//	==================================================================
	{
		boolean out = false;
		
		return out;
	}
	
	/**
	 * Metodo que devuelve la consulta para accedes a las facturas con sus cobros en el ERP
	 */
//	==================================================================
	public String getQueryCobroFromErp(long numeroDocumento)
//	==================================================================
	{
		String query = "SELECT RFAC numero_documento,RREC porcion_cobro,RFECHA fecha,RFECVTO fechaVto," +
				"RCODCL codCliente,RIMP importe,RFP formaPago,RTIPOFP tipoFormaPago,RIMPCOBR importeCobrado " +
				"FROM fcrec" + SysData.getEmpresa() +
				" WHERE rfac='" + numeroDocumento + "'";
		
		return query;
	}

}
