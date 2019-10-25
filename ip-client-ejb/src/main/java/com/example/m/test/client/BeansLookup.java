package com.example.m.test.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.example.m.test.server.Calculator;

public class BeansLookup {

	private static Calculator calculator;

	public static Calculator lookupCalculatorService() throws NamingException {
		if (calculator == null) {

			final Hashtable<String, String> jndiProperties = new Hashtable<>();
			jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.wildfly.naming.client.WildFlyInitialContextFactory");
			
			// use HTTP upgrade, an initial upgrade requests is sent to upgrade to the remoting protocol

			// jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			jndiProperties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");

			final Context context = new InitialContext(jndiProperties);

			calculator = (Calculator) context
					// .lookup("java:global/ip-server/ip-server-ejb/CalculatorBean!com.example.m.test.server.Calculator");
					.lookup("ejb:ip-server/ip-server-ejb/CalculatorBean!com.example.m.test.server.Calculator");

		}
		return calculator;
	}

}