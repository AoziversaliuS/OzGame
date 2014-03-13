package g.basis;

import java.util.HashMap;

import g.button.GameButton;
import g.tool.OzPoint;
import g.tool.P;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessorQueue;

public class GameView extends InputProcessorQueue implements ApplicationListener {
	
	private  HashMap<String, OzPoint> points;
	//GameStatus
	private GameButton gameBtn;
	
	@Override
	public void create() {	
		points = new HashMap<String, OzPoint>();
		P.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //图片资源初始化
		Gdx.input.setCatchBackKey(true); //不让系统接收到Back键
		Gdx.input.setInputProcessor(this); //设置触屏监听
		this.gameInit();
		
	}
	@Override
	public synchronized boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public synchronized boolean touchDown(int screenX, int screenY,int pointer, int button) {
		
		points.put(""+pointer, new OzPoint(screenX, screenY, true));
		gameBtn.logic(points);
		Gdx.app.log("Interact", "touchDown  有"+points.size()+"个点! "+"   "+pointer);
		return false;
	}
	@Override
	public synchronized boolean touchUp(int screenX, int screenY, int pointer,
			int button) {
		
		points.remove(""+pointer);
		gameBtn.logic(points);
		Gdx.app.log("Interact", "touchUp 有"+points.size()+"个点! "+"   "+pointer);
		return false;
	}
	@Override
	public synchronized boolean touchDragged(int screenX, int screenY,
			int pointer) {
		
		points.get(""+pointer).set_XY_fromScreen(screenX, screenY);
		gameBtn.logic(points);
		Gdx.app.log("Interact", "touchDragged 有"+points.size()+"个点! "+"   "+pointer);
		return false;
	}
	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	public void dispose() {
		P.dispose();
	}


	@Override
	public void render() {	
		Gdx.app.log("FPS", " FPS:  "+Gdx.graphics.getFramesPerSecond());
		
		engine();
		showGraphic();
	}
	public void engine(){
		gameEngine();
		
	}
	public void showGraphic(){
		P.begin();
		P.draw(0, 0, P.backGround.getSprite());
		gameDraw();
		P.end();
	}
	public void gameInit(){
		gameBtn = new GameButton();
	}
	public void gameEngine(){
		
	}
	public void gameDraw(){
		gameBtn.draw();
	}

}
