package funcionalidad;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Contraste 
{
	private PApplet app;

	private PImage[] canales;
	private PImage image;
	
	private int[] hred;
	private int[] hgreen;
	private int[] hblue;
	
	private int[] hCred;
	private int[] hCgreen;
	private int[] hCblue;

	private int w;
	private int h;
	private int offset;
	

	public Contraste(PApplet app, PImage[] canales) 
	{
		this.app = app;
		this.canales = canales;
		
		w = canales[0].width;
		h = canales[0].height;
		offset = 10;
		
		image = app.createImage(canales[0].width, canales[0].height, PConstants.RGB);
		
		hred = new int[256];
		hgreen = new int[256];
		hblue = new int[256];
		
		hCred = new int[256];
		hCgreen = new int[256];
		hCblue = new int[256];

		hred = hist(canales[2], "r");
		hgreen = hist(canales[1], "g");
		hblue = hist(canales[0], "b");
		
		hCred = histC(hred, w, h);
		hCgreen = histC(hgreen, w, h);
		hCblue = histC(hblue, w, h);
		
		imagenEq(image, canales, hCred, hCgreen, hCblue);
		
	}

	public void display() 
	{
		app.image(canales[2], 0, 0);
		app.image(canales[1], 0, h + offset);
		app.image(canales[0], 0, (h + offset) * 2);
		
		app.image(image, w, 2 * (h / 3), w * 2, h * 2);
	}
	
	
	
	public void reSize() 
	{
		app.getSurface().setSize(750, 660);
	}
	
	private int[] hist(PImage img, String canal) 
	{
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
	
	// Calcular el histograma Acumulado y normalizarlo
	private int[] histC(int[] histIn, int w, int h) 
	{
		int[] Hc = new int[256];
		
		Hc[0]=histIn[0];
	
		for(int i = 1; i < 256; i++) 
		{
			Hc[i] = Hc[i-1] + histIn[i];
		}
		
		for(int j = 0; j < 256; j++)
		{
			Hc[j] = (int)(255*Hc[j]/(w*h));
		}
		
		return Hc;
	}
	
	// Calcular imagen ecualizada
	private void imagenEq(PImage img, PImage[] canal, int[] histR, int[] histG, int[] histB) {
		img.loadPixels();
		for (int i = 0; i < img.width; i++) {
			for (int j = 0; j < img.height; j++) {
				int index = i + j * img.width;

				int r = histR[(int) app.red(canal[2].get(i,j))];
				int g = histG[(int) app.green(canal[1].get(i,j))];
				int b = histB[(int) app.blue(canal[0].get(i,j))];

				img.pixels[index] = app.color(r, g, b);
			}
		}
		img.updatePixels();
	}
	
}
