package main.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class Symbol {
	
	private String name;
	private BufferedImage image;
	private List<Integer> imageData;
	
	Symbol(String name) {
		this.name = name;
		image = new BufferedImage(25, 25, BufferedImage.TYPE_BYTE_BINARY);
        int x, y;		
		for(y = 0; y < image.getHeight(); y++) {
			for(x = 0; x < image.getWidth(); x++)
				image.setRGB(x, y, Color.WHITE.getRGB());
		}
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.setFont(new Font("Calibri", 1, 40));
		g.drawString(name, 0, 25);
		g.dispose();
		setImageData();
	}
	
	Symbol(String name, BufferedImage image) {
		this.name = name;
		this.image = new BufferedImage(25, 25, BufferedImage.TYPE_BYTE_BINARY);
		Graphics g = this.image.getGraphics();
		g.drawImage(image, 0, 0, 25, 25, null);
		g.dispose();
		setImageData();
	}
	
	String getName() {
		return name;
	}
	
	BufferedImage getImage() {
		return image;
	}
	
	List<Integer> getImageData() {
		return imageData;
	}
	
	private void setImageData() {
		int x, y;
		int[] a = null;
		
		imageData = new ArrayList<Integer>();
		for(y = 0; y < image.getHeight(); y++) {
			for(x = 0; x < image.getWidth(); x++) {
				a = image.getRaster().getPixel(x, y, a);
				imageData.add(a[0]);
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		Symbol symbol = (Symbol)obj;
		if(this.name == null)
			return symbol.name == null;
		return this.name.equals(symbol.name);
	}
	
	@Override
	public int hashCode() {
		return 31 * ((name == null) ? 0 : name.hashCode()) + ((image == null) ? 0 : image.hashCode());
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
