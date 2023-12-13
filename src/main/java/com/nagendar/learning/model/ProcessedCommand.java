/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:24 pm
 */

package com.nagendar.learning.model;

import java.util.List;
import java.util.Set;

public class ProcessedCommand extends Command{
	private Set<String> flags;
	private String delimiter;
	private String option;
	private List<Range> ranges;
	private Set<String> filePaths;

	public ProcessedCommand() {
		super();
	}

	public ProcessedCommand(Set<String> flags, String delimiter, Set<String> filePaths) {
		super();
		this.flags = flags;
		this.delimiter = delimiter;
		this.filePaths = filePaths;
	}

	public Set<String> getFlags() {
		return flags;
	}

	public void setFlags(Set<String> flags) {
		this.flags = flags;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public List<Range> getRanges() {
		return ranges;
	}

	public void setRanges(List<Range> ranges) {
		this.ranges = ranges;
	}

	public Set<String> getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(Set<String> filePaths) {
		this.filePaths = filePaths;
	}
}
