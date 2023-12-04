/*
 * @author: pagidimarri.nagendar
 * @createdAt: 04/12/23 8:42 pm
 */

package com.nagendar.learning.io;

public class ConsolePrinter implements Printer{
	@Override
	public void print(String message) {
		System.out.println(message);
	}
}
