package com.nagendar.learning;

/*
 * @author: pagidimarri.nagendar
 * @createdAt: 02/12/23 11:13 am
 */

import com.nagendar.learning.executor.CommandExecutor;
import com.nagendar.learning.executor.CutCommandExecutor;
import com.nagendar.learning.executor.RangeResolver;
import com.nagendar.learning.executor.RangeResolverImpl;
import com.nagendar.learning.io.ConsolePrinter;
import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.InputCommand;
import com.nagendar.learning.validator.CommandValidator;
import com.nagendar.learning.validator.CutCommandValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		CommandValidator validator = new CutCommandValidator();
		Printer printer = new ConsolePrinter();
		RangeResolver rangeResolver = new RangeResolverImpl();
		CommandExecutor executor = new CutCommandExecutor(printer);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			final String input = reader.readLine();
			InputCommand command = new InputCommand(input);
			boolean validation = validator.validate(command);
			System.out.println("validation = " + validation);
//			executor.execute(command);
		}
	}
}