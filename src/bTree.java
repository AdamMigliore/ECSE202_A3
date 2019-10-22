
/**
 * Implements a B-Tree class using a NON-RECURSIVE algorithm.
 * 
 * @author ferrie
 *
 */

public class bTree {

	private static final double DELTASIZE = 0.1;

	// Instance variables

	private bNode root = null;
	private boolean running = true;
	private double lastSize = 0;
	private double xPos = 0, yPos = 0;

	/**
	 * addNode method - adds a new node by descending to the leaf node using a while
	 * loop in place of recursion. Ugly, yet easy to understand.
	 */

	public void addNode(aBall iBall) {

		bNode current;

// Empty tree

		if (root == null) {
			root = makeNode(iBall);
		}

// If not empty, descend to the leaf node according to
// the input data.  

		else {
			current = root;
			while (true) {
				if (iBall.getBSize() < current.iBall.getBSize()) {

// New data < data at node, branch left

					if (current.left == null) { // leaf node
						current.left = makeNode(iBall); // attach new node here
						break;
					} else { // otherwise
						current = current.left; // keep traversing
					}
				} else {
// New data >= data at node, branch right

					if (current.right == null) { // leaf node
						current.right = makeNode(iBall); // attach
						break;
					} else { // otherwise
						current = current.right; // keep traversing
					}
				}
			}
		}

	}

	/**
	 * makeNode
	 * 
	 * Creates a single instance of a bNode
	 * 
	 * @param aBall iBall ball to be added
	 * @return bNode node Node created
	 */

	bNode makeNode(aBall iBall) {
		bNode node = new bNode(); // create new object
		node.iBall = iBall; // initialize data field
		node.left = null; // set both successors
		node.right = null; // to null
		return node; // return handle to new object
	}

	/**
	 * inorder method - inorder traversal via call to recursive method to check if a
	 * ball is running in the simulation
	 */

	public void checkRunning() { // hides recursion from user
		traverse_inorder_isRunning(root);
	}

	/**
	 * traverse_inorder method - recursively traverses tree in order
	 * (LEFT-Root-RIGHT) and checks if that node is currently running a simulation
	 */

	private void traverse_inorder_isRunning(bNode root) {
		running = false;

		if (root.left != null)
			traverse_inorder_isRunning(root.left);

		if (root.iBall.isRunning())
			running = true;

		if (root.right != null)
			traverse_inorder_isRunning(root.right);
	}

	public void stackBalls(bSim link) {
		traverse_inorder_stack(root, link);
	}

	private void traverse_inorder_stack(bNode root, bSim link) {
		if (root.left != null)
			traverse_inorder_stack(root.left, link);

		if (root.iBall.getBSize() - lastSize > DELTASIZE) {

			xPos += gUtil.meterToPixels(link.getScale(), lastSize * 2);
			yPos = link.getHeight() - gUtil.meterToPixels(link.getScale(), root.iBall.getBSize() * 2);
			root.iBall.moveTo(xPos, yPos);

		} else {

			yPos -= gUtil.meterToPixels(link.getScale(), root.iBall.getBSize() * 2);
			root.iBall.moveTo(xPos, yPos);

		}

		lastSize = root.iBall.getBSize();

		if (root.right != null)
			traverse_inorder_stack(root.right, link);
	}

	/**
	 * @return running : if the complete simulation is currently running
	 */
	public boolean isRunning() {
		return running;
	}
}

/**
 * A simple bNode class for use by bTree. The "payload" can be modified
 * accordingly to support any object type.
 * 
 * @author ferrie
 *
 */

class bNode {
	aBall iBall;
	bNode left;
	bNode right;
}
