package okul_proje;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OgrenciPaneli extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblGano;

    public OgrenciPaneli(User user) {
        setTitle("Öðrenci Bilgi Sistemi - Hoþgeldiniz " + user.getAdSoyad());
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

       
        JLabel lblBaslik = new JLabel("Ders Notlarý ve Akademik Durum", SwingConstants.CENTER);
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 18));
        lblBaslik.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblBaslik, BorderLayout.NORTH);

     
        String[] kolonlar = {"Ders Adý", "Vize (%40)", "Final (%60)", "Ortalama", "Harf", "4'lük Sistem", "Durum"};
        model = new DefaultTableModel(kolonlar, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        
        add(new JScrollPane(table), BorderLayout.CENTER);

       
        JPanel panelAlt = new JPanel();
        panelAlt.setBackground(new Color(230, 240, 255));
        lblGano = new JLabel("Hesaplanýyor...");
        lblGano.setFont(new Font("Arial", Font.BOLD, 16));
        lblGano.setForeground(new Color(0, 50, 150));
        panelAlt.add(lblGano);
        
        add(panelAlt, BorderLayout.SOUTH);

       
        notlariGetirVeHesapla(user.getId());
    }

    private void notlariGetirVeHesapla(int ogrenciId) {
        double toplamKatsayi = 0;
        int dersSayisi = 0;

        try {
            Connection con = Veritabani.baglan();
            String sql = "SELECT ders_adi, vize, `final` FROM notlar WHERE ogrenci_id = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ogrenciId);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0); 

            while (rs.next()) {
                String dersAdi = rs.getString("ders_adi");
                int vize = rs.getInt("vize");
                int finalNot = rs.getInt("final"); 

              
                double ortalama = (vize * 0.4) + (finalNot * 0.6);

                
                double katsayi = ortalama / 25.0;

                String harf;
                String durum;

                if (ortalama >= 90) { harf = "AA"; durum = "Geçti"; }
                else if (ortalama >= 85) { harf = "BA"; durum = "Geçti"; }
                else if (ortalama >= 80) { harf = "BB"; durum = "Geçti"; }
                else if (ortalama >= 75) { harf = "CB"; durum = "Geçti"; }
                else if (ortalama >= 65) { harf = "CC"; durum = "Geçti"; }
                else if (ortalama >= 58) { harf = "DC"; durum = "Þartlý"; } 
                else if (ortalama >= 50) { harf = "DD"; durum = "Þartlý"; }
                else { harf = "FF"; durum = "Kaldý"; }

                toplamKatsayi += katsayi;
                dersSayisi++;


                model.addRow(new Object[]{
                    dersAdi, 
                    vize, 
                    finalNot, 
                    Math.round(ortalama), 
                    harf, 
                    String.format("%.2f", katsayi), 
                    durum
                });
            }
            con.close();

         
            if (dersSayisi > 0) {
                double gano = toplamKatsayi / dersSayisi;
                lblGano.setText(String.format("GENEL ORTALAMA (GANO): %.2f / 4.00", gano));
            } else {
                lblGano.setText("Henüz not giriþi yok.");
            }

        } catch (Exception e) {
            e.printStackTrace(); 
            JOptionPane.showMessageDialog(this, "Hata Detayý: " + e.getMessage());
        }
    }
}