package g.basis;

import java.util.HashMap;

import g.refer.ViewInterface;
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

public class MainEntry extends InputProcessorQueue implements ApplicationListener {
	
	private boolean showFPS = false;
	
	private boolean debug = true;
	private long times = 50;//debug�ã�debugΪtrueʱʹ��Ϸ���л������ܿ���ÿһ֡
	
	private  HashMap<String, OzPoint> points;
	
	//�ؿ�����
	private SelectView selectView;
	//��ʼ����
	private StartView startView;
	//��ͣ����
	private PauseView pauseView;
	//��Ϸ����
	private GameView gameView;
	
	private static Status status;  //��ǰ����״̬
    private static Status toStatus;
	
    
	@Override
	public void create() {	
		
		P.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //��ͼ�������ʼ��
		Res.init();//��Դ��������ʼ����
		
		points = new HashMap<String, OzPoint>(); //������
		
		Gdx.input.setCatchBackKey(true); //����ϵͳ���յ�Back��
		Gdx.input.setInputProcessor(this); //���ô�������
		
		status = Status.Start;  //���õ�ǰ����״̬
		toStatus = Status.Start;
		
		//��ʼ�˵���ʼ��
		startView = new StartView();
        //��Ϸ�����ʼ��
		gameView = new GameView();
		//ѡ��˵���ʼ��
		selectView = new SelectView();
		//��ͣ�˵���ʼ��
		pauseView = new PauseView();
		
	}
	@Override
	public synchronized boolean keyUp(int keycode) {
		System.out.println("keyUp = "+keycode);
		return super.keyUp(keycode);
	}
	@Override
	public synchronized boolean keyDown(int keycode) {
		System.out.println("keyDown = "+keycode);
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
	 * �ڽ����л���ɺ���ô˷���ʹ status = toStatus;
	 * */
	public static void finishSwitch(){
		status = toStatus;
	}
	
	/**
	 * ���  status!=toStatus �򷵻�true
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
