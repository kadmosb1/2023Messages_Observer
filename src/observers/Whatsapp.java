package observers;

public class Whatsapp extends Receiver {

    private String telephoneNumber;

    @Override
    protected String getReceiver () {
        return String.format ("\"%s\" (%s)", owner, telephoneNumber);
    }

    public Whatsapp (String telephoneNumber, String owner) {

        if (patternMatches ("^06\\d{8}$", telephoneNumber)) {
            this.telephoneNumber = telephoneNumber;
            this.owner = owner;
        }
        else {
            this.telephoneNumber = "0686801234";
            this.owner = "Front Office";
        }
    }
}