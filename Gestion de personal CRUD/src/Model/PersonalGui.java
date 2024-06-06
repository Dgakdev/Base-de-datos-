package Model;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Db.ConexionDB;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonalGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDocumento;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JTextField txtDireccion;
	private JTextField txtEmail;
	private JTextField txtTelefono;
	private JTextField txtContraseña;
	private JTextField txtConfircontraseña;
	private JTextField textEdad;
	private JComboBox<String> BoxGenero;
	private JComboBox<String> BoxTipoidentificacion;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PersonalGui frame = new PersonalGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public PersonalGui() {
		setTitle("Agregar Personal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDocumento = new JLabel("Documento");
		lblDocumento.setBounds(4, 11, 65, 14);
		contentPane.add(lblDocumento);

		txtDocumento = new JTextField();
		txtDocumento.setBounds(162, 5, 249, 20);
		contentPane.add(txtDocumento);
		txtDocumento.setColumns(10);

		JLabel lblTipoIdentificacion = new JLabel("Tipo Identificacion");
		lblTipoIdentificacion.setBounds(4, 39, 129, 14);
		contentPane.add(lblTipoIdentificacion);

		JLabel lblNombres = new JLabel("Nombres");
		lblNombres.setBounds(4, 67, 65, 14);
		contentPane.add(lblNombres);

		txtNombres = new JTextField();
		txtNombres.setColumns(10);
		txtNombres.setBounds(162, 61, 249, 20);
		contentPane.add(txtNombres);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(4, 95, 65, 14);
		contentPane.add(lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(162, 89, 249, 20);
		contentPane.add(txtApellidos);

		JLabel lblDireccion = new JLabel("Direccion");
		lblDireccion.setBounds(4, 123, 65, 14);
		contentPane.add(lblDireccion);

		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(162, 117, 249, 20);
		contentPane.add(txtDireccion);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(4, 151, 65, 14);
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(162, 145, 249, 20);
		contentPane.add(txtEmail);

		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(4, 179, 65, 14);
		contentPane.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(162, 173, 249, 20);
		contentPane.add(txtTelefono);

		BoxTipoidentificacion = new JComboBox<>();
		BoxTipoidentificacion.setBounds(162, 32, 100, 22);
		BoxTipoidentificacion.addItem(" CC");
		BoxTipoidentificacion.addItem(" Pasaporte");
		contentPane.add(BoxTipoidentificacion);

		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(4, 210, 81, 14);
		contentPane.add(lblContrasea);

		txtContraseña = new JTextField();
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(162, 204, 249, 20);
		contentPane.add(txtContraseña);

		JLabel lblConfirmarContrasea = new JLabel("Confirmar Contraseña");
		lblConfirmarContrasea.setBounds(4, 241, 129, 14);
		contentPane.add(lblConfirmarContrasea);

		txtConfircontraseña = new JTextField();
		txtConfircontraseña.setColumns(10);
		txtConfircontraseña.setBounds(162, 235, 249, 20);
		contentPane.add(txtConfircontraseña);

		JLabel lblGenero = new JLabel("Genero");
		lblGenero.setBounds(4, 272, 65, 14);
		contentPane.add(lblGenero);

		JLabel lblEdad = new JLabel("Edad");
		lblEdad.setBounds(4, 303, 65, 14);
		contentPane.add(lblEdad);

		textEdad = new JTextField();
		textEdad.setColumns(10);
		textEdad.setBounds(162, 297, 249, 20);
		contentPane.add(textEdad);

		BoxGenero = new JComboBox<>();
		BoxGenero.addItem(" M");
		BoxGenero.addItem(" F");
		BoxGenero.addItem("No binario");
		BoxGenero.addItem("No responder");
		BoxGenero.setBounds(162, 266, 100, 22);
		contentPane.add(BoxGenero);

		// trabajando aqui
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoobyGui loobyGui = new LoobyGui();
				loobyGui.setVisible(true);
				dispose(); // cierra el Jframe Actual(LobbyGui)
			}
		});
		btnVolver.setForeground(new Color(0, 0, 0));
		btnVolver.setBounds(44, 344, 89, 23);
		contentPane.add(btnVolver);

		JButton btnConsulta = new JButton("Consulta");
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultaGui consultaGui = new ConsultaGui();
				consultaGui.setVisible(true);
				dispose(); // cierra el jframe
			}
		});
		btnConsulta.setBounds(173, 344, 89, 23);
		contentPane.add(btnConsulta);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarRegistro();

			}
		});
		btnRegistrar.setBounds(301, 344, 89, 23);
		contentPane.add(btnRegistrar);
	}

	private void agregarRegistro() {
		try (Connection connection = ConexionDB.conectar()) {
			String sql = "INSERT INTO Personal (ID_Documento, Tipo_de_Identificacion, Nombre, Apellido, Direccion, Correo_Electronico, Telefono, Contraseña, ConfirmarContraseña, Genero, Edad ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, txtDocumento.getText());
			statement.setString(2, BoxTipoidentificacion.getSelectedItem().toString());
			statement.setString(3, txtNombres.getText());
			statement.setString(4, txtApellidos.getText());
			statement.setString(5, txtDireccion.getText());
			statement.setString(6, txtEmail.getText());
			statement.setString(7, txtTelefono.getText());
			statement.setString(8, txtContraseña.getText());
			statement.setString(9, txtConfircontraseña.getText()); // Agregar la contraseña confirmada
			statement.setString(10, BoxGenero.getSelectedItem().toString()); // Obtener el género del ComboBox
			statement.setInt(11, Integer.parseInt(textEdad.getText())); // Convertir la edad a entero (código comentado
																		// por ahora)
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("¡Registro insertado correctamente!");
				// Aquí puedes mostrar un mensaje de éxito o limpiar los campos del formulario
			}
		} catch (SQLException ex) {
			System.out.println("Error al insertar el registro: " + ex.getMessage());
			// Aquí puedes mostrar un mensaje de error o manejar la excepción de otra manera
		} catch (NumberFormatException ex) {
			System.out.println("Error al convertir la edad a entero: " + ex.getMessage());
			// Manejar la excepción si la edad ingresada no es un número (código comentado
			// por ahora)
		}
	}
}
