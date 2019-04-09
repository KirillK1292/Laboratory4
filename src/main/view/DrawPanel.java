package main.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

class DrawPanel extends JPanel {
	
	private BufferedImage image;
	private List<Point> noise;
	private int lastValue;
	
	DrawPanel(int width, int height) {
		this.setSize(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		clearDrawPanel();
		noiseGenerator();
	}
	
	BufferedImage getSymbolImage() {
		return image;
	}
	
	private void noiseGenerator() {
		int n = image.getWidth() * image.getHeight() / 5;
		Point point;
		Random r = new Random();
		
		lastValue = 0;
		noise = new ArrayList<Point>();
		while(n != 0) {
			do {
				point = new Point(r.nextInt(image.getWidth()), r.nextInt(image.getHeight()));
			} while(noise.contains(point));
			noise.add(point);
			n--;
		}
	}
	
	void addNoiseRemoveNoise(int value) {
		int i, n = value * noise.size() / 100;
		int[] a = null;
		
		for(i = 0; i < lastValue; i++) {
			a = image.getRaster().getPixel(noise.get(i).x, noise.get(i).y, a);
			a[0]++;
			image.getRaster().setPixel(noise.get(i).x, noise.get(i).y, a);
		}
		for(i = 0; i < n; i++) {
			a = image.getRaster().getPixel(noise.get(i).x, noise.get(i).y, a);
			a[0]++;
			image.getRaster().setPixel(noise.get(i).x, noise.get(i).y, a);
		}
		lastValue = n;
		this.repaint();
	}
	
	void draw(int x, int y) {
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 25, 25);
		g.dispose();
		this.repaint();
	}
	
	void clearDrawPanel() {
		int x, y;
		
		for(y = 0; y < image.getHeight(); y++) {
			for(x = 0; x < image.getWidth(); x++)
				image.setRGB(x, y, Color.WHITE.getRGB());
		}
		this.repaint();
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image.getWidth() != this.getWidth() || image.getHeight() != this.getHeight()) {
			image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
			clearDrawPanel();
			noiseGenerator();
		}
		g.drawImage(image, 0, 0, null);
		g.dispose();
	}
			
}
