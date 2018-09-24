package ejecutable;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Main extends PApplet {

	private PImage img;
	private PImage newImg;

	private PImage[] canales;

	private int[] red;
	private int[] blue;

	public static void main(String[] args) {
		PApplet.main("ejecutable.Main");
	}

	@Override
	public void settings() {
		size(247, 640);
	}

	@Override
	public void setup() {
		img = loadImage("../data/mini.jpg"); // Imagen inicial
		newImg = createImage(img.width, img.height / 3, RGB); // Imagen nueva

		canales = new PImage[3]; // Arreglo de canales 2:Red - 1:Green - 0:Blue

		red = new int[30];
		blue = new int[30];

		for (int i = 0; i < 3; i++) {
			canales[i] = img.get(0, i * (img.height / 3), img.width, img.height / 3);
		}

		int[] rssd = new int[900];
		int[] bssd = new int[900];

		int index = 0;

		for (int i = -15; i < 15; i++) {
			for (int j = -15; j < 15; j++) {
				PVector values = SSD(canales[1], canales[0], canales[2], i, j);

				rssd[index] = (int) values.x;
				bssd[index] = (int) values.y;
				index++;
			}
		}
		
		System.out.println(index);
		
		int minRed = min(rssd);
		int minBlue = min(bssd);

		System.out.println(minRed);

		image(canales[0], 0, 0);
	}

	private PVector SSD(PImage img1, PImage img2, PImage img3, int offsetX, int offsetY) {
		float redSSD = 0;
		float blueSSD = 0;

		for (int i = 15; i < img1.width - 15; i++) {
			for (int j = 15; j < img1.height - 15; j++) {
				// int index = i + (j * img1.width);

				float r = red(img2.get(i + offsetX, j + offsetY));
				float g = green(img1.get(i, j));
				float b = blue(img3.get(i + offsetX, j + offsetY));

				redSSD += pow(g - r, 2);
				blueSSD += pow(g - b, 2);
			}
		}

		return new PVector(redSSD, blueSSD);
	}
}
