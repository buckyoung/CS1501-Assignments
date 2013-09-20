
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @status INCOMPLETE
 *
 * @author BuckYoung
 * @date Sep 12, 2013
 *
 * @description
 *
 */
public class ExpressionEvaluator {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ExpressionEvaluator fileName1");
        } else {
            try {
                Scanner in = new Scanner(new File(args[0]));

                //parse the key and values out of the first 26 lines of input
                for (int i = 0; i < 26; i++) { //get the first 26 lines of the input file, 
                    StringTokenizer tokenizer = new StringTokenizer(in.nextLine());
                    Expression.setAtom(tokenizer.nextToken(), tokenizer.nextToken());
                }

                //now start parsing the expressions to display
                displayerEval(in);




            } catch (FileNotFoundException ex) {
                System.out.println("Error: File not found. [" + args[0] + "]");
            }


        }

    }

    public static boolean evaluateNode(Node n) {
        boolean result = false;

        if (n.symbol.matches("[A-Z]")) { //we have an atom and, thus, a truth value
            result = Expression.getValueOf(n.symbol); //BASE
        } else if (n.symbol.equals("^")) { //AND
            result = evaluateNode(n.left) && evaluateNode(n.right);
        } else if (n.symbol.equals("v")) { //OR
            result = evaluateNode(n.left) || evaluateNode(n.right);
        } else if (n.symbol.equals("!")) { //NOT
            result = !(evaluateNode(n.right));
        }

        return result;
    }

    /*
     * This will parse a line from a file
     * Create a new expression and evaluate it
     * Then pause, waiting for the user to continue
     */
    public static void displayerEval(Scanner in) {
        In input = new In();
      
        int count = 1;
        while (in.hasNext()) {

            String expression = in.nextLine();

            if (ParseError.parseError(expression)) { //check for errors

                Expression e = new Expression(expression); //create expression object (and displays it)
                
                System.out.println(count + ".  " + expression + " = " + e.evaluate()); //spits out truth value 
                
                //DEBUG  ------ ----- ----- ----- 
               // String xxx = "f";
              //  while (!(xxx.contains("y")))  {
              //      System.out.println("Display Normalized? y");
             //       xxx = input.readString();
             //   }
                
             //   e.normalize();
                //END DEBUG ---- ---- ---- ----
                
                String c = "f";
                while (!(c.contains("y") || c.contains("n")))  {
                    System.out.println("Continue? y or n : ");
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
