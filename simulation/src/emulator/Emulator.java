/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emulator;

import InterfaceandPreset.Interface_ECU;
import data.WorldMap;

/**
 *
 * @author kimhy
 */
public class Emulator {

	//Filename
	private String FILENAME;
	
    /**
     * @param args the command line arguments
     */
	
	//Emulator status
	private boolean isRunning=false;
	
	private Mainframe Interfaceframe;
	private Frame_Steering steeringframe;
	private Dialog_Input inputDialog;
    //World
  	private Car currentcar;
  	private WorldMap currentworld;
  	private Interface_ECU currentECU;
  	//LED Status
  	private int lampa=0, lampb=0;
  	//Sound Status
  	private boolean sound=false;
  	//Reader
    
    public static void main(String[] args) {
        Emulator maininit = new Emulator();
        maininit.Init();
        
    }
    
    public void Init(){
    	//프로그램 시작과 동시에 실행되는 부분
    	inputDialog=new Dialog_Input();
    	inputDialog.setVisible(true);
    	currentECU = new Interface_ECU();
    	
    	if( inputDialog.mapmode == 1 ){
    		FILENAME="Res/longstraight.bmp";
    	}
    	else if( inputDialog.mapmode == 2 ){
    		FILENAME="Res/curve.bmp";
    	}
    	else{
    		FILENAME="Res/intensecurve.bmp";
    	}
    	
    	currentcar = new Car();
    	currentworld =  new WorldMap(FILENAME);
    	currentcar.SetWorld(currentworld);
    	
    	Interfaceframe = new Mainframe(this, FILENAME);
    	
    	//while(currentcar==null); //Wait
    	this.Start();
    	this.ResetWorld();
    	
    	
    	Interfaceframe.setVisible(true);
    	steeringframe=new Frame_Steering(this);
    	steeringframe.setVisible(true);
    	
    	
    	
    	//Test Scenario
    	
    	
    	
    	//while(true);
    	
    }
    
    //Update the world
    public void Start(){
    	if(!currentworld.isLoaded())
    		return;
    	else{
    		isRunning=true;
    		Update();
    	}
    }
    public void Stop(){
    	isRunning=false;
    }
    
    int i1;
	int i2;
	int i3;
	int i4;
    
    public void Update(){ //주기적으로 폼과 ECU로 데이터 주고받는 부분
    	
    	//1틱 진행
    	this.currentcar.movenexttick();
    	currentECU.sendLAKS(Interfaceframe.LKASmode);
    	//System.out.println("TurnSignal : " + Interfaceframe.Direction);
    	currentECU.sendTurnSignal(Interfaceframe.Direction);
    	//ECU 데이터 전달부
    	double speed;
    	if(currentcar.getSpd() <0 )
    		speed = 0;
    	else 
    		speed = currentcar.getSpd();
    	System.out.println(currentcar.getSpd()*100);
    	
    	speed = 100 * speed;
    	currentECU.sendSpeed((int)speed);
    	currentECU.sendRadius(120);    
    	
    	
    	
//    	p1=currentcar.getLanesenseLeft(50);
//    	point1x=(int) currentcar.Lsx;
//    	point1y=(int) currentcar.Lsy;
//    	
//    	p2=currentcar.getLanesenseLeft(0);
//    	point2x=(int) currentcar.Lsx;
//    	point2y=(int) currentcar.Lsy;
//    	
//    	p3=currentcar.getLanesenseRight(50);
//    	point3x=(int) currentcar.Rsx;
//    	point3y=(int) currentcar.Rsy;
//    	
//    	p4=currentcar.getLanesenseRight(0);
//    	point4x=(int) currentcar.Rsx;
//    	point4y=(int) currentcar.Rsy;
    	i1=currentcar.getLanesenseLeft(50);
    	i2=currentcar.getLanesenseLeft(0);
    	i3=currentcar.getLanesenseRight(50);
    	i4=currentcar.getLanesenseRight(0);
    	
    	
    	currentECU.sendPos_x1(i1);
    	currentECU.sendPos_x2(i2);
    	currentECU.sendPos_x3(i3);
    	currentECU.sendPos_x4(i4);
    	
//    	System.out.println("i1:"+i1);
//		System.out.println("i2:"+i2);
//		System.out.println("i3:"+i3);
//		System.out.println("i4:"+i4);
    	
    	
//    	System.out.println("x1 : " +currentcar.getLanesenseLeft(50));
//    	System.out.println("x2 : " +currentcar.getLanesenseLeft(0));
//    	System.out.println("x3 : " +currentcar.getLanesenseRight(50));
//    	System.out.println("x4 : " +currentcar.getLanesenseRight(0));
//
//
//    	
//    	currentECU.sendPos_x1(currentcar.getLanesenseLeft(50));
//    	currentECU.sendPos_x2(currentcar.getLanesenseLeft(0));
//    	currentECU.sendPos_x3(currentcar.getLanesenseRight(50));
//    	currentECU.sendPos_x4(currentcar.getLanesenseRight(0));
    	
    	//System.out.println("speed State : " +speed);
    	
    	//System.out.println("LED State : " +currentECU.getLEDaction());
        	
	   	if(currentECU.getLEDaction() == 2 && Interfaceframe.temp==1 ){
    	    			Interfaceframe.LKASmode = 2;
    	       	}
    	   
     	if(currentECU.getLEDaction() == 1 && Interfaceframe.temp==1)
    	       		Interfaceframe.LKASmode = 1;
     	
//     	int wheelangleRaw;
//     	int wheelangle;
//     	wheelangleRaw = currentECU.getwheelaction();
//     	wheelangle=wheelangleRaw-40;
     	int flag;
     	flag = currentECU.getwheelaction();
     	if(flag != 99999){
     		if(flag==1){
     			currentcar.steer_left(1);
     			
     		}
     		else if(flag==2){
     			currentcar.steer_right(1);
     		}
     		else{
     			currentcar.steer_left(0);
     			currentcar.steer_right(0);
     			
     		}
     		
//     		if(wheelangle <0){
//     	     	//currentcar.steer_left_absolute((wheelangle)/15);
//     	     	currentcar.steer_left(((wheelangle)/70));
//     	     	System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLL      " + wheelangle);
//     		}
//     	     else{
//     	     	//currentcar.steer_right_absolute((wheelangle)/15);
//     	     	currentcar.steer_right((wheelangle)/70);
//     	     	System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRrr   "   + wheelangle);
//     	     	}
     		
     	}else if(flag == 99999){
     		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAa   " + wheelangle);
     		
     	}
     	
    	//ECU 데이터 전달부
    	//currentECU.sendSpeed((int)currentcar.getSpd());
    	
    	//ECU 명령 수신부
    	//int tmp;
    	//lampa=currentECU.getLEDaction();
    	//tmp=currentECU.getwheelaction();
    	//if(tmp>0){
    	//	this.steer_right((double)tmp);
    	//}else{
    	//	this.steer_left((double)tmp*(-1.0));
    	//}
    	//if(currentECU.getSoundaction()==1)
    	//	sound=true;
    	//else
    	//	sound=false;
    	
    }
    
    //Car
    public Car getCar(){
    	return this.currentcar;
    }
    
    public void steer_left(double value){
        currentcar.steer_left(value);
    }
    
    public void steer_right(double value){
        currentcar.steer_right(value);
    }
    
    public void accelerate(double value){
        currentcar.accelerate(value);
    }
    
    public void brake(double value){
        currentcar.brake(value);
    }
    public void pause(){
        currentcar.pause();
    }
    
    //Reset
    public void ResetWorld(){
    	isRunning=false;
    	currentcar.setPos(currentworld.getStartX(),currentworld.getStartY());
    	
    }
    
    //Load Map
    public void ReadNewMap(String filename){
    	currentworld=new WorldMap(filename);
    	this.ResetWorld();
    }
    
    //Get Map
    public WorldMap getMap(){
    	return currentworld;
    }

    //Get LED Status
    public int getLED_a(){
    	return lampa;
    }
    public int getLED_b(){
    	return lampb;
    }
    
    //Get Sound Status
    public boolean getSound(){
    	return sound;
    }
}
