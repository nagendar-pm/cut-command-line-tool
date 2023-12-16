/*
 * @author: pagidimarri.nagendar
 * @createdAt: 04/12/23 8:42 pm
 */

package com.nagendar.learning.io;

import java.util.List;

public class ConsolePrinter implements Printer{
	@Override
	public void print(String message) {
		System.out.println(message);
	}

	@Override
	public void print(List<String> messages) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String message : messages) {
			stringBuilder.append(message);
			stringBuilder.append("\n");
		}
		System.out.println(stringBuilder.deleteCharAt(stringBuilder.length()-1));
	}
}
