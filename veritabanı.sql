
DROP DATABASE IF EXISTS okul_db;
CREATE DATABASE okul_db;
USE okul_db;


CREATE TABLE kullanicilar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ad_soyad VARCHAR(100),
    kullanici_adi VARCHAR(50) UNIQUE,
    sifre VARCHAR(50),
    rol VARCHAR(20)
);

CREATE TABLE notlar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ogrenci_id INT,
    ders_adi VARCHAR(50),
    vize INT,
    final INT, 
    FOREIGN KEY (ogrenci_id) REFERENCES kullanicilar(id)
);


INSERT INTO kullanicilar (ad_soyad, kullanici_adi, sifre, rol) VALUES 

('Hünkar Kayhan', 'hoca1', '123', 'akademisyen'),      
('Mehmet Milli', 'hoca2', '123', 'akademisyen'),       


('Berat Batın Göl', 'ogrenci1', '123', 'ogrenci'),       
('Enes Gökay Arslan', 'ogrenci2', '123', 'ogrenci'),     
('Cihan Emir Bektaş', 'ogrenci3', '123', 'ogrenci'),     
('Selim Ayvaz', 'ogrenci4', '123', 'ogrenci'),           
('Aliihsan Arslan', 'ogrenci5', '123', 'ogrenci'),       
('Mert Özkök', 'ogrenci6', '123', 'ogrenci'),            
('Ahmet Yasin Çatalkaya', 'ogrenci7', '123', 'ogrenci'), 
('Gülizar Demir', 'ogrenci8', '123', 'ogrenci');         


INSERT INTO notlar (ogrenci_id, ders_adi, vize, final) VALUES 

(3, 'Nesne Tabanlı Programlama', 85, 90), (3, 'Diferansiyel Denklemler', 40, 50), (3, 'Elektrik Devreleri', 70, 80),


(4, 'Web Programlama', 90, 95), (4, 'C Programlama', 60, 65), (4, 'Ayrık Matematik', 85, 90),


(5, 'Nesne Tabanlı Programlama', 100, 100), (5, 'Matematik 2', 55, 60), (5, 'Elektrik Devreleri', 90, 95),


(6, 'C Programlama', 45, 50), (6, 'Diferansiyel Denklemler', 30, 40), (6, 'Web Programlama', 70, 75),


(7, 'Elektrik Devreleri', 65, 75), (7, 'Matematik 2', 80, 85), (7, 'Nesne Tabanlı Programlama', 55, 60),


(8, 'Ayrık Matematik', 75, 80), (8, 'Web Programlama', 50, 55), (8, 'C Programlama', 40, 45),


(9, 'Nesne Tabanlı Programlama', 95, 90), (9, 'Diferansiyel Denklemler', 85, 95), (9, 'Matematik 2', 70, 80),


(10, 'Web Programlama', 100, 100), (10, 'Nesne Tabanlı Programlama', 90, 95), (10, 'Elektrik Devreleri', 85, 90);