package com.altentechnology.gridcomponent.cli;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.altentechnology.gridcomponent.properties.GridProperties;

import javafx.scene.paint.Color;

public class CLIParser {

	private Logger logger = LoggerFactory.getLogger(CLIParser.class);

	private static final int HEX_PARSING_BASE = 16;
	private static final int RGB_RED_BYTE_OFFSET = 16;
	private static final int RGB_GREEN_BYTE_OFFSET = 8;

	private static final String ARG_SIDE_LENGTH = "side-length";
	private static final String ARG_LINE_GAP = "line-gap";
	private static final String ARG_LINE_WIDTH = "line-width";
	private static final String ARG_GRID_COLOR = "grid-color";
	private static final String ARG_CENTER_COLOR = "center-color";
	private static final String ARG_BG_COLOR = "bg-color";
	private static final String ARG_HELP = "help";
	private static final String ARG_NO_DRAWING_MODE = "no-drawing-mode";

	private static final double DEFAULT_SIDE_LENGTH = 300d;
	private static final double DEFAULT_LINE_GAP = 30d;
	private static final double DEFAULT_LINE_WIDTH = 1d;
	private static final Color DEFAULT_CENTER_COLOR = Color.RED;
	private static final Color DEFAULT_GRID_COLOR = Color.YELLOW;
	private static final Color DEFAULT_BG_COLOR = Color.BLACK;
	private static final boolean DEFAULT_NO_DRAWING_MODE = false;

	private static final String CLI_COMMAND = "java -jar GridComponentApplication.jar";

	public CLIParser() {
	}

	public GridProperties parseCmdArgs(String[] args) {
		Options options = createCmdOptions();
		GridProperties props = parseOptions(options, args);
		return props;
	}

	private Options createCmdOptions() {
		Options options = new Options();
		Option optSideLength = new Option(null, ARG_SIDE_LENGTH, true, "side length of the grid in pixels");
		Option optLineGap = new Option(null, ARG_LINE_GAP, true, "gap between the lines in pixel");
		Option optLineWidth = new Option(null, ARG_LINE_WIDTH, true, "thickness of the lines in pixels");
		Option optGridColor = new Option(null, ARG_GRID_COLOR, true, "RGB value for the color of the grid lines (from 000000 to ffffff)");
		Option optBgColor = new Option(null, ARG_BG_COLOR, true, "RGB value for the color of the background  (from 000000 to ffffff)");
		Option optCenterColor = new Option(null, ARG_CENTER_COLOR, true, "RGB value for the color of the center lines  (from 000000 to ffffff)");
		Option optHelp = new Option(null, ARG_HELP, false, "shows this help");
		Option optNoDrawingMode = new Option(null, ARG_NO_DRAWING_MODE, false,
				"Flag to disbale drawing and only show created lines in cli output");

		options.addOption(optSideLength);
		options.addOption(optLineGap);
		options.addOption(optLineWidth);
		options.addOption(optCenterColor);
		options.addOption(optGridColor);
		options.addOption(optBgColor);
		options.addOption(optHelp);
		options.addOption(optNoDrawingMode);

		return options;
	}

	private GridProperties parseOptions(Options options, String[] args) {
		CommandLineParser parser = new BasicParser();
		HelpFormatter formatter = new HelpFormatter();
		GridProperties props = new GridProperties();
		try {
			CommandLine cmd = parser.parse(options, args);

			// print help if -h was given
			if (cmd.hasOption(ARG_HELP)) {
				formatter.printHelp(CLI_COMMAND, options);
				System.exit(0);
			}

			// parse side-length
			if (cmd.hasOption(ARG_SIDE_LENGTH)) {
				props.setSideLength(new Double(cmd.getOptionValue(ARG_SIDE_LENGTH)));
			} else {
				props.setSideLength(DEFAULT_SIDE_LENGTH);
				logger.info("Parameter {} was missing, using default: {}", ARG_SIDE_LENGTH, DEFAULT_SIDE_LENGTH);
			}

			// parse line-gap
			if (cmd.hasOption(ARG_LINE_GAP)) {
				props.setLineGap(new Double(cmd.getOptionValue(ARG_LINE_GAP)));
			} else {
				props.setLineGap(DEFAULT_LINE_GAP);
				logger.info("Parameter {} was missing, using default: {}", ARG_LINE_GAP, DEFAULT_LINE_GAP);
			}

			// parse line-width
			if (cmd.hasOption(ARG_LINE_WIDTH)) {
				props.setLineWidth(new Double(cmd.getOptionValue(ARG_LINE_WIDTH)));
			} else {
				props.setLineWidth(DEFAULT_LINE_WIDTH);
				logger.info("Parameter {} was missing, using default: {}", ARG_LINE_WIDTH, DEFAULT_LINE_WIDTH);
			}

			// parse center-color
			if (cmd.hasOption(ARG_CENTER_COLOR)) {
				Color centerColor = createColorFromHexValue(cmd.getOptionValue(ARG_CENTER_COLOR));
				props.setCenterLineColor(centerColor);
			} else {
				logger.warn("Parameter {} was missing, using default: {}", ARG_CENTER_COLOR, DEFAULT_CENTER_COLOR);
				props.setCenterLineColor(DEFAULT_CENTER_COLOR);
			}

			// parse grid-color
			if (cmd.hasOption(ARG_GRID_COLOR)) {
				Color gridColor = createColorFromHexValue(cmd.getOptionValue(ARG_GRID_COLOR));
				props.setGridLineColor(gridColor);
			} else {
				props.setGridLineColor(DEFAULT_GRID_COLOR);
				logger.warn("Parameter {} was missing, using default: {}", ARG_GRID_COLOR, DEFAULT_GRID_COLOR);
			}

			// parse bg-color
			if (cmd.hasOption(ARG_BG_COLOR)) {
				Color bgColor = createColorFromHexValue(cmd.getOptionValue(ARG_BG_COLOR));
				props.setBgColor(bgColor);
			} else {
				props.setBgColor(DEFAULT_BG_COLOR);
				logger.warn("Parameter {} was missing, using default: {}", ARG_BG_COLOR, DEFAULT_BG_COLOR);
			}

			// parse no-drawing-mode
			if (cmd.hasOption(ARG_NO_DRAWING_MODE)) {
				props.setNoDrawingMode(true);
			} else {
				props.setNoDrawingMode(DEFAULT_NO_DRAWING_MODE);
			}

		} catch (ParseException e) {
			formatter.printHelp(CLI_COMMAND, options);
			e.printStackTrace();
			System.exit(1);
		}

		return props;
	}

	private Color createColorFromHexValue(String rgbHex) {
		int rgbValue = Integer.parseInt(rgbHex, HEX_PARSING_BASE);
		int red = (rgbValue & 0xff0000) >> RGB_RED_BYTE_OFFSET;
		int green = (rgbValue & 0x00ff00) >> RGB_GREEN_BYTE_OFFSET;
		int blue  = rgbValue & 0x0000ff;
		return Color.rgb(red, green, blue);
	}
}
