package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	// par�metros de conex�o
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.45.101:3306/dbavinicius";
	private String user = "dbas";
	private String password = "Senac@123";
 /**
  * Conex�o com banco de dados
  * @return
  */

	public Connection conectar() {
		// con -> objeto
		Connection con = null;
// tratamento de exce�oes
		try {

			Class.forName(driver);
		// estabelecendo a conex�o

			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
		
			System.out.println(e);
		
			return null;
		}
	}

}
