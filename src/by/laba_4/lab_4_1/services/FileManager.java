package by.laba_4.lab_4_1.services;

import java.io.*;


public class FileManager {

    private static StringBuilder resultStringBuilder;


    public static String readFromFile(String pathName, String word){
        resultStringBuilder = new StringBuilder();
        File file = new File(pathName);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;
            while ((st = br.readLine()) != null)
                resultStringBuilder.append(st);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return   findSentencesByWord(word);
    }

    public static void writeIntoFile(String pathName, String msg) {
        try (FileWriter writer = new FileWriter(pathName, true))
        {
            writer.write("Result: " + msg);
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    private static String findSentencesByWord(String word) {
        StringBuilder sentences = new StringBuilder();

        try {
            int index_end = 0;
            while (index_end >= 0) {
                int index = resultStringBuilder.indexOf(word, index_end);
                int index_begin = index;
                while (resultStringBuilder.charAt(index_begin) != '.' && index_begin > 0) {
                    index_begin--;
                }
                index_end = resultStringBuilder.indexOf(".", index);

                sentences.append(resultStringBuilder.substring(index_begin + 1, index_end + 1));
            }
            return sentences.toString();

        }
        catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());

            if (sentences.length() == 0)
                return "Nothing found...Try again.";
            return sentences.toString();
        }
    }


}
