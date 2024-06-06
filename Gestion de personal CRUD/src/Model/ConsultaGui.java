package Model;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Db.ConexionDB;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ConsultaGui extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tablePersonal;
    private JTable tablePermisos;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ConsultaGui frame = new ConsultaGui();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ConsultaGui() {
        setTitle("Consulta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 702, 478);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrPPersonal = new JScrollPane();
        scrPPersonal.setBounds(10, 11, 643, 152);
        contentPane.add(scrPPersonal);

        tablePersonal = new JTable();
        scrPPersonal.setViewportView(tablePersonal);

        JButton btnConsulta = new JButton("Consultar");
        btnConsulta.setBounds(80, 405, 89, 23);
        contentPane.add(btnConsulta);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(198, 405, 89, 23);
        contentPane.add(btnVolver);

        JScrollPane scrPPermisos = new JScrollPane();
        scrPPermisos.setBounds(10, 174, 643, 152);
        contentPane.add(scrPPermisos);

        tablePermisos = new JTable();
        scrPPermisos.setViewportView(tablePermisos);

        JTextArea txtDocumento = new JTextArea();
        txtDocumento.setToolTipText("");
        txtDocumento.setBounds(139, 348, 148, 22);
        contentPane.add(txtDocumento);

        JLabel lblNewLabel = new JLabel("Ingrese documento");
        lblNewLabel.setBounds(25, 353, 104, 14);
        contentPane.add(lblNewLabel);

        btnConsulta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numeroDocumento = txtDocumento.getText().trim();
                if (numeroDocumento.isEmpty()) {
                    cargarDatosTablaPersonal();
                    cargarDatosTablaPermisos();
                } else {
                    buscarPorNumeroDocumento(numeroDocumento);
                }
            }
        });

        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoobyGui loobyGui = new LoobyGui();
                loobyGui.setVisible(true);
                dispose();
            }
        });
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
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la tabla Personal: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosTablaPermisos() {
        try (Connection connection = ConexionDB.conectar();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT ID_Documento, Tipo_Permiso, Fecha_Salida, Fecha_Ingreso, Observaciones FROM Permisos");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID Documento");
            tableModel.addColumn("Tipo de permiso");
            tableModel.addColumn("Fecha Salida");
            tableModel.addColumn("Fecha Entrada");
            tableModel.addColumn("Observaciones");

            while (resultSet.next()) {
                Object[] rowData = { resultSet.getString("ID_Documento"), resultSet.getString("Tipo_Permiso"),
                        resultSet.getString("Fecha_Salida"), resultSet.getString("Fecha_Ingreso"),
                        resultSet.getString("Observaciones") };
                tableModel.addRow(rowData);
            }

            tablePermisos.setModel(tableModel);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de la tabla Permisos: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarPorNumeroDocumento(String numeroDocumento) {
        try (Connection connection = ConexionDB.conectar()) {
            String queryPersonal = "SELECT * FROM Personal WHERE ID_Documento = ?";
            try (PreparedStatement preparedStatementPersonal = connection.prepareStatement(queryPersonal)) {
                preparedStatementPersonal.setString(1, numeroDocumento);
                ResultSet resultSetPersonal = preparedStatementPersonal.executeQuery();
                actualizarTabla(tablePersonal, resultSetPersonal);
            }

            String queryPermisos = "SELECT * FROM Permisos WHERE ID_Documento = ?";
            try (PreparedStatement preparedStatementPermisos = connection.prepareStatement(queryPermisos)) {
                preparedStatementPermisos.setString(1, numeroDocumento);
                ResultSet resultSetPermisos = preparedStatementPermisos.executeQuery();
                actualizarTabla(tablePermisos, resultSetPermisos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al buscar en la base de datos: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla(JTable table, ResultSet resultSet) throws SQLException {
        DefaultTableModel tableModel = new DefaultTableModel();

        if (table == tablePersonal) {
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
            while (resultSet.next()) {
                encontrado = true;
                Object[] rowData = { resultSet.getString("ID_Documento"), resultSet.getString("Tipo_de_identificacion"),
                        resultSet.getString("Nombre"), resultSet.getString("Apellido"),
                        resultSet.getString("Direccion"), resultSet.getString("Correo_Electronico"),
                        resultSet.getString("Telefono"), resultSet.getString("Contraseña"),
                        resultSet.getString("ConfirmarContraseña"), resultSet.getString("Genero"),
                        resultSet.getInt("Edad") };
                tableModel.addRow(rowData);
            }
            if (!encontrado) {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados para el documento especificado.", "Mensaje",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (table == tablePermisos) {
            tableModel.addColumn("ID Documento");
            tableModel.addColumn("Tipo de permiso");
            tableModel.addColumn("Fecha Salida");
            tableModel.addColumn("Fecha Entrada");
            tableModel.addColumn("Observaciones");

            boolean encontrado = false;
            while (resultSet.next()) {
                encontrado = true;
                Object[] rowData = { resultSet.getString("ID_Documento"), resultSet.getString("Tipo_Permiso"),
                        resultSet.getString("Fecha_Salida"), resultSet.getString("Fecha_Ingreso"),
                        resultSet.getString("Observaciones") };
                tableModel.addRow(rowData);
            }
            if (!encontrado) {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados para el documento especificado.", "Mensaje",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

        table.setModel(tableModel);
    }
}

