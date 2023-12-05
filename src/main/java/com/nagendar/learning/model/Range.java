/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:26 pm
 */

package com.nagendar.learning.model;

public class Range {
	private int from;
	private int to;

	public Range() {}

	public Range(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public boolean contains(int element) {
		return from <= element && element <= to;
	}
}
