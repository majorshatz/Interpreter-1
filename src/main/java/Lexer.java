import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

/**
 * Created by Tenorio on 4/23/2017.
 */
public class Lexer {

    public PushbackReader fp;
    private int counter = 1;

    public Lexer(String file){
        try {
            fp = new PushbackReader(new  FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print("File not found");
        }
    }


    public Token nextToken() throws IOException {

        int state = 1;
        int digit = 0;
        String alphaBuffer = null;
        char ch;
        ch = (char) fp.read();
        while (true)
        {


            switch (state)
            {

                case 1:

                    switch (ch)
                    {
                        case '(':

                            return new Token("LP", "(", counter);

                        case ')':

                            return new Token("RP", ")", counter);

                        case '{':

                            return new Token("LC", "{", counter);

                        case '}':

                            return new Token("RC", "}", counter);

                        case '[':
                            return new Token("LB", "[", counter);

                        case ']':
                            return new Token("RB", "]", counter);


                        case '\n':
                            ch = (char) fp.read(); // spaces can be ignored
                            counter++;
                            continue;
                        case '\b':
                        case '\f':
                        case '\r':
                        case '\t':
                        case ' ':
                            ch = (char) fp.read(); // spaces can be ignored

                            continue;

                        case '.':

                            return new Token("PD", ".", counter);

                        case '+':

                            return new Token("SM", "+",counter);

                        case '=':

                            ch = (char) fp.read();
                            state = 6;
                            continue;

                        case '-':

                            ch = (char) fp.read();
                            state = 7;
                            continue;

                        case '*':

                            return new Token("MUL", "*",counter);

                        case '/':

                            return new Token("DIV", "/",counter);

                        default:
                            state = 2;
                            continue;
                    }

                case 2:

                    if (Character.isDigit(ch)) {

                        digit = ch - '0';
                        state = 3;
                        ch = (char) fp.read();
                    }
                    else {
                        state = 4;
                    }

                    continue;

                case 3:

                    if (Character.isDigit(ch)) {


                        digit *= 10;
                        digit += ch - '0';


                        ch = (char) fp.read();
                    }

                    else {

                        return new Token("NUM", String.valueOf(digit),counter);

                    }

                    continue;


                case 4:

                    if (Character.isAlphabetic(ch) || ch == '_') {

                        alphaBuffer = "";
                        alphaBuffer += ch;
                        state = 5;
                        ch = (char) fp.read();
                    }

                    else {
                        alphaBuffer = "";
                        alphaBuffer += ch;
                        return new Token("INVALID: ", alphaBuffer,counter);
                    }

                    continue;

                case 5:

                    if (Character.isAlphabetic(ch) || Character.isDigit(ch)|| ch == '_') {

                        alphaBuffer += ch;
                        ch = (char) fp.read();
                    }

                    else {

                        fp.unread(ch);

                        if (alphaBuffer.equals("then") || alphaBuffer.equals("type") || alphaBuffer.equals("local") || alphaBuffer.equals("end") ||
                                alphaBuffer.equals("if") || alphaBuffer.equals("while") || alphaBuffer .equals( "print") || alphaBuffer.equals("do")) {
                            return new Token("KW", alphaBuffer,counter);
                        }

                        return new Token("ID", alphaBuffer,counter);
                    }

                    continue;

                case 6:

                    if (ch == '=') {

                        return new Token("IEQ", "==",counter);
                    }

                    else {
                        return new Token("EQ", "=",counter);
                    }

                case 7:

                    if (ch == '-') {
                        alphaBuffer = "--";
                        ch = (char) fp.read();
                        state = 8;

                    }

                    else {
                        return new Token("SUB", "-",counter);
                    }
                    continue;

                case 8:

                    if (ch != '\n') {

                        alphaBuffer += ch;

                        ch = (char) fp.read();
                    }
                    else {
                        return new Token("COM", alphaBuffer,counter);
                    }

                    continue;

                case 9:

                    if (ch == '\n') {
                        state = 1;
                    }
                    continue;

            }

        }

    }
}
