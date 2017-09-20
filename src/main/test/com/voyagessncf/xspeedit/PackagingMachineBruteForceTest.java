package com.voyagessncf.xspeedit;

import org.junit.Test;
import org.junit.Assert;

public class PackagingMachineBruteForceTest {

	PackagingMachineBruteForce machine = new PackagingMachineBruteForce(10);

	@Test
	public void testProcessPackaging() throws InvalidListOfArticlesException {
		Assert.assertEquals(6, machine.pack("1638416895").split("/").length);
	}

	@Test
	public void testRemoveProductFromProductList() {
		String productList = "123456789";
		Assert.assertEquals("12346789", machine.removeProductFromProductList(productList, "5"));
	}

	@Test
	public void testGetSetOfProductSizes() {
		String productList = "123456789";
		Assert.assertEquals(9, machine.getSetOfProductSizes(productList).size());
		productList = "123456789123456789123456789123456789";
		Assert.assertEquals(9, machine.getSetOfProductSizes(productList).size());
	}

	@Test
	public void testSum() {
		String productList = "123456789";
		Assert.assertEquals(45, machine.sum(productList));
	}

	@Test
	public void testPackOneProductInEachPack() {
		String productList = "123456789";
		Assert.assertEquals("1/2/3/4/5/6/7/8/9", machine.packOneProductInEachPack(productList));
	}

	@Test
	public void testIsValid() {
		String productList = "123456789";
		Assert.assertTrue(machine.isValid(productList));
		productList = "e123456789";
		Assert.assertFalse(machine.isValid(productList));
	}
}
