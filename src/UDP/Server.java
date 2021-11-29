package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

    public class Server extends Thread {
        private final DatagramSocket socket;
        private final byte[] buf = new byte[256];

        public Server() throws SocketException {
            socket = new DatagramSocket(4444);
        }

        public void run() {
            System.out.println("Server started");
            boolean running = true;
            while (running) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                String xStr = new String(packet.getData(), 0, packet.getLength()).trim();
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String yStr = new String(packet.getData(), 0, packet.getLength()).trim();
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String zStr = new String(packet.getData(), 0, packet.getLength()).trim();
                if (xStr.equals("quit") || yStr.equals("quit") || zStr.equals("quit")) {
                    running = false;
                    continue;
                }
                double x = Double.parseDouble(xStr);
                double y = Double.parseDouble(yStr);
                double z = Double.parseDouble(zStr);
                double result = calculate(x, y, z);
                String res = String.valueOf(result);
                packet = new DatagramPacket(res.getBytes(), res.getBytes().length, address, port);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket.close();
        }

        public double calculate(double x, double y, double z) {
            double module = Math.abs(Math.pow(x, x / y) - Math.sqrt(y / x));
            double numerator = Math.cos(y) - Math.exp(z / (y - x));
            double denominator = 1 + Math.pow((y - x), 2);
            return (module + (y - x) * (numerator / denominator));
        }

        public static void main(String[] args) throws SocketException {
            new Server().start();
        }

}
