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
            

            while (sc.hasNextLine()){
                String line = sc.nextLine();
                String[] tokens = line.split("(?=[(){};:,<>:=+\\-*/])|(?<=[(){};:,<>:=+\\-*/])|\\s");

                for (String token : tokens){
                    token = token.trim();
                    System.out.println(token);
                    if (!token.isEmpty()){
                        tokenString.add(token);
                    }
                }
            }
            
            System.out.println(tokenString);
            

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
      return  tokenString; 
    }
}
