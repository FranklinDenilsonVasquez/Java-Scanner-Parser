import java.util.ArrayList;
import java.io.File;                  
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Scanners {
    
    public ArrayList<String> Tokenizer(File file) throws Exception {

        ArrayList<String> tokenString = new ArrayList<String>();

        try(Scanner sc = new Scanner(file)){
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
            
            tokenString = finalTokenString(tokenString);
            System.out.println(tokenString);
            

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
      return  tokenString; 
    }

// Function that combines ":" and "=" to the assignment opperator ":="
public ArrayList<String> finalTokenString(ArrayList<String> tokens){

    ArrayList<String> finaList = tokens;
    
    // Loop through the ArrayList 
    for (int i = 0; i < finaList.size(); i++){
        if (i + 1 < finaList.size()){
                String currentToken = finaList.get(i);
                String nextToken = finaList.get(i+1);
                if (currentToken.equals(":") && nextToken.equals("=")){
                    finaList.set(i, ":=");
                    finaList.remove(i+1);
                }
            continue;
        }
    }
    return tokens;
}
}


