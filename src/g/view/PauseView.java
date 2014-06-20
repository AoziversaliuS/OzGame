package g.view;

import java.util.HashMap;

import g.basis.MainEntry;
import g.button.PauseButtons;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.OzPoint;
import g.tool.P;
import g.type.Status;

public class PauseView implements ViewInterface,BtnMethods{

	private PauseButtons pauseBtns;

	public PauseView() {
		super();
		this.init();
	}

	@Override
	public void init() {
		pauseBtns = new PauseButtons();
	}

	@Override
	public void engine() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(ViewInterface ...viewInterfaces) {
		//¡ýgameView.draw();
		viewInterfaces[0].draw();
		
		P.useDarkness(P.MAX_BLACK_NUM/2);
		pauseBtns.draw();
	}
	
	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		pauseBtns.logic(points);
	}

	@Override
	public void btnEnter() {
	}

	@Override
	public void btnExit() {
	}
	
	public void toGameView(ViewInterface ...viewInterfaces){
		GameView gameView = (GameView) viewInterfaces[0];
		if( MainEntry.switchType==MainEntry.SWITCH_PREPARE ){
//			lightNum = maxLight/2;
			P.setDarkness(P.MAX_BLACK_NUM/2);
//			gameDraw();
			gameView.draw();
			MainEntry.switchType = MainEntry.SWITCH_LOADING;
		}
		else if( MainEntry.switchType==MainEntry.SWITCH_LOADING ){
//			gameDraw();
			gameView.draw();
			MainEntry.switchType = MainEntry.SWITCH_LOADED;
		}
		else if( MainEntry.switchType==MainEntry.SWITCH_LOADED ){
//			lightNum = lightNum-dNum;
			P.decreaseDarkness();
//			gameDraw();
			gameView.draw();
			if(P.getBlackNum()<=P.MIN_BLACK_NUM){
				MainEntry.switchType = MainEntry.SWITCH_FINISH;
			}
		}
	}

	@Override
	public void thisToView(Status status, ViewInterface... viewInterfaces) {
		// TODO Auto-generated method stub
		
	}

}
