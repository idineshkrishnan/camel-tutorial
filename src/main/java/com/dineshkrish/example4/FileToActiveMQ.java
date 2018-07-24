package com.dineshkrish.example4;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 
 * @author Dinesh Krishnan
 *
 */

public class FileToActiveMQ {

	public static void main(String[] args) throws Exception {

		// create the camel context
		CamelContext context = new DefaultCamelContext();

		// create connection for activeMQ server
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		try {
			// adding route to camel context
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("file:input_dir?noop=true")
						.to("activemq:queue:my_queue");
				}
			});

			while (true) {
				// start the context
				context.start();
			}

		} catch (Exception e) {
			context.stop();
			e.printStackTrace();
		}

	}
}
