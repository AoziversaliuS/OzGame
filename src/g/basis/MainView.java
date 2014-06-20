package g.basis;

import java.util.ArrayList;
import java.util.HashMap;

import g.button.GameButtons;
import g.button.PauseButtons;
import g.button.SelectButtons;
import g.button.StartButtons;
import g.refer.BasicBody;
import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzInt;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;
import g.type.Status;
import g.view.GameView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessorQueue;

public class MainView extends InputProcessorQueue implements ApplicationListener {
	
	private boolean showFPS = false;
	
	private boolean debug = false;
	private long times = 50;//debug用，debug为true时使游戏运行缓慢，能看清每一帧
	
	private  HashMap<String, OzPoint> points;
	//SelectStatus
	private SelectButtons selectBtns;
	//StartStatus
	private StartButtons startBtns;
	//PauseStatus
	private PauseButtons pauseBtns;
	//GameStatus
	private GameView gameView;
	
	private static Status status;  //当前界面状态
    private static Status toStatus;
	private float lightNum = 0f;
	
	private final int SWITCH_PREPARE=1,SWITCH_LOADING=2,SWITCH_LOADED=3,SWITCH_FINISH=4;
	private int sT = SWITCH_PREPARE;
	

	
	
	private final float dNum = 0.05f;
	private final float maxLight = 0.98f;
	private final float minLight = 0.01f;
	
	@Override
	public void create() {	
		
		P.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //画图工具类初始化
		Res.init();//资源管理器初始化。
		
		
		points = new HashMap<String, OzPoint>(); //触摸点
		

		
		Gdx.input.setCatchBackKey(true); //不让系统接收到Back键
		Gdx.input.setInputProcessor(this); //设置触屏监听
		
		status = Status.Start;  //设置当前界面状态
		toStatus = Status.Start;
		
		startInit();//开始菜单初始化
//		this.gameInit();//游戏界面初始化
		gameView = new GameView();
		this.selectInit();//选择菜单初始化
		this.pauseInit();//暂停菜单初始化
		
//		Res.prepare(Res.resourceA);
//		while( !Res.update() ){
//		}
//		System.out.println("更新状态"+Res.update());
	}
	@Override
	public synchronized boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public synchronized boolean touchDown(int screenX, int screenY,int pointer, int button) {
		
		points.put(""+pointer, new OzPoint(screenX, screenY, true));
		btnLogic();
//		Gdx.app.log("Interact", "touchDown  有"+points.size()+"个点! "+"   "+pointer);
		return false;
	}
	@Override
	public synchronized boolean touchUp(int screenX, int screenY, int pointer,
			int button) {
		
		points.remove(""+pointer);
		btnLogic();
//		Gdx.app.log("Interact", "touchUp 有"+points.size()+"个点! "+"   "+pointer);
		return false;
	}
	@Override
	public synchronized boolean touchDragged(int screenX, int screenY,
			int pointer) {
		
		points.get(""+pointer).set_XY_fromScreen(screenX, screenY);
		btnLogic();
//		Gdx.app.log("Interact", "touchDragged 有"+points.size()+"个点! "+"   "+pointer+" L: "+screenX+" "+screenY);
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
		P.begin();
		if(showFPS){
			Gdx.app.log("FPS", " FPS:  "+Gdx.graphics.getFramesPerSecond());
		}
		if( toStatus==status ){
			engine();
			showGraphic();
		}
		else{
			statusSwitch();
		}
		
		P.setlight(lightNum);
		P.end();
		
		if(debug){
			try {
				Thread.sleep(times);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void statusSwitch(){

		if( toStatus==Status.Select && status==Status.Start ){
			this.startToSelect();
		}
		else if( toStatus==Status.Game && status==Status.Select ){
			this.selectToGame();
		}
		if( toStatus==Status.Pause && status==Status.Game ){
			gameToPause();
		}
		if( toStatus==Status.Game && status==Status.Pause ){
			pauseToGame();
		}
		
		if( sT==SWITCH_FINISH ){
			status = toStatus;
			//重置信息
			lightNum = 0;
			sT=SWITCH_PREPARE;
		}
	}
	private void pauseToGame(){
		if( sT==SWITCH_PREPARE ){
			lightNum = maxLight/2;
//			gameDraw();
			gameView.draw();
			sT = SWITCH_LOADING;
		}
		else if( sT==SWITCH_LOADING ){
//			gameDraw();
			gameView.draw();
			sT = SWITCH_LOADED;
		}
		else if( sT==SWITCH_LOADED ){
			lightNum = lightNum-dNum;
//			gameDraw();
			gameView.draw();
			if(lightNum<=minLight){
				sT = SWITCH_FINISH;
			}
		}
	}
	private void gameToPause(){
		if( sT==SWITCH_PREPARE ){
			lightNum = lightNum + dNum;
//			gameDraw();
			gameView.draw();
			if( lightNum>=maxLight/2 ){
				sT = SWITCH_LOADING;
				Res.prepare(Res.PAUSE_SOURCE);
			}
		}
		else if( sT==SWITCH_LOADING ){
//				gameDraw();
				gameView.draw();
				if(Res.update()){
					sT = SWITCH_LOADED;
				}
		}
		else if( sT==SWITCH_LOADED ){
			lightNum = 0;
			pauseDraw();
			sT = SWITCH_FINISH;
		}
	}
	private void startToSelect(){
		if( sT==SWITCH_PREPARE ){
			lightNum = lightNum + dNum;
			startDraw();
			if( lightNum>=maxLight ){
				sT = SWITCH_LOADING;
			}
		}
		else if( sT==SWITCH_LOADING ){
				sT = SWITCH_LOADED;
		}
		else if( sT==SWITCH_LOADED ){
			lightNum = lightNum - dNum;
			selectDraw();
			if( lightNum<=minLight ){
				sT = SWITCH_FINISH;
			}
		}
	}
	private void selectToGame(){
		if( sT==SWITCH_PREPARE ){
			lightNum = lightNum + dNum;
			selectDraw();
			if( lightNum>=maxLight ){
				sT = SWITCH_LOADING;
				Res.prepare(Res.GAME_A);
			}
		}
		else if( sT==SWITCH_LOADING ){
			selectDraw();
			if(Res.update()){
				//加载完图片之后载入地图
				GameChapter.chapterLoad(
						gameView.getGateAtlas(),
						gameView.getRankNum(),
						SelectButtons.getChapterId()
						); 
				sT = SWITCH_LOADED;
			}
		}
		else if( sT==SWITCH_LOADED ){
			lightNum = lightNum - dNum;
//			gameDraw();
			gameView.draw();
			if( lightNum<=minLight ){
				sT = SWITCH_FINISH;
			}
		}
	}
	
	public void engine(){
		
		switch (status) {
		
		case Credits:  {       break;}
		
		case Game:     {   gameView.engine();    break;}
			
		case Loading:  {        break;}
		
		case Pause:    {        break;}
		
		case Select:   {    selectEngine();   break;}
		
		case Start:    {    startEngine();     break;}
		
		case Init:     {        break;}
		
		}
		
		
	}
	
	public void showGraphic(){
		
		
		switch (status) {
		
		case Credits:  {        break;}
		
		case Game:     {    gameView.draw();    break;}
			
		case Loading:  {        break;}
		
		case Pause:    {    pauseDraw();    break;}
		
		case Select:   {    selectDraw();   break;}
		
		case Start:    {    startDraw();   break;}
		
		case Init:     {        break;}
		
		}
		
		
		
	}
	
	public void btnLogic(){
		
		switch (status) {
				
		case Credits:  {        break;}
				
		case Game:     {    gameView.btnLogic(points);     break;}
					
		case Loading:  {        break;}
				
		case Pause:    {    pauseBtns.logic(points);    break;}
				
		case Select:   {    selectBtns.logic(points);   break;}
				
		case Start:    {     startBtns.logic(points);   break;}
				
		case Init:     {        break;}
		
		}
	}
	
	
	public void startInit(){
		startBtns = new StartButtons();
	}
	public void startEngine(){
		
	}
	public void startDraw(){
//		P.drawForce(0, 0, Res.startBg);
		P.draw(0, 0, Res.startBg, P.FORCE_RATIO);
		startBtns.draw();
	}
	
	
	public void selectInit(){
		selectBtns = new SelectButtons();
	}
	public void selectEngine(){
		selectBtns.logic();
	}
	public void selectDraw(){
//		P.drawForce(0, 0, Res.selectBg);
		P.draw(0, 0, Res.selectBg, P.FORCE_RATIO);
		selectBtns.draw();
	}
	
	public void pauseInit(){
		pauseBtns = new PauseButtons();
	}
	public void pauseEngine(){
		
	}
	public void pauseDraw(){
//		gameDraw();
		gameView.draw();
		P.setlight(maxLight/2);//暂停按钮绘画前设置亮度
		pauseBtns.draw();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static boolean switchFinished(){
		if( status==toStatus && status==Status.Game ){
			return true;
		}
		return false;
	}
	
	public static void setToStatus(Status toStatus) {
		MainView.toStatus = toStatus;
	}

}
