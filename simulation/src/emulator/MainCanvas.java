/**
 * 
 */
package emulator;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import data.WorldMap;

/**
 * @author kimhy
 *
 */
public class MainCanvas extends JPanel{
	
	private class RoadImageLoader extends Component{
		BufferedImage buf;
		public void paint(Graphics g){
			g.drawImage(buf, 0, 0, null);
		}
		public RoadImageLoader(BufferedImage buf) {
			super();
			this.buf = buf;
		}
	}
	
	private int carposx,carposy,cardir;
	
	//Interface for MainFrame
	public void showWorld(WorldMap worldmap, Car car){
	}
	
	public MainCanvas() {
		init();
	}

	private void init() {
		//GL Unit add
		//this.add(glcanvas);
	}
}
