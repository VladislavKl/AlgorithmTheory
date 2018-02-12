import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Tree tree = new Tree();
        Scanner scanner;
        try {
            scanner = new Scanner(new File("in.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return;
        }
        while (scanner.hasNext()) {
            tree.add(Integer.parseInt(scanner.next()));
        }
        Solution.mainSolution(tree);
    }

}

class Tree {

    static class Node {

        public Node(int value) {
            key = value;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public Node getLeft() {
            return left;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getKey() {
            return key;
        }

        public int getTemporateHeight() {
            return temporateHeight;
        }

        public void setTemporateHeight(int temporateHeight) {
            this.temporateHeight = temporateHeight;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public void setIsSolution(boolean solution) {
            isSolution = solution;
        }

        public boolean getIsSolution(){
            return isSolution;
        }
        private int key;
        private int temporateHeight;
        private Node left;
        private Node right;
        private int length;
        private int weight;
        private boolean isSolution;
    }

    public void add(int x) {
        root = addTo(root, x);
    }

    private static Node addTo(Node node, int x) {
        if (node == null) {
            return new Node(x);
        }
        if (x < node.key) {
            node.left = addTo(node.left, x);
        } else if (x > node.key) {
            node.right = addTo(node.right, x);
        }
        return node;
    }

    public void preOrder(Node t, BufferedWriter fileWriter) throws IOException { //рекурсивная функция
        if (t == null)
            return;
        else {
            int temp = t.key;
            fileWriter.write(temp + "\n");
            preOrder(t.left, fileWriter);
            preOrder(t.right, fileWriter);
        }
    }

    public Node findMin(Node v){
        if (v.left != null)
            return findMin(v.left);
        else return v;
    }

    public Node delete(Node v, int x){
        if (v == null)
            return null;
        if (x < v.key) {
            v.left = delete(v.left, x);
            return v;
        }
        if (x > v.key) {
            v.right = delete(v.right, x);
            return v;
        }

        if (v.left == null) {
            return v.right;
        }
        else if (v.right == null) {
            return v.left;
        }
        else {
            int min = findMin(v.right).key;
            v.key = min;
            v.right = delete(v.right, min);
            return v;
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    private Node root;
}

class Solution{

    //решение задачи
    public static void mainSolution(Tree tree){
        if (tree.getRoot() != null) {
            postOrderHelper(tree.getRoot()); //для корня дерева находим наименьшую длину и наименьший вес
            HashSet<Tree.Node> dupletes = new HashSet<>();
            //вершина, которую нужно удалить
            Tree.Node finalNode = chooseFromDupleteNodes(twoKidsTraversalHelper(tree.getRoot(), dupletes, true), tree.getRoot());
            // в случае четности длины, можем найти центр полупути
            if (finalNode.getLength() % 2 == 0) {
                finalNode = findCentreOfFinalNode(finalNode); // ищем центр
                tree.delete(tree.getRoot(), finalNode.getKey()); // удаляем
            }
            FileWriter fout = null;
            try {
                fout = new FileWriter("out.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fout);
                tree.preOrder(tree.getRoot(), bufferedWriter);// выводим в прямом порядке
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            return;
    }

    // из всех вершин с двумя детьми находит наменьшую по длине и весу, если у корня не два ребенка, возвращает onlyChildSolution
    public static Tree.Node chooseFromDupleteNodes(HashSet<Tree.Node> dupletes, Tree.Node root) {
            Tree.Node result = (Tree.Node) dupletes.toArray()[0];
            Tree.Node temp;
            for (int i = 1; i < dupletes.size(); ++i) {
                temp = (Tree.Node) dupletes.toArray()[i];
                if (result.getLength() > temp.getLength())
                    result = temp;
                else if (result.getWeight() > temp.getWeight())
                    result = temp;
                else if (result.getLength() == temp.getLength() && result.getWeight() == temp.getWeight() && result.getKey() > temp.getKey())
                    result = temp;
            }
            return result;
    }

    // находит вершины с двумя детьми, находит их наименьший вес и наименьшую длину, запихивает в dupletes
    public static HashSet<Tree.Node> twoKidsTraversalHelper(Tree.Node root, HashSet<Tree.Node> dupletes, boolean b)
            throws StackOverflowError{
        if (b == true){
            postOrderHelper(root);
            dupletes.add(root);
            b=false;
        }
        if (root.getLeft() != null && root.getRight() != null){
            postOrderHelper(root);
            dupletes.add(root);
            twoKidsTraversalHelper(root.getLeft(), dupletes, b);
            twoKidsTraversalHelper(root.getRight(), dupletes, b);
        } else if (root.getLeft() != null)
            twoKidsTraversalHelper(root.getLeft(), dupletes, b);
        else if (root.getRight() != null)
            twoKidsTraversalHelper(root.getRight(), dupletes, b);
        return dupletes;
    }

    // для заданной вершины находит длину наименьшего пути как сумма путей до ближайших листьев, а так же вес ее вес как сумму весов до ближайших листьев
    public static void postOrderHelper(Tree.Node root){
        postOrderSetHeight(root, 0);
        int leftSum=0, rightSum=0,  leftTempHeight=-1, rightTempHeight=-1;
        if (root.getLeft() != null) {
            leftSum = lightestPath(root.getLeft(), root.getLeft().getKey());
            leftTempHeight = root.getLeft().getTemporateHeight();
        }
        if (root.getRight() != null) {
            rightSum = lightestPath(root.getRight(), root.getRight().getKey());
            rightTempHeight = root.getRight().getTemporateHeight();
        }
        root.setLength(leftTempHeight+rightTempHeight+2);
        root.setWeight(leftSum+rightSum+root.getKey());
    }

    //ищет центр для найденной вершины с двумя детьми
    public static Tree.Node findCentreOfFinalNode(Tree.Node root) {
        if (root.getLeft() == null || root.getRight() ==null || root.getIsSolution() == true)
            return root;
        postOrderSetHeight(root, 0);
        ArrayList<Tree.Node> nodes = new ArrayList<>();
        nodes.add(root);
        Tree.Node leftKid;
        Tree.Node rightKid;
        if (root.getLeft() != null) {
            leftKid = root.getLeft();
            nodes.add(leftKid);
            findCentreHelper(leftKid,nodes);
        }
        if (root.getRight() != null) {
            rightKid = root.getRight();
            nodes.add(rightKid);
            findCentreHelper(rightKid, nodes);
        }
        Collections.sort(nodes, new Comparator<Tree.Node>() {
            @Override
            public int compare(Tree.Node o1, Tree.Node o2) {
                return o1.getTemporateHeight()-o2.getTemporateHeight();
            }
        });
        return nodes.get(nodes.size()-1);
    }

    public static void findCentreHelper(Tree.Node root, ArrayList<Tree.Node> nodes){
        while (true) {
            if (root.getLeft() != null && root.getRight() != null) {
                if (root.getLeft().getTemporateHeight() < root.getRight().getTemporateHeight()) {
                    root = root.getLeft();
                    nodes.add(root);
                } else {
                    root = root.getRight();
                    nodes.add(root);

                }
            } else if (root.getLeft() != null) {
                root = root.getLeft();
                nodes.add(root);
            } else if (root.getRight() != null) {
                root = root.getRight();
                nodes.add(root);
            } else {
                return;
            }
        }

    }

    // для заданной вершины выводит самый легкий путь
    public static int lightestPath (Tree.Node root, int keySum) throws StackOverflowError{
        if (root==null){
            return 0;
        } else if (root.getRight() != null && root.getLeft() != null){
            int min = lightestPath(root.getLeft(), keySum+root.getLeft().getKey());
            int temp = lightestPath(root.getRight(), keySum+root.getRight().getKey());
            if (min<temp)
                return min;
            else
                return temp;
        } else if (root.getLeft() != null){
            keySum += root.getLeft().getKey();
            lightestPath(root.getLeft(),keySum);
        } else if (root.getRight() != null){
            keySum +=root.getRight().getKey();
            lightestPath(root.getRight(), keySum);
        }
        return keySum;
    }
    
    // расставляет высоты вниз, начиная с заданной вершины, высота каждой вершины выставляется наименьшая из возможных
    public static void postOrderSetHeight(Tree.Node root, int heightToSet)throws StackOverflowError {
        if (root!=null) {
            postOrderSetHeight(root.getLeft(), heightToSet);
            postOrderSetHeight(root.getRight(), heightToSet);
            if (root.getLeft() == null && root.getRight() ==null)
                root.setTemporateHeight(0);
            else if (root.getLeft() == null)
                root.setTemporateHeight(root.getRight().getTemporateHeight()+1);
            else if (root.getRight() == null)
                root.setTemporateHeight(root.getLeft().getTemporateHeight()+1);
            else if (root.getLeft().getTemporateHeight()<root.getRight().getTemporateHeight())
                root.setTemporateHeight(root.getLeft().getTemporateHeight()+1);
            else
                root.setTemporateHeight(root.getRight().getTemporateHeight()+1);
        }
    }

}