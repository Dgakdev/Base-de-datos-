package Model;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import Db.ConexionDB;

public class PermisosGui extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JComboBox<String> BoxPermiso;
    private JDateChooser dateSalida;
    private JDateChooser dateEntrada;
    private JTextArea textArea;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PermisosGui frame = new PermisosGui();
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
    public PermisosGui() {
        setTitle("Permisos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 245);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarPermiso();
            }
        });
        btnGuardar.setBounds(126, 150, 89, 23);
        contentPane.add(btnGuardar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoobyGui loobyGui = new LoobyGui();
                loobyGui.setVisible(true);
                dispose(); // cierra el Jframe Actual(LobbyGui)
            }
        });
        btnVolver.setBounds(235, 150, 89, 23);
        contentPane.add(btnVolver);

        JLabel lblDocumento = new JLabel("Documento");
        lblDocumento.setBounds(27, 11, 76, 14);
        contentPane.add(lblDocumento);

        JLabel lblPermiso = new JLabel("Permiso");
        lblPermiso.setBounds(27, 36, 76, 14);
        contentPane.add(lblPermiso);

        JLabel lblFechaSalida = new JLabel("Fecha Salida");
        lblFechaSalida.setBounds(27, 60, 76, 14);
        contentPane.add(lblFechaSalida);

        JLabel lblFechaEntrada = new JLabel("Fecha Entrada");
        lblFechaEntrada.setBounds(27, 85, 84, 14);
        contentPane.add(lblFechaEntrada);

        textField = new JTextField();
        textField.setBounds(120, 8, 109, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        BoxPermiso = new JComboBox<>();
        BoxPermiso.setBounds(120, 32, 109, 22);
        BoxPermiso.addItem("Vacaciones");
        BoxPermiso.addItem("Incapacidad");
        BoxPermiso.addItem("Permiso dia");
        contentPane.add(BoxPermiso);

        dateSalida = new JDateChooser();
        dateSalida.setBounds(121, 60, 108, 20);
        contentPane.add(dateSalida);

        dateEntrada = new JDateChooser();
        dateEntrada.setBounds(121, 85, 108, 20);
        contentPane.add(dateEntrada);

        textArea = new JTextArea();
        textArea.setBounds(246, 31, 178, 74);
        contentPane.add(textArea);

        JLabel lblObservaciones = new JLabel("Observaciones");
        lblObservaciones.setBounds(295, 8, 89, 14);
        contentPane.add(lblObservaciones);
    }

    private void guardarPermiso() {
        String idDocumento = textField.getText();
        String tipoPermiso = (String) BoxPermiso.getSelectedItem();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaSalida = dateFormat.format(dateSalida.getDate());
        String fechaIngreso = dateFormat.format(dateEntrada.getDate());
        String observaciones = textArea.getText();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer la conexión a la base de datos
            conn = ConexionDB.conectar();

            // Crear la consulta SQL
            String sql = "INSERT INTO Permisos (ID_Documento, Tipo_Permiso, Fecha_Salida, Fecha_Ingreso, Observaciones) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(idDocumento));
            pstmt.setString(2, tipoPermiso);
            pstmt.setDate(3, java.sql.Date.valueOf(fechaSalida));
            pstmt.setDate(4, java.sql.Date.valueOf(fechaIngreso));
            pstmt.setString(5, observaciones);

            // Ejecutar la consulta
            pstmt.executeUpdate();

            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(this, "Permiso guardado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el permiso: " + e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
