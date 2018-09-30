package funcionalidad;

import processing.core.PApplet;
import processing.core.PImage;

public class WhitePatch {
	private PApplet app;

	private PImage[] canales;

	public WhitePatch(PApplet app, PImage[] canales) {
		this.app = app;
		this.canales = canales;
	}

	public void display() {

	}

	public void reSize() {
		app.getSurface().setSize(750, 660);
	}
}
