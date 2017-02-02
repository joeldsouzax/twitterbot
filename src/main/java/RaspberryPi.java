package src.main.java;

import com.pi4j.io.gpio.*;

/**
 * Created by joel on 01/02/17.
 */
public class RaspberryPi {

    static void blink(){
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput myLed  = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_18,"My LED",PinState.HIGH);
        myLed.high(); 
        gpio.shutdown();
    }
}
