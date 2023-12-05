/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 6:09 pm
 */

package com.nagendar.learning.exceptions;

public class BaseException extends RuntimeException {
	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}
}
