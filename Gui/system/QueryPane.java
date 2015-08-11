package Gui.system;

import java.awt.TextField;

import javax.swing.JOptionPane;

public class QueryPane extends JOptionPane {

	private static final long serialVersionUID = 1L;
	protected Object[] options = {"Si","No"};
//	==================================================================
	public QueryPane() 
//	==================================================================
	{
		super();		
	}
//	==================================================================
	public int consultar(String mensaje)
//	==================================================================
	{
		return showOptionDialog(null,
				mensaje,
				"Atención",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //don't use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
	}
//	==================================================================
	public int consultarRuta(String in)
//	==================================================================
	{
		TextField ruta = new TextField(in);
		this.add(ruta);
		return showOptionDialog(null,
				"Seleccione ruta",
				"Atención",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //don't use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
	}
/*
	public static int showOptionDialog(Component parentComponent,
	        Object message, String title, int optionType, int messageType,
	        Icon icon, Object[] options, Object initialValue)
	        throws HeadlessException {
	        JOptionPane             pane = new JOptionPane(message, messageType,
	                                                       optionType, icon,
	                                                       options, initialValue);

	        pane.setInitialValue(initialValue);
	        pane.setComponentOrientation(((parentComponent == null) ?
		    getRootFrame() : parentComponent).getComponentOrientation());

	        int style = styleFromMessageType(messageType);
	        JDialog dialog = pane.createDialog(parentComponent, title, style);

	        pane.selectInitialValue();
	        dialog.show();
	        dialog.dispose();

	        Object        selectedValue = pane.getValue();

	        if(selectedValue == null)
	            return CLOSED_OPTION;
	        if(options == null) {
	            if(selectedValue instanceof Integer)
	                return ((Integer)selectedValue).intValue();
	            return CLOSED_OPTION;
	        }
	        for(int counter = 0, maxCounter = options.length;
	            counter < maxCounter; counter++) {
	            if(options[counter].equals(selectedValue))
	                return counter;
	        }
	        return CLOSED_OPTION;
	    }
*/
}
