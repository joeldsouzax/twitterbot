package RaspBerryLED;

/**
 * Created by joel on 02/02/17.
 */
public class Controller implements ButtonListener {

    private LED led;


    public Controller(LED led) {
        this.led = led;
    }

    public void buttonChanged(boolean isPressed) {

    }
}
