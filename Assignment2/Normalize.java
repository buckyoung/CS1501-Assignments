
import java.util.Stack;
/*
 * This class has three steps to normalize a tree, given a node
 */

public class Normalize {

    private static Stack<Node> allNodes;
    public static boolean runStep2;
    private static TreeDisplay treeDisplay; //DEBUG
    /*
     * 
     * 
     * 
     * 
     * 
     * S
     * T
     * E
     * P
     * 
     * 2
     * 
     * 
     * 
     * 
     */

    public static Node step2(Node r) {
        allNodes = new Stack<Node>();

        //Follows the same path as step1:

        Node newRoot = null;

        newRoot = r;

        while (runStep2) { //Check if we should run step 2, if not just return the root which we passed

            runStep2 = false;
            allNodes = new Stack<Node>();
            addAll(Node.returnMax(newRoot)); //Adds all the nodes to a stack starting with the root and working down

            while (!allNodes.empty()) { //while there are still members in the stack
                Node n = allNodes.pop(); //pop one
                newRoot = n;             //set the result...

                if (n.symbol.equals("^") && !runStep2) { //if it is an and, we must distribute and we havent finished a cycle
                    newRoot = distributeAnd(n); // distribute it down return the root
                }
            }


        }

        return newRoot;
    }

    //This class if for step 2 -- this checks an AND node for OR-children then returns the new subRoot
    private static Node distributeAnd(Node and) {
        Node result = and;

        Node leftChild = Node.copy(and.left);
        Node rightChild = Node.copy(and.right);



        //Check left
        if (leftChild.symbol.equals("v")) { //we have a match!
            //1. 
            moveAndDown(leftChild, and, true);
            result = leftChild; //this result may get overwritten by right child
        } else if (rightChild.symbol.equals("v")) { //Otherwise check right
            //1.
            moveAndDown(rightChild, and, false);
            result = rightChild;
        }

        return result;
    }

    //This method moves distributes an AND down one level and checks if they should
    //continue to distribute further
    private static void moveAndDown(Node or, Node and, boolean leftChildBool) {
        runStep2 = true;
        //2.Change parent
        Node parent = and.parent;
        or.parent = parent;
        //2b.set the parents new child 
        if (parent != null) {
            if (parent.left == and) {//if the not is in the parents left child position
                parent.left = or;
            } else {
                parent.right = or;
            }
        }
        //3.Save the or children
        Node orLeftChild = or.left;
        Node orRightChild = or.right;
        //4.Create two new ands with left,right,and parent set
        Node leftAnd;
        Node rightAnd;
        if (leftChildBool) {
            Node newAndRightChild = and.right;
            leftAnd = new Node("^", orLeftChild, newAndRightChild, or);
            rightAnd = new Node("^", orRightChild, newAndRightChild, or);
        } else {
            Node newAndLeftChild = and.left;
            leftAnd = new Node("^", newAndLeftChild, orLeftChild, or);
            rightAnd = new Node("^", newAndLeftChild, orRightChild, or);
        }

        //5.Set or left/right to the new ands
        or.left = leftAnd;
        or.right = rightAnd;

        //6. Check if the distribution should continue
        and = null;





    }

    /*
     * 
     * 
     * 
     * 
     * 
     * S
     * T
     * E
     * P
     * 
     * 1
     * 
     * 
     * 
     * 
     */
    public static Node step1(Node r) {
        allNodes = new Stack<Node>();
        Node newRoot = null;

        newRoot = r;
        //add all nodes in the tree starting with ROOT to the stack (recursively)
        addAll(newRoot);
        //go through each node in stack and check for a negation
        while (!allNodes.empty()) {
            Node n = allNodes.pop();
            newRoot = n; //result is the last node in the stack OR the returned root from within the if:
            if (n.symbol.equals("!")) { //If we have a negation
                newRoot = distributeNot(n); //distribute it, starting with itself (Node n) //returns the new subRoot
            }
        }

        return newRoot;
    }//end step1

    //For Step1 -- distributes a not in node n to its single right-hand child 
    private static Node distributeNot(Node not) {
        Node result;

        Node child = not.right;


        //check right child of n
        if (child.symbol.equals("v")) { //demorgan the or
            //1.Convert to an AND
            child.symbol = "^";
            moveNotDown(child, not);
            result = child;

        } else if (child.symbol.equals("^")) { //demorgan the and
            //1.Convert to an OR
            child.symbol = "v";
            moveNotDown(child, not);
            result = child;

        } else { //else: not-not or not-Atom //do nothin special
            result = not;
        }

        return result;


    }

    //This method moves distributes a not down one level and checks if they should
    //continue to distribute further
    private static void moveNotDown(Node child, Node not) {

        //2.Swap parent
        child.parent = not.parent;
        //2b.change parents child reference from the not to the new child
        Node parent = not.parent;
        if (parent != null) {
            if (parent.left == not) {//if the not is in the parents left child position
                parent.left = child;
            } else {
                parent.right = child;
            }
        }
        //3.Save the new and/or children
        Node leftNotChild = child.left;
        Node rightNotChild = child.right;
        //4.Create two new nots with left,right,and parent set
        Node leftNot = new Node("!", null, leftNotChild, child);
        Node rightNot = new Node("!", null, rightNotChild, child);
        //5.Set nots to the new and/or child/parent
        child.left = leftNot;
        child.right = rightNot;

        //6. Check if the distribution should continue
        if (leftNotChild.symbol.matches("[v^]")) { //if this is the case, we must distribute!
            distributeNot(leftNot);
        }
        if (rightNotChild.symbol.matches("[v^]")) { //must distribute!
            distributeNot(rightNot);
        }

    }

    //For Step1 and Step 2-- adds all the nodes in a tree to the stack
    private static void addAll(Node n) {

        if (n == null) {
            return;
        } else {
            allNodes.push(n);
            addAll(n.left);
            addAll(n.right);
        }
    } //end addAll
}//end Normalize

