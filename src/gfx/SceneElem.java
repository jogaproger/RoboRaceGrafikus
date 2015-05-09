/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfx;

import java.awt.Graphics;

/**
 *
 * @author Patricia
 */
public abstract class SceneElem {
    public boolean visible;
    protected Scene scene;
    
    public abstract void draw(Graphics g);
}
