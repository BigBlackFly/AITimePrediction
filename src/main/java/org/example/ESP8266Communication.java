package org.example;

import com.fazecast.jSerialComm.SerialPort;

public class ESP8266Communication {
    public static void main(String[] args) {
        // 获取所有可用的串口
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            System.out.println("Available port: " + port.getSystemPortName());
        }

        // 打开指定的串口
        SerialPort serialPort = SerialPort.getCommPort("COM3");
        serialPort.setBaudRate(115200);
        serialPort.setNumDataBits(8);
        serialPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        serialPort.setParity(SerialPort.NO_PARITY);

        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open the port.");
            return;
        }

        // 发送AT指令
        String atCommand = "AT\r\n";
        byte[] commandBytes = atCommand.getBytes();
        serialPort.writeBytes(commandBytes, commandBytes.length);

        // 读取响应
        byte[] readBuffer = new byte[1024];
        int numRead = serialPort.readBytes(readBuffer, readBuffer.length);
        String response = new String(readBuffer, 0, numRead);
        System.out.println("Response: " + response);

        // 关闭串口
        serialPort.closePort();
    }
}

