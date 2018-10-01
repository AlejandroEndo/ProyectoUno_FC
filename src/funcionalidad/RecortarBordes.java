package funcionalidad;

import processing.core.PApplet;
import processing.core.PImage;

public class RecortarBordes {

	private PApplet app;

	private PImage img;

	public RecortarBordes(PApplet app, PImage img) {
		this.app = app;
		this.img = img;
	}

	public void display() {

	}
	
	public void reSize() {
		app.getSurface().setSize(750, 660);
	}
}
