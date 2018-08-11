
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Draw extends JPanel {

	private static final long serialVersionUID = 1L;

	static BufferedImage image;
	static Graphics2D g2;
	Poly poly;
	DNA best;
	static DNA current;

	public Draw() {
		//setBackground(Color.BLACK);
		image = new BufferedImage(GUI.tWidth, GUI.tHeight, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) image.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	protected void paintComponent(Graphics g) {
		//super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
}
