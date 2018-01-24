package BTCodeEvaluation;

import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MonitoringSystemDisplayTest {

    MonitoringSystemDisplay monitor;

    @Before
    public void setUp() {
        monitor = new MonitoringSystemDisplay("C:\\Users\\Alvin\\IdeaProjects\\Free\\src\\BTCodeEvaluation\\test.txt");
    }


    @Test
    public void inSync(){
        monitor.generated = "000001";
        monitor.previousGenerated = "000000";
        assertTrue(monitor.inSync());

        monitor.generated = "000001";
        monitor.previousGenerated = "000002";
        assertFalse(monitor.inSync());
    }


    @Test
    public void isInteger(){
        assertTrue(monitor.isInteger("000000"));
        assertTrue(monitor.isInteger("123456789012"));
        assertFalse(monitor.isInteger("1234r5135"));
    }


    @Test
    public void decipher(){
        assertTrue(monitor.received.equals("1508405807468"));
        assertTrue(monitor.generated.equals("1508405807480"));
        assertTrue(monitor.node1.equals("luke"));
        assertTrue(monitor.notification.equals("LOST"));
        assertTrue(monitor.node2.equals("leia"));
    }


    @Test
    public void clearVariables(){
        monitor.clearVariables();
        assertTrue(monitor.received == null);
        assertTrue(monitor.generated == null);
        assertTrue(monitor.node1 == null);
        assertTrue(monitor.node2 == null);
        assertTrue(monitor.notification == null);
    }


    @Test
    public void timeStampValid(){
        monitor.received = "1500000007500";
        monitor.generated = "1500000007470";
        assertTrue(monitor.timeStampValid());

        monitor.received = "1500000007100";
        monitor.generated = "1500000007600";
        assertFalse(monitor.timeStampValid());

        monitor.received = "1500000007100";
        monitor.generated = "1501000007100";
        assertFalse(monitor.timeStampValid());
    }
}