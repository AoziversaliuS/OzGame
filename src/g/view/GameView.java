package g.view;

import java.util.ArrayList;
import java.util.HashMap;

import g.basis.MainEntry;
import g.button.GameButtons;
import g.refer.BasicBody;
import g.refer.BasicView;
import g.refer.BtnMethods;
import g.refer.OzElement;
import g.refer.Player;
import g.refer.ViewInterface;
import g.tool.OzInt;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;
import g.type.Status;

public class GameView extends BasicView implements BtnMethods{

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



	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		gameBtns.logic(points);
	}
	

	public ArrayList<OzElement> getGateAtlas() {
		return gateAtlas;
	}

	public ArrayList<OzInt> getRankNum() {
		return rankNum;
	}


	@Override
	public void thisToView(Status toStatus, ViewInterface... views) {
		switch (toStatus) {
			
			case Pause:{ toPauseView(views); break;}
			

		}
	}
	
	/**
	 * ����Ϸ�����л�����ͣ�˵�
	 * */
	private void toPauseView(ViewInterface ...views){
		PauseView pauseView = (PauseView) views[1];
		if( switchType==SWITCH_PREPARE ){
			P.increaseDarkness();
			this.draw();
			if( P.getBlackNum()>=P.MAX_BLACK_NUM/2 ){
				switchType = SWITCH_LOADING;
				Res.prepare(Res.PAUSE_SOURCE);
			}
		}
		else if( switchType==SWITCH_LOADING ){
				this.draw();
				if(Res.update()){
					switchType = SWITCH_LOADED;
				}
		}
		else if( switchType==SWITCH_LOADED ){
			P.setDarkness(0);
			pauseView.draw(this);
			switchType = SWITCH_FINISH;
		}
	}


	@Override
	public void reset() {
		player.reset();
		gameBtns.reset();
	}

	



}
