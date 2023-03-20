package BE;

public class Location {
    private int id;
    private String name;
    private String adress;
    private int zip_code;

    public Location(int id, String name, String adress, int zip_code) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.zip_code = zip_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }
}
