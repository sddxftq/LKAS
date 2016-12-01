package emulator;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Dialog_Input extends JDialog implements ActionListener{
	private JLabel jlabel;
	private JRadioButton road1;
	private JRadioButton road2;
	private JRadioButton road3;
	private ButtonGroup buttonGroup;
	private JButton start;
	int mapmode=0;
	
	public Dialog_Input(){
		setModal(true);
		setResizable(false);
		setTitle("Input");
		this.getContentPane().setLayout(new FlowLayout());
		
		road1 = new JRadioButton("직선 도로");
		road1.setActionCommand("road1");
		//road1.addActionListener(this);
		
		
		road2 = new JRadioButton("곡선 도로");
		road2.setActionCommand("road2");
		//road2.addActionListener(this);
		
		road3 = new JRadioButton("더 심한 곡선 도로");
		road3.setActionCommand("road3");
//		road3.addActionListener(this);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(road1);
		buttonGroup.add(road2);
		buttonGroup.add(road3);
		
		start = new JButton("Start");
		start.addActionListener(this);
		
		
		add(road1);
		add(road2);
		add(road3);
		add(start);

		
		pack();
		
		setSize(400, 100);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		
		ButtonModel b=buttonGroup.getSelection();
		if( b.getActionCommand().equals("road1") ){
			mapmode=1;
		}
		else if( b.getActionCommand().equals("road2") ){
			mapmode=2;
		}
		else{
			mapmode=3;
		}
		dispose();
		
	}
	
}
