/*
 * @author: pagidimarri.nagendar
 * @createdAt: 10/12/23 6:08 pm
 */

package com.nagendar.learning.exceptions;

public class UnknownCommandException extends BaseException {
	public UnknownCommandException() {
	}

	public UnknownCommandException(String message) {
		super(message);
	}
}
