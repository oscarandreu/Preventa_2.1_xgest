package Gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import log.LogWriter;
import system.Cargador;
import Gui.system.InfoPane;
import bbdd.FrameTableExtendida;

/**
 * es la gui de gestion del servidor sonde se le meteria la direccion del servidor
 * @author Elena
 *
 */

 public class GuiEventos  extends GuiInternalFrame{

//	Declaracion de Botones	
	private JButton eliminar = null;
	private JButton mostrar = null;
	private JButton cerrar = null;

//  Declaracion de jpanel
	private JPanel panel = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	//  Contador totales	
	private JLabel jLabel = null;
	private JScrollPane jScrollPane = null;
	
	//tipo de evento seleccionado de la ventana de Cola de Eventos
	private String tipoEventoSeleccionado;
	//numero/codigo del evento (nuevo cliente, nuevo pedido, nueva incidencia) seleccionado
	private String numero;
	
	private JDesktopPane jdtpGuiEventos = null;
	private Cargador cargadorGuiEventos = null;
	
		
//	==================================================================
	public GuiEventos( Cargador cargador, JDesktopPane jdtp ) 
//	==================================================================
	{
		super( cargador );
		icono = "tab_comparativa.gif";
		toolTip = "Muestra los eventos recibidos";
		titulo = "Cola de eventos";
		cargadorGuiEventos = cargador;
		jdtpGuiEventos = jdtp;
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
			panel.setSize(new java.awt.Dimension(644,446));
	//		panel.add(getEliminar(), null);
			panel.add(getMostrar(), null);
			panel.add(getCerrar(), null);
			panel.add( jLabel, null);
			panel.add(getJScrollPane(), null);
			filasImportadas = jLabel;
			filasImportadas.setText("Eventos Recibidos : "+model.numeroFilas);
		}
		frame.setBounds(new java.awt.Rectangle(50,50,790,480));
		return panel;
	}
	
/**
	 * This method initializes eliminar	
	 * 	@deprecated
	 * @return javax.swing.JButton	
	 */
//	==================================================================
	private JButton getEliminar() 
//	==================================================================
	{
		if (eliminar == null) {
			eliminar = new JButton();
			eliminar.setText("Eliminar");
			eliminar.setLocation(new java.awt.Point(295,406));
			eliminar.setSize(new java.awt.Dimension(100,26));
			eliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(model.tabla.getSelectedRow()!=-1){
				/*		System.out.println("Linea:" + model.tabla.getSelectedRow());
						System.out.println("Valor where:" + tipoEventoSeleccionado);
						TableCellEditor tce = model.tabla.getCellEditor(model.tabla.getSelectedRow(),3);
						int val = new Integer ((Integer) tce.getCellEditorValue());
						System.out.println("Valor de cell editor:" + val);
						EliminarFila(jTable.getSelectedRow());
						scroll= new ScrollableTableModel(sys.getConexionPropia(),query);
						jTable.setModel(scroll);
						filasImportadas.setText("Numero de Filas: "+new Integer(scroll.getRowCount()).toString());
						jTable.repaint();
						jTable.revalidate();*/
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
		tipoEventoSeleccionado = where;		
		/*
		Statement sta;
		
		try {
			sta = this.conect.createStatement();
			String query = 
					"SELECT " +
					"b.nombre \"nombreAgente\",b.codigo \"codAgente\"," +
					"a.fecha,a.observaciones," +
					"c.nombre \"nombreCliente\",c.codigo \"codCliente\",c.razon," +
					"d.descripcion " +
					"FROM incidencias a, agentes b, dpersonales c, tipos_incidencias d " +
					"WHERE " +
					"c.codigo = a.cod_cliente AND " +
					"b.codigo = c.agente AND " +
					"d.codigo = a.codigo AND " +
					where;
			
			ResultSet resul = sta.executeQuery( query );

			if( resul.next() ){

					this.jTcodigoagente.setText(resul.getString("codAgente"));
					this.jTfechaalta.setText(resul.getString("fecha"));
					this.nombreAgente.setText(resul.getString("nombreAgente"));
					this.jTcodigocli.setText(resul.getString("codCliente"));
					this.jTcodigoagente.setText(resul.getString("codAgente"));
					this.jTnombrecli.setText(resul.getString("nombreCliente"));
					this.jTrazoncli.setText(resul.getString("razon"));
					this.jTtipoincidencia.setText(resul.getString("descripcion"));
					this.observaciones.setText(resul.getString("observaciones"));
					
					resul.close();
					sta.close();
				}
				
			} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * This method initializes validar	
	 * 	
	 * @return javax.swing.JButton	
	 */
//	==================================================================
	private JButton getMostrar()
//	==================================================================
	{
		if (mostrar == null) {
			mostrar = new JButton();
			mostrar.setText("Mostrar");
			mostrar.setLocation(new java.awt.Point(415,406));
			mostrar.setSize(new java.awt.Dimension(100,26));
			mostrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(model.tabla.getSelectedRow()!=-1){
						// Mostramos la ventana correspondiente al evento seleccionado
						System.out.println("Where eventos:"+ tipoEventoSeleccionado);
						int codigoEvento = formatearTipo(tipoEventoSeleccionado);
						switch (codigoEvento){
						case 1:		// Nuevo cliente
							GuiClientes clientesWindow = new GuiClientes(cargadorGuiEventos,true);
							clientesWindow.initialize(jdtpGuiEventos);
							break;
							
						case 2:		// Nuevo pedido
							GuiPedidos pedidosWindow = new GuiPedidos(cargadorGuiEventos);
							pedidosWindow.initialize(jdtpGuiEventos);
							break;
							
						case 3:		// Nueva incidencia
							GuiIncidencias incidenciasWindow = new GuiIncidencias(cargadorGuiEventos);
							incidenciasWindow.initialize(jdtpGuiEventos);
							break;
							
						case 4:		// Nuevo cobro
							GuiCobros cobrosWindow = new GuiCobros(cargadorGuiEventos);
							cobrosWindow.initialize(jdtpGuiEventos);
							break;
							
						default:
							break;
						}
						
						//eliminamos los eventos de la cola del tipo seleccionado
						try{
							Connection c = sys.getConexionPropia();
							Statement s = c.createStatement();
							String query = "DELETE FROM COLA_EVENTOS WHERE CODIGO="+codigoEvento ;
							System.out.println(query);
							int rs = s.executeUpdate(query);
							s.close();
							c.close();
						}
						catch (SQLException ex){
							System.out.println("Error al eliminar:"+ex.toString());
							new LogWriter( ex.getStackTrace() );
						}
						
						//refrescamos la tabla
						actualizar();
						
						//volvemos a calcular el numero de eventos en cola
						try{
							Connection c = sys.getConexionPropia();
							Statement s = c.createStatement();
							String query = "SELECT count(*) FROM cola_eventos";
							ResultSet rs = s.executeQuery( query );
							while (rs.next()){
								int numEve = rs.getInt("count");
								System.out.println("Eventos en cola: " + numEve);
								cargador.setContadorEventosCargador( numEve );
								if (numEve == 0){
									mostrar.setEnabled(false);
								}
							}
							s.close();
							c.close();
						}
						catch (SQLException ex){
							System.out.println("Error al contabilizar: "+ex.toString());
							new LogWriter( ex.getStackTrace() );
						}
						// Cerramos la ventana de Eventos
						cerrar();
					}else{
						new InfoPane("ATENCIÓN","Seleccione un Evento");
					}
				}
			});
		}
		return mostrar;
	}

	/** Método que nos devuelve el tipo del evento seleccionado */
//	==================================================================
	public int formatearTipo(String t)
//	==================================================================
	{
		int index = t.indexOf("codigo");
		String temp = t.substring(index,t.length());
		for(int i=0;i<temp.length();i++){
			if (temp.charAt(i)>='0' && temp.charAt(i)<='9'){
				switch (temp.charAt(i)){
				case '1':
					return 1;
				case '2': 
					return 2;
				case '3': 
					return 3;
				case '4': 
					return 4;
				default:
					break;
				}
			}
		}
		return -1;
	}

	/**
	 * This method initializes cerrar	
	 * 	
	 * @return javax.swing.JButton	
	 */
//	==================================================================
	private JButton getCerrar() 
//	==================================================================
	{
		if (cerrar == null) {
			cerrar = new JButton();
			cerrar.setText("Cerrar");
			cerrar.setLocation(new java.awt.Point(535,406));
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
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
//	==================================================================
	private JScrollPane getJScrollPane() 
//	==================================================================
	{
		model = new FrameTableExtendida( this, new String[]{"Cliente"}, 1 );
		//Configuracion de la tabla
		model.setPrimaryKeysFormateadas( new String[]{"codigoCliente","codigo"} );
		model.setPrimaryKeys( new String[]{"a.cliente","b.codigo"} );
		model.setColumnas( new String[]{"b.descripcion","c.nombre","a.numero"} ); //,"d.nombre"} );
		model.setColumnasFormateadas(  new String[]{"Tipo","Agente","Numero"} ); //,"Cliente"} );
		model.setAnchoColumnas( new int[]{100,200,80} ); //,200} );
		model.setDatabase("cola_eventos a, tipo_mensaje b, agentes c"); //, dpersonales d");
		model.setWhere("a.codigo = b.codigo AND c.codigo = a.extras"); // AND d.codigo = a.cliente");
		
		jScrollPane = new JScrollPane( model.getTabla() );
		model.tabla.setAutoResizeMode(model.tabla.AUTO_RESIZE_ALL_COLUMNS);
		jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane.setBounds(new java.awt.Rectangle(20,20,729,331));
		return jScrollPane;
	}
	
}  //  @jve:decl-index=0:visual-constraint="2,21"


