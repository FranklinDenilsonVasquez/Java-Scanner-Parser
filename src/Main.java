import java.io.*;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        String inputFile = args[0];
        String relativePath = "test/";
        File file = new File(relativePath + inputFile);
        
        Scanners scanner = new Scanners();
        try {
            ArrayList<String> arrayToken = scanner.Tokenizer(file);
            System.out.println(arrayToken);

        } catch (Exception e) {
            System.out.println("Something went wrong with Tokenizer.");
            e.printStackTrace();
        }

    }
}
