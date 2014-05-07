package g.button;

import java.awt.Point;
import java.util.HashMap;

import g.basis.GameView;
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
	
	public static final int START_GAME = 1,ELSE = 3; 
	private boolean submit = false;//�������ɿ���ťʱΪtrue
	
	private  int selected = StartButtons.ELSE;
	
	public StartButtons( ) {
		super("StartButton", Rank.SELF_CUSTOM , ET.StartButton, null, null );
		
		startGameButton = new OzRect(450, 300, Res.startBtnA.getWidth(), Res.startBtnA.getHeight());
//		startGameButton = new OzRect(450, 300, Res.startBtnA.getWidth(), Res.startBtnA.getHeight());
//		System.out.println("startGameButton ��:"+startGameButton.width+"   ��:"+startGameButton.height);
		
		System.out.println("startGameButton ��:"+Res.startBtnA.getOriginWidth()+"   ��:"+Res.startBtnA.getOriginHeight() );
		System.out.println("startGameButton ʹ�ñ���Ϊ:"+Res.startBtnA.getRatioType());
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
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
			System.out.println("����");
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
			GameView.setToStatus(Status.Select);
		}
		
		
		submit = false;
		selected = StartButtons.ELSE;
	}
	

	@Override
	public void draw() {
		
		if( selected == StartButtons.START_GAME ){
			P.drawForce(startGameButton.x, startGameButton.y, Res.startBtnB);
		}
		else if( selected==StartButtons.ELSE ){
			P.drawForce(startGameButton.x, startGameButton.y, Res.startBtnA);
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
