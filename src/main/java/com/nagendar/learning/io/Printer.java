package com.nagendar.learning.io;
/*
 * @author: pagidimarri.nagendar
 * @createdAt: 04/12/23 8:40 pm
 */

import java.util.List;

public interface Printer {
	default void print(String message, boolean isAppend) {}

	default void print(List<String> message, boolean isAppend) {}
}
