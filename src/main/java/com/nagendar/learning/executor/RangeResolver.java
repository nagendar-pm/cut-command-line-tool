package com.nagendar.learning.executor;
/*
 * @author: pagidimarri.nagendar
 * @createdAt: 09/12/23 7:45pm
 */

import com.nagendar.learning.model.Range;

import java.util.List;

public interface RangeResolver {
	List<Range> parseRanges(String rangesString);
	List<Range> mergeOverlappingRanges(List<Range> ranges);
}
