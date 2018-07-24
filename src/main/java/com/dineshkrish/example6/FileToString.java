package com.dineshkrish.example6;

import java.io.File;
import java.nio.file.Files;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 
 * @author Dinesh Krishnan
 *
 */

public class FileToString {

	public static void main(String[] args) throws Exception {

		// creating the camel context
		CamelContext context = new DefaultCamelContext();

		try {
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {

					from("file:input_dir")
					.process(new Processor() {
						public void process(Exchange exchange) throws Exception {

							// get the file
							File file = exchange.getIn().getBody(File.class);

							// extract the content of the file
							byte[] bytes = Files.readAllBytes(file.toPath());
							String content = new String(bytes);

							// set the content
							exchange.getOut().setBody(content);
						}
					})
					.to("seda:end");
				}
			});

			// start the context
			context.start();

			// creating the consumer template
			ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
			String content = consumerTemplate.receiveBody("seda:end", String.class);

			System.out.println(content);

			// stop the context
			context.stop();

		} catch (Exception e) {
			context.stop();
			e.printStackTrace();
		}

	}
}
