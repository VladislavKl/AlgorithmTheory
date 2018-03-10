import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int N = 38;
    public static final int A = 30;

    //public static int[][][] field = new int[A][A][];


    public static void main(String[] args) {

        ArrayList<Solution.Dot> dots = Solution.readFromFile();
        MinDir minDir = new MinDir(dots.length);
        Solution.checkAvailability(dots);
        Solution.solve(dots, 0, minDir);
        Solution.writeToFile(minDir);
    }

}

//has the solution array
class MinDir{
    public  int minLength;
    public  Solution.Dot[] dots;

    public MinDir(int size){
        minLength = 1000000000;
        dots = new Solution.Dot[size];
    }

    public void setDots(Solution.Dot[] dots) {
        for (int i=0; i< dots.length; ++i) {
            Solution.Dot kaka = new Solution.Dot(dots[i].size, dots[i].x, dots[i].y, dots[i].availableDirections, dots[i].direction);
            this.dots[i] = kaka;
        }
    }

    @Override
    public String toString() {
        String out=minLength+"\n";
        for (Solution.Dot temp : dots)
            out+=temp.direction+"\n";
        return out;
    }

}

class Solution {


    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int A = 30;
    public static final int N = 38;

    public static int dp[][][][][] = new int[A][A][A][A][A];

    //elements of the task
    static class Dot implements Comparable<Dot>{

        public Dot(int iX, int iY) {
            x = iX;
            y = iY;
        }

        public Dot(){
            x=0;
            y=0;
        }

        @Override
        public int compareTo(Dot o) {
            return Integer.compare(x,o.x);
        }
        @Override
        public String toString() {
            return this.direction+"";
        }


        public int x;
        public int y;
        public boolean[] availableDirections = {true, true, true, true};
        public int direction;


    }

    public static int min (int a, int b, int c, int d, boolean[] two) {
        if (a == c || a==d || b==c || b==d)
            two[0] = true;
        if (a < b && a < c && a < d) {
            return a;
        } else if (b < a && b < c && b < d) {
            return b;
        } else if (c < a && c < b && c < d) {
            return c;
        } else {
            return d;
        }
    }

    //reads info from the file
    public static ArrayList<Dot> readFromFile(){
        Scanner scanner;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
        int size = scanner.nextInt();
        int arraySize = scanner.nextInt();
        ArrayList<Dot> dots = new ArrayList<>();
        for (int i = 0; i < arraySize; ++i) {
            dots.add(new Dot(scanner.nextInt(), scanner.nextInt()));
        }
        Collections.sort(dots);
        return dots;
    }

    //writes solution to the file
    public static void writeToFile(MinDir minDir) {
        FileWriter fout;
        try {
            fout = new FileWriter("output.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fout);
            fout.write(minDir.minLength+"\n");
            for (Dot temp : minDir.dots) {
                fout.write(temp+"\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checking(Dot[] dots) {
        for (int i = 0; i < dots.length; ++i) {
            if (dots[i].availableDirections[0] != null && dots[i].availableDirections[1] != null &&
                    dots[i].availableDirections[2] != null && dots[i].availableDirections[3] != null) {
                boolean[] two = new boolean[]{false, false};
                int minBorder = min(dots[i].leftBorder, dots[i].rightBorder, dots[i].upBorder, dots[i].downBorder, two);
                boolean fl = false, fr = false, fu = false, fd = false;
                if (minBorder == dots[i].leftBorder)
                    fl = true;
                if (minBorder == dots[i].rightBorder)
                    fr = true;
                if (minBorder == dots[i].downBorder)
                    fd = true;
                if (minBorder == dots[i].upBorder)
                    fu = true;
                if (!two[0]) {
                    if (fl) {
                        dots[i].availableDirections = new String[]{null, null, "LEFT", null};
                        dots[i].direction = "LEFT";
                    }
                    if (fr) {
                        dots[i].availableDirections = new String[]{null, null, null, "RIGHT"};
                        dots[i].direction = "RIGHT";
                    }
                    if (fu) {
                        dots[i].availableDirections = new String[]{"UP", null, null, null};
                        dots[i].direction = "UP";
                    }
                    if (fd) {
                        dots[i].availableDirections = new String[]{null, "DOWN", null, null};
                        dots[i].direction = "DOWN";
                    }
                    else if (!(fl && fr && fd && fu)) {
                        if (fl && fu)
                            dots[i].availableDirections = new String[]{"UP", null, "LEFT", null};
                        if (fl && fd)
                            dots[i].availableDirections = new String[]{null, "DOWN", "LEFT", null};
                        if (fr && fu)
                            dots[i].availableDirections = new String[]{"UP", null, null, "RIGHT"};
                        if (fr && fd)
                            dots[i].availableDirections = new String[]{null, "DOWN", null, "RIGHT"};
                    }
                }
            }
        }
    }

    //checks and deletes unavailable directions for dots
    public static boolean checkAvailability(Dot[] dots) {
        boolean pointer = false;
        for (int i = 0; i < dots.length; ++i) {
            for (int j = i + 1; j < dots.length; ++j) {
                if (dots[i].x == dots[j].x) {
                    if (dots[i].y < dots[j].y) {
                        dots[i].availableDirections[0] = null;
                        dots[j].availableDirections[1] = null;
                        pointer = true;
                    } else {
                        dots[j].availableDirections[0] = null;
                        dots[i].availableDirections[1] = null;
                        pointer = true;
                    }
                }
                if (dots[i].y == dots[j].y) {
                    if (dots[i].x < dots[j].x) {
                        dots[i].availableDirections[3] = null;
                        dots[j].availableDirections[2] = null;
                        pointer = true;
                    } else {
                        dots[j].availableDirections[3] = null;
                        dots[i].availableDirections[2] = null;
                        pointer = true;
                    }
                }
            }

        }
        return pointer;
    }

    //checks if there any crossings in the directions of two dots
    public static boolean crossingCheck(Dot a, Dot b){
        if ((a.x == b.x) &&
                ((a.upBorder < b.upBorder &&
                        (a.direction == "DOWN" || b.direction == "UP"))
                        ||
                        (a.upBorder > b.upBorder &&
                                (b.direction == "DOWN" || a.direction == "UP"))))
            return true;
        if ((a.y == b.y) &&
                ((a.leftBorder < b.leftBorder &&
                        (b.direction == "LEFT" || a.direction == "RIGHT"))
                        ||
                        ( a.leftBorder > b.leftBorder && (a.direction == "LEFT" || b.direction == "RIGHT"))))
            return true;
        if (a.x<b.x && a.y < b.y){
            if ((a.direction == "RIGHT" && b.direction == "DOWN") || (a.direction == "UP" && b.direction == "LEFT"))
                return true;
        }
        if (a.x<b.x && a.y > b.y){
            if ((a.direction == "DOWN" && b.direction == "LEFT") || (a.direction == "RIGHT" && b.direction == "UP"))
                return true;
        }
        if (a.x>b.x){
            Dot tempA = b;
            Dot tempB = a;
            crossingCheck(tempA, tempB);
        }
        return false;
    }

    //Main Solution recursive function
    public static void solve(int left, int top, int bottom, int topAllowed, int bottomAllowed){




    }

    // helper function for the main solution
    public static void helpSolution (Dot[] dots, int numberOfDot, MinDir minDir, String direct){
        boolean b = true;
        if (wayExists(dots, numberOfDot, direct)) {
            dots[numberOfDot].direction = direct;
            for (int i = 0; i < dots.length; ++i) {
                for (int j = i + 1; j < dots.length; ++j) {
                    if (crossingCheck(dots[i], dots[j])) {
                        b = false;
                        i = dots.length;
                        j = dots.length;
                    }
                }
            }
            if (b && minDir.minLength > directionsSummary(dots)) {
                minDir.minLength = directionsSummary(dots);
                minDir.setDots(dots);
            }
        }
    }

    //Helper function so that java won't just cope a link to an object
    public static Dot[] createArrayCopy(Dot[] dots){
        Dot[] temp = new Dot[dots.length];
        for (int i=0; i< dots.length; ++i) {
            Solution.Dot kaka = new Solution.Dot(dots[i].size, dots[i].x, dots[i].y, dots[i].availableDirections, dots[i].temporateDirections, dots[i].direction);
            temp[i] = kaka;
        }
        return temp;
    }

    //Summary of connections' length
    public static int directionsSummary(Dot[] dots){
        int sum = 0;
        for (int i = 0; i < dots.length; ++i){
            if (dots[i].direction == "UP")
                sum += dots[i].upBorder;
            if (dots[i].direction == "DOWN")
                sum += dots[i].downBorder;
            if (dots[i].direction == "LEFT")
                sum += dots[i].leftBorder;
            if (dots[i].direction == "RIGHT")
                sum += dots[i].rightBorder;
        }
        return sum;
    }

    public static boolean wayExists(Dot[] dots, int numberOfDot, String dir){
        if (dots[numberOfDot].availableDirections[0] == dir || dots[numberOfDot].availableDirections[1] == dir
                || dots[numberOfDot].availableDirections[2] == dir || dots[numberOfDot].availableDirections[3] == dir) {
            dots[numberOfDot].temporateDirections = dots[numberOfDot].availableDirections;
            Dot[] temp = createArrayCopy(dots);
            cuttingDirections(temp, numberOfDot);
            if (temp[numberOfDot].temporateDirections[0] == dir || temp[numberOfDot].temporateDirections[1] == dir
                    || temp[numberOfDot].temporateDirections[2] == dir || temp[numberOfDot].temporateDirections[3] == dir) {
                temp[numberOfDot].direction = dir;
                return true;
            }
        }
        return false;
    }


    public static void cuttingDirections(Dot[] dots, int numberOfDot){
        for (int i = 0; i<numberOfDot; ++i){
            if (dots[numberOfDot].direction == "UP" && dots[numberOfDot].upBorder >= dots[i].upBorder){
                if (dots[numberOfDot].leftBorder >= dots[i].leftBorder)
                    dots[i].temporateDirections[3] = null;
                else
                    dots[i].temporateDirections[2] = null;
            }
            if (dots[numberOfDot].direction == "DOWN" && dots[numberOfDot].downBorder >= dots[i].downBorder) {
                if (dots[numberOfDot].leftBorder >= dots[i].leftBorder)
                    dots[i].temporateDirections[3] = null;
                else
                    dots[i].temporateDirections[2] = null;
            }
            if (dots[numberOfDot].direction == "LEFT" && dots[numberOfDot].leftBorder >= dots[i].leftBorder){
                if (dots[numberOfDot].downBorder >= dots[i].downBorder)
                    dots[i].temporateDirections[0] = null;
                else
                    dots[i].temporateDirections[1] = null;
            }
            if (dots[numberOfDot].direction == "RIGHT" && dots[numberOfDot].rightBorder >= dots[i].rightBorder){
                if (dots[numberOfDot].downBorder >= dots[i].downBorder)
                    dots[i].temporateDirections[0] = null;
                else
                    dots[i].temporateDirections[1] = null;
            }
        }
    }

}