package com.voyagessncf.xspeedit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PackagingMachine2 {

	private int boxCapacity;

	/**
	 * Creates a PackagingMaching that will produce packages according to the
	 * capacity
	 * 
	 * @param capacity
	 */
	public PackagingMachine2(int capacity) {
		this.boxCapacity = capacity;
	}

	/**
	 * Takes as input the concatenated list of product size, and outputs a
	 * proposal of packing
	 * 
	 * @param input
	 * @return
	 * @throws InvalidListOfArticlesException
	 */
	public String pack(String input) throws InvalidListOfArticlesException {
		if (!isValid(input)) {
			throw new InvalidListOfArticlesException();
		}
		//convert the string to a map and sort it by descending size (biggest sizes first)
		List<String> inputList = new ArrayList<>();
		inputList.addAll(Arrays.asList(input.split("")));
		Collections.sort(inputList, Collections.reverseOrder());
		return processPackaging(inputList);
	}

	/**
	 * Recursively creates packages in a simple way : 
	 * it creates a pack with the first product in the list 
	 * - while there is a complementary product (the biggest possible each time), it adds to the pack 
	 * - when there is no more complementary product, it returns a string composed of
	 * the pack plus the result of the same algorithm done for the remaining
	 * products.
	 * - if the inputList at the end is empty, then return only the pack, we finished the recursion
	 * 
	 * @param inputList
	 * @return
	 */
	private String processPackaging(List<String> inputList) {
		// create a pack with the first element (biggest element)
		String pack = inputList.get(0);
		// remove this to the list
		inputList.remove(0);
		// get the biggest complementary
		String complementary = getComplementary(pack, inputList);
		// if it exists
		while (complementary != null) {
			// add the complementary to the pack
			pack += complementary;
			// and remove it from the list
			inputList.remove(inputList.indexOf(complementary));
			// and get the biggest complementary again
			complementary = getComplementary(pack, inputList);
		}
		// if the list is empty, we finished the boxing
		if (inputList.isEmpty()) {
			return pack;
		}
		// when finished with this pack, return it and the rest of packs
		return pack + "/" + processPackaging(inputList);
	}

	/**
	 * Compute a list of possible complementary products and return the biggest
	 * one a product is complementary if when added to the pack, the size of the
	 * pack is less or equals to the max capacity
	 * 
	 * @param pack
	 * @param inputList
	 * @return
	 */
	protected String getComplementary(String pack, List<String> inputList) {
		List<String> possibleComplementary = new ArrayList<>();
		for (String product : inputList) {
			if (sum(pack + product) <= boxCapacity) {
				possibleComplementary.add(product);
			}
		}
		if (possibleComplementary.isEmpty()) {
			return null;
		}
		Collections.sort(possibleComplementary, Collections.reverseOrder());
		return possibleComplementary.get(0);
	}

	/**
	 * sum all the digits contained in the string
	 * 
	 * @param string
	 * @return
	 */
	protected int sum(String products) {
		int sum = 0;
		for (int index = 0; index < products.length(); index++) {
			sum += Integer.valueOf(String.valueOf(products.charAt(index)));
		}
		return sum;
	}

	/**
	 * validate that the input only contains numbers from 1 to 9
	 * 
	 * @param input
	 * @return
	 */
	protected boolean isValid(String input) {
		return input.matches("[1-9]+");
	}

}
