import java.util.Arrays;

public class BurrowsWheeler {
   // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
  public static void encode(){//    - 20 points
    
      //Get a byte from stdIn
      String s = BinaryStdIn.readString();
      CircularSuffixArray csa = new CircularSuffixArray(s);
      
      //find the "first"  (what row?)
      int first = 0;
     
      for(int i = 0; i < csa.length(); i++){
        int n = csa.index(i);
        if (n == 0){
          first = i;
          break;
        }
      }
        
        BinaryStdOut.write(first);
        BinaryStdOut.write(csa.getLastColumn()); 
      
    
    //Shut her down
    BinaryStdIn.close();
    BinaryStdOut.close();
    
  
  }
        
   // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
  public static void decode(){   // - 20 points
    
    int finalIndex = BinaryStdIn.readInt(); //get the final index, where our decoded string will live
    String original = BinaryStdIn.readString();    
    String[] a = new String[original.length()];
    
    
    //do transformations to decode
    for(int i = 0; i < original.length(); i++){
      
      for(int j = 0; j < original.length(); j++){ //loop for each row
        if(a[j] == null){
          a[j] = "";
        }
        
        a[j] = original.charAt(j) + a[j];
        
      }
      
      Arrays.sort(a);
    }
    
    BinaryStdOut.write(a[finalIndex]);
    
    BinaryStdOut.close();
    BinaryStdIn.close();
    
  }
        
   // if args[0] is '-', apply Burrows-Wheeler encoding   - 5 points
   // if args[0] is '+', apply Burrows-Wheeler decoding   - 5 points
  public static void main(String[] args){
     
    //Check args length
    if(args.length == 0){
       System.out.println("Usage (encode): java BurrowsWheeler - < file.txt\nUsage (decode): java BurrowsWheeler + < file.txt");
       return;
    }
    
    //Check for +/-
    if (args[0].equals("-")){
      encode();
    } else if (args[0].equals("+")){
      decode();
    } else {
      System.out.println("Usage (encode): java BurrowsWheeler - < file.txt\nUsage (decode): java BurrowsWheeler + < file.txt");
      return;
    }
    
    
  }
  
} 