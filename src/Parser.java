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

    // Error message
    public void errorMessage() {
        if (currToken() != null){
            System.out.println("Error on line " + currToken().getLine() + currToken());
            System.exit(1);
        }
        else
            System.out.println("Error at end of input");
             System.exit(1);

    }

    // Parse program
    public void parseProgram() {
        System.out.println("PROGRAM");

        if (!match("program")) {  
            errorMessage();
            return;
        }
        while (!currToken().getType().equals("begin")){
            decl_sec();
        }

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

    }

    // Declaration section
    public void decl_sec() {
        System.out.println("DECL_SEC");

        if (currToken() != null && !currToken().getType().equals("IDENTIFIER")) {
            decl();
        }

    }

    // Single declaration
    public void decl() {
        System.out.println("DECL");

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

    }

    // Identifier list
    public void id_list() {
        System.out.println("ID_LIST");

        if (!currToken().getValue().equals("IDENTIFIER")) {
            errorMessage();
            return;
        }
        getNextToken();

        System.out.println("ID_LIST");
        while (currToken() != null && currToken().getType().equals(",")) {
            getNextToken(); 
            if (currToken() == null || !currToken().getValue().equals("IDENTIFIER")) {
                errorMessage();
                return;
            }
            getNextToken(); 
        }

    }

    // Statement section
    public void stmt_sec() {
        System.out.println("STMT_SEC");

        while (currToken() != null) {
            String stringValue = currToken().getType();  
            String typeValue = currToken().getValue();   

            if (typeValue.equals("KEYWORD")) {
            switch (stringValue) {
                case "if":
                    ifstmt();
                    break;
                case "while":
                    while_stmt();
                    break;
                case "input":
                    input_stmt();
                    break;
                case "output":
                    output_stmt();
                    break;
                case "end":
                case "else":
                    return; // exit STMT_SEC
                default:
                    errorMessage();
                    getNextToken();
            }
        } else if (typeValue.equals("IDENTIFIER")) {
            assign();
        } else {
            errorMessage();
            getNextToken();
        }
        }

    }

    // Assignment statement
    public void assign() {
        System.out.println("ASSIGN");
        getNextToken(); 
        // System.out.println(currToken());

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
        // System.out.println("FFFFFFFFFFFFF");
    }

    // Input statement
    public void input_stmt() {
        System.out.println("INPUT");
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
        System.out.println("OUTPUT");
        getNextToken(); 

        if (currToken().getValue().equals("IDENTIFIER")) {
            id_list();
        } else if (currToken().getValue().equals("NUMBER")) {
            getNextToken();
        } else {
            errorMessage();
            return;
        }
        getNextToken();

        if (!currToken().getType().equals(";")) {
            errorMessage();
            return;
        }
        getNextToken(); 
    }

    /*  Rule 19: IFSTMT -> if COMP then STMT_SEC end if ; |
                if COMP then STMT_SEC else STMT_SEC end if ;*/
    public void ifstmt() {
        System.out.println("STMT");

        if (!currToken().getType().equals("if")){
            errorMessage();
            return;
        }

        getNextToken();

        if (!currToken().getType().equals("(")){
            errorMessage();
            return;
        }
        getNextToken();

        comp();

        if (!currToken().getType().equals(")")){
            // System.out.println("he");
            errorMessage();
            return;
        }
        getNextToken();

        if (!currToken().getType().equals("then")){
            errorMessage();
            return;
        }
        getNextToken();

        stmt_sec();

        if (currToken() != null && currToken().getType().equals("else")){
            getNextToken();
            stmt_sec();
        }

        if (!currToken().getType().equals("end")){
            errorMessage();
            return;
        }
        getNextToken();

        if (!currToken().getValue().equals("KEYWORD") || !currToken().getType().equals("if")){
            errorMessage();
            return;
        }
        getNextToken();

        if (!currToken().getType().equals(";")){
            errorMessage();
            return;
        }

        getNextToken();        
    }

    public void comp(){
        System.out.println("COMP");

        operand();

        if (currToken() == null){
            errorMessage();
            return;
        }

        String type = currToken().getValue();
        String value = currToken().getType();

        if (!type.equals("OPERATOR") || !(value.equals("=") || value.equals("<>") 
                || value.equals(">") || value.equals("<") || value.equals(">=") 
                || value.equals("<="))){
                    errorMessage();
                    return;
        }
        getNextToken();

        operand();

    }

    public void while_stmt() {
        System.out.println("WHILESTMT");

        if (!currToken().getType().equals("while")){
            errorMessage();
            return;
        }
        getNextToken();
        
        if (currToken() == null || !currToken().getType().equals("(")){
            errorMessage();
            return;
        }
        getNextToken();

        comp();

        if (currToken() == null || !currToken().getType().equals(")")){
            errorMessage();
            return;
        }
        getNextToken();

        if (currToken() == null || !currToken().getType().equals("loop")){
            errorMessage();
            return;
        }
        getNextToken();

        stmt_sec();

        if (currToken() == null || !currToken().getType().equals("end")){
            errorMessage();
            return;
        }

        getNextToken();

        if (currToken() == null || !currToken().getType().equals("loop")){
            errorMessage();
            return;
        }
        getNextToken();

        if (currToken() == null || !currToken().getType().equals(";")){
            // System.out.println(currToken());
            errorMessage();
            return;
        }
        getNextToken();

    }

    // Rule 13: EXPR -> FACTOR | FACTOR + EXPR | FACTOR - EXPR
    public void expr() {
        System.out.println("EXPR");
        factor();

        while (currToken() != null && (currToken().getType().equals("+") 
                || currToken().getType().equals("-"))) {
            getNextToken();
            factor();
            
        }
            // System.out.println("FFFFFFFFFFF");
            
    }

    // Rule 14: FACTOR -> OPERAND | OPERAND * FACTOR | OPERAND / FACTOR
    public void factor(){
        System.out.println("FACTOR");
        operand();

        while (currToken() != null && (currToken().getType().equals("*") || currToken().getType().equals("/"))){
            getNextToken();
            operand();
        }
    }

    // Rule 15: OPERAND -> NUM | ID | ( EXPR )
    public void operand() {
        System.out.println("OPERAND");

        if (currToken() == null) {
            errorMessage();
            return;
        }

        String type = currToken().getValue();
        String value = currToken().getType();

        if (type.equals("IDENTIFIER") || type.equals("NUMBER")) {

            getNextToken(); // consume the ID or number
            //System.out.println(currToken());
            
        }
        else if (value.equals("(")) {
            getNextToken(); // consume '('
            expr();         // parse the expression inside parentheses
            

            // Now check the current token for ')'
            if (currToken() == null || !currToken().getType().equals(")")) {
                errorMessage();
                return;
            }
            getNextToken(); // consume ')'
        }
        else {
            errorMessage();
        }

    }
        
}
