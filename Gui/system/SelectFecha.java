package Gui.system;

import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

public class SelectFecha extends JFrame {

	private JPanel jContentPane = null;
	private JCalendar jPanel = null;
	private JButton jButton = null;
	protected JTextField campo;
	protected JButton boton;
	
	public SelectFecha( JTextField campo, JButton boton ) {
		super();
		this.boton = boton;
		boton.setEnabled(false);
		this.campo = campo;
		initialize();
		this.setLocation(300,300);
	}
	
	private JCalendar getJPanel(  ) {
		if (jPanel == null) {
			jPanel = new JCalendar();
			jPanel.setBounds(new java.awt.Rectangle(1,1,265,185));
		}
		return jPanel;
	}

	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(1,186,263,27));
			jButton.setText("Aceptar");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Calendar cal = jPanel.getCalendar();
					int d = cal.get(Calendar.DAY_OF_MONTH);
					int m = cal.get(Calendar.MONTH) + 1;
					int y = cal.get(Calendar.YEAR);
					campo.setText( y+"-"+m+"-"+d);
					boton.setEnabled(true);
					dispose();
				}
			});
		}
		return jButton;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private void initialize() {
		this.setSize(264, 218);
		this.setUndecorated(true);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.setVisible(true);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add( getJPanel(), null);
			jContentPane.add(getJButton(), null);
		}
		return jContentPane;
	}

}  //  @jve:decl-index=0:visual-constraint="39,10"
