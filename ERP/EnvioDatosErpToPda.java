package ERP;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.NetCodec;

import system.SysData;
import data.Articulo;
import data.Cliente;


public class EnvioDatosErpToPda {

	/** Statement de la conexion a la base de datos ERP */
	protected Statement stErp;
	
	protected SysData sys;
	
	protected NetCodec nc;
	
	// ==================================================================
	public EnvioDatosErpToPda(SysData sys,NetCodec nc) throws SQLException
	// ==================================================================
	{
		this.nc = nc;
		this.sys = sys;
		Connection ct = sys.getConexionErp();
		stErp = ct.createStatement();
	}
	
	
	/** Envia a la pda los clientes ligados al comercial que tiene la pda */
	// ==================================================================
	public void enviarHistorico(String cod) throws SQLException
	// ==================================================================
	{
		//nc.enviar("0");
		
		int fin = 0;
		String query = "SELECT COUNT(*) " + 
			"FROM FCLIA" + SysData.getEmpresa() + " a, FCCLI" + SysData.getEmpresa() + " b " +
			"WHERE b.CREPRE=" + cod + " AND b.CCODCL=a.LCODCL";
		
		ResultSet rst = stErp.executeQuery(query);
		if (rst.next()) {
			fin = rst.getInt("COUNT(*)");
		}
		nc.enviar( ""+fin );
		// Cuando el agente no haya cambiado pasar solo los historicos nuevos
		
		/* NOTA : ver si es posible pasar históricos sólo del último año 
		 * Ej: fecha del sistema - 1 año 
		 * */
	
		if( fin > 0 ){
			query = "SELECT a.LCODCL cliente,a.LCODAR articulo,a.LPRECI precio_venta," +
				"a.LDTO dto1,a.LFECHA fecha,b.CREPRE " +
				"FROM FCLIA" + SysData.getEmpresa() + " a, FCCLI" + SysData.getEmpresa() + " b " +
				"WHERE b.CREPRE=" + cod + " AND b.CCODCL=a.LCODCL";
			
			rst = stErp.executeQuery(query);
			while (rst.next()) {
				nc.enviar(
						rst.getString("cliente")+"#"+
						rst.getString("articulo")+"#"+
						rst.getString("precio_venta")+"#"+
						rst.getString("dto1")+"#"+
						rst.getString("fecha")
				);
			}
			nc.enviar("EOF");
		}
		
		rst.close();
	}
	
	/** Envia a la pda los clientes ligados al comercial que tiene la pda */
	// ==================================================================
	public void enviarClientes(String cod) throws SQLException
	// ==================================================================
	{
		Cliente cli = new ClienteErp();
		int i = cli.getNumeroClientesAgente(stErp, cod);
		nc.enviar(String.valueOf(i));
		if (i != 0) {
			String query = cli.getQueryClientesPda(cod);
			ResultSet rst = stErp.executeQuery(query);
			while (rst.next()) {
				cli = new ClienteErp();
				cli.setClienteFromErp(rst);
				nc.enviar( cli.clienteEnvioPda() );
			}
			rst.close();
			nc.enviar("EOF");
		}
	}

	/** Envia las formas de pago */
	// ==================================================================
	public void enviarFormasPago() throws SQLException
	// ==================================================================
	{
		// Cuento el numero de entradas que tenemos
		int i = 0;
		String query = "SELECT COUNT(*) FROM fcfpg" + SysData.getEmpresa() + "";

		ResultSet rs;
		rs = stErp.executeQuery(query);
		rs.next();
		i = rs.getInt("COUNT(*)");

		nc.enviar(String.valueOf(i));
		if (i > 0) {
			query = "SELECT gcodfp codigo, gdesfp nombre,gnumvc vencimientos FROM fcfpg" + SysData.getEmpresa() + " ORDER BY gcodfp";
			rs = stErp.executeQuery(query);
			while (rs.next()) {
				nc.enviar(rs.getString("codigo") + "#"
						+ rs.getString("nombre") + "#"
						+ rs.getString("vencimientos"));
			}
			nc.enviar("EOF");
			rs.close();
		}
	}
	
	/**
	 * Metodo que envia los Tipos de Iva a la PDA
	 * @throws SQLException
	 */
	// ==================================================================
	public void enviarTiposIva() throws SQLException
	// ==================================================================
	{
		int i = 6;  // tenemos 5 tipos de iva, mas el iva 0
		nc.enviar(String.valueOf(i));
		if (i > 0) {
			String query = "SELECT giva1,giva2,giva3,giva4,giva5,grec1,grec2,grec3,grec4,grec5 FROM fcgen" + SysData.getEmpresa();
			ResultSet rs = stErp.executeQuery(query);
			nc.enviar("0#0#0#0");
			i = 1;
			if (rs.next()){
				while (i<=5) {
					nc.enviar(i + "#" 		
							+ i + "#" 		
							+ SysData.formatearDoubleToPda(rs.getDouble("giva"+i)) + "#"		
							+ SysData.formatearDoubleToPda(rs.getDouble("grec"+i)) );		
					i++;
				}
			}
			nc.enviar("EOF");
			rs.close();
		}
	}
	
	/**
	 * Metodo que envia los articulos activos (no bloqueados) a la PDA
	 * @throws SQLException
	 */
	// ==================================================================
	public void enviarArticulos() throws SQLException
	// ==================================================================
	{
		Articulo art = new ArticuloErp();
		int i = art.getNumeroArticulos(stErp);
		nc.enviar(String.valueOf(i));
		if (i > 0) {
			String query = art.getQueryArticulosPda();
			ResultSet rst = stErp.executeQuery(query);
			while (rst.next()) {
				art = new ArticuloErp();
				art.setArticulo(rst);
				if (art.tieneLineaDto(rst)){
					Statement stErp2 = sys.getConexionErp().createStatement();
					art.setDescuentos(rst,stErp2);
				}
				if(art.esActivo())
					nc.enviar(art.articuloEnvioPda() );
			}
			rst.close();
			nc.enviar("EOF");
		}
	}
	
	/** Metodo que envia los cobros pendientes de los clientes a la PDA
	 * @param cod
	 * @throws SQLException
	 */
// ==================================================================
	public void enviarCobrosPendientes(String cod) throws SQLException
// ==================================================================
	{
		/* Se mandan solo aquellas facturas tales que Importe - Importe cobrado > 0 
		 * (no se haya cobrado todo el importe)
		 * */
		
		
		//nc.enviar("0");
		int fin = 0;
		String query = "SELECT COUNT(*) " + 
			"FROM FCREC" + SysData.getEmpresa() + " a, FCCLI" + SysData.getEmpresa() + " b " +
			"WHERE b.CREPRE=" + cod + " AND b.CCODCL=a.RCODCL AND a.RIMP - a.RIMPCOBR > 0";
		ResultSet rst = stErp.executeQuery(query);
		if (rst.next()) {
			fin = rst.getInt("COUNT(*)");
		}
		nc.enviar( ""+fin );
		// Cuando el agente no haya cambiado pasar solo los historicos nuevos
		/* NOTA : ver si es posible pasar históricos sólo del último año 
		 * Ej: fecha del sistema - 1 año 
		 * */
		if( fin > 0 ){
			query = "SELECT a.RCODCL cliente,a.RFAC documento,a.RFECHA anyo," +
				"a.RFECHA fecha,a.RIMP importe,a.RIMPCOBR imp_cobrado,a.RFECVTO fec_vencimiento " +
				"FROM FCREC" + SysData.getEmpresa() + " a, FCCLI" + SysData.getEmpresa() + " b " +
				"WHERE b.CREPRE=" + cod + " AND b.CCODCL=a.RCODCL " +
						"AND a.RIMP - a.RIMPCOBR > 0 "; 
						//+ "AND a.RCOBRADO='N'";
			
			rst = stErp.executeQuery(query);
			while (rst.next()) {
				nc.enviar(rst.getString("cliente") + "#"
						+ rst.getString("documento") + "#"
						//+ rst.getString("anyo") + "#"
						//++ "S" + cod + "#" 
						+ rst.getString("importe") + "#"
						+ rst.getString("imp_cobrado") + "#"
						+ rst.getString("fecha") + "#"
						+ rst.getString("fec_vencimiento"));
			}
			nc.enviar("EOF");
			rst.close();
		}
	}
	
	/** Metodo que envia las Rutas de Venta a la PDA 
	 * @param cod
	 * @throws SQLException
	 */
	// ==================================================================
	public void enviarRutasVenta(String cod) throws SQLException
	// ==================================================================
	{
		nc.enviar("0");
		
		/*
		// Cuento el numero de entradas que tenemos
		int i = 0;
		String query = "SELECT COUNT(*) FROM RUTAS_VENTA_LIN a, RUTAS_VENTA b , CLIENTES c "
				+ "WHERE (a.VENDEDOR=b.VENDEDOR) AND (a.DIA=b.DIA) AND ( c.codigo = a.cliente ) " 
				+ "AND a.VENDEDOR = '"+cod+"'";

		ResultSet rs;
		rs = stErp.executeQuery(query);
		rs.next();
		i = rs.getInt("COUNT");

		nc.enviar(String.valueOf(i));
		if (i > 0) {
			query = "SELECT a.VENDEDOR, a.DIA, a.LINEA, b.RUTA, a.CLIENTE ,c.NOMBRE " +
					"FROM RUTAS_VENTA_LIN a, RUTAS_VENTA b , CLIENTES c "+
				 	"WHERE (a.VENDEDOR = b.VENDEDOR) " +
				 	"AND (a.DIA = b.DIA) " +
				 	"AND ( c.codigo = a.cliente ) " +
				 	"AND a.VENDEDOR = '"+cod+"' " +
				 	"ORDER BY b.ruta,a.dia,a.linea";
			
			rs = stErp.executeQuery(query);
			while (rs.next()) {
				nc.enviar(rs.getString("ruta") + "#"
						+ rs.getString("cliente") + "#"
						+ rs.getString("nombre") + "#"
						+ rs.getString("vendedor") + "#" 
						+ rs.getString("dia") + "#" 
						+ rs.getString("linea"));
			}
			nc.enviar("EOF");
			rs.close();
		}
		*/
	}

	/**
	 * Envia los datos generales que son :
	 * subfamilias,familias,tipos,actividades,rutas,secciones,tipos_incidencias,zonas
	 */
	// ==================================================================
	public void enviarDatosGenerales(String database) throws SQLException
	// ==================================================================
	{
		String codigo = "";
		String descripcion = "";
		if (database.equals("familias") || database.equals("zonas")){
			if (database.equals("familias")){
				database = "fcfam" + SysData.getEmpresa();
				codigo = "fcod";
				descripcion = "fdes";
			}else if (database.equals("zonas")){
				database = "fczon" + SysData.getEmpresa();
				codigo = "zcod";
				descripcion = "zdes";
			}
			
			
			int i = 0;
			// Cuento el numero de entradas que tenemos
			String query = "SELECT COUNT(*) FROM " + database;
			
			ResultSet rs;
			rs = stErp.executeQuery(query);
			if ( rs.next() )
				i = rs.getInt("COUNT(*)");
			
			nc.enviar(String.valueOf(i));
			if (i > 0) {
				query = "SELECT " + codigo + "," + descripcion + " FROM " + database
				+ " ORDER BY " + codigo;
				rs = stErp.executeQuery(query);
				while (rs.next()) {
					nc.enviar(rs.getString(codigo) + "#"
							+ rs.getString(descripcion));
				}
				nc.enviar("EOF");
				rs.close();
			}
			
		}else if (database.equals("tipos_incidencias")){
			
			nc.enviar("9");
			int i = 1;
			
			while (i<=9){
				String query = "SELECT gtipinc" + i + " descripcion FROM fcgen" + SysData.getEmpresa();
				ResultSet rs = stErp.executeQuery(query);
				if (rs.next()) {
					nc.enviar(i + "#"
							+ rs.getString("descripcion"));
				}
				rs.close();
				i++;
			}
			nc.enviar("EOF");
		}else{
			nc.enviar("0");
		}
	}
	
}
