import java.util.Arrays;


public class CircularSuffixArray {
  
  //PRIVATE INNER CLASS (must be public because of Comparable interface!)
  public class Node implements Comparable<Node> {
    private String word;
    private int originalIndex;
    
    private Node(String w, int q){
      word = w;
      originalIndex = q;
    }
    
    private String getWord(){
      return word;
    }
    
    private int getIndex(){
      return originalIndex;
    }
    
    public int compareTo(Node o){
      return word.compareTo(o.getWord());
    }
  }
  //END PRIVATE INNER CLASS
  
  private Node[] index; //Class Level Variable
  
  //Constructor:
  public CircularSuffixArray(String s){  // circular suffix array of s   - 10 points
    
    index = new Node[s.length()]; //init index
    index[0] = new Node(s, 0); //seeding for loop. construct a new node @ index 0
    
    //Cycle through each letter
    for (int i = 1; i < s.length(); i++){ //we already have 0 set, start at 1 and go to length-1   
      String indexMinusOne = index[i-1].getWord();
      Character firstChar = indexMinusOne.charAt(0); //get the char at position 0 of the indexi-1
      String newString = indexMinusOne.substring(1, s.length()); //create a new string of last section of word
      newString = newString.concat(firstChar.toString()); //append the first char to the end of it
      index[i] = new Node(newString, i);//store it in index   
    }

    Arrays.sort(index); //Sort the words!
  }
  
  public int length(){                   // length of s
    return index[0].getWord().length();
  }
  
  public int index(int i){               // returns index of ith sorted suffix - 10 points
    return index[i].getIndex();
  }
  
  
  public String getLastColumn(){ //returns the last column of the index[]
    String result ="";
    for(int i = 0; i < this.length(); i++){
      String w = index[i].getWord();
      Character c = w.charAt(this.length()-1);
      result = new String(result.concat(c.toString()));
    }
    
    return result;
  }
  
}//end class