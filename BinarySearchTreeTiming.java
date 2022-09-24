package assign07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

/**
 * Tests the Runtime of the BST
 * 
 * @author Samuel Langlois and Noah Garff
 * 
 **/
public class BinarySearchTreeTiming {
	private static Random rand;
	private static TreeSet<Integer> balancedBST;
	private static BinarySearchTree<Integer> unbalancedBST, sortedBST;
	private static ArrayList<Integer> randNumList, addedInOrderList;

	public static void main(String[] args) {
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());

		// Do 10000 lookups and use the average running time.
		int timesToLoop = 10;
		for (int n = 1000; n <= 20000; n += 1000) {

			long startTime, midpointTime, stopTime;
			randNumList = randomNumber(n);
			addedInOrderList = addedInOrder(n);
//			balancedBST = new TreeSet<Integer>(randNumList);
//			balancedBST = new TreeSet<Integer>(addedInOrderList);
//			unbalancedBST = new BinarySearchTree<Integer>(randNumList);
//			sortedBST = new BinarySearchTree<Integer>(addedInOrderList);
//			unbalancedBST = new BinarySearchTree<Integer>(addedInOrderList);
			ArrayList<Integer> tempBoy = new ArrayList<Integer>();
			Integer[] removeNTime = new Integer[n];
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) {

			}
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) {

			}
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) {

			}
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) {

			}

			startTime = System.nanoTime();

			for (int i = 0; i < timesToLoop; i++) {
				unbalancedBST = new BinarySearchTree<Integer>(randNumList);
			}

			midpointTime = System.nanoTime();

			for (int i = 0; i < timesToLoop; i++) {

				
				unbalancedBST.containsAll(randNumList);

			}

			stopTime = System.nanoTime();

			double firstTime = (midpointTime - startTime) / (double) timesToLoop;
			double secondTime = (stopTime - midpointTime) / (double) timesToLoop;
//			double firstTotalTime = firstTime / n, secondTotalTime = secondTime/n;
			/*
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * */
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) {

			}
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) {

			}
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) {

			}
			startTime = System.nanoTime();
			while (System.nanoTime() - startTime < 1000000000) {

			}

			startTime = System.nanoTime();

			for (int i = 0; i < timesToLoop; i++) {
				balancedBST = new TreeSet<Integer>(randNumList);
			}

			midpointTime = System.nanoTime();

			for (int i = 0; i < timesToLoop; i++) {

				
				balancedBST.containsAll(randNumList);

			}

			stopTime = System.nanoTime();

			double balancedAddTime = (midpointTime - startTime) / (double) timesToLoop;
			double balancedContainsTime = (stopTime - midpointTime) / (double) timesToLoop;
//			double balancedAddTotalTime = balancedAddTime / n, balancedContainsTotalTime = balancedContainsTime/n;
			

			System.out.println(n + "\t   " + balancedAddTime + "\t   " + n + "\t   " + balancedContainsTime + "\t   " +
							   n + "\t   " + firstTime + "\t   " + n + "\t   " + secondTime);
//			System.out.println(n + "\t   " + totalTime);
		}
		// / (n * Math.log(n))
	}

	public static ArrayList<Integer> randomNumber(int maxVal) {
		BinarySearchTree<Integer> tempBST = new BinarySearchTree<Integer>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < maxVal; i++) {
			Integer randInt = rand.nextInt(maxVal + 1);
			while (tempBST.contains(randInt))
				randInt = rand.nextInt(maxVal + 1);// random value between 1 & maxVal(n)
			tempBST.add(randInt);
			list.add(randInt);
		}

		return list;
	}

	public static ArrayList<Integer> addedInOrder(int maxVal) {
		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int i = 0; i < maxVal; i++) {
			list.add(i + 1);
		}

		return list;
	}


}