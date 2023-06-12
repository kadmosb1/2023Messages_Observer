package observers;

import message.Information;
import message.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ReceiverTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream ();
    private final PrintStream originalOut = System.out;
    private Message message;
    private String expectedMessage;

    @BeforeEach
    public void setup () {
        System.setOut (new PrintStream (outContent));
        int temperature = 25;
        expectedMessage = String.format ("Dit bericht is afkomstig van: Temperatuursensor%n"  +
                                         "De ernst van deze melding: ter informatie%n" +
                                         "Bericht:%n" +
                                         "De temperatuur is %d°C.%n" +
                                         "Kachel en ramen lijken weer goed te werken.%n", temperature);
        message = new Information (temperature);
    }

    @AfterEach
    public void restoreStream () {
        System.setOut (originalOut);
    }

    @Test
    public void testEmail () {
        String email = "k.j.vanderlelij";
        String domain = "hhs.nl";
        String name = "Karel J. van der Lelij";
        String title = "Bericht wordt verstuurd naar: ";

        outContent.reset ();
        new Email (email + "@" + domain, name).update (null, message);
        assertEquals (String.format ("%s\"%s\" <%s@%s>%n%s%n", title, name, email, domain, expectedMessage), outContent.toString ());
        outContent.reset ();
        new Email (email, name).update (null, message);
        assertEquals (String.format ("%s\"Front Office\" <frontoffice@domein.nl>%n%s%n", title, expectedMessage), outContent.toString ());
    }

    @Test
    public void testWhatsapp () {
        String net = "06";
        String number = "86809876";
        String name = "Karel J. van der Lelij";
        String title = "Bericht wordt verstuurd naar: ";

        outContent.reset ();
        new Whatsapp (net + number, name).update (null, message);
        assertEquals (String.format ("%s\"%s\" (%s%s)%n%s%n", title, name, net, number, expectedMessage), outContent.toString ());
        outContent.reset ();
        new Whatsapp (number, name).update (null, message);
        assertEquals (String.format ("%s\"Front Office\" (0686801234)%n%s%n", title, expectedMessage), outContent.toString ());
    }
}