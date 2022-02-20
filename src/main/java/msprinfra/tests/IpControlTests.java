package msprinfra.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.UnknownHostException;

import org.junit.Test;

import msprinfra.factory.ADFactory;

public class IpControlTests {

	@Test
	public void testIp() throws UnknownHostException {
		assertTrue(ADFactory.ipControl("90.32.235.85"));
		assertFalse(ADFactory.ipControl("1.0.0.2"));
		assertTrue(ADFactory.ipControl("90.32.235.84"));
	}

}
