package game.utility.input;

import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class ArduinoSingleInput_Digital {
    public boolean state;

    SerialPort ardu;
    Scanner data;

    public ArduinoSingleInput_Digital() {
        ardu = null;
        state = false;
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            String name = port.getDescriptivePortName();
            if (name.startsWith("Arduino"))
                ardu = port;
        }
        initialize();
    }

    private void initialize() {
        if (ardu == null)
            return;

        if (!ardu.openPort())
            return;

        ardu.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        data = new Scanner(ardu.getInputStream());

        Thread inputThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (data.hasNextLine()) {
                                int input = data.nextInt();
                                if (input == 0)
                                    state = false;
                                else
                                    state = true;
                                // System.out.println(input);
                            }
                            ardu.closePort();
                        } catch (Exception e) {
                            state = false;
                        }

                    }

                });

        try {
            inputThread.start();
        } catch (Exception e) {
            state = false;
        }

    }
    /**
     * Epic Chase by MaxKoMusic | https://maxkomusic.com/
     * Music promoted by https://www.chosic.com/free-music/all/
     * Creative Commons Attribution-ShareAlike 3.0 Unported (CC BY-SA 3.0)
     * https://creativecommons.org/licenses/by-sa/3.0/
     * 
     */

}
