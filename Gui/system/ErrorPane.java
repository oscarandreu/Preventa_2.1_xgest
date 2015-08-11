package Gui.system;

import javax.swing.JOptionPane;

public class ErrorPane extends JOptionPane {

	private static final long serialVersionUID = 1L;

	public ErrorPane(String mensaje) {
		super();
		Object[] options = {"Aceptar"};
		showOptionDialog(null,
				mensaje,
				"Error en la aplicación",
				JOptionPane.YES_OPTION,
				JOptionPane.ERROR_MESSAGE,
				null,     //don't use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
	}

}
