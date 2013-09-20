
import java.util.Hashtable;

/**
 * @status INCOMPLETE
 *
 * @author BuckYoung
 * @date Sep 12, 2013
 *
 * @description
 *
 */
public class Expression {

    private static Hashtable<String, Boolean> valueTable = new Hashtable<String, Boolean>(26);
    private TreeDisplay treeDisplay;
    private Node root;
    private String s;

    public Expression(String s) { //creates a new Expression object and its tree and displays the tree.
        this.s = s;
        treeDisplay = new TreeDisplay(s); //will set the title to the window
        root = TreeBuilder.buildTree(s); //will build the tree and return the root
        treeDisplay.setRoot(root); //will draw the tree
    }

    public static void setAtom(String atom, String value) { //sets the value of an atom.
        if (value.equals("true")) {
            valueTable.put(atom, Boolean.TRUE);
        } else if (value.equals("false")) {
            valueTable.put(atom, Boolean.FALSE);
        }
    }
    
    public static boolean getValueOf(String atom){
        return valueTable.get(atom);
    }

    public boolean evaluate() { //evaluates this expression expression.
        return ExpressionEvaluator.evaluateNode(root); //returns the value of the root, and thus of the expression
    }

    public Expression copy() { //makes a deep copy of this expression.
        return null;
    }

    public void normalize() { //converts this expression's tree to normal form.
      //1) Push negations down
      root = Normalize.step1(root);
      
      //3) Remove double negations
       root = Normalize.step3(root);
       
      //2) Distribute ^ over v
      Normalize.runStep2 = true;
      root = Normalize.step2(root);
      
      displayNormalized();
    }

    public void displayNormalized() { //displays the normalized tree.
      treeDisplay = new TreeDisplay("Normalized: "+s);
      treeDisplay.setRoot(root);
    }

    public String toString() { //returns the print form of an expression. //DONE
        return s;
    }
    
    
}
