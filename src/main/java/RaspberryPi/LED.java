package RaspberryPi;

import com.pi4j.io.gpio.*;

/**
 * Created by joel on 02/02/17.
 */
public class LED {
    private GpioPinDigitalOutput ledPin;
    private static final Pin[] pinMap = new Pin[]{RaspiPin.GPIO_00,RaspiPin.GPIO_01,RaspiPin.GPIO_02,RaspiPin.GPIO_03,RaspiPin.GPIO_04
    ,RaspiPin.GPIO_05,RaspiPin.GPIO_06,RaspiPin.GPIO_07,RaspiPin.GPIO_08,RaspiPin.GPIO_09,RaspiPin.GPIO_10,RaspiPin.GPIO_11
    ,RaspiPin.GPIO_12,RaspiPin.GPIO_13,RaspiPin.GPIO_14,RaspiPin.GPIO_15,RaspiPin.GPIO_16,RaspiPin.GPIO_17,RaspiPin.GPIO_18,RaspiPin.GPIO_19,RaspiPin.GPIO_20
    ,RaspiPin.GPIO_21,RaspiPin.GPIO_22,RaspiPin.GPIO_23,RaspiPin.GPIO_24,RaspiPin.GPIO_25,RaspiPin.GPIO_26,RaspiPin.GPIO_27
    ,RaspiPin.GPIO_28,RaspiPin.GPIO_29,RaspiPin.GPIO_30,RaspiPin.GPIO_31};
    private final static int defaultPin = 1;

    public LED() {
        this(defaultPin);
    }

    public LED(int defaultPin) {
        GpioController gpio = GpioFactory.getInstance();
        ledPin = gpio.provisionDigitalOutputPin(pinMap[defaultPin],PinState.LOW);
    }

    public void on(){
        ledPin.high();
    }

    public void off(){
        ledPin.low();
    }

    public boolean ison(){
        return ledPin.isHigh();
    }

    public void pulse() throws InterruptedException {
        int x = 0;
        while(x<10){
            ledPin.high();
            Thread.sleep(500);
            ledPin.low();
            Thread.sleep(500);
            x++;
        }
    }

    public void tweet() throws InterruptedException {
        ledPin.high();
        Thread.sleep(2000);
        ledPin.low();
    }



}
