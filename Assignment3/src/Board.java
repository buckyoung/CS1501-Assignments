public class Board{
  
  private int[][] board;
  private int[][] goal;
  private int dimension;
  
  
//FINISHED
    public Board(int[][] blocks){ //constuct a board from an N by N array of blocks // where blocks[i][j] = block in row i, column j
       board = blocks;
       dimension = board.length;
       
       //create goal board
       goal = new int[dimension][dimension];
       int count = 1; 
       for (int i=0; i<dimension; i++){
         for (int j=0; j<dimension; j++){
             goal[i][j] = count;
             count++;
         }
       }
       goal[dimension-1][dimension-1] = 0; //set '*'
       
       System.out.println(this.isGoal());
    }
      
 //FINISHED
    public int dimension(){ //board dimension N
       return dimension;
    }
    
    public int hamming(){ //number of blocks out of place
      return 0;
    }
    
    public int manhattan(){ //sum of manhattan distances between blocks and goal
      return 0;
    }

//FINISHED
    public boolean isGoal(){ //is this board the goal board?
      boolean result = true;
      
      FOR: for (int i = 0; i < dimension; i++){
             for (int j = 0; j < dimension; j++){
               if (board[i][j] != goal[i][j]){
                 result = false;
                 break FOR;
               }
             }
           }
      
      
      return result;
    }
    
    public boolean isSolvable(){ //is the board solvable?
      return false;
    }
    
    public boolean equals(Object y){ //does this board equal y?
      return false;
    }
    
    public Iterable<Board> neighbors(){ //place all neighboring boards into your iterable Queue (assignment 1)
      return null;
    }
    
    public String toString(){ //string representation of the board (in the output format specified below)
      return null;
    }
    
}