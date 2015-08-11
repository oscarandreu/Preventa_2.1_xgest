package ERP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;
import system.SysData;
import data.Agente;

public class AgenteErp extends Agente{

	
//	 ==================================================================	
	public AgenteErp()
//	 ==================================================================
	{
		super();
	}
	
	/**
	 * Setea el agente con los datos que vienen del ERP*/
	// ==================================================================
	public void setAgente(ResultSet rs)
	// ==================================================================
	{
		try {
			codigoAgente = rs.getInt("CODIGO");
			//serieAgente = "S" + codigoAgente;
			passwAgente = "000000";
			// serieAgente = rs.getString("SERIE");
			nombreAgente = rs.getString("NOMBRE");
			// passwAgente = rs.getString("PASSW");

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
	// ==================================================================
	public void setSerie(Statement sErp )
	// ==================================================================
	{
		try{
			ResultSet rErp = sErp.executeQuery("SELECT MAX(scod) FROM fcser" + SysData.getEmpresa());
			if (rErp.next()){
				int codigo = rErp.getInt("MAX(scod)");
				codigo++;
				String query = "INSERT INTO fcser" + SysData.getEmpresa() + " ("
				+ "scod,sdes,spridoc,sultdoc"
				+ ") VALUES ('" + codigo + "','Agente " + nombreAgente + " PDA',1,70000)";
				System.out.println(query);
				sErp.executeUpdate("INSERT INTO fcser" + SysData.getEmpresa() + " ("
						+ "scod,sdes,spridoc,sultdoc"
						+ ") VALUES ('" + codigo + "','Agente " + nombreAgente + " PDA',1,70000)");
				this.serieAgente = new Integer(codigo).toString();
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
		}
	}
	
}
