package g.basis;

import java.util.ArrayList;
import java.util.HashMap;

import g.button.GameButtons;
import g.button.PauseButtons;
import g.button.SelectButtons;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;
import g.type.Status;
import g.view.GameView;
import g.view.PauseView;
import g.view.SelectView;
import g.view.StartView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessorQueue;

public class MainView extends InputProcessorQueue implements ApplicationListener {
	
	private boolean showFPS = false;
	
	private boolean debug = false;
	private long times = 50;//debug用，debug为true时使游戏运行缓慢，能看清每一帧
	
	private  HashMap<String, OzPoint> points;
	
	//SelectStatus
	private SelectView selectView;
	//StartStatus
	private StartView startView;
	//PauseStatus
	private PauseView pauseView;
	//GameStatus
	private GameView gameView;
	
	private static Status status;  //当前界面状态
    private static Status toStatus;
	
    public static final int SWITCH_PREPARE=1,SWITCH_LOADING=2,SWITCH_LOADED=3,SWITCH_FINISH=4;
	
	public static int switchType = SWITCH_PREPARE;
	

	
	
	@Override
	public void create() {	
		
		P.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //画图工具类初始化
		Res.init();//资源管理器初始化。
		
		points = new HashMap<String, OzPoint>(); //触摸点
		
		Gdx.input.setCatchBackKey(true); //不让系统接收到Back键
		Gdx.input.setInputProcessor(this); //设置触屏监听
		
		status = Status.Start;  //设置当前界面状态
		toStatus = Status.Start;
		
		//开始菜单初始化
		startView = new StartView();
        //游戏界面初始化
		gameView = new GameView();
		//选择菜单初始化
		selectView = new SelectView();
		//暂停菜单初始化
		pauseView = new PauseView();
		
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
		
//		P.setlight(lightNum);
		P.useDarkness();
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
			gameView.toPauseView(pauseView);
		}
		if( toStatus==Status.Game && status==Status.Pause ){
//			pauseToGame();
			pauseView.toGameView(gameView);
		}
		
		if( switchType==SWITCH_FINISH ){
			status = toStatus;
			//重置信息
//			lightNum = 0;
			P.setDarkness(0);
			switchType=SWITCH_PREPARE;
		}
	}
//	private void pauseToGame(){
//		if( switchType==SWITCH_PREPARE ){
////			lightNum = maxLight/2;
//			P.setDarkness(P.MAX_BLACK_NUM/2);
////			gameDraw();
//			gameView.draw();
//			switchType = SWITCH_LOADING;
//		}
//		else if( switchType==SWITCH_LOADING ){
////			gameDraw();
//			gameView.draw();
//			switchType = SWITCH_LOADED;
//		}
//		else if( switchType==SWITCH_LOADED ){
////			lightNum = lightNum-dNum;
//			P.decreaseDarkness();
////			gameDraw();
//			gameView.draw();
//			if(P.getBlackNum()<=P.MIN_BLACK_NUM){
//				switchType = SWITCH_FINISH;
//			}
//		}
//	}
//	private void gameToPause(){
//		if( switchType==SWITCH_PREPARE ){
////			lightNum = lightNum + dNum;
//			P.increaseDarkness();
////			gameDraw();
//			gameView.draw();
//			if( P.getBlackNum()>=P.MAX_BLACK_NUM/2 ){
//				switchType = SWITCH_LOADING;
//				Res.prepare(Res.PAUSE_SOURCE);
//			}
//		}
//		else if( switchType==SWITCH_LOADING ){
////				gameDraw();
//				gameView.draw();
//				if(Res.update()){
//					switchType = SWITCH_LOADED;
//				}
//		}
//		else if( switchType==SWITCH_LOADED ){
////			lightNum = 0;
//			P.setDarkness(0);
////			pauseDraw();
//			pauseView.draw(gameView);
//			switchType = SWITCH_FINISH;
//		}
//	}
	private void startToSelect(){
		if( switchType==SWITCH_PREPARE ){
//			lightNum = lightNum + dNum;
			P.increaseDarkness();
//			startDraw();
			startView.draw();
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
			}
		}
		else if( switchType==SWITCH_LOADING ){
				switchType = SWITCH_LOADED;
		}
		else if( switchType==SWITCH_LOADED ){
//			lightNum = lightNum - dNum;
			P.decreaseDarkness();
//			selectDraw();
			selectView.draw();
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				switchType = SWITCH_FINISH;
			}
		}
	}
	private void selectToGame(){
		if( switchType==SWITCH_PREPARE ){
//			lightNum = lightNum + dNum;
			P.increaseDarkness();
//			selectDraw();
			selectView.draw();
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
				Res.prepare(Res.GAME_A);
			}
		}
		else if( switchType==SWITCH_LOADING ){
//			selectDraw();
			selectView.draw();
			if(Res.update()){
				//加载完图片之后载入地图
				GameChapter.chapterLoad(
						gameView.getGateAtlas(),
						gameView.getRankNum(),
						SelectButtons.getChapterId()
						); 
				switchType = SWITCH_LOADED;
			}
		}
		else if( switchType==SWITCH_LOADED ){
//			lightNum = lightNum - dNum;
			P.decreaseDarkness();
//			gameDraw();
			gameView.draw();
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				switchType = SWITCH_FINISH;
			}
		}
	}
	
	public void engine(){
		
		switch (status) {
		
		case Credits:  {       break;}
		
		case Game:     {   gameView.engine();    break;}
			
		case Loading:  {        break;}
		
		case Pause:    {        break;}
		
		case Select:   {    selectView.engine();   break;}
		
		case Start:    {    startView.engine();;     break;}
		
		case Init:     {        break;}
		
		}
		
		
	}
	
	public void showGraphic(){
		
		
		switch (status) {
		
		case Credits:  {        break;}
		
		case Game:     {    gameView.draw();    break;}
			
		case Loading:  {        break;}
		
		case Pause:    {    pauseView.draw(gameView);    break;}
		
		case Select:   {    selectView.draw();   break;}
		
		case Start:    {    startView.draw();   break;}
		
		case Init:     {        break;}
		
		}
		
		
		
	}
	
	public void btnLogic(){
		
		switch (status) {
				
		case Credits:  {        break;}
				
		case Game:     {    gameView.btnLogic(points);     break;}
					
		case Loading:  {        break;}
				
		case Pause:    {    pauseView.btnLogic(points);    break;}
				
		case Select:   {    selectView.btnLogic(points);   break;}
				
		case Start:    {     startView.btnLogic(points);   break;}
				
		case Init:     {        break;}
		
		}
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
