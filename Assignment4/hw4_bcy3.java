import java.util.Scanner;
//Buck Young -- bcy3
//CS1501: Assignment 4
//Due: Nov 4, 2013
//Reads in graph from file (as cmdline arg) and outputs status of the network after user changes
public class hw4_bcy3{
  
  private static boolean quit = false;
  private static EdgeWeightedGraph graph;
  
  public static void main(String[] args){
    if(args.length != 1){
      System.out.println("Usage: java NetworkStatus fileName.txt");
    } else {
      int input;
      //1) Read in lines from file
      In in = new In(args[0]);
      if (in.exists()){
        //Build network from lines of text
        graph = new EdgeWeightedGraph(in);
      
      System.out.println("Network read from file successfully.");
      System.out.println("You may now interact with the network.\n");

      
      while (!quit){
        In userIn = new In();
        //Get user input
        displayHelp();
        System.out.print("Enter your choice: ");
        
        try{
          input = userIn.readInt();
          System.out.println();
          if (input <= 8 && input >= 1){
          //good input
            try{
              handleInput(input);
            } catch (java.lang.ArrayIndexOutOfBoundsException exc){
              System.out.println("Vertex does not exist. Try 0 to "+ (graph.V()-1) + ".");
            } catch (java.util.NoSuchElementException exc){
              System.out.println("Input not recognized. Try again.");
            }
          
            System.out.println();
          } else {
               //bad input
               System.out.println("Input not recognized. Try 1 through 8.\n");
          }
         
        } catch(java.util.InputMismatchException exp){
          System.out.println("Input not recognized. Try 1 through 8.\n");
        }
        
          
       
      }
        //TODO: Close stream in (Q)uit method!
      
      } else { //File does not exits
        System.out.println("Error: File does not exist.");
      } 
      
    }
  }
  
  //calls the proper method depending on input
  private static void handleInput(int input){
    In in = new In(); 
  
    int i, j;
    double x;
    
     switch(input){
       case 1: //report
         graph.report();
         break;
       case 2: //MST
         graph.mst();
         break;
       case 3: //shortest path
         //GET 2 INPUTS and pass to shortest()
         System.out.print("Enter from vertex: ");
         i = in.readInt();
         System.out.print("Enter to vertex: ");
         j = in.readInt();
         graph.shortest(i, j);
         break;
       case 4: //down
         System.out.print("Enter from vertex: ");
         i = in.readInt();
         System.out.print("Enter to vertex: ");
         j = in.readInt();
         graph.edgeDown(i, j);
         break;
       case 5: //up
         System.out.print("Enter from vertex: ");
         i = in.readInt();
         System.out.print("Enter to vertex: ");
         j = in.readInt();
         graph.edgeUp(i, j);
         break;
       case 6: //change
         System.out.print("Enter from vertex: ");
         i = in.readInt();
         System.out.print("Enter to vertex: ");
         j = in.readInt();
         System.out.print("Enter weight: ");
         x = in.readDouble();
         graph.changeWeight(i, j, x);
         break;
       case 7: //eulerian
         graph.eulerian();
         break;
       case 8: //quit
         System.out.println("Exiting...");
         quit = true;
         break;
       default:
         //nothing -- should never get here if all input is verified
     }
     
  }
  
  //Displays the list of commands
  private static void displayHelp(){
    System.out.println("1. (R)eport");
    System.out.println("2. (M)inimum Spanning Tree");
    System.out.println("3. (S)hortest Path from i j");
    System.out.println("4. (D)own i j");
    System.out.println("5. (U)p i j");
    System.out.println("6. (C)hange Weight i j x");
    System.out.println("7. (E)ulerian");
    System.out.println("8. (Q)uit");
  }
  
}