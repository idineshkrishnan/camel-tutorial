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

public class CallMethodCamel_2 {

	public static void main(String[] args) throws Exception {

		// creating a camel context
		CamelContext context = new DefaultCamelContext();

		// adding the route to context
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {

				// creating a object
				MyService service = new MyService();

				from("direct:start")
				.bean(service, "doSomething");
			}
		});

		// start the context
		context.start();

		// create a producer and send message
		ProducerTemplate template = context.createProducerTemplate();
		template.sendBody("direct:start", "Your message goes here....");

		// stop the context
		context.stop();

	}
}
