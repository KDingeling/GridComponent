package com.toby;

import java.util.List;

import com.toby.entities.GridComponentScene;
import com.toby.entities.Line;
import com.toby.exceptions.LineGapOutOfBoundsException;
import com.toby.exceptions.LineWidthOutOfBoundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.toby.cli.CLIParser;
import com.toby.entities.Grid;
import com.toby.exceptions.BadSizedGridException;
import com.toby.properties.GridProperties;
import com.toby.properties.GridPropertyChecker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GridComponentApplication extends Application {

	private static final Logger logger = LoggerFactory.getLogger(GridComponentApplication.class);
	private static final String applicationName = "GridComponent - Alten Technology";

	private static GridProperties props;
	

	public static void main(String[] args)
			throws BadSizedGridException, LineGapOutOfBoundsException, LineWidthOutOfBoundsException {
		CLIParser parser = new CLIParser();
		props = parser.parseCmdArgs(args);
		checkProperties(props);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		logger.debug("Starting GridComponenetApplication...");

		if (props.isNoDrawingMode()) {
			printLinesForQA(null, true);
		} else {
			createAndShowGrid(primaryStage);
		}
	}

	private void createAndShowGrid(Stage primaryStage) {
		GridComponentScene gridComponentScene = new GridComponentScene(props);
		Scene scene = gridComponentScene.createGridScene3d();
		printLinesForQA(gridComponentScene.getGrid(), false);
		primaryStage.setTitle(applicationName);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void printLinesForQA(Grid grid, boolean shutDownAfterPrint) {
		if (grid == null) {
			grid = new Grid(props);
			grid.createGrid();
		}
		List<Line> lines = grid.getLines();
		for (Line l : lines) {
			logger.debug(l + "");
		}
		if (shutDownAfterPrint) {
			System.exit(0);
		}
	}

	private static void checkProperties(GridProperties props)
			throws BadSizedGridException, LineGapOutOfBoundsException, LineWidthOutOfBoundsException {
		GridPropertyChecker checker = new GridPropertyChecker(props);
		checker.checkGridSizeFitsToGap();
		checker.checkGridSizeHasCenter();
		checker.checkLineGap();
		checker.checkLineWidth();
	}
}
