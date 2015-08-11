package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import log.LogWriter;
import system.Cargador;
import system.SysData;
import ERP.ClienteErp;
import ERP.DatosGeneralesErp;
import Gui.system.InfoPane;
import Gui.system.PopupMenu;
import Gui.system.QueryPane;
import bbdd.InternalFrameTable;
import data.Cliente;


public class GuiClientes extends GuiInternalFrame{
	
//	Declaracion de Botones
	protected JButton eliminar = null;
	protected JButton modificar = null;
	protected JButton cerrar = null;
	
//	Declaracion de jpanel 
	private JPanel panel = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	protected JPanel jPanel1 = null;
	protected JPanel jPanel2 = null;  //  @jve:decl-index=0:visual-constraint="332,38"
	
//	Declaracion jScrollPane	
	protected JScrollPane jScrollPane = null;
	protected JSplitPane jSplitPane = null;
	
	
//	Declaracion campos pestaña 1
	protected JTabbedPane jTabbedPane = null;
	
	protected JLabel jLcodigo = null;
	protected JLabel jLfechalta = null;
	protected JLabel jLcodigo2 = null;
	protected JLabel jLnombre = null;
	protected JLabel jLrazon = null;
	protected JLabel jLdireccion1 = null;
	protected JLabel jLdireccion2 = null;
	protected JLabel jLpoblacion = null;
	protected JLabel jLprovincia = null;
	protected JLabel jLcontacto = null;
	protected JLabel jLemail = null;
	protected JLabel jLweb = null;
	protected JLabel jLnif = null;
	protected JLabel jLtelefono1 = null;
	protected JLabel jLtelefono2 = null;
	protected JLabel jLpais = null;
	protected JLabel jLcodigopostal = null;
	protected JLabel jLfax = null;
	protected JLabel jLpotencial = null;
	
	protected JTextField jTcodigo = null;
	protected JTextField jTfechaalta = null;
	protected JTextField jTcodigo2 = null;
	protected JTextField jTnombre = null;
	protected JTextField jTrazon = null;
	protected JTextField jTdireccion = null;
	protected JTextField jTdireccion2 = null;
	protected JTextField jTpoblacion = null;
	protected JTextField jTprovincia = null;
	protected JTextField jTcontacto = null;
	protected JTextField jTemail = null;
	protected JTextField jTweb = null;
	protected JTextField jTnif = null;
	protected JTextField jTtelefono1 = null;
	protected JTextField jTtelefono2 = null;
	protected JTextField jTpais = null;
	protected JTextField jTcodigopostal = null;
	protected JTextField jTfax = null;
	protected JTextField jTpotencial = null;
	
	private JButton validar = null;

	
	//	Declaracion campos pestaña 2
	protected JPanel comercial = null;
	
	protected JLabel jLzona = null;
	protected JLabel jLrutas = null;
	protected JLabel jLtipo = null;
	protected JLabel jLactividad = null;
	protected JLabel jLagente = null;
	protected JLabel jLformapago = null;
	protected JLabel jLtipoiva = null;
	protected JLabel jLdiavisita = null;
	protected JLabel jLirpf = null;
	
	protected JTextField jTzona = null;
	protected JTextField jTruta = null;
	protected JTextField jTtipo = null;
	protected JTextField jTactividad = null;
	protected JTextField jTagente = null;
	protected JTextField jTformapago = null;
	protected JTextField jTtipoiva = null;
	protected JTextField jTdiavisita = null;
	protected JTextField jTirpf = null;
	
	
//	Declaracion campos pestaña 3
	protected JPanel banco = null;
	
	protected JLabel jLdivisa = null;
	protected JLabel jLtarifa = null;
	protected JLabel jLdiapago = null;
	protected JLabel jLmesnopago = null;
	protected JLabel jLdto1 = null;
	protected JLabel jLdto2 = null;
	protected JLabel jLdescuentopp = null;
	protected JLabel jLincremento = null;
	protected JLabel jLportes = null;
	protected JLabel jLriesgoconcedido = null;
	protected JLabel jLriesgodisponible = null;
	protected JLabel jLriesgosolicita = null;
	protected JLabel jLbanco = null;
	protected JLabel jLentidad = null;
	protected JLabel jLsucursal = null;
	protected JLabel jLcb = null;
	protected JLabel jLcc = null;
	
	protected JTextField jTdivisa = null;
	protected JTextField jTtarifa = null;
	protected JTextField jTdiapago1 = null;
	protected JTextField jTdiapago2 = null;
	protected JTextField jTdiapago3 = null;
	protected JTextField jTmesnopago = null;
	protected JTextField jTdescuento1 = null;
	protected JTextField jTdescuento2 = null;
	protected JTextField jTdescuentopp = null;
	protected JTextField jTincremento = null;
	protected JTextField jTportes = null;
	protected JTextField jTriesgoconcedido = null;
	protected JTextField jTriesgodisponible = null;
	protected JTextField jTbanco = null;
	protected JTextField jTentidad = null;
	protected JTextField jTsucursal = null;
	protected JTextField jTcb = null;
	protected JTextField jTcc = null;
	
//	Declaracion campos pestaña 4
	protected JPanel aviso = null;
	
	protected JLabel jLobservaciones = null;
	protected JLabel jLaviso = null;
	
	protected JTextArea jTobservaciones = null;
	protected JTextArea jTaviso = null;
	
//	Contador
	protected JLabel jLabel = null;
	
	protected boolean isEditable = false;

//  Base de datos
	private String condicionWhere;
	protected String query;
	private JButton jButtonZona = null;
	private JButton jButtonRuta = null;
	private JButton jButtonTipo = null;
	private JButton jButtonActividades = null;
	private JButton jButtonFormaPago = null;
	private JButton jButtonTipoIva = null;
	private JLabel jLriesgoSolicita = null;
	private JTextField jTriesgosolicita = null;
	
	
//	==================================================================
	public GuiClientes( Cargador cargador, boolean isEditable) 
//	==================================================================
	{
		super( cargador );
		this.isEditable = isEditable;
		icono = "tab_comparativa.gif";
		toolTip = "Muestra los clientes importados";
		titulo = "Clientes";
	}
	
//	==================================================================
	protected JPanel getPanel() 
//	==================================================================
	{
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.setSize(new java.awt.Dimension(843,495));
			panel.add(getJSplitPane(), null);
			panel.add(getCerrar(), null);
			if( isEditable ){
				panel.add(getEliminar(), null);
				panel.add(getModificar(), null);
				panel.add(getValidar(), null);
			}
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(16,455,248,29));
			jLabel.setText("JLabel");
			panel.add(jLabel, null);
			filasImportadas = jLabel;
			if (isEditable)
				filasImportadas.setText("Numero de Nuevos Clientes : "+model.numeroFilas);
			else
				filasImportadas.setText("Numero de Clientes Importados : "+model.numeroFilas);
		}
		frame.setBounds(new java.awt.Rectangle(50,50,830,525));
		return panel;
	}
	
//	===========================================================================================
	protected JScrollPane getJScrollPane() 
//	===========================================================================================
	{
		model = new InternalFrameTable( this){
			public boolean isCellEditable(int row, int column){
				return isEditable;
			}
		};
		//Configuracion de la tabla
		model.setColumnas( new String[]{"codigo","nombre"} );
		model.setColumnasFormateadas(  new String[]{"Código","Nombre"} );
		model.setAnchoColumnas( new int[]{80,255} );
		model.setDatabase("dpersonales");
		if( isEditable ){
			model.setWhere("potencial='S' OR potencial='V'");
			model.setVerificado( new String[]{"potencial","potencial"} );
		}else{
			model.setWhere("potencial='N' AND fecha_baja IS NULL");
		}
		model.setOrder("codigo");
		
		jScrollPane = new JScrollPane( model.getTabla() );
		
		jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane.setBounds(new java.awt.Rectangle(0,0,279,390));
		return jScrollPane;
	}
//	==============================================================================================
	protected JButton getEliminar()
//	==============================================================================================
	{
		if (eliminar == null) {
			eliminar = new JButton();
			eliminar.setText("Eliminar");
			eliminar.setLocation(new java.awt.Point(370,455));
			eliminar.setSize(new java.awt.Dimension(100,25));
			eliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(model.tabla.getSelectedRow()!=-1){
						//int fila = model.tabla.getSelectedRow();
						int n = new QueryPane().consultar( "Desea eliminar (S/N)" );
						if ( n == 0 ){
							try {
								String query = "DELETE FROM dpersonales WHERE ";
								query += condicionWhere;
								System.out.println( query );
								Statement sta;
								sta = conect.createStatement();
								sta.executeUpdate( query );
							} catch (SQLException e1) {
								e1.printStackTrace();
								new LogWriter( e1.getStackTrace() );
								new InfoPane("ERROR","Ha ocurrido un ERROR al eliminar un Cliente");
							}
							cargador.actualizarVentanas();
						}
					}
				}
			});
		}
		return eliminar;
	}
	
//	==============================================================================================
	protected JButton getModificar() 
//	==============================================================================================
	{
		if (modificar == null) {
			modificar = new JButton();
			modificar.setText("Modificar");
			modificar.setLocation(new java.awt.Point(490,455));
			modificar.setSize(new java.awt.Dimension(100,25));
			modificar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(model.tabla.getSelectedRow()!=-1){
						int fila = model.tabla.getSelectedRow();
						int n = new QueryPane().consultar( "Desea modificar (S/N)" );
						if ( n == 0 ){
							String query = "UPDATE dpersonales SET "; //, dbancarios b SET ";
							query += "fecha_alta='" + jTfechaalta.getText() + "',";
							query += "codigo2='" + jTcodigo2.getText() + "',";
							query += "nombre='" + jTnombre.getText() + "',";
							query += "razon='" + jTrazon.getText() + "',";
							query += "direccion1='" + jTdireccion.getText() + "',";
							query += "direccion2='" + jTdireccion2.getText() + "',";
							query += "poblacion='" + jTpoblacion.getText() + "',";
							query += "provincia='" + jTprovincia.getText() + "',";
							query += "contacto='" + jTcontacto.getText() + "',";
							query += "e_mail='" + jTemail.getText() + "',";
							query += "web='" + jTweb.getText() + "',";
							query += "nif='" + jTnif.getText() + "',";
							query += "telefono1='" + jTtelefono1.getText() + "',";
							query += "telefono2='" + jTtelefono2.getText() + "',";
							query += "cod_pais='" + jTpais.getText() + "',";
							query += "cod_postal='" + jTcodigopostal.getText() + "',";
							query += "fax='" + jTfax.getText() + "',";
							query += "potencial='" + jTpotencial.getText() + "',";
							
							query += "zona='" + cortarDato( jTzona.getText() ) + "',";
							
							query += "ruta='" + cortarDato( jTruta.getText() ) + "',";
							
							query += "tipo='" + cortarDato( jTtipo.getText() ) + "',";
							
							query += "actividad='" + cortarDato( jTactividad.getText() ) + "',";
							
							query += "agente='" + calcularAgente(jTagente.getText()) + "',";
							
							query += "forma_pago='" + cortarDato( jTformapago.getText() ) + "',";
							
							if ("".equals( cortarDato(jTtipoiva.getText()) )){
								query += "iva_fijo=null,";
							}else{
								query += "iva_fijo='" + cortarDato( jTtipoiva.getText() ) + "',";
							}
						
							query += "irpf='" + jTirpf.getText() + "'";
							
							query += " WHERE codigo='"+jTcodigo.getText()+"'";
							
							
							String query2 = "UPDATE dbancarios SET "; 
							query2 += "divisa='" + jTdivisa.getText() + "',";
							query2 += "tarifa='" + jTtarifa.getText() + "',";
							query2 += "dia_pago1='" + jTdiapago1.getText() + "',";
							query2 += "dia_pago2='" + jTdiapago2.getText() + "',";
							query2 += "dia_pago3='" + jTdiapago3.getText() + "',";
							query2 += "mes_no_pago='" + jTmesnopago.getText() + "',";
							query2 += "dto_1='" + jTdescuento1.getText() + "',";
							query2 += "dto_2='" + jTdescuento2.getText() + "',";
							query2 += "dto_pp='" + jTdescuentopp.getText() + "',";
							query2 += "incremento='" + jTincremento.getText() + "',";
							query2 += "portes='" + jTportes.getText() + "',";
							query2 += "riesgo_conced='" + jTriesgoconcedido.getText() + "',";
							query2 += "riesgo_disp='" + jTriesgodisponible.getText() + "',";
							query2 += "banco='" + jTbanco.getText() + "',";
							query2 += "entidad='" + jTentidad.getText() + "',";
							query2 += "sucursal='" + jTsucursal.getText() + "',";
							query2 += "cb='" + jTcb.getText() + "',";
							query2 += "cc='" + jTcc.getText() + "',";
							query2 += "observaciones='" + jTobservaciones.getText() + "',";
							query2 += "aviso='" + jTaviso.getText() + "'";
						
																			
							query2 += " WHERE codigo='"+jTcodigo.getText()+"'";
							
							System.out.println( query);
							System.out.println( query2 );
							Statement sta;
							try {
								sta = conect.createStatement();
								sta.executeUpdate( query );
								sta.executeUpdate( query2 );
							} catch (SQLException e1) {
								e1.printStackTrace();
								new LogWriter( e1.getStackTrace() );
								new InfoPane("ERROR","Ha ocurrido un ERROR al Modificar un Cliente");
							}
						}
					}
				}
			});
		}
		return modificar;
	}
//	==============================================================================================	
	private JButton getCerrar() 
//  ==============================================================================================
	{
		if (cerrar == null) {
			cerrar = new JButton();
			cerrar.setText("Cerrar");
			cerrar.setLocation(new java.awt.Point(605,455));
			cerrar.setSize(new java.awt.Dimension(100,25));
			cerrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cerrar();
				}
			});
		}
		return cerrar;
	}

	/**
	 * This method initializes validar	
	 * 	
	 * @return javax.swing.JButton	
	 */
//	===========================================================================================	
	private JButton getValidar() 
//	===========================================================================================
	{
		if (validar == null) {
			validar = new JButton();
			validar.setText("Validar");
			validar.setBounds(new java.awt.Rectangle(715,455,100,25));
			validar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					if (model.tabla.getSelectedRow() != -1){
						try{
							int n = new QueryPane().consultar( "Desea validar (S/N)" );
							if ( n == 0) {
								// creamos las dos statements de las dos conexiones necesarios
								Statement s = conect.createStatement();
								Statement st = sys.getConexionErp().createStatement();
								Cliente cliErp = new ClienteErp();
								cliErp.insertarClienteERP(st,s,jTcodigo.getText());
								st.close();
								s.close();
							}
						}catch(Exception ex) {
							ex.printStackTrace();
							new LogWriter( ex.getStackTrace() );
							new InfoPane("ERROR","Ha ocurrido un ERROR al validar un Cliente");
						}
						actualizar();
					}

				}
			});
		}
		return validar;
	}
	
//	===========================================================================================
	public void mostrarDatos(String where )
//	===========================================================================================
	{
		Statement sta;
		Statement stErp;
		condicionWhere = where;
				
		try {
			sta = this.conect.createStatement();
			stErp = this.sys.getConexionErp().createStatement();
			ResultSet resul = sta.executeQuery( "SELECT * FROM dpersonales WHERE "+ where +
					" AND fecha_baja IS NULL");
			
			if( resul.next() ){
				this.jTcodigo.setText(resul.getString("codigo"));
				this.jTfechaalta.setText(resul.getString("FECHA_ALTA"));
				this.jTcodigo2.setText(resul.getString("codigo2"));
				this.jTnombre.setText(resul.getString("nombre"));
				this.jTrazon.setText(resul.getString("razon"));
				this.jTdireccion.setText(resul.getString("direccion1"));
				this.jTdireccion2.setText(resul.getString("direccion2"));
				this.jTpoblacion.setText(resul.getString("poblacion"));
				this.jTprovincia.setText(resul.getString("provincia"));
				this.jTdireccion2.setText(resul.getString("direccion2"));
				this.jTcontacto.setText(resul.getString("contacto"));
				this.jTemail.setText(resul.getString("e_mail"));
				this.jTweb.setText(resul.getString("web"));
				this.jTnif.setText(resul.getString("nif"));
				this.jTpais.setText(resul.getString("cod_pais"));
				this.jTtelefono1.setText(resul.getString("telefono1"));
				this.jTtelefono2.setText(resul.getString("telefono2"));
				this.jTcodigopostal.setText(resul.getString("cod_postal"));
				this.jTfax.setText(resul.getString("fax"));
				this.jTpotencial.setText(resul.getString("potencial"));
				
				String codigoZona = resul.getString("zona");
				String codigoRuta = resul.getString("ruta");
				String codigoTipo = resul.getString("tipo");
				String codigoActividad = resul.getString("actividad");
				String codigoFormaPago = resul.getString("forma_pago");
				String codigoIva = resul.getString("iva_fijo");
				this.jTdiavisita.setText(resul.getString("DIA_VISITA"));
				
				if (isEditable){
//					activamos o desactivamos el botón de validar y el de modificar dependiendo del valor de su campo
					if ( "S".equals( model.getValorVectorVerificados( model.tabla.getSelectedRow() ) ) ){
						this.validar.setEnabled( true );
						this.modificar.setEnabled( true );
					}else{
						this.validar.setEnabled( false );
						this.modificar.setEnabled( false );
					}
				}
				
				String irpf=resul.getString("IRPF");
				if(irpf!=null){
					
					this.jTirpf.setText(resul.getString("IRPF"));
				}else{
					this.jTirpf.setText("0");	
					
				}
				String agente=resul.getString("agente");
				
				if (!("0").equals(codigoZona))
					jTzona.setText(codigoZona + "-" + DatosGeneralesErp.getDescripcionZona(codigoZona,stErp));
				/* resul = sta.executeQuery("SELECT descripcion FROM zonas WHERE codigo='"+codigoZona+"'");
				if (resul.next()){
					jTzona.setText(codigoZona+"-"+resul.getString("descripcion"));
				}
				*/
				
				if (!("0").equals(codigoRuta))
					jTruta.setText(codigoRuta + "-" + DatosGeneralesErp.getDescripcionRuta(codigoRuta,stErp));				
				/*resul = sta.executeQuery("SELECT descripcion FROM rutas WHERE codigo='"+codigoRuta+"'");
				if (resul.next()){
					jTruta.setText(codigoRuta+"-"+resul.getString("descripcion"));
				}
				*/
				
				if (!codigoTipo.equals("0"))
					jTtipo.setText(codigoTipo + "-" + DatosGeneralesErp.getDescripcionTipo(codigoTipo,stErp));
				/*resul = sta.executeQuery("SELECT descripcion FROM tipos WHERE codigo='"+codigoTipo+"'");
				if (resul.next()){
					jTtipo.setText(codigoTipo+"-"+resul.getString("descripcion"));
				}
				*/
				
				if (!("0").equals(codigoActividad))
					//En xgest la descripcion de actividad está implicita en el Cliente
					jTactividad.setText(codigoActividad);
					//jTactividad.setText(codigoActividad + "-" + DatosGeneralesErp.getDescripcionActividad(codigoActividad,stErp));
				
				/*resul = sta.executeQuery("SELECT descripcion FROM actividades WHERE codigo='"+codigoActividad+"'");
				if (resul.next()){
					jTactividad.setText(codigoActividad+"-"+resul.getString("descripcion"));
				}
				*/
				
				if (!("0").equals(codigoFormaPago))
					jTformapago.setText(codigoFormaPago + "-" + DatosGeneralesErp.getNombreFormasPago(codigoFormaPago,stErp));
				/*resul = sta.executeQuery("SELECT nombre FROM formas_pago WHERE codigo='"+codigoFormaPago+"'");
				if (resul.next()){
					jTformapago.setText(codigoFormaPago+"-"+resul.getString("nombre"));
				}
				*/
				
				if ("0".equals( codigoIva )){
					jTtipoiva.setText("");
				}else{
					jTtipoiva.setText(codigoIva + "-" + DatosGeneralesErp.getDescripcionTiposIva(codigoIva,stErp));
					/*resul = sta.executeQuery("SELECT descripcion FROM tipos_iva WHERE codigo='"+codigoIva+"'");
					if (resul.next()){
						jTtipoiva.setText(codigoIva+"-"+resul.getString("descripcion"));
					}
					*/
				}
				
				resul = sta.executeQuery("SELECT * FROM dbancarios WHERE "+where);
				if(resul.next()){
					this.jTdivisa.setText(resul.getString("divisa"));	
					this.jTtarifa.setText(resul.getString("tarifa"));
					this.jTdiapago1.setText(resul.getString("DIA_PAGO1"));
					this.jTdiapago2.setText(resul.getString("DIA_PAGO2"));
					this.jTdiapago3.setText(resul.getString("DIA_PAGO3"));
					this.jTmesnopago.setText(resul.getString("MES_NO_PAGO"));
					this.jTdescuento1.setText(resul.getString("dto_1"));
					this.jTdescuento2.setText(resul.getString("dto_2"));
					this.jTdescuentopp.setText(resul.getString("dto_pp"));
					this.jTincremento.setText(resul.getString("incremento"));
					this.jTportes.setText(resul.getString("portes"));
					this.jTriesgoconcedido.setText(resul.getString("RIESGO_CONCED"));
					this.jTriesgodisponible.setText(resul.getString("RIESGO_disp"));
					this.jTriesgosolicita.setText(resul.getString("riesgo_solicita"));
					this.jTbanco.setText(resul.getString("banco"));
					this.jTentidad.setText(resul.getString("ENTIDAD"));
					this.jTsucursal.setText(resul.getString("sucursal"));
					this.jTcb.setText(resul.getString("cb"));
					this.jTcc.setText(resul.getString("cc"));
					this.jTobservaciones.setText(resul.getString("observaciones"));
					this.jTaviso.setText(resul.getString("aviso"));
				}
				resul = sta.executeQuery("select nombre from agentes where codigo="+agente);
				if(resul.next()){
					agente = resul.getString("nombre");
					if( "null".equals( agente ) ){
						agente = "";
					}
					this.jTagente.setText( agente );
				}
				
			resul.close();
			sta.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	protected JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new java.awt.Rectangle(3,2,807,436));
			jSplitPane.setDividerLocation(340);
			jSplitPane.setDividerSize(5);
			jSplitPane.setRightComponent( getJTabbedPane() );
			jSplitPane.setLeftComponent( getJScrollPane() );
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	protected JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLpotencial = new JLabel();
			jLpotencial.setBounds(new java.awt.Rectangle(174,43,69,16));
			jLpotencial.setText("Potencial");
			jLfax = new JLabel();
			jLfax.setText("Fax");
			jLfax.setBounds(new java.awt.Rectangle(214,334,38,19));
			jLcodigopostal = new JLabel();
			jLcodigopostal.setText("Codigo Postal");
			jLcodigopostal.setBounds(new java.awt.Rectangle(214,314,105,19));
			jLpais = new JLabel();
			jLpais.setText("Pais");
			jLpais.setBounds(new java.awt.Rectangle(214,294,38,19));
			jLtelefono2 = new JLabel();
			jLtelefono2.setText("Telefono 2");
			jLtelefono2.setBounds(new java.awt.Rectangle(20,334,71,19));
			jLtelefono1 = new JLabel();
			jLtelefono1.setText("Telefono 1");
			jLtelefono1.setBounds(new java.awt.Rectangle(20,314,71,19));
			jLnif = new JLabel();
			jLnif.setText("N.I.F");
			jLnif.setBounds(new java.awt.Rectangle(20,294,36,19));
			jLweb = new JLabel();
			jLweb.setBounds(new java.awt.Rectangle(20,271,61,16));
			jLweb.setText("Web");
			jLemail = new JLabel();
			jLemail.setText("Email");
			jLemail.setBounds(new java.awt.Rectangle(20,251,61,19));
			jLcontacto = new JLabel();
			jLcontacto.setBounds(new java.awt.Rectangle(20,231,61,16));
			jLcontacto.setText("Contacto");
			jLprovincia = new JLabel();
			jLprovincia.setText("Provincia");
			jLprovincia.setBounds(new java.awt.Rectangle(20,211,61,19));
			jLpoblacion = new JLabel();
			jLpoblacion.setText("Población");
			jLpoblacion.setBounds(new java.awt.Rectangle(20,191,61,19));
			jLdireccion2 = new JLabel();
			jLdireccion2.setBounds(new java.awt.Rectangle(20,146,146,16));
			jLdireccion2.setText("Dirección Secundaria");
			jLdireccion1 = new JLabel();
			jLdireccion1.setBounds(new java.awt.Rectangle(20,106,119,16));
			jLdireccion1.setText("Dirección Principal");
			jLrazon = new JLabel();
			jLrazon.setText("Razón");
			jLrazon.setBounds(new java.awt.Rectangle(20,81,49,19));
			jLnombre = new JLabel();
			jLnombre.setText("Nombre");
			jLnombre.setBounds(new java.awt.Rectangle(20,61,51,19));
			jLcodigo2 = new JLabel();
			jLcodigo2.setText("Codigo 2");
			jLcodigo2.setBounds(new java.awt.Rectangle(20,41,52,19));
			jLfechalta = new JLabel();
			jLfechalta.setText("Fecha Alta");
			jLfechalta.setSize(new java.awt.Dimension(69,19));
			jLfechalta.setLocation(new java.awt.Point(174,21));
			jLcodigo = new JLabel();
			jLcodigo.setText("Codigo");
			jLcodigo.setSize(new java.awt.Dimension(51,19));
			jLcodigo.setLocation(new java.awt.Point(20,21));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new java.awt.Dimension(90,150));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			jPanel1.add(jLcodigo, null);
			jPanel1.add(jLfechalta, null);
			jPanel1.add(jLcodigo2, null);
			jPanel1.add(jLnombre, null);
			jPanel1.add(jLrazon, null);
			jPanel1.add(jLdireccion1, null);
			jPanel1.add(jLdireccion2, null);
			jPanel1.add(jLpoblacion, null);
			jPanel1.add(jLprovincia, null);
			jPanel1.add(jLcontacto, null);
			jPanel1.add(jLemail, null);
			jPanel1.add(jLweb, null);
			jPanel1.add(jLnif, null);
			jPanel1.add(jLtelefono1, null);
			jPanel1.add(jLtelefono2, null);
			jPanel1.add(jLpais, null);
			jPanel1.add(jLcodigopostal, null);
			jPanel1.add(jLfax, null);
			jPanel1.add(getJTcodigo(), null);
			jPanel1.add(getJTfechaalta(), null);
			jPanel1.add(getJTcodigo2(), null);
			jPanel1.add(getJTnombre(), null);
			jPanel1.add(getJTrazon(), null);
			jPanel1.add(getJTdireccion(), null);
			jPanel1.add(getJTdireccion2(), null);
			jPanel1.add(getJTpoblacion(), null);
			jPanel1.add(getJTprovincia(), null);
			jPanel1.add(getJTcontacto(), null);
			jPanel1.add(getJTemail(), null);
			jPanel1.add(getJTweb(), null);
			jPanel1.add(getJTnif(), null);
			jPanel1.add(getJTtelefono1(), null);
			jPanel1.add(getJTtelefono2(), null);
			jPanel1.add(getJTpais(), null);
			jPanel1.add(getJTcodigopostal(), null);
			jPanel1.add(getJTfax(), null);
			jPanel1.add(jLpotencial, null);
			jPanel1.add(getJTpotencial(), null);
			jPanel1.add(getValidar(), null);
		}
		return jPanel1;
	}
	
	
	protected JTextField getJTcodigo() {
		if (jTcodigo == null) {
			jTcodigo = new JTextField();
			jTcodigo.setLocation(new java.awt.Point(76,21));
			jTcodigo.setSize(new java.awt.Dimension(96,19));
			jTcodigo.setEditable(this.isEditable);
		}
		return jTcodigo;
	}
	/**
	 * This method initializes jTfechaalta	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTfechaalta() {
		if (jTfechaalta == null) {
			jTfechaalta = new JTextField();
			jTfechaalta.setBounds(new java.awt.Rectangle(245,21,113,19));
			jTfechaalta.setEditable(this.isEditable);
		}
		return jTfechaalta;
	}
	/**
	 * This method initializes jTcodigo2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTcodigo2() {
		if (jTcodigo2 == null) {
			jTcodigo2 = new JTextField();
			jTcodigo2.setBounds(new java.awt.Rectangle(76,41,96,19));
			jTcodigo2.setEditable(this.isEditable);
		}
		return jTcodigo2;
	}
	/**
	 * This method initializes jTnombre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTnombre() {
		if (jTnombre == null) {
			jTnombre = new JTextField();
			jTnombre.setBounds(new java.awt.Rectangle(76,61,371,19));
			jTnombre.setEditable(this.isEditable);
		}
		return jTnombre;
	}
	/**
	 * This method initializes jTrazon	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTrazon() {
		if (jTrazon == null) {
			jTrazon = new JTextField();
			jTrazon.setLocation(new java.awt.Point(76,81));
			jTrazon.setSize(new java.awt.Dimension(371,19));
			jTrazon.setEditable(this.isEditable);
		}
		return jTrazon;
	}
	/**
	 * This method initializes jTdireccion	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTdireccion() {
		if (jTdireccion == null) {
			jTdireccion = new JTextField();
			jTdireccion.setBounds(new java.awt.Rectangle(20,126,427,19));
			jTdireccion.setEditable(this.isEditable);
		}
		return jTdireccion;
	}
	/**
	 * This method initializes jTdireccion2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTdireccion2() {
		if (jTdireccion2 == null) {
			jTdireccion2 = new JTextField();
			jTdireccion2.setBounds(new java.awt.Rectangle(20,166,427,19));
			jTdireccion2.setEditable(this.isEditable);
		}
		return jTdireccion2;
	}
	/**
	 * This method initializes jTpoblacion	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTpoblacion() {
		if (jTpoblacion == null) {
			jTpoblacion = new JTextField();
			jTpoblacion.setBounds(new java.awt.Rectangle(85,191,361,19));
			jTpoblacion.setEditable(this.isEditable);
		}
		return jTpoblacion;
	}
	/**
	 * This method initializes jTprovincia	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTprovincia() {
		if (jTprovincia == null) {
			jTprovincia = new JTextField();
			jTprovincia.setBounds(new java.awt.Rectangle(86,211,361,19));
			jTprovincia.setEditable(this.isEditable);
		}
		return jTprovincia;
	}
	/**
	 * This method initializes jTcontacto	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTcontacto() {
		if (jTcontacto == null) {
			jTcontacto = new JTextField();
			jTcontacto.setBounds(new java.awt.Rectangle(86,231,361,19));
			jTcontacto.setEditable(this.isEditable);
		}
		return jTcontacto;
	}
	/**
	 * This method initializes jTemail	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTemail() {
		if (jTemail == null) {
			jTemail = new JTextField();
			jTemail.setBounds(new java.awt.Rectangle(86,251,361,19));
			jTemail.setEditable(this.isEditable);
		}
		return jTemail;
	}
	/**
	 * This method initializes jTweb	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTweb() {
		if (jTweb == null) {
			jTweb = new JTextField();
			jTweb.setBounds(new java.awt.Rectangle(86,271,361,19));
			jTweb.setEditable(this.isEditable);
		}
		return jTweb;
	}
	/**
	 * This method initializes jTnif	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTnif() {
		if (jTnif == null) {
			jTnif = new JTextField();
			jTnif.setLocation(new java.awt.Point(94,294));
			jTnif.setSize(new java.awt.Dimension(111,19));
			jTnif.setEditable(this.isEditable);
		}
		return jTnif;
	}
	/**
	 * This method initializes jTtelefono1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTtelefono1() {
		if (jTtelefono1 == null) {
			jTtelefono1 = new JTextField();
			jTtelefono1.setLocation(new java.awt.Point(94,314));
			jTtelefono1.setSize(new java.awt.Dimension(111,19));
			jTtelefono1.setEditable(this.isEditable);
		}
		return jTtelefono1;
	}
	/**
	 * This method initializes jTtelefono2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTtelefono2() {
		if (jTtelefono2 == null) {
			jTtelefono2 = new JTextField();
			jTtelefono2.setBounds(new java.awt.Rectangle(94,334,111,19));
			jTtelefono2.setEditable(this.isEditable);
		}
		return jTtelefono2;
	}
	/**
	 * This method initializes jTpais	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTpais() {
		if (jTpais == null) {
			jTpais = new JTextField();
			jTpais.setBounds(new java.awt.Rectangle(324,294,111,19));
			jTpais.setEditable(this.isEditable);
		}
		return jTpais;
	}
	/**
	 * This method initializes jTcodigopostal	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTcodigopostal() {
		if (jTcodigopostal == null) {
			jTcodigopostal = new JTextField();
			jTcodigopostal.setBounds(new java.awt.Rectangle(324,314,111,19));
			jTcodigopostal.setEditable(this.isEditable);
		}
		return jTcodigopostal;
	}
	/**
	 * This method initializes jTfax	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTfax() {
		if (jTfax == null) {
			jTfax = new JTextField();
			jTfax.setLocation(new java.awt.Point(324,334));
			jTfax.setSize(new java.awt.Dimension(111,19));
			jTfax.setEditable(this.isEditable);
		}
		return jTfax;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	protected JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new java.awt.Rectangle(21,14,459,412));
			jTabbedPane.addTab("Datos Generales",this.getJPanel1());
			jTabbedPane.addTab("Datos Comerciales", null, getComercial(), null);
			jTabbedPane.addTab("Datos Bancarios", null, getBanco(), null);
			jTabbedPane.addTab("Otros", null, getAviso(), null);
		}
		return jTabbedPane;
	}
	/**
	 * This method initializes comercial	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	protected JPanel getComercial() {
		if (comercial == null) {
			jLirpf = new JLabel();
			jLirpf.setText("I.R.P.F.");
			jLirpf.setLocation(new java.awt.Point(257,179));
			jLirpf.setSize(new java.awt.Dimension(54,19));
			jLtipoiva = new JLabel();
			jLtipoiva.setText("Tipo fijo IVA");
			jLtipoiva.setBounds(new java.awt.Rectangle(15,157,105,19));
			jLformapago = new JLabel();
			jLformapago.setText("Forma de Pago");
			jLformapago.setBounds(new java.awt.Rectangle(15,135,106,19));
			jLagente = new JLabel();
			jLagente.setText("Agente");
			jLagente.setBounds(new java.awt.Rectangle(15,113,106,19));
			jLactividad = new JLabel();
			jLactividad.setText("Actividad");
			jLactividad.setBounds(new java.awt.Rectangle(15,91,65,19));
			jLtipo = new JLabel();
			jLtipo.setText("Tipo");
			jLtipo.setBounds(new java.awt.Rectangle(15,69,38,19));
			jLdiavisita = new JLabel();
			jLdiavisita.setText("Dia Visita");
			jLdiavisita.setBounds(new java.awt.Rectangle(15,179,106,19));
			jLrutas = new JLabel();
			jLrutas.setText("Rutas");
			jLrutas.setBounds(new java.awt.Rectangle(15,47,37,19));
			jLzona = new JLabel();
			jLzona.setText("Zona");
			jLzona.setSize(new java.awt.Dimension(38,19));
			jLzona.setLocation(new java.awt.Point(15,25));
			comercial = new JPanel();
			comercial.setLayout(null);
			comercial.add(jLzona, null);
			comercial.add(jLrutas, null);
			comercial.add(jLdiavisita, null);
			comercial.add(jLtipo, null);
			comercial.add(jLactividad, null);
			comercial.add(jLagente, null);
			comercial.add(jLformapago, null);
			comercial.add(jLtipoiva, null);
			comercial.add(jLirpf, null);
			comercial.add(getJTzona(), null);
			comercial.add(getJTruta(), null);
			comercial.add(getJTtipo(), null);
			comercial.add(getJTactividad(), null);
			comercial.add(getJTagente(), null);
			comercial.add(getJTformapago(), null);
			comercial.add(getJTtipoiva(), null);
			comercial.add(getJTdiavisita(), null);
			comercial.add(getJTirpf(), null);
			if (isEditable){
				comercial.add(getJButtonZona(), null);
				comercial.add(getJButtonRuta(), null);
				comercial.add(getJButtonTipo(), null);
				comercial.add(getJButtonActividades(), null);
				comercial.add(getJButtonFormaPago(), null);
				comercial.add(getJButtonTipoIva(), null);
			}
		}
		return comercial;
	}
	/**
	 * This method initializes banco	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	protected JPanel getBanco() {
		if (banco == null) {
			jLriesgosolicita = new JLabel();
			jLriesgosolicita.setBounds(new java.awt.Rectangle(216,197,107,15));
			jLriesgosolicita.setText("Riesgo Solicita");
			jLcc = new JLabel();
			jLcc.setText("CC");
			jLcc.setSize(new java.awt.Dimension(201,19));
			jLcc.setLocation(new java.awt.Point(191,266));
			jLcb = new JLabel();
			jLcb.setText("CB");
			jLcb.setBounds(new java.awt.Rectangle(157,267,22,19));
			jLsucursal = new JLabel();
			jLsucursal.setText("Sucursal");
			jLsucursal.setBounds(new java.awt.Rectangle(87,267,60,19));
			jLentidad = new JLabel();
			jLentidad.setText("Entidad");
			jLentidad.setSize(new java.awt.Dimension(60,19));
			jLentidad.setLocation(new java.awt.Point(16,267));
			jLbanco = new JLabel();
			jLbanco.setBounds(new java.awt.Rectangle(16,214,52,16));
			jLbanco.setText("Banco");
			jLriesgodisponible = new JLabel();
			jLriesgodisponible.setText("Riesgo disponible");
			jLriesgodisponible.setBounds(new java.awt.Rectangle(215,171,108,19));
			jLriesgoconcedido = new JLabel();
			jLriesgoconcedido.setText("Riesgo Concedido");
			jLriesgoconcedido.setSize(new java.awt.Dimension(107,19));
			jLriesgoconcedido.setLocation(new java.awt.Point(17,171));
			jLportes = new JLabel();
			jLportes.setText("Portes");
			jLportes.setSize(new java.awt.Dimension(43,19));
			jLportes.setLocation(new java.awt.Point(163,138));
			jLincremento = new JLabel();
			jLincremento.setText("Incremento %");
			jLincremento.setSize(new java.awt.Dimension(81,19));
			jLincremento.setLocation(new java.awt.Point(16,138));
			jLdescuentopp = new JLabel();
			jLdescuentopp.setText("Descuento pronto pago %");
			jLdescuentopp.setSize(new java.awt.Dimension(147,19));
			jLdescuentopp.setLocation(new java.awt.Point(213,102));
			jLdto2 = new JLabel();
			jLdto2.setText("Dto2%");
			jLdto2.setSize(new java.awt.Dimension(42,19));
			jLdto2.setLocation(new java.awt.Point(117,102));
			jLdto1 = new JLabel();
			jLdto1.setText("Dto1%");
			jLdto1.setBounds(new java.awt.Rectangle(15,102,42,19));
			jLmesnopago = new JLabel();
			jLmesnopago.setBounds(new java.awt.Rectangle(213,60,80,16));
			jLmesnopago.setText("Mes no pago");
			jLdiapago = new JLabel();
			jLdiapago.setBounds(new java.awt.Rectangle(14,60,91,16));
			jLdiapago.setText("Dias de pago");
			jLtarifa = new JLabel();
			jLtarifa.setText("Tarifa asignada");
			jLtarifa.setBounds(new java.awt.Rectangle(212,23,96,19));
			jLdivisa = new JLabel();
			jLdivisa.setText("Divisa del pais");
			jLdivisa.setSize(new java.awt.Dimension(92,19));
			jLdivisa.setLocation(new java.awt.Point(13,23));
			banco = new JPanel();
			banco.setLayout(null);
			banco.add(jLdivisa, null);
			banco.add(jLtarifa, null);
			banco.add(jLdiapago, null);
			banco.add(jLmesnopago, null);
			banco.add(jLdto1, null);
			banco.add(jLdto2, null);
			banco.add(jLdescuentopp, null);
			banco.add(jLincremento, null);
			banco.add(jLportes, null);
			banco.add(jLriesgoconcedido, null);
			banco.add(jLriesgodisponible, null);
			banco.add(jLbanco, null);
			banco.add(jLentidad, null);
			banco.add(jLsucursal, null);
			banco.add(jLcb, null);
			banco.add(jLcc, null);
			banco.add(getJTdivisa(), null);
			banco.add(getJTtarifa(), null);
			banco.add(getJTdiapago1(), null);
			banco.add(getJTdiapago2(), null);
			banco.add(getJTdiapago3(), null);
			banco.add(getJTmesnopago(), null);
			banco.add(getJTdescuento1(), null);
			banco.add(getJTdescuento2(), null);
			banco.add(getJTdescuentopp(), null);
			banco.add(getJTincremento(), null);
			banco.add(getJTportes(), null);
			banco.add(getJTriesgoconcedido(), null);
			banco.add(getJTriesgodisponible(), null);
			banco.add(getJTbanco(), null);
			banco.add(getJTentidad(), null);
			banco.add(getJTsucursal(), null);
			banco.add(getJTcb(), null);
			banco.add(getJTcc(), null);
			banco.add(jLriesgosolicita, null);
			banco.add(getJTriesgosolicita(), null);
		}
		return banco;
	}
	/**
	 * This method initializes aviso	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	protected JPanel getAviso() {
		if (aviso == null) {
			jLaviso = new JLabel();
			jLaviso.setBounds(new java.awt.Rectangle(14,172,35,22));
			jLaviso.setText("Aviso");
			jLobservaciones = new JLabel();
			jLobservaciones.setText("Observaciones");
			jLobservaciones.setSize(new java.awt.Dimension(99,19));
			jLobservaciones.setLocation(new java.awt.Point(15,29));
			aviso = new JPanel();
			aviso.setLayout(null);
			aviso.add(jLobservaciones, null);
			aviso.add(jLaviso, null);
			aviso.add(getJTobservaciones(), null);
			aviso.add(getJTaviso(), null);
		}
		return aviso;
	}
	/**
	 * This method initializes jTzona	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTzona() {
		if (jTzona == null) {
			jTzona = new JTextField();
			jTzona.setEditable(false);
			jTzona.setBounds(new java.awt.Rectangle(90,25,330,19));
		}
		return jTzona;
	}
	/**
	 * This method initializes jTruta	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTruta() {
		if (jTruta == null) {
			jTruta = new JTextField();
			jTruta.setEditable(false);
			jTruta.setLocation(new java.awt.Point(90,47));
			jTruta.setSize(new java.awt.Dimension(330,19));
		}
		return jTruta;
	}
	/**
	 * This method initializes jTtipo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTtipo() {
		if (jTtipo == null) {
			jTtipo = new JTextField();
			jTtipo.setEditable(false);
			jTtipo.setLocation(new java.awt.Point(90,69));
			jTtipo.setSize(new java.awt.Dimension(330,19));
		}
		return jTtipo;
	}
	/**
	 * This method initializes jTactividad	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTactividad() {
		if (jTactividad == null) {
			jTactividad = new JTextField();
			jTactividad.setEditable(false);
			jTactividad.setLocation(new java.awt.Point(90,91));
			jTactividad.setSize(new java.awt.Dimension(330,19));
		}
		return jTactividad;
	}
	/**
	 * This method initializes jTagente	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTagente() {
		if (jTagente == null) {
			jTagente = new JTextField();
			jTagente.setBounds(new java.awt.Rectangle(128,113,317,19));
			jTagente.setEditable(this.isEditable);
		}
		return jTagente;
	}
	/**
	 * This method initializes jTformapago	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTformapago() {
		if (jTformapago == null) {
			jTformapago = new JTextField();
			jTformapago.setEditable(false);
			jTformapago.setBounds(new java.awt.Rectangle(129,135,295,19));
		}
		return jTformapago;
	}
	/**
	 * This method initializes jTtipoiva	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTtipoiva() {
		if (jTtipoiva == null) {
			jTtipoiva = new JTextField();
			jTtipoiva.setEditable(false);
			jTtipoiva.setBounds(new java.awt.Rectangle(129,157,295,19));
		}
		return jTtipoiva;
	}
	/**
	 * This method initializes jTdiavisita	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTdiavisita() {
		if (jTdiavisita == null) {
			jTdiavisita = new JTextField();
			jTdiavisita.setBounds(new java.awt.Rectangle(129,179,86,19));
			jTdiavisita.setEditable(this.isEditable);
		}
		return jTdiavisita;
	}
	/**
	 * This method initializes jTirpf	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTirpf() {
		if (jTirpf == null) {
			jTirpf = new JTextField();
			jTirpf.setBounds(new java.awt.Rectangle(318,179,128,19));
			jTirpf.setEditable(this.isEditable);
		}
		return jTirpf;
	}
	/**
	 * This method initializes jTdivisa	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTdivisa() {
		if (jTdivisa == null) {
			jTdivisa = new JTextField();
			jTdivisa.setBounds(new java.awt.Rectangle(114,23,85,19));
			jTdivisa.setEditable(this.isEditable);
		}
		return jTdivisa;
	}
	/**
	 * This method initializes jTtarifa	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTtarifa() {
		if (jTtarifa == null) {
			jTtarifa = new JTextField();
			jTtarifa.setLocation(new java.awt.Point(317,23));
			jTtarifa.setSize(new java.awt.Dimension(21,19));
			jTtarifa.setEditable(this.isEditable);
		}
		return jTtarifa;
	}
	/**
	 * This method initializes jTdiapago1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTdiapago1() {
		if (jTdiapago1 == null) {
			jTdiapago1 = new JTextField();
			jTdiapago1.setBounds(new java.awt.Rectangle(115,60,21,19));
			jTdiapago1.setEditable(this.isEditable);
		}
		return jTdiapago1;
	}
	/**
	 * This method initializes jTdiapago2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTdiapago2() {
		if (jTdiapago2 == null) {
			jTdiapago2 = new JTextField();
			jTdiapago2.setBounds(new java.awt.Rectangle(145,60,21,19));
			jTdiapago2.setEditable(this.isEditable);
		}
		return jTdiapago2;
	}
	/**
	 * This method initializes jTdiapago3	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTdiapago3() {
		if (jTdiapago3 == null) {
			jTdiapago3 = new JTextField();
			jTdiapago3.setBounds(new java.awt.Rectangle(173,60,21,19));
			jTdiapago3.setEditable(this.isEditable);
		}
		return jTdiapago3;
	}
	/**
	 * This method initializes jTmesnopago	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTmesnopago() {
		if (jTmesnopago == null) {
			jTmesnopago = new JTextField();
			jTmesnopago.setLocation(new java.awt.Point(302,60));
			jTmesnopago.setSize(new java.awt.Dimension(21,19));
			jTmesnopago.setEditable(this.isEditable);
		}
		return jTmesnopago;
	}
	/**
	 * This method initializes jTdescuento1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTdescuento1() {
		if (jTdescuento1 == null) {
			jTdescuento1 = new JTextField();
			jTdescuento1.setBounds(new java.awt.Rectangle(62,102,34,19));
			jTdescuento1.setEditable(this.isEditable);
		}
		return jTdescuento1;
	}
	/**
	 * This method initializes jTdescuento2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTdescuento2() {
		if (jTdescuento2 == null) {
			jTdescuento2 = new JTextField();
			jTdescuento2.setBounds(new java.awt.Rectangle(164,102,34,19));
			jTdescuento2.setEditable(this.isEditable);
		}
		return jTdescuento2;
	}
	/**
	 * This method initializes jTdescuentopp	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTdescuentopp() {
		if (jTdescuentopp == null) {
			jTdescuentopp = new JTextField();
			jTdescuentopp.setBounds(new java.awt.Rectangle(364,102,34,19));
			jTdescuentopp.setEditable(this.isEditable);
		}
		return jTdescuentopp;
	}
	/**
	 * This method initializes jTincremento	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTincremento() {
		if (jTincremento == null) {
			jTincremento = new JTextField();
			jTincremento.setBounds(new java.awt.Rectangle(102,138,34,19));
			jTincremento.setEditable(this.isEditable);
		}
		return jTincremento;
	}
	/**
	 * This method initializes jTportes	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTportes() {
		if (jTportes == null) {
			jTportes = new JTextField();
			jTportes.setBounds(new java.awt.Rectangle(214,138,147,19));
			jTportes.setEditable(this.isEditable);
		}
		return jTportes;
	}
	
	/**
	 * This method initializes jTriesgoSolicita	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTriesgosolicita() {
		if (jTriesgosolicita == null) {
			jTriesgosolicita = new JTextField();
			jTriesgosolicita.setBounds(new java.awt.Rectangle(334,195,73,17));
			jTriesgosolicita.setEditable(this.isEditable);
		}
		return jTriesgosolicita;
	}

	
	/**
	 * This method initializes jTriesgoconcedido	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTriesgoconcedido() {
		if (jTriesgoconcedido == null) {
			jTriesgoconcedido = new JTextField();
			jTriesgoconcedido.setBounds(new java.awt.Rectangle(133,171,76,19));
			jTriesgoconcedido.setEditable(this.isEditable);
		}
		return jTriesgoconcedido;
	}
	/**
	 * This method initializes jTriesgodisponible	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTriesgodisponible() {
		if (jTriesgodisponible == null) {
			jTriesgodisponible = new JTextField();
			jTriesgodisponible.setLocation(new java.awt.Point(332,171));
			jTriesgodisponible.setSize(new java.awt.Dimension(76,19));
			jTriesgodisponible.setEditable(this.isEditable);
		}
		return jTriesgodisponible;
	}
	/**
	 * This method initializes jTbanco	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTbanco() {
		if (jTbanco == null) {
			jTbanco = new JTextField();
			jTbanco.setBounds(new java.awt.Rectangle(16,238,418,19));
			jTbanco.setEditable(this.isEditable);
		}
		return jTbanco;
	}
	/**
	 * This method initializes jTentidad	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTentidad() {
		if (jTentidad == null) {
			jTentidad = new JTextField();
			jTentidad.setBounds(new java.awt.Rectangle(16,292,60,19));
			jTentidad.setEditable(this.isEditable);
		}
		return jTentidad;
	}
	/**
	 * This method initializes jTsucursal	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTsucursal() {
		if (jTsucursal == null) {
			jTsucursal = new JTextField();
			jTsucursal.setBounds(new java.awt.Rectangle(87,292,60,19));
			jTsucursal.setEditable(this.isEditable);
		}
		return jTsucursal;
	}
	/**
	 * This method initializes jTcb	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTcb() {
		if (jTcb == null) {
			jTcb = new JTextField();
			jTcb.setBounds(new java.awt.Rectangle(157,292,22,19));
			jTcb.setEditable(this.isEditable);
		}
		return jTcb;
	}
	/**
	 * This method initializes jTcc	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTcc() {
		if (jTcc == null) {
			jTcc = new JTextField();
			jTcc.setBounds(new java.awt.Rectangle(191,292,198,19));
			jTcc.setEditable(this.isEditable);
		}
		return jTcc;
	}
	/**
	 * This method initializes jTobservaciones	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextArea getJTobservaciones() {
		if (jTobservaciones == null) {
			jTobservaciones = new JTextArea();
			jTobservaciones.setBounds(new java.awt.Rectangle(15,55,425,112));
			jTobservaciones.setEditable(this.isEditable);
		}
		return jTobservaciones;
	}
	/**
	 * This method initializes jTaviso	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextArea getJTaviso() {
		if (jTaviso == null) {
			jTaviso = new JTextArea();
			jTaviso.setBounds(new java.awt.Rectangle(15,202,425,130));
			jTaviso.setEditable(this.isEditable);
		}
		return jTaviso;
	}
	/**
	 * This method initializes jTpotencial	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	protected JTextField getJTpotencial() {
		if (jTpotencial == null) {
			jTpotencial = new JTextField();
			jTpotencial.setBounds(new java.awt.Rectangle(245,41,113,19));
			jTpotencial.setEditable(this.isEditable);
		}
		return jTpotencial;
	}

	/** Metodo para sacar el codigo de un dato dado */
//  ====================================================	
	public String cortarDato(String str)
//  ====================================================
	{
		if (!str.equals("")) System.out.println("Char 0=" + str.charAt(0));
		if (!str.equals("") && str.charAt(0)!='0'){
			int i = str.indexOf('-');
			String res = str.substring(0,i);
			System.out.println(res);
			return res;
		}
		return "";
		
	}
	
	/**
	 * This method initializes jButtonZona	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonZona() {
		if (jButtonZona == null) {
			jButtonZona = new JButton();//
			jButtonZona.setIcon( sys.getIcono("iconBuscar.gif") );
			jButtonZona.setBounds(new java.awt.Rectangle(426,23,20,20));
			jButtonZona.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PopupMenu menuZona = new PopupMenu( cargador.gui.jFrame , sys.getConexionErp(), 0, "zonas" );
					jTzona.setText( menuZona.getValor() );
				}
			});
		}
		return jButtonZona;
	}

	/**
	 * This method initializes jButtonRuta	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonRuta() {
		if (jButtonRuta == null) {
			jButtonRuta = new JButton();
			jButtonRuta.setIcon( sys.getIcono("iconBuscar.gif") );
			jButtonRuta.setBounds(new java.awt.Rectangle(426,45,20,20));
			jButtonRuta.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PopupMenu menuRuta = new PopupMenu( cargador.gui.jFrame , sys.getConexionErp(), 0, "rutas" );
					jTruta.setText( menuRuta.getValor() );
				}
			});
		}
		return jButtonRuta;
	}

	/**
	 * This method initializes jButtonTipo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonTipo() {
		if (jButtonTipo == null) {
			jButtonTipo = new JButton();
			jButtonTipo.setIcon( sys.getIcono("iconBuscar.gif") );
			jButtonTipo.setBounds(new java.awt.Rectangle(426,68,20,20));
			jButtonTipo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PopupMenu menuTipo = new PopupMenu( cargador.gui.jFrame , sys.getConexionErp(), 0, "tipos" );
					jTtipo.setText( menuTipo.getValor() );
				}
			});
		}
		return jButtonTipo;
	}

	/**
	 * This method initializes jButtonActividades	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonActividades() {
		if (jButtonActividades == null) {
			jButtonActividades = new JButton();
			jButtonActividades.setIcon( sys.getIcono("iconBuscar.gif") );
			jButtonActividades.setBounds(new java.awt.Rectangle(426,91,20,20));
			jButtonActividades.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PopupMenu menuActividades = new PopupMenu( cargador.gui.jFrame , sys.getConexionErp(), 0, "actividades" );
					jTactividad.setText( menuActividades.getValor() );
				}
			});
		}
		return jButtonActividades;
	}

	/**
	 * This method initializes jButtonFormaPago	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonFormaPago() {
		if (jButtonFormaPago == null) {
			jButtonFormaPago = new JButton();
			jButtonFormaPago.setIcon( sys.getIcono("iconBuscar.gif") );
			jButtonFormaPago.setBounds(new java.awt.Rectangle(427,133,20,20));
			jButtonFormaPago.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PopupMenu menuFormaPago = new PopupMenu( cargador.gui.jFrame , sys.getConexionErp(), 1, null );
					jTformapago.setText( menuFormaPago.getValor() );
				}
			});
		}
		return jButtonFormaPago;
	}

	/**
	 * This method initializes jButtonTipoIva	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonTipoIva() {
		if (jButtonTipoIva == null) {
			jButtonTipoIva = new JButton();
			jButtonTipoIva.setIcon( sys.getIcono("iconBuscar.gif") );
			jButtonTipoIva.setBounds(new java.awt.Rectangle(427,157,20,20));
			jButtonTipoIva.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PopupMenu menuTipoIva = new PopupMenu( cargador.gui.jFrame , sys.getConexionErp(), 2, null );
					jTtipoiva.setText( menuTipoIva.getValor() );
				}
			});
		}
		return jButtonTipoIva;
	}

	/** Devuelve el codigo de un agente dado */
//	==============================================================================================	
	protected int calcularAgente(String str)
//	==============================================================================================
	{
		Statement s;
		String que;
		ResultSet rs;
		
		try{
			que = "SELECT codigo FROM Agentes WHERE nombre=";
			que += "'" + str +"'";
			s = conect.createStatement();
			rs = s.executeQuery(que);
			if (rs.next()){
				return rs.getInt("codigo");
			}
		}catch ( SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return 0;
	}


}  //  @jve:decl-index=0:visual-constraint="2,21"
