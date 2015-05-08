/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Patricia
 */
public class ImageInstance extends SceneElem{
	/**
	 * A mutatott kep
	 */
    private BufferedImage image;
    /**
     * x koordinata
     */
    public int x;
    /**
     * y koordinata
     */
    public int y;
    
    /**
     * Inicializalja az ImageInstance-et egy adott BufferedImage-re
     * @param image BufferedImage
     */
    public ImageInstance( BufferedImage image ){
    	this.image = image;
    }
    
    /**
     * ImageInstance rajzolasa a helyere
     */
    @Override
    public void draw(Graphics g){
    	g.drawImage(image, x, y, null);    	
    }
}
