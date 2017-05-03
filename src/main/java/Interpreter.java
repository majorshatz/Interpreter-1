import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by Tenorio on 4/24/2017.
 */
public class Interpreter  {


    private Parser par;

    //Main.class.getResource("Sample.lua").getPath()

    public Interpreter(){

    }

    public void run(String filename) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        par = new Parser(filename);

        //LinkedList<Token>
        // LinkedList<LinkedList<Token>>[] arr;
        try {
            par.populateList();
            //par.list.toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        par.printT();

        String source = ("import java.io.IOException; \n" +
                "import java.util.*; \n" +
                "public class ex{\n" +
                " public static void main(String[] args){\n");
        for (LinkedList<Token> queue : par.list) {
            // for every token in the queue
            for (Token token : queue) {
                source += (token.getLexeme());
            }
        }

        source += ("}\n}\n}");


        // Save source in .java file.
        File root = new File(System.getProperty("user.home")); // On Windows running on C:\, this is C:\java.
        File sourceFile = new File(root, "ex.java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

// Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());


// Load and instantiate compiled class.
        Runtime rt = Runtime.getRuntime();

        rt.exec("cmd.exe /c C: && dir && cd "+System.getProperty("user.home")+" && dir && start cmd /k java ex");





}

}
