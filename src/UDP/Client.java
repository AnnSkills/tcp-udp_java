package UDP;

import java.io.IOException;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private final DatagramSocket socket;
    private final InetAddress address;
    private DatagramPacket packet;

    public Client() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public void sendMessage(String msg) throws IOException {
        byte[] buf = msg.getBytes();
        packet = new DatagramPacket(buf, buf.length, address, 4444);
        socket.send(packet);
    }

    public String receiveMessage() throws IOException {
        byte[] buf = new byte[32];
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength()).trim();
    }


    public void close() {
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome, client!");
        System.out.println(" type quit to exit");
        while (true) {
            System.out.print("x: ");
            String x = scanner.nextLine();
            System.out.print("y: ");
            String y = scanner.nextLine();
            System.out.print("z: ");
            String z = scanner.nextLine();
           if (Objects.equals(x, "quit") || Objects.equals(y, "quit") || Objects.equals(z, "quit")) {
                client.sendMessage(x);
                client.sendMessage(y);
                client.sendMessage(z);
                break;
            }
                client.sendMessage(x);
                client.sendMessage(y);
                client.sendMessage(z);
                String result = client.receiveMessage();
                System.out.println("Result: " + result);
        }
        client.close();
    }
}


