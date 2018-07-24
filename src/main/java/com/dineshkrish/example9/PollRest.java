package com.dineshkrish.example9;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 
 * @author Dinesh Krishnan
 *
 */

public class PollRest {

	public static void main(String[] args) throws Exception {

		// creating the camel context
		CamelContext context = new DefaultCamelContext();

		try {

			// adding the routes to the camel
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {

					// creating an instance for my bean
					MyRestService service = new MyRestService();
					
					from("timer://poll?fixedRate=true&delay=0&period=5000") // start immediately and poll every 5 second
					.to("https://jsonplaceholder.typicode.com/users/1") 
					.bean(service, "doSomething");
				}
			});

			while (true) {
				// start the context
				context.start();
			}

		} catch (Exception e) {
			// stop the context
			context.stop();
			System.out.println(e.getMessage());
		}

	}
}
