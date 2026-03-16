package com.java.private_chat;

import com.java.private_chat.ChatUI;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Network extends Thread { // fix: extend Thread

    private int port;
    private ChatUI chatUI;
    private volatile boolean running = true; // fix: true
    private DatagramSocket socket;

    public Network(ChatUI chatUI, Integer port) {
        this.chatUI = chatUI;
        this.port = (port != null) ? port : 5000;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            this.socket = socket;
            byte[] buf = new byte[1024];

            while (running) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                chatUI.receiveMessage(received);
            }
        } catch (Exception e) {
            if (running) e.printStackTrace();
        }
    }

    public void send(String message) {
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] buf = message.getBytes();
            InetAddress address = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
            socket.setBroadcast(true);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        running = false;
        if (socket != null) socket.close();
    }
}