import java.io.*;
import java.io.File;                  
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Scanners {
    
    public ArrayList<String> Tokenizer(File file) throws Exception {

        ArrayList<String> tokenString = new ArrayList<String>();

        try(Scanner sc = new Scanner(file)){
            
            System.out.println(file);
            

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
      return  tokenString; 
    }
}
