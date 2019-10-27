// import javax.swing.tree.TreeNode;

public class redblacktree {

	  private treeNode Root;
	  
	  public treeNode getRoot() { return Root; }
	  
	  // Constructs a new empty red-black tree.
	  public redblacktree() { Root = Root.nullnode;}
	  public void clear() { Root = Root.nullnode; }
	  
	  public boolean isEmpty() { return Root.isEmpty(); }	  

	  public int size() { return Root.size(); }
	    
	  public treeNode find(int key) {
		  if (Root.isEmpty()) return Root;
		  return Root.find(key);
	  }

	  public void checkRedBlack() {
		  if (Root.isEmpty()) return;
		  if (Root.isRed) System.out.println( "The root is red");
		  Root.bheight();
	  }
	  
	  public void insert(int id) { // call recursive insert
	       treeNode nnd = new treeNode(id);    // make new node

	       if (Root.isEmpty()) Root = nnd;
	       else if (id < Root.iData) {
	    	   if (Root.left.isEmpty())
	    		   Root.left = nnd;
	    	   else if (insert(nnd, Root.left, Root)) {
	  	    	   // red-red violation exists at Root.left and its children
			       if (Root.right.isRed) { // red uncle case:
			    	   Root.right.isRed = Root.left.isRed = false;
			       } else { // black uncle case:			    	   
			    	   Root.isRed = true;
			           if (nnd.iData < Root.left.iData) {
			               Root = Root.rotateToRight();
			           } else {
			               Root = Root.doubleRotateToRight();
			           } 			 
			       } // else
	    	   }
	       } else if (Root.right.isEmpty()) {
	    		   Root.right = nnd;
	       } else if (insert(nnd, Root.right, Root)) {
  	    	   // red-red violation exists at Root.right and its children
		       if (Root.left.isRed) { // red uncle case:
		    	   Root.right.isRed = Root.left.isRed = false;
		       } else { // black uncle case:		    	   
		    	   Root.isRed = true;
		           if (nnd.iData >= Root.right.iData) {
		               Root = Root.rotateToLeft();
		           } else {
		               Root = Root.doubleRotateToLeft();
		           } 
		       } // else
	       }
	       Root.isRed = false;
	    }  // end insert()
	  
	  /* *************************************************** *
	   *  PRIVATE METHODS                                    *
	   * *************************************************** */
	  
	  /* Inserts a node into tree and returns a boolean */
	  private boolean insert(treeNode nnd, treeNode t, treeNode par) {
		  // return true iff t is red and t has a red child

		  if (nnd.iData < t.iData) {
		      if (t.left.isEmpty()) {
		         t.left = nnd;  //attach new node as leaf
		      } else if (insert(nnd, t.left, t)) { 
		    	 // red-red violation exists at t.left and its children
		    	 if (t.right.isRed) { // red uncle case:
		    		 t.right.isRed = t.left.isRed = false;
		    		 t.isRed = true;
		    	 } else { // black uncle case:
		    		 treeNode nt;
		    		 
		             if (nnd.iData < t.left.iData) {
		                nt = t.rotateToRight();
		             } else {
		                nt = t.doubleRotateToRight();
		             } 
		    		 t.isRed = true;
		    		 nt.isRed = false;
		    		 if (nt.iData < par.iData) par.left = nt;
		    		 else par.right = nt;
		         } //if
		      }
		      
		      return (t.isRed && t.left.isRed);
		 } else { // branch right
		      if (t.right.isEmpty()) {
		           t.right = nnd;   // attach new node as leaf
			  } else if (insert(nnd, t.right, t)) { 
			       // red-red violation exists at t.right and its children
			       if (t.left.isRed) { // red uncle case:
			    		 t.right.isRed = t.left.isRed = false;
			    		 t.isRed = true;
			       } else { // black uncle case:
			    		 treeNode nt;
			    		 
			             if (nnd.iData >= t.right.iData) {
			                nt = t.rotateToLeft();
			             } else {
			                nt = t.doubleRotateToLeft();
			             } 
			    		 t.isRed = true;
			    		 nt.isRed = false;
			    		 if (nt.iData < par.iData) par.left = nt;
			    		 else par.right = nt;
			       } // else
			  } 
		      
			  return (t.isRed && t.right.isRed);
		 } // else
	  }  // end insert



	/* *************************************************** *
	 *  Remove Method                                  *
	 * *************************************************** */

	public void remove(int id){
		if (Root.isEmpty()) return;
		remove(id, Root, null, null);
	}

	private boolean remove(int x, treeNode t, treeNode parent, treeNode grandPa) {
		// Perform Standard Binary Search Tree Removal
		if (t.isEmpty() ) return false;   // Item not found; do nothing

		boolean doubleB = false;
		treeNode s;
		
		//Find the Node to remove
		if (x < t.iData) {
			doubleB = remove(x, t.left, t, parent); }
		else if( x > t.iData) {
			doubleB = remove(x, t.right, t, parent); }

		//Found node with Two Children Case
		else if( !t.left.isEmpty() && !t.right.isEmpty() ) {
			t.iData = (t.right).findMin().iData; //Left most node in right subtree
			//Remove the node we swapped into this node from the right subtree
			doubleB = remove(t.iData, t.right, t, parent); //remove(data we swapped in, right subtree)
		}
		
		// Found node with Single child case: Note this will be the case when we remove the leftmost of right subtree
		else {	
			s = ( t.right.isEmpty() ) ? t.left : t.right;
			if (parent == null) Root = s; // t is the old root
			if (parent.left == t) parent.left = s; else parent.right = s;
			if (t.isRed) return false; // no further processing
			if (s.isEmpty()) return true; // further processing is needed
			if (s.isRed) { 
				s.isRed = false; // s is not empty and changed to black
				return false; // no further processing
			}
			return true; // the current node is called double black because
			// the old t as a black node is deleted and further processing is needed.
		}
		
		if (doubleB) { // handle the problem generated from the children
			if (t.isRed || parent == null) { t.isRed = false; return false; }
			treeNode sibling = (parent.left == t)? parent.right : parent.left;
			
			// case 0: the sibling is red: convert it into cases 1 or 2.
			if (sibling.isRed) {
				sibling.isRed = false;
				parent.isRed = true;
				if (parent.left == sibling) {
					s = parent.rotateToRight(); // sibling on the left
					sibling = s.left;
				} else { 
					s = parent.rotateToLeft(); // sibling on the right
					sibling = s.right;
				}
				if (grandPa == null) { Root = s; Root.isRed = false; }
				else if (grandPa.left == parent) grandPa.left = s;
				else grandPa.right = s;
			    grandPa = s;
			}
			
			// case 1: the sibling is black and one of children of sibling is red.
			if (sibling.left.isRed) {
				if (parent.left == sibling)  
					s = parent.rotateToRight(); // left-left case
				else
					s = parent.doubleRotateToRight(); // right-left case	
				if (grandPa == null) { Root = s; Root.isRed = false; }
				else if (grandPa.left == parent) grandPa.left = s;
				else grandPa.right = s;
				return false;
			} else if (sibling.right.isRed) {
				if (parent.right == sibling)  
					s = parent.rotateToLeft(); // right-right case
				else
					s = parent.doubleRotateToLeft(); // left-right case	
				if (grandPa == null) { Root = s; Root.isRed = false; }
				else if (grandPa.left == parent) grandPa.left = s;
				else grandPa.right = s;
				return false;
			}
			
			// case 2: the sibling is black and both children of sibling are black.
			else if (parent.isRed) {
				parent.isRed = false;
				sibling.isRed = true;
				return false;
			} else { // parent is black
				sibling.isRed = true;
				return true;
			}
		}
		return false;
	}

}
