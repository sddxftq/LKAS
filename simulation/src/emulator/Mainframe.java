package emulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import InterfaceandPreset.Interface_ECU;
import data.WorldInst;
import data.WorldMap;
import javax.swing.JLabel;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
public class Mainframe extends JFrame{
	
	
	
	private enum KEYACTION{
		ACCEL,BRAKE,LEFT,RIGHT,BLANK,WHEELBLANK, PAUSE;
	}
	
	private Emulator parent;
	
	//현재 차량 맵 정보
	private Car currentcar;
	private WorldMap currentmap;
	
	//메인부 구현
	private KeyListener keyListener;
	private MainCanvas mainview;
	private Panel_Bottom panel_btm;
	private Panel_Steering panel_steer;
	private Panel_Dashboard panel_dashboard;
	
	private Timer worldtimer;
	private int timercnt=0;
	private ActionListener timeraction;
	
	//템포구현용
	private KEYACTION nextAction=KEYACTION.BLANK;
	
	int temp;
	//임시
	// Variables declaration - do not modify                     
    /*private javax.swing.JLabel ScannedValues;
    private javax.swing.JLabel ValuePixel;
    private javax.swing.JLabel ValueSpeed;
    private javax.swing.JLabel ValueStartX;
    private javax.swing.JLabel ValueStartY;
    private javax.swing.JLabel ValueSteer;
    private javax.swing.JLabel XValue;
    private javax.swing.JLabel YValue;
    private javax.swing.JLabel asdfasdf;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;*/
    // End of variables declaration 
	
	//임시
	private Test_Dash dashboard;
	ImageIcon car_Image = new ImageIcon("Image/TempImage.jpg");
	ImageIcon road_Image = new ImageIcon("Image/Background.jpg");
	
	
	
	private final int ROADIMAGE_FRAMESIZE_WIDTH=700;
	private final int ROADIMAGE_FRAMESIZE_HEIGHT=700;
	private final int ROADIMAGE_CAR_POSX=350;
	private final int ROADIMAGE_CAR_POSY=350;
	private final int ROADIMAGE_CAR_WIDTH=30;
	private final int ROADIMAGE_CAR_HEIGHT=30;
	
	private int ROADIMAGE_HEIGHT=0;
	private int ROADIMAGE_WIDTH=0;
	
	double car_Speed;
	int car_View_Spot_X, car_View_Spot_Y;
	int LKASmode;
	int Direction;
	int road_View_Spot_X, road_View_Spot_Y;
	double double_Road_View_Spot_X;
	double double_Road_View_Spot_Y;
	int road_View_FrameSize_X, road_View_FrameSize_Y;
	double car_currentdirection;
	int car_currentdirection_Constant;
	int car_Speed_Constant;

	int car_Data_Spot_X;
	int car_Data_Spot_Y;
	double double_Car_Data_Spot_X;
	double double_Car_Data_Spot_Y;
	
	String FILENAME;
	
	BufferedImage fp_map01;
	BufferedImage Image_car;
	//World
/*	private Car currentcar;
	private WorldMap currentworld;
	private Interface_ECU currentECU;
	*/
	public Mainframe(Emulator parent, String filename){
		
		setResizable(false);
		setTitle("Simulator");
		this.parent=parent;
		currentcar=parent.getCar();
		
    	Color buf;
    	System.out.println(filename);
    	try {
			fp_map01=ImageIO.read(new File(filename));
			Image_car=ImageIO.read(new File("Image/car2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	ROADIMAGE_HEIGHT=fp_map01.getHeight();
    	ROADIMAGE_WIDTH=fp_map01.getWidth();
    	
    	
		JPanel view = new JPanel() {
			public void paint(Graphics g) {
				int x;
				int y;
				
				
	
				setOpaque(false);
				g.drawImage(fp_map01, 0, 0, ROADIMAGE_FRAMESIZE_WIDTH, ROADIMAGE_FRAMESIZE_HEIGHT,
						road_View_Spot_X, road_View_Spot_Y,	road_View_Spot_X+ROADIMAGE_FRAMESIZE_WIDTH, road_View_Spot_Y+ROADIMAGE_FRAMESIZE_HEIGHT, this);
				setOpaque(false);
				BufferedImage rotated=new BufferedImage(Image_car.getWidth(),Image_car.getHeight(),Image_car.getType());
				Graphics2D grp=(Graphics2D)rotated.getGraphics();
				
				grp.rotate(Math.toRadians(-1*(currentcar.getCurrentdirection()-90)),Image_car.getWidth()/2,Image_car.getHeight()/2);
				//System.out.println("555555555555");
				
				//grp.translate((rotated.getWidth()-Image_car.getWidth())/2,(rotated.getHeight()-Image_car.getHeight())/2);
				grp.drawImage(Image_car, 0, 0, Image_car.getWidth(), Image_car.getHeight(), null);
				
				
				
				g.drawImage(rotated, ROADIMAGE_CAR_POSX-Image_car.getWidth()/2, ROADIMAGE_CAR_POSY-Image_car.getHeight()/2, this);
				setOpaque(false);
				
				
				double i1, point1x, point1y;
		    	double i2, point2x, point2y;
		    	double i3, point3x, point3y;
		    	double i4, point4x, point4y;
		    	
		    	/*
		    	
		    	point1x=(int) parent.point1x;
		    	point1y=(int) parent.point1y;
		    	
		 
		    	point2x=(int) parent.point2x;
		    	point2y=(int) parent.point2y;
		    	
		    	
		    	point3x=(int) parent.point3x;
		    	point3y=(int) parent.point3y;
		    	
		    	
		    	point4x=(int) parent.point4x;
		    	point4y=(int) parent.point4y;
		    	
		    	g.setColor(Color.RED);
				g.fillOval(point1x-2, point1y-2, 4, 4);
				g.setColor(Color.YELLOW);
				g.fillOval(point2x-2, point2y-2, 4, 4);
				g.setColor(Color.GREEN);
				g.fillOval(point3x-2, point3y-2, 4, 4);
				g.setColor(Color.BLUE);
				g.fillOval(point4x-2, point4y-2, 4, 4);
				
				g.setColor(Color.BLACK);
				g.fillOval((int) currentcar.getCurrentpositionx()-2, (int) currentcar.getCurrentpositiony()-2, 4, 4);
				
				*/
		    	
		    	
		    	i1=currentcar.getLanesenseLeft(50);
		    	i2=currentcar.getLanesenseLeft(0);
		    	i3=currentcar.getLanesenseRight(50);
		    	i4=currentcar.getLanesenseRight(0);
		    	
				x=ROADIMAGE_CAR_POSX; //-Image_car.getWidth()/2;
				y=ROADIMAGE_CAR_POSY;//-Image_car.getHeight()/2;
				
				double ActualAngle;
				ActualAngle=Math.toRadians(currentcar.getCurrentdirection());
				point1x = x+50*Math.cos(ActualAngle) - i1*Math.sin(ActualAngle);
				point1y = y-( 50*Math.sin(ActualAngle) + i1*Math.cos(ActualAngle) );
				
				
				point2x = x+Math.cos(ActualAngle) - i2*Math.sin(ActualAngle);
				point2y = y-( Math.sin(ActualAngle) + i2*Math.cos(ActualAngle) );
				
				point3x = x+50*Math.cos(ActualAngle) + i3*Math.sin(ActualAngle);
				point3y = y-( 50*Math.sin(ActualAngle) - i3*Math.cos(ActualAngle) );
				
				point4x = x+Math.cos(ActualAngle) + i4*Math.sin(ActualAngle);
				point4y = y-( Math.sin(ActualAngle) - i4*Math.cos(ActualAngle) );
				
//				System.out.println("p1:"+point1x+","+point1y);
//				System.out.println("p2:"+point2x+","+point2y);
//				System.out.println("p3:"+point3x+","+point3y);
//				System.out.println("p4:"+point4x+","+point4y);
//				
				
				
				g.setColor(Color.RED);
				g.fillOval((int)point1x-2, (int)point1y-2, 4, 4);
				g.setColor(Color.YELLOW);
				g.fillOval((int)point2x-2, (int)point2y-2, 4, 4);
				g.setColor(Color.GREEN);
				g.fillOval((int)point3x-2, (int)point3y-2, 4, 4);
				g.setColor(Color.BLUE);
				g.fillOval((int)point4x-2, (int)point4y-2, 4, 4);
				
				g.setColor(Color.BLACK);
				g.fillOval((int) x-2, (int) y-2, 4, 4);

			}

		};
		
		this.add(view);
		this.pack();
		this.setVisible(true);
		this.setSize(ROADIMAGE_FRAMESIZE_WIDTH, ROADIMAGE_FRAMESIZE_HEIGHT);
		init();
		

	}
	
	
	private void init() {
		dashboard =	new Test_Dash(this);
		dashboard.init();
		
		Direction=1;
		
		car_Speed = 0;
		LKASmode = 0;
		car_View_Spot_Y = 250;
		car_View_Spot_X = 250;
		car_Data_Spot_X = 250;
		car_Data_Spot_Y = 250;
		double_Car_Data_Spot_X = 250;
		double_Car_Data_Spot_Y = 250;
		road_View_Spot_X = 200;	
		road_View_Spot_Y = 200;
		double_Road_View_Spot_X = 200;
		double_Road_View_Spot_Y = 200;
		road_View_FrameSize_X = 900;
		road_View_FrameSize_Y = 700;
		car_currentdirection = 90;
		car_currentdirection_Constant = 5;
		car_Speed_Constant = -2;
		
        //타이밍 액션
        timeraction=new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//1프레임 진행 및 뷰
				parent.Update();
				road_View_Spot_X=(int)currentcar.getCurrentpositionx()-ROADIMAGE_FRAMESIZE_WIDTH/2;
				road_View_Spot_Y=(ROADIMAGE_HEIGHT-(int)currentcar.getCurrentpositiony())-ROADIMAGE_CAR_POSY;
				repaint();
				//1프레임마다 처리되는 내용을 규정한다.
				
				//1프레임 키입력처리
				if(nextAction==KEYACTION.ACCEL){
					parent.accelerate(0.02);
				}else if(nextAction==KEYACTION.BRAKE){
					parent.brake(0.02);
				}else if(nextAction==KEYACTION.LEFT){
					parent.steer_left(2.0);
				}else if(nextAction==KEYACTION.RIGHT){
					parent.steer_right(2.0);
				}else if(nextAction==KEYACTION.WHEELBLANK){
					parent.steer_right(0.0);
				}else if(nextAction==KEYACTION.PAUSE){
					parent.pause();
				}
				nextAction=KEYACTION.BLANK;
			}
        	
        };
        worldtimer=new Timer(20, timeraction);
        worldtimer.start();
        
		
		//키보드 입력 액션지정
		keyListener = new KeyListener(){
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.getKeyCode()== KeyEvent.VK_UP) {
					nextAction=KEYACTION.ACCEL;
					
					
					//car_Speed += car_Speed_Constant;
				}
				if (event.getKeyCode()== KeyEvent.VK_DOWN) {
					nextAction=KEYACTION.BRAKE;
					car_Speed += -car_Speed_Constant;
				}	
				if (event.getKeyCode()== KeyEvent.VK_RIGHT) {
					nextAction=KEYACTION.RIGHT;
					car_currentdirection += car_currentdirection_Constant;
				}
				if (event.getKeyCode()== KeyEvent.VK_LEFT) {
					nextAction=KEYACTION.LEFT;
					car_currentdirection += -car_currentdirection_Constant;
				}
				if (event.getKeyCode()== KeyEvent.VK_I) {
					System.exit(0);
				}
				if (event.getKeyCode()== KeyEvent.VK_A) {
				
						LKASmode=1;
						temp=1;
					System.out.println("LKASmode State : " +LKASmode);

				}
				if (event.getKeyCode()== KeyEvent.VK_Q) {
					temp=2;
					LKASmode=0;

				}
				if (event.getKeyCode()== KeyEvent.VK_S) {
					Direction = 0;
				}
				if (event.getKeyCode()== KeyEvent.VK_D) {
					Direction = 1;
				}
				if (event.getKeyCode()== KeyEvent.VK_F) {
					Direction = 2;
				}
				if (event.getKeyCode()== KeyEvent.VK_B) {
					//System.out.println("b");
					nextAction=KEYACTION.PAUSE;
				}
				
			}
			@Override public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_LEFT || arg0.getKeyCode()==KeyEvent.VK_RIGHT){
					nextAction=KEYACTION.WHEELBLANK;
				}
			}
			@Override public void keyTyped(KeyEvent arg0) {		}
		};
		
		this.addKeyListener(keyListener);
		
	
	}

	//World Showing Interface
	/*
	public void showWorld(WorldMap worldmap, Car car, int lampa, int lampb){
		mainview.showWorld(worldmap, car);
	}
	*/
	//Bumped!
	public void Bumped(){
		
	}
	
	//Start and stop
	public void Startmoving(){
	}
	
	public void Stopmoving(){
	}

	public double getSpd(){
		//System.out.println("speed:"+currentcar.getSpd()*100*(5/8));
	      return -currentcar.getSpd()*100;
	      
	}


}