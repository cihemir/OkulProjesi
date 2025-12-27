package okul_proje;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane; // Hata kutusu için gerekli

public class Veritabani {
    private static final String URL = "jdbc:mysql://localhost:3306/okul_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    public static Connection baglan() {
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            

            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HATA: MySQL Driver Bulunamadý!\nLütfen indirdiðin .jar dosyasýný projeye (Build Path) ekle.");
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "HATA: Veritabaný Baðlantýsý Yok!\n" + e.getMessage());
            return null;
        }
    }
}