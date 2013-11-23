public class MoveToFront {
  
  private static char[] sequence = new char[256]; //Alphabet Sequence (extended ASCII)
  
  // apply move-to-front encoding, reading from standard input and writing to standard output
  public static void encode(){ //- 20 points
    //Initialize the alphabet sequence
    initSequence();
    
    while(!BinaryStdIn.isEmpty()){ //while has input
      
      //Get a byte from stdIn
      char c = BinaryStdIn.readChar();
      
      //Search for it in the array
      for (int i = 0; i < 256; i++){
        if(c == sequence[i]){ //Found at index i
          //Print i to stdOut
          BinaryStdOut.write((byte)i);
          
          //Move character at index i to the front of sequence
          for(; i>0; i--){
            sequence[i] = sequence[i-1]; //Move all to the right
          }
          sequence[0] = c; //Place byte in front
          
          break; //short-circuit this iteration
        } //end if
      } //end for
    } //end while
    
    //Shut her down
    BinaryStdIn.close();
    BinaryStdOut.close();
  }
  
  // apply move-to-front decoding, reading from standard input and writing to standard output
  public static void decode(){ //- 20 points
    //Initialize the alphabet sequence
    initSequence();
    
    while(!BinaryStdIn.isEmpty()){ //while has input
     
      //Get a byte from stdIn
      char index = BinaryStdIn.readChar();
      
      //Get Character from alphabet
      char c = sequence[index];
     
      //Print c to stdOut
      BinaryStdOut.write(c);
          
      //Move character at index to the front of sequence
      for(; index>0; index--){
        sequence[index] = sequence[index-1]; //Move all to the right
      }
      sequence[0] = c; //Place byte in front
        
    } //end while
    
    //Shut her down
    BinaryStdIn.close();
    BinaryStdOut.close();
  }
  
  private static void initSequence(){
        //Initialize alphabet sequence
    for (int i = 0; i < 256; i++){
      sequence[i] = (char)i;
    }
  }
  
  // if args[0] is '-', apply move-to-front encoding - 5 points
  // if args[0] is '+', apply move-to-front decoding - 5 points
  public static void main(String[] args){
    
    //Check args length
    if(args.length == 0){
       System.out.println("Usage (encode): java MoveToFront - < file.txt\nUsage (decode): java MoveToFront + < file.txt");
       return;
    }
    
    //Check for +/-
    if (args[0].equals("-")){
      encode();
    } else if (args[0].equals("+")){
      decode();
    } else {
      System.out.println("Usage (encode): java MoveToFront - < file.txt\nUsage (decode): java MoveToFront + < file.txt");
      return;
    }
        
  } //end main
} //end class 