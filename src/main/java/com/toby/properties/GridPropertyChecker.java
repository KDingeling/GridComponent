package com.toby.properties;

import com.toby.exceptions.LineGapOutOfBoundsException;
import com.toby.exceptions.LineWidthOutOfBoundsException;
import com.toby.exceptions.BadSizedGridException;

public class GridPropertyChecker {
	
	private double MIN_LINE_GAP = 0d;
	private double MAX_LINE_GAP_MULTIPLICATOR = 0.5d;
	
	private double MIN_LINE_WIDTH = 0d;
	private double MAX_LINE_WIDTH = 10d;

	private GridProperties props;

	public GridPropertyChecker(GridProperties props) {
		this.props = props;
	}

	public void checkGridSizeFitsToGap() throws BadSizedGridException {
		if (props.getSideLength() % props.getLineGap() != 0) {
			throw new BadSizedGridException("side-length must be dividable by line-gap evenly");
		}
	}
	
	public void checkGridSizeHasCenter() throws BadSizedGridException {
		if ((props.getSideLength() / props.getLineGap()) %2 != 0) {
			throw new BadSizedGridException("this grid would have no center lines, make sure that (side-length / line-gap) % 2 = 0");
		}
	}

	public void checkLineGap() throws LineGapOutOfBoundsException {
		if (props.getLineGap() <= MIN_LINE_GAP || props.getLineGap() > MAX_LINE_GAP_MULTIPLICATOR * props.getSideLength()) {
			throw new LineGapOutOfBoundsException("line-gap must be in a range of 0 < line-gap < " + MAX_LINE_GAP_MULTIPLICATOR + " * side-length");
		}
	}

	public void checkLineWidth() throws LineWidthOutOfBoundsException {
		if (props.getLineWidth() <= MIN_LINE_WIDTH || props.getLineWidth() > MAX_LINE_WIDTH) {
			throw new LineWidthOutOfBoundsException(
					"line-width must be in a range of " + MIN_LINE_WIDTH + " < line-width <= " + MAX_LINE_WIDTH);
		}
	}
}
