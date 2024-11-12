/* Binary Search Tree Implementation
 * Author: Ritika Suman
 * Date: November 12, 2024
 * This program implements a Binary Search Tree (BST) where nodes are inserted and displayed in a visually organized manner. 
 * 1. Inserting nodes
 * 2. Changing the root of the BST 
 * 3. Printing the tree structure in a formatted
 */

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Scanner;
 
 class TreeNode {
     int value;
     TreeNode left, right;
 
     // Constructor to initialize a node with a given value
     public TreeNode(int value) {
         this.value = value;
         left = right = null;
     }
 }
 
 class BinarySearchTree {
     private TreeNode root;
     private int nodeCount;
     private static final int MAX_NODES = 15;
     private static final int MAX_VALUE = 999;
 
     // Constructor to initialize an empty binary search tree
     public BinarySearchTree() {
         root = null;
         nodeCount = 0;
     }
 
     // Inserts a new value into the tree if the node limit and value constraints are met
     public void insert(int value) {
         if (nodeCount >= MAX_NODES) {
             System.out.println("Cannot insert more than " + MAX_NODES + " nodes.");
             return;
         }
         if (value > MAX_VALUE) {
             System.out.println("Node value cannot exceed " + MAX_VALUE + ".");
             return;
         }
         root = insertRecursively(root, value);
         nodeCount++;
     }
 
     // Recursively inserts a value into the appropriate position in the tree
     private TreeNode insertRecursively(TreeNode node, int value) {
         if (node == null) {
             return new TreeNode(value);
         }
         if (value < node.value) {
             node.left = insertRecursively(node.left, value);
         } else {
             node.right = insertRecursively(node.right, value);
         }
         return node;
     }
 
     // Changes the root of the tree to a new value, maintaining other nodes
     public void changeRoot(int newValue) {
         if (newValue > MAX_VALUE) {
             System.out.println("New root value cannot exceed " + MAX_VALUE + ".");
             return;
         }
 
         if (root == null) {
             root = new TreeNode(newValue);
             nodeCount++;
             return;
         }
 
         List<Integer> values = new ArrayList<>();
         gatherAllNodes(root, values, newValue);
 
         root = new TreeNode(newValue);
         nodeCount = 1;
         for (int value : values) {
             insert(value);
         }
     }
 
     // Gathers all the node values in the tree, excluding a specified value
     private void gatherAllNodes(TreeNode node, List<Integer> values, int excludeValue) {
         if (node == null) return;
         if (node.value != excludeValue) {
             values.add(node.value);
         }
         gatherAllNodes(node.left, values, excludeValue);
         gatherAllNodes(node.right, values, excludeValue);
     }
 
     // Prints the structure of the binary search tree in a visually organized manner
     public void printTree() {
         List<List<String>> levels = new ArrayList<>();
         List<TreeNode> currentLevel = new ArrayList<>();
         List<TreeNode> nextLevel = new ArrayList<>();
 
         currentLevel.add(root);
         int widest = 0;
         int nonNullNodes = 1;
 
         while (nonNullNodes != 0) {
             List<String> line = new ArrayList<>();
             nonNullNodes = 0;
 
             for (TreeNode node : currentLevel) {
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
 
 public class BST_TASK2{
     public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         BinarySearchTree bst = new BinarySearchTree();
 
         System.out.println("---Enter node values one by one (type 'exit' to stop)(MAX 15 nodes):---");
         System.out.println("        ---Type 'newroot <value>' to set a new root value---");
 
         while (true) {
             String input = scanner.nextLine().trim();
 
             if (input.equalsIgnoreCase("exit")) {
                 break;
             }
 
             if (input.startsWith("newroot")) {
                 try {
                     int newRootValue = Integer.parseInt(input.split(" ")[1]);
                     bst.changeRoot(newRootValue);
                     System.out.println("BST Structure after changing root to " + newRootValue + ":");
                     bst.printTree();
                     System.out.println();
                 } catch (Exception e) {
                     System.out.println("Invalid format. Use 'newroot -value-' to set a new root.");
                 }
             } else {
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
         }
 
         scanner.close();
     }
 }
 