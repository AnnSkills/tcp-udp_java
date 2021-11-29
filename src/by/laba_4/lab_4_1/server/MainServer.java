package by.laba_4.lab_4_1.server;

import by.laba_4.lab_4_1.services.FileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static by.laba_4.lab_4_1.services.Paths.*;


public class MainServer {
    private static final Logger logger = Logger.getLogger(MainServer.class.getName());
    private static FileHandler fh;

    public static void main(String[] args) {
        setLoggerTester();

        try (ServerSocket server = new ServerSocket(8000)) {
            logger.info("Server has started.");

            while (true) {

                Socket client = server.accept();
                logger.info("New client connected: " + client.getInetAddress().getHostAddress());

                new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){

                String searchingWord;
                while ((searchingWord = reader.readLine()) != null) {

                    System.out.println("Sent from the client word: " + searchingWord);
                    logger.info("Sent from the client word: " + searchingWord);

                    FileManager.writeIntoFile(outputFile_path, "Searching: " + searchingWord);
                    String answer = FileManager.readFromFile(inputFile_path, searchingWord);
                    writer.println(answer);

                    FileManager.writeIntoFile(outputFile_path, answer);
                    logger.info("Result: " + answer);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void setLoggerTester() {

        SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");
        try {
            fh = new FileHandler("D:\\5 СЕМ\\Lab_4_Connections\\src\\resources\\MyLogFile_"
                    + format.format(Calendar.getInstance().getTime()) + ".log");
        } catch (Exception e) {
            e.printStackTrace();
        }

        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }
}