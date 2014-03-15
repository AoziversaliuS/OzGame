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
	private static   ArrayList<OzElement>  gateAtlas; //每一个关卡的地图集序列
	private  ArrayList<OzInt> rankNum;   //按图层等级来进行显示图片
	private Status status;  //当前界面状态
	public static Player player;
	@Override
	public void create() {	
		points = new HashMap<String, OzPoint>(); //触摸点
		P.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //图片资源初始化
		Gdx.input.setCatchBackKey(true); //不让系统接收到Back键
		Gdx.input.setInputProcessor(this); //设置触屏监听
		status = Status.Game;  //设置当前界面状态
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
		
		//碰撞↓
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).impact(player);
		}
		player.set_VerticalT_and_PlaneT(gateAtlas); //设置玩家的垂直状态和水平状态值
		boolean pushed = false;
		for(OzElement g:gateAtlas){
			if(g instanceof BasicBody){
				//让玩家回到穿墙前的一瞬，相对来说玩家穿墙实际上是墙穿玩家，正确的做法是把墙从玩家身边拉开。
				g.l.x = g.l.x + player.getPush_X();
				g.l.y = g.l.y - player.getPush_Y();
				pushed = true;
			}
		}
		if(Player.getL().y >= Player.limitDown && !pushed){
			player.pushBack_Y();
		}
//		
		//此时还未绘图
		
		//玩家状态改变↓
		player.updateAction();
		
		//元素移动等逻辑↓
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
