package Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
	private static final String URL = "jdbc:mysql://localhost:3306/gestion_personal";
	private static final String USUARIO = "root";
	private static final String CONTRASENA = "123456789";

	public static Connection conectar() {
		try {
			Connection connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
			System.out.println("Conexión exitosa a la base de datos.");
			return connection;
		} catch (SQLException e) {
			System.err.println("Error al conectar a la base de datos.");
			e.printStackTrace();
			return null;
		}
	}
}