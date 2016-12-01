/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceandPreset;

import com.etas.vrta.comms.VECUConnection;

/**
 *
 * @author kimhy
 */
public class Interface_ECU {
	private ECUConnector vecu;
	private String PATH="";

    public Interface_ECU() {
    	vecu=new ECUConnector();
    }
    
    public void poll(){
        
    }
    
    public int getwheelaction(){
    
        return (Integer)vecu.recvdataManually(ECUConnector.inWheelActuator);
    }
    
    public int getLEDaction(){
        return (Integer)vecu.recvdataManually(ECUConnector.inLEDActuator);
    }
    
    public int getSoundaction(){
        return (Integer)vecu.recvdataManually(ECUConnector.inSoundActuator);
    }
    
    
    
    
    public void sendTurnSignal(int value){
    	int buf=value;
        vecu.sendsensorManually(ECUConnector.outTurnSignalSwitch,(Object)buf);
    }
    
    public void sendSpeed(int value){
    	int buf=value;
        vecu.sendsensorManually(ECUConnector.outSpeedSensor, (Object)buf);
    }
	public void sendRadius(int value)
	{
		int buf=value;
		vecu.sendsensorManually(ECUConnector.outRadiusSensor, (Object)buf);
	}
	public void sendLAKS(int value)
	{
		int buf = value;
		vecu.sendsensorManually(ECUConnector.outLKASSwitch, (Object)buf);
	
	}
    public void sendPos_x1(int value)
    {
    	int buf = value;
    	vecu.sendsensorManually(ECUConnector.outPointSensor_PointA_x, (Object)buf);
    }
    public void sendPos_x2(int value)
    {
    	int buf = value;
    	vecu.sendsensorManually(ECUConnector.outPointSensor_PointA_y, (Object)buf);
    }
    public void sendPos_x3(int value)
    {
    	int buf = value;
    	vecu.sendsensorManually(ECUConnector.outPointSensor_PointB_x, (Object)buf);
    }
    public void sendPos_x4(int value)
    {
    	int buf = value;
    	vecu.sendsensorManually(ECUConnector.outPointSensor_PointB_y, (Object)buf);
    }
}