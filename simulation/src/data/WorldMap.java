/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author kimhy
 */
public class WorldMap {
    private int width, height;
    private WorldInst[][] mapdata;
    
    public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	private int StartX,StartY;

    public WorldMap(String filename) {
    	this.width=0; this.height=0;
        LoadMap(filename);
    }
    
    public void LoadMap(String filename){
        //비트맵 이미지를 읽어 그 정보에 따라 맵을 설정
    	//#000000 : 도로가 아니다
    	//#7F7F7F : 도로
    	//#FFFFFF : 차선
    	
    	BufferedImage fp=null;
    	Color buf;
    	try {
			fp=ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	this.width=fp.getWidth();
    	this.height=fp.getHeight();
    	System.out.println(Integer.toString(this.width)+" "+Integer.toString(this.height));
    	if(this.width==0 || this.height==0){
    		this.height=0;
    		this.width=0;
    		return;
    	}
    	mapdata=new WorldInst[this.height][this.width];
    	
    	int i,j;
    	for(i=0;i<this.height;i++){
    		for(j=0;j<this.width;j++){
    			buf=new Color(fp.getRGB(j,this.height-i-1));
    			if(buf.getRed()==63 && buf.getGreen()==62 && buf.getBlue()==67){ //Normal Road
    				this.mapdata[i][j]=WorldInst.BLACKROAD;
    			}else if(buf.getRed()==254 && buf.getGreen()==240 && buf.getBlue()==0){ //Border Lane (Yellow)
    				this.mapdata[i][j]=WorldInst.BORDERLANE;
    			}else if(buf.getRed()==255 && buf.getGreen()==255 && buf.getBlue()==255){ //Lane (White)
    				this.mapdata[i][j]=WorldInst.LANE;
    			}else if(buf.getRed()==0 && buf.getGreen()==0 && buf.getBlue()==255){ //Starting Point
    				this.mapdata[i][j]=WorldInst.BLACKROAD;
    				StartX=j;
    				StartY=i;
    			}else{
    				this.mapdata[i][j]=WorldInst.NOTROAD;
    			}
    		}
    	}
    }
    
    public boolean isLoaded(){
    	if(this.height==0)
    		return false;
    	else
    		return true;
    }
    
    public WorldInst getPos(int x,int y){
        return mapdata[y][x];
    }
    
    public int getStartX(){
    	return this.StartX;
    }
    public int getStartY(){
    	return this.StartY;
    }
}
