package com.altentechnology.gridcomponent.entities;

import com.altentechnology.gridcomponent.properties.GridProperties;

import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

public class GridComponentScene {

	private static double mouseXold = 0;
	private static double mouseYold = 0;

	private static final double rotateModifier = 10;

	private GridProperties gridProperties;

	private Grid grid;

	public GridComponentScene(GridProperties gridProperties) {
		this.gridProperties = gridProperties;
	}

	public Scene createGridScene3d() {
		Scene scene = initScene();
		return scene;
	}

	private Scene initScene() {
		Group group = new Group();
		boolean depthBuffer = true;
		Scene scene = new Scene(group, gridProperties.getSceneSize(), gridProperties.getSceneSize(), depthBuffer,
				SceneAntialiasing.BALANCED);
		scene.setFill(gridProperties.getBgColor());

		Camera camera = createCamera();
		scene.setCamera(camera);

		/*
		 * XXX extend the scene
		 * 
		 * here it would be possible to extend the scene, e.g. to have different
		 * grids with graphs displayed in the same scene.
		 * 
		 * it would look like this:
		 * group.getChildren().add(createSomething());
		 */

		group.getChildren().addAll(createGrid(gridProperties));

		addEventHandlers(scene, group);

		return scene;
	}

	private void addEventHandlers(Scene scene, Group group) {
		scene.addEventHandler(MouseEvent.ANY, event -> {
			mouseHandler(group, event);
		});

		addMouseWheelControl(scene, group);
	}

	private void addMouseWheelControl(Scene scene, Group group) {
		scene.setOnScroll(event -> {
			if (event.isShiftDown()) {
				group.setTranslateX(group.getTranslateX() + event.getDeltaY());
			} else if (event.isControlDown()) {
				group.setTranslateZ(group.getTranslateZ() - event.getDeltaY());
			} else {
				group.setTranslateY(group.getTranslateY() + event.getDeltaY());
			}
		});
	}

	private void mouseHandler(Group sceneRoot, MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED || event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			mousePressedOrMoved(sceneRoot, event);
		}
	}

	private Camera createCamera() {
		Camera camera = new PerspectiveCamera(true);

		camera.setNearClip(0.1);
		camera.setFarClip(5000.0);

		camera.setTranslateX(500);
		camera.setTranslateY(0);
		camera.setTranslateZ(-1200);

		camera.setRotationAxis(Rotate.X_AXIS);
		camera.setRotate(20);

		camera.setRotationAxis(Rotate.Y_AXIS);
		camera.setRotate(-20);

		return camera;
	}

	private void mousePressedOrMoved(Group sceneRoot, MouseEvent event) {
		Rotate xRotate = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate yRotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		sceneRoot.getTransforms().addAll(xRotate, yRotate);
		double mouseXnew = event.getSceneX();
		double mouseYnew = event.getSceneY();
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			double pitchRotate = xRotate.getAngle() + (mouseYnew - mouseYold) / rotateModifier;
			xRotate.setAngle(pitchRotate);
			double yawRotate = yRotate.getAngle() - (mouseXnew - mouseXold) / rotateModifier;
			yRotate.setAngle(yawRotate);
		}
		mouseXold = mouseXnew;
		mouseYold = mouseYnew;
	}

	public Group createGrid(GridProperties gridProperties) {
		grid = new Grid(gridProperties);
		return new Group(grid.createGrid());
	}

	public Grid getGrid() {
		return grid;
	}
}
