public class Board{
    
    public Board(int[][] blocks){ //constuct a board from an N by N array of blocks // where blocks[i][j] = block in row i, column j
       
    }
      
    public int dimension(){ //board dimension N
    
    }
    
    public int hamming(){ //number of blocks out of place
    }
    
    public int manhattan(){ //sum of manhattan distances between blocks and goal
    }
    
    public boolean isGoal(){ //is this board the goal board?
    }
    
    public boolean isSolvable(){ //is the board solvable?
    }
    
    public boolean equals (Object y){ //does this board equal y?
    }
    
    public Iterable<Board> neighbors(){ //place all neighboring boards into your iterable Queue (assignment 1)
    }
    
    public String toString(){ //string representation of the board (in the output format specified below)
    }
    
}