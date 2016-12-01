package emulator;

import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;



public class Frame_Steering extends JFrame implements GLEventListener{

	/**
	 * Create the panel.
	 */
	private GLU glu = new GLU();
	private float zrot=0;
	private int texture_steering;
	
	private Emulator parent;
	
	
	
	//현재 차량 맵 정보
	private Car currentcar;
	
	private double steer_value;
	
	public Frame_Steering(Emulator parent) {
		
		
		setResizable(false);
		setTitle("Steering");
		this.parent=parent;
		
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		
		GLCanvas glcanvas = new GLCanvas(capabilities);
		
		glcanvas.addGLEventListener(this);
		glcanvas.setSize(400, 400);
		
		FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
		animator.start();
		
		
		
		getContentPane().add(glcanvas);
		setSize(getContentPane().getPreferredSize());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		GroupLayout layout = new GroupLayout(getContentPane());
//		getContentPane().setLayout(layout);
		
		

	}

	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL2 gl = drawable.getGL().getGL2();
		
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		gl.glClearColor(1f, 1f, 1f, 1f); // Background Color
		
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		
		//steering
		gl.glLoadIdentity();
		gl.glTranslatef(0f, 0f, -6.0f);
		gl.glRotatef(zrot, 0.0f, 0.0f, -7.0f);
		gl.glScalef(0.7f, 0.7f, 0.7f);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture_steering);
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(1.0f, 0.0f);// 사각형 중 오른쪽 아래
		gl.glVertex3f(2.30f, -2.3f, 0.0f);
		gl.glTexCoord2f(1.0f, 1.0f);// 사각형 중 오른쪽 위
		gl.glVertex3f(2.30f, 2.3f, 0.0f);
		gl.glTexCoord2f(0.0f, 1.0f);// 사각형 중 왼쪽 위
		gl.glVertex3f(-2.5f, 2.3f, 0.0f);
		gl.glTexCoord2f(0.0f, 0.0f);// 사각형 중 왼쪽 아래
		gl.glVertex3f(-2.5f, -2.3f, 0.0f);
		gl.glEnd();
		
		gl.glFlush();
		
		
		currentcar=parent.getCar();
		steer_value=currentcar.getLastwheelcontrol()*10;
		
		//////// 회전각도
		double zrot2=-zrot;
		if (steer_value == zrot2) {

		} else if (steer_value > zrot2) {
			zrot -= 1.00f;
		} else if (steer_value < zrot2) {
			zrot += 1.00f;
		} 
		
	}

	@Override
	public void init(GLAutoDrawable gLDrawable) {
		GL2 gl = gLDrawable.getGL().getGL2();
		
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0, 0, 0, 0.5f);
		gl.glClearDepth(1);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glDepthFunc(GL.GL_LEQUAL);
		
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		
		gl.glEnable(GL2.GL_TEXTURE_2D);
		try {

			File im1 = new File("wheel.png");
			Texture t1 = TextureIO.newTexture(im1, true);
			texture_steering = t1.getTextureObject(gl);


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		GL2 gl = gLDrawable.getGL().getGL2();
		
		if(height <= 0)
			height = 1;
		
		float h=(float)width/(float)height;
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45f, h, 1, 20);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
	}
	


	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

}