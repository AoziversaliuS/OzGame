package g.basis;

import java.util.ArrayList;
import java.util.HashMap;

import g.button.GameButtons;
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

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessorQueue;

public class GameView extends InputProcessorQueue implements ApplicationListener {
	
	private boolean showFPS = false;
	
	private boolean debug = false;
	private long times = 50;
	
	private  HashMap<String, OzPoint> points;
	//SelectStatus
	private SelectButtons selectBtns;
	//StartStatus
	private StartButtons startBtns;
	//GameStatus
	private GameButtons gameBtns;
	private static   ArrayList<OzElement>  gateAtlas; //每一个关卡的地图集序列
	private  ArrayList<OzInt> rankNum;   //按图层等级来进行显示图片
	
	private static Status status;  //当前界面状态
    private static Status toStatus;
	private float lightNum = 0f;
	
	private final int SWITCH_PREPARE=1,SWITCH_LOADING=2,SWITCH_LOADED=3,SWITCH_FINISH=4;
	private int sT = SWITCH_PREPARE;
	
	public static Player player;
	
	
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
		this.gameInit();//游戏界面初始化
		this.selectInit();//选择菜单初始化
		
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
		
		if( sT==SWITCH_FINISH ){
			status = toStatus;
			//重置信息
			lightNum = 0;
			sT=SWITCH_PREPARE;
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
				Res.prepare(Res.resourceA);
			}
		}
		else if( sT==SWITCH_LOADING ){
			boolean s = Res.update();
			System.out.println(s);
			if(s){
				//加载完图片之后载入地图
				GameChapter.chapterLoad(gateAtlas, rankNum,SelectButtons.getChapterId()); 
				sT = SWITCH_LOADED;
			}
		}
		else if( sT==SWITCH_LOADED ){
			lightNum = lightNum - dNum;
			gameDraw();
			if( lightNum<=minLight ){
				sT = SWITCH_FINISH;
			}
		}
	}
	
	public void engine(){
		
		switch (status) {
		
		case Credits:  {       break;}
		
		case Game:     {    gameEngine();    break;}
			
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
		
		case Game:     {    gameDraw();    break;}
			
		case Loading:  {        break;}
		
		case Pause:    {        break;}
		
		case Select:   {     selectDraw();   break;}
		
		case Start:    {    startDraw();   break;}
		
		case Init:     {        break;}
		
		}
		
		
		
	}
	
	public void btnLogic(){
		
		switch (status) {
				
		case Credits:  {        break;}
				
		case Game:     {    gameBtns.logic(points);     break;}
					
		case Loading:  {        break;}
				
		case Pause:    {        break;}
				
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
		P.drawForce(0, 0, Res.startBg);
		startBtns.draw();
	}
	
	
	public void selectInit(){
		selectBtns = new SelectButtons();
	}
	public void selectEngine(){
		selectBtns.logic();
	}
	public void selectDraw(){
		P.drawForce(0, 0, Res.selectBg);
		selectBtns.draw();
	}
	
	
	
	
	
	
	
	public void gameInit(){
		gameBtns     = new GameButtons();
		gateAtlas   = new ArrayList<OzElement>();
		rankNum     = new ArrayList<OzInt>();
		player =  new Player();
	}
	public void gameEngine(){
		
		player.resetOnBegin();
		
		//↓复活回滚
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
			//重设坐标之后开始复活
			if(finish){
				Player.setCondition(Player.REVIVE_START);
			}
		}
		//↑复活回滚
		
		//碰撞检测A↓ [用于更新玩家碰撞状态]
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).impact(player);
		}
		player.set_VerticalT_and_PlaneT(gateAtlas); //设置玩家的垂直状态和水平状态值
		//碰撞检测A↑
		
		
		//玩家状态改变↓
		player.updateAction();
		//玩家状态改变↑
		
		//在进入engine前对参数进行一些更新,目前仅用于MoveLand方向发生改变时更新参数
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).prepare();
		}
		
		//元素移动等逻辑↓
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).engine();
		}
		player.engine();
		//元素移动等逻辑↑
		
		//碰撞检测B↓ [用于PushBack]
		for(int i=0;i<gateAtlas.size();i++){
			gateAtlas.get(i).impact(player);
		}
		//位置微调
		for(OzElement g:gateAtlas){
			if(g instanceof BasicBody){
				//让玩家回到穿墙前的一瞬，相对来说玩家穿墙实际上是墙穿玩家，正确的做法是把墙从玩家身边拉开。
				g.l.x = g.l.x + player.getPush_X();
				g.l.y = g.l.y - player.getPush_Y();
			}
		}
		//碰撞检测B↑
		
		/**此时还未绘图******/
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
		gameBtns.draw();
	
	}
	
	
	
	
	
	
	
	
	
	
	public static boolean switchFinished(){
		if( status==toStatus){
			return true;
		}
		return false;
	}
	
	public static void setToStatus(Status toStatus) {
		GameView.toStatus = toStatus;
	}

}
