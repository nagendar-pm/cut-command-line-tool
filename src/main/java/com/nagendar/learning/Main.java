package com.nagendar.learning;

/*
 * @author: pagidimarri.nagendar
 * @createdAt: 02/12/23 11:13 am
 */

import com.nagendar.learning.factory.CommandExecutorFactory;
import com.nagendar.learning.factory.CommandModeFactory;
import com.nagendar.learning.factory.CommandProcessorFactory;
import com.nagendar.learning.factory.CommandValidatorFactory;
import com.nagendar.learning.io.ConsolePrinter;
import com.nagendar.learning.io.FilePrinter;
import com.nagendar.learning.io.Printer;
import com.nagendar.learning.mapper.InputCommandToProcessedCommandMapper;
import com.nagendar.learning.mapper.RangeResolver;
import com.nagendar.learning.mapper.RangeResolverImpl;
import com.nagendar.learning.service.CommandProcessorService;
import com.nagendar.learning.service.CommandProcessorServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		Printer consolePrinter = new ConsolePrinter();
		Printer filePrinter = new FilePrinter();
		RangeResolver rangeResolver = new RangeResolverImpl();
		InputCommandToProcessedCommandMapper commandToProcessedCommandMapper =
				new InputCommandToProcessedCommandMapper(rangeResolver);
		CommandValidatorFactory commandValidatorFactory = new CommandValidatorFactory();
		CommandProcessorFactory commandProcessorFactory = new CommandProcessorFactory(commandToProcessedCommandMapper);
		CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(consolePrinter, filePrinter);
		CommandProcessorService commandProcessorService =
				new CommandProcessorServiceImpl(commandValidatorFactory,
						commandProcessorFactory, commandExecutorFactory);
		CommandModeFactory commandModeFactory = new CommandModeFactory(commandProcessorService);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			final String input = reader.readLine();
			commandModeFactory.getCommandMode(input).process(input);
		}
	}
}