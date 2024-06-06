package Model;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Db.ConexionDB;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ModificacionDatosGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDocumento;
	private JTable tablePersonal;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificacionDatosGui frame = new ModificacionDatosGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ModificacionDatosGui() {
		setTitle("Modificacion Datos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		// inicializa tabla personal
		tablePersonal = new JTable();
		JScrollPane scrMPersonal = new JScrollPane(tablePersonal);
		scrMPersonal.setBounds(10, 55, 603, 125);
		contentPane.add(scrMPersonal);

		cargarDatosTablaPersonal();

		tablePersonal = new JTable();
		scrMPersonal.setViewportView(tablePersonal);

		JLabel lblDocumento = new JLabel("Documento");
		lblDocumento.setBounds(35, 25, 75, 14);
		contentPane.add(lblDocumento);

		txtDocumento = new JTextField();
		txtDocumento.setBounds(120, 22, 86, 20);
		contentPane.add(txtDocumento);
		txtDocumento.setColumns(10);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String numeroDocumento = txtDocumento.getText().trim();
				if (numeroDocumento.isEmpty()) {
					cargarDatosTablaPersonal();
				} else {
					buscarPorNumeroDocumento(numeroDocumento);
				}
			}
		});
		btnBuscar.setBounds(230, 19, 89, 23);
		contentPane.add(btnBuscar);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tablePersonal.getSelectedRow();
				if (filaSeleccionada != -1) {
					modificar(filaSeleccionada);
				} else {
					JOptionPane.showMessageDialog(ModificacionDatosGui.this,
							"Por favor, seleccione un usuario para editar.", "Advertencia",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnModificar.setBounds(46, 204, 89, 23);
		contentPane.add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        int filaSeleccionada = tablePersonal.getSelectedRow();
		        if (filaSeleccionada != -1) {
		            int confirmacion = JOptionPane.showConfirmDialog(ModificacionDatosGui.this,
		                    "¿Está seguro de que desea eliminar este usuario?", "Confirmar eliminación",
		                    JOptionPane.YES_NO_OPTION);
		            if (confirmacion == JOptionPane.YES_OPTION) {
		                eliminarUsuario(filaSeleccionada);
		            }
		        } else {
		            JOptionPane.showMessageDialog(ModificacionDatosGui.this,
		                    "Por favor, seleccione un usuario para eliminar.", "Advertencia",
		                    JOptionPane.WARNING_MESSAGE);
		        }
			}
		});
		btnEliminar.setBounds(166, 204, 89, 23);
		contentPane.add(btnEliminar);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonalGui PersonalGui = new PersonalGui();
				PersonalGui.setVisible(true);
				dispose(); // cierra el Jframe Actual(PersonalGui)
			}
		});
		btnRegistrar.setBounds(279, 204, 89, 23);
		contentPane.add(btnRegistrar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoobyGui loobyGui = new LoobyGui();
				loobyGui.setVisible(true);
				dispose(); // cierra el Jframe Actual(LobbyGui)
			}
		});
		btnVolver.setBounds(395, 204, 89, 23);
		contentPane.add(btnVolver);
	}

	private void cargarDatosTablaPersonal() {
		try (Connection connection = ConexionDB.conectar();
				PreparedStatement preparedStatement = connection.prepareStatement(
						"SELECT ID_Documento, Tipo_de_identificacion, Nombre, Apellido, Direccion, Correo_Electronico, Telefono, Contraseña, ConfirmarContraseña, Genero, Edad FROM Personal");
				ResultSet resultSet = preparedStatement.executeQuery()) {

			DefaultTableModel tableModel = new DefaultTableModel();
			tableModel.addColumn("ID Documento");
			tableModel.addColumn("Tipo de identificación");
			tableModel.addColumn("Nombre");
			tableModel.addColumn("Apellido");
			tableModel.addColumn("Dirección");
			tableModel.addColumn("Correo Electrónico");
			tableModel.addColumn("Teléfono");
			tableModel.addColumn("Contraseña");
			tableModel.addColumn("Confirmar Contraseña");
			tableModel.addColumn("Género");
			tableModel.addColumn("Edad");

			while (resultSet.next()) {
				Object[] rowData = { resultSet.getString("ID_Documento"), resultSet.getString("Tipo_de_identificacion"),
						resultSet.getString("Nombre"), resultSet.getString("Apellido"),
						resultSet.getString("Direccion"), resultSet.getString("Correo_Electronico"),
						resultSet.getString("Telefono"), resultSet.getString("Contraseña"),
						resultSet.getString("ConfirmarContraseña"), resultSet.getString("Genero"),
						resultSet.getInt("Edad") };
				tableModel.addRow(rowData);
			}

			tablePersonal.setModel(tableModel);

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar los datos de la tabla Personal: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void buscarPorNumeroDocumento(String numeroDocumento) {
		try (Connection connection = ConexionDB.conectar()) {
			String queryPersonal = "SELECT * FROM Personal WHERE ID_Documento = ?";
			try (PreparedStatement preparedStatementPersonal = connection.prepareStatement(queryPersonal)) {
				preparedStatementPersonal.setString(1, numeroDocumento);
				try (ResultSet resultSetPersonal = preparedStatementPersonal.executeQuery()) {
					DefaultTableModel tableModel = new DefaultTableModel();
					tableModel.addColumn("ID Documento");
					tableModel.addColumn("Tipo de identificación");
					tableModel.addColumn("Nombre");
					tableModel.addColumn("Apellido");
					tableModel.addColumn("Dirección");
					tableModel.addColumn("Correo Electrónico");
					tableModel.addColumn("Teléfono");
					tableModel.addColumn("Contraseña");
					tableModel.addColumn("Confirmar Contraseña");
					tableModel.addColumn("Género");
					tableModel.addColumn("Edad");

					boolean encontrado = false;
					while (resultSetPersonal.next()) {
						encontrado = true;
						Object[] rowData = { resultSetPersonal.getString("ID_Documento"),
								resultSetPersonal.getString("Tipo_de_identificacion"),
								resultSetPersonal.getString("Nombre"), resultSetPersonal.getString("Apellido"),
								resultSetPersonal.getString("Direccion"),
								resultSetPersonal.getString("Correo_Electronico"),
								resultSetPersonal.getString("Telefono"), resultSetPersonal.getString("Contraseña"),
								resultSetPersonal.getString("ConfirmarContraseña"),
								resultSetPersonal.getString("Genero"), resultSetPersonal.getInt("Edad") };
						tableModel.addRow(rowData);
					}
					if (!encontrado) {
						JOptionPane.showMessageDialog(this,
								"No se encontraron resultados para el documento especificado.", "Mensaje",
								JOptionPane.INFORMATION_MESSAGE);
					}
					tablePersonal.setModel(tableModel);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error al buscar en la base de datos: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void modificar(int filaSeleccionada) {
		// obtener datos de fila seleccionada
		int ID_Documento = Integer.parseInt((String) tablePersonal.getValueAt(filaSeleccionada, 0));
		String Nombre = (String) tablePersonal.getValueAt(filaSeleccionada, 2);
		String Apellido = (String) tablePersonal.getValueAt(filaSeleccionada, 3);
		String Direccion = (String) tablePersonal.getValueAt(filaSeleccionada, 4);
		String Correo = (String) tablePersonal.getValueAt(filaSeleccionada, 5);
		String Telefono = (String) tablePersonal.getValueAt(filaSeleccionada, 6);
		String Contraseña = (String) tablePersonal.getValueAt(filaSeleccionada, 7);
		String ConfirmarContraseña = (String) tablePersonal.getValueAt(filaSeleccionada, 8);
		String Genero = (String) tablePersonal.getValueAt(filaSeleccionada, 9);
		int Edad = (int) tablePersonal.getValueAt(filaSeleccionada, 10);

		// crear un nuevo Jdialog para editar datos
		JDialog ModificarDialog = new JDialog();
		ModificarDialog.setSize(800, 800);
		ModificarDialog.getContentPane().setLayout(null);

		// Crear etiquetas y campos de texto para mostrar y editar datos
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 10, 80, 20);
		JTextField txtNombre = new JTextField(Nombre);
		txtNombre.setBounds(100, 10, 150, 20);

		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(10, 40, 80, 20);
		JTextField txtApellido = new JTextField(Apellido);
		txtApellido.setBounds(100, 40, 150, 20);

		JLabel lblDireccion = new JLabel("Direccion:");
		lblDireccion.setBounds(10, 70, 80, 20);
		JTextField txtDireccion = new JTextField(Direccion);
		txtDireccion.setBounds(100, 70, 150, 20);

		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setBounds(10, 100, 80, 20);
		JTextField txtCorreo = new JTextField(Correo);
		txtCorreo.setBounds(100, 100, 150, 20);

		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(10, 130, 80, 20);
		JTextField txtTelefono = new JTextField(Telefono);
		txtTelefono.setBounds(100, 130, 150, 20);

		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(10, 160, 80, 20);
		JTextField txtContraseña = new JTextField(Contraseña);
		txtContraseña.setBounds(100, 160, 150, 20);

		JLabel lblConfirmarContraseña = new JLabel("Confirmar Contraseña:");
		lblConfirmarContraseña.setBounds(10, 190, 150, 20);
		JTextField txtConfirmarContraseña = new JTextField(ConfirmarContraseña);
		txtConfirmarContraseña.setBounds(160, 190, 150, 20);

		JLabel lblGenero = new JLabel("Genero:");
		lblGenero.setBounds(10, 220, 80, 20);
		// Crear JComboBox para Género
		String[] generos = { " M", " F", "No Binario","No responder" };
		JComboBox<String> comboGenero = new JComboBox<>(generos);
		comboGenero.setSelectedItem(Genero);
		comboGenero.setBounds(100, 220, 150, 20);

		JLabel lblEdad = new JLabel("Edad:");
		lblEdad.setBounds(10, 250, 80, 20);
		JTextField txtEdad = new JTextField(String.valueOf(Edad));
		txtEdad.setBounds(100, 250, 150, 20);

		// Crear boton para aceptar y guardar los cambios
		JButton btnGuardarCambios = new JButton("Guardar Cambios");
		btnGuardarCambios.setBounds(100, 280, 150, 20);
		btnGuardarCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Obtener los valores editados en los campos de texto
				String nuevoNombre = txtNombre.getText();
				String nuevoApellido = txtApellido.getText();
				String nuevoDireccion = txtDireccion.getText();
				String nuevoCorreo = txtCorreo.getText();
				String nuevoTelefono = txtTelefono.getText();
				String nuevoContraseña = txtContraseña.getText();
				String nuevoConfirmarContraseña = txtConfirmarContraseña.getText();
				String nuevoGenero = (String) comboGenero.getSelectedItem();
				int nuevoEdad = Integer.parseInt(txtEdad.getText());

				// Logica para actualizar la BD
				try (Connection connection = ConexionDB.conectar();
						PreparedStatement preparedStatement = connection.prepareStatement(
								"UPDATE Personal SET Nombre=?, Apellido=?, Direccion=?, Correo_Electronico=?, Telefono=?, Contraseña=?, ConfirmarContraseña=?, Genero=?, Edad=? WHERE ID_Documento=?")) {

					// Establecer los parámetros en la sentencia SQL
					preparedStatement.setString(1, nuevoNombre);
					preparedStatement.setString(2, nuevoApellido);
					preparedStatement.setString(3, nuevoDireccion);
					preparedStatement.setString(4, nuevoCorreo);
					preparedStatement.setString(5, nuevoTelefono);
					preparedStatement.setString(6, nuevoContraseña);
					preparedStatement.setString(7, nuevoConfirmarContraseña);
					preparedStatement.setString(8, nuevoGenero);
					preparedStatement.setInt(9, nuevoEdad);
					preparedStatement.setInt(10, ID_Documento);

					// Ejecutar la actualización
					int filasAfectadas = preparedStatement.executeUpdate();

					// Verificar si la actualización fue exitosa
					if (filasAfectadas > 0) {
						System.out.println("Usuario actualizado exitosamente en la base de datos.");
					} else {
						System.out.println("No se pudo actualizar el Usuario en la base de datos.");
					}

					// Actualizar la tabla llamando a cargarDatosTabla()
					cargarDatosTablaPersonal();

					// Cierra el dialogo despues de editar el producto
					ModificarDialog.dispose();

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		// Agregar componentes al diálogo de edición
		ModificarDialog.getContentPane().add(lblNombre);
		ModificarDialog.getContentPane().add(txtNombre);
		ModificarDialog.getContentPane().add(lblApellido);
		ModificarDialog.getContentPane().add(txtApellido);
		ModificarDialog.getContentPane().add(lblDireccion);
		ModificarDialog.getContentPane().add(txtDireccion);
		ModificarDialog.getContentPane().add(lblCorreo);
		ModificarDialog.getContentPane().add(txtCorreo);
		ModificarDialog.getContentPane().add(lblTelefono);
		ModificarDialog.getContentPane().add(txtTelefono);
		ModificarDialog.getContentPane().add(lblContraseña);
		ModificarDialog.getContentPane().add(txtContraseña);
		ModificarDialog.getContentPane().add(lblConfirmarContraseña);
		ModificarDialog.getContentPane().add(txtConfirmarContraseña);
		ModificarDialog.getContentPane().add(lblGenero);
		ModificarDialog.getContentPane().add(comboGenero);
		ModificarDialog.getContentPane().add(lblEdad);
		ModificarDialog.getContentPane().add(txtEdad);
		ModificarDialog.getContentPane().add(btnGuardarCambios);

		// Hacer visible el diálogo de edición
		ModificarDialog.setVisible(true);
	}
	
	private void eliminarUsuario(int filaSeleccionada) {
	    int ID_Documento = Integer.parseInt((String) tablePersonal.getValueAt(filaSeleccionada, 0));
	    try (Connection connection = ConexionDB.conectar();
	            PreparedStatement preparedStatement = connection.prepareStatement(
	                    "DELETE FROM Personal WHERE ID_Documento=?")) {
	        preparedStatement.setInt(1, ID_Documento);
	        int filasAfectadas = preparedStatement.executeUpdate();
	        if (filasAfectadas > 0) {
	            JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.", "Éxito",
	                    JOptionPane.INFORMATION_MESSAGE);
	            cargarDatosTablaPersonal();
	        } else {
	            JOptionPane.showMessageDialog(this, "No se pudo eliminar el usuario.", "Error",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error al eliminar el usuario: " + ex.getMessage(), "Error",
	                JOptionPane.ERROR_MESSAGE);
	    }
	}
}
