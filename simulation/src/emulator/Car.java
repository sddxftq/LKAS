/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emulator;

import data.WorldInst;
import data.WorldMap;
import java.lang.Math;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author kimhy
 */
public class Car {
    private WorldMap world;
    private double currentpositionx; //?쁽?옱 ?쐞移? x
    private double currentpositiony; //?쁽?옱 ?쐞移? y
    private double currentdirection; //?쁽?옱 諛⑺뼢
    private double spd; //?쁽?옱 ?냽?룄
    
    private double lastwheelcontrol;

    public void setPos(int x,int y){
    	currentpositionx=(double)x;
    	currentpositiony=(double)y;
    }
    
    public double getCurrentpositionx() {
		return currentpositionx;
	}

	public double getCurrentpositiony() {
		return currentpositiony;
	}

	public double getCurrentdirection() {
		return currentdirection;
	}

	public double getSpd() {
		return spd;
	}

	public Car(float currentpositionx, float currentpositiony, int currentdirection) {
        this.currentpositionx = currentpositionx;
        this.currentpositiony = currentpositiony;
        this.currentdirection = currentdirection;
        this.spd=0;
    }

    public Car() {
        this.currentpositionx = 0.0;
        this.currentpositiony = 0.0;
        this.currentdirection = 90.0;
        this.spd=0;
        this.lastwheelcontrol=0;
    }
    
    public void SetWorld(WorldMap world){
    	this.world=world;
    }
    
    public void steer_left(double value){
        //this.lastwheelcontrol+=value;
        this.lastwheelcontrol=value;
        movenexttick();
    }
    
    public void steer_left_absolute(double value){
    	this.currentdirection+=value;
    	movenexttick();
    }
    
    public void steer_right(double value){
        
        //this.lastwheelcontrol+=value*-1;
        this.lastwheelcontrol=value*-1;
        movenexttick();
    }
    
    public void steer_right_absolute(double value){
    	this.currentdirection-=value;
    	movenexttick();
    }
    
    
    public void accelerate(double value){
    	
        spd+=value;
        
        
        movenexttick();
    }
    
    public void brake(double value){
        spd-=value;
        movenexttick();
    }
    
    public void pause(){
        spd=0;
        this.lastwheelcontrol=0;
        movenexttick();
    }
    
    public void movenexttick(){
    	double resx, resy;
    	currentdirection=currentdirection+this.lastwheelcontrol;
    /*	if(currentdirection>135){
    		currentdirection=135.0;
    	}else if(currentdirection<45){
    		currentdirection=45.0;
    	}
    	*/
    	/*if(currentdirection==90.0 || currentdirection==270.0){	
    		resx=currentpositionx+spd*cos(Math.toRadians(currentdirection));
    		resy=currentpositiony;
    	}else if(currentdirection==180.0 || currentdirection==0.0){
    		resx=currentpositionx;
    		resy=currentpositiony+spd*sin(Math.toRadians(currentdirection));
    	}else{*/
    		resx=currentpositionx+spd*cos(Math.toRadians(currentdirection));
    		resy=currentpositiony+spd*sin(Math.toRadians(currentdirection));
    	//}
    	//System.out.println(resx+" "+resy);
    	
    	
    	//이동이 불가능한 영역으로 차가 이동하려고 할 경우!
    	if(world.getHeight()<=resy || 0>=resy || world.getWidth()<=resx || 0>=resx){
    		resx=currentpositionx; resy=currentpositiony;
    		//this.spd=0;
    		//System.out.println(resx+" "+resy+" "+"Border Not OK");
    	}
    	/*else if(world.getPos((int)resx, (int)resy)==WorldInst.NOTROAD){
    		//System.out.println(resx+" "+resy+" "+world.getPos((int)resx, (int)resy).toString());
    		resx=currentpositionx; resy=currentpositiony;
    		this.spd=0;
    		
    	}*/
    	
    	
    	this.currentpositionx=resx;
    	this.currentpositiony=resy;
        
    }
    private final int MAX_SENSE_RANGE = 300; //최대로 탐지할 좌우 범위
    double Lsx, Lsy, Rsx, Rsy;
    
    public int getLanesenseLeft(int d){
    	double sensecenterdx, sensecenterdy, sx=0,sy=0;
    	sensecenterdx=currentpositionx+d*cos(Math.toRadians(currentdirection));
    	sensecenterdy=currentpositiony+d*sin(Math.toRadians(currentdirection));
    	//System.out.println("current direction:"+currentdirection);
    	
    	int i;
    	for(i=0;i<MAX_SENSE_RANGE;i++){
    		sx=sensecenterdx-(double)i*sin(Math.toRadians(currentdirection));
    		sy=sensecenterdy+(double)i*cos(Math.toRadians(currentdirection));
    		
    		if(sx>=world.getWidth() || sx<=0 || sy>=world.getHeight() || sy<=0){
                break;
             }
    		if(world.getPos((int)sx,(int)sy)==WorldInst.LANE || world.getPos((int)sx,(int)sy)==WorldInst.BORDERLANE)
    			break;
    	}
    	Lsx=sy;
    	Lsy=sx;
    	return i;
    }
    
    
    public int getLanesenseRight(int d){
    	double sensecenterdx, sensecenterdy, sx,sy;
    	sensecenterdx=currentpositionx+d*cos(Math.toRadians(currentdirection));
    	sensecenterdy=currentpositiony+d*sin(Math.toRadians(currentdirection));
    	int i;
    	for(i=0;i<MAX_SENSE_RANGE;i++){
    		sx=sensecenterdx+(double)i*sin(Math.toRadians(currentdirection));
    		sy=sensecenterdy-(double)i*cos(Math.toRadians(currentdirection));
    		if(sx>=world.getWidth() || sx<=0 || sy>=world.getHeight() || sy<=0){
                break;
             }
    		if(world.getPos((int)sx,(int)sy)==WorldInst.LANE || world.getPos((int)sx,(int)sy)==WorldInst.BORDERLANE)
    			break;
    	}
    	return i;
    }

	public double getLastwheelcontrol() {
		return lastwheelcontrol;
	}
}
