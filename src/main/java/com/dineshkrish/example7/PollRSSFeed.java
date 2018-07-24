package com.dineshkrish.example7;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 
 * @author Dinesh Krishnan
 *
 */

public class PollRSSFeed {

	public static void main(String[] args) throws Exception {

		// creating camel context
		CamelContext context = new DefaultCamelContext();

		try {

			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {

					from("rss:http://www.feedforall.com/sample.xml?alt=rss&splitEntries=false&consumer.delay=1000")
							.to("seda:end");
				}
			});

			// creating a consumer template
			ConsumerTemplate template = context.createConsumerTemplate();

			while (true) {
				context.start();

				// receiving the RSS feed
				String rssFeed = template.receiveBody("seda:end", String.class);
				System.out.println(rssFeed);
			}

		} catch (Exception e) {
			context.stop();
			System.out.println(e.getMessage());
		}
	}
}
