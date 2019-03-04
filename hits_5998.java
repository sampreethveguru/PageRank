/* Sai Sampreeth Mourya Veguru  cs610  5998 prp  */


import static java.lang.Math.abs;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class hits_5998 {

       static int iteration;                          // iter for iteration values
       static  int initVal;                      // initial value
       static String file;                   // File name for the graph.
       static int n;                             // number of vertices in the graph
       static int m;                             // number of edges in the graph
       static  int[][] adjMat;                   // adjacency matrix 
       static double[] hu;                       //  hub arr
       static double[] au;                       // authority arr
       static double errorrate = Math.pow(10, -5);   //Error rate defined as 10^-5
  

    public static void main(String[] args)
    {
        
        try{
        // Throw exception if the number of parameters are not equal.
        	 if(args.length == 3) {
                
             }
        	 else {
        		 throw new Exception("ENTER: ITERATIONS  INITIALVALUE  FILENAME");
        		 
        	 }
        // command line input
        // Initializing values of iteration, initial value and file name.
        iteration=(int)Integer.parseInt( args[0]);
        initVal=(int)Integer.parseInt( args[1]);
        file=args[2];
        // Checking initial value if it is -2, -1, 0 or 1
            if( !(initVal== -2 || initVal == -1 ||initVal == 0 ||initVal == 1) ) {
            	 System.out.println("Initial value must be -2,-1,0,1");
                 return;
             }
        // Calling initailizeValues method to initialize the initial values of hub and authority.
            initializeValues_5998();
        // Calling hits algo 
            hitsAlgo_5998();
        }
        catch(Exception e)
        {
        	System.out.println("exception occured in"+e);
        }     
      }

       // Checking for intersection
       static boolean  intersection(double[] p, double[] q)
      {
       for(int i = 0 ; i < n; i++) {
           if ( abs(q[i] - p[i]) > errorrate ) 
             return false;
       }
       return true;
       
       
     } 
     // Hits algo for N greater than 10
    public static void hitsAlgoForNGreaterThan10_5998(double[] hub,double []aut,double aut_sum_square,double aut_scale_factor,double hub_scale_factor,double hub_sum_square,double[] hprev,double[] aprev)
     {

        iteration = 0;
        for(int i =0; i < n; i++) {
        	hub[i] = 1.0/n;
            aut[i] = 1.0/n;
            hprev[i] = hub[i];
            aprev[i] = aut[i];
        }
        
        System.out.println("Base:0");
        for(int i = 0; i < n; i++) {
          System.out.printf(" A/H[%d]=%.6f/%.6f\n",i,Math.round(au[i]*1000000.0)/1000000.0,Math.round(hu[i]*1000000.0)/1000000.0); 
        }
        
      int i = 0;
      do {  
           for(int a= 0; a < n; a++) {
               aprev[a] = aut[a];
               hprev[a] = hub[a];
           }

            //Authority step starts
            for(int b = 0; b < n; b++) {
                aut[b] = 0.0;
            }
        
            for(int b = 0; b< n; b++) {
                for(int c = 0; c < n; c++) {
                    if(adjMat[c][b] == 1) {
                        aut[b] += hub[c]; 
                    }
                }
            }

            //Hub step starts
            for(int a = 0; a < n; a++) {
            	hub[a] = 0.0;
            }

            for(int b = 0; b< n; b++) {
                for(int c = 0; c < n; c++) {
                    if(adjMat[b][c] == 1) {
                    	hub[b] += aut[c]; 
                    }
                }
            }

            //Scaling Authority starts
            aut_scale_factor = 0.0;
            aut_sum_square = 0.0;
            for(int a = 0; a < n; a++) {
                aut_sum_square += aut[a]*aut[a];    
            }
            aut_scale_factor = Math.sqrt(aut_sum_square); 
            for(int b = 0; b< n; b++) {
                aut[b] = aut[b]/aut_scale_factor;
            }

            //Scaling Hub starts
            hub_scale_factor = 0.0;
            hub_sum_square = 0.0;
            for(int a = 0; a < n; a++) {
                hub_sum_square += hub[a]*hub[a];    
            }
            hub_scale_factor = Math.sqrt(hub_sum_square); 
            for(int b = 0; b < n; b++) {
            	hub[b] = hub[b]/hub_scale_factor;
            }
            i++; // incr the iteration counter
            System.out.println("Iter:" + i);
            for(int a = 0; a < n; a++) {
                System.out.printf(" A/H[%d]=%.6f/%.6f\n",a,Math.round(aut[a]*1000000.0)/1000000.0,Math.round(hub[a]*1000000.0)/1000000.0); 
            }
      } while( false == intersection(aut, aprev) || false == intersection(hub, hprev));
      
      System.exit(0);
      return;
    
    }
    public  static void hitsAlgo_5998()
    {
        double[] hub = new double[n];
        double[] aut = new double[n];
        double aut_scale_factor = 0.0;
        double aut_sum_square = 0.0;
        double hub_scale_factor = 0.0;
        double hub_sum_square = 0.0; 
        double[] aprev = new double[n]; //last iterations values of a, used for convergence
        double[] hprev = new double[n]; //last iterations values of h, used for convergence

        //If the graph has N greater than 10, then the values for iterations, initial value revert to 0 and -1 respectively
        if(n > 10) {
        	hitsAlgoForNGreaterThan10_5998(hub,aut,aut_sum_square,aut_scale_factor,hub_scale_factor,hub_sum_square,hprev,aprev);
        }

        //Initialization
        for(int i = 0; i < n; i++)
        {
        	hub[i] = hu[i];
            aut[i] = au[i];
            hprev[i] = hub[i];
            aprev[i] = aut[i]; 
        }
        
        //Base Case
        System.out.print("Base:    0 :");
        for(int i = 0; i < n; i++) {
          System.out.printf(" A/H[%d]=%.6f/%.6f",i,Math.round(au[i]*1000000.0)/1000000.0,Math.round(hu[i]*1000000.0)/1000000.0); 
        }
        
        if (iteration > 0) { 
            for(int i = 0; i < iteration; i++) { //iteration starts
            
                //Authority step starts
                for(int p = 0; p < n; p++) {
                    aut[p] = 0.0;
                }
            
                for(int j = 0; j < n; j++) {
                    for(int k = 0; k < n; k++) {
                        if(adjMat[k][j] == 1) {
                            aut[j] += hub[k]; 
                        }
                    }
                }

                //Hub step starts
                for(int p = 0; p < n; p++) {
                	hub[p] = 0.0;
                }

                for(int j = 0; j < n; j++) {
                    for(int k = 0; k < n; k++) {
                        if(adjMat[j][k] == 1) {
                            hub[j] += aut[k]; 
                        }
                    }
                }

                //Scaling Authority starts
                aut_scale_factor = 0.0;
                aut_sum_square = 0.0;
                for(int l = 0; l < n; l++) {
                    aut_sum_square += aut[l]*aut[l];    
                }
                aut_scale_factor = Math.sqrt(aut_sum_square); 
                for(int l = 0; l < n; l++) {
                    aut[l] = aut[l]/aut_scale_factor;
                }  
 
                //Scaling Hub starts
                hub_scale_factor = 0.0;
                hub_sum_square = 0.0;
                for(int l = 0; l < n; l++) {
                    hub_sum_square += hub[l]*hub[l];    
                }
                hub_scale_factor = Math.sqrt(hub_sum_square); 
                for(int l = 0; l < n; l++) {
                    hub[l] = hub[l]/hub_scale_factor;
                }
            
                System.out.println();
                System.out.print("Iter:    " + (i+1) + " :");
                for(int l = 0; l < n; l++) {
                    System.out.printf(" A/H[%d]=%.6f/%.6f",l,Math.round(aut[l]*1000000.0)/1000000.0,Math.round(hub[l]*1000000.0)/1000000.0); 
                }
   
            }//iteration ends
        } 
        else
        {  if (iteration<0) {
    		errorrate= Math.pow(10, iteration)	;
    	}
          int i = 0;
          do {  
                for(int a= 0; a < n; a++) {
                    aprev[a] = aut[a];
                    hprev[a] = hub[a];
                }

                //Authority step starts
                for(int a = 0; a < n; a++) {
                    aut[a] = 0.0;
                }
            
                for(int a = 0; a < n; a++) {
                    for(int b = 0; b < n; b++) {
                        if(adjMat[b][a] == 1) {
                            aut[a] += hub[b]; 
                        }
                    }
                }

                //Hub step starts
                for(int c= 0; c < n; c++) {
                    hub[c] = 0.0;
                }

                for(int a = 0; a < n; a++) {
                    for(int b = 0; b < n; b++) {
                        if(adjMat[a][b] == 1) {
                            hub[a] += aut[b]; 
                        }
                    }
                }

                //Scaling Authority starts
                aut_scale_factor = 0.0;
                aut_sum_square = 0.0;
                for(int a = 0; a < n; a++) {
                    aut_sum_square += aut[a]*aut[a];    
                }
                aut_scale_factor = Math.sqrt(aut_sum_square); 
                for(int a = 0; a < n; a++) {
                    aut[a] = aut[a]/aut_scale_factor;
                }
                //Scaling Hub starts
                hub_scale_factor = 0.0;
                hub_sum_square = 0.0;
                for(int a = 0; a< n; a++) {
                    hub_sum_square += hub[a]*hub[a];    
                }
                hub_scale_factor = Math.sqrt(hub_sum_square); 
                for(int s = 0; s < n; s++) {
                    hub[s] = hub[s]/hub_scale_factor;
                }
                i++; // incr the iteration counter
                System.out.println();
                
                System.out.print("Iter:    " + i + " :");
                for(int a = 0; a < n; a++) {
                    System.out.printf(" A/H[%d]=%.6f/%.6f",a,Math.round(aut[a]*1000000.0)/1000000.0,Math.round(hub[a]*1000000.0)/1000000.0); 
                }
          } while( false == intersection(aut, aprev) || false == intersection(hub, hprev));
        }
        System.out.println();
    }
      // Initializing values and reading data from file
    public static void initializeValues_5998()   
    {
        try {        
            Scanner scanner = new Scanner(new File(file));
            n = scanner.nextInt();
            m = scanner.nextInt();
              //Adjacency matrix representation of graph
       // Initializing zero values for adjacency matrix
            adjMat = new int[n][n];
            for(int i = 0; i < n; i++)
             for(int j = 0; j < n; j++)
               adjMat[i][j] = 0;

            while(scanner.hasNextInt())
            {
                adjMat[scanner.nextInt()][scanner.nextInt()] = 1; 
            }
            
           

            hu = new double[n];
            au = new double[n];
            if(initVal==0)
            {
            	  for(int i = 0; i < n; i++) {
                      hu[i] = 0;
                      au[i] = 0;
                    }
            }
            else if (initVal==1)
            {
            	 for(int i = 0; i < n; i++) {
                     hu[i] = 1;
                     au[i] = 1;
                   }
            }
            else if (initVal==-1)
            {
            	 for(int i =0; i < n; i++) {
                     hu[i] = 1.0/n;
                     au[i] = 1.0/n;
                   }
            }
            else if (initVal==-2)
            {
            	for(int i =0; i < n; i++) {
                    hu[i] = 1.0/Math.sqrt(n);
                    au[i] = 1.0/Math.sqrt(n);
                  }
            }
          

        }
        catch(FileNotFoundException fnfe)
        {
        }
    }
}