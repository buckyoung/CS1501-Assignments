public class Solver {
  
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
    
    public int compareTo(Object o){
      
      return 0;  
    }
    
  }
 
  private Board initial;
  private int moves;
  
  public Solver(Board i){ //find a solution to the initial board (using the A* algorithm)
    initial = i;
    moves = 0;
    
  }
  
  //FINISHED
  public boolean isSolvable(){ //is the initial board solvable? 
    return initial.isSolvable();
  }
  
  public int moves(){ //min number of moves to solve initial board
    return moves;
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
  
}