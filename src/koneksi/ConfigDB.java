package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConfigDB {

    Connection con;

    public static Connection conect() {
        try {
            // Mengganti alamat host dengan alamat host yang benar dari penyedia hosting Anda.
            // Anda dapat menggunakan alamat host atau alamat IP yang diberikan oleh penyedia hosting.
            String host = "154.41.240.1"; // Ganti dengan alamat IP yang sesuai
            String databaseName = "u559092880_fbox_db";
            String username = "u559092880_phomoria";
            String password = "Phomo5321";

//            String host = "localhost"; // Ganti dengan alamat IP yang sesuai
            //     String databaseName = "fbox_db";
            //          String username = "root";
            //      String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + databaseName, username, password);

            return con;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
