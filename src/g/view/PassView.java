package g.view;

import java.util.HashMap;

import g.basis.GameChapter;
import g.button.PassButtons;
import g.refer.BasicView;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;
import g.type.Status;

public class PassView  extends BasicView implements BtnMethods{

	private PassButtons passButtons;
	
	public PassView() {
		super();
		init();
	}

	@Override
	public void init() {
		passButtons = new PassButtons();
	}

	@Override
	public void engine() {
		
	}

	@Override
	public void draw(ViewInterface... views) {
		views[0].draw();
		P.useDarkness(P.MAX_BLACK_NUM/2);
		passButtons.draw();
	}

	@Override
	public boolean enter() {
		 float enterX = P.BASIC_SCREEN_WIDTH - passButtons.getBtnNext().width;
		if( passButtons.getBtnNext().x > enterX){
			passButtons.getBtnNext().x = passButtons.getBtnNext().x - BTN_SPEED;
			passButtons.getBtnRestart().x = passButtons.getBtnRestart().x - BTN_SPEED;
			return false;
		}
		else{
			passButtons.getBtnNext().x = enterX;
			passButtons.getBtnRestart().x = enterX;
		}
		return true;
	}

	@Override
	public boolean exit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void thisToView(Status toStatus, ViewInterface... views) {
		switch (toStatus) {
		
			case Game:{toGameView(views); break;}
		
		}
	}
	
	private void toGameView(ViewInterface... views){
		GameView gameView = (GameView) views[0];
		if( switchType==SWITCH_PREPARE ){
			P.increaseDarkness();
			this.draw(views);
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
				Res.prepare(Res.GAME_A);
			}
		}
		else if( switchType==SWITCH_LOADING ){
			this.draw(views);
			if(Res.update()){
				//加载完图片之后载入地图
				System.out.println(" C chapterId = "+SelectView.getChapterId());
				GameChapter.chapterLoad(
						gameView.getGateAtlas(),
						gameView.getRankNum(),
						SelectView.getChapterId()
						); 
				gameView.reset();
				this.reset();
				switchType = SWITCH_LOADED;
			}
		}
		else if( switchType==SWITCH_LOADED ){
			P.decreaseDarkness();
			gameView.draw(views);
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				switchType = SWITCH_FINISH;
			}
		}
	}

	@Override
	public void reset() {
		passButtons.reset();
	}

	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		passButtons.btnLogic(points);
	}


}
