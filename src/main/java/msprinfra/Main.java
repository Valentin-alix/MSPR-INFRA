package msprinfra;

import java.net.UnknownHostException;

import msprinfra.factory.ADFactory;

public class Main {

	public static void main(String[] args) throws UnknownHostException {

		System.out.println(ADFactory.connection("JeanTest", "Mdpauhasard35!"));
	}

}
