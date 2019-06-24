package controller.io.serial;

import javafx.scene.paint.Color;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import model.config.ConfigManager;

import java.util.List;
import java.util.Objects;

public class SerialCommunicator {

    private static String portName;
    private static final byte[] header = "send".getBytes();

    private static final byte LIGHT_COMMAND = (byte) 0;
    private static final byte SET_LED_COUNT_COMMAND = (byte) 1;
    private static final byte TURN_OFF_COMMAND = (byte) 2;

    private static String receivedData;
    private static long waitForResponseTime = 10;
    private static byte[] commandPackage = new byte[4];

    public static void sendLedColors(List<Color> ledColors) throws SerialPortException {

        portName = ConfigManager.getGlobalConfig().getSerialPortName();


        SerialPortSingleton.getSerialPort(portName).writeBytes(header);
        SerialPortSingleton.getSerialPort(portName).writeByte(LIGHT_COMMAND);
        SerialPortSingleton.getSerialPort(portName).writeByte((byte) ledColors.size());

        int i;

        for (i = 0; i < ledColors.size(); ++i) {
            commandPackage = buildRGBData(i, ledColors.get(i));
            SerialPortSingleton.getSerialPort(portName).writeBytes(commandPackage);

        }

    }

    private static byte[] buildRGBData(int index, Color color) {
        String rgbOrder = ConfigManager.getGlobalConfig().getStripRgbOrder().toString();
        commandPackage[0] = (byte) index;
        commandPackage[rgbOrder.indexOf("R") + 1] = (byte) (color.getRed() * 255);
        commandPackage[rgbOrder.indexOf("G") + 1] = (byte) (color.getGreen() * 255);
        commandPackage[rgbOrder.indexOf("B") + 1] = (byte) (color.getBlue() * 255);
        return commandPackage;
    }

    public static void sendTurnOffCommand() throws SerialPortException {
        portName = ConfigManager.getGlobalConfig().getSerialPortName();

        receivedData = null;
        long currentTime = System.currentTimeMillis();

        while (!Objects.equals(receivedData, "&")) {
            SerialPortSingleton.getSerialPort(portName).writeBytes(header);
            SerialPortSingleton.getSerialPort(portName).writeByte(TURN_OFF_COMMAND);
            while (System.currentTimeMillis() - currentTime < waitForResponseTime) ;
        }

    }

    public static void sendSetActualLedCountCommand(int ledCount) throws SerialPortException {
        portName = ConfigManager.getGlobalConfig().getSerialPortName();
        byte actualLedCount = (byte) ledCount;

        receivedData = null;

        long currentTime = System.currentTimeMillis();
        while (!Objects.equals(receivedData, "#\n")) {
            SerialPortSingleton.getSerialPort(portName).writeBytes(header);
            SerialPortSingleton.getSerialPort(portName).writeByte(SET_LED_COUNT_COMMAND);
            SerialPortSingleton.getSerialPort(portName).writeByte(actualLedCount);
            while (System.currentTimeMillis() - currentTime < waitForResponseTime) ;
        }

    }

    static class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    portName = ConfigManager.getGlobalConfig().getSerialPortName();
                    receivedData = SerialPortSingleton.getSerialPort(portName).readString(event.getEventValue());
                    //System.out.print(receivedData);
                } catch (SerialPortException ex) {
                    System.out.println("Error in receiving string from COM-port: " + ex);
                }
            }
        }

    }

}
