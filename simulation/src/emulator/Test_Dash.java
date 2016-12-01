package emulator;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test_Dash extends JFrame implements Runnable{
   ImageIcon car_Image = new ImageIcon("Image/speed_dash.jpg");
   ImageIcon LKAS_ON = new ImageIcon("Image/lkasOn.jpg");
   ImageIcon LKAS_OFF = new ImageIcon("Image/lkasOff.jpg");
   ImageIcon LKAS_SUSPEND = new ImageIcon("Image/lkasSuspend.jpg");
   ImageIcon image_Turn_Signal_Off = new ImageIcon("Image/TurnOff.jpg");
   ImageIcon image_Turn_Signal_Right = new ImageIcon("Image/TurnRight.jpg");
   ImageIcon image_Turn_Signal_Left = new ImageIcon("Image/TurnLeft.jpg");
   int car_View_Spot_X, car_View_Spot_Y;
   private Mainframe parent;
   int lkas_switch;
   int turn_Signal;

   int speed_Arrow_CENTER_X, speed_Arrow_CENTER_Y;
   int speed_Arrow_Width;
   double speed_Arrow_X, speed_Arrow_Y;
   //int [] x={1,10,speed_Arrow_CENTER_X, speed_Arrow_CENTER_X + speed_Arrow_Width};
   //int [] y={1,1,speed_Arrow_CENTER_Y, speed_Arrow_CENTER_Y};

   public Test_Dash(Mainframe parent) {
      this.parent=parent;
      //lkas_switch=0;
      turn_Signal=1;
      // TODO Auto-generated constructor stub
      JPanel view = new JPanel() {
         public void paint(Graphics g) {
   
         g.drawImage(car_Image.getImage(), 110, 0, this);
            setOpaque(false);
            if(lkas_switch ==0)
            //   g.drawImage(LKAS_OFF.getImage(), 0, 300, this);
               g.drawImage(LKAS_OFF.getImage(), 250, 90, this);
         else if(lkas_switch ==1)
               g.drawImage(LKAS_ON.getImage(), 250, 90, this);
            else if(lkas_switch ==2)
               g.drawImage(LKAS_SUSPEND.getImage(), 250, 90, this);
            setOpaque(false);
            if(turn_Signal ==1){
               g.drawImage(image_Turn_Signal_Off.getImage(), 0, 260, this);
               setOpaque(false);
               g.drawImage(image_Turn_Signal_Off.getImage(), 500, 260, this);
               setOpaque(false);
               }else if ( turn_Signal ==0){
                  g.drawImage(image_Turn_Signal_Left.getImage(), 0, 260, this);
                  setOpaque(false);
                  g.drawImage(image_Turn_Signal_Off.getImage(), 500, 260, this);
                  setOpaque(false);
                  
               }else if ( turn_Signal ==2){
                  g.drawImage(image_Turn_Signal_Off.getImage(), 0, 260, this);
                  setOpaque(false);
                  g.drawImage(image_Turn_Signal_Right.getImage(), 500, 260, this);
                  setOpaque(false);
            
         }
            g.setColor(Color.red); 
           g.fillArc(215, 85, 200, 250,(int)speed_Arrow_Y, 5);
           // -40 = 120 ¼Óµµ 
           //System.out.println(speed_Arrow_Y);

         }

      };
         car_View_Spot_Y = 10;
      car_View_Spot_X = 20;
      view.setSize(500, 500);

      this.add(view);
      this.pack();
      this.setVisible(true);
      this.setSize(650, 400);
      Thread t = new Thread(this);
      t.start();
      
   }
   
   void init() {   

      
      speed_Arrow_X= 50;
      speed_Arrow_Y=200;
      speed_Arrow_CENTER_X = 150;
      speed_Arrow_CENTER_Y = 150;
      speed_Arrow_Width = 10;

      
   }
   
   
   @Override
   public void run() {
      // TODO Auto-generated method stub
      while (true) {
         lkas_switch = parent.LKASmode;
      //   System.out.println("lkas_switch State : " +lkas_switch);
         speed_Arrow_Y = 215 + (parent.getSpd()/2)*4.5;
        turn_Signal=parent.Direction;

         try {
            this.repaint();
            Thread.sleep(30);
         } catch (Exception e) {}
      }
   }
   

}