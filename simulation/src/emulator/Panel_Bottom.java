package emulator;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel_Bottom extends JPanel {

	/**
	 * Create the panel.
	 */
	
	public JButton btn = new JButton("����");
	public JButton btn1 = new JButton("����");
	
	private Mainframe parent;
	
	//Action Listener
	private ActionListener al;
	
	public Panel_Bottom(Mainframe parent) {
		//this.add(btn);
		this.parent=parent;
	}

}
