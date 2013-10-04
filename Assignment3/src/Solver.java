import java.util.Stack;

public class Solver {
  
  private SearchNode initial;
  private SearchNode finalNode;
  private MinPQ<SearchNode> pq; //.insert(SN n) //.delMin()
  
  //FINISHED -- reduncancy with main
  public Solver(Board i){ //find a solution to the initial board (using the A* algorithm)
    initial = new SearchNode(i); //create the initial search node
    
  if (isSolvable()){
    //add initial search node to the pq
    pq = new MinPQ<SearchNode>(); //init pq
    pq.insert(initial);
    
    initial.board.neighbors();
    
    DO: do {
      //Get MinNode from PQ
      SearchNode minNode = pq.delMin();
      
      //Check for goal
      if (minNode.board.isGoal()){
         finalNode = minNode; //SAVE THE FINAL NODE!
         break DO;
      }
         
      //Find neighbors
      Queue<Board> q = (Queue<Board>)minNode.board.neighbors();
      
      //Create SearchNodes
      for (Board b : q){
        SearchNode newNode = new SearchNode(b, (minNode.moves+1), minNode);
        //Add to PQ if not the previous
        if (minNode.previous != null){
           if (!newNode.board.equals(minNode.previous.board)){
             pq.insert(newNode); //ONLY ADD IF IT ISNT THE PREVIOUS NODE WE JUST CAME FROM
           }
        } else {
          pq.insert(newNode);
        }
        
        
      }
        
      //Start again
      
    }while(true); //TODO : this is a problem!

  } else { //IS NOT SOLVEABLE
    System.out.println("Sorry. This board is not solveable.");
  }
    
 }
  
  //FINISHED
  public boolean isSolvable(){ //is the initial board solvable? 
    return initial.board.isSolvable();
  }
  
 //FINISHED
  public int moves(){ //min number of moves to solve initial board
    return finalNode.moves; 
  }

//FINISHED
  public Iterable<Board> solution(){ //sequence of boards in a shortest solution
    Stack<SearchNode> s = new Stack<SearchNode>();
    Queue<Board> result = new Queue<Board>();

    SearchNode tmp = finalNode;
    
    //enqueue the solution nodes
    while (tmp != null){
      //Put tmp in there
      s.push(tmp);
      //Get previous
      tmp = tmp.previous;
    }
    
    while (!s.isEmpty()){
      result.enqueue(s.pop().board);
    }
    
    return result;
  }
  
//FINISHED --- Redundancy here in NoSolutionPossible
  public static void main(String[] args){ //solve a slider puzzle (given below)
    // create initial board from file
       In in = new In(args[0]);
       int N = in.readInt();
       int[][] blocks = new int[N][N];
                                
       for (int i = 0; i < N; i++)
          for (int j = 0; j < N; j++)
             blocks[i][j] = in.readInt();
                             
       Board initial = new Board(blocks);      // solve the puzzle

       Solver solver = new Solver(initial);    // print solution to standard output
         
       if (!initial.isSolvable())
          System.out.println("No solution possible");
       else {
          System.out.println("Minimum number of moves = " + solver.moves());
                                  
          for (Board board : solver.solution())
               System.out.println(board);
       }
  }
  
  
//FINISHED
//PRIVATE INNER CLASS SEARCHNODE
  private class SearchNode implements Comparable { //private inner class Node
    
    private Board board;
    private int moves;
    private SearchNode previous;
    
    
    public SearchNode(Board b, int m, SearchNode p){
      board = b;
      moves = m;
      previous = p;
    }
    
    public SearchNode(Board initial){ //constructs the initial board
      board = initial;
      moves = 0;
      previous = null;
    }
    
    @Override
    public int compareTo(Object n){
      int result = 0;
      
      if ( n instanceof SearchNode){
         SearchNode tmp = (SearchNode)n;
         
         //ASSUME HAMMING
         if (this.board.hamming()+this.moves == tmp.board.hamming()+tmp.moves){
           result = 0; 
         } else if (this.board.hamming()+this.moves < tmp.board.hamming()+tmp.moves) {
           result = -1;
         } else if (this.board.hamming()+this.moves > tmp.board.hamming()+tmp.moves) {
           result = 1; 
         } else {
           System.out.println("Programmer Error: if-else logic is not mutually exclusive");
           result = 4242;
         }
      } else {
        result = -8008;
      }
      
      return result;  
    }
    
    public String toString(){
       StringBuilder result = new StringBuilder();
       
       result.append("Manhattan  = " + board.manhattan() + "\n");
       result.append("Hamming    = " + board.hamming() + "\n");
       
       result.append(board.toString());
      
       return result.toString();
    }
    
  }
  
}