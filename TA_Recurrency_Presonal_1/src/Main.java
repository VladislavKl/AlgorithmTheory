import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


class Number {

    public Number() {
        num=0;
        summary=0;
    }

    public Number(int a, int b) {
        num=a;
        summary=b;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public int getSummary() {
        return summary;
    }

    public void setSummary(int summary) {
        this.summary = summary;
    }

    private int num;
    private int summary;
}

class Solution {
    public static Double mainSolution(int K, Double A, Double B, int P, int Q) {
        if (B <= 1000)
            return lessThan1000(K, A, B, P, Q);
        Double counterA = 0., counterB = 0.;
        ArrayList<ArrayList<Number>> array = new ArrayList<>();
        for (int i=0; i<K;++i) {
            ArrayList<Number> temp = new ArrayList<>();
            for (int j=i;j<1000;j+=K) {
                temp.add(new Number(j , digitSummary(j)));
                if (i==0. && withinRange(temp.get(temp.size()-1).getSummary(), P, Q) && j<=B) {
                    ++counterB;
                    if (j<A)
                        ++counterA;
                }
            }
            array.add(temp);
        }

        ArrayList<Integer> fissionResidues = new ArrayList<>();
        int realNumber = array.get(0).get(array.get(0).size()-1).getNum() + K - 1000;
        fissionResidues.add(realNumber);
        for (;;) {
            realNumber = array.get(realNumber).get(array.get(realNumber).size() - 1).getNum() + K - 1000;
            fissionResidues.add(realNumber);
            if (fissionResidues.get(0).equals(fissionResidues.get(fissionResidues.size()-2)) &&
                    fissionResidues.get(fissionResidues.size()-1).equals(fissionResidues.get(1))) {
                fissionResidues.remove(fissionResidues.size()-1);
                fissionResidues.remove(fissionResidues.size()-1);
                break;
            }
        }





        Double lowerBound = A/1000;
        Double upperBound = B/1000;
        for (Double i = 1.; i < upperBound + 1; ++i) {
            int size = array.get(realNumber).size();
            int dig = digitsSummary(i);
            for (int j = 0; j < size; ++j) {
                if (withinRange(array.get(realNumber).get(j).getSummary() + dig, P, Q)) {
                    if (i < lowerBound)
                        ++counterA;
                    if (i.equals(lowerBound) && j*K < A%1000)
                        ++counterA;
                    ++counterB;
                }
            }
            realNumber = array.get(realNumber).get(array.get(realNumber).size() - 1).getNum() + K - 1000;
        }

        return (counterB-counterA);
    }

    public static Double lessThan1000(int K, Double A, Double B, int P, int Q){
        Double newc=0.;
        for (Double i=A;i<=B;i+=K) {
            if (withinRange(digitsSummary(i), P, Q))
                ++newc;
        }
        return newc;
    }

    public static int digitsSummary(Double num) {
        int sum=0;
        while (num != 0) {
            sum += num%10;
            num/=10;
        }
        return sum;
    }

    public static int digitSummary(int num) {
        int sum=0;
        while (num != 0) {
            sum += num%10;
            num/=10;
        }
        return sum;
    }

    public static boolean withinRange(int dig, int P, int Q){
        if (dig>=P && dig<=Q)
            return true;
        return false;
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
        Double A = Double.parseDouble(scanner.next());
        Double B = Double.parseDouble(scanner.next());
        int P = Integer.parseInt(scanner.next());
        int Q = Integer.parseInt(scanner.next());
        double r = Solution.mainSolution(K, A, B, P, Q);
        int result = (int) r;
        FileWriter fout;
        try {
            fout = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fout);
            fout.write(result + "");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}