import java.util.ArrayList;

public class Parser {

    public ArrayList<Token> tokens;
    public int current = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    // Get current token
    public Token currToken() {
        if (current < tokens.size()) {
            return tokens.get(current);
        }
        return null;
    }

    // Get next token and advance
    public Token getNextToken() {
        if (current < tokens.size()) {
            return tokens.get(current++);
        }
        return null;
    }

    // Match by token string 
    public boolean match(String expectedString) {
        if (currToken() != null && currToken().getType().equals(expectedString)) {
            getNextToken();
            return true;
        }
        return false;
    }

    public void someFunction() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("Currently in method: " + methodName);
    }
    // Error message
    public void errorMessage() {
        if (currToken() != null){
            System.out.println("Error on line " + currToken().getLine() + currToken());
        }
        else
            System.out.println("Error at end of input");
    }

    // Parse program
    public void parseProgram() {
        System.out.println("In parser");

        if (!match("program")) {  
            errorMessage();
            return;
        }

        decl_sec();

        if (!match("begin")) {
            errorMessage();
            return;
        }
        stmt_sec();

        if (!match("end")) {
            errorMessage();
            return;
        }

        if (!match(";")) {
            errorMessage();
            return;
        }

        System.out.println("Parsing completed successfully!");
    }

    // Declaration section
    public void decl_sec() {
        System.out.println("Entering decl_sec");

        if (currToken() != null && !currToken().getType().equals("IDENTIFIER")) {
            decl();
        }

        System.out.println("Exiting decl_sec");
    }

    // Single declaration
    public void decl() {
        System.out.println("In decl section, current token: " + currToken());

        id_list();

        if (!currToken().getType().equals(":")) {
            errorMessage();
            return;
        }
        getNextToken(); 

        if (!currToken().getValue().equals("KEYWORD")) {
            errorMessage();
            return;
        }
        
        String type = currToken().getValue(); 
        if ((type.equals("int") || type.equals("float") || type.equals("double"))) {
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

        System.out.println("Declaration parsed successfully");
    }

    // Identifier list
    public void id_list() {
        System.out.println("In id_list section, current token: " + currToken());

        if (!currToken().getValue().equals("IDENTIFIER")) {
            errorMessage();
            return;
        }
        getNextToken();

        while (currToken() != null && currToken().getType().equals(",")) {
            getNextToken(); 
            if (currToken() == null && !currToken().getType().equals("IDENTIFIER")) {
                errorMessage();
                return;
            }
            getNextToken(); 
        }

        System.out.println("ID list parsed successfully");
        
    }

    // Statement section
    public void stmt_sec() {
        System.out.println("STMT_SEC");

        while (currToken() != null) {
            String stringValue = currToken().getType();  
            String typeValue = currToken().getValue();   

            if (stringValue.equals("if")) {
                ifstmt();
            } else if (stringValue.equals("while")) {
                while_stmt();
            } else if (stringValue.equals("input")) {
                input_stmt();
            } else if (stringValue.equals("output")) {
                output_stmt();
            } else if (typeValue.equals("IDENTIFIER")) {
                assign();
            } else if (stringValue.equals("end") || stringValue.equals("else")) {
                break;
            } else {
                errorMessage();
                getNextToken();
            }
        }

        System.out.println("Exiting STMT_SEC");
    }

    // Assignment statement
    public void assign() {
        Token idToken = currToken();
        System.out.println("In assign " + idToken);
        getNextToken(); 

        if (currToken() == null || !currToken().getType().equals(":=")) {
            errorMessage();
            return;
        }
        getNextToken(); 

        expr();

        if (currToken() == null || !currToken().getType().equals(";")) {
            errorMessage();
            return;
        }
        getNextToken(); 
    }

    // Input statement
    public void input_stmt() {
        System.out.println("In input_stmt " + currToken());
        getNextToken();
        id_list();

        if (!currToken().getType().equals(";")) {
            errorMessage();
            return;
        }
        getNextToken(); 
    }

    // Output statement
    public void output_stmt() {
        System.out.println("In output_stmt " + currToken());
        getNextToken(); 

        if (currToken().getValue().equals("IDENTIFIER")) {
            id_list();
        } else if (currToken().getValue().equals("NUMBER")) {
            getNextToken();
        } else {
            errorMessage();
            return;
        }

        if (!currToken().getType().equals(";")) {
            errorMessage();
            return;
        }
        getNextToken(); 
    }

    // Dummy implementations for now
    public void ifstmt() {
        System.out.println("In ifstmt " + currToken());
        getNextToken();
    }

    public void while_stmt() {
        System.out.println("In while_stmt " + currToken());
        getNextToken();
    }

    public void expr() {
        while (currToken() != null && !currToken().getType().equals(";")) {
            getNextToken();
            
        }
    }
    
}
