import com.emillindberg.rovarspraket.*;

import java.io.*;

/**
 * Created by Emil Lindberg on 2015-12-08.
 */
public class Test {
    private static final String INPUT_TEXT_PATH = "../resources/masterdetektiven_blomkvist.txt";
    private static final String INPUT_TEXT_ENCODING= "UTF-8";
    public static void main (String[] args) {
        try {

            Reader noRovarsprak = new InputStreamReader(
                                    new FileInputStream(INPUT_TEXT_PATH ), INPUT_TEXT_ENCODING);
            printReader(noRovarsprak);
            System.out.println("\n\n");



            Reader badCapitalization =
                    new RovarspraketReader(
                            new InputStreamReader(
                                    new FileInputStream(INPUT_TEXT_PATH ), INPUT_TEXT_ENCODING));
            printReader(badCapitalization);
            System.out.println("\n\n");


            Reader goodCapitalization =
                    new RovarspraketReader(
                            new PushbackReader(
                                new InputStreamReader(
                                    new FileInputStream(INPUT_TEXT_PATH ), INPUT_TEXT_ENCODING)));
            printReader(goodCapitalization);
            System.out.println("\n\n");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void printReader(Reader reader) throws IOException{

            int c;
            while ((c = reader.read()) >= 0) {
                System.out.print((char)c);
            }

    }
}
