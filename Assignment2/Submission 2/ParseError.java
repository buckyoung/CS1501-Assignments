//Buck Young - bcy3@pitt.edu - 1045 - 9/20/13
import java.util.Stack;

/**
 * @status INCOMPLETE
 *
 * @TODO: parseChar -- final else case -- will tell us if we have an illegal
 * operator!! :D
 *
 * @author BuckYoung
 * @date Sep 14, 2013
 *
 * @description
 *
 */
public class ParseError {

    private static char[] expression;
    private static String s;
    private static String leftright;

    public static boolean parseError(String e) {
        boolean result = true;

        s = e;
        expression = e.replaceAll("\\s+", "").toCharArray(); //removes all whitespace


        /*
         * 1) Check for paren problems:
         */
        result = parseParens();

        if (!result) { //if result is  false, we should print the error
            System.out.println("Error:\n" + s + "   missing " + leftright + " parenthesis."
                    + "\nGetting next expression.\n");
        }


        /*
         * 2) Check for illegal operators
         */
        
        if (result){ //short circuit if above failed
            if (!parseIllegal()){
                result = false;
                System.out.println("Error:\n"+ s + " Illegal Binary Operator.\nGetting Next expression.\n");
            }
        }
        
        /*
         * 3) Check illegal "flow" problems:
         */
        if (result) { //if the above test returned OK, check for parens problems
            //try to prove false
            for (int i = 0; i < expression.length - 1; i++) { //n2 is i+1 so must go to .length-1
                if (!parseChars(expression[i], expression[i + 1])) { //if parsechars comes back false
                    result = false;
                    System.out.println("Error:\n" + s + "  Illegal Character: A non-atom "
                            + "must begin with '('.\nGetting next expression.\n");
                    break;
                }
            }
        }




        return result;
    }

    //Returns TRUE  :  if OK
    //Returns FALSE :  if there is a missing parens
    private static boolean parseParens() {
        boolean result = true;

        //Check for paren matching:
        Stack<Character> leftParen = new Stack<Character>();
        Stack<Character> rightParen = new Stack<Character>();



        //Push all of them onto the stack
        for (char c : expression) {

            if (c == '(') {
                leftParen.push(c);
            } else if (c == ')') {
                rightParen.push(c);
            }
        }

        //check for initial left
        if (leftParen.empty()) {
            leftright = "left";
            result = false;
        } else {
            //Check for equality
            while (!leftParen.empty()) {
                if (rightParen.empty()) {
                    leftright = "right"; //right is missing
                    result = false;
                    break;
                }
                leftParen.pop();
                rightParen.pop();
            }
            //Final Check
            if (!rightParen.empty()) {
                leftright = "left";
                result = false;
            }
        }


        return result;
    }

    /*
     * Parses every character for an illegal character (+ is illegal, for example)
     * 
     * @returns: True if OK
     * @return: False if BAD
     */
    private static boolean parseIllegal(){
        boolean result=true;
        
        for (char c: expression){
            if (!(((Character)c).toString()).matches("[A-Z!^v)(]")){ //if it does not match one of these chars
                result = false;
                break;
            }
        }
        
        return result;
    }
    
    
    /*
     * Certain chars MUST lead to other ones.
     * This method determines if char n1 can legally lead to char n2
     * 
     * Returns TRUE  : if OK
     * Returns FALSE : if BAD
     * 
     */
    private static boolean parseChars(char n1, char n2) {

        //if ! or v or ^
        if (n1 == '!' || n1 == 'v' || n1 == '^') {
            //then the next char must be ( or an ATOM
            return ((n2 == '(') || (((Character) n2).toString()).matches("[A-Z]"));
        } //else if an ATOM
        else if ((((Character) n1).toString()).matches("[A-Z]")) {
            //then the next char must be ) or v or ^
            return ((n2 == ')') || (n2 == 'v') || (n2 == '^'));
        } //else if )
        else if (n1 == ')') {
            //then the next char must be ( or ) or ^ or v
            return ((n2 == '(') || (n2 == ')') || (n2 == 'v') || (n2 == '^'));
        } //else if (
        else if (n1 == '(') {
            //then the next char must be ( or an ATOM or !
            return ((n2 == '(') || (n2 == '!') || (((Character) n2).toString()).matches("[A-Z]"));
        } else {
            //TODO
            //should ONLY get here if the n1 character is not legal!
            return false;
        }
    }
    // private static boolean parseOperators() {
    //   return true;
    //}
}
