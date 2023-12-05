/*
 * @author: pagidimarri.nagendar
 * @createdAt: 03/12/23 11:25 am
 */

package com.nagendar.learning.constants;

import java.util.Set;

public interface CommonConstants {
	String WHITESPACE_DELIMITER = " ";
	String CUT_COMMAND = "nagi_cut";
	String EXIT_COMMAND = "exit";
	String NON_CUT_COMMAND = "non_cut_command";
	String PIPE_OPERATOR = "\\|";
	String TEMP_FILE_PATH = "resources/temp.txt";
	String DOUBLE_QUOTE_CHARACTER = "\"";
	String SINGLE_QUOTE_CHARACTER = "'";
	String COMMAND_OPTION_AND_FLAG_DELIMITER = "-";
	String FLAG_NOT_SPLIT_MULTI_BYTE_CHARACTERS = "n";
	String FLAG_SUPPRESS_LINES_WITH_NO_FIELD_DELIMITER = "s";
	String FLAG_WHITESPACE_DELIMITER = "w";
	String OPTION_BYTE_LIST = "b";
	String OPTION_CHARACTER_LIST = "c";
	String OPTION_DELIMITER_SPECIFIER = "d";
	String OPTION_FIELD_SPECIFIER = "f";
	Set<String> ALLOWED_FLAGS = Set.of(FLAG_NOT_SPLIT_MULTI_BYTE_CHARACTERS,
			FLAG_SUPPRESS_LINES_WITH_NO_FIELD_DELIMITER,
			FLAG_WHITESPACE_DELIMITER);

	Set<String> ALLOWED_OPTIONS = Set.of(OPTION_BYTE_LIST,
			OPTION_CHARACTER_LIST,
			OPTION_DELIMITER_SPECIFIER,
			OPTION_FIELD_SPECIFIER);
}
