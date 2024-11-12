import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Node {
    int value;
    Node left, right;

    public Node(int value) {
        this.value = value;
        left = right = null;
    }
}

class BinarySearchTree {
    private Node root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node node, int value) {
        if (node == null) {
            node = new Node(value);
            return node;
        }
        if (value < node.value) {
            node.left = insertRec(node.left, value);
        } else if (value > node.value) {
            node.right = insertRec(node.right, value);
        } else {
            if (node.left == null) {
                node.left = new Node(value);
            } else if (node.right == null) {
                node.right = new Node(value);
            } else {
                insertInLeftmost(node.left, value);
            }
        }
        return node;
    }

    private void insertInLeftmost(Node node, int value) {
        if (node.left == null) {
            node.left = new Node(value);
        } else if (node.right == null) {
            node.right = new Node(value);
        } else {
            insertInLeftmost(node.left, value);
        }
    }

    public void printTree() {
        List<List<String>> levels = new ArrayList<>();
        List<Node> currentLevel = new ArrayList<>();
        List<Node> nextLevel = new ArrayList<>();

        currentLevel.add(root);
        int widest = 0;
        int nonNullNodes = 1;

        while (nonNullNodes != 0) {
            List<String> line = new ArrayList<>();
            nonNullNodes = 0;

            for (Node node : currentLevel) {
                if (node == null) {
                    line.add(null);
                    nextLevel.add(null);
                    nextLevel.add(null);
                } else {
                    String valueStr = String.valueOf(node.value);
                    line.add(valueStr);
                    if (valueStr.length() > widest) widest = valueStr.length();

                    nextLevel.add(node.left);
                    nextLevel.add(node.right);

                    if (node.left != null) nonNullNodes++;
                    if (node.right != null) nonNullNodes++;
                }
            }

            levels.add(line);
            currentLevel = nextLevel;
            nextLevel = new ArrayList<>();
        }

        int perPiece = levels.get(levels.size() - 1).size() * (widest + 4);
        for (int i = 0; i < levels.size(); i++) {
            List<String> line = levels.get(i);
            int halfPiece = (int) Math.floor(perPiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '|' : '_';
                        } else if (line.get(j) != null) {
                            c = '_';
                        }
                    }
                    System.out.print(c);

                    if (line.get(j) == null) {
                        for (int k = 0; k < perPiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        for (int k = 0; k < halfPiece; k++) {
                            System.out.print(j % 2 == 0 ? " " : "_");
                        }
                        System.out.print(j % 2 == 0 ? "_" : "_");
                        for (int k = 0; k < halfPiece; k++) {
                            System.out.print(j % 2 == 0 ? "_" : " ");
                        }
                    }
                }
                System.out.println();
            }

            for (int j = 0; j < line.size(); j++) {
                String nodeValue = line.get(j);
                if (nodeValue == null) nodeValue = "";
                int leftPadding = (int) Math.ceil(perPiece / 2f - nodeValue.length() / 2f);
                int rightPadding = (int) Math.floor(perPiece / 2f - nodeValue.length() / 2f);

                for (int k = 0; k < leftPadding; k++) {
                    System.out.print(" ");
                }
                System.out.print(nodeValue);
                for (int k = 0; k < rightPadding; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perPiece /= 2;
        }
    }
}

public class BSTVisualization {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BinarySearchTree bst = new BinarySearchTree();

        System.out.println("Enter node values one by one (type 'exit' to stop):");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int node = Integer.parseInt(input);
                bst.insert(node);

                System.out.println("BST Structure after insertion of " + node + ":");
                bst.printTree();
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer value or 'exit' to stop.");
            }
        }

        scanner.close();
    }
}
