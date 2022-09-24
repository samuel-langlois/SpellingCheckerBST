package assign07;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*****
 * This tests the functionality of the BST
 * 
 * @author Samuel Langlois and Noah Graff
 * 
 */

class BinarySearchTreeTest {
	private BinarySearchTree<Integer> bst, nullBTS, bigBST, balancedBST;
	BinarySearchTree<String> nullBSTstring;

	@BeforeEach
	public void setup() {
		bst = new BinarySearchTree<Integer>();
		balancedBST = new BinarySearchTree<Integer>();
		nullBTS = new BinarySearchTree<Integer>();
		nullBSTstring = new BinarySearchTree<String>();
		bigBST = new BinarySearchTree<Integer>();
		ArrayList<Integer> setUpArray = new ArrayList<Integer>();
		Integer[] setUpIntegers = { 50, 5, 12, 2, 6, 100, 123, 99, 87, 89, 86 };
		Integer[] balancedBSTsetUp = { 50, 40, 30, 45, 25, 39, 43, 47, 60, 70, 80, 55, 53, 58, 69 };

		bst.add(7);
		bst.add(11);
		bst.add(2);
		bst.add(5);
		bst.add(3);
		bst.add(10);
		bst.add(1);
		bst.add(6);
		bst.add(14);
		bst.add(9);
		bst.add(12);
		bst.add(4);
		bst.add(13);
		bst.add(8);

		for (int i = 0; i < balancedBSTsetUp.length; i++)
			setUpArray.add(balancedBSTsetUp[i]);
		balancedBST.addAll(setUpArray);

		setUpArray.clear();
		for (int i = 0; i < setUpIntegers.length; i++)
			setUpArray.add(setUpIntegers[i]);
		bigBST.addAll(setUpArray);

	}

	@Test
	void testRemoveAllValuesBST() {
		ArrayList<Integer> list = bst.toArrayList();
		System.out.println("\n ---Test1");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

		bst.writeDot("src/assign07/BST.dot");

		assertFalse(nullBTS.remove(3));

		for (int i = 0; i < list.size(); i++) {
			boolean wasRemove = bst.remove(i + 1);
			assertTrue(wasRemove);
		}

		list = bst.toArrayList();
		System.out.println("\n ---Test2");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

	}

	@Test
	void test2RemoveAllBigBST() {
		ArrayList<Integer> removeArray = new ArrayList<Integer>();
		Integer[] removeIntegers = { 50, 5, 12, 2, 6, 100, 123, 99, 87, 89, 86 };
		for (int i = 0; i < removeIntegers.length; i++) {
			removeArray.add(removeIntegers[i]);
		}

		bigBST.writeDot("src/assign07/bigBST.dot");

		System.out.println("\n ---Test3");

		// assertTrue(bigBST.removeAll(removeArray));
		for (int i = 0; i < removeIntegers.length; i++) {
			assertTrue(bigBST.remove(removeIntegers[i]));
		}
		ArrayList<Integer> list = bigBST.toArrayList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	@Test
	void test3RemoveAllValuesBigBST() {
		ArrayList<Integer> removeArray = new ArrayList<Integer>();
		Integer[] removeIntegers = { 11111 };
		for (int i = 0; i < removeIntegers.length; i++) {
			removeArray.add(removeIntegers[i]);
		}

//		bigBST.writeDot("src/assign07/bigBST.dot");

		System.out.println("\n ---Test4");

		assertFalse(bigBST.removeAll(removeArray));
		ArrayList<Integer> list = bigBST.toArrayList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		bigBST.writeDot("src/assign07/bigBST.dot");
	}

	@Test
	void test4Contains() {
		assertTrue(bigBST.contains(50));

		ArrayList<Integer> containsArray = new ArrayList<Integer>();
		Integer[] containsIntegers = { 50, 5, 12, 2, 6, 100, 123, 99, 87, 89, 86 };
		for (int i = 0; i < containsIntegers.length; i++) {
			containsArray.add(containsIntegers[i]);
		}

		assertTrue(bigBST.containsAll(containsArray));

	}

	@Test
	void test5BalancedBST() {
		ArrayList<Integer> containsArray = new ArrayList<Integer>();
		Integer[] containsIntegers = { 50, 40, 30, 45, 25, 39, 43, 47, 60, 70, 80, 55, 53, 58, 69 };
		for (int i = 0; i < containsIntegers.length; i++) {
			containsArray.add(containsIntegers[i]);
		}

//		assertTrue(balancedBST.containsAll(containsArray));
//		assertTrue(balancedBST.removeAll(containsArray));
//		assertFalse(balancedBST.containsAll(containsArray));
		balancedBST.remove(40);
		balancedBST.remove(60);
		assertTrue(balancedBST.contains(80));
		balancedBST.writeDot("src/assign07/balancedBST.dot");

	}

	@Test
	void test6() {
		Integer[] balancedBSTsetUp = { 50, 40, 30, 45, 25, 39, 43, 47, 60, 70, 80, 55, 53, 58, 69 };
		ArrayList<String>containsArray = new ArrayList<String>();
		for (int i = 0; i < balancedBSTsetUp.length; i++) {
			containsArray.add("Method"+balancedBSTsetUp[i]);
			nullBSTstring.add("Method" + balancedBSTsetUp[i]);
		}
		
		assertTrue(nullBSTstring.containsAll(containsArray));
		
		nullBSTstring.writeDot("src/assign07/nullBSTstring.dot");
		
	}
	@Test
	void selfBalanceTest() {
		Integer[] allInOrder = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		ArrayList<Integer> containsArray = new ArrayList<Integer>();
		for(int i = 0; i < allInOrder.length; i++) {
			nullBTS.add(allInOrder[i]);
			containsArray.add(allInOrder[i]);
		}
		nullBTS.writeDot("src/assign07/nullBST.dot");
	}
}