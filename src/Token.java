public class Token {
    String type;
    String value;

    Token(String type, String value){
        this.type = type;
        this.value = value;
    }

    public String getValue(){
        return  value;
    }

    public String getType(){
        return type;
    }

    // Retrun the type and value
    @Override
    public String toString() {
        return "(" + value + ", " + type + ")";
    }

    
}
