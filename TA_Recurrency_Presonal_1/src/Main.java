import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return;
        }
        int K = Integer.parseInt(scanner.next());
        long A = Integer.parseInt(scanner.next());
        long B = Integer.parseInt(scanner.next());
        int P = Integer.parseInt(scanner.next());
        int Q = Integer.parseInt(scanner.next());
        long result = Solution.mainSolution(K, A, B, P, Q);
        FileWriter fout = null;
            try {
                fout = new FileWriter("output.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fout);
                fout.write(result+"");
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

class Solution{

    public static long mainSolution(int K, long A, long B, int P, int Q){
        long begin = A/K;
        if (A%K != 0)
            begin++;
        long end = B/K;
        long counter = 0;
        long sum = begin*K;
        for (long i = begin; i <= end; ++i){
            if (withinRange(sum, P, Q) == true){
                ++counter;
            }
            sum+=K;
        }
        return counter;
    }

    public static boolean withinRange(long N, int P, int Q){
        int sum = 0;
        while(N != 0){
            sum+=N%10;
            N/=10;
            if (sum > Q)
                return false;
        }
        if (sum >= P)
            return true;
        return false;
    }

}