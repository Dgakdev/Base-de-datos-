package Model;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificacionDatos extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtDocumento;

    public ModificacionDatos(String documentoId) {
        super();
        initComponents();
        txtDocumento.setText(documentoId);
        txtDocumento.setEditable(false); // Desactiva la edición del documento
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes implementar la lógica para guardar los cambios en la base de datos
                // Asegúrate de validar y recopilar los datos de los campos de texto
                // Actualiza los registros en la base de datos según sea necesario
                // Cierra el cuadro de diálogo después de guardar los cambios
                dispose();
            }
        });
        btnGuardar.setBounds(277, 189, 89, 23);
        contentPane.add(btnGuardar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(68, 189, 100, 23);
        contentPane.add(btnVolver);

        JLabel lblDocumento = new JLabel("Documento");
        lblDocumento.setBounds(53, 40, 60, 14);
        contentPane.add(lblDocumento);

        txtDocumento = new JTextField();
        txtDocumento.setBounds(123, 37, 86, 20);
        contentPane.add(txtDocumento);
        txtDocumento.setColumns(10);

        // Event handlers
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra el cuadro de diálogo
            }
        });
    }
}
