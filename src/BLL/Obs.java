package BLL;


import BE.Event;
import DAL.db.EventDAO;

import java.io.IOException;

public class Obs {
    private EventDAO dao;

    private boolean isRunning = true;

    private Update manager;
    private int lengthArray, newLength;



    public void observe(Event event) throws IOException {

        dao = new EventDAO();
        manager=new Update();



        Thread t = new Thread(() ->
        {
            try {
                lengthArray = dao.getTicketsFromEvent(event).size();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            while (isRunning) {
                try {
                    newLength = dao.getTicketsFromEvent(event).size();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


                if (lengthArray != newLength) {

                    manager.update(event);

                    lengthArray=newLength;
                }

                try {
                    Thread.sleep(2000);}
                 catch (InterruptedException e) {
                     System.out.println("This is a treat exception");;
                }

            }

        });
        t.setDaemon(true); //I mark the thread as a daemon thread, so  its terminated when I exit the app.
        t.start();


    }






}









