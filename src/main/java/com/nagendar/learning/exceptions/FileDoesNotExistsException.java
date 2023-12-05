/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 6:42 pm
 */

package com.nagendar.learning.exceptions;

public class FileDoesNotExistsException extends BaseException {
	public FileDoesNotExistsException() {
	}

	public FileDoesNotExistsException(String message) {
		super(message);
	}
}
