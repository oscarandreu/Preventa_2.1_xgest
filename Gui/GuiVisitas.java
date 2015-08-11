package Gui;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import log.LogWriter;

import system.Cargador;
import system.SysData;
import bbdd.InternalFrameTable;

/**
 * es la gui de gestion del servidor sonde se le meteria la direccion del servidor
 * @author Elena
 *
 */
 public class GuiVisitas extends GuiInternalFrame{
	
	private JPanel panel = null;
	public JInternalFrame frame = null;  //  @jve:decl-index=0:visual-constraint="22,3"
	protected SysData sys;
	private JScrollPane jScrollPane = null;
	private Connection conect=null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private String queryT="select * from dpersonales";
	//private String query="Select nombre,direccion1,direccion2,telefono1 from dpersonales";
	private JButton eliminar = null;
	private JButton validar = null;
	private JButton cerrar = null;
	private JLabel filasImportadas = null;
	private JLabel jLagente = null;
	private JLabel jLfechalta = null;
	private JLabel jLrazoncli = null;
	private JLabel jLrazon = null;
	private JTextField jTagente = null;
	private JTextField jTfechaalta = null;
	private JTextField jTcodigo2 = null;
	private JTextField jTrazon = null;
	private JPanel jPanel2 = null;
	private JTabbedPane jTabbedPane = null;
	private JLabel jLnombrecli = null;
	private JTextField jTnombrecli = null;
	private JLabel jLcodigocli = null;
	private JTextField jTcodigocli = null;
	private JLabel jLcodigoagente = null;
	private JTextField jTcodigoagentes = null;
	private JLabel jLhoravisita = null;
	private JTextField jThoravisita = null;
	

//	==================================================================
	public GuiVisitas( Cargador cargador ) 
//	==================================================================
	{
		super( cargador );
		icono = "tab_comparativa.gif";
		toolTip = "Muestra las rutas importadas";
		titulo = "Rutas";
	}
//	==================================================================
	public JInternalFrame initialize( JDesktopPane jDesktopPane ) 
//	==================================================================
	{
		frame = new JInternalFrame();
		
		jDesktopPane.add( frame );
		try {
			frame.setSelected(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
			new LogWriter( e1.getStackTrace() );
		}
		
		frame.setClosable(true);
		frame.setToolTipText("Configuracióon del servidor web de enlace con las PDA's");
		frame.setTitle("Visitas");
		frame.setResizable(true);
		frame.setIconifiable(true);
		frame.setBounds(new java.awt.Rectangle(50,50,800,437));
		frame.setContentPane(getPanel());
		frame.setFrameIcon(new ImageIcon(getClass().getResource("/img/tab_comparativa.gif")));
		frame.setVisible(true);	
		
		return frame;
	}
	
//	==================================================================
	protected JPanel getPanel() 
//	==================================================================
	{
		if (panel == null) {
			filasImportadas = new JLabel();
			filasImportadas.setBounds(new java.awt.Rectangle(45,365,205,21));
			filasImportadas.setText("");
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getJSplitPane(), null);
			panel.add(getEliminar(), null);
			panel.add(getValidar(), null);
			panel.add(getCerrar(), null);
			panel.add(filasImportadas, null);
			
		}
		return panel;
	}
//	===========================================================================================
	protected JScrollPane getJScrollPane() 
//	===========================================================================================
	{
		model = new InternalFrameTable( this);
		//Configuracion de la tabla
		model.setColumnas( new String[]{"codigo","nombre"} );
		model.setColumnasFormateadas(  new String[]{"Código","Nombre"} );
		model.setAnchoColumnas( new int[]{80,200} );
		model.setDatabase("articulos");
		model.setOrder("codigo");
		jScrollPane = new JScrollPane( //new JTable() );
										model.getTabla() );
		
		jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane.setBounds(new java.awt.Rectangle(0,0,279,390));
		return jScrollPane;
	}
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new java.awt.Rectangle(3,2,786,357));
			jSplitPane.setDividerLocation(290);
			jSplitPane.setDividerSize(5);
			jSplitPane.setRightComponent(getJPanel2());
			jSplitPane.setLeftComponent(getJPanel());
		}
		return jSplitPane;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
		}
		return jPanel;
	}
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLhoravisita = new JLabel();
			jLhoravisita.setText("Hora Visita:");
			jLhoravisita.setBounds(new java.awt.Rectangle(20,159,83,19));
			jLcodigoagente = new JLabel();
			jLcodigoagente.setText("Codigo");
			jLcodigoagente.setSize(new java.awt.Dimension(48,19));
			jLcodigoagente.setLocation(new java.awt.Point(90,21));
			jLcodigocli = new JLabel();
			jLcodigocli.setText("Codigo");
			jLcodigocli.setBounds(new java.awt.Rectangle(145,71,45,19));
			jLnombrecli = new JLabel();
			jLnombrecli.setText("Nombre Cliente");
			jLnombrecli.setBounds(new java.awt.Rectangle(20,71,96,19));
			jLrazon = new JLabel();
			jLrazon.setText("Observaciones");
			jLrazon.setBounds(new java.awt.Rectangle(20,186,98,19));
			jLrazoncli = new JLabel();
			jLrazoncli.setText("Razón Comercial");
			jLrazoncli.setBounds(new java.awt.Rectangle(20,115,103,19));
			jLfechalta = new JLabel();
			jLfechalta.setText("F. Creación");
			jLfechalta.setBounds(new java.awt.Rectangle(243,21,68,19));
			jLagente = new JLabel();
			jLagente.setText("Agente");
			jLagente.setSize(new java.awt.Dimension(51,19));
			jLagente.setLocation(new java.awt.Point(20,21));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new java.awt.Dimension(90,150));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			jPanel1.add(jLagente, null);
			jPanel1.add(jLfechalta, null);
			jPanel1.add(jLrazoncli, null);
			jPanel1.add(jLrazon, null);
			jPanel1.add(getJTagente(), null);
			jPanel1.add(getJTfechaalta(), null);
			jPanel1.add(getJTcodigo2(), null);
			jPanel1.add(getJTrazon(), null);
			jPanel1.add(jLnombrecli, null);
			jPanel1.add(getJTnombrecli(), null);
			jPanel1.add(jLcodigocli, null);
			jPanel1.add(getJTcodigocli(), null);
			jPanel1.add(jLcodigoagente, null);
			jPanel1.add(getJTcodigoagentes(), null);
			jPanel1.add(jLhoravisita, null);
			jPanel1.add(getJThoravisita(), null);
		}
		return jPanel1;
	}
	
	/**
	 * This method initializes eliminar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getEliminar() {
		if (eliminar == null) {
			eliminar = new JButton();
			eliminar.setText("Eliminar");
			eliminar.setLocation(new java.awt.Point(322,365));
			eliminar.setSize(new java.awt.Dimension(100,26));
			eliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
				}
			});
		}
		return eliminar;
	}
	
	public void mostrarTestos(int posicion){
		//posicion--;
		boolean flag=true;
		//ScrollableTableModel scroll1 = new ScrollableTableModel(sys.getConexionPropia(),this.queryT);
		
		Statement sta;
		Statement sta1;
		Statement sta2;
		Statement sta3;
		Statement sta4;
		Statement sta5;
		Statement sta6;
		Statement sta7;
		try {
			sta = this.conect.createStatement();
			sta1 = this.conect.createStatement();
			sta2 = this.conect.createStatement();
			sta3 = this.conect.createStatement();
			sta4 = this.conect.createStatement();
			sta5 = this.conect.createStatement();
			sta6 = this.conect.createStatement();
			sta7 = this.conect.createStatement();
			ResultSet resul = sta.executeQuery(this.queryT);

			posicion++;
			while(flag && resul.next()){
				System.out.println("posicion-> "+posicion);
				System.out.println(resul.getRow());
				if(resul.getRow()==posicion){
					flag=false;
					this.jTagente.setText(resul.getString("codigo"));
					this.jTfechaalta.setText(resul.getString("FECHA_ALTA"));
					this.jTcodigo2.setText(resul.getString("codigo2"));
					//this.jTnombreagen.setText(resul.getString("nombre"));
					this.jTrazon.setText(resul.getString("razon"));
					
					String codigo=resul.getString("codigo");
					ResultSet resul1 = sta1.executeQuery("select * from dcomerciales where codigo="+codigo);
					if(resul1.next()){
						//this.jTzona.setText(resul1.getString("zona"));
						//this.jTruta.setText(resul1.getString("ruta"));
						//this.jTtipoiva.setText(resul1.getString("IVA_FIJO"));
						String agente=resul1.getString("agente");
						String pago=resul1.getString("FORMA_PAGO");
						String actividad=resul1.getString("actividad");
						String tipoiva=resul1.getString("iva_fijo");
						ResultSet resul3 = sta3.executeQuery("select descripcion from rutas where codigo="+resul1.getString("ruta"));
						
						resul1.close();
						
						if(resul3.next()){
							//this.jTruta.setText(resul3.getString("descripcion"));
							resul3.close();
						}
						
						ResultSet resul4 = sta4.executeQuery("select nombre from agentes where codigo="+agente);
						if(resul4.next()){
							
							//this.jTagente.setText(resul4.getString("nombre"));
							resul4.close();
						}
						ResultSet resul5 = sta5.executeQuery("select nombre from FORMAS_PAGO where codigo="+pago);
						if(resul5.next()){
							
							//this.jTformapago.setText(resul5.getString("nombre"));
							resul5.close();
						}
						ResultSet resul6 = sta6.executeQuery("select DESCRIPCION  from ACTIVIDADES where codigo='"+actividad+"'");
						if(resul6.next()){
							//this.jTactividad.setText(resul6.getString("DESCRIPCION"));
							resul6.close();
						}
						System.out.println(tipoiva);
						if(tipoiva!=null){
						ResultSet resul7 = sta7.executeQuery("select DESCRIPCION  from tipos_iva where codigo="+tipoiva);
						if(resul7.next()){
							//this.jTtipoiva.setText(resul7.getString("DESCRIPCION"));
							resul7.close();	
						}
						}else{
							//this.jTtipoiva.setText("");	
							
						}
					}
					ResultSet resul2 = sta2.executeQuery("select * from dbancarios where codigo="+codigo);
					if(resul2.next()){
						//this.jTdivisa.setText(resul2.getString("divisa"));	
						
					resul2.close();
					}
				}
				
			}
			
			resul.close();
			sta.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
	public void EliminarFila(int posicion){
		boolean flag=true;
		Statement sta;
		Statement sta2;
		try {
			sta = this.conect.createStatement();
			sta2 = this.conect.createStatement();
			
			ResultSet resul = sta.executeQuery(this.queryT);
			posicion++;
			while(flag && resul.next()){
				System.out.println("posicion-> "+posicion);
				System.out.println(resul.getRow());
				if(resul.getRow()==posicion){
					sta2.execute("delete from dpersonales where codigo='"+resul.getString("codigo")+"'");	
					flag=false;
				}
				
			}
			//resul.close();
			sta.close();
			sta2.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		
		
	}
	public boolean validar(int posicion)
	{	
		boolean out=false;
		boolean flag=true;
		Statement sta;
		Statement sta2;
		try {
			sta = this.conect.createStatement();
			sta2 = this.conect.createStatement();
			
			ResultSet resul = sta.executeQuery(this.queryT);
			posicion++;
			while(flag && resul.next()){
				if(resul.getRow()==posicion){
}
				
			}
			//resul.close();
			sta.close();
			sta2.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		
	return out;	
	}
	/**
	 * This method initializes validar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getValidar() {
		if (validar == null) {
			validar = new JButton();
			validar.setText("Validar");
			validar.setLocation(new java.awt.Point(442,365));
			validar.setSize(new java.awt.Dimension(100,26));
			validar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
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
	private JButton getCerrar() {
		if (cerrar == null) {
			cerrar = new JButton();
			cerrar.setText("Cerrar");
			cerrar.setLocation(new java.awt.Point(562,365));
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
	private JTextField getJTagente() {
		if (jTagente == null) {
			jTagente = new JTextField();
			jTagente.setBounds(new java.awt.Rectangle(20,43,418,19));
		}
		return jTagente;
	}
	/**
	 * This method initializes jTfechaalta	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTfechaalta() {
		if (jTfechaalta == null) {
			jTfechaalta = new JTextField();
			jTfechaalta.setBounds(new java.awt.Rectangle(312,21,124,19));
		}
		return jTfechaalta;
	}
	/**
	 * This method initializes jTcodigo2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTcodigo2() {
		if (jTcodigo2 == null) {
			jTcodigo2 = new JTextField();
			jTcodigo2.setBounds(new java.awt.Rectangle(20,137,418,19));
		}
		return jTcodigo2;
	}
	/**
	 * This method initializes jTrazon	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTrazon() {
		if (jTrazon == null) {
			jTrazon = new JTextField();
			jTrazon.setBounds(new java.awt.Rectangle(20,208,425,88));
		}
		return jTrazon;
	}
	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.add(getJTabbedPane(), null);
		}
		return jPanel2;
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new java.awt.Rectangle(21,14,459,333));
			jTabbedPane.addTab("Visitas Agentes",this.getJPanel1());
		}
		return jTabbedPane;
	}
	/**
	 * This method initializes jTnombrecli	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTnombrecli() {
		if (jTnombrecli == null) {
			jTnombrecli = new JTextField();
			jTnombrecli.setBounds(new java.awt.Rectangle(20,93,418,19));
		}
		return jTnombrecli;
	}
	/**
	 * This method initializes jTcodigocli	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTcodigocli() {
		if (jTcodigocli == null) {
			jTcodigocli = new JTextField();
			jTcodigocli.setBounds(new java.awt.Rectangle(194,71,114,19));
		}
		return jTcodigocli;
	}
	/**
	 * This method initializes jTcodigoagentes	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTcodigoagentes() {
		if (jTcodigoagentes == null) {
			jTcodigoagentes = new JTextField();
			jTcodigoagentes.setPreferredSize(new java.awt.Dimension(4,19));
			jTcodigoagentes.setSize(new java.awt.Dimension(101,19));
			jTcodigoagentes.setLocation(new java.awt.Point(140,21));
		}
		return jTcodigoagentes;
	}
	/**
	 * This method initializes jThoravisita	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJThoravisita() {
		if (jThoravisita == null) {
			jThoravisita = new JTextField();
			jThoravisita.setBounds(new java.awt.Rectangle(107,159,76,19));
		}
		return jThoravisita;
	}
	
}  //  @jve:decl-index=0:visual-constraint="2,21"



