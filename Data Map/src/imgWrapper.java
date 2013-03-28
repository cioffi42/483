

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**An imaging handling class for better control of the images */
  public class imgWrapper {
 

	private Image image;


	/**
	 */
	public imgWrapper(Image image) {
		this.image = image;
	}
	/**
	 */
	public int getWidth() {
		return image.getWidth(null);
	}

	/**
	 */
	public int getHeight() {
		return image.getHeight(null);
	}

	/**
	 */
	public void draw(Graphics g,int x,int y) {
		g.drawImage(image,x,y,null);
	}
	
}