package observable;

import message.Alert;
import message.Information;
import message.Message;
import observers.Email;
import observers.Whatsapp;

import java.util.Scanner;

public class Sensor {

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
        new Email ("k.j.vanderlelij@hhs.nl", "Karel J. van der Lelij");
        new Whatsapp ("0686801234", "Karel J. van der Lelij");
    }

    protected void checkTemperature (String invoer) {

        // Op deze plek moet worden aangegeven dat de temperatuur gewijzigd is.

        if (invoer.equals ("+")) {
            temperature++;
        }
        else {
            temperature--;
        }

        Message message = null;

        // Als de temperatuur terugkomt op het minimum
        if ((temperature == MINIMUM) && (invoer.equals ("+"))) {
            message = new Information (temperature);
        }
        else if ((temperature == MAXIMUM) && (invoer.equals ("-"))) {
            message = new Information (temperature);
        }
        else if (temperature < MINIMUM) {
            message = new Alert ("laag", temperature);
        }
        else if (temperature > MAXIMUM){
            message = new Alert("hoog", temperature);
        }

        if (message != null) {
            // Op deze plek moeten de geregistreerde Receivers worden geïnformeerd over
            // wijzigingen in de temperatuur.
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