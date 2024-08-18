package org.example.demo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public static void main(String[] args) throws Exception {
        String message = "Hello world from java client!";
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverHost = InetAddress.getByName("127.0.0.1");
        int serverPort = UDPServer.PORT;

        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, serverHost, serverPort);

        socket.send(packet);
        socket.close();
    }
}
