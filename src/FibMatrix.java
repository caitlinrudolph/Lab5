import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class FibMatrix {
    //reminder: towards end of video, y would be the variable we want the fibonacci of  (we want fib(y) there)
    //See the matrix multiplication method from exercise 0.4 in Algorithms (DPV)
    //Write a helper function called by MatrixPower that efficiently calculates powers of a matrix
    //which, in trun needs a helper function to do simple matrix multiplication
    //Calculating MX should take O(log2(X)) matrix multiplications instead of the naïve O(X).
      //Note that because the size (number of bits) of N ~ log2(X), calculating MX should take O(N) multiplications.
    static ThreadMXBean bean = ManagementFactory.getThreadMXBean();

    /* define constants */
    static long numberOfTrials = 128;

    static String ResultsFolderPath = "/home/caitlin/Documents/Lab5/"; // pathname to results folder
    static FileWriter resultsFile;
    static PrintWriter resultsWriter;

    public static void main(String[] args) {

        //direct the verification test results to file
        // run the whole experiment at least twice, and expect to throw away the data from the earlier runs, before java has fully optimized
        System.out.println("Running first full experiment...");
        runFullExperiment("FibLoop-Exp1-ThrowAway.txt");
        System.out.println("Running second full experiment...");
        runFullExperiment("FibLoop-Exp2.txt");
        System.out.println("Running third full experiment...");
        runFullExperiment("FibLoop-Exp3.txt");

    }

    static void runFullExperiment(String resultsFileName) {
        try {
            resultsFile = new FileWriter(ResultsFolderPath + resultsFileName);
            resultsWriter = new PrintWriter(resultsFile);
        } catch (Exception e) {
            System.out.println("*****!!!!!  Had a problem opening the results file " + ResultsFolderPath + resultsFileName);
            return; // not very foolproof... but we do expect to be able to create/open the file...
        }

        ThreadCpuStopWatch TrialStopwatch = new ThreadCpuStopWatch(); // for timing an individual trial

        resultsWriter.println("#X(value)    N(size)     T(time)"); // # marks a comment in gnuplot data
        resultsWriter.flush();
        /* for each size of input we want to test: in this case starting small and doubling the size each time */
        for (long  i = 0;  i <= numberOfTrials; i ++) {
            //for each integer <= 128 run a trial
            // progress message...
            System.out.println("Running test for input size " + i + " ... ");

            /* repeat for desired number of trials (for a specific size of input)... */
            long batchElapsedTime = 0;

            System.out.print("    Running trial batch...");

            /* force garbage collection before each batch of trials run so it is not included in the time */
            System.gc();
            // run the trial

            TrialStopwatch.start(); // *** uncomment this line if timing trials individually

            long size = FibMatrix(i);

            batchElapsedTime = batchElapsedTime + TrialStopwatch.elapsedTime(); // *** uncomment this line if timing trials individually

            //batchElapsedTime = BatchStopwatch.elapsedTime(); // *** comment this line if timing trials individually
            double averageTimePerTrialInBatch = (double) batchElapsedTime / (double) numberOfTrials; // calculate the average time per trial in this batch

            /* print data for this size of input */
            resultsWriter.printf("%12d  %12d  %15.2f \n", i, size, averageTimePerTrialInBatch); // might as well make the columns look nice
            resultsWriter.flush();
            System.out.println(" ....done.");
        }
    }

    public static long FibMatrix(long X){
        //take a single 8-byte unsigned integer and return a single 8-byte result

        //a dynamic programming version using a straight forward loop to add up numbers in the sequence and return the nth number

        //loop through the Fib numbers and return the X value
        long fib1 = 1, fib2 = 1, fibonacci = 1;
        for (int i = 3; i <= X; i++) {
            fibonacci = fib1 + fib2; // Fibonacci number is sum of previous two Fibonacci number
            fib1 = fib2;
            fib2 = fibonacci;

        }
        return fibonacci; // Fibonacci number

    }
}
