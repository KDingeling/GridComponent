package com.altentechnology.gridcomponent.properties;

import javafx.scene.paint.Color;

public class GridProperties {

	private double lineGap;
	private double lineWidth;
	private double sideLength;

	private Color centerLineColor;
	private Color gridLineColor;
	private Color bgColor;

	private boolean isNoDrawingMode;

	public GridProperties() {

	}

	public double getLineGap() {
		return lineGap;
	}

	public void setLineGap(double lineGap) {
		this.lineGap = lineGap;
	}

	public Color getCenterLineColor() {
		return centerLineColor;
	}

	public void setCenterLineColor(Color centerLineColor) {
		this.centerLineColor = centerLineColor;
	}

	public Color getGridLineColor() {
		return gridLineColor;
	}

	public void setGridLineColor(Color gridLineColor) {
		this.gridLineColor = gridLineColor;
	}

	public boolean isNoDrawingMode() {
		return isNoDrawingMode;
	}

	public void setNoDrawingMode(boolean isNoDrawingMode) {
		this.isNoDrawingMode = isNoDrawingMode;
	}

	public double getSideLength() {
		return sideLength;
	}

	public void setSideLength(double sideLength) {
		this.sideLength = sideLength;
	}

	public double getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(double lineWidth) {
		this.lineWidth = lineWidth;
	}

	public double getSceneSize() {
		return sideLength + 100;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	@Override
	public String toString() {
		return "GridProperties [lineGap=" + lineGap + ", lineWidth=" + lineWidth + ", sideLength=" + sideLength
				+ ", centerLineColor=" + centerLineColor + ", gridLineColor=" + gridLineColor + ", bgColor=" + bgColor
				+ ", isNoDrawingMode=" + isNoDrawingMode + "]";
	}

}
