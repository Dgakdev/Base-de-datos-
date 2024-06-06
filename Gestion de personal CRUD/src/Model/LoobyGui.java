package Model;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class LoobyGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoobyGui frame = new LoobyGui();
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
	public LoobyGui() {
		setTitle("Looby");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnConsulta = new JButton("Consulta");
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultaGui consultaGui = new ConsultaGui();
				consultaGui.setVisible(true);
				dispose(); // cierra el Jframe Actual(LobbyGui)
			}
		});
		btnConsulta.setBounds(177, 118, 89, 23);
		contentPane.add(btnConsulta);

		JButton btnModificaciondatos = new JButton("Modificacion Datos");
		btnModificaciondatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModificacionDatosGui modificacionDatosGui = new ModificacionDatosGui();
				modificacionDatosGui.setVisible(true);
				dispose(); // cierra el Jframe Actual(LobbyGui)
			}
		});
		btnModificaciondatos.setBounds(150, 160, 142, 23);
		contentPane.add(btnModificaciondatos);

		JButton btnPersonal = new JButton("Personal");
		btnPersonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonalGui personalGui = new PersonalGui();
				personalGui.setVisible(true);
				dispose(); // cierra el Jframe Actual(PersonalGui)
			}
		});
		btnPersonal.setBounds(79, 118, 89, 23);
		contentPane.add(btnPersonal);

		JButton btnPermisos = new JButton("Permisos");
		btnPermisos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PermisosGui permisosGui = new PermisosGui();
				permisosGui.setVisible(true);
				dispose(); // cierra el Jframe Actual(PermisosGui)
			}
		});
		btnPermisos.setBounds(275, 118, 89, 23);
		contentPane.add(btnPermisos);
	}
}
