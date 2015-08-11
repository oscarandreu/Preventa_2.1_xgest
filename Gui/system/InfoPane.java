package Gui.system;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class InfoPane extends JOptionPane {

	private static final long serialVersionUID = 1L;

	public InfoPane(String titulo,String mensaje) {
		super();
		Object[] options = {"Aceptar"};
		showOptionDialog(null,
				mensaje,
				titulo,
				JOptionPane.YES_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,     //don't use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title

	}
	public InfoPane(String titulo,String mensaje,ImageIcon icono) {
		super();
		Object[] options = {"Aceptar"};
		showOptionDialog(null,
				mensaje,
				titulo,
				JOptionPane.YES_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				icono,     //don't use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
		

	}	
	public InfoPane(String titulo,String mensaje,ImageIcon icono,Color color) {
		super();
		UIManager.put("OptionPane.background",color);
	    UIManager.put("Panel.background",color);
	    //UIManager.put("Button.background",color);

		Object[] options = {"Aceptar"};
		showOptionDialog(null,
				mensaje,
				titulo,
				JOptionPane.YES_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				icono,     //don't use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
	}	

}
