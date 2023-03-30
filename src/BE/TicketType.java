package BE;

public class TicketType {
    private int id;
    private String type_name;
    private int price;


    public TicketType(int id, String type_name, int price)
    {
        this.id = id;
        this.type_name = type_name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
