package com.toby.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

public class Line extends Box {

	private Point startPoint;
	private Point endPoint;

	private Color color;

	public Line(Point startPoint, Point endPoint, Color color, double width, double length) {
		// Box takes (x, y, z), x and z have to be the same, because we want a
		// squared, long box as line.
		super(width, length, width);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.color = color;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Line [startPoint=" + startPoint + ", endPoint=" + endPoint + ", color=" + color + "]";
	}
}
