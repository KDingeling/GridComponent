package com.altentechnology.gridcomponent.entities;

import java.util.List;

public interface ILineAccessor {

	/**
	 * @return A list of all {@link Line} instances in the grid. They don't have
	 *         to be sorted and can be in any random order.
	 */
	List<Line> getLines();
}
