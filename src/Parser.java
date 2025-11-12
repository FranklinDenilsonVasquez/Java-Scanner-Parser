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
        if (currToken() != null && currToken().getType().equals(expectedType)){
            getNextToken();
            return true; 
        }
        return false;
    }

    // Function that returns an error message
    public void errorMessage(){
        // TODO: Update this method to return the line number as well
        System.out.println("Error on line " + currToken().getLine());
    }

    // Function that parses the program 
    public void parseProgram(){
        // DEBUG
        //System.out.println("In parser");

        // TODO: Implement rule 01 of the grammar
        if (!match("program")){
            errorMessage();
            return;
        }

        // DEBUG
        //System.out.println("program detected");

        if (currToken() != null && !currToken().getValue().equals("ID")){

            // DEBUG
            //System.out.println("Current token = " + currToken());
            decl_sec();
        }

        if (!match("begin")){
            // DEBUG
            //System.out.println("expected type begin");
            errorMessage();
            return;
        }

        stmt_sec();

        if (!match("END")){
            errorMessage();
            return;
        }

        if (!match(";")){
            errorMessage();
            return;
        }


    }

    public void decl_sec(){
        // TODO: implement rule 02

        // DEBUG 
        System.out.println("Entering decl_sec");

        // Keep calling decl until you find a "begin" keyword
        while (currToken() != null && !currToken().getType().equals("begin")){
            
            decl();
        }

        // DEBUG
        System.out.println("Exiting decl_sec");       
    }
    

    // Function that defines the stmt_sec section
    public void stmt_sec(){
        // TODO: Implement rule 06

        // DEBUG
        System.out.println("STMT_SEC");

        while (currToken() != null){
            String value = currToken().getType();
            String type = currToken().getValue();

            if (value.equals("if")){
                ifstmt();
            } else if (value.equals("while")){
                while_stmt();
            } else if (value.equals("input")){
                input_stmt();
            } else if (value.equals("output")){
                output_stmt();
            } else if (type.equals("IDENTIFIER")){
                assign();
            } else if (value.equals("end") || value.equals( "else")){
                break;
            } else {
                errorMessage();
                getNextToken();
            }

        }

        //DEBUG
        System.out.println("Exiting STMT_SEC");


    }

    // Function that defines the dec section
    public void decl() {
        // DEBUG
        System.out.println("In decl section, current token: " + currToken());

        // Parse the list of identifiers
        id_list();

       
        if (!currToken().getType().equals(":")) {
            errorMessage();
            return;
        }
        getNextToken(); 

        // Expect a type keyword (int, float, double)
        if (!currToken().getValue().equals("KEYWORD")) {
            errorMessage();
            return;
        }

        
        String type = currToken().getType(); 
        if (!(type.equals("int") || type.equals("float") || type.equals("double"))) {
            System.out.println("Unknown type: " + type);
            errorMessage();
            return;
        }
        getNextToken(); 

        
        if (!currToken().getType().equals(";")) {
            errorMessage();
            return;
        }
        getNextToken(); 

        // DEBUG
        System.out.println("Declaration parsed successfully");
    }

    public void id_list() {
        // DEBUG
        System.out.println("In id_list section, current token: " + currToken());

        // First token must be an identifier
        if (!currToken().getValue().equals("IDENTIFIER")) {
            errorMessage();
            return;
        }
        getNextToken(); 

        
        while (currToken() != null && currToken().getType().equals(",")) {
            getNextToken(); 

            if (currToken() == null || !currToken().getValue().equals("IDENTIFIER")) {
                errorMessage();
                return;
            }
            getNextToken(); 
        }

        // DEBUG
        System.out.println("ID list parsed successfully");
    }

    public void ifstmt(){
        System.out.println("In ifstmt" + currToken());
        getNextToken();
    }

    public void while_stmt(){
        System.out.println("In while_stmt"+ currToken());
        getNextToken();
        

    }
    
    public void input_stmt(){
        System.out.println("In input_stmt"+ currToken());
        getNextToken();

    }

    public void output_stmt(){
        System.out.println("In output_stmt"+ currToken());
        getNextToken();

    }

    public void assign(){
        System.out.println("In assign" + currToken());
        getNextToken();

    }


}


