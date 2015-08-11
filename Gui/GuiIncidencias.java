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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import log.LogWriter;
import system.Cargador;
import ERP.ClienteErp;
import ERP.IncidenciaErp;
import Gui.system.InfoPane;
import Gui.system.QueryPane;
import bbdd.FrameTableExtendida;
import bbdd.InternalFrameTable;
import data.Cliente;
import data.Incidencia;

 public class GuiIncidencias  extends GuiInternalFrame{

//	Declaracion de Botones	
	private JButton eliminar = null;
	private JButton validar = null;
	private JButton cerrar = null;

//  Declaracion de jpanel
	private JPanel panel = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JPanel jPanel1 = null;
	
//  Declaracion jScrollPane	
	private JScrollPane jScrollPane = null;
	private JSplitPane jSplitPane = null;
	
	
	private JTabbedPane jTabbedPane = null;
	
	private JLabel jLcodigo = null;
	private JLabel jLfechalta = null;
	private JLabel jLrazoncli = null;
	private JLabel jLtipoincidencia = null;
	private JLabel jLobsevacionesincidencia = null;
	private JLabel jLnombrecli = null;
	private JLabel jLcodigoagente = null;
	private JLabel jLcodigocli = null;
	
	private JTextField nombreAgente = null;
	private JTextField jTfechaalta = null;
	private JTextField jTrazoncli = null;
	private JTextField jTtipoincidencia = null;
	private JTextField jTnombrecli = null;
	JTextField jTcodigoagente = null;
	private JTextField jTcodigocli = null;
	int numeroIncidencia;
	
//  Contador totales	
	private JLabel jLabel = null;
	private JScrollPane jScrollPane1 = null;
	private JTextPane observaciones = null;

// Bases de datos
	protected String condicionWhere;
	
// Identificador de incidencia
	protected String codInci = null;
	
//	==================================================================
	public GuiIncidencias( Cargador cargador ) 
//	==================================================================
	{
		super( cargador );
		icono = "iconIncidencias.gif";
		toolTip = "Muestra los incidencias Importadas";
		titulo = "Incidencias";
	}
	
//	==================================================================
	protected JPanel getPanel() 
//	==================================================================
	{
		if (panel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(14,403,271,28));
			jLabel.setText("JLabel");
			panel = new JPanel();
			panel.setLayout(null);
			panel.setSize(new java.awt.Dimension(805,446));
			panel.add(getJSplitPane(), null);
			panel.add(getEliminar(), null);
			panel.add(getValidar(), null);
			panel.add(getCerrar(), null);
			panel.add( jLabel, null);
			filasImportadas = jLabel;
			filasImportadas.setText("Numero de Nuevas Incidencias : "+model.numeroFilas);
		}
		frame.setBounds(new java.awt.Rectangle(50,50,790,480));
		return panel;
	}
//	===========================================================================================
	protected JScrollPane getJScrollPane() 
//	===========================================================================================
	{
		model = new FrameTableExtendida( this, new String[]{"Cliente"}, 1 );
		//Configuracion de la tabla
		model.setPrimaryKeysFormateadas( new String[]{"codigoCliente","numero"} );
		model.setPrimaryKeys( new String[]{"a.cod_cliente","a.numero"} );
		//ver como incluir el nombre del cliente
		model.setColumnas( new String[]{"a.numero","a.fecha"} ); //,"b.nombre"} );
		model.setColumnasFormateadas(  new String[]{"Numero","Fecha"}); //,"Cliente"} );
		model.setAnchoColumnas( new int[]{40,145}); //,300} );
		model.setDatabase("incidencias a"); //,dpersonales b");
		model.setOrder("fecha");
		model.setVerificado(new String[]{"a.verificado","verificado"});

		jScrollPane = new JScrollPane( model.getTabla() );
		model.tabla.setAutoResizeMode(model.tabla.AUTO_RESIZE_ALL_COLUMNS);
		/* Capturar tambien el campo 'Verificado' y si es NULL o valor 'NO'
		 * establecemos un color diferenciador en su linea */

		jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setBounds(new java.awt.Rectangle(0,45,285,390));
		return jScrollPane;
	}
	
	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
//	===========================================================================================
	private JSplitPane getJSplitPane()
//	===========================================================================================
	{
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new java.awt.Rectangle(3,2,794,386));
			jSplitPane.setDividerLocation(300);
			jSplitPane.setDividerSize(5);
			jSplitPane.setRightComponent(getJTabbedPane());
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(java.beans.PropertyChangeEvent e) {
					if ((e.getPropertyName().equals("lastDividerLocation"))) {
						model.tabla.setAutoResizeMode( JTable.AUTO_RESIZE_ALL_COLUMNS );
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
//	===========================================================================================
	private JPanel getJPanel1()
//	===========================================================================================
	{
		if (jPanel1 == null) {
			jLcodigocli = new JLabel();
			jLcodigocli.setText("Codigo:");
			jLcodigocli.setSize(new java.awt.Dimension(58,19));
			jLcodigocli.setLocation(new java.awt.Point(129,71));
			jLcodigoagente = new JLabel();
			jLcodigoagente.setText("Codigo:");
			jLcodigoagente.setBounds(new java.awt.Rectangle(82,21,49,19));
			jLnombrecli = new JLabel();
			jLnombrecli.setText("Nombre Cliente");
			jLnombrecli.setBounds(new java.awt.Rectangle(20,71,96,19));
			jLobsevacionesincidencia = new JLabel();
			jLobsevacionesincidencia.setText("Observaciones");
			jLobsevacionesincidencia.setBounds(new java.awt.Rectangle(20,217,98,19));
			jLtipoincidencia = new JLabel();
			jLtipoincidencia.setText("Tipo");
			jLtipoincidencia.setBounds(new java.awt.Rectangle(20,159,51,19));
			jLrazoncli = new JLabel();
			jLrazoncli.setText("Razón Comercial");
			jLrazoncli.setBounds(new java.awt.Rectangle(20,115,103,19));
			jLfechalta = new JLabel();
			jLfechalta.setText("F.Creación");
			jLfechalta.setBounds(new java.awt.Rectangle(255,21,66,19));
			jLcodigo = new JLabel();
			jLcodigo.setText("Agente");
			jLcodigo.setSize(new java.awt.Dimension(51,19));
			jLcodigo.setLocation(new java.awt.Point(20,21));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new java.awt.Dimension(90,150));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			jPanel1.add(jLcodigo, null);
			jPanel1.add(jLfechalta, null);
			jPanel1.add(jLrazoncli, null);
			jPanel1.add(jLtipoincidencia, null);
			jPanel1.add(jLobsevacionesincidencia, null);
			jPanel1.add(getNombreAgente(), null);
			jPanel1.add(getJTfechaalta(), null);
			jPanel1.add(getJTrazoncli(), null);
			jPanel1.add(getJTtipoincidencia(), null);
			jPanel1.add(jLnombrecli, null);
			jPanel1.add(getJTnombrecli(), null);
			jPanel1.add(jLcodigoagente, null);
			jPanel1.add(getJTcodigoagente(), null);
			jPanel1.add(jLcodigocli, null);
			jPanel1.add(getJTcodigocli(), null);
			jPanel1.add(getJScrollPane1(), null);
		}
		return jPanel1;
	}
	
	/**
	 * This method initializes eliminar	
	 * 	
	 * @return javax.swing.JButton	
	 */
//	===========================================================================================
	private JButton getEliminar()
//	===========================================================================================
	{
		if (eliminar == null) {
			eliminar = new JButton();
			eliminar.setText("Eliminar");
			eliminar.setLocation(new java.awt.Point(373,401));
			eliminar.setSize(new java.awt.Dimension(100,26));
			eliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(model.tabla.getSelectedRow()!=-1){
						int n = new QueryPane().consultar( "Desea eliminar (S/N)" );
						if ( n == 0 ){
							try {
								String query = "DELETE FROM incidencias WHERE ";
								query += cortarCondicion(condicionWhere);
								System.out.println( query );
								Statement sta;
								sta = conect.createStatement();
								sta.executeUpdate( query );
								sta.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
								new LogWriter( e1.getStackTrace() );
							}
							cargador.actualizarVentanas();
						}
					}else{
						new InfoPane("ATENCIÓN","Seleccione una Incidencia");
					}
					
				}
			});
		}
		return eliminar;
	}
	
//	===========================================================================================
	public void mostrarDatos(String where )
//	===========================================================================================
	{
		Statement sta;
		
		condicionWhere = where;
		int codigoCliente = 0;

		try {
			sta = this.conect.createStatement();
			String query = "SELECT a.cod_cliente FROM incidencias a WHERE " + condicionWhere;
			System.out.println(query);
			ResultSet rs = sta.executeQuery(query);
			if (rs.next()){
				codigoCliente = rs.getInt("cod_cliente");
			}
			Cliente cliErp = new ClienteErp();
			Statement stErp = sys.getConexionErp().createStatement();
			// Buscamos el Cliente de la Incidencia en la base de datos ERP si no está en nuestra base de datos
			ResultSet rsErp = stErp.executeQuery( cliErp.getQueryClienteByCodigo( Integer.toString(codigoCliente) ) );
			if (rsErp.next())
				cliErp.setClienteFromErp(rsErp);
			// Buscamos los datos de la incidencia y del agente relacionado en nuestra base de datos
			query = 
					"SELECT " +
					"b.nombre \"nombreAgente\",b.codigo \"codAgente\"," +
					"a.fecha,a.observaciones,a.codigo \"codInci\" " +
					",a.numero " +
					"FROM incidencias a, agentes b " +
					"WHERE " +
					"b.codigo = '" + cliErp.getAgente() + "' AND " +
					where;
			System.out.println(query);
			ResultSet resul = sta.executeQuery( query );
			Incidencia inciErp = new IncidenciaErp();
			// Mostramos los datos por pantalla
			if( resul.next() ){
				this.jTcodigoagente.setText(resul.getString("codAgente"));
				this.jTfechaalta.setText(resul.getString("fecha"));
				this.nombreAgente.setText(resul.getString("nombreAgente"));
				this.jTcodigocli.setText(Integer.toString(codigoCliente));
				this.jTnombrecli.setText(cliErp.getNombre());
				this.jTrazoncli.setText(cliErp.getRazon());
				this.jTtipoincidencia.setText(inciErp.getDescripcionByCodigo(resul.getString("codInci"),stErp));
				this.observaciones.setText(resul.getString("observaciones"));
				this.codInci = resul.getString("codInci");
				this.numeroIncidencia = resul.getInt("numero");
				// activamos o desactivamos el botón de validar dependiendo del valor de su campo
				if ( "S".equals( model.getValorVectorVerificados( model.tabla.getSelectedRow() ) ) ){
					this.validar.setEnabled( true );
				}else{
					this.validar.setEnabled( false );
				}
				resul.close();
				sta.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	/** metodo para eliminar el X. de la sentencia X.CAMPO en una condicion */
//	===========================================================================================
	public String cortarCondicion(String str)
//	===========================================================================================
	{
		int i = str.indexOf('.');
		str = str.substring(i+1);
		return str;	
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
			validar.setLocation(new java.awt.Point(493,401));
			validar.setSize(new java.awt.Dimension(100,26));
			validar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (model.tabla.getSelectedRow() != -1) {
						try{
							int n = new QueryPane().consultar( "Desea validar (S/N)" );
							if ( n == 0) {
								Statement s = conect.createStatement();
								Statement stErp = sys.getConexionErp().createStatement();
								Incidencia inciErp = new IncidenciaErp();
								// Comprueba el cliente de la incidencia
								int cod = Cliente.esValido(jTcodigocli.getText(),s,stErp);
								
								switch (cod){
								
								case 1:		// El Cliente existe en la base de datos ERP: está validado
									ResultSet rs = stErp.executeQuery(inciErp.getSelectCount());
									int numero = 0;
									if (rs.next()){
										numero = rs.getInt("count(*)"); 
									}
									numero ++;
									// Buscamos la incidencia seleccionada
									String query = inciErp.getQueryIncidenciaByNumero(numeroIncidencia);
									System.out.println(query);
									rs = s.executeQuery( query );
									// Creamos una instancia de tipo incidencia con la incidencia seleccionada
									if(rs.next()){
										inciErp.setIncidencia(rs);
									}
									// Insertamos la incidencia en la base de datos ERP
									inciErp.insertarIncidenciaERP(stErp,numero,jTcodigoagente.getText());
									stErp.close();
									// Validamos la incidencia en nuestra base de datos
									query = "UPDATE incidencias SET verificado='V' WHERE numero='" + numeroIncidencia + "'";
									System.out.println( query );
									s.executeUpdate(query);
									s.close();
									break;
									
								case 2:		// El Cliente existe sólo en nuestra base de datos local
									new InfoPane("ATENCION","Debe validar antes el cliente de la incidencia");
									break;
									
								case 3:		// El Cliente no existe
									new InfoPane("ATENCION","El cliente asociado a la incidencia no existe");
									break;
								}
							}
						}catch(SQLException ex) {
							ex.printStackTrace();
							new LogWriter( ex.getStackTrace() );
							new InfoPane("ERROR","Se ha producido un Error al validar la Incidencia");
						}catch(Exception ex1){
							ex1.printStackTrace();
							new LogWriter( ex1.getStackTrace() );
							new InfoPane("ERROR","Se ha producido un Error al validar la Incidencia");
						}
						cargador.actualizarVentanas();
					}else{
						new InfoPane("ATENCIÓN","Seleccione una Incidencia");
					}
				}
			});
		}
		return validar;
	}
	
	/**
	 * This method initializes cerrar	
	 * 	
	 * @return javax.swing.JButton	
	 */
//	===========================================================================================
	private JButton getCerrar()
//	===========================================================================================
	{
		if (cerrar == null) {
			cerrar = new JButton();
			cerrar.setText("Cerrar");
			cerrar.setLocation(new java.awt.Point(613,401));
			cerrar.setSize(new java.awt.Dimension(100,26));
			cerrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cerrar();
				}
			});
		}
		return cerrar;
	}
	
	/**
	 * This method initializes jTcodigo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
//	===========================================================================================
	private JTextField getNombreAgente()
//	===========================================================================================
	{
		if (nombreAgente == null) {
			nombreAgente = new JTextField();
			nombreAgente.setBounds(new java.awt.Rectangle(20,43,418,19));
			nombreAgente.setEditable(false);
		}
		return nombreAgente;
	}
	
	/**
	 * This method initializes jTfechaalta	
	 * 	
	 * @return javax.swing.JTextField	
	 */
//	===========================================================================================
	private JTextField getJTfechaalta()
//	===========================================================================================
	{
		if (jTfechaalta == null) {
			jTfechaalta = new JTextField();
			jTfechaalta.setBounds(new java.awt.Rectangle(324,21,113,19));
			jTfechaalta.setEditable(false);
		}
		return jTfechaalta;
	}
	
	/**
	 * This method initializes jTcodigo2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
//	===========================================================================================
	private JTextField getJTrazoncli()
//	===========================================================================================
	{
		if (jTrazoncli == null) {
			jTrazoncli = new JTextField();
			jTrazoncli.setBounds(new java.awt.Rectangle(20,137,418,19));
			jTrazoncli.setEditable(false);
		}
		return jTrazoncli;
	}
	
	/**
	 * This method initializes jTnombre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
//	===========================================================================================
	private JTextField getJTtipoincidencia()
//	===========================================================================================
	{
		if (jTtipoincidencia == null) {
			jTtipoincidencia = new JTextField();
			jTtipoincidencia.setBounds(new java.awt.Rectangle(74,159,363,19));
			jTtipoincidencia.setEditable(false);
		}
		return jTtipoincidencia;
	}
	
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
//	===========================================================================================
	private JTabbedPane getJTabbedPane()
//	===========================================================================================
	{
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new java.awt.Rectangle(-1,1,488,381));
			jTabbedPane.addTab("Datos Incidencias",this.getJPanel1());
		}
		return jTabbedPane;
	}
	
	/**
	 * This method initializes jTnombrecli	
	 * 	
	 * @return javax.swing.JTextField	
	 */
//	===========================================================================================
	private JTextField getJTnombrecli()
//	===========================================================================================
	{
		if (jTnombrecli == null) {
			jTnombrecli = new JTextField();
			jTnombrecli.setBounds(new java.awt.Rectangle(20,93,418,19));
			jTnombrecli.setEditable(false);
		}
		return jTnombrecli;
	}
	
	/**
	 * This method initializes jTcodigoagente	
	 * 	
	 * @return javax.swing.JTextField	
	 */
//	===========================================================================================
	private JTextField getJTcodigoagente()
//	===========================================================================================
	{
		if (jTcodigoagente == null) {
			jTcodigoagente = new JTextField();
			jTcodigoagente.setLocation(new java.awt.Point(136,21));
			jTcodigoagente.setSize(new java.awt.Dimension(114,19));
			jTcodigoagente.setEditable(false);
		}
		return jTcodigoagente;
	}
	
	/**
	 * This method initializes jTcodigocli	
	 * 	
	 * @return javax.swing.JTextField	
	 */
//	===========================================================================================
	private JTextField getJTcodigocli()
//	===========================================================================================
	{
		if (jTcodigocli == null) {
			jTcodigocli = new JTextField();
			jTcodigocli.setLocation(new java.awt.Point(191,71));
			jTcodigocli.setSize(new java.awt.Dimension(114,19));
			jTcodigocli.setEditable(false);
		}
		return jTcodigocli;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
//	===========================================================================================
	private JScrollPane getJScrollPane1()
//	===========================================================================================
	{
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new java.awt.Rectangle(20,242,446,98));
			jScrollPane1.setViewportView( getObservaciones() );
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
//	===========================================================================================
	private JTextPane getObservaciones()
//	===========================================================================================
	{
		if (observaciones == null) {
			observaciones = new JTextPane();
			observaciones.setEditable(false);
		}
		return observaciones;
	}
	
}  //  @jve:decl-index=0:visual-constraint="2,21"


