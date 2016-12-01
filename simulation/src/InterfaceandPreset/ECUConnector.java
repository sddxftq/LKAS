package InterfaceandPreset;

import com.etas.vrta.comms.VECUConnection;
import com.etas.vrta.comms.VECUFinder;

public class ECUConnector {
	//Ä¿³Ø¼Ç ¼³Á¤
	private static final String HOST="127.0.0.1";
	static final String ecu_path="C:\\ETASData\\ISOLAR-EVE3.0\\workspace\\A_Test3\\VECUs\\MinGW\\Bin\\A_Test3.exe";
	
	//Ä¿³ØÅÍ ÀÌ¸§ ÁöÁ¤
	//---Sensor---
	public static final String outLKASSwitch="LKASSwitch.Value";
	public static final String outRadiusSensor="RadiusSensor.Value";
	public static final String outSpeedSensor="SpeedSensor.Value";
	public static final String outTurnSignalSwitch="TurnSignalSwitch.Value";
	public static final String outPointSensor_PointA_x="PointSensor_PointA_x.Value";
	public static final String outPointSensor_PointA_y="PointSensor_PointA_y.Value";
	public static final String outPointSensor_PointB_x="PointSensor_PointB_x.Value";
	public static final String outPointSensor_PointB_y="PointSensor_PointB_y.Value";
	//---Actuator---
	public static final String inSoundActuator="SoundActuator.Value";
	public static final String inLEDActuator="LEDActuator.Value";
	public static final String inWheelActuator="WheelActuator.Value";
	
	//Ä¿³Ø¼Ç
	private VECUConnection m_connection;
	
	public ECUConnector(){
		Init();
	}
	
	private void Init(){
		m_connection=VECUFinder.attach(HOST, ecu_path);
		m_connection.start();
	}
	
	public void sendsensorManually(String sensorName, Object value){
		m_connection.action(sensorName).send(value);
	}
	
	public Object recvdataManually(String recvName){
		return m_connection.event(recvName).state();
	}
}

