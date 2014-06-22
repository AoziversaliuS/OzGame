package g.basis;

import java.util.HashMap;

import g.refer.ViewInterface;
import g.tool.Data;
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
import com.badlogic.gdx.files.FileHandle;

public class MainEntry extends InputProcessorQueue implements ApplicationListener {
	
	private boolean showFPS = true;
	
	private boolean debug = false;
	private long times = 50;//debug用，debug为true时使游戏运行缓慢，能看清每一帧
	
	private  HashMap<String, OzPoint> points;
	
	//关卡界面
	private SelectView selectView;
	//开始界面
	private StartView startView;
	//暂停界面
	private PauseView pauseView;
	//游戏界面
	private GameView gameView;
	
	private static Status status;  //当前界面状态
    private static Status toStatus;
	
    
    private static final int KEY_LEFT=21;
    private static final int KEY_RIGHT=22;
    private static final int KEY_SPACE=62;
    
	@Override
	public void create() {	
		
		//存档文件的初始化
		Data.init();
		
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
	public synchronized boolean keyUp(int keycode) {
		//←按钮 141 568 →按钮 318 516 跳跃 733 529
//		System.out.println("keyUp = "+keycode);
		switch (keycode) {
		
		case KEY_LEFT:  points.remove("0"); break;
		
		case KEY_RIGHT: points.remove("0"); break;
		
		case KEY_SPACE: points.remove("1"); break;
			
		}
		btnLogic();
		return false;
	}
	@Override
	public synchronized boolean keyDown(int keycode) {
		
		switch (keycode) {
		
		case KEY_LEFT: points.put("0", new OzPoint(141, 568, true)); break;
		
		case KEY_RIGHT: points.put("0", new OzPoint(318, 516, true)); break;
		
		case KEY_SPACE: points.put("1", new OzPoint(733, 529, true)); break;
			
		}
		btnLogic();
		return false;
	}
	@Override
	public synchronized boolean touchDown(int screenX, int screenY,int pointer, int button) {
		points.put(""+pointer, new OzPoint(screenX, screenY, true));
		btnLogic();
		return false;
	}
	@Override
	public synchronized boolean touchUp(int screenX, int screenY, int pointer,
			int button) {
		points.remove(""+pointer);
		btnLogic();
		return false;
	}
	@Override
	public synchronized boolean touchDragged(int screenX, int screenY,
			int pointer) {
		points.get(""+pointer).set_XY_fromScreen(screenX, screenY);
		btnLogic();
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
		P.useDarkness();
		P.end();
		
		if(debug){
			try {
				Thread.sleep(times);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void statusSwitch(){

		
		ViewInterface[] views = { gameView,pauseView,selectView,startView };
		
		switch (status) {
		
		case Credits:{              break; }
		
		case Game:   {   gameView.toView(toStatus,views);        break; }
		
		case Init:   {              break; }
		
		case Loading:{              break; }
		
		case Pause:  {   pauseView.toView(toStatus,views);           break; }
		
		case Select: {   selectView.toView(toStatus,views);          break; }
		
		case Start:  {   startView.toView(toStatus,views);       break; }
		
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
	
	
	public static Status getStatus() {
		return status;
	}
	public static Status getToStatus() {
		return toStatus;
	}
	/**
	 * 在界面切换完成后调用此方法使 status = toStatus;
	 * */
	public static void finishSwitch(){
		status = toStatus;
	}
	
	/**
	 * 如果  status!=toStatus 则返回true
	 * */
	public static boolean isSwitching(){
		if( status==toStatus ){
			return false;
		}
		return true;
	}
	
	public static void setToStatus(Status toStatus) {
		MainEntry.toStatus = toStatus;
	}

}
