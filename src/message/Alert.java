package message;

public class Alert extends Message {

    private static String getMessage (String lowOrHigh, int temperature) {

        String message = String.format ("De temperatuur is %d°C en dat is te %s.%n", temperature, lowOrHigh);

        if (lowOrHigh.equals ("hoog")) {
            message += "Controleer of de kachel uit is en of de ramen open zijn.";
        }
        else {
            message += "Controleer of de kachel aan is en of de ramen dicht zijn.";
        }

        return message;
    }

    public Alert (String lowOrHigh, int temperature) {
        super ("Temperatuursensor", "alert", getMessage (lowOrHigh, temperature));
    }
}
