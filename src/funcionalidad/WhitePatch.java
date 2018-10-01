package funcionalidad;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class WhitePatch {
	private PApplet app;

	private PImage image;
	private PImage whiteImage;

	public WhitePatch(PApplet app, PImage img) {
		this.app = app;
		this.image = img;

		whiteImage = app.createImage(image.width, image.height, PConstants.RGB);

		whitePatch(image, whiteImage);
	}

	public void display() {
		app.image(image, 0, 0, image.width * 2, image.height * 2);
		app.image(whiteImage, image.width * 2, 0, whiteImage.width * 2, whiteImage.height * 2);
	}

	public void reSize() {
		app.getSurface().setSize(image.width * 4, image.height * 2);
	}
	
	private void whitePatch(PImage img, PImage newImg) {
		newImg.loadPixels();

		float[] red = new float[img.pixels.length];
		float[] green = new float[img.pixels.length];
		float[] blue = new float[img.pixels.length];

		for (int i = 0; i < img.width; i++) {
			for (int j = 0; j < img.height; j++) {
				int index = i + (j * img.width);

				float r = app.red(img.get(i, j));
				float g = app.green(img.get(i, j));
				float b = app.blue(img.get(i, j));

				red[index] = r;
				green[index] = g;
				blue[index] = b;
			}
		}

		float maxR = PApplet.max(red);
		float maxG = PApplet.max(green);
		float maxB = PApplet.max(blue);

		for (int i = 0; i < img.width; i++) {
			for (int j = 0; j < img.height; j++) {
				int index = i + (j * img.width);

				float r = app.red(img.get(i, j));
				float g = app.green(img.get(i, j));
				float b = app.blue(img.get(i, j));

				float newR = (255 / maxR) * r;
				float newG = (255 / maxG) * g;
				float newB = (255 / maxB) * b;

				newImg.pixels[index] = app.color(newR, newG, newB);
			}
		}
		newImg.updatePixels();
	}
}
