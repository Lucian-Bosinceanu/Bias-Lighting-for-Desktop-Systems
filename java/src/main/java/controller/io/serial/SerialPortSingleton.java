package controller.io.serial;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortSingleton {

    private static SerialPort serialPort = null;
    private static String currentPortName = null;

    private SerialPortSingleton() {}

    public static SerialPort getSerialPort(String portName) throws SerialPortException {
        if (currentPortName != null && !currentPortName.equals(portName)) {
            //serialPort.closePort();
            serialPort = null;
        }
        if (serialPort == null) {
            serialPort = buildSerialPort(portName);
        }
        return serialPort;
    }

    private static SerialPort buildSerialPort(String portName) throws SerialPortException {
        SerialPort serialPort = new SerialPort(portName);

        currentPortName = portName;
        serialPort.openPort();
        serialPort.setParams(SerialPort.BAUDRATE_115200,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        serialPort.addEventListener(new SerialCommunicator.PortReader(), SerialPort.MASK_RXCHAR);
        return serialPort;
    }
}
