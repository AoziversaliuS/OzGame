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

public class StartButton extends OzElement{

	private OzRect startGameButton;
	
	public static final int START_GAME = 1,ELSE = 3; 
	private boolean submit = false;//按下且松开按钮时为true
	
	private  int selected = StartButton.ELSE;
	
	public StartButton( ) {
		super("StartButton", Rank.SELF_CUSTOM , ET.StartButton, null, null );
		
		startGameButton = new OzRect(450, 300, Res.startBtnA.getWidth(), Res.startBtnA.getHeight());
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
		
		//只有第一个碰到屏幕的点才有效
		l = points.get("0");
		
		
		if( l!=null ){
			
			if( startGameButton.inside(l) ){
				selected = StartButton.START_GAME;
			}
			else{
				selected = StartButton.ELSE;
			}
			
		}
		else{
			System.out.println("进入");
			if( selected!=StartButton.ELSE ){
				submit = true;
			}
		}
		
		if(submit){
			active();
		}
		
		
	}
	//按键触发事件
	private void active(){
		if( selected==StartButton.START_GAME ){
			GameView.setToStatus(Status.Game);
		}
		
		
		submit = false;
		selected = StartButton.ELSE;
	}
	

	@Override
	public void draw() {
		
		if( selected == StartButton.START_GAME ){
			P.draw(startGameButton.x, startGameButton.y, Res.startBtnB);
		}
		else if( selected==StartButton.ELSE ){
			P.draw(startGameButton.x, startGameButton.y, Res.startBtnA);
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
