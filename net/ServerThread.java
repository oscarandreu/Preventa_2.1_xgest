package net;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;

import log.LogWriter;
import system.Cargador;
import system.SysData;
import ERP.ClienteErp;
import ERP.CobroErp;
import ERP.EnvioDatosErpToPda;
import ERP.IncidenciaErp;
import ERP.PedidoErp;
import Gui.GuiAltaPda;
import data.CabeceraPedido;
import data.Cliente;
import data.Cobro;
import data.Evento;
import data.Incidencia;
import data.LineaPedido;

public class ServerThread extends Thread {

	protected NetCodec nc;

	protected SysData sys;
	/**Conexion a nuestra base de datos*/
	protected Connection c;
	/**Conexion a la base de datos de tecnopolis*/
	protected Connection cErp;
	/** Statement de nuestra conexion*/
	protected Statement s;
	/** Statement de la conexion al ERP*/
	protected Statement sErp;	
	
	protected String op;

	protected String cod_pda;

	protected String cod_user;

	protected String pass_user;

	protected String database;

	protected Cargador cargador;

	// contador de eventos recibidos
	protected int contadorEventosGlobal = 0;

	
// ==================================================================
	public ServerThread(Socket s, Cargador cargador) throws java.io.IOException
// ==================================================================
	{
		this.cargador = cargador;
		this.sys = cargador.sys;
		nc = new NetCodec( s );
	}

	
// ==================================================================
	public void run()
// ==================================================================
	{
		String datos;
		
		try {
			datos = nc.recibir();
			// Si los datos son una peticion web
			if (!nc.isPeticionHttp(datos)) {
				// leemos los datos que nos proporciona le clientey generamos
				// las conexiones a la bbdd
				StringTokenizer stok = new StringTokenizer(datos, "#");
				if (stok.countTokens() == 4) {
					op = stok.nextToken();
					cod_pda = stok.nextToken();
					cod_user = stok.nextToken();
					pass_user = stok.nextToken();// opcion tres sera nombre de tabla
					int operacion = Integer.parseInt(op);
					c = sys.getConexionPropia();
					s = c.createStatement();
					cErp = sys.getConexionErp();
					sErp = cErp.createStatement();
					
					switch (operacion) {
					case 1:
						altaPDA();
						break;
					case 4:
						loginPDA();
						break;
					case 2:
						recibirDatosPda();
						break;
					case 3:
						envioDatosPda();
						break;
					}
				}
				if (s != null)
					s.close();
				if (c != null)
					c.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
		} catch (NullPointerException ioe) {
			ioe.printStackTrace();
			ioe.printStackTrace();
			new LogWriter(ioe.getStackTrace());
		} catch (SQLException ioe) {
			ioe.printStackTrace();
			ioe.printStackTrace();
			new LogWriter(ioe.getStackTrace());
		}finally{
		// Finalizar conexion
		nc.cerrarConexion();
		}
	}

	

	/** Metodo que comprueba que concuerden los datos agente PDA */
	// ==================================================================
	protected boolean comprobarCredenciales() throws SQLException
	// ==================================================================
	{
		boolean out = false;
		String query = "SELECT id,codigo_agente FROM PPC WHERE " + "id='"
				+ cod_pda + "'AND " + "codigo_agente='" + cod_user + "'";

		// Se comprueban las credenciales
		ResultSet rs = s.executeQuery(query);
		if (rs.next()) {
			if (rs.getString("id").equals(cod_pda)
					&& rs.getString("codigo_agente").equals(cod_user)) {
				out = true;
				nc.enviar("ACK");
			}
		} else {
			nc.enviar("NACK");
		}
		return out;
	}

	/**
	 * Metodo que recibe de la PDA y por este orden: Cabeceras de pedidos lineas
	 * de pedido nuevos clientes incidencias
	 */
	// ==================================================================
	@SuppressWarnings("unchecked")
	protected void recibirDatosPda() throws SQLException, IOException
	// ==================================================================
	{
		if (comprobarCredenciales()) {

			boolean error = false;
			String datos = "";
			int contadorEventos = 0;
			

			// ############## Recepcion de los clientes ##############
			
				Cliente cli = new ClienteErp();
				int antiguoCodigoCliente = 0;
				Hashtable nuevosCodigosClientes = new Hashtable();
				
				while ( !"EOF".equals( datos = nc.recibir() ) ) {
					try{
						antiguoCodigoCliente = cli.setDatosPersonalesClienteFromPda(s,
								nc.eweDecode(datos), sys);
						if (antiguoCodigoCliente != 0) {
							nc.enviar("ACK");
							datos = nc.recibir();
							if ( cli.setDatosBancariosClienteFromPda( datos )
									&& cli.insertarCliente( s ) ) {
								nc.enviar("" + cli.getCodigo());
								nuevosCodigosClientes.put(
										"" + antiguoCodigoCliente, "" +
										cli.getCodigo() );
							}
						} else {
							error = true;
						}
					}catch (Exception ex ){
						error = true;
						new LogWriter( ex.getStackTrace() );
					}
					if (error) {
						nc.enviar("NACK");
						error = false;
					} else {
						// nuevo cliente recibido
						Evento even = new Evento(1, cod_user,cli.getCodigo(),cli.getCodigo());
						even.insertarEvento( s );
						contadorEventos++;
					}
				}
				// Al recibir un cliente marcarlo como potencial
				if (contadorEventos > 0) {
					// creamos un evento de tipo Alta Nuevo Cliente
					//Evento even = new Evento(1, cod_user,0,cli.getCodigo());
					//even.insertarEvento(s, sys);
					contadorEventosGlobal = contadorEventosGlobal + contadorEventos;
				}
				contadorEventos = 0;

			// ############## Recepcion de los pedidos ##############

			CabeceraPedido cab = new PedidoErp();
			LineaPedido lin = new LineaPedido();
			// flag que indica si se esta recibiendo una cabecera(true) o una
			// linea(false)
			while (!"EOF".equals(datos = nc.recibir())) {
				try{
					// recibo la cabecera
					error = true;
					if (cab.setCabeceraFromPda( nc.eweDecode(datos) ) ) {
						// Si el pedido es de un nuevo cliente controlo el nuevo
						// codigo del cliente
						if (nuevosCodigosClientes.containsKey("" + cab.codCliente))
							cab.codCliente = SysData.setInt(nuevosCodigosClientes
									.get("" + cab.codCliente));
						if (cab.insertarCabeceraPedido(s)) {
							error = false;
						}
					}
				}catch(Exception ex){
					error = true;
					new LogWriter( ex.getStackTrace() );
				}
				
				if (error) {
					nc.enviar("NACK");
				} else {
					nc.enviar("ACK");
				}
				// Si envio el NACK la PDA es la que se encarga de enviar un EOL
				// por lo que no envia las lineas
				// Recibo las lineas del pedido
				while (!"EOL".equals(datos = nc.recibir())) {
					try{
						if (lin.setLineaFromPda(nc.eweDecode(datos), cab.serie )
								&& lin.insertarLineaPedido(s)) {
							nc.enviar("ACK");
						} else {
							error = true;
							nc.enviar("NACK");
						}
					}catch (Exception ex){
						error = true;
						new LogWriter( ex.getStackTrace() );
					}
				}
				
				if (error) {
					cab.eliminarCabeceraPedido(s);
					error = false;
				} else {
					// nuevo pedido recibido
					Evento even = new Evento(2, cod_user,cab.getNumeroCabecera(),cab.getCodCliente());
					even.insertarEvento( s );
					contadorEventos++;
				}
			}

			if (contadorEventos > 0) {
				// creamos un evento de tipo Nuevo Pedido
				//Evento even = new Evento(2, cod_user,cab.getNumeroCabecera(),cab.getCodCliente());
				//even.insertarEvento(s, sys);
				contadorEventosGlobal = contadorEventosGlobal + contadorEventos;
			}
			contadorEventos = 0;

			// ############## Recepcion de las incidencias ##############
			
			Incidencia inc = new IncidenciaErp();
			while (!"EOF".equals(datos = nc.recibir())) {
				try{
					error = true;
					// recibo la incidencia
					if (inc.setIncidenciaFromPda( nc.eweDecode(datos) ) ) {
						// Si la incidencia es de un nuevo cliente controlo el nuevo
						// codigo del cliente
						if (nuevosCodigosClientes.containsKey("" + inc.codCliente))
							inc.codCliente = SysData.setInt(nuevosCodigosClientes.get("" + inc.codCliente));
						if (inc.insertarIncidencia(s)) {
							error = false;
						}
					}
				}catch (Exception ex){
					error = true;
					new LogWriter( ex.getStackTrace() );
				}
				if (error) {
					nc.enviar("NACK");
				} else {
					nc.enviar("ACK");
					// nueva incidencia recibida
					Evento even = new Evento(3, cod_user,inc.getNumero(),inc.getCodCliente());
					even.insertarEvento( s );
					contadorEventos++;
				}
			}
			

			if (contadorEventos > 0) {
				// creamos un evento de tipo Nueva Incidencia
				//Evento even = new Evento(3, cod_user,inc.getNumero(),inc.getCodCliente());
				//even.insertarEvento(s, sys);
				contadorEventosGlobal = contadorEventosGlobal + contadorEventos;
			}

			// Fin recepcion de las incidencias
			
			// ############## Recepcion de las cobros ##############
			
			Cobro cob = new CobroErp();
			while (!"EOF".equals(datos = nc.recibir())) {
				try{
					error = true;
					// recibo el cobro
					if (cob.setCobroFromPda( nc.eweDecode(datos), sys, cod_user ) ) {
						/*// Si el cobro es de un nuevo cliente controlo el nuevo
						// codigo del cliente
						if (nuevosCodigosClientes.containsKey("" + cob.codCliente))
							cob.codCliente = SysData.setInt(nuevosCodigosClientes.get("" + cob.codCliente));
						*/
						if (cob.insertarCobro(s)) {
							error = false;
						}
					}
				}catch (Exception ex){
					error = true;
					new LogWriter( ex.getStackTrace() );
				}
				if (error) {
					nc.enviar("NACK");
				} else {
					nc.enviar("ACK");
					// nuevo cobro recibido
					Evento even = new Evento(4, cod_user,cob.getNumeroDocumento(),cob.getCodigoCliente());
					even.insertarEvento( s );
					contadorEventos++;
				}
			}
			

			if (contadorEventos > 0) {
				contadorEventosGlobal = contadorEventosGlobal + contadorEventos;
			}

			
			cargador.actualizarVentanas();

			// se refresca el mensaje de eventos pendientes
			try{
				String query = "SELECT count(*) FROM cola_eventos";
				ResultSet rs = s.executeQuery( query );
				while (rs.next()){
					int numEve = rs.getInt("count");
					System.out.println("Eventos en cola: " + numEve);
					cargador.setContadorEventosCargador( numEve );
				}
			}
			catch(SQLException ex){
				ex.printStackTrace(); 
				new LogWriter( ex.getStackTrace() );
			}
			
		}
	}

	// ==================================================================
	protected void envioDatosPda() throws SQLException, IOException
	// ==================================================================
	{
		if (comprobarCredenciales()) {

			while (!"ACK".equals(database = nc.recibir())) {
				EnvioDatosErpToPda env = new EnvioDatosErpToPda( sys , nc);
				
				if ("clientes".equals(database)) {
					env.enviarClientes(cod_user);
				

				} else if ("subfamilias".equals(database)
						|| "familias".equals(database)
						|| "tipos".equals(database)
						|| "actividades".equals(database)
						|| "rutas".equals(database)
						|| "secciones".equals(database)
						|| "tipos_incidencias".equals(database)
						|| "zonas".equals(database)) {
					env.enviarDatosGenerales(database);
					

				} else if ("formas_pago".equals(database)) {
					env.enviarFormasPago();

				} else if ("tipos_iva".equals(database)) {
					env.enviarTiposIva();

				} else if ("rutas_venta".equals(database)) {
					env.enviarRutasVenta( cod_user );
					
				} else if ("cobros_pdtes".equals(database)) {
						env.enviarCobrosPendientes( cod_user );

				//Peticion incorrecta mando EOF para no colgar la aplicacion	
				} else if ("articulos".equals(database)) {
					env.enviarArticulos();

				} else if ("historico_ventas".equals(database)) {
					env.enviarHistorico( cod_user );

				}else {
					nc.enviar("EOF");
				}
			}
		}
	}
	
	// ==================================================================
	protected void loginPDA() throws SQLException
	// ==================================================================
	{
		String query = "SELECT codigo, passw, nombre, serie FROM agentes "
				+ "WHERE codigo='" + cod_user + "' " + "AND passw='"
				+ pass_user + "' "; // +"AND fecha_baja = 'null'" ;
		System.out.println(query);
		ResultSet rs = s.executeQuery(query);
		if (rs.next()) {
			// esta comprobación parece innecesaria pero aun asi la hago
			// (sqlinyection)
			if (rs.getString("codigo").equals(cod_user)
					&& rs.getString("passw").equals(pass_user)) {
				String nombreAgente = rs.getString("nombre");
				String serie = rs.getString("serie");
				// enlazo al agente con la PDA
				query = "SELECT id FROM PPC where id='" + cod_pda + "'";
				rs = s.executeQuery(query);
				if (rs.next()) {
					rs.close();
					// Consigo el siguiente numero de cabecera para este comercial
					CabeceraPedido cab = new PedidoErp();
					long nextNumeroPedido = cab.siguienteNumeroPedido(s,	cod_user);
					/*Si el metodo anterior nos devuelve 0 significa que no tenemos ningun pedido en 
					 * nuestra bbdd por lo que acudimos a la del ERP*/
					if ( nextNumeroPedido == 0 ){
						nextNumeroPedido = PedidoErp.nextNumeroPedidoErp(sErp,serie);
					}

					query = "UPDATE PPC SET codigo_agente=null  WHERE codigo_agente='"
							+ cod_user + "'";
					s.executeUpdate(query);

					query = "UPDATE PPC set codigo_agente=" + cod_user
							+ " WHERE id=" + "'" + cod_pda + "';";

					int nextNumeroCliente = Cliente.siguienteNumeroCliente(s,sErp);
					if (s.executeUpdate(query) == 1) {
						nc.enviar(this.cod_pda + "#" + nombreAgente + "#" + serie
								+ "#" + nextNumeroPedido + "#"
								+ nextNumeroCliente);
					} else {
						nc.enviar("000000#xx");
					}
				} else {
					// PDA no esta dada de alta
					nc.enviar("222222#xx");
				}
			} else {
				System.out.println("no se coinciden user y pass");
				nc.enviar("111111#xx");
			}
		} else {
			System.out.println("no se encontro user y pass");
			nc.enviar("111111#xx");
		}
	}

	// ==================================================================
	protected void altaPDA() throws SQLException
	// ==================================================================
	/**
	 * recibe la peticion de alta y si todavia se pueden realizar mas altas lo
	 * hace enviando un codigo de seis digitos aleatorio, sino envia 000000
	 */
	{
		String query;
		if (op.equals("01") && cod_user.equals("000000")
				&& pass_user.equals("000000") && cod_pda.equals("000000")) {
			query = "SELECT id FROM PPC";
			int count = 0;

			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				count++;
			}
			rs.close();
			if (count >= Integer.parseInt(cargador.get(11))) {
				nc.enviar("111111");
			} else {
				cod_pda = asignarIdUnicoPda();
				nc.enviar(cod_pda + "#" + cargador.a3 + "#" + cargador.a4);
				GuiAltaPda alta = new GuiAltaPda(cargador, cod_pda);
				
				cargador.gui.jDesktopPane.add(alta);
				alta.setVisible(true);
				try {
					alta.setSelected(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
					new LogWriter( e1.getStackTrace() );
				}

			}
		}
	}

	// ==================================================================
	public String asignarIdUnicoPda() throws SQLException
	// ==================================================================
	{
		boolean out = true;
		ResultSet rs = null;
		String cod_pda = null;
		while (out) {
			cod_pda = getRandomCad();
			rs = s
					.executeQuery("SELECT id FROM PPC WHERE id='" + cod_pda
							+ "'");
			if (!rs.next()) {
				out = false;
			}
		}
		rs.close();
		return cod_pda;
	}

	// ==================================================================
	protected String getRandomCad()
	// ==================================================================
	{
		Random rand = new Random();
		String out = Integer.toHexString(rand.nextInt());
		out = out.substring(0, 6);
		return out;
	}



	/**
	 * Metodo que recibe de la PDA una serie de codigos de articulos,
	 * para que el programa base devuelva sus stocks
	 */
	// =============================================================================================
	protected String recibirArticulosParaStock(String in) throws SQLException
	// =============================================================================================
	{
		Iterator it = SysData.tokenizar(in);
		Statement st = c.createStatement();
		String stock = new String();
		while (it.hasNext()){
			String codigo = (String) it.next();
			try{
				ResultSet rs = st.executeQuery("SELECT stock FROM articulos WHERE codigo='" + codigo + "'");
				if (rs.next()){
					// crear String con stock de articulos
					stock += rs.getString("stock") + "#";
				}
			}catch(Exception ex){
				ex.printStackTrace();
				new LogWriter( ex.getStackTrace() );
			}
		}
		stock += "EOF";
		System.out.println("Stock:" + stock);
		return stock;
	}
	
}