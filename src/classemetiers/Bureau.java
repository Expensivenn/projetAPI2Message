public class Bureau {
    private int id;
    private String sigle;
    private String tel;

    public Bureau(int id, String sigle, String tel) {
        this.id = id;
        this.sigle = sigle;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
