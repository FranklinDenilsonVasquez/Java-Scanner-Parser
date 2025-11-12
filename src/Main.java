import java.io.*;


public class Main {

    public static void main(String[] args) {
        String inputFile = args[0];
        String relativePath = "test/";
        File file = new File(relativePath + inputFile);
        
        Scanners scanner = new Scanners();
        try {
            // scanner.Tokenizer(file);
            Parser parser = new Parser(scanner.Tokenizer(file));
            //System.out.println(parser.getNextToken());
            parser.parseProgram();
        } catch (Exception e) {
            System.out.println("Something went wrong with Tokenizer.");
            e.printStackTrace();
        }

    }
}
