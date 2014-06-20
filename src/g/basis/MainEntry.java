package g.basis;

import java.util.HashMap;

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

public class MainEntry extends InputProcessorQueue implements ApplicationListener {
	
	private boolean showFPS = false;
	
	private boolean debug = false;
	private long times = 50;//debug�ã�debugΪtrueʱʹ��Ϸ���л������ܿ���ÿһ֡
	
	private  HashMap<String, OzPoint> points;
	
	//SelectStatus
	private SelectView selectView;
	//StartStatus
	private StartView startView;
	//PauseStatus
	private PauseView pauseView;
	//GameStatus
	private GameView gameView;
	
	private static Status status;  //��ǰ����״̬
    private static Status toStatus;
	
    public static final int SWITCH_PREPARE=1,SWITCH_LOADING=2,SWITCH_LOADED=3,SWITCH_FINISH=4;
	
	public static int switchType = SWITCH_PREPARE;
	

	
	
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
	public synchronized boolean keyDown(int keycode) {
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
			//������Ϣ
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
				//������ͼƬ֮�������ͼ
				GameChapter.chapterLoad(
						gameView.getGateAtlas(),
						gameView.getRankNum(),
						selectView.getChapterId()
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
	
	
	
	
	
	
	
	
	public static Status getStatus() {
		return status;
	}
	public static Status getToStatus() {
		return toStatus;
	}
	public static boolean switchFinished(){
		if( status==toStatus ){
			return true;
		}
		return false;
	}
	
	public static void setToStatus(Status toStatus) {
		MainEntry.toStatus = toStatus;
	}

}
