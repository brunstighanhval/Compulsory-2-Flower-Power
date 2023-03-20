package BE;

public class ZipCode {
    private int id;
    private int zip_code;

    public ZipCode(int id, int zip_code) {
        this.id = id;
        this.zip_code = zip_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }
}
