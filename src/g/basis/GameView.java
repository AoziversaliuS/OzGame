package g.basis;

import java.util.ArrayList;
import java.util.HashMap;

import g.button.GameButton;
import g.button.StartButton;
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
	//StartStatus
	private StartButton startBtn;
	//GameStatus
	private GameButton gameBtn;
	private static   ArrayList<OzElement>  gateAtlas; //ÿһ���ؿ��ĵ�ͼ������
	private  ArrayList<OzInt> rankNum;   //��ͼ��ȼ���������ʾͼƬ
	
	private static Status status;  //��ǰ����״̬
    private static Status toStatus;
	private float lightNum = 0f;
	private boolean switching = false;
	private boolean switchFinish = false;

	public static Player player;
	
	@Override
	public void create() {	
		
		
		points = new HashMap<String, OzPoint>(); //������
		P.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //ͼƬ��Դ��ʼ��
		Gdx.input.setCatchBackKey(true); //����ϵͳ���յ�Back��
		Gdx.input.setInputProcessor(this); //���ô�������
		
		status = Status.Start;  //���õ�ǰ����״̬
		toStatus = Status.Start;
		
		startInit();
		this.gameInit();
		 
	}
	@Override
	public synchronized boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public synchronized boolean touchDown(int screenX, int screenY,int pointer, int button) {
		
		points.put(""+pointer, new OzPoint(screenX, screenY, true));
		btnLogic();
		Gdx.app.log("Interact", "touchDown  ��"+points.size()+"����! "+"   "+pointer);
		return false;
	}
	@Override
	public synchronized boolean touchUp(int screenX, int screenY, int pointer,
			int button) {
		
		points.remove(""+pointer);
		btnLogic();
		Gdx.app.log("Interact", "touchUp ��"+points.size()+"����! "+"   "+pointer);
		return false;
	}
	@Override
	public synchronized boolean touchDragged(int screenX, int screenY,
			int pointer) {
		
		points.get(""+pointer).set_XY_fromScreen(screenX, screenY);
		btnLogic();
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
		P.begin();
//		Gdx.app.log("FPS", " FPS:  "+Gdx.graphics.getFramesPerSecond());
		if( toStatus==status ){
			engine();
			showGraphic();
		}
		else{
			statusSwitch();
		}
		
		P.setlight(lightNum);
		P.end();
	}
	public void statusSwitch(){
		final float dNum = 0.05f;
		final float maxLight = 0.98f;
		final float minLight = 0.01f;
		if( toStatus==Status.Game && status==Status.Start ){
			
			if(!switching){
				lightNum = lightNum + dNum;
				startDraw();
				if( lightNum>=maxLight ){
					System.out.println("������");
					switching = true;
				}
				System.out.println("lightNum="+lightNum);
			}
			else{
				lightNum = lightNum - dNum;
				gameDraw();
				if( lightNum<=minLight ){
					switchFinish = true;
				}
			}
		}
		
		if( switchFinish ){
			status = toStatus;
			//������Ϣ
			lightNum = 0;
			switching = false;
			switchFinish = false;
		}
	}
	public void engine(){
		
		switch (status) {
		
		case Credits:  {       break;}
		
		case Game:     {    gameEngine();    break;}
			
		case Loading:  {        break;}
		
		case Pause:    {        break;}
		
		case Select:   {        break;}
		
		case Start:    {    startEngine();     break;}
		
		case Init:     {        break;}
		
		}
		
		
	}
	public void showGraphic(){
		
		
		switch (status) {
		
		case Credits:  {        break;}
		
		case Game:     {    gameDraw();    break;}
			
		case Loading:  {        break;}
		
		case Pause:    {        break;}
		
		case Select:   {        break;}
		
		case Start:    {    startDraw();   break;}
		
		case Init:     {        break;}
		
		}
		
		
		
	}
	
	public void btnLogic(){
		
		switch (status) {
				
		case Credits:  {        break;}
				
		case Game:     {    gameBtn.logic(points);     break;}
					
		case Loading:  {        break;}
				
		case Pause:    {        break;}
				
		case Select:   {        break;}
				
		case Start:    {     startBtn.logic(points);   break;}
				
		case Init:     {        break;}
		
		}
	}
	
	
	public void startInit(){
		startBtn = new StartButton();
	}
	
	public void startEngine(){
		
	}
	
	public void startDraw(){
		P.drawFr(0, 0, P.startBg);
		startBtn.draw();
	}
	
	
	
	
	
	
	
	public void gameInit(){
		gameBtn     = new GameButton();
		gateAtlas   = new ArrayList<OzElement>();
		rankNum     = new ArrayList<OzInt>();
		GameChapter.chapterLoad(gateAtlas, rankNum,1); 
		player =  new Player();
	}
	public void gameEngine(){
		
		player.resetOnBegin();
		
		//������ع�
		if(Player.getCondition()==Player.DEAD_END){
			boolean finish = false;
			for(OzElement g:gateAtlas){

				if( finish==false ){
					finish = g.rollBack();
				}
				else{
					g.rollBack();
				}
			}
			//��������֮��ʼ����
			if(finish){
				Player.setCondition(Player.REVIVE_START);
			}
		}
		//������ع�
		
		//��ײ���A�� [���ڸ��������ײ״̬]
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).impact(player);
		}
		player.set_VerticalT_and_PlaneT(gateAtlas); //������ҵĴ�ֱ״̬��ˮƽ״ֵ̬
		//��ײ���A��
		
		
		//���״̬�ı��
		player.updateAction();
		//���״̬�ı��
		
		//�ڽ���engineǰ�Բ�������һЩ����,Ŀǰ������MoveLand�������ı�ʱ���²���
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).prepare();
		}
		
		//Ԫ���ƶ����߼���
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).engine();
		}
		player.engine();
		//Ԫ���ƶ����߼���
		
		//��ײ���B�� [����PushBack]
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).impact(player);
		}
		//λ��΢��
		for(OzElement g:gateAtlas){
			if(g instanceof BasicBody){
				//����һص���ǽǰ��һ˲�������˵��Ҵ�ǽʵ������ǽ����ң���ȷ�������ǰ�ǽ��������������
				g.l.x = g.l.x + player.getPush_X();
				g.l.y = g.l.y - player.getPush_Y();
			}
		}
		//��ײ���B��
		
		/**��ʱ��δ��ͼ******/
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
	
	
	
	
	
	
	
	
	
	
	
	
	public static void setToStatus(Status toStatus) {
		GameView.toStatus = toStatus;
	}

}
