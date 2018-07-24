package com.dineshkrish.example52;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {

		File file = exchange.getIn().getBody(File.class);
		
		Files.write(Paths.get(file.getAbsolutePath()), "\nBy,\nDinesh Krishnan".getBytes(),
				StandardOpenOption.APPEND);
	}

}
