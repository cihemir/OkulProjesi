package okul_proje;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GirisEkrani extends JFrame {
    JTextField txtKullanici;
    JPasswordField txtSifre;

    public GirisEkrani() {
        setTitle("UBYS Giriþ Sistemi");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lbl1 = new JLabel("Kullanýcý Adý:");
        lbl1.setBounds(20, 20, 100, 25);
        add(lbl1);

        txtKullanici = new JTextField();
        txtKullanici.setBounds(120, 20, 140, 25);
        add(txtKullanici);

        JLabel lbl2 = new JLabel("Þifre:");
        lbl2.setBounds(20, 60, 100, 25);
        add(lbl2);

        txtSifre = new JPasswordField();
        txtSifre.setBounds(120, 60, 140, 25);
        add(txtSifre);

        JButton btnGiris = new JButton("Giriþ Yap");
        btnGiris.setBounds(90, 100, 100, 30);
        add(btnGiris);

        btnGiris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                girisYap();
            }
        });
        
        setVisible(true);
    }

    private void girisYap() {
        String kadi = txtKullanici.getText();
        String sifre = new String(txtSifre.getPassword());

        try {
            
            Connection con = Veritabani.baglan();
            
            
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Veritabanýna baðlanýlamadý!\n1. XAMPP açýk mý?\n2. MySQL Driver kütüphanesi ekli mi?");
                return;
            }

            String sql = "SELECT * FROM kullanicilar WHERE kullanici_adi = ? AND sifre = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, kadi);
            ps.setString(2, sifre);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String ad = rs.getString("ad_soyad");
                String rol = rs.getString("rol");
                
                User girisYapan = new User(id, ad, rol);
                this.dispose(); 

                if (rol.equals("akademisyen")) {
                    new HocaPaneli(girisYapan).setVisible(true);
                } else {
                    new OgrenciPaneli(girisYapan).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Hatalý kullanýcý adý veya þifre!");
            }
            con.close();
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(this, "Büyük Hata Oluþtu:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}