package g.button;

import java.awt.Point;
import java.util.HashMap;

import g.basis.MainView;
import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;
import g.type.Status;

public class StartButtons extends OzElement{

	private OzRect startGameButton;
	
	private static final int START_GAME = 1,ELSE = 3; 
	private boolean submit;//�������ɿ���ťʱΪtrue
	
	private  int selected;
	
	public StartButtons( ) {
		super("StartButtons", Rank.SELF_CUSTOM , ET.StartButtons, null, null );
		
		startGameButton = new OzRect(450, 300, Res.startBtnA.getWidth(), Res.startBtnA.getHeight());
		
		this.reset();
	}

	@Override
	public void reset() {
		selected = StartButtons.ELSE;
		submit = false;
	}

	@Override
	public void logic() {
		// TODO Auto-generated method stub
		
	}
	
	public void logic(HashMap<String, OzPoint> points) {
		
		//ֻ�е�һ��������Ļ�ĵ����Ч
		l = points.get("0");
		
		
		if( l!=null ){
			if( startGameButton.inside(l,P.FORCE_RATIO) ){
				selected = StartButtons.START_GAME;
			}
			else{
				selected = StartButtons.ELSE;
			}
		}
		else{
			if( selected!=StartButtons.ELSE ){
				submit = true;
			}
		}
		
		if(submit){
			active();
		}
		
		
	}
	//���������¼�
	private void active(){
		if( selected==StartButtons.START_GAME ){
			MainView.setToStatus(Status.Select);
		}
		
		
		submit = false;
		selected = StartButtons.ELSE;
	}
	

	@Override
	public void draw() {
		
		if( selected == StartButtons.START_GAME ){
//			P.drawForce(startGameButton.x, startGameButton.y, Res.startBtnB);
			P.draw(startGameButton.x, startGameButton.y, Res.startBtnB, P.FORCE_RATIO);
		}
		else if( selected==StartButtons.ELSE ){
//			P.drawForce(startGameButton.x, startGameButton.y, Res.startBtnA);
			P.draw(startGameButton.x, startGameButton.y, Res.startBtnA, P.FORCE_RATIO);
		}
		
	}

	@Override
	public void impact(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean rollBack() {
		// TODO Auto-generated method stub
		return false;
	}

}
