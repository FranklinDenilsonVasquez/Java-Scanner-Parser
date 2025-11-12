public class Token {
    String type;
    String value;
    int line;

    Token(String type, String value, int line){
        this.type = type;
        this.value = value;
        this.line = line;
    }

    public String getValue(){
        return  value;
    }

    public String getType(){
        return type;
    }

    public int getLine(){
        return line;
    }

    // Retrun the type and value
    @Override
    public String toString() {
        return "(" + value + ", " + type + ", " + line + ")";
    }

    
}
