package com.voyagessncf.xspeedit;

import java.util.HashSet;
import java.util.Set;

public class PackagingMachineBruteForce {

	private int boxCapacity;

	/**
	 * Creates a PackagingMaching that will produce packages according to the
	 * capacity
	 * 
	 * @param capacity
	 */
	public PackagingMachineBruteForce(int capacity) {
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
		return processPackaging(input, "", packOneProductInEachPack(input));
	}

	private String processPackaging(String productsLeftToPack, String currentPackagingSolution,
			String previousBestPackagingSolution) {
		String[] splitCurrentPackagingSolution = currentPackagingSolution.split("/");

		// if we already have more packs then the previous best solution, return
		// the previous best solution
		// TODO : this could be enhanced for sure
		if (splitCurrentPackagingSolution.length >= previousBestPackagingSolution.split("/").length) {
			return previousBestPackagingSolution;
		}

		// as it passed the previous check, we have a local best solution
		if (productsLeftToPack.isEmpty()) {
			return currentPackagingSolution;
		}

		// this is the pack being filled with products
		String currentPack = splitCurrentPackagingSolution[splitCurrentPackagingSolution.length - 1];

		// optimisation1 : no need to do useless symmetrical steps, just
		// consider
		// one product for each size

		for (String currentProduct : getSetOfProductSizes(productsLeftToPack)) {
			// this is the list of products left to pack after this operation
			String nextProductsLeftToPack = removeProductFromProductList(productsLeftToPack, currentProduct);

			// if the product fits to the current pack
			if (sum(currentPack + currentProduct) <= this.boxCapacity) {

				// add the product to the currentPackagingSolution without slash
				// (this means it's added to the last pack)
				previousBestPackagingSolution = processPackaging(nextProductsLeftToPack,
						currentPackagingSolution + currentProduct, previousBestPackagingSolution);
			} else {
				// "close" the previous pack, and create the next one with the
				// current Product
				previousBestPackagingSolution = processPackaging(nextProductsLeftToPack,
						currentPackagingSolution + "/" + currentProduct, previousBestPackagingSolution);
			}
		}
		return previousBestPackagingSolution;
	}

	/**
	 * Removes the current product from the list of products, and returns it
	 * 
	 * @param productsLeftToPack
	 * @param currentProduct
	 * @return
	 */
	protected String removeProductFromProductList(String productsLeftToPack, String currentProduct) {
		int indexOfCurrentProduct = productsLeftToPack.indexOf(currentProduct);
		return productsLeftToPack.substring(0, indexOfCurrentProduct)
				+ productsLeftToPack.substring(indexOfCurrentProduct + 1, productsLeftToPack.length());
	}

	/**
	 * takes the list of products size as string in input, output the list of
	 * sizes
	 * 
	 * @param productsLeftToPack
	 * @return
	 */
	protected Set<String> getSetOfProductSizes(String productsLeftToPack) {
		Set<String> result = new HashSet<>();
		for (int index = 0; index < productsLeftToPack.length(); index++) {
			String size = String.valueOf(productsLeftToPack.charAt(index));
			result.add(size);
		}
		return result;
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
	 * creates a packing solution with only one product on each pack
	 * 
	 * @param listProduct
	 * @return
	 */
	protected String packOneProductInEachPack(String listProduct) {
		StringBuilder builder = new StringBuilder();
		builder.append(String.valueOf(listProduct.charAt(0)));
		for (int index = 1; index < listProduct.length(); index++) {
			builder.append("/" + listProduct.charAt(index));
		}
		return builder.toString();
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
