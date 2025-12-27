package okul_proje;

public class User {
    private int id;
    private String adSoyad;
    private String rol;

    public User(int id, String adSoyad, String rol) {
        this.id = id;
        this.adSoyad = adSoyad;
        this.rol = rol;
    }

    public int getId() { return id; }
    public String getAdSoyad() { return adSoyad; }
    public String getRol() { return rol; }
}