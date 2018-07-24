package com.dineshkrish.example1;

import org.apache.camel.builder.RouteBuilder;

/**
 * 
 * @author Dinesh Krishnan
 *
 */

public class HelloWorldRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// implementation goes here
		System.out.println("Hello World...");
	}
	
}
