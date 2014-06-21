package g.view;

import java.util.HashMap;

import g.basis.MainEntry;
import g.button.PauseButtons;
import g.refer.BasicView;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;
import g.type.Status;

public class PauseView extends BasicView implements BtnMethods{

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
	public void thisToView(Status toStatus, ViewInterface... views) {
		switch (toStatus) {
		
			case Game:{ toGameView(views); break;}
			
			case Select:{ toSelectView(views); break;}

		}
	}
	private void toSelectView(ViewInterface ...views){
		GameView gameView = (GameView) views[0];
		SelectView selectView = (SelectView) views[2];
		if( switchType==SWITCH_PREPARE ){
			P.increaseDarkness();
			this.draw(gameView);
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
			}
		}
		else if( switchType==SWITCH_LOADING ){
			//Ð¶ÔØ×ÊÔ´
			Res.unload(Res.PAUSE_SOURCE);
			
//			Res.unload(Res.GAME_A);
			
			switchType = SWITCH_LOADED;
		}
		else if( switchType==SWITCH_LOADED ){
			P.decreaseDarkness();
			selectView.draw();
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				switchType = SWITCH_FINISH;
			}
		}
	}
	private void toGameView(ViewInterface ...views){
		GameView gameView = (GameView) views[0];
		if( switchType==SWITCH_PREPARE ){
			P.setDarkness(P.MAX_BLACK_NUM/2);
			gameView.draw();
			switchType = SWITCH_LOADING;
		}
		else if( switchType==SWITCH_LOADING ){
			gameView.draw();
			switchType = SWITCH_LOADED;
		}
		else if( switchType==SWITCH_LOADED ){
			P.decreaseDarkness();
			gameView.draw();
			if(P.getBlackNum()<=P.MIN_BLACK_NUM){
				switchType = SWITCH_FINISH;
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
