package Gui;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import log.LogWriter;
import system.Cargador;
import Gui.system.ErrorPane;
import Gui.system.InfoPane;
import bbdd.InternalFrameTable;


public class GuiAgentes  extends GuiInternalFrame{
	
	
	private JButton validar = null;
	private JButton cerrar = null;
	
//	Declaracion de jpanel 	
	private JPanel panel = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	
//	Declaracion jScrollPane	
	private JScrollPane jScrollPane = null;
	private JSplitPane jSplitPane = null;
	
//	Declaracion campos	
	
	private JLabel jLcodigo = null;
	private JLabel jLnombre = null;
	private JLabel jLserie = null;
	private JLabel jLpassword = null;
	private JLabel jLCpassword = null;
	
	JTextField jTcodigo = null;
	JTextField jTnombre = null;
	JTextField jTserie = null;
	JPasswordField jTpassword = null;
	JPasswordField jTCpassword = null;
	
//	Contador
	private JLabel jLabel = null;
	private JTabbedPane jTabbedPane = null;
	
	//	==================================================================
	public GuiAgentes( Cargador cargador ) 
//	==================================================================
	{
		super( cargador );
		
		icono = "tab_comparativa.gif";
		toolTip = "Muestra los Agentes Importados";
		titulo = "Agentes";
	}
//	==================================================================
	protected JPanel getPanel() 
//	==================================================================
	{
		if (panel == null) {
			
			jLabel = new JLabel();
			jLabel.setText("JLabel");
			jLabel.setSize(new java.awt.Dimension(258,29));
			jLabel.setLocation(new java.awt.Point(9,295));
			panel = new JPanel();
			panel.setLayout(null);
			panel.setSize(new java.awt.Dimension(757,338));
			panel.add(getValidar(), null);
			panel.add(getCerrar(), null);
			panel.add(jLabel, null);
			panel.add(getJTabbedPane(), null);
			filasImportadas = jLabel;
			filasImportadas.setText("Numero de Agentes Importados : "+model.numeroFilas);
			
		}
		frame.setBounds(new java.awt.Rectangle(50,50,789,390));
		return panel;
	}
//	===========================================================================================
	protected JScrollPane getJScrollPane() 
//	===========================================================================================
	{
		model = new InternalFrameTable( this){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//Configuracion de la tabla
		model.setColumnas( new String[]{"codigo","nombre"} );
		model.setColumnasFormateadas(  new String[]{"Código","Nombre"} );
		model.setAnchoColumnas( new int[]{80,220} );
		model.setDatabase("agentes");
		model.setOrder("codigo");
		jScrollPane = new JScrollPane( //new JTable() );
				model.getTabla() );
		
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane.setBounds(new java.awt.Rectangle(0,0,279,390));
		return jScrollPane;
	}

	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(300);
			jSplitPane.setDividerSize(5);
			jSplitPane.setRightComponent(getJPanel1());
			jSplitPane.setLeftComponent(getJPanel());
		}
		return jSplitPane;
	}

	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane(), java.awt.BorderLayout.NORTH);
		}
		return jPanel;
	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jLserie = new JLabel();
			jLserie.setBounds(new java.awt.Rectangle(207,43,47,25));
			jLserie.setText("Serie");
			jLCpassword = new JLabel();
			jLCpassword.setBounds(new java.awt.Rectangle(14,190,137,26));
			jLCpassword.setText(" Confirmar contraseña");
			jLpassword = new JLabel();
			jLpassword.setBounds(new java.awt.Rectangle(16,157,72,27));
			jLpassword.setText("Contraseña");
			jLnombre = new JLabel();
			jLnombre.setBounds(new java.awt.Rectangle(19,76,50,26));
			jLnombre.setText("Nombre");
			jLcodigo = new JLabel();
			jLcodigo.setBounds(new java.awt.Rectangle(16,44,41,23));
			jLcodigo.setText("Codigo");
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new java.awt.Dimension(90,150));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agentes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			jPanel1.add(jLcodigo, null);
			jPanel1.add(jLnombre, null);
			jPanel1.add(jLpassword, null);
			jPanel1.add(jLserie, null);
			jPanel1.add(getJTcodigo(), null);
			jPanel1.add(getJTnombre(), null);
			jPanel1.add(getJTserie(), null);
			jPanel1.add(jLCpassword, null);
			jPanel1.add(getJTpassword(), null);
			jPanel1.add(getJTCpassword(), null);
		}
		return jPanel1;
	}
	
	/**
	 * This method initializes jTcodigo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTcodigo() {
		if (jTcodigo == null) {
			jTcodigo = new JTextField();
			jTcodigo.setLocation(new java.awt.Point(69,44));
			jTcodigo.setEditable(false);
			jTcodigo.setSize(new java.awt.Dimension(124,23));
		}
		return jTcodigo;
	}
	/**
	 * This method initializes jTnombre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTnombre() {
		if (jTnombre == null) {
			jTnombre = new JTextField();
			jTnombre.setLocation(new java.awt.Point(18,107));
			jTnombre.setEditable(false);
			jTnombre.setSize(new java.awt.Dimension(394,23));
		}
		return jTnombre;
	}
//	===========================================================================================
	public void mostrarDatos(String where )
//	===========================================================================================
	{
		
		String query = "SELECT * FROM agentes WHERE "+where;
		Statement sta;
		try {
			sta = this.conect.createStatement();
			ResultSet resul = sta.executeQuery( query );
			if( resul.next() ){
				this.jTcodigo.setText(resul.getString("codigo"));
				this.jTserie.setText(resul.getString("serie"));
				this.jTnombre.setText(resul.getString("nombre"));
				this.jTpassword.setText(resul.getString("passw"));
				this.jTCpassword.setText(resul.getString("passw"));
			}
			resul.close();
			sta.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
	private JButton getValidar() {
		if (validar == null) {
			validar = new JButton();
			validar.setText("Validar");
			validar.setLocation(new java.awt.Point(368,300));
			validar.setSize(new java.awt.Dimension(100,26));
			validar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String pass1 = new String( jTpassword.getPassword() );
					String pass2 = new String(  jTCpassword.getPassword() );
					if( pass1.equals( pass2 ) && !"".equals(jTcodigo.getText())){
						String query = "UPDATE agentes SET ";
						query += "serie='"+jTserie.getText()+"',";
						query += "nombre='"+jTnombre.getText()+"',";
						query += "passw='"+pass1+"'";
						query += " WHERE codigo='"+jTcodigo.getText()+"'";
						System.out.println( query );
						try {
							Statement sta = conect.createStatement();
							if( sta.executeUpdate( query ) == 1 ){
								new InfoPane("Cambio realizado","La operación se ha realizado con\nexito.");
							}else{
								new ErrorPane("La operación no ha podido ser realizada.");
							}
							sta.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
							new LogWriter( e1.getStackTrace() );
						}
					}else{
						new InfoPane("Atención","Compruebe que ha seleccionado un agente\n" +
								"y que las contraseñas coninciden.");
					}
				}
			});
		}
		return validar;
	}
	
	private JButton getCerrar() {
		if (cerrar == null) {
			cerrar = new JButton();
			cerrar.setText("Cerrar");
			cerrar.setLocation(new java.awt.Point(488,300));
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
	 * This method initializes jTpassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getJTpassword() {
		if (jTpassword == null) {
			jTpassword = new JPasswordField();
			jTpassword.setLocation(new java.awt.Point(155,156));
			jTpassword.setSize(new java.awt.Dimension(168,23));
		}
		return jTpassword;
	}
	/**
	 * This method initializes jTCpassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getJTCpassword() {
		if (jTCpassword == null) {
			jTCpassword = new JPasswordField();
			jTCpassword.setLocation(new java.awt.Point(156,192));
			jTCpassword.setSize(new java.awt.Dimension(168,23));
		}
		return jTCpassword;
	}
	/**
	 * This method initializes jTserie	
	 * 	
	 * @return javax.swing.JTextField	
	 */
//	==================================================================
	private JTextField getJTserie() 
//	==================================================================
	{
		if (jTserie == null) {
			jTserie = new JTextField();
			jTserie.setLocation(new java.awt.Point(269,46));
			jTserie.setEditable(true);
			jTserie.setSize(new java.awt.Dimension(145,23));
		}
		return jTserie;
	}
	/*
//	==================================================================
    private JFreeChart createChart(PieDataset piedataset,String titulo)
//	==================================================================
    {
        JFreeChart jfreechart = ChartFactory.createPieChart3D(titulo, piedataset, true, true, false);
        jfreechart.setBackgroundPaint(Color.WHITE);
        PiePlot3D pie3dplot = (PiePlot3D)jfreechart.getPlot();
        pie3dplot.setStartAngle(270D);
        //pie3dplot.setBackgroundImage( sys.getIcono("logo mobisys.jpg").getImage() );
        //pie3dplot.setBackgroundAlpha(0.1F);
        pie3dplot.setLabelFont( new Font("LucidaTypewriter", Font.BOLD, 12) );
        pie3dplot.setDirection(Rotation.CLOCKWISE);
        pie3dplot.setForegroundAlpha(0.7F);
        pie3dplot.setNoDataMessageFont( new Font("LucidaTypewriter", Font.BOLD, 50) );
        pie3dplot.setNoDataMessage("No hay datos \nque mostrar.");
        return jfreechart;
    }
*/
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
//	==================================================================
	private JTabbedPane getJTabbedPane() 
//	==================================================================
	{
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new java.awt.Rectangle(2,2,752,288));
			jTabbedPane.addTab("Datos de  agentes", null, getJSplitPane(), null);
		}
		return jTabbedPane;
	}

	
}  //  @jve:decl-index=0:visual-constraint="2,21"
