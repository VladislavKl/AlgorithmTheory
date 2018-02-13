import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
     /*   Scanner scanner;
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
        }*/
        ArrayList<Number> numbers = new ArrayList<>();
        for (long i = 0; i < 10; ++i) {
            numbers.add(new Number(i * 7, Solution.sumOfNumber(i * 7)));
        }
        for (int i=1; i<10; ++i){
            for (int j=0;j<10;++j){
                numbers.add(new Number(0, numbers.get(i).getSum()+numbers.get(j).getSum()));
            }
        }
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i=0;i<100;++i){
            integers.add(Solution.sumOfNumber(i*7));
        }

      
        for (int i=0; i<100; ++i){
            System.out.println(i*7+"    "+integers.get(i)+"    "+numbers.get(i).getSum());
        }

    }
}

class Solution {

    public static long mainSolution(int K, long A, long B, int P, int Q) {
        ArrayList<Number> numbers = new ArrayList<>();
        long i = 0;
        for (i = 1; i <= 10; ++i)
            numbers.add(new Number(i * K, sumOfNumber(i * K)));
        i = (B - 10) / K;

        return 0;
    }

    public static long count9Helper() {
        return 0;
    }

    public static long count99Helper() {
        return 0;
    }

    public static long count100Helper() {
        return 0;
    }

    public static int sumOfNumber(long N) {
        int sum = 0;
        while (N != 0) {
            sum += N % 10;
            N /= 10;
        }
        return sum;
    }

    public static boolean withinRange(long N, int P, int Q) {
        int sum = 0;
        while (N != 0) {
            sum += N % 10;
            N /= 10;
            if (sum > Q)
                return false;
        }
        if (sum >= P)
            return true;
        return false;
    }

    public static void testSMTH() {
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
        long begin = A / K;
        if (A % K == 1)
            ++begin;
        long end = B / K;
        ArrayList<Number> numbers = new ArrayList<>();
        for (long i = begin; i < begin + 12; ++i) {
            numbers.add(new Number(i * K, sumOfNumber(i * K)));
        }
        long diff = numbers.get(11).getSum()-numbers.get(0).getSum();

        System.out.println(diff);

        FileWriter fout = null;
        try {
            fout = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fout);
            //fout.write(result+"");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Number{

    public Number(){
        sum=0; number=0;
    }

    public Number(long num, long summ){
        number=num; sum=summ;
    }

    public long getSum() {
        return sum;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    private long number;
    private long sum;
}