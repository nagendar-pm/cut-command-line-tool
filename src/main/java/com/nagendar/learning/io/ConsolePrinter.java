/*
 * @author: pagidimarri.nagendar
 * @createdAt: 04/12/23 8:42 pm
 */

package com.nagendar.learning.io;

import java.util.List;

public class ConsolePrinter implements Printer{
	@Override
	public void print(String message, boolean isAppend) {
		System.out.println(message);
	}

	@Override
	public void print(List<String> messages, boolean isAppend) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String message : messages) {
			stringBuilder.append(message);
			stringBuilder.append("\n");
		}
		if (!stringBuilder.isEmpty()) {
			stringBuilder.deleteCharAt(stringBuilder.length()-1);
		}
		System.out.println(stringBuilder);
	}
}
