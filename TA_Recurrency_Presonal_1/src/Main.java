import java.io.*;
import java.util.Scanner;



class Solution {

    public static void parseNumberAndFindResidues(long[] rem, long[] digits, long N, int K){
        for (int i = 0; i < 3; ++i) {
            digits[i] = N % 10;
            N /= 10;
        }

        rem[0] = 1;
        for (int i = 1; i < 3; ++i) {
            rem[i] = (rem[i - 1] * 10) % K;
        }
    }

    public static Long mainSolution(int K, long N, int P, int Q) {
        long[][][][] matrix = new long[4][8][28][2];
        long rem[] = new long[3], digits[] = new long[3];

        parseNumberAndFindResidues(rem, digits, N, K);
        
        matrix[3][0][0][0] = 1;

        for (int digitNumber = 3; digitNumber > 0; --digitNumber) {
            for (int remainder = 0; remainder < K; ++remainder) {
                for (int digitSum = 0; digitSum <= 9 * (3 - digitNumber); ++digitSum) {
                    for (int l = 0; l < 2; ++l) {
                        if (matrix[digitNumber][remainder][digitSum][l] == 0)
                            continue;
                        long m = digits[digitNumber - 1];
                        if (l == 1)
                            m = 9;
                        for (int j = 0; j <= m; ++j) {
                            int temp = 1;
                            if (l != 1 && j == m)
                                temp = 0;
                            matrix[digitNumber - 1][(int)((remainder + j * rem[digitNumber - 1]) % K)][digitSum + j][temp]
                                    += matrix[digitNumber][remainder][digitSum][l];
                        }
                    }
                }
            }
        }

        long ans = 0;
        for (int i = P; i <= Q; ++i) {
            ans += matrix[0][0][i][0] + matrix[0][0][i][1];
        }
        return ans;
    }

}





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
        Long A = Long.parseLong(scanner.next());
        Long B = Long.parseLong(scanner.next());
        int P = Integer.parseInt(scanner.next());
        int Q = Integer.parseInt(scanner.next());
        FileWriter fout;
        try {
            fout = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fout);
            fout.write( (Solution.mainSolution(K, B, P, Q) - Solution.mainSolution(K, A-1, P, Q)) + "");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}