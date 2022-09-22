package assign07;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/****************************************************************************
 * Represents Binary Tree made of Binary Nodes that contain given elements
 * 
 * @author Samuel Langlois, Noah Garff
 * @version October 28, 2021
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet<T> {

	private BinaryNode<T> root;
	private int size, leftHeight, rightHeight;

	/************************************************************
	 * 
	 * This BST Constructor builds a null BST
	 * 
	 * */
	public BinarySearchTree() {
		this.root = null;
		this.size = 0;
	}
	
	/************************************************************
	 * 
	 * This BST Constructor builds a BST from a Given Element
	 * 
	 * @param element - Given element
	 * 
	 * */
	public BinarySearchTree(T element) {
		this.root = new BinaryNode<T>(element);
		this.size = 1;
	}
	
	/************************************************************
	 * 
	 * This BST Constructor builds a BST from a Given list 
	 * of Elements
	 * 
	 * @param listedElements - 
	 * 
	 * */
	public BinarySearchTree(Collection<? extends T> listedElements ) {
		this.root = null;
		this.size = 0;
		this.addAll(listedElements);
	}

	@Override
	public boolean add(T element) {
		return add(element, this.root);
	}

	/****************************************************
	 * 
	 * This the recursive part of the add method that searches through one Binary
	 * node at a time to add a new Node on the left or right.
	 * 
	 * @return True if a BinaryNode was added to the currentNodes child
	 * 
	 */
	private boolean add(T element, BinaryNode<T> currentNode) {
		BinaryNode<T> newBinaryNode;
		if (this.root == null) {
			this.root = new BinaryNode<T>(element);
			this.size++;
			return true;
		}

		int currentLeftHeight = 0, currentRightHeight = 0;
		if(currentNode.hasLeftChild())
			currentLeftHeight = currentNode.getLeftChild().minHeight()+1;
		if(currentNode.hasRightChild())
			currentRightHeight = currentNode.getRightChild().minHeight()+1;


		if (currentNode.getElement().compareTo(element) > 0) {
			if (currentNode.getLeftChild() != null)
				add(element, currentNode.getLeftChild());
			else {
				newBinaryNode = new BinaryNode<T>(element, null, null, currentNode);
				currentNode.setLeftChild(newBinaryNode);
				this.size++;
				balanceSelf();
				return true;
			}

		} 
		else if (currentNode.getElement().compareTo(element) < 0) {
			if (currentNode.getRightChild() != null)
				add(element, currentNode.getRightChild());

			else {
				newBinaryNode = new BinaryNode<T>(element, null, null, currentNode);
				currentNode.setRightChild(newBinaryNode);
				this.size++;
				balanceSelf();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> listedElements) {
		boolean ifAddedAnything = false;

		for (T item : listedElements) {
			if (add(item))
				ifAddedAnything = true;
		}
		return ifAddedAnything;
	}
	
	@Override
	public boolean remove(T element) {
		return remove(element, this.root);

	}

	/*************************************************
	 * 
	 * This the recursive part of the Remove method
	 * 
	 * @return True if Element is removed
	 * 
	 */
	private boolean remove(T element, BinaryNode<T> currentNode) {
		boolean wasRemoved = false;
		if (currentNode == null)
			return false;
		T currentElement = currentNode.getElement();
		
		if (element.compareTo(currentElement) < 0)
			wasRemoved = remove(element, currentNode.getLeftChild());
		else if (element.compareTo(currentElement) > 0)
			wasRemoved = remove(element, currentNode.getRightChild());
		else {

			// this needed for self balancing
			int currentHeight = currentNode.minHeight();
			// Both Children
			if (currentNode.getLeftChild() != null && currentNode.getRightChild() != null) {
				currentNode.setElement(currentNode.getLeftChild().getRightmostNode().getElement());
				wasRemoved = remove(currentNode.getLeftChild().getRightmostNode().getElement(), currentNode.getLeftChild());
			}
			// Has Left Child
			else if (currentNode.getLeftChild() != null) {
				if (currentNode.getParent() != null)
					if (currentNode.getParent().getLeftChild().getElement().compareTo(currentNode.getElement()) == 0) {

						currentNode.getParent().setLeftChild(currentNode.getLeftChild());
						currentNode.getLeftChild().setParent(currentNode.getParent());
						this.size--;
						balanceSelf();
						return true;
					} 
					else {
						currentNode.getParent().setRightChild(currentNode.getLeftChild());
						currentNode.getLeftChild().setParent(currentNode.getParent());
						this.size--;
						balanceSelf();
						return true;
					}
				else {

					this.root = currentNode.getLeftChild();
					this.root.setParent(null);
					this.size--;
					balanceSelf();
					return true;
				}
			}
			// Has Right child
			else if (currentNode.getRightChild() != null) {
				if (currentNode.getParent() != null) {
					if (currentNode.getParent().getLeftChild().getElement().compareTo(currentNode.getElement()) == 0) {
						currentNode.getParent().setLeftChild(currentNode.getRightChild());
						currentNode.getRightChild().setParent(currentNode.getParent());
						this.size--;
						balanceSelf();
						return true;
					} 
					else {
						currentNode.getParent().setRightChild(currentNode.getRightChild());
						currentNode.getRightChild().setParent(currentNode.getParent());
						this.size--;
						balanceSelf();
						return true;
					}
				}
				else {
					this.root = currentNode.getRightChild();
					this.root.setParent(null);
					this.size--;
					balanceSelf();
					return true;
				}
			}
			// Has no Children
			else {
				if (currentNode.getParent() != null)
					if (currentNode.getParent().getLeftChild() != null
					&&  currentNode.getParent().getLeftChild().getElement().compareTo(currentNode.getElement()) == 0) {
						
						currentNode.getParent().setLeftChild(null);
						this.size--;
						balanceSelf();
						return true;
					}

					else {
						currentNode.getParent().setRightChild(null);
						this.size--;
						balanceSelf();
						return true;
					}
				else {
					this.root = null;
					this.size--;
					return true;
				}
			}
		}
		//Height check then balanceSelf() if neeeded
		
		
		return wasRemoved;
	}

	@Override
	public boolean removeAll(Collection<? extends T> listedElements) {
		boolean wasRemoved = false;
		for (T item : listedElements) {
			if (remove(item) != false)
				wasRemoved = true;

		}
		return wasRemoved;
	}
	
	private void balanceSelf() {
		balanceSelf(this.root);
	}
	//Recursive part
	private void balanceSelf(BinaryNode<T> currentNode) {
		int leftHeight = 0, 
			rightHeight = 0,
			maxHeight = currentNode.maxHeight(),
			minHeight = currentNode.minHeight();
		
		if(currentNode.getLeftChild() != null)
			leftHeight = currentNode.getLeftChild().minHeight();
		if(currentNode.getRightChild() != null)
			rightHeight = currentNode.getRightChild().minHeight();

		BinaryNode<T> successorNode;

		
//		if (!(leftHeight > rightHeight)&&!(leftHeight < rightHeight)) {
//			if(leftHeight>0)
//				balanceSelf(currentNode.getLeftChild());
//			if(rightHeight>0)
//				balanceSelf(currentNode.getRightChild());
//		}
		if(leftHeight>0 && rightHeight>0) {
			balanceSelf(currentNode.getLeftChild());
			balanceSelf(currentNode.getRightChild());
		}
			balanceSelf(currentNode);

		if (leftHeight > rightHeight) {

			successorNode = currentNode.getLeftChild().getRightmostNode();

			if(successorNode.getParent().getElement().equals(currentNode.getElement())) {
				currentNode.setElement(successorNode.getElement());
				currentNode.setLeftChild(successorNode.getLeftChild());
				currentNode.getLeftChild().setParent(currentNode);
				currentNode.setRightChild(new BinaryNode<T>(successorNode.getElement(), null, null, currentNode));
			}
			else {
				currentNode.setRightChild(new BinaryNode<T>(currentNode.getElement()));
				currentNode.setElement(successorNode.getElement());
				successorNode.getParent().setRightChild(null);
			}

		}
		else if (leftHeight < rightHeight) {

			successorNode = currentNode.getRightChild().getLeftmostNode();

			if(successorNode.getParent().getElement().equals(currentNode.getElement())) {
				currentNode.setElement(successorNode.getElement());
				currentNode.setRightChild(successorNode.getRightChild());
				currentNode.getRightChild().setParent(currentNode);
				currentNode.setLeftChild(new BinaryNode<T>(successorNode.getElement(), null, null, currentNode));
			}
			else {
				currentNode.setLeftChild(new BinaryNode<T>(currentNode.getElement()));
				currentNode.setElement(successorNode.getElement());
				successorNode.getParent().setLeftChild(null);
			}
		}
	}

	@Override
	public void clear() {
		this.root = null;
		this.size = 0;
	}

	@Override
	public boolean contains(T element) {
		boolean contains = contains(element, this.root);
		return contains;
	}

	/****************************************************
	 * 
	 * This the recursive part of the contains method
	 * 
	 * @return True if a BinaryNode references element
	 * 
	 */
	private boolean contains(T element, BinaryNode<T> currentNode) {
		if (this.root == null) {
			return false;
		}
		boolean doesContain = false;

		if (currentNode.getElement().compareTo(element) > 0) {
			if (currentNode.getLeftChild() != null)
				doesContain = contains(element, currentNode.getLeftChild());
			else
				return false;
		} else if (currentNode.getElement().compareTo(element) < 0) {
			if (currentNode.getRightChild() != null)
				doesContain = contains(element, currentNode.getRightChild());
			else
				return false;
		} else
			return true;
		return doesContain;
	}

	@Override
	public boolean containsAll(Collection<? extends T> listedElements) {
		boolean doesContain = true;
		for (T item : listedElements) {
			if (!contains(item))
				doesContain = false;
		}
		return doesContain;
	}

	@Override
	public T first() throws NoSuchElementException {
		if (this.root == null)
			throw new NoSuchElementException();
		return this.root.getLeftmostNode().getElement();
	}

	@Override
	public boolean isEmpty() {
		return this.root == null && size() == 0;
	}

	@Override
	public T last() throws NoSuchElementException {
		if (this.root == null)
			throw new NoSuchElementException();
		return this.root.getRightmostNode().getElement();
	}
	@Override
	public int size() {
		return this.size;
	}

	@Override
	public ArrayList<T> toArrayList() {
		ArrayList<T> list = new ArrayList<T>();
		addToArray(this.root, list);

		return list;
	}

	/****************************************************
	 * 
	 * This is the recursive method of the toArrayList() 
	 * method, it adds the elements contained in each BinaryNode 
	 * connected to the root Node.
	 * 
	 * @param currentNode - Current Node being added
	 * @param currentList - The List of Currently Added Nodes
	 * 
	 */
	private void addToArray(BinaryNode<T> currentNode, ArrayList<T> currentList) {
		if (currentNode == null) {
			return;
		}
		addToArray(currentNode.getLeftChild(), currentList);
		currentList.add(currentNode.getElement());
		addToArray(currentNode.getRightChild(), currentList);
	}

	/****************************************************************************
	 * Represents A Binary Nodes that contains elements and References left/right Children 
	 * and parent Binary Nodes
	 * 
	 * @author Samuel Langlois and Noah Garff
	 * @version October 28, 2021
	 */
	private class BinaryNode<T> {

		private T element;
		private BinaryNode<T> leftChild, rightChild, parentNode;
		private int nodeHeight;

		/************************************************************
		 * 
		 * This constructor creates a Node from a given element, and
		 * sets the parent and children for the new BinaryNode
		 * 
		 * @param element - Given element
		 * @param leftChild - Given left child
		 * @param rightChild - Given right child
		 * @param parentNode - Given parent
		 * 
		 */
		public BinaryNode(T element, BinaryNode<T> leftChild, BinaryNode<T> rightChild, BinaryNode<T> parentNode) {
			this.element = element;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
			this.parentNode = parentNode;
//			this.nodeHeight = currentHeight;
		}

		public boolean hasBothChildren() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean hasOneChild() {
			if((this.leftChild != null && this.rightChild == null) || (this.leftChild == null && this.rightChild != null))
				return true;
			return false;
		}

		/*********************************************************
		 * 
		 * This method Returns the height of the node root 
		 * node is at 0 like an array index
		 * 
		 * @return nodeHeight - The height of the node
		 * 
		 * */
		public int getHeight() {
			return this.nodeHeight;
		}

		/***********************************************************
		 * 
		 * This constructor creates a Root Node from a given element
		 * 
		 * @param element - Given element
		 * 
		 */
		public BinaryNode(T element) {
			this(element, null, null, null);
		}

		/**
		 * @return the node data
		 */
		public T getElement() {
			return this.element;
		}

		/**
		 * @param data - the node data to be set
		 */
		public void setElement(T element) {
			this.element = element;
		}

		/**
		 * @return reference to the parent node
		 */
		public BinaryNode<T> getParent() {
			return this.parentNode;
		}

		/**
		 * @return reference to the left child node
		 */
		public BinaryNode<T> getLeftChild() {
			return this.leftChild;
		}

		/**
		 * @return true if has a left child node
		 */
		public boolean hasLeftChild() {

			return this.leftChild != null;
		}

		/**
		 * @return true if has a right child node
		 */
		public boolean hasRightChild() {

			return this.rightChild != null;
		}

		/**
		 * @return true if has a right child node
		 */
		public boolean hasParent() {

			return this.parentNode != null;
		}

		/**
		 * @param parent - reference of the parent node to be set
		 */
		public void setParent(BinaryNode<T> parent) {
			this.parentNode = parent;
		}

		/**
		 * @param leftChild - reference of the left child node to be set
		 */
		public void setLeftChild(BinaryNode<T> leftChild) {
			this.leftChild = leftChild;
		}

		/**
		 * @return reference to the right child node
		 */
		public BinaryNode<T> getRightChild() {
			return this.rightChild;
		}

		/**
		 * @param rightChild - reference of the right child node to be set
		 */
		public void setRightChild(BinaryNode<T> rightChild) {
			this.rightChild = rightChild;
		}

		/**
		 * @return reference to the leftmost node in the binary tree rooted at this node
		 */
		public BinaryNode<T> getLeftmostNode() {
			if (this.leftChild != null)
				return this.leftChild.getLeftmostNode();
			return this;
		}

		/**
		 * @return reference to the rightmost node in the binary tree rooted at this
		 *         node
		 */
		public BinaryNode<T> getRightmostNode() {
			if (rightChild != null)
				return this.rightChild.getRightmostNode();
			return this;
		}

		/**
		 * @return the minimum height of the binary tree rooted at this node
		 * 
		 *         The height of a tree is the length of the longest path to a leaf
		 *         node. Consider a tree with a single node to have a height of zero.
		 */
		public int minHeight() {
			if (leftChild == null && rightChild == null) {
				return 0;
			}
			if (leftChild == null && rightChild != null) {
				return 1 + rightChild.minHeight();
			}
			if (rightChild == null && leftChild != null) {
				return 1 + leftChild.minHeight();
			}
			if (leftChild != null && rightChild != null) {
				return 1 + Math.min(leftChild.minHeight(), rightChild.minHeight());

			}
			return 0;
		}
		/**
		 * @return the maximum height of the binary tree rooted at this node
		 * 
		 *         The height of a tree is the length of the longest path to a leaf
		 *         node. Consider a tree with a single node to have a height of zero.
		 */
		public int maxHeight() {
			if (leftChild == null && rightChild == null) {
				return 0;
			}
			if (leftChild == null && rightChild != null) {
				return 1 + rightChild.maxHeight();
			}
			if (rightChild == null && leftChild != null) {
				return 1 + leftChild.maxHeight();
			}
			if (leftChild != null && rightChild != null) {
				return 1 + Math.max(leftChild.maxHeight(), rightChild.maxHeight());

			}
			return 0;
		}
		

	}

	/**
	 * Writes the tree to a dot file
	 * 
	 * @param filename - the file to write to
	 */
	public void writeDot(String filename) {
		try {
			// PrintWriter(FileWriter) will write output to a file
			PrintWriter output = new PrintWriter(new FileWriter(filename));

			// Set up the dot graph and properties
			output.println("digraph BST {");
			output.println("node [shape=record]");

			if (this.root != null)
				writeDotRecursive(this.root, output);
			// Close the graph
			output.println("}");
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recursive helper for writing this tree to a dot file
	 * 
	 * @param currentNode      - the current subtree
	 * @param output - a PrintWriter with an open output file
	 * @throws Exception
	 */
	private void writeDotRecursive(BinaryNode<T> currentNode, PrintWriter output) throws Exception {
		output.println(currentNode.element + "[label=\"<L> |<D> " + currentNode.element + "|<R> \"]");
		if (currentNode.leftChild != null) {
			// write the left subtree
			writeDotRecursive(currentNode.leftChild, output);

			// then make a link between n and the left subtree
			output.println(currentNode.element + ":L -> " + currentNode.leftChild.element + ":D");
		}
		if (currentNode.rightChild != null) {
			// write the left subtree
			writeDotRecursive(currentNode.rightChild, output);

			// then make a link between n and the right subtree
			output.println(currentNode.element + ":R -> " + currentNode.rightChild.element + ":D");
		}
	}
}