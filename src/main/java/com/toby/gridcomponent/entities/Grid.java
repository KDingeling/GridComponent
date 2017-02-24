package com.altentechnology.gridcomponent.entities;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.altentechnology.gridcomponent.properties.GridProperties;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Grid implements ILineAccessor {

	private static final Logger logger = LoggerFactory.getLogger(Grid.class);

	private List<Line> xLines = new ArrayList<Line>();
	private List<Line> yLines = new ArrayList<Line>();

	private List<Line> allLines;

	/*
	 * XXX splitted sidelength
	 * 
	 * The reason I splitted the sidelength up into single variables is that
	 * maybe a customer wants a grid starting from 0 to 100 and not from
	 * -sideLength to sideLength, so there won't be much effort needed to
	 * achieve this.
	 * 
	 * The method Grid#isCenterLine(double, double, double) will not work in that case
	 * and has to be adjusted to the correct zero lines.
	 */
	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;

	private double lineGap;

	private double lineWidth;

	private Color centerLineColor;
	private Color gridLineColor;

	public Grid(GridProperties gridProperties) {
		logger.debug("Instantiating Grid with properties: {}", gridProperties);

		this.xMin = -gridProperties.getSideLength() / 2;
		this.xMax = gridProperties.getSideLength() / 2;
		this.yMin = -gridProperties.getSideLength() / 2;
		this.yMax = gridProperties.getSideLength() / 2;

		this.lineGap = gridProperties.getLineGap();
		this.lineWidth = gridProperties.getLineWidth();

		this.centerLineColor = gridProperties.getCenterLineColor();
		this.gridLineColor = gridProperties.getGridLineColor();
	}

	/**
	 * Builds the main group of the grid. This is the entry point for
	 * extensions.
	 * 
	 * @return
	 */
	public Node createGrid() {
		Group gridGroup = new Group();
		gridGroup.getChildren().addAll(buildXYPlane());
		/*
		 * XXX extend the grid
		 *  
		 * here it would be possible to extend the grid, e.g. with a graph
		 * or another plane with a grid (XZ, YZ, ...).
		 * 
		 * it would look like this:
		 * gridGroup.getChildren().addAll(buildGraph());
		 */
		return gridGroup;
	}

	/**
	 * Builds the XY plane by first creating the lines that cross the X axis,
	 * then the lines that cross the Y axis.
	 * 
	 * @return A {@link Group} with the created {@link Line} instances attached.
	 */
	private Node buildXYPlane() {
		Group group = new Group();

		// create and add lines along the x-axis
		xLines = buildXCrossingLines();
		group.getChildren().addAll(xLines);

		// create and add lines along the y axis
		yLines = buildYCrossingLines();
		group.getChildren().addAll(yLines);

		return group;
	}

	/**
	 * Builds the lines that cross the X axis in a 90 degree angle. The lines
	 * will be colored as given in {@link #gridLineColor}. If the call of
	 * {@link #isCenterLine(double, double, double)} reveals that this line is in
	 * the center of the grid, it will be colored as given in
	 * {@link #centerLineColor}.
	 * 
	 * @return The created {@link Line} instances as list.
	 */
	private List<Line> buildXCrossingLines() {
		List<Line> lines = new ArrayList<Line>();
		for (double x = xMin; x <= xMax; x += lineGap) {
			Point startPoint = new Point(x, yMin, 0);
			Point endPoint = new Point(x, yMax, 0);
			Color lineColor;
			if (isCenterLine(x, xMin, xMax)) {
				lineColor = centerLineColor;
			} else {
				lineColor = gridLineColor;
			}
			Line line = createLine(startPoint, endPoint, lineColor);
			lines.add(line);
		}
		return lines;
	}

	/**
	 * Builds the lines that cross the Y axis in a 90 degree angle. The lines
	 * will be colored as given in {@link #gridLineColor}. If the call of
	 * {@link #isCenterLine(double, double, double)} reveals that this line is in
	 * the center of the grid, it will be colored as given in
	 * {@link #centerLineColor}.
	 * 
	 * @return The created {@link Line} instances as list.
	 */
	private List<Line> buildYCrossingLines() {
		List<Line> lines = new ArrayList<Line>();
		for (double y = yMin; y <= yMax; y += lineGap) {
			Point startPoint = new Point(xMin, y, 0);
			Point endPoint = new Point(xMax, y, 0);
			Color lineColor;
			if (isCenterLine(y, yMin, yMax)) {
				lineColor = centerLineColor;
			} else {
				lineColor = gridLineColor;
			}
			Line line = createLine(startPoint, endPoint, lineColor);
			lines.add(line);
		}
		return lines;
	}

	/**
	 * @param axisValue
	 *            The value to check for center.
	 * @param axisMin
	 *            The lowest value on this axis.
	 * @param axisMaxd
	 *            The highest value on this axis.
	 * @return True if this is the center of the given axis. Works only for axes
	 *         that have same length in negative and positive values, e.g.-600
	 *         to +600
	 */
	private boolean isCenterLine(double axisValue, double axisMin, double axisMax) {
		return axisMin + axisMax == axisValue;
	}

	/**
	 * Calls {@link LineFactory#createLine(Point, Point, Color, double)} to
	 * build and return a {@link Line} instance. Uses {@link #lineWidth} to
	 * determine how thick a line should be.
	 * 
	 * @param startPoint
	 *            Start point of the line.
	 * @param endPoint
	 *            End point of the line.
	 * @param color
	 *            Color of the line.
	 * @return A {@link Line} instance.
	 */
	private Line createLine(Point startPoint, Point endPoint, Color color) {
		return LineFactory.createLine(startPoint, endPoint, color, lineWidth);
	}

	/**
	 * This uses lazy loading so we don't create the list of lines if we don't
	 * need to. Also it uses caching, so if we already constructed the list with
	 * all the {@link Line} instances, we don't have to create it a second time.
	 */
	@Override
	public List<Line> getLines() {
		if (allLines == null || allLines.isEmpty()) {
			allLines = new ArrayList<Line>(xLines.size() + yLines.size());
			allLines.addAll(xLines);
			allLines.addAll(yLines);
		}
		return allLines;
	}
}
