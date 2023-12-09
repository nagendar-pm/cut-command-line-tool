/*
 * @author: pagidimarri.nagendar
 * @createdAt: 05/12/23 8:26 pm
 */

package com.nagendar.learning.model;

import java.util.Objects;

public class Range implements Comparable<Range> {
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

	@Override
	public int compareTo(Range that) {
		return this.to == that.to ? this.from - that.from : this.to - that.to;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Range range = (Range) o;
		return from == range.from && to == range.to;
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
}
