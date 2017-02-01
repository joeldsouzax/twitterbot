import com.pi4j.io.gpio.*;

/**
 * Created by joel on 01/02/17.
 */
public class RaspberryPi {

    static void blink(){
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput myLed  = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,"My LED",PinState.LOW);
        myLed.setShutdownOptions(true,PinState.LOW);
        myLed.pulse(5000,true);
        gpio.shutdown();
    }
}
