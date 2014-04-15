package g.button;

import java.util.HashMap;

import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.type.ET;
import g.type.Rank;

public class StartButton extends OzElement{

	private OzRect startGameButton;
	
	public static final int START_GAME = 1,ELSE = 2; 
	
	private  int selected = StartButton.ELSE;
	
	public StartButton( ) {
		super("StartButton", Rank.SELF_CUSTOM , ET.StartButton, null, null );
		
		startGameButton = new OzRect(450, 300, P.startBtnA.getWidth(), P.startBtnA.getHeight());
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
		
		if( startGameButton.insides(points) ){
			selected = StartButton.START_GAME;
		}
		else{
			selected = StartButton.ELSE;
		}
		active();
		
	}
	//按键触发事件
	private void active(){
		
	}
	

	@Override
	public void draw() {
		
		if( selected == StartButton.START_GAME ){
			P.draw(startGameButton.x, startGameButton.y, P.startBtnB);
		}
		else if( selected==StartButton.ELSE ){
			P.draw(startGameButton.x, startGameButton.y, P.startBtnA);
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
