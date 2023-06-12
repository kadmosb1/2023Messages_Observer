package observable;

import message.Alert;
import message.Information;
import message.Message;
import observers.Email;
import observers.Whatsapp;

import java.util.Scanner;

public class Sensor extends Observable {

    public static final int MINIMUM = 0;
    public static final int MAXIMUM = 25;

    int temperature;

    public Sensor (int temperature) {
        setTemperature (temperature);
    }

    public void setTemperature (int temperature) {
        this.temperature = temperature;
    }

    private void seeder () {
        // Op deze worden twee Receivers geregistreerd: één voor Email en één voor Whatsapp.
        addObserver(new Email ("k.j.vanderlelij@hhs.nl", "Karel J. van der Lelij"));
        addObserver(new Whatsapp ("0686801234", "Karel J. van der Lelij"));
    }

    protected void checkTemperature (String invoer) {

        // Op deze plek moet worden aangegeven dat de temperatuur gewijzigd is.
        setChanged ();

        if (invoer.equals ("+")) {
            temperature++;
        }
        else {
            temperature--;
        }

        Message message = null;

        // Als de temperatuur van onderaf terugkomt in de veilige range.
        if ((temperature == MINIMUM) && (invoer.equals ("+"))) {
            message = new Information (temperature);
        }
        // Als de temperatuur van bovenaf terugkomt in de veilige range.
        else if ((temperature == MAXIMUM) && (invoer.equals ("-"))) {
            message = new Information (temperature);
        }
        // Als de temperatuur onder het minimum komt of blijft.
        else if (temperature < MINIMUM) {
            message = new Alert ("laag", temperature);
        }
        // Als de temperatuur boven het maximum komt of blijft.
        else if (temperature > MAXIMUM){
            message = new Alert("hoog", temperature);
        }

        // Op deze plek moeten de observers worden geïnformeerd over
        // wijziging van de temperatuur.
        if (message != null) {
            notifyObservers (message);
        }
    }

    public static void main (String [] args) {

        Scanner toetsenbord = new Scanner (System.in);
        String invoer = "=";

        System.out.print ("Voer de starttemperatuur in: ");
        Sensor sender = new Sensor (toetsenbord.nextInt ());
        toetsenbord.nextLine ();
        sender.seeder ();

        while (invoer.equals ("+") || invoer.equals ("-") || invoer.equals ("=")) {

            if (!invoer.equals ("=")) {
                sender.checkTemperature (invoer);
            }

            System.out.println (sender.temperature + "°C");
            invoer = toetsenbord.nextLine ();
        }
    }
}