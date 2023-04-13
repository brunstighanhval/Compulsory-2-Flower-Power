package GUI.Controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {
    //Arrange -setup our test objects etc.
    MainController mainController=new MainController();
    @Test
    void makeTicketNumber() {


        //Act - do the actual calc or method run
    int actualValue=mainController.makeTicketNumber(10,20);
    int expectedValue=10_000_020;

        //Assert -check if actual val is equal to expected val
        Assertions.assertEquals(expectedValue,actualValue);

    }

    @Test
    void makeTicketNumber1() {


        //Act - do the actual calc or method run
        int actualValue=mainController.makeTicketNumber(101,20);
        int expectedValue=1_000_020;

        //Assert -check if actual val is equal to expected val
        Assertions.assertEquals(expectedValue,actualValue);

    }



}