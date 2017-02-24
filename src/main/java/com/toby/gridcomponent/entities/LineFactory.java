package com.altentechnology.gridcomponent.entities;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.paint.Color;

public class LineFactory {

	/**
	 * This is some math to draw a line between two points. A line is
	 * actually a long standard {@link Box} shape that connects to points. In
	 * this method is some trigonometry to connect the start- and endpoint with
	 * a rotated box.
	 * 
	 * Better use a {@link MeshView} next time.
	 * 
	 * @param startPoint
	 *            The start of the line.
	 * @param endPoint
	 *            The end of the line.
	 * @param color
	 *            The color of the line.
	 * @param lineWidth
	 *            The thickness of the line.
	 * @see http://netzwerg.ch/blog/2015/03/22/javafx-3d-line/ - This explains the trigonometry used here.
	 */
	public static Line createLine(Point startPoint, Point endPoint, Color color, double lineWidth) {
		Point yAxis = new Point(0, 1, 0);
		Point diff = new Point(endPoint.subtract(startPoint));
		double length = diff.magnitude();

		//@formatter:off
		Point midPoint = new Point(
				endPoint.midpoint(startPoint.getX(), 
								  startPoint.getY(), 
								  startPoint.getZ()
				)
		);
		//@formatter:on

		Translate moveToMidPoint = new Translate(midPoint.getX(), midPoint.getY(), midPoint.getZ());
		Point axisOfRotation = new Point(diff.crossProduct(yAxis));

		double angle = Math.acos(diff.normalize().dotProduct(yAxis));
		Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

		Line line = new Line(startPoint, endPoint, color, lineWidth, length);
		PhongMaterial material = new PhongMaterial();
		material.setSpecularColor(color);
		material.setDiffuseColor(color.brighter());
		line.setMaterial(material);

		line.getTransforms().addAll(moveToMidPoint, rotateAroundCenter);

		return line;
	}
}
