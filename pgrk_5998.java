/* Sai Sampreeth Mourya Veguru  cs610 5998 prp   */

import static java.lang.Math.abs;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class pgrk_5998 {

       static int iteration;                            // iter for iteration values
       static int initVal;                         // initial value 
       static String file;                     // File name for the graph
       static int n;                               // number of vertices in the graph
       static int m;                               // number of edges in the graph
       static int[][] adjMat;                      // adjacency matrix 
       static final double d = 0.85;                   
       static double errorrate = Math.pow(10, -5); //Error rate defined as 10^-5
       static double[] dsa;
       static int[] OutGoingLinks;                 // Outgoing links 
	
        
       
        //Throws exception if number of parameters are not equal
        public static void main(String[] args) throws Exception               
        {
        	if(args.length == 3) {
                //do nothing
            }
       	 else {
       		 throw new Exception("ENTER: ITERATIONS  INITIALVALUE  FILENAME");
       	 }
        
        
        //command line arguments
        //Initializing values of iteration, initial value and file name.
        iteration=(int)Integer.parseInt( args[0]);
        initVal=(int)Integer.parseInt( args[1]);
    	file=args[2];
    	
    	
        //Checking initial value if it is -2, -1, 0 or 1
        if( !(initVal== -2 || initVal == -1 ||initVal == 0 ||initVal == 1) ) {
            System.out.println("Initial value must be -2,-1,0,1");
        return;
        }
        
        // Calling initializeValues method to initialize the initial values.
        initializeValues_5998();
        
        
        // Calling Page rank algorithm
        pagerankAlgo_5998();
        }
                
        // Checking for intersection
        public static boolean intersection_5998(double[] src, double[] target)
        {
        for(int i = 0; i < n; i++)
        {
        if ( abs(target[i]-src[i]) > errorrate )
        return false;
        }
        return true;
        }
        
        //Page rank algorithm for N greater than 10
        public static void pagerankAlgoForNGreaterThan10_5998(boolean flag,double[] arr)
        {
        iteration = 0;
        for(int i =0; i < n; i++) {
        dsa[i] = 1.0/n;
        }
        
            System.out.println("Base: 0");
              for(int j = 0; j < n; j++) {
                  System.out.printf("P[" + j + "]=%.6f\n",Math.round(dsa[j]*1000000.0)/1000000.0);
              }
        int i = 0;
        
        
        
        do{
            if(flag == true)
        {
            flag = false;
        }
            else
        {
            for(int a = 0; a < n; a++) {
            dsa[a] = arr[a];
        }
        }
            for(int a = 0; a < n; a++) {
            arr[a] = 0.0;
        }

        for(int b= 0; b < n; b++) {
           for(int c= 0; c< n; c++)
        {
           if(adjMat[c][b] == 1) {
           arr[b] += dsa[c]/OutGoingLinks[c];
        }
        }
        }

        //outGoingLinks compute and print page rank of all pages
        for(int a = 0; a < n; a++) {
           arr[a] = d*arr[a] + (1-d)/n;
        }

        System.out.println("Iter: " + (i+1));
     for(int a = 0 ; a < n; a++) {
        System.out.printf("P[" + a + "] = %.6f\n",Math.round(arr[a]*1000000.0)/1000000.0);
     }
        i++;
        } while (intersection_5998(dsa, arr) != true);

        
        System.exit(0);
        return;
        
        
        }
        
        
        
        //Page rank algorithm starts.
        public static void pagerankAlgo_5998() {
        double[] arr = new double[n];
        boolean flag = true;
        
        // If  graph has N > 10, iter= 0, initval = -1
        if(n > 10) {
        	pagerankAlgoForNGreaterThan10_5998(flag,arr);
        }
        
        //Base Case
            System.out.print("Base    : 0");
        for(int j = 0; j < n; j++) {
            System.out.printf(" :P[" + j + "]=%.6f",Math.round(dsa[j]*1000000.0)/1000000.0);
        }

        if (iteration > 0) {
          for(int i = 0; i < iteration; i++)
          {
              for(int a = 0; a < n; a++) {
                arr[a] = 0.0;
              }
            
              for(int b = 0; b < n; b++) {
                for(int c = 0; c < n; c++)
                {
                    if(adjMat[c][b] == 1) {
                        arr[b] += dsa[b]/OutGoingLinks[c];
                    } 
                }
              }

              
        //Compute and print pgrk
              System.out.println();
              System.out.print("Iter    : " + (i+1));
              for(int a = 0; a< n; a++) {
                arr[a] = d*arr[a] + (1-d)/n;
                System.out.printf(" :P[" + a + "]=%.6f",Math.round(arr[a]*1000000.0)/1000000.0);
              }
            
              for(int a = 0; a < n; a++) {
                dsa[a] = arr[a]; 
              } 
          }
          System.out.println();
        }
        else 
        { 
        	
       //if iter <=0 , try convergence
        	
        	if (iteration<0) {
        		errorrate= Math.pow(10, iteration)	;
        	}
        	
          int i = 0;
          do {
               if(flag == true)
               {
                  flag = false;
               }
               else
               {
                 for(int a = 0; a < n; a++) {
                   dsa[a] = arr[a];
                 }
               }
               for(int a = 0; a < n; a++) {
                 arr[a] = 0.0;
               }

               for(int j = 0; j < n; j++) {
                 for(int k = 0; k < n; k++)
                 {
                    if(adjMat[k][j] == 1) {
                        arr[j] += dsa[k]/OutGoingLinks[k];
                    }
                 }
               }
               

         //OutGoingLinksCompute and print pagerank of all pages
               System.out.println(); 
               System.out.print("Iter    : " + (i+1));
               for(int a = 0; a < n; a++) {
                 arr[a] = d*arr[a] + (1-d)/n;
                 System.out.printf(" :P[" + a + "]=%.6f",Math.round(arr[a]*1000000.0)/1000000.0);
               }
               i++;  
             } while (intersection_5998(dsa, arr) != true);  
        System.out.println(); 
        }
    }
  public static void initializeValues_5998()   
  {
      
      int p = 0;
      int q = 0;
      try {        
    	
          Scanner  scanner = new Scanner(new File(file));
          n = scanner.nextInt();
          m = scanner.nextInt();
          
          //Adjacency matrix representation of graph
          adjMat = new int[n][n];
          for(int i = 0; i < n; i++)
           for(int j = 0; j < n; j++)
             adjMat[i][j] = 0;
          
          while(scanner.hasNextInt())
          {
              p = scanner.nextInt();
              q = scanner.nextInt();
              adjMat[p][q] = 1;
          }
          

          //outGoingLinks[i] --> number of outgoing links of page 'Ti'
          OutGoingLinks = new int[n];
          for(int i = 0; i < n; i++) {
              OutGoingLinks[i] = 0;
              for(int j = 0; j < n; j++) {
                  OutGoingLinks[i] += adjMat[i][j];
              }
          }

          dsa = new double[n];
          
          if(initVal==0) {
            for(int i = 0; i < n; i++) {
              dsa[i] = 0;
            }
          }
          else if(initVal==1)
          {
          for(int i = 0; i < n; i++) {
              dsa[i] = 1;
            }
          }
          else if(initVal==-1)
          {
            for(int i =0; i < n; i++) {
              dsa[i] = 1.0/n;
            }
          }
          else if (initVal==-2)
          {
          	for(int i =0; i < n; i++) {
              dsa[i] = 1.0/Math.sqrt(n);
          	}
           
          }
      }
      catch(FileNotFoundException fnfe)
      {
      }
  }
  
}