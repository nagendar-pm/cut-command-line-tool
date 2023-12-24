/*
 * @author: pagidimarri.nagendar
 * @createdAt: 11/12/23 6:55 pm
 */

package com.nagendar.learning.option;

import com.nagendar.learning.factory.PrinterFactory;
import com.nagendar.learning.io.Printer;
import com.nagendar.learning.model.Command;
import com.nagendar.learning.model.Flag;
import com.nagendar.learning.model.ProcessedCommand;
import com.nagendar.learning.model.Range;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.nagendar.learning.constants.CommonConstants.CHAR_HIGHER_ORDER_BITS;
import static com.nagendar.learning.constants.CommonConstants.CHAR_INNER_BITS;

public class ByteCutOptionExecutor implements OptionExecutor {
	private final PrinterFactory printerFactory;

	public ByteCutOptionExecutor(PrinterFactory printerFactory) {
		this.printerFactory = printerFactory;
	}

	@Override
	public void executeOption(Command command) {
		ProcessedCommand processedCommand = (ProcessedCommand) command;
		Printer printer = printerFactory.getPrinter(command.getIsTerminal());
		boolean isFirstFile = true;
		for (String filePath : processedCommand.getFilePaths()) {
			printerFactory.getConsolePrinter()
					.print(String.format("Executing file: %s", filePath), false);
			Path path = Paths.get(filePath);
			try {
				StringBuilder sb = new StringBuilder();
				for (String line : Files.readAllLines(path)) {
					String s = handleLine(line, processedCommand);
					sb.append(s).append("\n");
				}
				String output = sb.deleteCharAt(sb.length()-1).toString();
				printer.print(String.format("%s", output), !isFirstFile);
				isFirstFile = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String handleLine(String line, ProcessedCommand processedCommand) {
		StringBuilder stringBuilder = new StringBuilder();
		// TODO: learn about the charsets and their usage
		byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
		for (Range range : processedCommand.getRanges()) {
			int from = Math.max(1, range.getFrom()) - 1;
			int to = Math.min(bytes.length, range.getTo()) - 1;
			// setting the flag here...
			if (processedCommand.getFlags().contains(Flag.FLAG_NOT_SPLIT_MULTI_BYTE_CHARACTERS)) {
				from = getValidUtf8Start(bytes, from);
				to = getValidUtf8Start(bytes, to);
			}
			byte[] bytesOfRange = Arrays.copyOfRange(bytes, from, to + 1);
			stringBuilder.append(new String(bytesOfRange));
		}
		return stringBuilder.toString();
	}

	private int getValidUtf8Start(byte[] bytes, int index) {
		/*
		 In UTF-8, the high-order bits for the start of a character are 110 for two-byte characters,
		 1110 for three-byte characters, and 11110 for four-byte characters.

		 The expression (b & 0xC0) != 0x80 checks that the byte does not match the bit pattern 10xxxxxx,
		 which indicates an interior byte in a multi-byte sequence.
		 */
		while (index >= 0 && (bytes[index] & CHAR_HIGHER_ORDER_BITS) != CHAR_INNER_BITS) {
			index--;
		}
		return Math.max(index, 0);
	}
}
