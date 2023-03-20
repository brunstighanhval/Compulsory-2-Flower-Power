package BE;

public class Ticket {
    private int id;
    private int event_id;
    private String first_name;
    private String last_name;
    private String mail;
    private int type_id;

    public Ticket(int id, int event_id, String first_name, String last_name, String mail, int type_id) {
        this.id = id;
        this.event_id = event_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mail = mail;
        this.type_id = type_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}
