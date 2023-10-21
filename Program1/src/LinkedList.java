public class LinkedList {
    private Node head;

    /**
    * Add a new node to the end of the list
    * @param s State to be added
    */
    public void add(State s) {
        Node newNode = new Node(s);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    /**
     * Print all the items in the linked list
     */

    public void printList() {
        Node temp = head;
        while (temp != null) {
            // print without line break
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
    }

    /**
    *  Remove the first node from the list and return the 
    * @return State removed
    */
    public State remove() {
        if (head == null) {
            return null;
        } else {
            Node temp = head;
            head = head.next;
            return temp.data;
        }
    }


    /**
     * 
     * @return number of nodes in the list
     */
    public int numberOfNodes() {
        int count = 0;
        Node temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;

    }
    
    /**
     * Get head of list
     * @return head of list
     */
    public Node getHead() {
        return head;
    }

    /**
    *  Create a linked list and add newly created moves to the end of the list
    */
    public LinkedList() {
        head = null;
    }
}
