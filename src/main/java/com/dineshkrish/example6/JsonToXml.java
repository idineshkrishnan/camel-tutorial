package com.dineshkrish.example6;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.json.JSONObject;
import org.json.XML;

/**
 * 
 * @author Dinesh Krishnan
 *
 */

public class JsonToXml {

	public static void main(String[] args) throws Exception {

		// creating the camel context
		CamelContext context = new DefaultCamelContext();

		try {
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {

					from("direct:start").process(new Processor() {
						public void process(Exchange exchange) throws Exception {

							// get the json
							String json = exchange.getIn().getBody(String.class);

							// transform json to xml
							JSONObject jsonObject = new JSONObject(json);
							String xml = XML.toString(jsonObject);

							// set the xml
							exchange.getOut().setBody(xml);
						}
					}).to("seda:end");
				}
			});

			// start the context
			context.start();

			String json = "{\"employee\": {\r\n" + "    \"name\": \"Dinesh Krishnan\",\r\n" + "    \"id\": 101,\r\n"
					+ "    \"age\": 20\r\n" + "}}";

			System.out.println(json);

			// creating the producer template
			ProducerTemplate producerTemplate = context.createProducerTemplate();
			producerTemplate.sendBody("direct:start", json);

			// creating the consumer template
			ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
			String xml = consumerTemplate.receiveBody("seda:end", String.class);

			System.out.println("-----------------JSON to XML---------------------");
			System.out.println(xml);

			// stop the context
			context.stop();

		} catch (Exception e) {
			context.stop();
			e.printStackTrace();
		}
	}
}
