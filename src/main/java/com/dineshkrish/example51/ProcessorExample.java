package com.dineshkrish.example51;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 
 * @author Dinesh Krishnan
 *
 */

public class ProcessorExample {

	public static void main(String[] args) throws Exception {

		// create the camel context
		CamelContext context = new DefaultCamelContext();

		// adding route to camel context
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start")
					.process(new MyProcessor())
					.to("seda:end");
			}
		});

		// start the context
		context.start();
		
		// creating producer template
		ProducerTemplate pTemplate = context.createProducerTemplate();
		pTemplate.sendBody("direct:start", "Dinesh Krishnan");
		
		// creating consumer template
		ConsumerTemplate cTemplate = context.createConsumerTemplate();
		String message = cTemplate.receiveBody("seda:end", String.class);
		
		System.out.println(message);

		// stop the context
		context.stop();

	}

}
