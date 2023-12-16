/*
 * @author: pagidimarri.nagendar
 * @createdAt: 04/12/23 8:40 pm
 */

package com.nagendar.learning.io;

import com.nagendar.learning.exceptions.BaseException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

import static com.nagendar.learning.constants.CommonConstants.TEMP_FILE_PATH;

public class FilePrinter implements Printer {
	@Override
	public void print(String message, boolean isAppend) {
		Path path = Paths.get(TEMP_FILE_PATH);
		try {
			if (isAppend) {
				Files.write(path, Collections.singleton(message),
						StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			}
			else {
				Files.write(path, Collections.singleton(message), StandardCharsets.UTF_8);
			}
		} catch (IOException e) {
			throw new BaseException(e.getMessage());
		}
	}

	@Override
	public void print(List<String> messages, boolean isAppend) {
		Path path = Paths.get(TEMP_FILE_PATH);
		try {
			if (isAppend) {
				Files.write(path, messages, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			}
			else {
				Files.write(path, messages, StandardCharsets.UTF_8);
			}
		} catch (IOException e) {
			throw new BaseException(e.getMessage());
		}
	}
}
