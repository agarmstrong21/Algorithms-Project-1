
public class avltree {
	private treeNode Root;             // first node of tree
	
	public avltree() { Root = Root.nullnode; }   // constructor; no nodes in tree yet
	public void clear() { Root = Root.nullnode; }
	  
	public treeNode getRoot() { return Root; }
	
	public boolean isEmpty() { return Root.isEmpty(); }
	
	public treeNode find(int key) {
		if (Root.isEmpty()) return Root;
		return Root.find(key);
	}

	public int height(treeNode x) { // return height of tree Rooted at x
        if (Root.isEmpty()) return -1;
        return Root.height;
    }

	private treeNode insert(treeNode newNode, treeNode t) {

	   treeNode nt = t;  // // t is the root of subtree 

	   if (newNode.iData < t.iData) {
	     if (t.left.isEmpty()) {
	         t.left = newNode;  //attach new node as leaf
	     } else {
	         t.left = insert(newNode, t.left);   // branch left

	         if ((t.left.height - t.right.height) > 1) {
	            if (newNode.iData < t.left.iData) {
	              nt = t.rotateToRight();
	            } else {
	              nt = t.doubleRotateToRight();
	              (nt.left).adjHeight();
	            } //else
	         } //if
	      } // else
	    } else { // branch right
	        if (t.right.isEmpty()) {
	           t.right = newNode;   // attach new node as leaf
	        } else {
	           t.right = insert(newNode, t.right);  // branch right

	           if ((t.right.height - t.left.height) > 1) {
	              if (newNode.iData >= t.right.iData) {  
	                  nt = t.rotateToLeft();
	              } else {
	                  nt = t.doubleRotateToLeft();
		          (nt.right).adjHeight();
	              } //else
	           } //if
	        } //else
	    } // else if

	    // Update the height, just in case
	    t.adjHeight();
	    if (t != nt) nt.adjHeight();
	 
	    return nt; // return new Root of this subtree
	}  // end insert

    public void insert(int id) { // call recursive insert
       treeNode newNode = new treeNode(id);    // make new node

       if (Root.isEmpty()) Root = newNode;
       else Root = insert(newNode, Root);
    }  // end insert()

    public void remove(int id) {
        Root = remove(id, Root);
    }

    private treeNode remove(int x, treeNode t) {
        if( t.isEmpty() ) return t;   // Item not found; do nothing
            
        if (x < t.iData) {
            t.left = remove(x, t.left);
        } else if( x > t.iData) {
            t.right = remove(x, t.right);
        } else if( !t.left.isEmpty() && !t.right .isEmpty() ) { // Two children
            t.iData = (t.right).findMin().iData;
            t.right = remove(t.iData, t.right);
        } else {
        	t = ( t.right.isEmpty() ) ? t.left : t.right;
        	return t;
        }
    	
        return balance(t);
    }
    
    private treeNode balance( treeNode t ) {
    	// Assume t is either balanced or within one of being balanced;
    	// Assume t's children are balanced.
    	treeNode nt = t;
        if (t.isEmpty()) return t;
        
        int h1 = t.left.height;
        int h2 = t.right.height;
        if (h1 - h2 > 1) {
            if (t.left.left.height >= t.left.right.height) {
                nt = t.rotateToRight();
            } else {
                nt = t.doubleRotateToRight();
                (nt.left).adjHeight();
            }
        } else if (h2 - h1 > 1) {
            if (t.right.right.height >= t.right.left.height) {
                nt = t.rotateToLeft();
            } else {
                nt = t.doubleRotateToLeft();
                (nt.right).adjHeight();
            }
        }
        
	    // Update the height, just in case
	    t.adjHeight();
	    if (t != nt) nt.adjHeight();
       
        return nt;
    }

}
