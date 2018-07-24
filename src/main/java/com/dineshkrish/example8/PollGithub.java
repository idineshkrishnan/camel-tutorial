package com.dineshkrish.example8;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 
 * @author Dinesh Krishnan
 *
 */

public class PollGithub {

	public static void main(String[] args) throws Exception {

		// creating the camel context
		CamelContext context = new DefaultCamelContext();

		try {

			// adding the routes to the camel
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {

					from("github:commit?userName=<your_use_name>&password=<your_password>&repoName=<repo_name>&branchName=<branch_name>&repoOwner=<repo_owner_user_name>")
							.to("class:com.dineshkrish.GithubService?method=doSomething"); 

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
