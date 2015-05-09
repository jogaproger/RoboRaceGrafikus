package gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Resource {
	/**
	 * Maximalis fontmeret
	 */
	public static final int MAX_FONT_SIZE = 100;
	/**
	 * Fontok tombje, flyweight minta
	 */
	static Font fonts[] = new Font[MAX_FONT_SIZE + 1];
	/**
	 * minden kert Bufferedimage kozos 
	 */
	static Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();

	/**
	 * Adott meretu betuhoz tartozo Font objektum lekerdezese
	 * @param size Betûméret
	 */
	public static Font getFont(int size) {
		if (size > MAX_FONT_SIZE)
			size = MAX_FONT_SIZE;

		if (fonts[size] == null)
			fonts[size] = new Font(Font.SERIF, Font.BOLD, size);
		return fonts[size];
	}
	/**
	 * Kep lekerese
	 * @param path Eleresi utvonal
	 * @return a kep betoltve BufferedImage-kent
	 */
	public static BufferedImage getImage(String path) {

		BufferedImage img = map.get(path);
		if (img == null) {
			try {
				img = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			map.put(path, img);
		}
		return img;
	}
}
