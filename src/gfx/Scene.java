/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfx;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Patricia
 */
public class Scene {
    private Map<Integer,List<SceneElem>> elems = 
    			new TreeMap<Integer,List<SceneElem>>();
    
    public void add(SceneElem se, int layer){
    	List<SceneElem> list =  elems.get(layer);
    	
        if( list == null )
        	elems.put(layer, list = new LinkedList<SceneElem>());
        
        list.add(se);
    }
    
    public void remove(SceneElem se){
        for( List<SceneElem> list : elems.values() )
        	list.remove(se);     
    }
    
    public void draw(Graphics g){
        for( List<SceneElem> list : elems.values() )
        	for( SceneElem se : list )
        		if(se.visible)
        			se.draw(g);
    }
}
