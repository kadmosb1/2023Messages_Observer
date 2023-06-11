package observers;

import observable.Observable;

import java.util.regex.Pattern;

public abstract class Receiver {

    protected String owner;

    protected boolean patternMatches (String regex, String stringToBeMatched) {
        Pattern pattern = Pattern.compile (regex);
        return pattern.matcher (stringToBeMatched).matches ();
    }

    protected abstract String getReceiver ();

    private String getMessage (Object message) {
        return message.toString ();
    }

    // Met deze methode wordt niet alleen het Observer Pattern geïmplementeerd.
    // Met update passen we ook het Template Method Pattern toe (dit is dus
    // de template method).
    public void update (Object object) {
        System.out.printf ("Bericht wordt verstuurd naar: %s%n%s%n",
                           getReceiver (), getMessage (object));
    }
}