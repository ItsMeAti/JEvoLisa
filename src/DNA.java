import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DNA {
	Poly[] genes;
	double fitness;
	int max = 3;
	Color[][] c;
	static BufferedImage image;

	public DNA(int mennyi) {
		this.genes = new Poly[mennyi];
		for (int i = 0; i < genes.length; i++) {
			genes[i] = new Poly(max);
		}
		this.c = calcColorArray();
	}

	public DNA(DNA d) {
		this.genes = new Poly[d.genes.length];
		for (int i = 0; i < genes.length; i++) {
			genes[i] = new Poly(d.genes[i]);
		}
		this.c = calcColorArray();
		this.fitness = d.fitness;
	}

	public Color[][] calcColorArray() {
		image = new BufferedImage(GUI.tWidth, GUI.tHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setBackground(Color.red);
		this.drawDNA(g2);
		Color[][] currentColors = getColors(image);
		return currentColors;
	}

	public void fitness(Color[][] target) {
		double fitness = 0;
		for (int i = 0; i < GUI.tWidth; i++) {
			for (int j = 0; j < GUI.tHeight; j++) {
				Color c1 = target[i][j];
				Color c2 = c[i][j];
				
				double deltaRed = c1.getRed() - c2.getRed();
				double deltaBlue = c1.getBlue() - c2.getBlue();
				double deltaGreen = c1.getGreen() - c2.getGreen();

				double pixelFitness = deltaRed * deltaRed + deltaGreen * deltaGreen + deltaBlue * deltaBlue;

				fitness += pixelFitness;
			}
		}
		this.fitness = fitness;
		// return fitness;
	}

	public void mutate(float mutRate, float mutPerc) {
		Random r = new Random();
		int rand = Util.getRandom(0, 3);
		for (int i = 0; i < genes.length; i++) {
			if (r.nextFloat() < mutRate) {
				genes[i] = new Poly(genes[i].softMut(mutPerc));
/*
				switch(rand){
				case 0:
					genes[i] = new Poly(genes[i].hardMut());
					break;
				case 1:
					genes[i] = new Poly(genes[i].mediumMut());
					break;
				case 2:
					genes[i] = new Poly(genes[i].softMut(mutPerc));
					break;
				}
*/
			}
		}
		this.c = calcColorArray();
	}
	

	public void drawDNA(Graphics g) {
		for (int i = 0; i < genes.length; i++) {
			genes[i].drawPolygon(g);
		}
	}

	public static Color[][] getColors(BufferedImage image) {
		Color[][] colors = new Color[image.getWidth()][image.getHeight()];
		for (int i = 0; i < GUI.tWidth; i++) {
			for (int j = 0; j < GUI.tHeight; j++) {
				int clr = image.getRGB(i, j);
				int red = (clr & 0x00ff0000) >> 16;
				int green = (clr & 0x0000ff00) >> 8;
				int blue = clr & 0x000000ff;
				colors[i][j] = new Color(red, green, blue);
			}
		}
		return colors;
	}
}
