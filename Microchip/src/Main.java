import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Solution.Dot[] dots = Solution.readFromFile();
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

class Solution{

    //elements of the task
    static class Dot{

        public Dot(int iSize, int iX, int iY, String[] avD, String dir) {
            size = iSize;
            x = iX;
            y = iY;
            leftBorder = x;
            rightBorder = size - x;
            upBorder = size - y;
            downBorder = y;
            availableDirections = avD;
            direction = dir;
        }

        @Override
        public String toString() {
            return this.direction;
        }

        public int x;
        public int y;
        public int size;
        public String availableDirections[] = {"UP", "DOWN", "LEFT", "RIGHT"};
        public int leftBorder;
        public int rightBorder;
        public int upBorder;
        public int downBorder;
        public String direction;


    }

    //reads info from the file
    public static Dot[] readFromFile(){
        Scanner scanner;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
        int size = Integer.parseInt(scanner.next());
        int arraySize = Integer.parseInt(scanner.next());
        int i=0;
        Dot[] dots = new Dot[arraySize];
        while (scanner.hasNext()) {
            dots[i] = new Dot(size, Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()), new String[] {"UP", "DOWN", "LEFT", "RIGHT"}, null);
            ++i;
        }
        checkAvailability(dots);
        return dots;
    }

    //writes solution to the file
    public static void writeToFile(MinDir minDir) {
        FileWriter fout = null;
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

    //checks and deletes unavailable directions for dots
    public static void checkAvailability(Dot[] dots){
    for (int i=0; i < dots.length; ++i)
        for (int j = i + 1; j < dots.length; ++j) {
            if (dots[i].x == dots[j].x) {
                if (dots[i].y < dots[j].y) {
                    dots[i].availableDirections[0] = null;
                    dots[j].availableDirections[1] = null;
                }
                else {
                    dots[j].availableDirections[0] = null;
                    dots[i].availableDirections[1] = null;
                }
            }
            if (dots[i].y == dots[j].y) {
                if (dots[i].x < dots[j].x) {
                    dots[i].availableDirections[3] = null;
                    dots[j].availableDirections[2] = null;
                }
                else{
                    dots[j].availableDirections[3] = null;
                    dots[i].availableDirections[2] = null;
                }
            }
        }
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
    public static void solve(Dot[] dots, int numberOfDot, MinDir minDir){
        if (dots.length-1 == numberOfDot)
        {
            boolean b = true;
            if (wayExists(dots, numberOfDot, "UP")) {
                dots[numberOfDot].direction = "UP";
                for (int i = 0; i < dots.length; ++i) {
                    for (int j = i + 1; j < dots.length; ++j) {
                        if (crossingCheck(dots[i], dots[i]) == true) {
                            b = false;
                            i = dots.length;
                            j = dots.length;
                        }
                    }
                }
                if (b == true && minDir.minLength > directionsSummary(dots)) {
                    minDir.minLength = directionsSummary(dots);
                    minDir.setDots(dots);
                }
            }
                b = true;
            if (wayExists(dots, numberOfDot, "DOWN")) {
                dots[numberOfDot].direction = "DOWN";
                for (int i = 0; i < dots.length; ++i) {
                    for (int j = i + 1; j < dots.length; ++j) {
                        if (crossingCheck(dots[i], dots[i]) == true) {
                            b = false;
                            i = dots.length;
                            j = dots.length;
                        }
                    }
                }
                if (b == true && minDir.minLength > directionsSummary(dots)) {
                    minDir.minLength = directionsSummary(dots);
                    minDir.setDots(dots);
                }
            }
            b = true;
            if (wayExists(dots, numberOfDot, "LEFT")) {
                dots[numberOfDot].direction = "LEFT";
                for (int i = 0; i < dots.length; ++i) {
                    for (int j = i + 1; j < dots.length; ++j) {
                        if (crossingCheck(dots[i], dots[i]) == true) {
                            b = false;
                            i = dots.length;
                            j = dots.length;
                        }
                    }
                }
                if (b == true && minDir.minLength > directionsSummary(dots)) {
                    minDir.minLength = directionsSummary(dots);
                    minDir.setDots(dots);
                }
            }
            b = true;
            if (wayExists(dots, numberOfDot, "RIGHT")) {
                dots[numberOfDot].direction = "RIGHT";
                for (int i = 0; i < dots.length; ++i) {
                    for (int j = i + 1; j < dots.length; ++j) {
                        if (crossingCheck(dots[i], dots[i]) == true) {
                            b = false;
                            i = dots.length;
                            j = dots.length;
                        }
                    }
                }
                if (b == true && minDir.minLength > directionsSummary(dots)) {
                    minDir.minLength = directionsSummary(dots);
                    minDir.setDots(dots);
                }
            }
            return;
        }
        else {
            if (wayExists(dots, numberOfDot, "UP")) {
                dots[numberOfDot].direction = "UP";
                solve(createArrayCopy(dots), numberOfDot + 1, minDir);
            }
            if (wayExists((dots), numberOfDot, "DOWN")) {
                dots[numberOfDot].direction = "DOWN";
                solve(createArrayCopy(dots), numberOfDot + 1, minDir);
            }
            if (wayExists((dots), numberOfDot, "LEFT")) {
                dots[numberOfDot].direction = "LEFT";
                solve(dots, numberOfDot + 1, minDir);
            }
            if (wayExists((dots), numberOfDot, "RIGHT")) {
                dots[numberOfDot].direction = "RIGHT";
                solve(createArrayCopy(dots), numberOfDot + 1, minDir);
            }

        }
    }

    //Helper function so that java won't just cope a link to an object
    public static Dot[] createArrayCopy(Dot[] dots){
        Dot[] temp = new Dot[dots.length];
        for (int i=0; i< dots.length; ++i) {
            Solution.Dot kaka = new Solution.Dot(dots[i].size, dots[i].x, dots[i].y, dots[i].availableDirections, dots[i].direction);
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
        dots[numberOfDot].availableDirections = new String[] {"UP", "DOWN", "LEFT", "RIGHT"};
        cuttingDirections(dots, numberOfDot);
        if (dots[numberOfDot].availableDirections[0] == dir || dots[numberOfDot].availableDirections[1] == dir
                || dots[numberOfDot].availableDirections[2] == dir || dots[numberOfDot].availableDirections[3] == dir) {
            Dot[] temp = new Dot[dots.length];
            for (int i=0; i<dots.length; ++i) {
                Dot kaka = new Dot(dots[i].size, dots[i].x, dots[i].y, dots[i].availableDirections, dots[i].direction);
                temp[i] = kaka;
            }
            temp[numberOfDot].direction = dir;
            cuttingDirections(temp, numberOfDot);
            if (dots[numberOfDot].availableDirections[0] == null && dots[numberOfDot].availableDirections[1] == null
                    && dots[numberOfDot].availableDirections[2] == null && dots[numberOfDot].availableDirections[3] == null)
                return false;
            return true;
        }
        return false;
    }


    public static void cuttingDirections(Dot[] dots, int numberOfDot){
        for (int i = 0; i<numberOfDot; ++i){
            if (dots[numberOfDot].direction == "UP" && dots[numberOfDot].upBorder >= dots[i].upBorder){
                if (dots[numberOfDot].leftBorder >= dots[i].leftBorder)
                    dots[i].availableDirections[3] = null;
                else
                    dots[i].availableDirections[2] = null;
            }
            if (dots[numberOfDot].direction == "DOWN" && dots[numberOfDot].downBorder >= dots[i].downBorder) {
                if (dots[numberOfDot].leftBorder >= dots[i].leftBorder)
                    dots[i].availableDirections[3] = null;
                else
                    dots[i].availableDirections[2] = null;
            }
            if (dots[numberOfDot].direction == "LEFT" && dots[numberOfDot].leftBorder >= dots[i].leftBorder){
                if (dots[numberOfDot].downBorder >= dots[i].downBorder)
                    dots[i].availableDirections[0] = null;
                else
                    dots[i].availableDirections[1] = null;
            }
            if (dots[numberOfDot].direction == "RIGHT" && dots[numberOfDot].rightBorder >= dots[i].rightBorder){
                if (dots[numberOfDot].downBorder >= dots[i].downBorder)
                    dots[i].availableDirections[0] = null;
                else
                    dots[i].availableDirections[1] = null;
            }
        }
    }

}