import java.util.Stack;

public class Solver {
  
  private SearchNode initial;
  private SearchNode finalNode;
  private MinPQ<SearchNode> pq; //.insert(SN n) //.delMin()
  private static boolean solvable;
  private static boolean hammingBool = false;
  
  //FINISHED -- reduncancy with main
  public Solver(Board i){ //find a solution to the initial board (using the A* algorithm)
    initial = new SearchNode(i); //create the initial search node
    finalNode = new SearchNode(null, -1, null);
    solvable = initial.board.isSolvable();

  if (solvable){
    
    //add initial search node to the pq
    pq = new MinPQ<SearchNode>(); //init pq
    SearchNode minNode;
    Queue<Board> q;
    pq.insert(initial);
    
    DO: do {
      
      //Get MinNode from PQ
      minNode = pq.delMin();

      //Check for goal
      if (minNode.board.isGoal()){
         finalNode = minNode; //SAVE THE FINAL NODE!
         break DO;
      } else{
         
      //Find neighbors
      q = (Queue<Board>)minNode.board.neighbors();
      
      //Create SearchNodes
        for (Board b : q){
   
        if (minNode.previous != null){
          if (b.equals(minNode.previous.board)){
            //dont add
          } else { 
            //create and add
                 pq.insert(new SearchNode(b, (minNode.moves+1), minNode));
                
          }
        } else { //initial node (no minNode.previous)
                 pq.insert(new SearchNode(b, (minNode.moves+1), minNode));
        }
        
      }
        
        
      }
        
      //Start again
      
    }while(true); //TODO : this is a problem!

    pq = null;
    System.gc();//garb collect
  
    
  } else { //IS NOT SOLVEABLE
    //do nothing
  }
    
 }
  
  //FINISHED
  public static boolean isSolvable(){ //is the initial board solvable? 
    return solvable;
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
    int fileIndex = 0;
    
    //Determine flags or default
    if (args.length == 0){
      System.out.println("USAGE: java Solver file.txt");
      return;
    } else if (args.length == 1){
      hammingBool = false;
      fileIndex = 0; //set where filename will be
      System.out.println("No cmd-line flag provided: Defaulting to Manhattan Priority Function.");
      System.out.println("USAGE: java Solver -h file.txt   //For Hamming Priority Function");
      System.out.println("OR:    java Solver -m file.txt   //For Manhattan Priority Function");
      System.out.println("Running... \n");
    } else if (args.length == 2){
      if (args[0].equals("-h")){
        hammingBool = true;
        fileIndex = 1; //set where filename will be
        System.out.println("Using Hamming Priority Function\n");
      } else if (args[0].equals("-m")){
        hammingBool = false;
        fileIndex = 1; //set where filename will be 
        System.out.println("Using Manhattan Priority Function\n");
      } else {
        hammingBool = false;
        fileIndex = 1; //set where filename will be
        System.out.println("Invalid cmd-line flag: Defaulting to Manhattan Priority Function");
        System.out.println("USAGE: java Solver -h file.txt   //To Specify Hamming Priority Function");
        System.out.println("OR:    java Solver -m file.txt   //To Specify Manhattan Priority Function\n");
    }
    }
    
       In in = new In(args[fileIndex]);
       int N = in.readInt();
       int[][] blocks = new int[N][N];
                                
       for (int i = 0; i < N; i++)
          for (int j = 0; j < N; j++)
             blocks[i][j] = in.readInt();
                             
       Board initial = new Board(blocks);      // solve the puzzle

       Solver solver = new Solver(initial);    // print solution to standard output
         
       if (!Solver.isSolvable())//This should call this.isSolvable to save the board the trouble
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
      Board.primeGoal();
    }
    
    @Override
    public int compareTo(Object n){
      int result = 0;
      
      if ( n instanceof SearchNode){
         SearchNode tmp = (SearchNode)n;
         
         if(hammingBool){  
         if (this.board.hamming()+this.moves == tmp.board.hamming()+tmp.moves){
           result = 0; 
         } else if (this.board.hamming()+this.moves < tmp.board.hamming()+tmp.moves) {
           result = -1;
         } else if (this.board.hamming()+this.moves > tmp.board.hamming()+tmp.moves) {
           result = 1; 
         } 
         } else {
         if (this.board.manhattan()+this.moves == tmp.board.manhattan()+tmp.moves){
           result = 0; 
         } else if (this.board.manhattan()+this.moves < tmp.board.manhattan()+tmp.moves) {
           result = -1;
         } else if (this.board.manhattan()+this.moves > tmp.board.manhattan()+tmp.moves) {
           result = 1; 
         } 
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
       result.append("Moves      = " + moves +"\n");
       
       result.append(board.toString());
      
       return result.toString();
    }
    
  }
  
}