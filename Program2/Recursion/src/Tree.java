// ******************ERRORS********************************
// Throws UnderflowException as appropriate

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     *
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}

public class Tree {
    private BinaryNode root;  // Root of tree
    private String treeName;     // Name of tree
    private static int N = 0; //size of the tree

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Create tree from list
     *
     * @param arr     List of elements
     * @param label   Name of tree
     * @param ordered true if want an ordered tree
     */
    public Tree(Integer[] arr, String label, boolean ordered) {
        N = arr.length;
        treeName = label;
        if (ordered) {
            root = null;
            for (int i = 0; i < arr.length; i++) {
                bstInsert(arr[i]);
            }
        } else root = buildUnordered(arr, 0, arr.length - 1);
    }

    /**
     * Build a NON BST tree by inorder
     *
     * @param arr   nodes to be added
     * @return new tree
     */
    private BinaryNode buildUnordered(Integer[] arr, int low, int high) {
        if (low > high) return null;
        int mid = (low + high) / 2;
        BinaryNode curr = new BinaryNode(arr[mid], null, null);
        curr.left = buildUnordered(arr, low, mid - 1);
        curr.right = buildUnordered(arr, mid + 1, high);
        return curr;
    }

    /**
     * Change name of tree
     *
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return a string displaying the tree contents as a indented vertial list
     */
    public String toString() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + "\n" + toString(root, 0);
    }

    /**
     * Return a string displaying the tree contents as a veritcal line
     */
    public String toString(BinaryNode t, int depth) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString(t.left, depth+1));
        for(int i=0; i<depth; i++){
            sb.append("     ");
        }
        sb.append(t.element.toString() +"(" + depth(t.element) + ")" + " " + "\n");
        sb.append(toString(t.right, depth+1));
        return sb.toString();
    }

    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + toString2(root);
    }

    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(n)
     *
     * @param t the node that roots the subtree.
     */
    public String toString2(BinaryNode t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }

    /**
     * Find the depth of a node in the tree
     * @return
     */
    public Integer depth(Integer x) {
        return depth(x, root, 0);
    }

    private Integer depth(Integer x, BinaryNode t, int level) {
        //Traversal of tree

        // stop if node is null or if element is found
        if (t == null) return null;
        if (t.element == x) return level;

        // check left and right subtrees
        Integer left = depth(x, t.left, level + 1);
        Integer right = depth(x, t.right, level + 1);

        // return a value 
        if(left == null){
            return right;
        }else{
            return left;}
    }

    /**
     * The complexity of finding the deepest node is O(log(n))
     *
     * @return
     */
    static int maxDepth;
    public Integer deepestNode() {
        deepestNode(root, 0);
        return maxDepth;
    }
    /**
     * Recursive function for deepest node
     * @param t node
     * @param level the level of the element in question is at
     * @return
     */
    public void deepestNode(BinaryNode t, int level) {
        if (t == null) return;
        
        // check left and right subtrees
        deepestNode(t.left, level+1);
        deepestNode(t.right, level+1);

        if(t.left == null && t.right == null) {
            if(level > maxDepth) {
                maxDepth = level;
            }
        }
        return;
    }

    /**
     * The complexity of finding the flip is O(n)
     * reverse left and right children recursively
     */
    public void flip() {
        flip(root);
    }

    private void flip(BinaryNode t) {
        if (t == null) return;
        flip(t.left);
        flip(t.right);
        BinaryNode temp = t.left;
        t.left = t.right;
        t.right = temp;
    }

    /**
     * Counts number of nodes in specified level
     * The complexity of nodesInLevel is O(n)
     *
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */
    public int nodesInLevel(int level) {
        count = 0;
        return nodesInLevel(level, root, 0);
    }

    /**
     * Recursive function for finding the nodesInLevel
     * 
     * @return count of nodes in level
     */
    static int count = 0;
    public int nodesInLevel(int level, BinaryNode t, int curr_level) {
        if(t == null) return 0;
        if(curr_level == level){
            count += 1;
        }
        
        Integer left = nodesInLevel(level, t.left, curr_level+1);
        Integer right = nodesInLevel(level, t.right, curr_level+1);

        return count;
    }

    /**
     * Print all paths from root to leaves
     * The complexity of printAllPaths is O(n)
     * @return String of all paths
     */
    public void printAllPaths() {
        StringBuilder sb = new StringBuilder();
        sb = printAllPaths(root, " ", sb);
        System.out.println(sb);
    }

    /**
     * Recursive function of printAllPaths
     * @param t node
     * @param path path of node
     * @param sb stringbuilder
     * @return stringbuilder
     */
    public StringBuilder printAllPaths(BinaryNode t, String path, StringBuilder sb) {
        if(t == null) return sb;
        if(t.left == null && t.right == null){
            sb.append(path + "," + t.element.toString() + "\n");
        }
        if(path == " "){
            sb = printAllPaths(t.left, path + t.element.toString(), sb);
            sb = printAllPaths(t.right, path + t.element.toString(), sb);
        }else {
            sb = printAllPaths(t.left, path + "," + t.element.toString(), sb);
            sb = printAllPaths(t.right, path + "," + t.element.toString(), sb);
        }
        return sb;
    }

    /**
     * Counts all non-null binary search trees embedded in tree
     * The complexity of countBST is O(n)
     *
     * @return Count of embedded binary search trees
     */
    public Integer countBST() {
        if (root == null) return 0;
        //create 2d vector
        Integer[] arr = new Integer[2];
        arr = countBST(root);
        return arr[0];
    }

    /**
     * Recursive function for countBST
     * @param t node
     * @return count of subtrees and if it is still a BST held in a sd vector
     */
    public Integer[] countBST(BinaryNode t) {
        if (t == null) {
            Integer[] arr = new Integer[2];   
            arr[0] = 0;
            arr[1] = 1;
            return arr;
        }
        //post traversal
        Integer[] left = countBST(t.left);
        Integer[] right = countBST(t.right);
        //if the tree is a leaf
        if (t.left == null && t.right == null) {
            Integer[] arr = new Integer[2];   
            arr[0] = 1;
            arr[1] = 1;
            return arr;
        }
        //if the tree is a leaf or node with a lower child on the left and a higher child on the right
        //and if the element is between the values of its childreen and both trees are BST
        if(left[1] == 1 && right[1] == 1){
            //if tree has right child only
            if(t.left == null){
                if(t.element < t.right.element){
                    Integer[] arr = new Integer[2];   
                    arr[0] = left[0] + right[0] + 1;
                    arr[1] = 1;
                    return arr;
                }
                //if tree has left child only
            }else if(t.right == null){
                if(t.element > t.left.element){
                    Integer[] arr = new Integer[2];   
                    arr[0] = left[0] + right[0] + 1;
                    arr[1] = 1;
                    return arr;
                }
                //if tree has both children
            }else if(t.element > t.left.element && t.element < t.right.element){
                Integer[] arr = new Integer[2];   
                arr[0] = left[0] + right[0] + 1;
                arr[1] = 1;
                return arr;
            }
        }
        Integer[] arr = new Integer[2];
        arr[0] = left[0] + right[0];
        arr[1] = 0;
        return arr;
    }
    /**
     * Insert into a bst tree; duplicates are allowed
     * The complexity of bstInsert depends on the tree.  If it is balanced the complexity is O(log n)
     * 
     * @param x the item to insert.
     */
    public void bstInsert(Integer x) {
        root = bstInsert(x, root);
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode bstInsert(Integer x, BinaryNode t) {
        if (t == null)
            return new BinaryNode(x, null, null);
        int compareResult = x.compareTo(t.element);
        if (compareResult < 0) {
            t.left = bstInsert(x, t.left);
        } else {
            t.right = bstInsert(x, t.right);
        }
        return t;
    }

    /**
     * Determines if item is in tree
     *
     * @param item the item to search for.
     * @return true if found.s
     */
    public boolean contains(Integer item) {
        return contains(item, root);
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains(Integer x, BinaryNode t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }

    /**
     * Remove all paths from tree that sum to less than given value
     * 
     * @param sum: minimum path sum allowed in final tree
     */
    public void pruneK(Integer sum) {
        pruneK(sum, 0, root, null, ' ');
        if(root.left == null && root.right == null && root.element < sum) root = null;
    }

    /**
     * 
     * @param sum, total sum needed
     * @param path_sum, array of path
     * @param t current node
     * @param parent parent of node
     * @param direction charecter if left or right
     */
    public void pruneK(int sum, int path_sum, BinaryNode t, BinaryNode parent, char direction) {
        if (t == null) return;
        path_sum += t.element;
        pruneK(sum, path_sum, t.left, t, 'l');
        pruneK(sum, path_sum, t.right, t, 'r');

        if (t.left == null && t.right == null) {
            if (path_sum < sum) {
                if (direction == 'l') {
                    parent.left = null;
                } else if (direction == 'r') {
                    parent.right = null;
                }
            }
        }
    }

    /**
     * Build tree given inOrder and preOrder traversals.  Each value is unique
     *
     * @param inOrder  List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     */
    public void buildTreeTraversals(Integer[] inOrder, Integer[] preOrder) {
        root = null;
    }

    /**
     * Find the least common ancestor of two nodes
     *
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     */
    public BinaryNode lca(BinaryNode t, Integer a, Integer b) {
        if (t.element.compareTo(b) > 0) {
            return lca(t.left, a, b);
        } else if (t.element.compareTo(a) < 0) {
            return lca(t.right, a, b);
        } else return t;
    }

    public Integer sumAll() {
        BinaryNode r = root;
        return sumAll(r);
    }

    public Integer sumAll(BinaryNode t) {
        if (t == null) return 0;
        return t.element + sumAll(t.left) + sumAll(t.right);
    }

    public Integer lca(Integer a, Integer b) {
        if(find(a) == false || find(b) == false) return null;
        BinaryNode l = lca(root, a, b);
        if (l == null) return null;
        return l.element;
    }

    /**
     * find function to ensure a and b exist in the tree
     */

    public boolean find(Integer x) {
        return find(x, root);
    }

    /**
     * find recursive function
     * @param x value to find
     * @param t node
     * @return a boolean of it is found
     */
    public boolean find(Integer x, BinaryNode t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return find(x, t.left);
        else if (compareResult > 0)
            return find(x, t.right);
        else {
            return true;
        }
    }

    /**
     * Balance the tree
     * Takes in a tree and reads of its inorder expression, sorts it then builds a new tree
     */
    public void balanceTree() {
        Integer[] arr = new Integer[N];
        InOrder(root, 0, arr);
        // organize the list by value
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    Integer temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            //System.out.println(arr[i]);
        }
        // for (int i = 0; i < arr.length; i++) {
        //     System.out.println(arr[i]);
        // }
        root = null;
        root = buildUnordered(arr, 0, N-1);
        return;
    }

    /** Recursive function for balancing the tree
     * 
     */

    public int InOrder(BinaryNode t, int i, Integer[] arr) {
        if (t == null) return i;
        i = InOrder(t.left, i, arr);
        arr[i] = t.element;
        i++;
        i = InOrder(t.right, i, arr);
        return i;
    }
    /**
     * In a BST, keep only nodes between range
     *
     * @param a lowest value
     * @param b highest value
     */
    public void keepRange(Integer a, Integer b) {
        balanceTree();
        keepRange(root, null, a, b, ' ');
    }

    /** Helper recursive function for keeping range
     * 
     * @param t node
     * @param parent parent of node
     * @param a lowest value
     * @param b highest value
     * @param direction char of left or right
     */
    public void keepRange(BinaryNode t, BinaryNode parent, Integer a, Integer b, char direction) {
        if (t == null) return;
        // preorder traversal
        if (t.element.compareTo(a) < 0) {
            if (direction == 'l') {
                parent.left = t.right;
            } else if (direction == 'r') {
                parent.right = t.right;
            }
        } else if (t.element.compareTo(b) > 0) {
            if (direction == 'l') {
                parent.left = t.left;
            } else if (direction == 'r') {
                parent.right = t.left;
            }
        }

        keepRange(t.left, t, a, b, 'l');
        keepRange(t.right, t, a, b, 'r');

        return;
    }

    // Basic node stored in unbalanced binary  trees
    public static class BinaryNode {
        Integer element;            // The data in the node
        BinaryNode left;   // Left child
        BinaryNode right;  // Right child

        // Constructors
        BinaryNode(Integer theElement) {
            this(theElement, null, null);
        }

        BinaryNode(Integer theElement, BinaryNode lt, BinaryNode rt) {
            element = theElement;
            left = lt;
            right = rt;
        }

        // toString for BinaryNode
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(element);
            return sb.toString();
        }
    }
}
