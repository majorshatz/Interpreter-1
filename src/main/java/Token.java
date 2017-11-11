
public class Token {

    private String token;
    private String lexeme;
    private int line;

    public Token(String token, String lexeme, int line){

        this.token = token;
        this.lexeme = lexeme;
        this.line = line;
    }


    public int getLineNum(){
        return line;
    }

    public String getLexeme(){
        return lexeme;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public void setLexeme(String lexeme){
        this.lexeme = lexeme;
    }

}
