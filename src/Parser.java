import java.util.ArrayList;

public class Parser {
    
    public ArrayList<Token> tokens;
    public int current = 0;

    public Parser(ArrayList<Token> tokens){
        this.tokens = tokens;
    }

    // Function that attains the current token
    public Token currToken(){
        if (current < tokens.size()){
            return tokens.get(current);
        }
        return null;
    }

    // Function that gets the next token
    public Token getNextToken(){
        if (current < tokens.size()){
            return tokens.get(current++);
        }
        return null;
    }

    // Function that matches expected type to the actual type
    public boolean match(String expectedType){
        if (currToken() != null && currToken().type.equals(expectedType)){
            return true; 
        }
        return false;
    }

    // Function that returns an error message
    public void errorMessage(){
        // TODO: Update this method to return the line number as well
        System.out.println("error");
    }

    // Function that parses the program 
    public void parseProgram(){
        // TODO: Implement rule 01 of the grammar
        if (!match("PROGRAM")){
            errorMessage();
        }
    }

}
