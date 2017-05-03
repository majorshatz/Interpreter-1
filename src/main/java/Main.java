import java.io.IOException;

/**
 * Created by Tenorio on 4/24/2017.
 */

public class Main{

    public static void main(String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {

            Interpreter in = new Interpreter();
            in.run(Main.class.getResource("Sample.lua").getPath());



    }
}
