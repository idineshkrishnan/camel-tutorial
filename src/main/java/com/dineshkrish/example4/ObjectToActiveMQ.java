package com.dineshkrish.example4;

import java.util.Date;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 
 * @author Dinesh Krishnan
 *
 */

public class ObjectToActiveMQ {
	
	public static void main(String[] args) throws Exception {

		// create a camel context
		CamelContext context = new DefaultCamelContext();

		// creating connection for activeMQ
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

		try {
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {

					from("direct:start")
						.to("activemq:queue:my_queue");
				}
			});

			// start the context
			context.start();

			// create producer template
			ProducerTemplate template = context.createProducerTemplate();
			
			// create Date instance. 
			// Note: you can send custom object too
			Date date = new Date();
			System.out.println("sent : " + date);
			
			// send an object
			template.sendBody("direct:start", date);

		} catch (Exception e) {
			// stop the context
			context.stop();
			e.printStackTrace();
		}

	}
}
