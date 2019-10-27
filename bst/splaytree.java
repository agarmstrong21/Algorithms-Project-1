
public class splaytree {
    private treeNode Root;   // Root of the BST

	public splaytree() { Root = Root.nullnode; }   // constructor; no nodes in tree yet
	public void clear() { Root = Root.nullnode; }
	  
	public treeNode getRoot() { return Root; }
	
	public boolean isEmpty() { return Root.isEmpty(); }

    public int size() { return Root.size(); }
    
    /***************************************************************************
     *  Splay tree find
     ***************************************************************************/
    public treeNode find(int key) {
		if (Root.isEmpty()) return Root;
		Root = splay(Root, key);
		if (Root.iData == key) return Root;
		return Root.nullnode;
	}

   /***************************************************************************
    *  Splay tree insertion.
    ***************************************************************************/
    public void insert(int key) {
        // splay key to Root
        treeNode n = new treeNode(key);
        if (Root.isEmpty()) { Root = n; return;}
        
        Root = splay(Root, key);
        
        // Insert new treeNode at Root
        if (key < Root.iData) { 
            n.left = Root.left;
            n.right = Root;
            Root.left = Root.nullnode;
        } else {
            n.left = Root;
            n.right = Root.right;
            Root.right = Root.nullnode;
        }
        Root = n;
    }
    
   /***************************************************************************
    *  Splay tree deletion.
    ***************************************************************************/
    public void remove(int key) {
        if (Root.isEmpty()) return; // empty tree
        
        Root = splay(Root, key);
        
        if (Root.iData == key) {
            if (Root.left.isEmpty()) {
                Root = Root.right;
            } else if (Root.right.isEmpty()) {
            	Root = Root.left;
            } else {
                Root.iData = Root.left.deleteMax(Root).iData;
            }
        }
        // else: it wasn't in the tree to remove
    }
    
   /***************************************************************************
    * Splay tree function.
    * **********************************************************************/
    // splay key in the tree Rooted at treeNode h. If a treeNode with that key exists,
    //   it is splayed to the Root of the tree. If it does not, the last treeNode
    //   along the search path for the key is splayed to the Root.
    private treeNode splay(treeNode t, int key) {
        if (t.isEmpty()) return t; // key not in tree, so we're done

        while (true) {
        	// t.printTree( ); System.out.println(" splay " + key);
        	if (key < t.iData) {
        		if (t.left.isEmpty()) { return t; } // key not in tree, so we're done
            
        		if (key < t.left.iData)
        			t = t.rotateToRight(); 
        		//	t.left.left = splay(t.left.left, key);	
            
        		else if (key > t.left.iData) {
        			// t.left.right = splay(t.left.right, key);
        			if (!t.left.right.isEmpty())
        				t.left = t.left.rotateToLeft();
        		}
            
        		if (t.left.isEmpty()) return t;
        		else { t = t.rotateToRight(); }
        	}

        	else if (key > t.iData) {             
        		if (t.right.isEmpty()) { return t; } // key not in tree, so we're done

        		if (key > t.right.iData) {
        			// t.right.right = splay(t.right.right, key);
        			t = t.rotateToLeft();
        		} else if (key < t.right.iData) {
        			// t.right.left  = splay(t.right.left, key);
        			if (!t.right.left.isEmpty())
        				t.right = t.right.rotateToRight();
        			else return t;
        		}
            
        		if (t.right.isEmpty()) return t;
        		else { t = t.rotateToLeft(); }
        	}
        	
        	else return t; // t.iData == key
        }
    }
    
    // recursive version
    private treeNode splay0(treeNode t, int key) {
        if (t.isEmpty()) return t; // key not in tree, so we're done

        if (key < t.iData) {
            if (t.left.isEmpty()) { return t; } // key not in tree, so we're done
            
            if (key < t.left.iData) {
                t.left.left = splay0(t.left.left, key);
                t = t.rotateToRight();
            }
            else if (key > t.left.iData) {
                t.left.right = splay0(t.left.right, key);
                if (!t.left.right.isEmpty())
                    t.left = t.left.rotateToLeft();
            }
            
            if (t.left.isEmpty()) return t;
            else                  return t.rotateToRight();
        }

        else if (key > t.iData) {             
            if (t.right.isEmpty()) { return t; } // key not in tree, so we're done

            if (key < t.right.iData) {
                t.right.left  = splay0(t.right.left, key);
                if (!t.right.left.isEmpty())
                    t.right = t.right.rotateToRight();
                else return t;
            }
            else if (key > t.right.iData) {
                t.right.right = splay0(t.right.right, key);
                t = t.rotateToLeft();
            }
            
            if (t.right.isEmpty()) return t;
            else                   return t.rotateToLeft();
        }

        else return t; // t.iData == key
    }
    
}
