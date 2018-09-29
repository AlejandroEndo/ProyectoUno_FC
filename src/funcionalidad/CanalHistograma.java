package funcionalidad;

import processing.core.PApplet;
import processing.core.PImage;

public class CanalHistograma {

	private PApplet app;

	private PImage[] canales;

	private int[] red;
	private int[] green;
	private int[] blue;

	private int w;
	private int h;
	private int offset;

	private int maxValue;

	public CanalHistograma(PApplet app, PImage[] canales) {
		this.app = app;
		this.canales = canales;

		// 247 * 640
		app.getSurface().setSize(750, 660);

		w = canales[0].width;
		h = canales[0].height;
		offset = 10;

		red = new int[256];
		green = new int[256];
		blue = new int[256];

		red = feed(canales[2], "r");
		green = feed(canales[1], "g");
		blue = feed(canales[0], "b");

		int maxR = PApplet.max(red);
		int maxG = PApplet.max(green);
		int maxB = PApplet.max(blue);

		maxValue = PApplet.max(maxR, maxG, maxB);

		app.background(0);
	}

	public void display() {
		app.image(canales[2], 0, 0);
		app.image(canales[1], 0, h + offset);
		app.image(canales[0], 0, (h + offset) * 2);

		for (int i = w; i < app.width; i++) {
			int which = (int) PApplet.map(i, w, app.width, 0, 255);

			int y = (int) PApplet.map(red[which], 0, maxValue, h, 0);
			app.stroke(255, 0, 0);
			app.line(i, h, i, y);

			y = (int) PApplet.map(green[which], 0, maxValue, h * 2 + offset, h - offset);
			app.stroke(0, 255, 0);
			app.line(i, h * 2 + offset, i, y);

			y = (int) PApplet.map(blue[which], 0, maxValue, h * 3 + offset * 2, h * 2 + offset);
			app.stroke(0, 0, 255);
			app.line(i, h * 3 + offset * 2, i, y);
		}
	}

	private int[] feed(PImage img, String canal) {
		int[] c = new int[256];

		for (int i = 0; i < img.width; i++) {
			for (int j = 0; j < img.height; j++) {

				switch (canal) {
				case "r":
					c[(int) app.red(img.get(i, j))]++;
					break;

				case "g":
					c[(int) app.green(img.get(i, j))]++;
					break;

				case "b":
					c[(int) app.blue(img.get(i, j))]++;
					break;
				}
			}
		}
		return c;
	}
}
