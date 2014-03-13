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
		P.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //ͼƬ��Դ��ʼ��
		Gdx.input.setCatchBackKey(true); //����ϵͳ���յ�Back��
		Gdx.input.setInputProcessor(this); //���ô�������
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
		Gdx.app.log("Interact", "touchDown  ��"+points.size()+"����! "+"   "+pointer);
		return false;
	}
	@Override
	public synchronized boolean touchUp(int screenX, int screenY, int pointer,
			int button) {
		
		points.remove(""+pointer);
		gameBtn.logic(points);
		Gdx.app.log("Interact", "touchUp ��"+points.size()+"����! "+"   "+pointer);
		return false;
	}
	@Override
	public synchronized boolean touchDragged(int screenX, int screenY,
			int pointer) {
		
		points.get(""+pointer).set_XY_fromScreen(screenX, screenY);
		gameBtn.logic(points);
		Gdx.app.log("Interact", "touchDragged ��"+points.size()+"����! "+"   "+pointer);
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
