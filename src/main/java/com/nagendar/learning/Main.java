package com.nagendar.learning;

/*
 * @author: pagidimarri.nagendar
 * @createdAt: 02/12/23 11:13 am
 */

import com.nagendar.learning.factory.*;
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
		PrinterFactory printerFactory = new PrinterFactory();
		RangeResolver rangeResolver = new RangeResolverImpl();
		InputCommandToProcessedCommandMapper commandToProcessedCommandMapper =
				new InputCommandToProcessedCommandMapper(rangeResolver);
		CommandValidatorFactory commandValidatorFactory = new CommandValidatorFactory();
		CommandProcessorFactory commandProcessorFactory = new CommandProcessorFactory(commandToProcessedCommandMapper);
		CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(printerFactory);
		CommandProcessorService commandProcessorService =
				new CommandProcessorServiceImpl(commandValidatorFactory,
						commandProcessorFactory, commandExecutorFactory);
		CommandModeFactory commandModeFactory = new CommandModeFactory(commandProcessorService);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.print("$ ");
			final String input = reader.readLine();
			commandModeFactory.getCommandMode(input).process(input);
			System.out.println();
		}
	}
}