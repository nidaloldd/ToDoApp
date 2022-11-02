package Model;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
    private Node<T> root;

    public Node<T> getRoot() {
        return root;
    }

    public Tree(Node<T> root) {
        this.root = root;
        root.children = new ArrayList<Node<T>>();
    }
    public Tree(T root) {
        Node rootNode = new Node<T>(root);
        this.root = rootNode;
        rootNode.children = new ArrayList<Node<T>>();
        rootNode.parent = null;
    }
    public static class Node<T> {
        public Node(T data) {
            this.data = data;
        }
        private T data;
        private Node<T> parent;

        private List<Node<T>> children = new ArrayList<Node<T>>();

        public List<Node<T>> getChildren() {
            return children;
        }
        public T getData() {
            return data;
        }
    }
    public void add(Node parent,Node... args){
        for(Node node:args){
            parent.children.add(node);
            node.parent = parent;
        }
    }
    public void print(Node actualNode){

        System.out.println(actualNode.data);
        for (int i = 0; i <actualNode.children.size() ; i++) {
            print((Node) actualNode.children.get(i));
        }
    }

    public static void main(String[] args) {
        Node<String> root = new Node<>("root");
            Node<String> l1 = new Node<>("l1");
                Node<String> r1 = new Node<>("r1");
                Node<String> r2 = new Node<>("r2");
            Node<String> l2 = new Node<>("l2");
            Node<String> l3 = new Node<>("l3");
                Node<String> e1 = new Node<>("e1");
                Node<String> e2 = new Node<>("e2");

        Tree<String> tree = new Tree<>("root");

        tree.add(root,l1,l2,l3);
        tree.add(l1,r1,r2);
        tree.add(l3,e1,e2);

        tree.print(root);
    }

}