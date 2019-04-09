package main.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

class ImagePanel extends JPanel {
	
	private BufferedImage image;

	BufferedImage getImage() {
		return image;
	}

	void setImage(BufferedImage image) {
		this.image = image;
		this.repaint();
	}
	
	void clearImagePanel() {
		image = null;
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image != null) {
			int width = (image.getWidth()>this.getWidth())?this.getWidth()%image.getWidth():image.getWidth(), 
			height = (image.getHeight()>this.getHeight())?this.getHeight()%image.getHeight():image.getHeight(),
			x = this.getWidth() / 2 - width / 2,
			y = this.getHeight() / 2 - height / 2;
			g.drawImage(image, x, y, width, height, null);
			g.dispose();
		}
	}

}
