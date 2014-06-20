package g.view;

import java.util.ArrayList;
import java.util.HashMap;

import g.basis.MainView;
import g.button.GameButtons;
import g.refer.BasicBody;
import g.refer.BtnMethods;
import g.refer.OzElement;
import g.refer.Player;
import g.refer.ViewInterface;
import g.tool.OzInt;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;

public class GameView implements ViewInterface,BtnMethods{

	private  GameButtons gameBtns;
	private  ArrayList<OzElement>  gateAtlas; //每一个关卡的地图集序列
	private  ArrayList<OzInt> rankNum;   //按图层等级来进行显示图片
	private  Player player;
	
	
	
	public GameView() {
		super();
		this.init();
	}


	@Override
	public void init() {
		gameBtns  = new GameButtons();
		gateAtlas = new ArrayList<OzElement>();
		rankNum   = new ArrayList<OzInt>();
		player    =  new Player();
	}
	

	@Override
	public void engine() {

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

	@Override
	public void draw(ViewInterface ...viewInterfaces) {
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
	/**
	 * 从游戏界面切换到暂停菜单
	 * */
	public void toPauseView(ViewInterface ...viewInterfaces){
		PauseView pauseView = (PauseView) viewInterfaces[0];
		if( MainView.switchType==MainView.SWITCH_PREPARE ){
//			lightNum = lightNum + dNum;
			P.increaseDarkness();
//			gameDraw();
			this.draw();
			if( P.getBlackNum()>=P.MAX_BLACK_NUM/2 ){
				MainView.switchType = MainView.SWITCH_LOADING;
				Res.prepare(Res.PAUSE_SOURCE);
			}
		}
		else if( MainView.switchType==MainView.SWITCH_LOADING ){
//				gameDraw();
				this.draw();
				if(Res.update()){
					MainView.switchType = MainView.SWITCH_LOADED;
				}
		}
		else if( MainView.switchType==MainView.SWITCH_LOADED ){
//			lightNum = 0;
			P.setDarkness(0);
//			pauseDraw();
			pauseView.draw(this);
			MainView.switchType = MainView.SWITCH_FINISH;
		}
	}


	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		gameBtns.logic(points);
	}
	
	@Override
	public void btnEnter() {
	}
	@Override
	public void btnExit() {
	}

	public ArrayList<OzElement> getGateAtlas() {
		return gateAtlas;
	}

	public ArrayList<OzInt> getRankNum() {
		return rankNum;
	}

	



}
