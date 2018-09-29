package ejecutable;

import funcionalidad.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Main extends PApplet {

	/**
	 * El objetivo es que para cada uno de los requerimientos del proyecto se deje
	 * un tamaño especifico para el canvas, para eso usar el metodo
	 * app.getSurface().setSize(w,h); en el constructor de la clase.
	 * 
	 * Para cada uno de los requerimientos se está usando una clase.
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

	public static void main(String[] args) {
		PApplet.main("ejecutable.Main");
	}

	@Override
	public void settings() {
		size(20, 20); // El tamaño en este caso no importa pues en cada caso se va a cambiar el tamaño
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

		switch (caso) {
		case 0: // Separacion correcta canales de color imagen con sus respectivos
				// historgramas.
			histograma.display();
			break;

		case 1: // TODO Alineacion de los 3 canales de color de la imagen.
			alineacion.display();
			break;

		case 2: // TODO Mejorar contraste de la imagen - Histogram equialization u otra tecnica.
			contraste.display();
			break;

		// BONUS

		case 3: // TODO usar imagenes TIFf de alta resolucion.
			tiffBonus.display();
			break;

		case 4: // TODO Recortar bordes de la imagen.
			bordesBonus.display();
			break;
		}
	}

	@Override
	public void keyPressed() {
		switch (keyCode) {
		case 96:
			caso = 0;
			System.out.println("[SEPEARACION CORRECTA DE CANALES DE COLOR]");
			break;
		case 97:
			caso = 1;
			System.out.println("[ALINEACION DE LOS 3 CANALES DE COLOR]");
			break;

		case 98:
			caso = 2;
			System.out.println("[MEJORAR CONTRASTE DE LA IMAGEN]");
			break;

		case 99:
			caso = 3;
			System.out.println("[BONUS] uso de imagen de alta resolución");
			break;

		case 100:
			caso = 4;
			System.out.println("[BONUS] Recorte de bordes");
			break;
		}
	}

	
}
