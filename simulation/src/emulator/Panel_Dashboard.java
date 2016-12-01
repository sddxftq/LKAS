package emulator;

import javax.swing.JPanel;

public class Panel_Dashboard extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private Panel_SpeedMonitor panel_speedmonitor;
	private Panel_SystemLamp panel_systemlamp;
	
	public Panel_Dashboard() {
		panel_speedmonitor=new Panel_SpeedMonitor();
		panel_systemlamp=new Panel_SystemLamp();
	}
	
	public void show(int value, int a, int b){
		panel_speedmonitor.show(value);
		panel_systemlamp.show(a,b);
	}

}
