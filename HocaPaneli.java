package okul_proje;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HocaPaneli extends JFrame {
   
    private JTextField txtOgrenciId, txtVize, txtFinal;
    private JComboBox<String> cmbDers; 
    private JTable tableOgrenci;
    private DefaultTableModel modelOgrenci;

   
    public HocaPaneli(User user) {
        setTitle("Akademisyen Paneli - Hoþgeldiniz " + user.getAdSoyad());
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

       
        JPanel panelSol = new JPanel();
        panelSol.setLayout(new GridLayout(6, 2, 10, 10)); 
        panelSol.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        panelSol.add(new JLabel("Seçili Öðrenci ID:"));
        txtOgrenciId = new JTextField();
        txtOgrenciId.setEditable(false);
        txtOgrenciId.setBackground(Color.LIGHT_GRAY);
        panelSol.add(txtOgrenciId);

        panelSol.add(new JLabel("Ders Seçiniz:"));
       
        String[] dersler = {"Nesne Tabanlý Programlama", "Web Programlama", "C Programlama", 
                            "Matematik 2", "Ayrýk Matematik", "Diferansiyel Denklemler", "Elektrik Devreleri"};
        cmbDers = new JComboBox<>(dersler);
        panelSol.add(cmbDers);

        panelSol.add(new JLabel("Vize Notu:"));
        txtVize = new JTextField();
        panelSol.add(txtVize);

        panelSol.add(new JLabel("Final Notu:"));
        txtFinal = new JTextField();
        panelSol.add(txtFinal);

        JButton btnKaydet = new JButton("NOTU KAYDET / GÜNCELLE");
        btnKaydet.setBackground(new Color(100, 200, 100)); 
        panelSol.add(btnKaydet);
        
        JButton btnTemizle = new JButton("Temizle");
        panelSol.add(btnTemizle);

        add(panelSol);

    
        JPanel panelSag = new JPanel(new BorderLayout());
        String[] kolonlar = {"ID", "Öðrenci Adý", "Kullanýcý Adý"};
        modelOgrenci = new DefaultTableModel(kolonlar, 0);
        tableOgrenci = new JTable(modelOgrenci);
        
      
        ogrencileriListele();
        
    
        tableOgrenci.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int seciliSatir = tableOgrenci.getSelectedRow();
                if (seciliSatir != -1) {
                    String id = modelOgrenci.getValueAt(seciliSatir, 0).toString();
                    txtOgrenciId.setText(id);
                }
            }
        });

        panelSag.add(new JScrollPane(tableOgrenci), BorderLayout.CENTER);
        
        JLabel lblBilgi = new JLabel(" *Not girmek için listeden öðrenciye týklayýnýz.");
        lblBilgi.setForeground(Color.RED);
        panelSag.add(lblBilgi, BorderLayout.NORTH);
        
        add(panelSag);

     
        btnKaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notIslemiYap();
            }
        });

        btnTemizle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtOgrenciId.setText(""); 
                txtVize.setText(""); 
                txtFinal.setText("");
            }
        });
    }

   

    private void ogrencileriListele() {
        try {
            Connection con = Veritabani.baglan();
            String sql = "SELECT id, ad_soyad, kullanici_adi FROM kullanicilar WHERE rol = 'ogrenci'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            modelOgrenci.setRowCount(0);
            while(rs.next()) {
                modelOgrenci.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("ad_soyad"),
                    rs.getString("kullanici_adi")
                });
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notIslemiYap() {
        if(txtOgrenciId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen saðdaki listeden bir öðrenci seçin!");
            return;
        }

        try {
            Connection con = Veritabani.baglan();
            int ogrId = Integer.parseInt(txtOgrenciId.getText());
            
            
            String ders = cmbDers.getSelectedItem().toString();
            
            int vize = Integer.parseInt(txtVize.getText());
            int finalNot = Integer.parseInt(txtFinal.getText());

           
            String kontrolSql = "SELECT id FROM notlar WHERE ogrenci_id = ? AND ders_adi = ?";
            PreparedStatement psKontrol = con.prepareStatement(kontrolSql);
            psKontrol.setInt(1, ogrId);
            psKontrol.setString(2, ders);
            ResultSet rs = psKontrol.executeQuery();

            if (rs.next()) {
              
                String updateSql = "UPDATE notlar SET vize = ?, `final` = ? WHERE id = ?";
                PreparedStatement psUpdate = con.prepareStatement(updateSql);
                psUpdate.setInt(1, vize);
                psUpdate.setInt(2, finalNot);
                psUpdate.setInt(3, rs.getInt("id"));
                psUpdate.executeUpdate();
                JOptionPane.showMessageDialog(this, "Not Baþarýyla GÜNCELLENDÝ!");
            } else {
              
                String insertSql = "INSERT INTO notlar (ogrenci_id, ders_adi, vize, `final`) VALUES (?, ?, ?, ?)";
                PreparedStatement psInsert = con.prepareStatement(insertSql);
                psInsert.setInt(1, ogrId);
                psInsert.setString(2, ders);
                psInsert.setInt(3, vize);
                psInsert.setInt(4, finalNot);
                psInsert.executeUpdate();
                JOptionPane.showMessageDialog(this, "Not Baþarýyla EKLENDÝ!");
            }
            con.close();
            
           
            txtVize.setText(""); txtFinal.setText("");

        } catch (NumberFormatException e) {
             JOptionPane.showMessageDialog(this, "Not kýsýmlarýna sadece SAYI giriniz!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage());
        }
    }
} 
