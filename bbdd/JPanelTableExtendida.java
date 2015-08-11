package bbdd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import log.LogWriter;
import ERP.ArticuloErp;
import Gui.GuiJPanel;
import data.Articulo;

public class JPanelTableExtendida extends JPanelTable{
	
	protected String[] nombreColumnas;
	protected int codigo;
	
	
	// ==================================================================
	public JPanelTableExtendida(GuiJPanel frame, String[] nomCol, int cod)
	// ==================================================================
	{
		super(frame);
		nombreColumnas = nomCol;
		codigo = cod;
	}
	
//	 ==================================================================
	@SuppressWarnings("unchecked")
	public void insertarFila(ResultSet rs)
	// ==================================================================
	{
		try {
			Vector v = new Vector();
			if (primaryKeys != null) {
				for (int i = 0; i < primaryKeys.length; i++) {
					keys[i] = rs.getString(primaryKeys[i]);
				}
				vectorPrimaryKeys.add(keys.clone());
			}
			if (columnasFormateadas != null) {
				for (int i = 0; i < columnasFormateadas.length; i++) {
					v.add(rs.getString(columnasFormateadas[i]));
				}
			} else {
				for (int i = 0; i < columnas.length; i++) {
					v.add(rs.getString(columnas[i]));
				}
			}
			
			//Añado los datos ERP: en el JPanelTable tendremos siempre Articulos
			if (nombreColumnas!= null){
				for(int i=0; i<nombreColumnas.length;i++){
					switch (codigo){
					case 1:
						break;
					case 2:	// insertamos el nombre del Articulo
						Articulo artErp = new ArticuloErp();
						String query = artErp.getQueryArticuloByCodigo(keys[0]);
						Statement stErp = sys.getConexionErp().createStatement();
						System.out.println("quey problematico:"+query);
						ResultSet rsErp = stErp.executeQuery(query);
						if (rsErp.next()){
							artErp.setArticulo(rsErp);
						}
						v.add(artErp.getNombre());
						
						break;
					default:
						break;
					}
					
				}
			}
			
			
			insertRow(getRowCount(), v.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setData(boolean setCabecera) throws SQLException
	// ==================================================================
	{
		getQuery();
		Connection c = null;
		c = sys.getConexionPropia();

		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(query);
		for (int i = getRowCount() - 1; i >= 0; i--) {
			removeRow(i);
		}
		if (setCabecera) {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			// Ajusta la cuenta si hay primaryKeys que no queremos mostrar
			int ajuste = 0;
			if (primaryKeys != null)
				ajuste = primaryKeys.length;
			for (int i = 1 + ajuste; i <= rsMetaData.getColumnCount(); i++) {
				addColumn(rsMetaData.getColumnName(i));
			}
			if (nombreColumnas != null){
				for(int i=0;i<nombreColumnas.length;i++){
					addColumn(nombreColumnas[i]);
				}
			}

		}
		numeroFilas = 0;

		while (rs.next()) {
			insertarFila(rs);
			numeroFilas++;
		}
		rs.close();
		s.close();
		c.close();
	}

}
