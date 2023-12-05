package com.nagendar.learning;

/*
 * @author: pagidimarri.nagendar
 * @createdAt: 02/12/23 11:13 am
 */

import com.nagendar.learning.model.Command;
import com.nagendar.learning.validator.CommandValidator;
import com.nagendar.learning.validator.CutCommandValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		CommandValidator validator = new CutCommandValidator();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			final String input = reader.readLine();
			Command command = new Command(input);
			boolean validation = validator.validate(command);
			System.out.println("validation = " + validation);
		}

	}
}