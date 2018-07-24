package com.dineshkrish.example31;

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

public class ProducerAndConsumer {

	public static void main(String[] args) throws Exception {

		// creating a camel context
		CamelContext context = new DefaultCamelContext();

		// adding routes to camel context
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start").to("seda:end");
			}
		});

		// start the context
		context.start();

		// producer template
		ProducerTemplate producerTemplate = context.createProducerTemplate();
		producerTemplate.sendBody("direct:start", "This message sent by producer");

		// consumer template
		ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
		String message = consumerTemplate.receiveBody("seda:end", String.class);

		System.out.println(message);

		// stop the context
		context.stop();
	}
}
