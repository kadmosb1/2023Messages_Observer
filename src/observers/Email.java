package observers;

public class Email extends Receiver {

    private final String emailAddress;

    @Override
    protected String getReceiver () {
        return String.format ("\"%s\" <%s>", owner, emailAddress);
    }

    public Email (String emailAddress, String owner) {

        if (patternMatches ("^(.+)@(\\S+)$", emailAddress)) {
            this.owner = owner;
            this.emailAddress = emailAddress;
        }
        else {
            this.owner = "Front Office";
            this.emailAddress = "frontoffice@domein.nl";
        }
    }
}
