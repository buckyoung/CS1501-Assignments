
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @status INCOMPLETE
 *
 * @author BuckYoung
 * @date Sep 13, 2013
 *
 * @description
 *
 */
public class ExpressionDisplayer {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java ExpressionEvaluator fileName1");
        } else {
            try {
                Scanner in = new Scanner(new File(args[0]));
                displayer(in);
            } catch (FileNotFoundException ex) {
                System.out.println("Error: File not found. [" + args[0] + "]");
            }
        }
    }

    /*
     * This will parse a line from a file
     * Create a new expression
     * Then pause, waiting for the user to continue
     */
    public static void displayer(Scanner in) {
        In input = new In();
        
        int count = 1;
        while (in.hasNext()) {
          
            String expression = in.nextLine();

            if (ParseError.parseError(expression)) { //check for errors

                System.out.println(count + ".  " + expression);
                Expression e = new Expression(expression);

                
                String c = "f";
                while (!(c.contains("y") || c.contains("n")))  {
                    System.out.println("Continue? y or n");
                    c = input.readString();
                }
                
                //break out if user says no
                if (c.contains("n")){
                  System.out.println("Exiting...");
                  break;
                }
                
                
              count++;
            }
        }//end while(in.hasNext)

        System.out.println("EOF reached");
        in.close();

    }//end displayer method
}
