package observable;

import observers.IObserver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SensorTest {

    Sensor sensor;
    Observermock observer;

    private String getExpectedAlert (int temperature, String highOrLow) {

        String message = String.format ("Dit bericht is afkomstig van: Temperatuursensor%n" +
                "De ernst van deze melding: alert%n" +
                "Bericht:%n" +
                "De temperatuur is %d°C en dat is te %s.%n", temperature, highOrLow);

        if (highOrLow.equals ("hoog")) {
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
                "Kachel en ramen lijken weer goed te werken.%n", temperature);
    }

    private String getExpected (String typeOfMessage, int temperature) {

        String highOrLow = "";

        if (temperature > 25) {
            highOrLow = "hoog";
        }
        else if (temperature < 0) {
            highOrLow = "laag";
        }

        switch (typeOfMessage) {
            case "information": return getExpectedInformation (temperature);
            case "alert": return getExpectedAlert (temperature, highOrLow);
            default: return "";
        }
    }

    // Voor elke randwaarde voor temperatuur in combinatie met het verhogen en met
    // het verlagen van de temperatuur worden de volgende stappen doorlopen:
    // - De temperatuur van de sensor wordt ingesteld.
    // - De message van de mock voor de IObserver wordt gereset.
    // - De methode checkTemperature van de sensor wordt aangeroepen
    //   (waardoor indien van toepassing de gemockte IObserver wordt geïnformeerd).
    // - Er wordt gecontroleerd of de gemockte IObserver correct is geïnformeerd:
    //   - Of een alert is verzonden (met hulpmethode getExpectedAlert).
    //   - Of informatie is verzonden (met hulpmethode getExpectedInformation).
    //   - Of de gemockte IObserver niet is geïnformeerd (message is leeg).
    private void testPlusAndMinus (int temperature, String firstMessage, String secondMessage) {

        sensor.setTemperature (temperature);
        observer.resetMessage ();
        sensor.checkTemperature ("+");
        String expected = getExpected (firstMessage, temperature + 1);
        assertEquals (expected, observer.getMessage ());

        sensor.setTemperature (temperature);
        observer.resetMessage ();
        sensor.checkTemperature ("-");
        expected = getExpected (secondMessage, temperature - 1);
        assertEquals (expected, observer.getMessage ());
    }

    @Test
    void testSending () {

        // We maken een object van Observermock aan en mocken die als IObserver van Sensor.
        observer = new Observermock ();
        int temperature = -2;
        sensor = new Sensor (temperature);

        // De gemockte IObserver wordt geregistreerd bij de sensor.
        sensor.addObserver (observer);

        testPlusAndMinus (-2, "alert", "alert");
        testPlusAndMinus (-1, "information", "alert");
        testPlusAndMinus (0, "", "alert");
        testPlusAndMinus (1, "", "");
        testPlusAndMinus (24, "", "");
        testPlusAndMinus (25, "alert", "");
        testPlusAndMinus (26, "alert", "information");
        testPlusAndMinus (27, "alert", "alert");
    }
}

// We gebruiken Observermock als een mock voor een IObserver van de sensor.
// De methode update die geïmplementeerd moet worden voor IObserver
// geeft de variabele message een waarde die gelijk is aan de aanroep
// van de methode toString op het Object (een standaard methode).
// Deze variabele message kan worden gereset en de waarde van de
// variabele kan worden opgevraagd (voor testdoeleinden).

class Observermock implements IObserver {

    private String message;

    public String getMessage () {
        return message;
    }

    public void resetMessage () {
        message = "";
    }

    @Override
    public void update(Observable observable, Object object) {
        message = object.toString ();
    }
}