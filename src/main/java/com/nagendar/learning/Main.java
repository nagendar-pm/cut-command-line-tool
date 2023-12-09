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
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.Range;
import com.nagendar.learning.validator.CommandValidator;
import com.nagendar.learning.validator.CutCommandValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		CommandValidator validator = new CutCommandValidator();
		Printer printer = new ConsolePrinter();
		CommandExecutor executor = new CutCommandExecutor(printer);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		while (true) {
//			final String input = reader.readLine();
//			Command command = new Command(input);
//			boolean validation = validator.validate(command);
//			System.out.println("validation = " + validation);
//			executor.execute(command);
//		}
		RangeResolver resolver = new RangeResolverImpl();
		List<String> rangeStrings = List.of(
				"1-,2-4,5-8,10-11,9-13,15-18,17-20",
				"1-3,2-4,5-8,10-11,9-13,15-18,17-20",
				"17-20,2-4,10-11,5-8,9-13,15-18",
				"17-20,2-4,10-11,5-8,9-13,15-18,-9");
		for (String rangeString : rangeStrings) {
			System.out.println("rangeString = " + rangeString);
			List<Range> ranges = resolver.parseRanges(rangeString);
			System.out.println("ranges = " + ranges);
			List<Range> mergedRanges = resolver.mergeOverlappingRanges(ranges);
			System.out.println("mergedRanges = " + mergedRanges);
			System.out.println("-------------------");
		}
	}
}