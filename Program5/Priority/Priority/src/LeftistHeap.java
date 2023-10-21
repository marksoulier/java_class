public class LeftistHeap <E extends Comparable<E>> {
    
    int Size;
    Node<E> root;
    LeftistHeap(){
        Size = 0;
        root = null;
    }

    //insert into the leftist heap
    public void insert(E element){
        //create new node
        Node<E> newNode = new Node<E>(element);
        //merge the new node with the root
        root = merge(root, newNode);
        //increment size
        Size++;
    }

    //delete the min element
    public E deleteMin(){
        //if the root is null return null
        if (root == null) return null;
        //set the min element to the root element
        E minElement = root.element;
        //merge the left and right of the root
        root = merge(root.left, root.right);
        //return the min element
        return minElement;
    }

    //merge two leftist heaps
    public Node<E> merge(Node<E> h1, Node<E> h2){
        //if h1 is null return h2
        if (h1 == null) return h2;
        //if h2 is null return h1
        if (h2 == null) return h1;
        //if h1 is less than h2 swap them
        if (h1.element.compareTo(h2.element) < 0){
            Node<E> temp = h1;
            h1 = h2;
            h2 = temp;
        }
        //merge the right of h1 with h2
        h1.right = merge(h1.right, h2);
        //if the left of h1 is null swap the left and right
        if (h1.left == null){
            h1.left = h1.right;
            h1.right = null;
        }
        //if the npl of the left is less than the right swap them
        else{
            if (h1.left.npl < h1.right.npl){
                Node<E> temp = h1.left;
                h1.left = h1.right;
                h1.right = temp;
            }
        }
        //set the npl of h1 to the npl of the left + 1
        h1.npl = h1.left.npl + 1;
        //return h1
        return h1;
    }

    //return the min element
    public E findMin(){
        //if the root is null return null
        if (root == null) return null;
        //return the root element
        return root.element;
    }

    //get the size of the leftist heap
    public int getSize(){
        return Size;
    }

    //print the leftest heap by calling the print method
    public void print(){
        print(root);
    }

    //print the leftist heap
    public void print(Node<E> node){
        //if the node is null return
        if (node == null) return;
        //print the left
        print(node.left);
        //print the element
        System.out.print(node.element + " \n");
        //print the right
        print(node.right);
    }

    //create node class
    private static class Node<E>{
        E element;
        Node<E> left;
        Node<E> right;
        int npl;

        Node(E element){
            this.element = element;
            left = null;
            right = null;
            npl = 0;
        }
    }
}
