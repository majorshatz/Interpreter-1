
import java.io.IOException;
import java.util.*;

public class Parser {


    public LinkedList<Token> tokens = new LinkedList();
    public LinkedList<LinkedList<Token>> list = new LinkedList();
    private String filename;
    Lexer lex;
    public Parser(String filename) {
        this.filename = filename;
    }

    public void populateList() throws IOException {

        Lexer lex = new Lexer(filename);
        this.lex=lex;
        Token tok = lex.nextToken();
        tokens.addLast(tok);
        while (lex.fp.ready()) {

            //keywords
             if (tokens.peekLast().getLexeme().equals("if")) {
                 foundIF();
             }
             else if (tokens.peekLast().getLexeme().equals("print") ){
                     foundPrint();
             }

             else if (tokens.peekLast().getLexeme().equals( "then") ){
                foundTHEN();
            } else if (tokens.peekLast().getLexeme().equals( "while") ){
                foundWHILE();
            } else if (tokens.peekLast().getLexeme().equals( "do") ){
                foundDO();
            } else if (tokens.peekLast().getLexeme().equals( "end") ){
                foudnEND();
            }

            else if (tokens.peekLast().getToken().equals( "ID") ){
                foundID();
            }






            // Checking for new LINE
            tok = lex.nextToken();
            if(tokens.size() > 0) {
                if (tokens.peekLast().getLineNum() < tok.getLineNum()) {
                    tokens.addLast(tok);
                    newLine();
                } else {
                    tokens.addLast(tok);
                }
            }
            else {
                tokens.addLast(tok);
            }

        }

        lex.fp.close();

    }

    private void newLine() {
        //Token that belongs to the new line
        Token tmp = tokens.peekLast();
        tokens.removeLast();


        // checking special cases
        if (tmp.getLexeme().equals( "do") ){
            foundDO();
        }

        else if (tmp.getLexeme().equals( "if") ){
            tokens.addLast(new Token("IDEN", ";" , 5));
            list.addLast(new LinkedList<Token>(tokens));//because reference clear removed all tokens
            tokens.clear();
            tokens.addLast(tmp);
            foundIF();
        } else if (tmp.getToken().equals( "COM") ){
            list.addLast(new LinkedList<Token>(tokens));
            tokens.clear();
            tokens.addLast(tmp);
            foundCOMMENT();
        }



        // making sure there are no open/closed parenthesis or curly brackets
        else if (!tokens.peekLast().getToken().equals("LP")  && !tokens.peekLast().getToken().equals("LC")  && !tokens.peekLast().getToken().equals("RP") ) {
            if(tokens.peekLast().getToken().equals("SEMI")){
                tokens.removeLast();
            }
            tokens.addLast(new Token("SEMI", ";", tokens.peekLast().getLineNum()));
            list.addLast(new LinkedList<>(tokens));

            tokens.clear();
            //Pushing the first token of the new line
            tokens.addLast(tmp);

        }

    }

    private  void foundPrint()throws IOException{


        tokens.getLast().setLexeme("System.out.print(");
                //addLast(new Token("KW", "System.out.print", tokens.peekLast().getLineNum()));
        tokens.addLast(lex.nextToken());
        tokens.addLast(new Token("RP", ")", tokens.peekLast().getLineNum()));
        tokens.addLast(new Token("SEMI", ";", tokens.peekLast().getLineNum()));

    }
    // function for when a comment is found
    private void foundCOMMENT() {

        Token tmp = tokens.peekLast();
        tokens.removeLast();


        String st = tmp.getLexeme();
        String cm = null;
        int index = 0;
        for(char c : st.toCharArray()){
            if(index > 1){
                cm += c;
            }
            index++;
        }
        tmp.setLexeme(cm);
        tokens.addLast(tmp);
    }

    // function for when an END is found
    private void foudnEND() {

        Token tmp = new Token("RC", "}", tokens.peekLast().getLineNum());
        if (tokens.peekLast().getLexeme().equals( "end") ){

            tokens.removeLast();
        }
        list.addLast(new LinkedList<>(tokens));
        tokens.clear();
        tokens.addLast(tmp);

    }

    // function for when a  while loop is found
    private void foundWHILE() {

        tokens.addLast(new Token("LP", "(", tokens.peekLast().getLineNum()));

    }

    // function for when a DO is found
    private void foundDO() {

        tokens.removeLast();
        tokens.addLast(new Token("RP", ")", tokens.peekLast().getLineNum()));
        tokens.addLast(new Token("LC", "{", tokens.peekLast().getLineNum()));

    }

    // function for when an IF is found
    private void foundIF() {

        tokens.addLast(new Token("LP", "(", tokens.peekLast().getLineNum()));

    }

    // function for when an THEN is found
    private void foundTHEN() {

        Token tmp = tokens.peekLast();
        tokens.removeLast();
        tokens.addLast(new Token("RP", ")", tmp.getLineNum()));
        tokens.addLast(new Token("LC", "{", tmp.getLineNum()));
        list.addLast(new LinkedList<>(tokens));
        tokens.clear();

    }

    // function for when an comma is found
    private void foundCOMMA() {

        if (tokens.getFirst().getLexeme() != "local" || tokens.getFirst().getLexeme() != "type") {

            System.out.printf("ERROR ON LINE: %d Define kind of variable", tokens.getFirst().getLineNum());
        }
    }


    // function for when an variable is found
    private void foundID() {


        boolean search = false;

        if (tokens.getFirst().getLexeme().equals( "type") ){

            tokens.getFirst().setLexeme("int ");

            return;
        }

        // performing the search if the definition of the variable is not within the same line
        else {
            for (LinkedList<Token> queue : list) {

                search = ScanQUEUE(tokens.peekLast().getLexeme(), queue);
            }

            // Throw an error if the search return false
            if (!search) {
                System.out.printf("ERROR ON LINE: %d Define kind of variable", tokens.getFirst().getLineNum());
                System.out.println();
            }
        }


    }


    // Searches a deque for a specific token
    private boolean ScanQUEUE(String var, LinkedList<Token> queue) {


        // using a lambda function to search the
        // since we are searching a list of deques of tokens
        // and looking for a specific string
        Optional<Token> optional = queue
                .stream()
                .filter(token -> token.getLexeme().equals(var))
                .findFirst();

        return optional.isPresent();
    }


    public void printT() {
        // For every queue in the list
        //prints to file
        for (Deque<Token> queue : list) {
            // for every token in the queue
            for (Token token : queue) {
                // print the lexeme
                System.out.print(token.getLexeme());
            }

            System.out.println();
        }
    }


}