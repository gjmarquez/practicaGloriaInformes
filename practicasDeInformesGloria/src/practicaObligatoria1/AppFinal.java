package practicaObligatoria1;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AppFinal extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	
	/**

	 * Esta clase define objetos que contienen tantos enteros aleatorios entre 0 y 1000 como se le definen al crear un objeto

	 * @author: Gloria J. Márquez Cano

	 * @version: 26/02/2020

	 * @param Necesitamos la clase Conexion para que funcione.
	 * @return Devuelve un informe con la factura de los proovedores.

	 */
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppFinal frame = new AppFinal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public AppFinal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		/**
		 * Menu de Consultas 
		 */
		JMenu mnConsultas = new JMenu("consultas");
		menuBar.add(mnConsultas);
		/**
		 *La primera opcion de menu va a ser clientes 
		 */
		JMenuItem mntmC = new JMenuItem("Clientes");
		mntmC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	itemStateChanged("pCliente");
			}
		});
		mnConsultas.add(mntmC);
		/**
		 *La segunda opcion de menu va a ser Facturas 
		 */
		JMenuItem mntmC_1 = new JMenuItem("Facturas");
		mntmC_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemStateChanged("pFactura");
				
			}
		});
		mnConsultas.add(mntmC_1);
		/**
		 *La tercera opcion de menu va a ser Generar Facturas 
		 */
		
		JMenuItem mntmC_2 = new JMenuItem("Generar Facturas");
		mntmC_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				itemStateChanged("pGenerar");
			}
		});
		mnConsultas.add(mntmC_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		ClientePanel cp = new ClientePanel();
		panel.add(cp,"pCliente");
		
		
		FacturaPanel fp = new FacturaPanel();
		panel.add(fp,"pFactura");
		
		GenerarFPanel gp = new GenerarFPanel();
		panel.add(gp,"pGenerar");
		
	}
	/**
	 * Hacemos un CardLayout para que podamos tener el menu visible en todos los paneles
	 */
	public void itemStateChanged(String evt) {
		CardLayout cl = (CardLayout)(panel.getLayout());
		cl.show(panel, evt);
	}

}
