import java.io.*;
import java.io.File;                  
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Scanners {
    
    public static void main(String[] args) throws Exception {

        String inputFile = args[0];
        String relativePath = "test/";
        File file = new File(relativePath + inputFile);


        try(Scanner sc = new Scanner(file)){
            
            System.out.println(file);
            ArrayList<String> tokenString = new ArrayList<String>();

            while (sc.hasNext()){
                String currTocken = sc.next();
                tokenString.add(currTocken);
                 System.out.println(currTocken);
                
            }
            System.out.println(tokenString);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }
}
