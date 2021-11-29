package by.laba_4.lab_4_1.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args)
    {
        try (Socket socket = new Socket("localhost", 8000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sc = new Scanner(System.in)
        ) {

            String line = null;

            while (!"exit".equalsIgnoreCase(line)) {

                System.out.print("Enter word for searching: ");
                line = sc.nextLine();

                out.println(line);
                out.flush();

                System.out.println("Server replied: " + in.readLine());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
