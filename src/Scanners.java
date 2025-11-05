import java.util.ArrayList;
import java.util.List;
import java.io.File;                  
import java.io.FileNotFoundException;
import java.util.Scanner;



public class Scanners {
    
    public ArrayList<Token> Tokenizer(File file) throws Exception {

        ArrayList<String> tokenString = new ArrayList<>();
        ArrayList<Token> classifiedTokens = new ArrayList<>();
        int lineNumber = 0;

        try(Scanner sc = new Scanner(file)){
            while (sc.hasNextLine()){
                lineNumber++;
                String line = sc.nextLine();
                // Print the line number and the contents on that line for debugging 
                System.out.println(lineNumber + " " +  line);
                String[] tokens = line.split("(?=[(){};:,<>:=+\\-*/])|(?<=[(){};:,<>:=+\\-*/])|\\s");

                for (String token : tokens){
                    token = token.trim();
                    //System.out.println(token);
                    if (!token.isEmpty()){
                        tokenString.add(token);
                    }
                }
            }
            
            tokenString = finalTokenString(tokenString);
            classifiedTokens = classifyTokens(tokenString);
            System.out.println("\nClassified Tokens:");
            for (Token t : classifiedTokens) {
                System.out.println(t);
            }
            System.out.println(classifiedTokens.size());
            

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
      return  classifiedTokens; 
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
    return finaList;
    }

    public enum tokenType{
        KEYWORD, IDENTIFIER, OPERATOR, DELIMITER, NUMBER, STRING, COMMENT, UNKNOWN
    }  

    private boolean isKeyword(String t){
        return List.of("program", "begin", "end", "if", "then", "else", "input", "output", "int", "while", "loop").contains(t);
    }

    private boolean isOperator(String t){
        return List.of(":=", "<", ">", "=", "<>", "+", "-", "*", "/", "(", ")").contains(t);
    }

    private boolean isDelimiter(String t){
        return List.of(";", ",", ":").contains(t);
    }

     private boolean isNumber(String t) {
        return t.matches("\\d+");
    }

    private boolean isIdentifier(String t) {
        return t.matches("[a-zA-Z][a-zA-Z0-9_]*");
    }

    public ArrayList<Token> classifyTokens(ArrayList<String> tokens){
        ArrayList<Token> tokenList = new ArrayList<>(); 
        for (String t : tokens){
            if (isKeyword(t)){
                tokenList.add(new Token(t,tokenType.KEYWORD.toString()));
            }
            else if (isOperator(t)){
                tokenList.add(new Token(t,tokenType.OPERATOR.toString()));
            }
            else if (isDelimiter(t)){
                tokenList.add(new Token(t,tokenType.DELIMITER.toString()));
            }
            else if (isNumber(t)){
                tokenList.add(new Token(t,tokenType.NUMBER.toString()));
            }
            else if (isIdentifier(t)){
                tokenList.add(new Token(t,tokenType.IDENTIFIER.toString()));
            }
            else {
                tokenList.add(new Token(t,tokenType.UNKNOWN.toString()));
            }
            
        }

        return tokenList;
    }

}


