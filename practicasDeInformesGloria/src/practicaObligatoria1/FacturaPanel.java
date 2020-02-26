package practicaObligatoria1;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

/**
 *En la clase Factura vamos a tener un panel que recoga los datos de la factura por el id del cliente y mostrarlo en una tabla
 */
public class FacturaPanel extends JPanel {
	private JTextField textField;
	private JTable table;
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement pt;
	/**
	 * Create the panel.
	 */
	public FacturaPanel() {
		setLayout(new BorderLayout(0, 0));
		/**
		 *Creamos el panel de facturas
		 */
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblFactura = new JLabel("Factura");
		lblFactura.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblFactura, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		/**
		 *Creamos el Jlabel y el panel donde vamos a meter el id  
		 */
		JLabel lblNewLabel = new JLabel("Id Cliente");
		panel_1.add(lblNewLabel);
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		/**
		 *El boton nos va ha ejecutar la funcion crear tabla y factura
		 */
		JButton btnNewButton = new JButton(" Buscar");
		panel_2.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("")) {
					crearTabla();
				}else {
					crearFactura();
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		

	}
	/**
	 *La funcion crearTabla hara una conexion y una consulta que es lo que vamos a recoger y enseñar en la tabla
	 */

	protected void crearTabla() {
		// TODO Auto-generated method stub
		try {
			con = Conexion.getConnection();
			if (con == null)
				System.out.println("Error en la conexion");

			stmt = con.createStatement();
			/**
			 *Hacemos la consulta sql de lo que queremos recoger 
			 */

			String sql = "SELECT * FROM invoice ORDER BY CUSTOMERID";

			rs = stmt.executeQuery(sql);

			// TableModel definition
			/**
			 *Hacemos un array de los objectos que queremos recoger y creamos latabla 
			 */
			String[] tableColumnsName = { "ID FACTURA", "ID CLIENTE", "TOTAL"};
			DefaultTableModel aModel = (DefaultTableModel) table.getModel();
			aModel.setColumnIdentifiers(tableColumnsName);

			// Loop through the ResultSet and transfer in the Model
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int colNo = rsmd.getColumnCount();
			/**
			 *Lo incluimos en la tabla para que se muestren
			 */
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
	public void crearFactura() {
		try {
		con = Conexion.getConnection();

		eliminar();
		/**
		 *Hacemos la consulta 
		 */
		String sql = "SELECT * FROM invoice WHERE customerid = ?";

		pt = con.prepareStatement(sql);

		pt.setString(1, textField.getText().toString());

		ResultSet rs = pt.executeQuery();

		// TableModel definition
		/**
		 *Recogemos los datos
		 */
		String[] tableColumnsName = { "ID", "CUSTOMERID", "TOTAL" };
		DefaultTableModel aModel = (DefaultTableModel) table.getModel();
		aModel.setColumnIdentifiers(tableColumnsName);

		/**
		 *Los mostramos en la tabla
		 */
		// Loop through the ResultSet and transfer in the Model
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		int colNo = rsmd.getColumnCount();
		while (rs.next()) {
			Object[] objects = new Object[colNo];
			for (int i = 0; i < colNo; i++) {
				objects[i] = rs.getObject(i + 1);
			}
			aModel.addRow(objects);
		}
			table.setModel(aModel);
		
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 *Para limpiar
	 */
		public void eliminar() {
			DefaultTableModel tb = (DefaultTableModel) table.getModel();
			int a = table.getRowCount() - 1;
			for (int i = a; i >= 0; i--) {
				tb.removeRow(tb.getRowCount() - 1);
			}
		}
		
		
}


