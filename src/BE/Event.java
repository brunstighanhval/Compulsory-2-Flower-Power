package BE;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {

    private int id,EvKId,max_tickets;
    private LocalDate date;
    private LocalTime start_time, end_time;
    private String notes,name;
    private int venue_id;

    public Event(int id, String name, int EvKId, LocalDate date, LocalTime start_time, LocalTime end_time, int max_tickets, String notes, int venue_id){
        this.id = id;
        this.name=name;
        this.EvKId=EvKId;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
        this.max_tickets = max_tickets;
        this.notes = notes;
        this.venue_id = venue_id;
    }

    public int getEvKId() {
        return EvKId;
    }

    public void setEvKId(int evKId) {
        EvKId = evKId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalTime start_time) {
        this.start_time = start_time;
    }

    public LocalTime getEnd_time(){
        return end_time;
    }

    public void setEnd_time(LocalTime end_time){
        this.end_time = end_time;
    }

    public int getMax_tickets() {
        return max_tickets;
    }

    public void setMax_tickets(int max_tickets) {
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

    @Override
    public String toString() {
        return name;
    }
}
