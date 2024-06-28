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
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Main {

	@Benchmark
	public void testParseAndFormat() throws IOException {
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
		String input = "nagi_cut -d , -f 1,2 resources/people-2000000.csv";
		commandModeFactory.getCommandMode(input).process(input);
		/*
		while (true) {
			System.out.print("$ ");
			final String input = reader.readLine();
			commandModeFactory.getCommandMode(input).process(input);
			System.out.println();
		}
		 */
	}

	public static void main(String[] args) throws IOException, RunnerException {
		Options opt = new OptionsBuilder()
				.include(Main.class.getSimpleName())
				.forks(1)
				.build();

		new Runner(opt).run();
	}
}