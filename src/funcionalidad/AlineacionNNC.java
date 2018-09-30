package funcionalidad;

import processing.core.PApplet;
import processing.core.PImage;

public class AlineacionNNC {
	private PApplet app;

	private PImage[] canales;

	public AlineacionNNC(PApplet app, PImage[] canales) {
		this.app = app;
		this.canales = canales;
	}

	public void display() {

	}

	public void reSize() {
		app.getSurface().setSize(750, 660);
	}
}
