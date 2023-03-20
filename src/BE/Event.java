package BE;

import java.sql.Time;
import java.util.Date;

public class Event {

    private int id;
    private Date date;
    private Time start_time;
    private Time end_time;
    private String max_tickets;
    private String notes;
    private int venue_id;


    private Event(int ID, Date date, Time start_time, Time end_time, String max_tickets, String notes, int venue_id){
        this.id = id;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.max_tickets = max_tickets;
        this.notes = notes;
        this.venue_id = venue_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStart_time() {
        return start_time;
    }

    public void setStart_time(Time start_time) {
        this.start_time = start_time;
    }

    public Time getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Time end_time) {
        this.end_time = end_time;
    }

    public String getMax_tickets() {
        return max_tickets;
    }

    public void setMax_tickets(String max_tickets) {
        this.max_tickets = max_tickets;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(int venue_id) {
        this.venue_id = venue_id;
    }
}
