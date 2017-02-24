package com.altentechnology.gridcomponent;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.altentechnology.gridcomponent.cli.CLIParser;
import com.altentechnology.gridcomponent.entities.Grid;
import com.altentechnology.gridcomponent.entities.GridComponentScene;
import com.altentechnology.gridcomponent.entities.Line;
import com.altentechnology.gridcomponent.exceptions.BadSizedGridException;
import com.altentechnology.gridcomponent.exceptions.LineGapOutOfBoundsException;
import com.altentechnology.gridcomponent.exceptions.LineWidthOutOfBoundsException;
import com.altentechnology.gridcomponent.properties.GridProperties;
import com.altentechnology.gridcomponent.properties.GridPropertyChecker;

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
