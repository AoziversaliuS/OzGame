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
import g.view.PauseView;
import g.view.SelectView;
import g.view.StartView;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessorQueue;

public class MainView extends InputProcessorQueue implements ApplicationListener {
	
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
	
	private final int SWITCH_PREPARE=1,SWITCH_LOADING=2,SWITCH_LOADED=3,SWITCH_FINISH=4;
	private int sT = SWITCH_PREPARE;
	

	
	
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
//		Gdx.app.log("Interact", "touchDown  ��"+points.size()+"����! "+"   "+pointer);
		return false;
	}
	@Override
	public synchronized boolean touchUp(int screenX, int screenY, int pointer,
			int button) {
		
		points.remove(""+pointer);
		btnLogic();
//		Gdx.app.log("Interact", "touchUp ��"+points.size()+"����! "+"   "+pointer);
		return false;
	}
	@Override
	public synchronized boolean touchDragged(int screenX, int screenY,
			int pointer) {
		
		points.get(""+pointer).set_XY_fromScreen(screenX, screenY);
		btnLogic();
//		Gdx.app.log("Interact", "touchDragged ��"+points.size()+"����! "+"   "+pointer+" L: "+screenX+" "+screenY);
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
			gameToPause();
		}
		if( toStatus==Status.Game && status==Status.Pause ){
			pauseToGame();
		}
		
		if( sT==SWITCH_FINISH ){
			status = toStatus;
			//������Ϣ
//			lightNum = 0;
			P.setDarkness(0);
			sT=SWITCH_PREPARE;
		}
	}
	private void pauseToGame(){
		if( sT==SWITCH_PREPARE ){
//			lightNum = maxLight/2;
			P.setDarkness(P.MAX_BLACK_NUM/2);
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
//			lightNum = lightNum-dNum;
			P.decreaseDarkness();
//			gameDraw();
			gameView.draw();
			if(P.getBlackNum()<=P.MIN_BLACK_NUM){
				sT = SWITCH_FINISH;
			}
		}
	}
	private void gameToPause(){
		if( sT==SWITCH_PREPARE ){
//			lightNum = lightNum + dNum;
			P.increaseDarkness();
//			gameDraw();
			gameView.draw();
			if( P.getBlackNum()>=P.MAX_BLACK_NUM/2 ){
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
//			lightNum = 0;
			P.setDarkness(0);
//			pauseDraw();
			pauseView.draw(gameView);
			sT = SWITCH_FINISH;
		}
	}
	private void startToSelect(){
		if( sT==SWITCH_PREPARE ){
//			lightNum = lightNum + dNum;
			P.increaseDarkness();
//			startDraw();
			startView.draw();
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				sT = SWITCH_LOADING;
			}
		}
		else if( sT==SWITCH_LOADING ){
				sT = SWITCH_LOADED;
		}
		else if( sT==SWITCH_LOADED ){
//			lightNum = lightNum - dNum;
			P.decreaseDarkness();
//			selectDraw();
			selectView.draw();
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				sT = SWITCH_FINISH;
			}
		}
	}
	private void selectToGame(){
		if( sT==SWITCH_PREPARE ){
//			lightNum = lightNum + dNum;
			P.increaseDarkness();
//			selectDraw();
			selectView.draw();
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				sT = SWITCH_LOADING;
				Res.prepare(Res.GAME_A);
			}
		}
		else if( sT==SWITCH_LOADING ){
//			selectDraw();
			selectView.draw();
			if(Res.update()){
				//������ͼƬ֮�������ͼ
				GameChapter.chapterLoad(
						gameView.getGateAtlas(),
						gameView.getRankNum(),
						SelectButtons.getChapterId()
						); 
				sT = SWITCH_LOADED;
			}
		}
		else if( sT==SWITCH_LOADED ){
//			lightNum = lightNum - dNum;
			P.decreaseDarkness();
//			gameDraw();
			gameView.draw();
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
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
