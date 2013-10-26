import java.util.Scanner;
//Buck Young -- bcy3
//CS1501: Assignment 4
//Due: Nov 4, 2013
//Reads in graph from file (as cmdline arg) and outputs status of the network after user changes
public class NetworkStatus{
  
  private static boolean quit = false;
  private static EdgeWeightedGraph graph;
  
  public static void main(String[] args){
    if(args.length != 1){
      System.out.println("Usage: java NetworkStatus fileName.txt");
    } else {
      String line;
      //1) Read in lines from file
      In in = new In(args[0]);
      if (in.exists()){
        //Build network from lines of text
        graph = new EdgeWeightedGraph(in);
      
      System.out.println("Network read from file successfully.");
      System.out.println("You may now interact with the network.\n\nType \"H\" for a list of commands.");
      
        
        
      //2) Ask user for input 
      In userIn = new In();
      
      while (!quit){
        //Get user input
        System.out.print("> ");
        line = userIn.readLine();
        if (verifyInput(line) ){
          //good input
          try{
            handleInput(line);
          } catch (java.lang.ArrayIndexOutOfBoundsException exc){
            System.out.println("Client does not exist. Try 0 to "+ (graph.V()-1) + ".");
          } catch (java.util.NoSuchElementException exc){
            System.out.println("Input not recognized. Type \"H\" for help.");
          }
          
        } else {
          //bad input
          System.out.println("Input not recognized. Type \"H\" for help.");
        }
      }
        //TODO: Close stream in (Q)uit method!
      
      } else { //File does not exits
        System.out.println("Error: File does not exist.");
      } 
    }
  }
  
  //calls the proper method depending on input
  private static void handleInput(String s){
    In in = new In(new Scanner(s)); 
    char c = in.readChar();
     c = Character.toLowerCase(c);
     
     switch(c){
       case 'r': //report
         graph.report();
         break;
       case 'm': //MST
         graph.mst();
         break;
       case 's': //shortest path
         graph.shortest(in.readInt(), in.readInt());
         break;
       case 'd': //down
         graph.edgeDown(in.readInt(), in.readInt());
         break;
       case 'u': //up
         graph.edgeUp(in.readInt(), in.readInt());
         break;
       case 'c': //change
         graph.changeWeight(in.readInt(), in.readInt(), in.readInt());
         break;
       case 'e': //eulerian
         graph.eulerian();
         break;
       case 'h': //help
         displayHelp();
         break;
       case 'q': //quit
         quit = true;
         break;
       default:
         //nothing -- should never get here if all input is verified
     }
     
  }

  //verifies the input input
  private static boolean verifyInput(String s){
    boolean result = false;
    In in = new In(new Scanner(s)); 
    try{
    char c = in.readChar();
     c = Character.toLowerCase(c);
     
     switch(c){
       case 'r':
       case 'm':
       case 's':
       case 'd':
       case 'u':
       case 'c':
       case 'e':
       case 'h':
       case 'q':
         result = true;
         break;
       default:
         result = false;
     }
    } catch (java.util.NoSuchElementException exc){
    }
     
     
     return result;
  }
  
  //Displays the list of commands
  private static void displayHelp(){
    System.out.println("List of Commands:");
    System.out.println("R         --   (R)eport");
    System.out.println("M         --   (M)inimum Spanning Tree");
    System.out.println("S i j     --   (S)hortest Path");
    System.out.println("D i j     --   (D)own");
    System.out.println("U i j     --   (U)p");
    System.out.println("C i j x   --   (C)hange Weight :If x<=0, the edge will go down. \n                               :If x>0, the edge will come up and be updated or created.");
    System.out.println("E         --   (E)ulerian");
    System.out.println("H         --   (H)elp");
    System.out.println("Q         --   (Q)uit");
  }
  
}