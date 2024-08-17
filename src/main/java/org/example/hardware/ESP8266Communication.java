package org.example.hardware;

import com.fazecast.jSerialComm.SerialPort;

/**
 * 还没调试通过
 */
public class ESP8266Communication {
    public static void main(String[] args) {
        // 获取所有可用的串口
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            System.out.println("Available port: " + port.getSystemPortName());
        }

        // 使用你的ESP8266所在的串口名称
        String portName = "/dev/ttyUSB0"; // 确认这个名称正确
        SerialPort serialPort = SerialPort.getCommPort(portName);
        serialPort.setBaudRate(115200);
        serialPort.setNumDataBits(8);
        serialPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        serialPort.setParity(SerialPort.NO_PARITY);

        System.out.println("Attempting to open port: " + portName);

        // 尝试打开串口
        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open the port. Check if the port is available and not in use by another application.");
            return;
        }

        // 等待设备准备好
        try {
            Thread.sleep(2000); // 等待2秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 发送AT指令
        String atCommand = "AT\r\n";
        byte[] commandBytes = atCommand.getBytes();
        serialPort.writeBytes(commandBytes, commandBytes.length);
        System.out.println("Sent AT command: " + atCommand);

        // 读取响应
        try {
            Thread.sleep(2000); // 等待2秒，以确保ESP8266有足够时间响应
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        byte[] readBuffer = new byte[1024];
        int numRead = serialPort.readBytes(readBuffer, readBuffer.length);
        if (numRead > 0) {
            String response = new String(readBuffer, 0, numRead);
            System.out.println("Response: " + response);
        } else {
            System.out.println("No response received.");
        }

        // 关闭串口
        serialPort.closePort();
    }
}