/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:24 pm
 */

package com.nagendar.learning.model;

import java.util.List;
import java.util.Set;

public class CommandMetaInfo {
	private Set<String> flags;
	private String delimiter;
	private String option;
	private List<Range> ranges;
	private Set<String> filePath;

	public CommandMetaInfo() {}

	public CommandMetaInfo(Set<String> flags, String delimiter, Set<String> filePath) {
		this.flags = flags;
		this.delimiter = delimiter;
		this.filePath = filePath;
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
		return filePath;
	}

	public void setFilePaths(Set<String> filePath) {
		this.filePath = filePath;
	}
}
