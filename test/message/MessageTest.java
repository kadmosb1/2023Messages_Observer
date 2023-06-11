package message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testToString() {
        int temperature = 25;
        Message message = new Information (temperature);
        String expected = String.format ("Dit bericht is afkomstig van: Temperatuursensor%n"  +
                                         "De ernst van deze melding: ter informatie%n" +
                                         "Bericht:%n" +
                                         "De temperatuur is %d°C.%n" +
                                         "Kachel en ramen lijken weer goed te werken.%n", temperature);
        assertEquals (expected, message.toString ());
    }
}