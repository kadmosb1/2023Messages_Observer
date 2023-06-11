package message;

public abstract class Message {

    private final String from;
    private final String level;
    private final String text;

    public Message (String from, String level, String text) {
        this.from = from;
        this.level = level;
        this.text = text;
    }

    public String toString () {
        return String.format ("Dit bericht is afkomstig van: %s%n" +
                              "De ernst van deze melding: %s%n" +
                              "Bericht:%n%s%n", from, level, text);
    }
}
