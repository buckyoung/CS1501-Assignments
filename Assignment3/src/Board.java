

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

//FINISHED
    public int hamming(){ //number of blocks out of place
      int result = 0;
      
      for (int i=0; i<dimension; i++){
        for (int j=0; j<dimension; j++){
          if (board[i][j] != goal[i][j]){
            result++; //if there is a block out of place, add it to the result
          }
        }
      }
      
      result += Solver.count; //add in current count
      return result;
    }
    
    
//FINISHED
    public int manhattan(){ //sum of manhattan distances between blocks and goal
      int result = 0;
      
      for (int b_x=0; b_x<dimension; b_x++){
        for (int b_y=0; b_y<dimension; b_y++){
          if (board[b_x][b_y] != goal[b_x][b_y]){ //if we hit an out-of-place
             int value = board[b_x][b_y]; //save value to search for
             //search for correct position
             int g_x=0;
             int g_y=0;
             SEARCH:for (; g_x<dimension; g_x++){
               for (; g_y<dimension; g_y++){
                 if (value == goal[g_x][g_y]){ //we found the proper position
                   break SEARCH; //break search, proper position is at g_x and g_y
                 }
               }
             }//end SEARCH
             
            //now we have proper position in g_x and g_y
            int xdif = Math.abs(b_x - g_x); //find distance between x's
            int ydif = Math.abs(b_y - g_y); //find distance between y's
            
            result += (xdif+ydif);//add dif's to result
            
        }//end if !=
      }
    }//end topmost for
      
      result += Solver.count; //add in current count
      return result;
 }

//FINISHED
    public boolean isGoal(){ //is this board the goal board?
      boolean result = true;
      
      //iterate through each element of board and compare to goal
      FOR: for (int i = 0; i < dimension; i++){
             for (int j = 0; j < dimension; j++){
               if (board[i][j] != goal[i][j]){
                 result = false;
                 break FOR; //shortcircuit
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