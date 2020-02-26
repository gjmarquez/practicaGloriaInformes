package practicaObligatoria1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class GenerarFPanel extends JPanel {
	private JTextField textField;
	private Connection con;

	/**
	 * Creamos el panel de Generar informe que nos va a servir como su nombre bien indica
	 */
	public GenerarFPanel() {
		setLayout(new BorderLayout(0, 0));
		/**
		 *Creamos el panel
		 */
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblGenerarInforme = new JLabel("Generar Informe");
		lblGenerarInforme.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblGenerarInforme, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		/**
		 *Pedimos el id que vayamos a guardar
		 */
		
		JLabel lblIdDeLa = new JLabel("ID de la factura:");
		lblIdDeLa.setBounds(44, 22, 262, 15);
		panel_1.add(lblIdDeLa);
		
		textField = new JTextField();
		textField.setBounds(22, 49, 174, 32);
		panel_1.add(textField);
		textField.setColumns(10);
		/**
		 *Creamos el boton con la funcion de generar la factura
		 */
		
		JButton btnNewButton = new JButton("Generar");
		btnNewButton.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 generarFactura();
			}
		});
		btnNewButton.setBounds(237, 36, 105, 56);
		panel_1.add(btnNewButton);

	}
	/**
	 *En esta funcion abriremos una ventana para que podamos guardar y recogeremos los datos de nuestra carpeta de jasper , para recogerlos y guardarlos en pdf
	 */
	protected void generarFactura() {
		// TODO Auto-generated method stub
		JFileChooser fc = new JFileChooser();

		int seleccion = fc.showSaveDialog(this);
		
		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File fichero = fc.getSelectedFile();
			try {
				con = Conexion.getConnection();
				Map<String, Object> parametros = new HashMap<String, Object>();
				// introducir nombre del parametro de jasper y la variable
				parametros.put("id", Integer.valueOf(textField.getText()));

				// ruta relativa del .jasper, map de los parametros,conexion
				JasperPrint print = JasperFillManager.fillReport("facturas/proveedores.jasper", parametros, con);
				System.out.println("prueba");
				JasperViewer.viewReport(print);
				String ruta = fichero.toString();
				JasperExportManager.exportReportToPdfFile(print, ruta);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error");
				e.printStackTrace();
			}

		}

	
	}
}

