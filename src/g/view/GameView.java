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
	private  ArrayList<OzElement>  gateAtlas; //ÿһ���ؿ��ĵ�ͼ������
	private  ArrayList<OzInt> rankNum;   //��ͼ��ȼ���������ʾͼƬ
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
	 * ����Ϸ�����л�����ͣ�˵�
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
