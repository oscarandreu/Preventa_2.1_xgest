package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import system.Cargador;
import system.SysData;
import ERP.ArticuloErp;
import ERP.ClienteErp;
import ERP.PedidoErp;
import Gui.system.InfoPane;
import Gui.system.PopupMenu;
import Gui.system.QueryPane;
import bbdd.FrameTableExtendida;
import data.Articulo;
import data.CabeceraPedido;
import data.Cliente;

public class GuiFacturas extends GuiInternalFrame {
	private JPanel panel = null; // @jve:decl-index=0:visual-constraint="10,10"

	private JScrollPane facturas = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	private JButton eliminar = null;

	private JButton modificar = null;

	private JButton cerrar = null;

	private JLabel jLFecha = null;

	private JLabel jLnumerofactura = null;

	private JLabel jLobservaciones = null;

	JTextField jTFecha = null;

	JTextField jTnumerofactura = null;

	JTextField jTobservaciones = null;

	private JLabel jLdto1 = null;

	private JLabel jLtotalneto = null;

	private JLabel jLtotaliva = null;

	private JLabel jLdto2 = null;

	private JLabel jLdtopp = null;

	private JLabel jLtotal = null;

	JTextField jTdto1 = null;

	private JTextField jTtotalneto = null;

	JTextField jTtotaliva = null;

	JTextField jTdto2 = null;

	JTextField jTdtopp = null;

	private JTextField jTtotal = null;

	JTabbedPane jTabbedPane = null;

	private JLabel jLtiva = null;

	private JTextField jTtiva = null;

	private JSplitPane jPanel3 = null;

	private JLabel jLabel = null;

	JCheckBox jCheckBox = null;

	private JLabel jLabel10 = null;

	JComboBox comboClase = null;

	protected GuiLineasPedidos lineas;

	private JLabel jLabel1 = null;

	JTextField serie = null;

	JButton validar = null;

	String condicionWhere;

	protected String query;

	private JPanel jPanel = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel2 = null;

	JTextField jTcodigo = null;

	private JLabel jLabel3 = null;

	private JTextField jTnombre = null;

	private JLabel jLFechaVto = null;

	JTextField jTFechaVto = null;

	private JLabel jLformapago = null;

	private JTextField jTformapago = null;

	private JButton jButtonformapago = null;

	private JTextField jTimporteCobrado = null;

	private JLabel jLimporteCobrado = null;

// ==================================================================
	public GuiFacturas(Cargador cargador)
// ==================================================================
	{
		super(cargador);
		icono = "tab_comparativa.gif";
		toolTip = "Facturas realizadas";
		titulo = "Facturas";
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
			filasImportadas.setText("Numero de Nuevas Facturas : "
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
		model.setPrimaryKeys(new String[] { "CODIGO_CLIENTE","NUMERO_CABECERA", "SERIE" });
		model.setColumnas(new String[] { "a.NUMERO_CABECERA", "a.SERIE" });
		//,"b.nombre" });
		model.setColumnasFormateadas(new String[] { "Número", "Serie" }); 
		//,"Cliente" });
		model.setAnchoColumnas(new int[] { 40, 40 }); //, 200 });
		model.setDatabase("cabecera_pedido a"); //,dpersonales b");
		model.setWhere("a.verificado = 'S'"); //a.Codigo_cliente=b.codigo AND a.verificado='S'");
		model.setVerificado(new String[] { "a.verificado", "verificado" });
		// model.setOrder("numero_cabecera");
		facturas = new JScrollPane(model.getTabla());
		model.tabla.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
		facturas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		facturas.setBounds(new java.awt.Rectangle(0, 0, 279, 390));
		return facturas;
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
					+ "NUMERO_CABECERA,SERIE,ALBARAN,Clase,Codigo_cliente,"
					+ "Dto_1,Dto_2,Dto_pp,FECHA,observaciones,TOTAL_IVA "
					+ "FROM cabecera_pedido "
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
				this.jTFecha.setText(""+resul.getDate("fecha"));
				this.jTFechaVto.setText(""+resul.getDate("fecha"));
				this.jTnumerofactura.setText(resul.getString("numero_cabecera"));
				this.jTobservaciones.setText(resul.getString("observaciones"));
				this.serie.setText(resul.getString("serie"));
				this.jTdto1.setText(resul.getString("dto_1"));
				this.jTdto2.setText(resul.getString("dto_2"));
				this.jTdtopp.setText(resul.getString("dto_pp"));
				this.jTcodigo.setText(codigoCliente);
				this.jTnombre.setText(cliErp.getNombre());
				if ("S".equals(resul.getString("albaran"))) {
					jCheckBox.setSelected(true);
				}
				if( "N".equals( resul.getString("clase") ) ){
					comboClase.setSelectedIndex(1);
				}else{
					comboClase.setSelectedIndex(0);
				}
				
				lineas.actualizar(" NUMERO_CABECERA ='"
						+ resul.getString("numero_cabecera") + "'"
						+ " AND SERIE = '" + resul.getString("serie") + "'");
				
				jTtotal.setText(resul.getString("total_iva"));
				Double totalConIva = new Double(jTtotal.getText());
				if (totalConIva == 0) {
					jTtotaliva.setText("0");
					jTtotalneto.setText("0");
					
				}else{
					
					/* calculamos el total neto (ARG == 0) de todas las lineas de pedido asociadas a la cabecera 
					 * recorriendo todas las lineas de pedido de la BDD asociadas a la cabecera */
					//double tempNet =  calcularTotal( 0,jTnumeropedido.getText(),serie.getText() );
					double tempNet =  calcularTotalNeto( jTnumerofactura.getText(),serie.getText() );
					System.out.println("Total cabecera: " + tempNet);
					jTtotalneto.setText( Double.toString(Math.rint(tempNet*100)/100) );
					Double totalNeto = new Double(jTtotalneto.getText());
					PedidoErp.setTotalNeto(totalNeto);
					
					Double totalIva = new Double(totalConIva - totalNeto);
					jTtotaliva.setText( Double.toString(Math.rint(totalIva*100)/100) );
				}
				
				/* calculamos el total del iva (ARG == 1) de todas las lineas de pedido asociadas a la cabecera
				 * recorriendo todas las lineas de pedido de la BDD asociadas a la cabecera 
				double tempIva = calcularTotal( 1,jTnumeropedido.getText(),serie.getText() );
				System.out.println("Total cabecera con iva:" + tempIva);
				jTtotaliva.setText( Double.toString(Math.rint(tempIva*100)/100) );
				*/
				System.out.println("Total global: " + new Double(jTtotalneto.getText()).toString() + " + " 
						+ new Double(jTtotaliva.getText()).toString() + " = "
						);
				//Double total = new Double(jTtotalneto.getText()) + new Double(jTtotaliva.getText());
				//jTtotal.setText( sys.formatearDouble2Dec( Double.toString(tempNet + tempIva) ) );
				//jTtotal.setText(resul.getString("total_iva"));
				
				// activamos o desactivamos el botón de validar dependiendo del
				// valor de su campo
				if ("S".equals(model.getValorVectorVerificados(model.tabla
						.getSelectedRow()))) {
					this.validar.setEnabled(true);
				} else {
					this.validar.setEnabled(false);
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
			jLimporteCobrado = new JLabel();
			jLimporteCobrado.setBounds(new java.awt.Rectangle(209,259,126,21));
			jLimporteCobrado.setText("IMPORTE COBRADO :");
			jLformapago = new JLabel();
			jLformapago.setBounds(new java.awt.Rectangle(17,189,73,18));
			jLformapago.setText("Forma Pago");
			jLFechaVto = new JLabel();
			jLFechaVto.setBounds(new java.awt.Rectangle(235,45,84,20));
			jLFechaVto.setText("Fecha Vencim.");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(111,224,42,18));
			jLabel1.setToolTipText("");
			jLabel1.setText("Serie");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(18,224,36,17));
			jLabel10.setText("Clase");
			jLtiva = new JLabel();
			jLtiva.setBounds(new java.awt.Rectangle(340,158,45,19));
			jLtiva.setText("T.IVA%");
			jLtotal = new JLabel();
			jLtotal.setText("IMPORTE TOTAL :");
			jLtotal.setBounds(new java.awt.Rectangle(229,235,106,19));
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
			jLobservaciones = new JLabel();
			jLobservaciones.setBounds(new java.awt.Rectangle(13,59,119,16));
			jLobservaciones.setText("Observaciones");
			jLnumerofactura = new JLabel();
			jLnumerofactura.setText("Numero Factura");
			jLnumerofactura.setBounds(new java.awt.Rectangle(13,19,95,19));
			jLFecha = new JLabel();
			jLFecha.setText("Fecha");
			jLFecha.setSize(new java.awt.Dimension(39,19));
			jLFecha.setLocation(new java.awt.Point(282,18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new java.awt.Dimension(90, 150));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Factura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			jPanel1.setBounds(new java.awt.Rectangle(2,83,451,301));
			jPanel1.add(jLFecha, null);
			jPanel1.add(jLnumerofactura, null);
			jPanel1.add(jLobservaciones, null);
			jPanel1.add(jLdto1, null);
			jPanel1.add(jLtotalneto, null);
			jPanel1.add(jLtotaliva, null);
			jPanel1.add(jLdto2, null);
			jPanel1.add(jLdtopp, null);
			jPanel1.add(jLtotal, null);
			jPanel1.add(jLtiva, null);
			jPanel1.add(getJTFecha(), null);
			jPanel1.add(getJTnumerofactura(), null);
			jPanel1.add(getJTobservaciones(), null);
			jPanel1.add(getJTdto1(), null);
			jPanel1.add(getJTtotalneto(), null);
			jPanel1.add(getJTtotaliva(), null);
			jPanel1.add(getJTdto2(), null);
			jPanel1.add(getJTdtopp(), null);
			jPanel1.add(getJTtotal(), null);
			jPanel1.add(getJTtiva(), null);
			jPanel1.add(getJCheckBox(), null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(getComboClase(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getSerie(), null);
			jPanel1.add(getValidar(), null);
			jPanel1.add(jLFechaVto, null);
			jPanel1.add(getJTFechaVto(), null);
			jPanel1.add(jLformapago, null);
			jPanel1.add(getJTformapago(), null);
			jPanel1.add(getJButtonformapago(), null);
			jPanel1.add(getJTimporteCobrado(), null);
			jPanel1.add(jLimporteCobrado, null);
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
							
							System.out.println("Eliminando factura");
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
									validar.setEnabled(false);
								}
							}
						}
						//La pestaña seleccionada es la de lineas de pedidos
						else{
							System.out.println("Eliminando linea");
							lineas.eliminarLineaPedido( cargador );
						}
					}else{
						new InfoPane("ATENCIÓN","Seleccione una Factura");
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
										" AND numero_cabecera='" + jTnumerofactura.getText() +"'" +
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
	private JButton getValidar() {
		if (validar == null) {
			validar = new JButton();
			validar.setText("Validar");
			validar.setBounds(new java.awt.Rectangle(51,252,124,35));
			validar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (model.tabla.getSelectedRow() != -1) {
						try {
							int n = new QueryPane().consultar("Desea validar (S/N)");
							if (n == 0) {
								
								Statement s = sys.getConexionPropia().createStatement();
								Statement st = sys.getConexionErp().createStatement();
								
								switch ( Cliente.esValido(jTcodigo.getText(),s,st)){
								case 1:
									CabeceraPedido cabErp = new PedidoErp();
									cabErp.insertarPedidoERP( s,st,serie.getText(),SysData.formatYearToFirebird(jTFecha.getText()),jTnumerofactura.getText(),jTcodigo.getText());
									s.close();
									st.close();
									break;
								case 2:
									new InfoPane("ATENCION","El Cliente no esta validado");
									break;
								case 3:
									new InfoPane("ATENCION","El Cliente no existe");
									break;
								}
							}
						} catch (SQLException ex) {
							ex.printStackTrace();
							new LogWriter( ex.getStackTrace() );
							new InfoPane("ERROR","Se ha producido un ERROR al Validar la Factura");
						}
						cargador.actualizarVentanas();
					}
				}
			});
		}

		return validar;
	}

	private JTextField getJTFecha() {
		if (jTFecha == null) {
			jTFecha = new JTextField();
			jTFecha.setLocation(new java.awt.Point(320,18));
			jTFecha.setSize(new java.awt.Dimension(111,19));
			jTFecha.setEditable(false);
		}
		return jTFecha;
	}

	private JTextField getJTnumerofactura() {
		if (jTnumerofactura == null) {
			jTnumerofactura = new JTextField();
			jTnumerofactura.setBounds(new java.awt.Rectangle(112,19,123,19));
			jTnumerofactura.setEditable(false);
		}
		return jTnumerofactura;
	}

	private JTextField getJTobservaciones() {
		if (jTobservaciones == null) {
			jTobservaciones = new JTextField();
			jTobservaciones.setEditable(false);
			jTobservaciones.setBounds(new java.awt.Rectangle(13,79,427,70));
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
			jTtotal.setBounds(new java.awt.Rectangle(339,235,100,19));
			jTtotal.setEditable(false);
		}
		return jTtotal;
	}

	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new java.awt.Rectangle(21, 14, 459, 393));
			jTabbedPane.addTab("Cabecera de la factura", null, getJPanel() );
			jTabbedPane.addTab("Lineas de la factura", null, getJPanel3());
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
			comboClase.setBounds(new java.awt.Rectangle(54,224,52,17));
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
			serie.setBounds(new java.awt.Rectangle(156,224,54,19));
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
			jTFechaVto.setBounds(new java.awt.Rectangle(321,45,110,20));
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
			jTformapago.setBounds(new java.awt.Rectangle(94,189,131,18));
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
			jButtonformapago.setBounds(new java.awt.Rectangle(227,187,21,21));
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
			jTimporteCobrado.setBounds(new java.awt.Rectangle(339,259,100,19));
			jTimporteCobrado.setEditable(false);
		}
		return jTimporteCobrado;
	}
	

}
