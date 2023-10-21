public class Node {
    State data;
    Node next;

    public Node(State s) {
        this.data = s;
        this.next = null;
    }

    public Node(State s, Node n) {
        this.data = s;
        this.next = n;
    }
}