package bbdd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import log.LogWriter;
import ERP.ClienteErp;
import Gui.GuiInternalFrame;
import data.Cliente;

public class FrameTableExtendida extends InternalFrameTable{
	
	protected String[] nombreColumnas;
	protected int codigo;
	
	// =================================================================================
	public FrameTableExtendida(GuiInternalFrame frame, String[] nomCol, int cod)
	// =================================================================================
	{
		super(frame);
		nombreColumnas = nomCol;
		codigo = cod;
	}
	
	// ==================================================================
	@SuppressWarnings("unchecked")
	public void insertarFila(ResultSet rs)
	// ==================================================================
	{
		try {
			// pongo las claves primarias en un vector para busquedas
			// posteriores
			if (primaryKeys != null) {
				for (int i = 0; i < primaryKeys.length; i++) {
					if (primaryKeysFormateadas != null) {
						keys[i] = rs.getString(primaryKeysFormateadas[i]);
					} else {
						keys[i] = rs.getString(primaryKeys[i]);
					}

				}
				vectorPrimaryKeys.add(keys.clone());
			}
			// preparo los datos de la columna
			Vector v = new Vector();
			if (columnasFormateadas != null) {
				for (int i = 0; i < columnasFormateadas.length; i++) {
					v.add(rs.getString(columnasFormateadas[i]));
				}
			} else {
				for (int i = 0; i < columnas.length; i++) {
					System.out.println("col :" + columnas[i] + "rs.getString:" + rs.getString(columnas[i]));
					v.add(rs.getString(columnas[i]));
				}
			}
			
			// Añado los datos ERP
			if (nombreColumnas!= null){
				for(int i=0; i<nombreColumnas.length;i++){
					//switch (codigo) {
						//case 1:	// insertamos el nombre del Cliente
							Cliente cliErp = new ClienteErp();
							String query = cliErp.getQueryClienteByCodigo(keys[0]);
							Statement stErp = sys.getConexionErp().createStatement();
							ResultSet rsErp = stErp.executeQuery(query);
							if (rsErp.next()){
								cliErp.setClienteFromErp(rsErp);
							}else{
								cliErp.getClienteByCodigoLocal(new Integer(keys[0]),sys);
							}
							v.add(cliErp.getNombre());
						/*
							break;
					default:
						break;
					}*/
					
				}
			}
			
			// Creo un vector con los datos de verificacion
			if (verificado != null) {
				vectorVerificados.add(rs.getString(verificado[1]));
			}
			insertRow(getRowCount(), v.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	/**
	 * Carga los datos de la tabla si true monta las cabeceras si false solo
	 * monta los datos
	 */
	// ==================================================================
	public void setData(boolean setCabecera) throws SQLException
	// ==================================================================
	{
		Connection c = null;
		c = sys.getConexionPropia();
		vectorPrimaryKeys = new Vector();
		Statement s = c.createStatement();
		getQuery();
		System.out.println(query);
		ResultSet rs = s.executeQuery(query);
		for (int i = getRowCount() - 1; i >= 0; i--) {
			removeRow(i);
		}
		if (setCabecera) {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			// Ajusta el numero de las cabeceras si hay primaryKeys que no
			// queremos mostrar
			int ajuste = 0;
			if (primaryKeys != null)
				ajuste = primaryKeys.length;
			// Ajusta el numero de las cabeceras si usamos verificar
			int fin = rsMetaData.getColumnCount();
			if (verificado != null)
				fin--;
			for (int i = 1 + ajuste; i <= fin; i++) {
				addColumn(rsMetaData.getColumnName(i));
			}
			if (nombreColumnas != null){
				for(int i=0; i<nombreColumnas.length;i++){
					addColumn(nombreColumnas[i]);
				}
			}
		}
		numeroFilas = 0;
		
		// cargo todos los datos en la tabla
		while (rs.next()) {
			insertarFila(rs);
			numeroFilas++;
		}
		rs.close();
		s.close();
		c.close();
	}

}
