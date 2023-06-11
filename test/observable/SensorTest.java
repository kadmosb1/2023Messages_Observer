package observable;

import observers.IObserver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorTest {

    Sensor sensor;

    private String getExpectedAlert (int temperature, String highOrLow) {

        String message = String.format ("Dit bericht is afkomstig van: Temperatuursensor%n" +
                "De ernst van deze melding: alert%n" +
                "Bericht:%n" +
                "De temperatuur is %d°C en dat is te %s.%n", temperature, highOrLow);

        if (highOrLow.equals ("high")) {
            return message + String.format ("Controleer of de kachel uit is en of de ramen open zijn.%n");
        }
        else {
            return message + String.format ("Controleer of de kachel aan is en of de ramen dicht zijn.%n");
        }
    }

    private String getExpectedInformation (int temperature) {
        return String.format ("Dit bericht is afkomstig van: Temperatuursensor%n" +
                "De ernst van deze melding: ter informatie%n" +
                "Bericht:%n" +
                "De temperatuur is %d°C.%n" +
                "Kachel en ramen lijken weer goed te werken%n", temperature);
    }

    @Test
    void testSending () {

        // We maken een object van TestObserver aan en mocken die als IObserver van Sensor.
        TestObserver observer = new TestObserver ();
        int temperature = -2;
        sensor = new Sensor (temperature);
        // De gemockte IObserver wordt geregistreerd bij de sensor.

        // Daarna worden telkens opnieuw de volgende stappen doorlopen:
        // - Indien nodig wordt de temperatuur van de sensor ingesteld.
        // - De message van de mock voor de IObserver wordt gereset.
        // - De methode checkTemperature van de sensor wordt aangeroepen
        //   (waardoor indien van toepassing de gemockte IObserver wordt geïnformeerd).
        // - Er wordt gecontroleerd of de gemockte IObserver correct is geïnformeerd:
        //   - Of een alert is verzonden (met hulpmethode getExpectedAlert).
        //   - Of informatie is verzonden (met hulpmethode getExpectedInformation).
        //   - Of de gemockte IObserver niet is geïnformeerd (message is leeg).
    }
}

// We gebruiken TestObserve als een mock voor een IObserver van de sensor.
class TestObserver {
    // De methode update die geïmplementeerd moet worden voor IObserver
    // geeft de variabele message een waarde die gelijk is aan de aanroep
    // van de methode toString op het Object (een standaard methode).
    // Deze variabele message kan worden gereset en de waarde van de
    // variabele kan worden opgevraagd (voor testdoeleinden).
}