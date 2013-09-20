
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * @status INCOMPLETE
 *
 * @author BuckYoung
 * @date Aug 28, 2013
 *
 * @description Finds the "distance" between 2 files as an angle of their
 * word/frequency vectors in radians
 *
 */
public class ComputeDistanceBetween {

    public static void main(String args[]) {
        
        //Initialize:
        //TIME SAVER(treemap data struct)
        TreeMap<String, Integer> treemap1 = new TreeMap<>();
        TreeMap<String, Integer> treemap2 = new TreeMap<>();
        double radians = Float.NaN;
        //Start timer:
        Stopwatch time = new Stopwatch();

        if (args.length != 2) {
            //Improper inputs:
            StdOut.println("Usage: java ComputeDistanceBetween fileName_1 fileName_2");
        } else {

            /*
             * 1) Process Files
             */
            //Returns # of distinct
            processFile(args[0], treemap1); //process and print-out stats
            //Returns # of distinct
            processFile(args[1], treemap2); //process and print-out stats

            /*
             * 2) Determine Distance
             * 
             * d(x,y) = arccos(inner_product(x,y) / (norm(x)*norm(y)))
             *      inner_product = summation fj * fk
             *      norm = sqrt(inner_product(x,x))
             * 
             */

            //Note: we need only consider the set with the least distinct words
            //TIMESAVER
            if (treemap1.size() < treemap2.size()) { //iterate treemap1 //<//
                
                radians = processDistance(treemap1, treemap2);
                
            } else { //iterate treeemap2
                //same but 2 is now smaller! (or equal, technically)
                 radians = processDistance(treemap2, treemap1);
            }
            
            //Round to 6 digit-precision
            radians = Math.round(radians*1000000);
            radians = radians/1000000;
            StdOut.println("The distance between the documents is " + radians + " radians");

            /*
             * 3) Time Elapsed
             */
            StdOut.println("Time elapsed: " + time.elapsedTime() + " seconds");



        }//end else
    }//end main    

    @SuppressWarnings("unchecked") 
    private static void processFile(String fileName, TreeMap treemap) {
        //Initialize
        StringTokenizer tokenizer;
        String token;
        int lines = 0;
        int words = 0;
        int distinct = 0;

        In in = new In(fileName);

        if (in.exists()) {
            while (in.hasNextLine()) {
                //Get Line
                String line = in.readLine();
                lines++; //Increment line counter
                //Regex non-alphanumeric to blanks
                line = line.replaceAll("\\W", " "); //Strings are immutable, so we must catch the return value here
                //Tokenize
                //TIME SAVER (string tokenizer vs string.split)
                tokenizer = new StringTokenizer(line, " ", false); //(string, delim, return-delim)
                while (tokenizer.hasMoreTokens()) {
                    token = tokenizer.nextToken();
                    token = token.toLowerCase();
                    //Add to treemap
                    if (treemap.containsKey(token)) {
                        //Non-Distinct token: Increment Frequency
                        
                        treemap.put(token, ((int) treemap.get(token) + 1));
                    } else {
                        //Distinct toke: Increment Distinct
                        treemap.put(token, 1);
                        distinct++;
                    }
                    //Increment words
                    words++;
                }
            }//end while hasNextLine
            //Output results of File 1
            StdOut.println("File " + fileName + ": " + lines + " lines, " + words + " words, " + distinct + " distinct words");

        } else {
            StdOut.println(fileName + " is not found!");
        }
        in.close();
    }//end processFile()
    
    public static double processDistance(TreeMap<String, Integer> smaller, TreeMap<String, Integer> larger){
                int inner_product_x_y = 0;
                int inner_product_x_x = 0;
                int inner_product_y_y = 0;
                Integer otherValue = null;
                //Inner Product X,Y
                //Dont need to check for the extra Y values here, because product will be zero
                //While iterating thru X, we can also do the inner product of x,x
                //We can also do a bit of work here for inner product y, y
                //TIME SAVER (this whole for each loop is awesome)
                for (Map.Entry<String, Integer> entry : smaller.entrySet()) {
                    //check for null, assume 0
                    otherValue = larger.get(entry.getKey());
                    if (otherValue == null) {
                        //otherValue = 0; 
                    } else {
                        //inner product = inner product + (freq1*freq2)
                        inner_product_x_y += (entry.getValue() * otherValue);
                        //TIME SAVER:
                        //Lets save us some work on the y_y inner product:
                        inner_product_y_y += (otherValue * larger.remove(entry.getKey())); //remove it to save work below
                    }

                    inner_product_x_x += (entry.getValue() * entry.getValue());

                }
                
                //Now we need to determine inner product y,y
                for (Map.Entry<String, Integer> entry : larger.entrySet()) {
                    //We saved time by removing the already-considered words,
                    //now we gotta consider the rest of the words
                    inner_product_y_y += (entry.getValue() * entry.getValue());
                }
                
                /*
                 * Now we have, inner-product(x,y), inner-product(x,x), inner-product(y,y)
                 * 
                 * Let's Rest For A Moment
                 * 
                 * ...Ah, That's Nice...
                 * 
                 * ...Ok, here we go:
                 * 
                 */
                
                //Find denom
                double result = Math.sqrt(inner_product_x_x) * Math.sqrt(inner_product_y_y);
                
                //Divide numer by denom
                result =  inner_product_x_y / result;
                
                //Get ACOS
                result = Math.acos(result);

                return result;
    }
}//end class
