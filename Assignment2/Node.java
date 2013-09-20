
/**
 * @status COMPLETE
 *
 * @author BuckYoung
 * @date Sep 12, 2013
 *
 * @description
 *
 */
public class Node {

    public String symbol;
    public Boolean value;
    public Node left;
    public Node right;
    public Node parent;

    public Node(String symbol) {
        this.symbol = symbol;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public Node(String symbol, Node left, Node right, Node parent) {
        this.symbol = symbol;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public static Node copy(Node n) {
        String symbolCopy = n.symbol;
        Node leftCopy = (n.left==null ? null : n.left);
        Node rightCopy = (n.right==null ? null : n.right);
        Node parentCopy = (n.parent==null ? null : n.parent);
        
        
        
        return new Node(symbolCopy, leftCopy, rightCopy, parentCopy);
    }
    
    public static Node returnMax(Node n){ //returns the absolute root of the tree
        
        if (n.parent == null){
            return n;
        } else {
            return returnMax(n.parent);
        }
    }
}
