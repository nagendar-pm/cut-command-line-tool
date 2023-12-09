/*
 * @author: pagidimarri.nagendar
 * @createdAt: 09/12/23 7:48 pm
 */

package com.nagendar.learning.executor;

import com.nagendar.learning.constants.CommonConstants;
import com.nagendar.learning.exceptions.InvalidRangeException;
import com.nagendar.learning.model.Range;

import java.util.*;

public class RangeResolverImpl implements RangeResolver {
	@Override
	public List<Range> parseRanges(String rangesString) {
		// 1. Split the string by comma(',')
		// 2. For every word in the response above, we can or can't have a range
		// 3. So we try to split by hyphen('-') now
		// 4. The response of the above shouldn't be more than 2 strings
		// 5. Parse the above to an integer and make a range out of it
		// Ex. 1,3-4,8-
		// For cases where range is open-ended, MAX_VALUE will be used as `to`
		// where range is open-started, 0 will be used as `from`

		List<Range> rangeList = new ArrayList<>();
		for (String rangeString : rangesString.split(CommonConstants.RANGE_SEPARATOR)) {
			Range newRange = getRange(rangeString);
			rangeList.add(newRange);
		}
		return rangeList;
	}

	private Range getRange(String rangeString) {
		String[] rangeNumbers = rangeString.split(CommonConstants.RANGE_HYPHEN);
		if (rangeNumbers.length > 2) {
			throw new InvalidRangeException(
					String.format("Range should contain only two numbers with a '-', Found: %s",
							rangeString));
		}
		String fromString = rangeNumbers[0];
		String toString = rangeNumbers.length > 1 ? rangeNumbers[1] : null;

		Integer from = null, to = null;
		if (Objects.nonNull(fromString) && !fromString.trim().isEmpty()) {
			from = Integer.parseInt(fromString);
		}
		if (Objects.nonNull(toString) && !toString.trim().isEmpty()) {
			to = Integer.parseInt(toString);
		}

		if (Objects.isNull(from)) {
			from = 0;
		}
		if (Objects.isNull(to)) {
			if (!rangeString.contains(CommonConstants.RANGE_HYPHEN)) {
				to = from;
			} else {
				to = Integer.MAX_VALUE;
			}
		}
		if (to < from) {
			throw new InvalidRangeException(
					String.format("Range should contain from-to, from <= to, Found: %s",
							rangeString));
		}

		return new Range(from, to);
	}

	@Override
	public List<Range> mergeOverlappingRanges(List<Range> ranges) {
		Collections.sort(ranges);
		LinkedList<Range> mergedRanges = new LinkedList<>();
		for (int i=0; i<ranges.size(); i++) {
			Range lastRange = null;
			if (!mergedRanges.isEmpty()) {
				lastRange = mergedRanges.peekLast();
			}
			Range nextRange = getOverlappedRange(lastRange, ranges.get(i));
			if (Objects.isNull(nextRange)) {
				mergedRanges.add(ranges.get(i));
			}
			else if (!nextRange.equals(lastRange)) {
				if (!mergedRanges.isEmpty()) mergedRanges.pollLast();
				mergedRanges.offerLast(nextRange);
			}
		}
		return mergedRanges;
	}

	private Range getOverlappedRange(Range range1, Range range2) {
		// f1, f2, t1, t2
		// f2, f1, t2, t1
		// f1, f2, t2, t1
		// f2, f1, t1, t2
		if (Objects.isNull(range1)) return range2;
		if (Objects.isNull(range2)) return range1;

		if (range1.getFrom() > range2.getFrom()) {
			return getOverlappedRange(range2, range1);
		}

		if (range1.getTo() < range2.getFrom() || range2.getTo() < range1.getFrom()) {
			return null;
		}

		if (range1.getFrom() <= range2.getFrom()
			&& range2.getTo() <= range1.getTo()) {
			return new Range(range1.getFrom(), range1.getTo());
		}
		else if (range2.getFrom() <= range1.getFrom()
			&& range1.getTo() <= range2.getTo()) {
			return new Range(range2.getFrom(), range2.getTo());
		}
		else if (range2.getFrom() <= range1.getTo()) {
			return new Range(range1.getFrom(), range2.getTo());
		}
		else if (range1.getFrom() <= range2.getTo()) {
			return new Range(range2.getFrom(), range1.getTo());
		}
		return null;
	}
}
