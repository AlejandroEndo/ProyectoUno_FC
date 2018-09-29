package ejecutable;

import funcionalidad.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Main extends PApplet {

	/**
	 * El objetivo es que para cada uno de los requerimientos del proyecto se deje
	 * un tama�o especifico para el canvas, para eso usar el metodo
	 * app.getSurface().setSize(w,h); en el constructor de la clase.
	 * 
	 * Para cada uno de los requerimientos se est� usando una clase.
	 * 
	 * Cada una de estas clases debe estar incluida en el paquete "funcionalidad".
	 */

	// Clases/Requerimientos
	private Alineacion alineacion;
	private CanalHistograma histograma;
	private Contraste contraste;
	private RecortarBordes bordesBonus;
	private TIFF tiffBonus;

	// Imagen con la que se trabaja.
	private PImage img;

	// Recorte inicial de la imagen.
	private PImage[] canales;

	// Manager de cada uno de los puntos a cumplir.
	private int caso;
	
	private boolean reSize;

	public static void main(String[] args) {
		PApplet.main("ejecutable.Main");
	}

	@Override
	public void settings() {
		size(750, 660); // El tama�o en este caso no importa pues en cada caso se va a cambiar el tama�o
						// de la ventana segun se requiera
	}

	@Override
	public void setup() {
		surface.setResizable(true);
		img = loadImage("../data/mini.jpg"); // Imagen inicial

		canales = new PImage[3]; // Arreglo de canales 2:Red - 1:Green - 0:Blue

		for (int i = 0; i < 3; i++) { // Se recortan cada una de las imagenes
			canales[i] = img.get(0, i * (img.height / 3), img.width, img.height / 3);
		}

		// Inicializacion de las clases/requerimientos
		histograma = new CanalHistograma(this, canales);
		alineacion = new Alineacion(this, canales);
		contraste = new Contraste(this, canales);
		tiffBonus = new TIFF(this, canales);
		bordesBonus = new RecortarBordes(this, canales);
	}

	@Override
	public void draw() {
		background(0);
		switch (caso) {
		case 0: // Separacion correcta canales de color imagen con sus respectivos
				// historgramas.
			histograma.display();
			
			if(reSize) {
				reSize = false;
				histograma.reSize();
			}
			break;

		case 1: // TODO Alineacion de los 3 canales de color de la imagen.
			alineacion.display();
			
			if(reSize) {
				reSize = false;
				alineacion.reSize();
			}
			break;

		case 2: // TODO Mejorar contraste de la imagen - Histogram equialization u otra tecnica.
			contraste.display();
			
			if(reSize) {
				reSize = false;
				contraste.reSize();
			}
			break;

		// BONUS

		case 3: // TODO usar imagenes TIFf de alta resolucion.
			tiffBonus.display();
			
			if(reSize) {
				reSize = false;
				tiffBonus.reSize();
			}
			break;

		case 4: // TODO Recortar bordes de la imagen.
			bordesBonus.display();
			
			if(reSize) {
				reSize = false;
				bordesBonus.reSize();
			}
			break;
		}
	}

	@Override
	public void keyPressed() {
		if (keyCode == RIGHT && caso < 4) {
			caso++;
			reSize = true;
		} else if (keyCode == LEFT && caso > 0) {
			caso--;
			reSize = true;
		}
	}

}
