package org.example.demo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {

    public static final int PORT = 5000;

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(PORT);
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            System.out.println("Waiting for data...");
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received message: " + message);
        }
        // run forever and do not close
//        socket.close();
    }
}