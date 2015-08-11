package Gui;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.beans.PropertyVetoException;

import log.LogWriter;

public class GuiPdas  {
	
	private JInternalFrame jInternalFrame = null;  //  @jve:decl-index=0:visual-constraint="43,4"
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JPanel jPanel = null;
	
	/** Clase que sirve para visualizar las pdas dadas de alta y poder modificar los datos de las mismas*/
//	==================================================================
	public GuiPdas( JDesktopPane jDesktopPane )
//	==================================================================
	{
		jInternalFrame = getJInternalFrame();
		jDesktopPane.add( jInternalFrame );
		try {
			jInternalFrame.setSelected(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
			new LogWriter( e1.getStackTrace() );
		}
	}
	
	/**
	 * This method initializes jInternalFrame	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */
	private JInternalFrame getJInternalFrame() 
	{
		jInternalFrame.setSize(new java.awt.Dimension(667,432));
		jInternalFrame.setContentPane(getJContentPane());
		return jInternalFrame;
	}
	
	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			jContentPane = new JPanel();
			jContentPane.setLayout(gridLayout);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJPanel(), null);
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	
	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
		}
		return jTable;
	}
	
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
		}
		return jPanel;
	}
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"
