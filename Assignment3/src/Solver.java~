public class Solver {
  
  private SearchNode initial;
  public static int count;
  private MinPQ<SearchNode> pq; //.insert(SN n) //.delMin()
  
  public Solver(Board i){ //find a solution to the initial board (using the A* algorithm)
    initial = new SearchNode(i); //create the initial search node
    count = 0; //init count 
    pq = new MinPQ<SearchNode>(); //init pq
    
    //add initial search node to the pq
    pq.insert(initial);
    
    do{
      SearchNode minNode = pq.delMin(); //pull min
      count++; //iterate count
      
      if (!minNode.isGoal()){
        Iterator<Board> i = minNode.neighbors; //get neighbor iterator
      
        //create next node and insert it into the PQ
        while(i.hasNext()){
          Board tmp = i.next();
          SearchNode tempNode = new SearchNode(tmp, count, minNode);
          pq.insert(tempNode);
        }
      }
         
    }while(!minNode.isGoal());
    
    
  }
  
  //FINISHED
  public boolean isSolvable(){ //is the initial board solvable? 
    return initial.board.isSolvable();
  }
  
 //FINISHED
  public int moves(){ //min number of moves to solve initial board
    return count;
  }
  
  public Iterable<Board> solution(){ //sequence of boards in a shortest solution
    return null;
  }
  
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
  
   
//PRIVATE INNER CLASS SEARCHNODE
  private class SearchNode implements Comparable { //private inner class Node
    
    public Board board;
    public int moves;
    public SearchNode previous;
    
    
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
         if (this.board.equals(tmp.board)){
           result = 0; 
         } else if (this.board.hamming() < tmp.board.hamming()) {
           result = -1;
         } else if (this.board.hamming() > tmp.board.hamming()) {
           result = 1; 
         } else {
           System.out.println("Programming Err: if-else logic is not mutually exclusive");
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