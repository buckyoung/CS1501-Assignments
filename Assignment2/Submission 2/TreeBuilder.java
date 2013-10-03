//Buck Young - bcy3@pitt.edu - 1045 - 9/20/13
import java.util.Stack;

/**
 * @status COMPLETE
 *
 * @author BuckYoung
 * @date Sep 13, 2013
 *
 * @description
 *
 */
public class TreeBuilder {

    private static int index;
    private static String s;
    /*
     * This class builds a tree from a String expression and returns the root
     */

    public static Node buildTree(String expression) {
        Node root = null;
        index = 1; //skip the first leftparens
        s = expression;
        
        root = eval(); //recursively builds the tree

        return root;
    }

    /*
     * Recursive Tree Building:
     */
    private static Node eval() {
        Stack<Node> resultStack = new Stack<Node>();
        Stack<Node> opStack = new Stack<Node>();
        
        //Get the first character
        String c = ((Character) s.charAt(index)).toString();

        //Keep getting characters in the (scope) and create nodes out of them then add them to a stack
        while (!c.equals(")")) {

            //Decide where to place the character
            if (c.matches("[A-Z]")) {
                Node n = new Node(c);
                resultStack.push(n);

            } else if (c.matches("[v^!]")) {
                Node n = new Node(c);
                opStack.push(n);

            } else if (c.equals("(")) {
                index++; //iterate or else infinite loop!
                Node returned = eval(); //recursion
                resultStack.push(returned); //save the results
            }

            index++;
            c = ((Character) s.charAt(index)).toString();
        }

        return buildSubTreeFromStacks(resultStack, opStack);
    }

    /*
     * This class will go through the stacks and build a sub tree from them
     * and return the root node
     */
    private static Node buildSubTreeFromStacks(Stack<Node> resultStack, Stack<Node> opStack) {
        Node subRoot = null;
        Node right = null;
        Node left = null;

        if (!opStack.empty()) {
            subRoot = opStack.pop(); //set subroot

            if (subRoot.symbol.matches("[v^]")) {
              //set left and right and parent
              right = resultStack.pop();
              left = resultStack.pop();
              
              right.parent = subRoot;
              left.parent = subRoot;
              
              subRoot.right = right;
              subRoot.left = left;

            } else if (subRoot.symbol.equals("!")) {
              //set right and parent
                right = resultStack.pop();
                
                right.parent = subRoot;
                
                subRoot.right = right;
            }
        }

        return subRoot;
    }
}
