package com.dineshkrish.example32;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 
 * @author Dinesh Krishnan
 *
 */

public class CallMethodCamel_1 {

	public static void main(String[] args) throws Exception {

		// creating a camel context
		CamelContext context = new DefaultCamelContext();

		// adding the routes to context
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:start").to("class:com.dineshkrish.example32.MyService?method=doSomething");
			}
		});

		// start the context
		context.start();

		// creating a producer template
		ProducerTemplate template = context.createProducerTemplate();

		// trigger the route
		template.sendBody("direct:start", "Your message goes here....");

		// stop the context
		context.stop();

	}
}
