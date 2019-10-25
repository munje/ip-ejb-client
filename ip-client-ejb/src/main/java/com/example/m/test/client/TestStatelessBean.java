package com.example.m.test.client;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import com.example.m.test.server.Calculator;

@Singleton
public class TestStatelessBean {

	boolean run = true;

	private static final Logger logger = Logger.getLogger(TestStatelessBean.class);

	protected Calculator lookuCalculator() throws NamingException {
		return BeansLookup.lookupCalculatorService();
	}

	@Schedule(second = "*/3", minute = "*", hour = "*", info = "test ", persistent = false)
	public void scheduleCall() {
		if (run) {
			logger.infov("===== @Schedule called");

			try {
				logger.warnv("=== Result of remote EJB call: {0}", lookuCalculator().doMagic(2));
			} catch (NamingException e) {
				run = false;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}