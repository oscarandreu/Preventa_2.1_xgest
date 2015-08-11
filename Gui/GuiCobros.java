package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import log.LogWriter;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;

import system.Cargador;
import system.SysData;
import ERP.ArticuloErp;
import ERP.ClienteErp;
import ERP.CobroErp;
import ERP.DatosGeneralesErp;
import Gui.system.InfoPane;
import Gui.system.PopupMenu;
import Gui.system.QueryPane;
import bbdd.FrameTableExtendida;
import data.Articulo;
import data.Cliente;
import data.Cobro;

public class GuiCobros extends GuiInternalFrame {
	private JPanel panel = null; // @jve:decl-index=0:visual-constraint="10,10"

	private JScrollPane cobros = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JButton eliminar = null;

	private JButton modificar = null;

	private JButton cerrar = null;

	private JLabel jLFecha = null;

	private JLabel jLnumerocobro = null;

	private JLabel jLobservaciones = null;

	private JTextField jTFecha = null;

	private JTextField jTnumerocobro = null;

	private JTextField jTobservaciones = null;

	private JLabel jLdto1 = null;

	private JLabel jLtotalneto = null;

	private JLabel jLtotaliva = null;

	private JLabel jLdto2 = null;

	private JLabel jLdtopp = null;

	private JLabel jLtotal = null;

	private JTextField jTdto1 = null;

	private JTextField jTtotalneto = null;

	private JTextField jTtotaliva = null;

	private JTextField jTdto2 = null;

	private JTextField jTdtopp = null;

	private JTextField jTtotal = null;

	private JTabbedPane jTabbedPane = null;

	private JLabel jLtiva = null;

	private JTextField jTtiva = null;

	private JSplitPane jPanel3 = null;

	private JLabel jLabel = null;

	private JCheckBox jCheckBox = null;

	private JLabel jLabel10 = null;

	private JComboBox comboClase = null;

	protected GuiLineasPedidos lineas;

	private JLabel jLabel1 = null;

	private JTextField serie = null;

	private JButton imprimir = null;

	String condicionWhere;

	protected String query;

	private JPanel jPanel = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel2 = null;

	private JTextField jTcodigo = null;

	private JLabel jLabel3 = null;

	private JTextField jTnombre = null;

	private JLabel jLFechaVto = null;

	private JTextField jTFechaVto = null;

	private JLabel jLformapago = null;

	private JTextField jTformapago = null;

	private JButton jButtonformapago = null;

	private JTextField jTimporteCobrado = null;

	private JLabel jLimporteCobrado = null;

	private JPanel jPanel4 = null;

	private JLabel jLagente = null;

	private JTextField jTagente = null;

	private JLabel jLpendiente = null;

	private JTextField jTpendiente = null;

	private JButton jBprevista = null;

	private JLabel jLcodcobrador = null;

	private JTextField jTcodcobrador = null;

	private JPanel jPanel5 = null;

	private JLabel jLentregado = null;

	private JTextField jTentregado = null;

	private JLabel jLpendienteTotal = null;

	private JTextField jTpendiente2 = null;

	private JLabel jLfechaCobro = null;

	private JTextField jTfechaCobro = null;

// ==================================================================
	public GuiCobros(Cargador cargador)
// ==================================================================
	{
		super(cargador);
		icono = "tab_comparativa.gif";
		toolTip = "Cobros realizados";
		titulo = "Cobros";
		lineas = new GuiLineasPedidos(sys);
	}

	// ==================================================================
	protected JPanel getPanel()
	// ==================================================================
	{
		if (panel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(37, 425, 205, 21));
			jLabel.setText("filas");
			panel = new JPanel();
			panel.setLayout(null);
			panel.setSize(new java.awt.Dimension(802,468));
			panel.add(getJSplitPane(), null);
			panel.add(getEliminar(), null);
			//panel.add(getModificar(), null);
			panel.add(getCerrar(), null);
			panel.add(jLabel, null);
			filasImportadas = jLabel;
			filasImportadas.setText("Numero de Nuevos Cobros : "
					+ model.numeroFilas);
		}
		return panel;
	}

	// ===========================================================================================
	protected JScrollPane getPedidos()
	// ===========================================================================================
	{
		model = new FrameTableExtendida(this, new String[]{"Cliente"}, 1);
		// Configuracion de la tabla
		model.setPrimaryKeys(new String[] { "CODIGO_CLIENTE","NUMERO_COBRO", "CODIGO_AGENTE" });
		model.setColumnas(new String[] { "a.NUMERO_COBRO", "a.CODIGO_CLIENTE" });
		//,"b.nombre" });
		model.setColumnasFormateadas(new String[] { "Número", "Agente" }); 
		//,"Cliente" });
		model.setAnchoColumnas(new int[] { 40, 40 }); //, 200 });
		model.setDatabase("cobro a"); //,dpersonales b");
		model.setWhere("a.numero_cobro>0"); //a.Codigo_cliente=b.codigo AND a.verificado='S'");
		//model.setVerificado(new String[] { "a.verificado", "verificado" });
		 model.setOrder("numero_cobro");
		cobros = new JScrollPane(model.getTabla());
		model.tabla.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
		cobros.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		cobros.setBounds(new java.awt.Rectangle(0, 0, 279, 390));
		return cobros;
	}

// ===========================================================================================
	public void mostrarDatos(String where)
// ===========================================================================================
	{
		Statement sta;
		condicionWhere = where;
		try {
			sta = conect.createStatement();
			String query = "SELECT "
					+ "NUMERO_COBRO,CODIGO_CLIENTE,CODIGO_AGENTE,NUMERO_DOCUMENTO,"
					+ "FECHA,FECHA_VTO,FORMA_PAGO,COBRADO,PENDIENTE,OBSERVACIONES,FECHA_COBRO "
					+ "FROM cobro "
					+ "WHERE " + where;

			ResultSet resul = sta.executeQuery(query);
			
			if (resul.next()) {
				String codigoCliente = resul.getString("codigo_cliente");
				
				Cliente cliErp = new ClienteErp();
				String queryErp = cliErp.getQueryClienteByCodigo(codigoCliente);
				Statement stErp = sys.getConexionErp().createStatement();
				ResultSet rsErp = stErp.executeQuery(queryErp);
				if (rsErp.next()){
					cliErp.setClienteFromErp(rsErp);
				}else{
					cliErp.getClienteByCodigoLocal(new Integer(codigoCliente),sys);
				}
				
				String codigoAgente = resul.getString("codigo_agente");
				Statement st = conect.createStatement();
				
				
				//this.jTFecha.setText(""+resul.getDate("fecha"));
				//this.jTFechaVto.setText(""+resul.getDate("fecha_vto"));
				long numDoc = resul.getLong("numero_documento");
				this.jTnumerocobro.setText(new Long(numDoc).toString());
				this.jTfechaCobro.setText(resul.getString("fecha_cobro"));
				String codigoFormaPago = resul.getString("forma_pago");
				this.jTformapago.setText(codigoFormaPago + "-" + DatosGeneralesErp.getNombreFormasPago(codigoFormaPago,stErp));
				this.jTobservaciones.setText(resul.getString("observaciones"));
				this.jTentregado.setText(resul.getString("cobrado"));
				this.jTpendiente2.setText(resul.getString("pendiente"));
				
				
				this.jTcodigo.setText(codigoCliente);
				this.jTnombre.setText(cliErp.getNombre());
				
				this.jTcodcobrador.setText(codigoAgente);
				ResultSet rs2 = st.executeQuery("SELECT nombre FROM AGENTES WHERE codigo='" + codigoAgente + "'");
				if (rs2.next())
					this.jTagente.setText(rs2.getString("NOMBRE"));
				
				Cobro cob= new CobroErp();
				queryErp = cob.getQueryCobroFromErp(numDoc); 
				ResultSet rsErp2 = stErp.executeQuery(queryErp);
				if (rsErp2.next()){
					this.jTFecha.setText(""+rsErp2.getDate("fecha"));
					this.jTFechaVto.setText(""+rsErp2.getDate("fechaVto"));
					this.jTimporteCobrado.setText(rsErp2.getString("importeCobrado"));
					this.jTtotal.setText(rsErp2.getString("importe"));
					this.jTpendiente.setText(new Double(new Double(jTtotal.getText())-new Double(jTimporteCobrado.getText())).toString());
				}
				
				
			}
			resul.close();
			sta.close();
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
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new java.awt.Rectangle(3, 2, 786, 415));
			jSplitPane.setDividerLocation(320);
			jSplitPane.setDividerSize(5);
			jSplitPane.setRightComponent(getJTabbedPane());
			jSplitPane.setLeftComponent(getPedidos());
			jSplitPane
					.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
						public void propertyChange(
								java.beans.PropertyChangeEvent e) {
							if ((e.getPropertyName()
									.equals("lastDividerLocation"))) {
								model.tabla
										.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
							}
						}
					});
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLpendiente = new JLabel();
			jLpendiente.setBounds(new java.awt.Rectangle(241,58,103,19));
			jLpendiente.setText("IMP. PENDIENTE :");
			jLimporteCobrado = new JLabel();
			jLimporteCobrado.setBounds(new java.awt.Rectangle(7,59,123,21));
			jLimporteCobrado.setText("IMPORTE COBRADO :");
			
			jLFechaVto = new JLabel();
			jLFechaVto.setBounds(new java.awt.Rectangle(246,38,84,20));
			jLFechaVto.setText("Fecha Vencim.");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(121,102,42,18));
			jLabel1.setToolTipText("");
			jLabel1.setText("Serie");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(11,103,36,17));
			jLabel10.setText("Clase");
			jLtiva = new JLabel();
			jLtiva.setBounds(new java.awt.Rectangle(340,158,45,19));
			jLtiva.setText("T.IVA%");
			jLtotal = new JLabel();
			jLtotal.setText("IMPORTE TOTAL :");
			jLtotal.setBounds(new java.awt.Rectangle(8,38,106,19));
			jLdtopp = new JLabel();
			jLdtopp.setText("Dto PP");
			jLdtopp.setBounds(new java.awt.Rectangle(215,158,42,19));
			jLdto2 = new JLabel();
			jLdto2.setText("Dto2%");
			jLdto2.setSize(new java.awt.Dimension(36, 19));
			jLdto2.setLocation(new java.awt.Point(116,158));
			jLtotaliva = new JLabel();
			jLtotaliva.setText("Total IVA   :");
			jLtotaliva.setBounds(new java.awt.Rectangle(266,210,71,19));
			jLtotalneto = new JLabel();
			jLtotalneto.setText("Total Neto :");
			jLtotalneto.setBounds(new java.awt.Rectangle(266,189,71,19));
			jLdto1 = new JLabel();
			jLdto1.setText("Dto1%");
			jLdto1.setBounds(new java.awt.Rectangle(18,158,36,19));
			jLnumerocobro = new JLabel();
			jLnumerocobro.setText("Numero Documento");
			jLnumerocobro.setBounds(new java.awt.Rectangle(7,17,121,19));
			jLFecha = new JLabel();
			jLFecha.setText("Fecha");
			jLFecha.setSize(new java.awt.Dimension(39,19));
			jLFecha.setLocation(new java.awt.Point(268,17));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new java.awt.Dimension(90, 150));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos de Cobro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			jPanel1.setBounds(new java.awt.Rectangle(3,160,448,87));
			jPanel1.add(jLFecha, null);
			jPanel1.add(jLnumerocobro, null);
			//jPanel1.add(jLobservaciones, null);
			//jPanel1.add(jLdto1, null);
			//jPanel1.add(jLtotalneto, null);
			//jPanel1.add(jLtotaliva, null);
			//jPanel1.add(jLdto2, null);
			//jPanel1.add(jLdtopp, null);
			jPanel1.add(jLtotal, null);
			//jPanel1.add(jLtiva, null);
			jPanel1.add(getJTFecha(), null);
			jPanel1.add(getJTnumerocobro(), null);
			//jPanel1.add(getJTobservaciones(), null);
			//jPanel1.add(getJTdto1(), null);
			//jPanel1.add(getJTtotalneto(), null);
			//jPanel1.add(getJTtotaliva(), null);
			//jPanel1.add(getJTdto2(), null);
			//jPanel1.add(getJTdtopp(), null);
			jPanel1.add(getJTtotal(), null);
			//jPanel1.add(getJTtiva(), null);
			//jPanel1.add(getJCheckBox(), null);
			//jPanel1.add(jLabel10, null);
			//jPanel1.add(getComboClase(), null);
			//jPanel1.add(jLabel1, null);
			//jPanel1.add(getSerie(), null);
			//jPanel1.add(getValidar(), null);
			jPanel1.add(jLFechaVto, null);
			jPanel1.add(getJTFechaVto(), null);
			//jPanel1.add(jLformapago, null);
			//jPanel1.add(getJTformapago(), null);
			//jPanel1.add(getJButtonformapago(), null);
			jPanel1.add(getJTimporteCobrado(), null);
			jPanel1.add(jLimporteCobrado, null);
			jPanel1.add(jLpendiente, null);
			jPanel1.add(getJTpendiente(), null);
			//jPanel1.add(getJBprevista(), null);
		}
		return jPanel1;
	}

	private JButton getEliminar() {
		if (eliminar == null) {
			eliminar = new JButton();
			eliminar.setText("Eliminar");
			eliminar.setLocation(new java.awt.Point(314, 425));
			eliminar.setSize(new java.awt.Dimension(100, 26));
			eliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (model.tabla.getSelectedRow() != -1) {
						//La pestaña seleccionada es la de la cabecera de pedidos
						if ( jTabbedPane.getSelectedIndex() == 0 ){
							
							System.out.println("Eliminando cobro");
							//int fila = model.tabla.getSelectedRow();
							int n = new QueryPane().consultar("Desea eliminar (S/N)");
							if (n == 0) {
								try {
									query = "DELETE FROM cabecera_pedido WHERE ";
									query += condicionWhere;
									System.out.println(query);
									Statement s = conect.createStatement();
									s.executeUpdate(query);
									s.close();
								} catch (SQLException ex) {
									ex.printStackTrace();
								}
								cargador.actualizarVentanas();
								if (model.numeroFilas == 0) {
									imprimir.setEnabled(false);
								}
							}
						}
						//La pestaña seleccionada es la de lineas de pedidos
						else{
							System.out.println("Eliminando linea");
							lineas.eliminarLineaPedido( cargador );
						}
					}else{
						new InfoPane("ATENCIÓN","Seleccione un Cobro");
					}
				}
			});
		}
		return eliminar;
	}

	/**@deprecated
	 * */
	private JButton getModificar() {
		if (modificar == null) {
			modificar = new JButton();
			modificar.setText("Modificar");
			modificar.setLocation(new java.awt.Point(434, 425));
			modificar.setSize(new java.awt.Dimension(100, 26));
			
			modificar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// La pestaña seleccionada es la de la cabecera de pedidos
					if (model.tabla.getSelectedRow() != -1) {
						if ( jTabbedPane.getSelectedIndex() == 0 ){
							
							int n = new QueryPane().consultar( "Desea modificar (S/N)" );
							if ( n == 0 ){
															
								String query = "UPDATE cabecera_pedido SET "; 
								query += "observaciones='" + jTobservaciones.getText() + "',";
								if (jCheckBox.isEnabled()){
									query += "albaran='S',";
								}else{
									query += "albaran='N',";
								}
								query += "total_iva='" + jTtotaliva.getText() + "',";
								
								/*
								query += "dto_1='" + jTdto1.getText() + "',";
								query += "dto_2='" + jTdto2.getText() + "',";
								query += "dto_pp='" + jTdtopp.getText() + "',";
								*/
								if (comboClase.getSelectedIndex() == 1){
									query += "clase='N'";
								}else{
									query += "clase=''";
								}
								
								query += " WHERE codigo_cliente='"+jTcodigo.getText()+"'" +
										" AND numero_cabecera='" + jTnumerocobro.getText() +"'" +
										" AND serie='" + serie.getText() + "'";
								
								System.out.println( query);
								Statement sta;
								try {
									sta = conect.createStatement();
									sta.executeUpdate( query );
									
									sta.close();
								} catch (SQLException e1) {
									e1.printStackTrace();
									new LogWriter( e1.getStackTrace() );
								}
								mostrarDatos(condicionWhere);
								
							}
							
							// La pestaña seleccionada es la de lineas de pedidos
						}else{
							lineas.modificarLineaPedido( cargador );
						}
					}else{
						new InfoPane("ATENCIÓN","Seleccione una Factura");
					}
				}
			});
		}
		return modificar;
	}

	private JButton getCerrar() {
		if (cerrar == null) {
			cerrar = new JButton();
			cerrar.setText("Cerrar");
			cerrar.setLocation(new java.awt.Point(554, 425));
			cerrar.setSize(new java.awt.Dimension(100, 26));
			cerrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cerrar();
				}
			});
		}
		return cerrar;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getImprimir() {
		if (imprimir == null) {
			imprimir = new JButton();
			imprimir.setText("Imprimir");
			imprimir.setBounds(new java.awt.Rectangle(282,97,125,29));
			imprimir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//if (model.tabla.getSelectedRow() != -1) {
					try {
						int n = new QueryPane().consultar("Desea imprimir (S/N)");
						if (n == 0) {
							
							//new InfoPane("ATENCION","Opción en construcción");
							
							EngineConfig config;
							IReportEngine engine = null;
							
							try{
								System.out.println("configuracion...");
								config = new EngineConfig( );
								config.setEngineHome( "C:\\work\\PReventa_2.0_xgest\\lib\\birt-runtime-2_1_0\\ReportEngine" );
								config.setLogConfig("c:/temp", Level.FINE);
										
								System.out.println("lanzamiento...");
								Platform.startup( config );
								IReportEngineFactory factory = (IReportEngineFactory) Platform
										.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
								engine = factory.createReportEngine( config );
								engine.changeLogLevel( Level.WARNING );
								
								/*
							}catch( Exception ex){
								ex.printStackTrace();
							}
							*/
//							 Run reports, etc.
							
								System.out.println("abriendo report...");
								IReportRunnable report = engine.openReportDesign( "C:\\work\\Preventa_2.0_xgest\\testReport.rptdesign" );
								
								IRunAndRenderTask task = engine.createRunAndRenderTask(report); 
								
							
								//FORenderOption option = new FORenderOption(); option.setOutputFileName("test.pdf"); option.setOutputFormat("pdf"); task.setRenderOption(option); 
								task.run(); 
								//engine.destroy();

							
								
//							 Shut down the engine.
							/* try
							{
							*/
								System.out.println("cerrando...");
								engine.shutdown();
								Platform.shutdown();    
								System.out.println("saliendo...");
							}
							catch ( Exception e1 )
							{
								e1.printStackTrace();
							}
							/*
								try{
									BufferedWriter out =  new BufferedWriter(new FileWriter("fichero.txt"));
									out.write("***** COBRO REALIZADO *****");
									out.write("\r\n\r\n");
									out.write("NOMBRE DEL CLIENTE: " + jTnombre.getText());
									out.write("\r\n");
									out.write("NOMBRE DEL COBRADOR: " + jTagente.getText());
									out.write("\r\n");
									out.write("FECHA DE COBRO: " + jTfechaCobro.getText()  );
									out.write("\r\n");
									out.write("FECHA DE VENCIMIENTO DEL COBRO: "  );
									out.write("\r\n");
									out.write("FORMA DE PAGO: " + jTformapago.getText());
									out.write("\r\n");
									out.write("IMPORTE PAGADO:" + jTentregado.getText());
									out.write("\r\n");

									out.flush();
									out.close();

								}catch(Exception ex){

								}

								/*
								Frame frameImpresion = new Frame();

								PrintJob impresion = jPanel.getToolkit().getPrintJob(frameImpresion,"Impresión de cobro",null);

								Graphics graficoImpresion = impresion.getGraphics(); 

								jPanel.printAll(graficoImpresion);
								graficoImpresion.dispose();
								impresion.end();
							 */
							Statement s = sys.getConexionPropia().createStatement();
							Statement st = sys.getConexionErp().createStatement();
							
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
						new LogWriter( ex.getStackTrace() );
						new InfoPane("ERROR","Se ha producido un ERROR al Imprimir el Cobro");
					}
					cargador.actualizarVentanas();
					//}
				}
			});
		}

		return imprimir;
	}

	private JTextField getJTFecha() {
		if (jTFecha == null) {
			jTFecha = new JTextField();
			jTFecha.setLocation(new java.awt.Point(307,16));
			jTFecha.setSize(new java.awt.Dimension(111,19));
			jTFecha.setEditable(false);
		}
		return jTFecha;
	}

	private JTextField getJTnumerocobro() {
		if (jTnumerocobro == null) {
			jTnumerocobro = new JTextField();
			jTnumerocobro.setBounds(new java.awt.Rectangle(128,17,123,19));
			jTnumerocobro.setEditable(false);
		}
		return jTnumerocobro;
	}

	private JTextField getJTobservaciones() {
		if (jTobservaciones == null) {
			jTobservaciones = new JTextField();
			jTobservaciones.setEditable(false);
			jTobservaciones.setBounds(new java.awt.Rectangle(15,61,206,64));
		}
		return jTobservaciones;
	}

	private JTextField getJTdto1() {
		if (jTdto1 == null) {
			jTdto1 = new JTextField();
			jTdto1.setLocation(new java.awt.Point(60,158));
			jTdto1.setSize(new java.awt.Dimension(50, 19));
			jTdto1.setEditable(false);
		}
		return jTdto1;
	}

	private JTextField getJTtotalneto() {
		if (jTtotalneto == null) {
			jTtotalneto = new JTextField();
			jTtotalneto.setBounds(new java.awt.Rectangle(339,189,100,19));
			jTtotalneto.setEditable(false);
		}
		return jTtotalneto;
	}

	private JTextField getJTtotaliva() {
		if (jTtotaliva == null) {
			jTtotaliva = new JTextField();
			jTtotaliva.setBounds(new java.awt.Rectangle(339,210,100,19));
			jTtotaliva.setEditable(false);
		}
		return jTtotaliva;
	}

	private JTextField getJTdto2() {
		if (jTdto2 == null) {
			jTdto2 = new JTextField();
			jTdto2.setLocation(new java.awt.Point(159,158));
			jTdto2.setSize(new java.awt.Dimension(50, 19));
			jTdto2.setEditable(false);
		}
		return jTdto2;
	}

	private JTextField getJTdtopp() {
		if (jTdtopp == null) {
			jTdtopp = new JTextField();
			jTdtopp.setBounds(new java.awt.Rectangle(266,158,50,19));
			jTdtopp.setEditable(false);
		}
		return jTdtopp;
	}

	private JTextField getJTtotal() {
		if (jTtotal == null) {
			jTtotal = new JTextField();
			jTtotal.setBounds(new java.awt.Rectangle(117,37,110,19));
			jTtotal.setEditable(false);
		}
		return jTtotal;
	}

	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new java.awt.Rectangle(21, 14, 459, 393));
			jTabbedPane.addTab("Cabecera del cobro", null, getJPanel() );
			//jTabbedPane.addTab("Lineas de la cobro", null, getJPanel3());
		}
		return jTabbedPane;
	}


	private JTextField getJTtiva() {
		if (jTtiva == null) {
			jTtiva = new JTextField();
			jTtiva.setBounds(new java.awt.Rectangle(390,158,38,19));
			jTtiva.setEditable(false);
		}
		return jTtiva;
	}

	private JSplitPane getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = lineas.getJSplitPane();
		}
		return jPanel3;
	}

	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(137,44,78,27));
			jCheckBox.setEnabled(true);
			jCheckBox.setName("albaran");
			jCheckBox.setText("Albarán");
		}
		return jCheckBox;
	}

	private JComboBox getComboClase() {
		if (comboClase == null) {
			comboClase = new JComboBox(new String[]{"","N"} );
			comboClase.setBounds(new java.awt.Rectangle(52,103,52,17));
		}
		return comboClase;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSerie() {
		if (serie == null) {
			serie = new JTextField();
			serie.setBounds(new java.awt.Rectangle(167,102,54,19));
			serie.setEditable(false);
		}
		return serie;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel2(), null);
			jPanel.add(getJPanel1(), null);
			jPanel.add(getJPanel4(), null);
			jPanel.add(getJPanel5(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(8,45,45,16));
			jLabel3.setText("Nombre");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(11,24,39,16));
			jLabel2.setText("Codigo");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new java.awt.Rectangle(2,0,452,83));
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJTcodigo(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJTnombre(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTcodigo() {
		if (jTcodigo == null) {
			jTcodigo = new JTextField();
			jTcodigo.setBounds(new java.awt.Rectangle(53,22,111,21));
			jTcodigo.setEditable(false);
		}
		return jTcodigo;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTnombre() {
		if (jTnombre == null) {
			jTnombre = new JTextField();
			jTnombre.setBounds(new java.awt.Rectangle(54,44,368,20));
			jTnombre.setEditable(false);
		}
		return jTnombre;
	}

	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTFechaVto() {
		if (jTFechaVto == null) {
			jTFechaVto = new JTextField();
			jTFechaVto.setBounds(new java.awt.Rectangle(329,37,110,20));
			jTFechaVto.setPreferredSize(new java.awt.Dimension(4,20));
			jTFechaVto.setEditable(false);
		}
		return jTFechaVto;
	}
	
	/** 
	 * @deprecated
	 * Metodo para calcular el total neto o con iva de los productos 
	 */

//  ======================================================================	
	public double calcularTotal(int op, String cabec, String serie)
//	======================================================================
	{
		double res = 0;
		
		switch (op){
		case 0:		// calculo del total neto de todas las lineas de pedido
			
			// primero obtenemos nos diversos descuentos del cliente
			double dto1 = Double.parseDouble(jTdto1.getText());
			double dto2 = Double.parseDouble(jTdto2.getText());
			double dtopp = Double.parseDouble(jTdtopp.getText());
			
			// recorremos todas las líneas sacando su total neto y aplicandoles descuentos, para despues sumarlas
			try{
				Statement st = conect.createStatement();
				ResultSet rs = st.executeQuery( "SELECT TOTAL " +
						"FROM linea_pedido " +
						"WHERE numero_cabecera = '" + cabec + "' AND serie='" + serie + "'");
				while ( rs.next() ){
					double totlin = rs.getDouble("total");
					double tmp = totlin * (dto1/100);
					totlin = totlin - tmp;
					tmp = totlin * (dto2/100);
					totlin = totlin - tmp;
					tmp = totlin * (dtopp/100);
					totlin = totlin - tmp;
					System.out.println("Total sin iva linea:" + totlin);
					res += totlin;
				}
				System.out.println("Total sin iva pedido:" + res);
				rs.close();
				st.close();
			}catch (SQLException ex){
				ex.printStackTrace();
				new LogWriter( ex.getStackTrace() );
			}
			
			break;
			
		case 1:		// calculo del total de iva de todas las lineas de pedido
			String iva_fijo = null;
			String clase = null;
			try{
				Statement st = conect.createStatement();
				Statement stErp = sys.getConexionErp().createStatement();
				
				ResultSet rs = st.executeQuery("SELECT a.clase,a.codigo_cliente " +
						"FROM cabecera_pedido a " +
						"WHERE a.numero_cabecera ='" + cabec + "' AND serie='" + serie + "'");  
				if (rs.next()){
					clase = rs.getString("clase");
					Cliente cliErp = new ClienteErp();
					String queryErp = cliErp.getQueryClienteByCodigo(rs.getString("codigo_cliente"));
					ResultSet rsErp = stErp.executeQuery(queryErp);
					if (rsErp.next()){
						cliErp.setClienteFromErp(rsErp);
						iva_fijo = new Integer(cliErp.getIvaFijo()).toString();
					}
				}
				rs.close();
				st.close();
			}catch(SQLException e1){
				e1.printStackTrace();
				new LogWriter( e1.getStackTrace() );
			}
			
			// primero vemos si el cliente es de tipo N o no
			if (comboClase.getSelectedItem().equals("N")){
				
				// si lo es no se le aplica IVA
				System.out.println("Tipo : N");
				res = 0;
				//res = Double.parseDouble( jTtotalneto.getText() );
				
			}else{
				
				// si no lo es, vemos si el Cliente tiene IVA fijo o no
				System.out.println("Tipo : no N, iva fijo:" + iva_fijo);
				if ( iva_fijo.equals("0") || iva_fijo.equals("4")){
					System.out.println("No tiene iva fijo");
					
					// no tiene iva fijo
					try{
						Statement st = conect.createStatement();
						Statement stErp = sys.getConexionErp().createStatement();
						double porciva = 0;
						
						
						ResultSet rs = st.executeQuery("SELECT a.codigo_articulo " +
								"FROM linea_pedido a " +
								"WHERE a.numero_cabecera ='" + cabec + "' " +
							"AND a.serie ='" + serie + "'");
						while (rs.next()){
							Articulo artErp = new ArticuloErp();
							String queryErp = artErp.getQueryArticuloByCodigo(rs.getString("codigo_articulo"));
							ResultSet rsErp = stErp.executeQuery( queryErp );
							if(rsErp.next()){
								artErp.setArticulo(rsErp);
								porciva = artErp.getPorcIva(sys);
							}
							System.out.println("Aplicando a neto:"+ rs.getString("total") + " iva:" + porciva);
							double totn = Double.parseDouble(rs.getString("total"));
							if (iva_fijo.equals("0")){
								res += totn*(porciva/100);
							}else if(iva_fijo.equals("4")){
								res = 0;
							}
							
						}
						rs.close();
						st.close();
					}catch(SQLException ex){
						ex.printStackTrace();
						new LogWriter( ex.getStackTrace() );
					}
					
				}else{
					System.out.println("SI tiene iva fijo:" + iva_fijo);
					// tiene iva fijo
					try{
						Statement st = conect.createStatement();
						ResultSet rs = st.executeQuery("SELECT total FROM linea_pedido " +
								"WHERE numero_cabecera ='" + cabec + "'");
						double ivafijo = Double.parseDouble(iva_fijo);
						while (rs.next()){
							System.out.println("Aplicando a neto:"+ rs.getString("total") + " iva fijo:" + ivafijo);
							double totn = Double.parseDouble(rs.getString("total"));
							res += totn*(ivafijo/100);
						}
						rs.close();
						st.close();
					}catch(SQLException ex){
						ex.printStackTrace();
						new LogWriter( ex.getStackTrace() );
					}
				}
			}
			
			break;
			
		}
		return res;
		
	}
	
	/**
	 * Metodo para calcular el numero de unidades totales de una linea de pedido, teniendo en cuenta
	 * las cajas pedidas, las unidades por bulto del articulo concreto y las unidades sueltas pedidas. 
	 */
//  ======================================================================	
	public int calculoUnidades(String caj, String unidBulto, String unid)
//  ======================================================================	
	{	
		System.out.println(new Integer(caj) + ":" +SysData.formatFloatToInteger( unidBulto ) + ":" + new Integer(unid));
		return ( new Integer(caj) * SysData.formatFloatToInteger(unidBulto) + new Integer(unid) );
	}
	
	/** 
	 * Metodo para calcular el total neto o con iva de los productos 
	 */

//  ======================================================================	
	public double calcularTotalNeto(String cabec, String serie)
//	======================================================================
	{
		double res = 0;
		
		// primero obtenemos los diversos descuentos del cliente
		double dto1 = Double.parseDouble(jTdto1.getText());
		double dto2 = Double.parseDouble(jTdto2.getText());
		double dtopp = Double.parseDouble(jTdtopp.getText());
		
		// recorremos todas las líneas sacando su total neto y aplicandoles descuentos, para despues sumarlas
		try{
			Statement st = conect.createStatement();
			ResultSet rs = st.executeQuery( "SELECT TOTAL " +
					"FROM linea_pedido " +
					"WHERE numero_cabecera = '" + cabec + "' AND serie='" + serie + "'");
			while ( rs.next() ){
				double totlin = rs.getDouble("total");
				double tmp = totlin * (dto1/100);
				totlin = totlin - tmp;
				tmp = totlin * (dto2/100);
				totlin = totlin - tmp;
				tmp = totlin * (dtopp/100);
				totlin = totlin - tmp;
				System.out.println("Total sin iva linea:" + totlin);
				res += totlin;
			}
			System.out.println("Total sin iva pedido:" + res);
			rs.close();
			st.close();
		}catch (SQLException ex){
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
		}

		return res;
		
	}

	/**
	 * This method initializes jTformapago	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTformapago() {
		if (jTformapago == null) {
			jTformapago = new JTextField();
			jTformapago.setBounds(new java.awt.Rectangle(94,22,131,18));
			jTformapago.setEditable(false);
		}
		return jTformapago;
	}

	/**
	 * This method initializes jButtonformapago	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonformapago() {
		if (jButtonformapago == null) {
			jButtonformapago = new JButton();
			jButtonformapago.setBounds(new java.awt.Rectangle(227,19,21,21));
			jButtonformapago.setIcon( sys.getIcono("iconBuscar.gif") );
			jButtonformapago.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					PopupMenu menuFormaPago = new PopupMenu( cargador.gui.jFrame , sys.getConexionErp(), 1, null );
					jTformapago.setText( menuFormaPago.getValor() );
				}
			});
		}
		return jButtonformapago;
	}

	/**
	 * This method initializes jTimporteCobrado	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTimporteCobrado() {
		if (jTimporteCobrado == null) {
			jTimporteCobrado = new JTextField();
			jTimporteCobrado.setBounds(new java.awt.Rectangle(130,60,100,19));
			jTimporteCobrado.setEditable(false);
		}
		return jTimporteCobrado;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jLcodcobrador = new JLabel();
			jLcodcobrador.setBounds(new java.awt.Rectangle(10,22,47,16));
			jLcodcobrador.setText("Codigo");
			jLagente = new JLabel();
			jLagente.setText("Nombre");
			jLagente.setBounds(new java.awt.Rectangle(10,46,48,16));
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(new java.awt.Rectangle(2,86,450,73));
			jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cobrador/Agente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			jPanel4.add(jLagente, null);
			jPanel4.add(getJTagente(), null);
			jPanel4.add(jLcodcobrador, null);
			jPanel4.add(getJTcodcobrador(), null);
			
		}
		return jPanel4;
	}

	/**
	 * This method initializes jTagente	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTagente() {
		if (jTagente == null) {
			jTagente = new JTextField();
			jTagente.setBounds(new java.awt.Rectangle(60,44,362,20));
			jTagente.setEditable(false);
		}
		return jTagente;
	}

	/**
	 * This method initializes jTpendiente	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextField getJTpendiente() {
		if (jTpendiente == null) {
			jTpendiente = new JTextField();
			jTpendiente.setBounds(new java.awt.Rectangle(344,57,99,20));
			jTpendiente.setEditable(false);
		}
		return jTpendiente;
	}

	/**
	 * This method initializes jBprevista	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBprevista() {
		if (jBprevista == null) {
			jBprevista = new JButton();
			jBprevista.setBounds(new java.awt.Rectangle(58,82,123,28));
			jBprevista.setText("Previsualizar");
		}
		return jBprevista;
	}

	/**
	 * This method initializes jTcodcobrador	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTcodcobrador() {
		if (jTcodcobrador == null) {
			jTcodcobrador = new JTextField();
			jTcodcobrador.setBounds(new java.awt.Rectangle(60,19,106,20));
			jTcodcobrador.setEditable(false);
		}
		return jTcodcobrador;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jLfechaCobro = new JLabel();
			jLfechaCobro.setBounds(new java.awt.Rectangle(234,22,80,18));
			jLfechaCobro.setText("Fecha Cobro:");
			jLpendienteTotal = new JLabel();
			jLpendienteTotal.setBounds(new java.awt.Rectangle(230,66,105,22));
			jLpendienteTotal.setText("IMP. PENDIENTE :");
			jLentregado = new JLabel();
			jLentregado.setBounds(new java.awt.Rectangle(230,44,108,19));
			jLentregado.setText("IMP. ENTREGADO :");
			jPanel5 = new JPanel();
			jPanel5.setLayout(null);
			jPanel5.setBounds(new java.awt.Rectangle(3,249,449,134));
			jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cobro Realizado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			jLformapago = new JLabel();
			jLformapago.setBounds(new java.awt.Rectangle(16,22,73,18));
			jLformapago.setText("Forma Pago");
			jPanel5.add(jLformapago);
			jPanel5.add(getJTformapago(), null);
			//jPanel5.add(getJButtonformapago(), null);
			jPanel5.add(jLentregado, null);
			jPanel5.add(getJTentregado(), null);
			jPanel5.add(jLpendienteTotal, null);
			jPanel5.add(getJTpendiente2(), null);
			jLobservaciones = new JLabel();
			jLobservaciones.setBounds(new java.awt.Rectangle(16,44,119,16));
			jLobservaciones.setText("Observaciones");
			jPanel5.add(jLobservaciones);
			jPanel5.add(getJTobservaciones());
			jPanel5.add(getImprimir(), null);
			//jPanel5.add(getJBprevista(), null);
			jPanel5.add(jLfechaCobro, null);
			jPanel5.add(getJTfechaCobro(), null);
		}
		return jPanel5;
	}

	/**
	 * This method initializes jTentregado	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTentregado() {
		if (jTentregado == null) {
			jTentregado = new JTextField();
			jTentregado.setBounds(new java.awt.Rectangle(339,42,106,21));
			jTentregado.setEditable(false);
		}
		return jTentregado;
	}

	/**
	 * This method initializes jTpendiente2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTpendiente2() {
		if (jTpendiente2 == null) {
			jTpendiente2 = new JTextField();
			jTpendiente2.setBounds(new java.awt.Rectangle(337,66,107,22));
			jTpendiente2.setEditable(false);
		}
		return jTpendiente2;
	}

	/**
	 * This method initializes jTfechaCobro	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTfechaCobro() {
		if (jTfechaCobro == null) {
			jTfechaCobro = new JTextField();
			jTfechaCobro.setBounds(new java.awt.Rectangle(314,21,130,19));
			jTfechaCobro.setEditable(false);
		}
		return jTfechaCobro;
	}
	

}
