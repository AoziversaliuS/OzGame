package g.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class P {
	private static float screenW;
	private static float screenH;
	private static SpriteBatch batch;
	
	public static void begin(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
	}
	public static void draw(float x,float y,Sprite sprite){
		sprite.setPosition(x, y);
		sprite.draw(batch);
	}
	public static void end(){
		batch.end();
	}
	
	
	
	
	
	
	
	
	
	
	
	public static float getScreenW() {
		return screenW;
	}
	public static void setScreenW(float screenW) {
		P.screenW = screenW;
	}
	public static float getScreenH() {
		return screenH;
	}
	public static void setScreenH(float screenH) {
		P.screenH = screenH;
	}
}
