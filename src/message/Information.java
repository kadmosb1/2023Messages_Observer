package message;

public class Information extends Message {

    private static String getMessage (int temperature) {
        String message = String.format ("De temperatuur is %d°C.%n", temperature);
        return message + "Kachel en ramen lijken weer goed te werken.";
    }

    public Information (int temperature) {
        super ("Temperatuursensor", "ter informatie", getMessage (temperature));
    }
}
