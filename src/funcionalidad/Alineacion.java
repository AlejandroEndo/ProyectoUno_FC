package funcionalidad;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

public class Alineacion {

	private PApplet app;

	private PImage[] canales;

	private PImage image;

	private int src;
	
	private int w;
	private int h;
	private int offset;

	private int ofRedX;
	private int ofRedY;

	private int ofBlueX;
	private int ofBlueY;

	public Alineacion(PApplet app, PImage[] canales) {
		System.out.println("[ALINEANDO FOTOS]");
		this.app = app;
		this.canales = canales;

		image = app.createImage(canales[0].width, canales[0].height, PConstants.RGB);

		w = canales[0].width;
		h = canales[0].height;
		
		offset = 10;
		src = 5;

		PVector[] redComparator = new PVector[(int) PApplet.pow(src * 2, 2) + 1];
		PVector[] blueComparator = new PVector[(int) PApplet.pow(src * 2, 2) + 1];

		for (int i = 0; i < 101; i++) {
			redComparator[i] = new PVector();
			blueComparator[i] = new PVector();
		}

		alinear(redComparator, blueComparator);

		asignarPixeles(image, canales, ofRedX, ofRedY, ofBlueX, ofBlueY);
	}
	
	public void reSize() {
		app.getSurface().setSize(w * 3, h * 3 + offset * 2);
	}

	public void display() {
		app.image(canales[2], 0, 0);
		app.image(canales[1], 0, h + offset);
		app.image(canales[0], 0, (h + offset) * 2);

		app.image(image, w, 2 * (h / 3), w * 2, h * 2);
	}

	private void alinear(PVector[] Pred, PVector[] Pblue) {
		int index = 0;

		int[] redSSD = new int[Pred.length];
		int[] blueSSD = new int[Pblue.length];

		for (int i = -src; i < src; i++) {
			for (int j = -src; j < src; j++) {

				// RED
				Pred[index] = diferencia(i, j, canales[2], canales[1], 'r');
				redSSD[index] = (int) Pred[index].z;

				// BLUE
				Pblue[index] = diferencia(i, j, canales[0], canales[1], 'b');
				blueSSD[index] = (int) Pblue[index].z;
				index++;
			}
		}

		System.out.println("[MIN RED]");
		int redMinIndex = getSSDindex(redSSD);
		System.out.println("[MIN BLUE]");
		int blueMinIndex = getSSDindex(blueSSD);

		System.out.println(Pred[redMinIndex]);
		ofRedX = (int) Pred[redMinIndex].x;
		ofRedY = (int) Pred[redMinIndex].y;

		ofBlueX = (int) Pred[blueMinIndex].x;
		ofBlueY = (int) Pred[blueMinIndex].y;
	}

	private int getSSDindex(int[] ssd) {
		ssd[ssd.length - 1] = 999999999;
		int minValue = PApplet.min(ssd);
		
		System.out.println("MIN VALUE = " + minValue);
		for (int i = 0; i < ssd.length; i++) {

//			System.out.println("[" + i + ": " + ssd[i] + "]");

			if (ssd[i] == minValue) {
				System.out.println("[MIN VALUE FOUND]: " + i);
				return i;
			}
		}
		System.err.println("[SSD INDEX VALUE NOT FOUND]");
		return 0;
	}

	private PVector diferencia(int x, int y, PImage img, PImage toCompare, char c) {
		float ssd = 0;

		for (int i = 0; i < toCompare.width - 1; i++) {
			for (int j = 0; j < toCompare.height - 1; j++) {

				int green = (int) app.green(toCompare.get(i, j));

				if (c == 'r') {
					int red = (int) app.red(img.get(i + x, j + y));
					ssd += PApplet.pow(green - red, 2);
				} else if (c == 'b') {
					int blue = (int) app.red(img.get(i + x, j + y));
					ssd += PApplet.pow(green - blue, 2);
				}

			}
		}
		return new PVector(x, y, ssd);
	}

	private void asignarPixeles(PImage img, PImage[] canal, int rx, int ry, int bx, int by) {
		img.loadPixels();
		for (int i = 0; i < img.width; i++) {
			for (int j = 0; j < img.height; j++) {
				int index = i + j * img.width;

				int r = (int) app.red(canal[2].get(i + rx, j + ry));
				int g = (int) app.red(canal[1].get(i, j));
				int b = (int) app.red(canal[0].get(i + bx, j + by));

				img.pixels[index] = app.color(r, g, b);
			}
		}
		img.updatePixels();
		System.out.println("[RED]: X " + rx + " Y " + ry);
		System.out.println("[BLUE]: X " + bx + " Y " + by);
	}

	public PImage getImage() {
		return image;
	}
}
