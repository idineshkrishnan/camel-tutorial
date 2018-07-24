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

public class XmlToJson {

	public static void main(String[] args) throws Exception {

		// create a camel context
		CamelContext context = new DefaultCamelContext();

		try {
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {

					from("direct:start")
						.process(new Processor() {
							public void process(Exchange exchange) throws Exception {
								
								// get the content
								String xml = exchange.getIn().getBody(String.class);
								
								// traform from xml to json
								byte[] encoded = xml.getBytes();
								JSONObject xmlJSONObj = XML.toJSONObject(new String(encoded));
								String json = xmlJSONObj.toString(4);
								
								// set the content
								exchange.getOut().setBody(json);
							}
						})
						.to("seda:end");
				}
			});

			// start the context
			context.start();


			String xml = "<employee>\r\n" + "	<id>101</id>\r\n" + "	<name>Dinesh Krishnan</name>\r\n"
					+ "	<age>20</age>\r\n" + "</employee>";
			
			System.out.println(xml);

			// creating the producer template
			ProducerTemplate producerTemplate = context.createProducerTemplate();
			producerTemplate.sendBody("direct:start", xml);
			
			// creating the consumer template
			ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
			String json = consumerTemplate.receiveBody("seda:end", String.class);
			
			System.out.println("------------XML to JSON-------------");
			System.out.println(json);

			// stop the context
			context.stop();
			
		} catch (Exception e) {
			context.stop();
			e.printStackTrace();
		}

	}
}
