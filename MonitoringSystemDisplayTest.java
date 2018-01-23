package BTCodeEvaluation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MonitoringSystemDisplayTest {

    MonitoringSystemDisplay mm;

    @Before
    public void setUp() {
        mm = new MonitoringSystemDisplay("C:\\Users\\Alvin\\IdeaProjects\\Free\\src\\BTCodeEvaluation\\test.txt");
    }


    @Test
    public void inSync(){
        mm.generated = "000001";
        mm.previousGenerated = "000000";
        assertTrue(mm.inSync());

        mm.generated = "000001";
        mm.previousGenerated = "000002";
        assertFalse(mm.inSync());
    }

    @Test
    public void isInteger(){
        assertTrue(mm.isInteger("000000"));
        assertTrue(mm.isInteger("123456789012"));
        assertFalse(mm.isInteger("1234r5135"));
    }

    @Test
    public void decipher(){
        assertTrue(mm.received.equals("1508405807468"));
        assertTrue(mm.generated.equals("1508405807480"));
        assertTrue(mm.node1.equals("luke"));
        assertTrue(mm.notification.equals("LOST"));
        assertTrue(mm.node2.equals("leia"));
    }

    @Test
    public void clearVariables(){
        mm.clearVariables();
        assertTrue(mm.received == null);
        assertTrue(mm.generated == null);
        assertTrue(mm.node1 == null);
        assertTrue(mm.node2 == null);
        assertTrue(mm.notification == null);
    }

}