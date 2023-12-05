/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:24 pm
 */

package com.nagendar.learning.model;

import java.util.List;
import java.util.Set;

public class CommandMetaInfo {
	private Set<Character> flags;
	private String delimiter;
	private Character option;
	private List<Range> ranges;
//	private Set<Integer> indices;
	private String filePath;

	public CommandMetaInfo(Set<Character> flags, String delimiter, String filePath) {
		this.flags = flags;
		this.delimiter = delimiter;
		this.filePath = filePath;
	}

	public Set<Character> getFlags() {
		return flags;
	}

	public void setFlags(Set<Character> flags) {
		this.flags = flags;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public Character getOption() {
		return option;
	}

	public void setOption(Character option) {
		this.option = option;
	}

	public List<Range> getRanges() {
		return ranges;
	}

	public void setRanges(List<Range> ranges) {
		this.ranges = ranges;
	}

//	public Set<Integer> getIndices() {
//		return indices;
//	}
//
//	public void setIndices(Set<Integer> indices) {
//		this.indices = indices;
//	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
