package Gui;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import log.LogWriter;

import system.Cargador;
import system.SysData;
import bbdd.InternalFrameTable;

/**
 * es la gui de gestion del servidor sonde se le meteria la direccion del servidor
 * @author Elena
 *
 */
public class GuiRutas extends GuiInternalFrame{

	private JPanel panel = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JButton cerrar = null;

	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane2 = null;
	//private JLabel numeroFilas = null;
	private JSplitPane jSplitPane = null;
	protected	InternalFrameTable model2 = null;
	private JLabel jLabel;
	
//	==================================================================
	public GuiRutas( Cargador cargador ) 
//	==================================================================
	{
		super( cargador );
		icono = "tab_comparativa.gif";
		toolTip = "Muestra los incidencias Importadas";
		titulo = "Rutas";
	}
//	===========================================================================================
	protected JScrollPane getJScrollPane() 
//	===========================================================================================
	{
		model = new InternalFrameTable( this ){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//Configuracion de la tabla
		model.setColumnas( new String[]{"codigo","descripcion"} );
		model.setColumnasFormateadas(  new String[]{"Código","Descripción"} );
		model.setAnchoColumnas( new int[]{80,166} );
		model.setDatabase("rutas");
		jScrollPane = new JScrollPane( model.getTabla() );
		
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		return jScrollPane;
	}
//	===========================================================================================
	protected JScrollPane getJScrollPane2() 
//	===========================================================================================
	{
		model2 = new InternalFrameTable( this ){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//model2.setPrimaryKeys( new String[]{"ruta"} );
		model2.setColumnas( new String[]{"nombre","poblacion"} );
		model2.setColumnasFormateadas(  new String[]{"Nombre","Población"} );
		model2.setAnchoColumnas( new int[]{250,265} );
		model2.setDatabase("dpersonales");
		model2.setWhere("codigo = 0");
		jScrollPane2 = new JScrollPane( model2.getTabla() );
		//jScrollPane2.setHorizontalScrollBarPolicy(jScrollPane2.HORIZONTAL_SCROLLBAR_ALWAYS);
		return jScrollPane2;
	}
//	===========================================================================================
	public void mostrarDatos(String where )
//	===========================================================================================
	{
		if( !where.contains( "nombre=" ) ){
			model2.setWhere( SysData.stringReplace(where,"codigo","ruta") );
			try {
				model2.setData(false);
			} catch (SQLException e) {
				e.printStackTrace();
				new LogWriter( e.getStackTrace() );
			}
		}
	}
//	==================================================================
	protected JPanel getPanel() 
//	==================================================================
	{
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.setBounds(new java.awt.Rectangle(0,0,790,344));
			panel.add(getCerrar(), null);
			panel.add(getJSplitPane(), null);
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(12,296,215,26));
			jLabel.setText("JLabel");
			panel.add(jLabel, null);
			filasImportadas = jLabel;
			filasImportadas.setText("Numero de Rutas Importados : "+model.numeroFilas);
		}
		frame.setBounds(new java.awt.Rectangle(50,50,790,370));
		return panel;
	}
	private JButton getCerrar() {
		if (cerrar == null) {
			cerrar = new JButton();
			cerrar.setBounds(new java.awt.Rectangle(385,297,100,26));
			cerrar.setText("Cerrar");
			cerrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cerrar();
				}
			});
		}
		
		return cerrar;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new java.awt.Rectangle(7,8,774,275));
			jSplitPane.setDividerSize(5);
			jSplitPane.setLeftComponent(getJScrollPane());
			jSplitPane.setRightComponent(getJScrollPane2());
			jSplitPane.setDividerLocation(250);
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

}  //  @jve:decl-index=0:visual-constraint="2,21"
