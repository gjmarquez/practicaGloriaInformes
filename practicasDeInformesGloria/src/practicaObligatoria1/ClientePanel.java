package practicaObligatoria1;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ClientePanel extends JPanel {
	private JTable table;
	private Connection con;
	private Statement stmt;
	private ResultSet rs;

	/**
	 * Panel del Clientes
	 */
	public ClientePanel() {
		setLayout(new BorderLayout(0, 0));

		JLabel lblClientes = new JLabel("Clientes");
		lblClientes.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblClientes, BorderLayout.NORTH);

		/**
		 *Ponemos un Jscroll para poder ver toda la informacion aunque no quepa del todo en el panel 
		 */
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		/**
		 *Creamos la tabla hacemos una consulta de mysql para definir lo que queremos que nos muestre en la tabla
		 */
		table = new JTable();
		scrollPane.setViewportView(table);
		try {
			con = Conexion.getConnection();
			if (con == null)
				System.out.println("Error en la conexion");

			stmt = con.createStatement();
			/**
			 *Aqui hacemos la consulta
			 */
			String sql = "SELECT * FROM CUSTOMER";

			rs = stmt.executeQuery(sql);

			// TableModel definition
			/**
			 *Aqui hacemos una array para poder definir lo que querems que recoga de la consulta 
			 */
			String[] tableColumnsName = { "ID", "FIRSTNAME", "LASTNAME", "STREET", "CITY" };
			DefaultTableModel aModel = (DefaultTableModel) table.getModel();
			aModel.setColumnIdentifiers(tableColumnsName);

			// Loop through the ResultSet and transfer in the Model
			/**
			 *Aqui incluimos todos los objectos mientras tengamos. 
			 */
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int colNo = rsmd.getColumnCount();
			while (rs.next()) {
				Object[] objects = new Object[colNo];
				// tanks to umit ozkan for the bug fix!
				for (int i = 0; i < colNo; i++) {
					objects[i] = rs.getObject(i + 1);
				}
				aModel.addRow(objects);
			}
			table.setModel(aModel);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
