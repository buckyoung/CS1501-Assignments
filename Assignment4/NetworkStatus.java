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
      System.out.println("\nYou may now interact with the network.\nType \"H\" for a list of commands.");
      
        
        
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
         Stack temp = new Stack();
       for (Edge e : graph.edges()){
         temp.push(e);
       }
       while (!temp.isEmpty()){
         System.out.println(temp.pop());
       }
         break;
       case 'm': //MST
         
         break;
       case 's': //shortest path
         
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
    System.out.println("R |(R)eport| : Displays the current active network (active nodes, edges, and weights), status (connected or not), and connected components.");
    System.out.println("M |(M)inimum Spanning Tree| : Displays the vertices and edges (with weights) in the current MST of the network.");
    System.out.println("S i j |(S)hortest Path| : Displays the shortest path (by latency) from vertex i to vertex j.");
    System.out.println("D i j |(D)own| : The edge from vertex i to vertex j will go down (become unavailable).");
    System.out.println("U i j |(U)p| : The edge from vertex i to vertex j will come back up (become available).");
    System.out.println("C i j x |(C)hange Weight| : Changes the weight of the edge from vertex i to vertex j to value x. If x<= 0, the edge will be removed. If x > 0 the edge will be updated or created.");
    System.out.println("E |(E)ulerian| : Displays a Eulerian tour or path if it exists.");
    System.out.println("H |(H)elp| : Displays this list of commands.");
    System.out.println("Q |(Q)uit| : Exits the program.");
  }
  
}