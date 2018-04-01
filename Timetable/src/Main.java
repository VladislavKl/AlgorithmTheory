import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

class Job implements Comparable<Job>
{
    private int	number;
    private int deadline;

    Job(int num, int dl) {
        number = num;
        deadline = dl;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    @Override
    public int compareTo(Job o) {
        return o.deadline - deadline;
    }
}

public class Main
{
    private static int jobsQuantity;
    private static int[] deadlines;
    private static int[] times;
    private static int[][] matrix;

    private static boolean[] processedJobs;
    private static Stack<Integer> jobsSequence;

    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jobsQuantity = scanner.nextInt();

        deadlines = new int[jobsQuantity];
        times = new int[jobsQuantity];
        matrix = new int[jobsQuantity][jobsQuantity];
        processedJobs = new boolean[jobsQuantity];

        for (int i = 0; i < jobsQuantity; ++i) {
            times[i] = scanner.nextInt();
            deadlines[i] = scanner.nextInt();
        }

        int arcsNumber = scanner.nextInt();

        for (int i = 0; i < arcsNumber; ++i) {
            matrix[scanner.nextInt() - 1][scanner.nextInt() - 1] = 1;
        }
        
        
        FindSolution();

        
        Stack<Integer> tempStack = (Stack<Integer>) jobsSequence.clone();

        int c = 0, m = -1, i, im = -1;
        while (!jobsSequence.empty()) {
            i = jobsSequence.pop();
            c += times[i];
            if (Integer.max(c - deadlines[i], 0) >= m) {
                m = Integer.max(c - deadlines[i], 0);
                im = i;
            }
        }

        FileWriter fout = null;
        try {
            fout = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fout);
            fout.write((im + 1) + " " + m + "\n");

            while (!tempStack.empty()) {
                i = tempStack.lastElement();
                tempStack.pop();
                fout.write((i + 1) + "\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean noJobsAfter(int job) {
        for (int i = 0; i < jobsQuantity; ++i)
            if (matrix[job][i] != 0) {
                return false;
            }
        return true;
    }

    private static boolean allJobsProcessed() {
        for (int i = 0; i < jobsQuantity; ++i)
            if (!processedJobs[i]) {
                return false;
            }
        return true;
    }

    private static void FindSolution() {
        PriorityQueue<Job> queue = new PriorityQueue<>();
        jobsSequence = new Stack<>();
        while (!allJobsProcessed()) {
            for (int i = 0; i < jobsQuantity; ++i)
                if (!processedJobs[i])
                    if (noJobsAfter(i)) {
                        processedJobs[i] = true;
                        queue.add(new Job(i, deadlines[i]));
                    }

            int num = queue.poll().getNumber();

            for (int j = 0; j < jobsQuantity; ++j)
                if (matrix[j][num] != 0)
                    matrix[j][num] = 0;
            jobsSequence.push(num);
        }
    }
}