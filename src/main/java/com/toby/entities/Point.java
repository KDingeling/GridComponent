package com.toby.entities;

import javafx.geometry.Point3D;

public class Point extends Point3D {

	public Point(double x, double y, double z) {
		super(x, y, z);
	}

	public Point(Point3D point) {
		super(point.getX(), point.getY(), point.getZ());
	}

	@Override
	public String toString() {
		return "Point [x=" + getX() + ", y=" + getY() + ", z=" + getZ() + "]";
	}
}
