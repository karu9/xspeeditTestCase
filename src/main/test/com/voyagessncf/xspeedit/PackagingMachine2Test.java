package com.voyagessncf.xspeedit;

import org.junit.Test;

import java.util.Arrays;

import org.junit.Assert;

public class PackagingMachine2Test {
	
	
	PackagingMachine2 machine = new PackagingMachine2(10);
	
	@Test
	public void testProcessPackaging() throws InvalidListOfArticlesException {
		Assert.assertEquals(8, machine.pack("163841689525773").split("/").length);
	}

	@Test
	public void testSum() {
		String productList = "123456789";
		Assert.assertEquals(45, machine.sum(productList));
	}

	@Test
	public void testIsValid() {
		String productList = "123456789";
		Assert.assertTrue(machine.isValid(productList));
		productList = "e123456789";
		Assert.assertFalse(machine.isValid(productList));
	}

	@Test
	public void testGetComplementary() {
		String pack = "";
		Assert.assertNotNull(machine.getComplementary(pack, Arrays.asList("9")));
		pack = "9";
		Assert.assertNotNull(machine.getComplementary(pack, Arrays.asList("1")));
		pack = "9";
		Assert.assertNull(machine.getComplementary(pack, Arrays.asList("2")));
		pack = "81";
		Assert.assertNotNull(machine.getComplementary(pack, Arrays.asList("1")));
		pack = "81";
		Assert.assertNull(machine.getComplementary(pack, Arrays.asList("2")));
	}
}
