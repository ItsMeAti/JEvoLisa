import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	JLabel image = new JLabel();
	JButton btnBrowse = new JButton("Browse");
	JButton btnStart = new JButton("Start");
	Draw panel = new Draw();
	Draw panel2 = new Draw();

	BufferedImage resizedImage;
	static BufferedImage targetImage;
	Image image2;
	int width = 200, height = 200;
	static int tWidth = 500, tHeight = 500;
	static Color[][] tColor = new Color[tWidth][tHeight];
	static int gen;
	DNA original = new DNA(50);

	public GUI() {
		//INIT
		super("Genetic Algorithm");
		setLayout(null);
		setSize(350+tWidth, 50+tHeight);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);

		// addlabel
		image.setBounds(10, 10, width, height);
		add(image);

		// add btn
		btnBrowse.setBounds(220, 10, 100, 30);
		add(btnBrowse);

		// add btn
		btnStart.setBounds(220, 180, 100, 30);
		add(btnStart);

		btnStart.setEnabled(false);

		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				SwingWorker<Void, DNA> worker = new SwingWorker<Void, DNA>() {

					@Override
					protected Void doInBackground() throws Exception {
						
						gen = 0;
						original.fitness(tColor);

						while (original.fitness > 0) {

							DNA copy = new DNA(original);
							copy.mutate(0.2f,0.1f);

							original.fitness(tColor);
							copy.fitness(tColor);
							
							System.out.println("Original fitness: " + original.fitness);
							System.out.println("Copy fitness: " + copy.fitness);
							
							gen++;
							if (original.fitness > copy.fitness) {
								original = new DNA(copy);

								publish(original);
							}

						}
						return null;
					}

					@Override
					protected void process(List<DNA> chunks) {
						if (chunks.size() > 0) {
							DNA best = new DNA(chunks.get(chunks.size() - 1));
							//toDraw.fitness(tColor);
							Draw.g2.clearRect(0, 0, tWidth, tHeight);
							System.out.println("**************************Best fitness: " + best.fitness);
							best.drawDNA(Draw.g2);
							repaint();
						}
					}
					

				};

				worker.execute();
			}
		});

		// browse button & pic loading
		btnBrowse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				// filter the files
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
				file.addChoosableFileFilter(filter);
				int result = file.showSaveDialog(null);
				// if the user click on save in Jfilechooser
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					try {
						BufferedImage image1 = ImageIO.read(selectedFile);
						targetImage = Util.resize(image1, tWidth, tHeight);
						for (int i = 0; i < tWidth; i++) {
							for (int j = 0; j < tHeight; j++) {
								int clr = targetImage.getRGB(i, j);
								int red = (clr & 0x00ff0000) >> 16;
								int green = (clr & 0x0000ff00) >> 8;
								int blue = clr & 0x000000ff;
								tColor[i][j] = new Color(red, green, blue);
							}
						}

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String path = selectedFile.getAbsolutePath();
					image.setIcon(ResizeImage(path));
					btnStart.setEnabled(true);
					System.out.println("Done");
				}
				// if the user click on save in Jfilechooser

				else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("No File Select");
				}
			}
		});

		panel.setBounds(330, 10, tWidth, tHeight);
		add(panel);

	}

	public ImageIcon ResizeImage(String ImagePath) {
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

}
