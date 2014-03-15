package g.basis;

import java.util.ArrayList;
import java.util.HashMap;

import g.button.GameButton;
import g.refer.BasicBody;
import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzInt;
import g.tool.OzPoint;
import g.tool.P;
import g.type.Status;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessorQueue;

public class GameView extends InputProcessorQueue implements ApplicationListener {
	
	private  HashMap<String, OzPoint> points;
	//GameStatus
	private GameButton gameBtn;
	private static   ArrayList<OzElement>  gateAtlas; //ÿһ���ؿ��ĵ�ͼ������
	private  ArrayList<OzInt> rankNum;   //��ͼ��ȼ���������ʾͼƬ
	private Status status;  //��ǰ����״̬
	public static Player player;
	@Override
	public void create() {	
		points = new HashMap<String, OzPoint>(); //������
		P.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //ͼƬ��Դ��ʼ��
		Gdx.input.setCatchBackKey(true); //����ϵͳ���յ�Back��
		Gdx.input.setInputProcessor(this); //���ô�������
		status = Status.Game;  //���õ�ǰ����״̬
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
		
		switch (status) {
		
		case Credits:  {        break;}
		
		case Game:     {    gameEngine();    break;}
			
		case Loading:  {        break;}
		
		case Pause:    {        break;}
		
		case Select:   {        break;}
		
		case Start:    {        break;}
		
		case Init:     {        break;}
		
		}
		
		
	}
	public void showGraphic(){
		P.begin();
		
		switch (status) {
		
		case Credits:  {        break;}
		
		case Game:     {    gameDraw();    break;}
			
		case Loading:  {        break;}
		
		case Pause:    {        break;}
		
		case Select:   {        break;}
		
		case Start:    {        break;}
		
		case Init:     {        break;}
		
		}
		
		P.end();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void gameInit(){
		gameBtn     = new GameButton();
		gateAtlas   = new ArrayList<OzElement>();
		rankNum     = new ArrayList<OzInt>();
		GameChapter.chapterLoad(gateAtlas, rankNum,1); 
		player =  new Player();
	}
	public void gameEngine(){
		
		player.resetOnGameLogic();
		
		//��ײ��
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).impact(player);
		}
		player.set_VerticalT_and_PlaneT(gateAtlas); //������ҵĴ�ֱ״̬��ˮƽ״ֵ̬
		boolean pushed = false;
		for(OzElement g:gateAtlas){
			if(g instanceof BasicBody){
				//����һص���ǽǰ��һ˲�������˵��Ҵ�ǽʵ������ǽ����ң���ȷ�������ǰ�ǽ��������������
				g.l.x = g.l.x + player.getPush_X();
				g.l.y = g.l.y - player.getPush_Y();
				pushed = true;
			}
		}
		if(Player.getL().y >= Player.limitDown && !pushed){
			player.pushBack_Y();
		}
//		
		//��ʱ��δ��ͼ
		
		//���״̬�ı��
		player.updateAction();
		
		//Ԫ���ƶ����߼���
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).engine();
		}
		
		player.engine();
	}
	public void gameDraw(){
		
		for(int i=0;i<rankNum.size();i++){
			for(int i2=0;i2<gateAtlas.size();i2++){
				if(rankNum.get(i).value == gateAtlas.get(i2).rankNum){
					gateAtlas.get(i2).draw();
				}
			}
		}
		player.draw();
		gameBtn.draw();
	}

}
