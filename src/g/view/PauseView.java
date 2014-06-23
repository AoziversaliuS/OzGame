package g.view;

import java.util.HashMap;

import g.basis.GameChapter;
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
	public void draw(ViewInterface ...views) {
		//↓gameView.draw();
		views[0].draw();
		
		P.useDarkness(P.MAX_BLACK_NUM/2);
		pauseBtns.draw();
	}
	
	@Override
	public boolean enter() {
		if( pauseBtns.getBtnRestart().x<0 ){
			
			pauseBtns.getBtnRestart().x =pauseBtns.getBtnRestart().x + BTN_SPEED;
			pauseBtns.getBtnResume().x  =pauseBtns.getBtnResume().x + BTN_SPEED;
			pauseBtns.getBtnToMain().x  =pauseBtns.getBtnToMain().x + BTN_SPEED;
			pauseBtns.getBtnToSelect().x = pauseBtns.getBtnToSelect().x + BTN_SPEED;
			
			return false;
		}
		else{
			pauseBtns.getBtnRestart().x =0;
			pauseBtns.getBtnResume().x  =0;
			pauseBtns.getBtnToMain().x  =0;
			pauseBtns.getBtnToSelect().x =0;
		}
		return true;
	}

	@Override
	public boolean exit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		pauseBtns.logic(points);
	}

	


	@Override
	public void thisToView(Status toStatus, ViewInterface... views) {
		switch (toStatus) {
		
			case Game:{ 
				if( pauseBtns.isRestart() ){
					toGameView_Restart(views);
				}
				else{
					toGameView_Resume(views);
				}
				break;
			}
			
			case Select:{ toSelectView(views); break;}
			
			case Start:{ toStartView(views); break;}

		}
	}
	private void toStartView(ViewInterface ...views){
		StartView startView = (StartView) views[3];
		if( switchType==SWITCH_PREPARE ){
			P.increaseDarkness();
			this.draw(views);
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
			}
		}
		else if( switchType==SWITCH_LOADING ){
			//卸载资源
			Res.unload(Res.PAUSE_SOURCE);
			this.reset();
//			Res.unload(Res.GAME_A);
			
			switchType = SWITCH_LOADED;
		}
		else if( switchType==SWITCH_LOADED ){
			P.decreaseDarkness();
			startView.draw(views);
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				switchType = SWITCH_FINISH;
			}
		}
	}
	private void toSelectView(ViewInterface ...views){
		SelectView selectView = (SelectView) views[2];
		if( switchType==SWITCH_PREPARE ){
			P.increaseDarkness();
			this.draw(views);
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
			}
		}
		else if( switchType==SWITCH_LOADING ){
			//卸载资源
			Res.unload(Res.PAUSE_SOURCE);
			this.reset();
//			Res.unload(Res.GAME_A);
			switchType = SWITCH_LOADED;
		}
		else if( switchType==SWITCH_LOADED ){
			P.decreaseDarkness();
			selectView.draw(views);
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				switchType = SWITCH_FINISH;
			}
		}
	}
	private void toGameView_Resume(ViewInterface ...views){
		GameView gameView = (GameView) views[0];
		if( switchType==SWITCH_PREPARE ){
			P.setDarkness(P.MAX_BLACK_NUM/2);
			gameView.draw(views);
			switchType = SWITCH_LOADING;
		}
		else if( switchType==SWITCH_LOADING ){
			gameView.draw(views);
			this.reset();
			switchType = SWITCH_LOADED;
		}
		else if( switchType==SWITCH_LOADED ){
			P.decreaseDarkness();
			gameView.draw(views);
			if(P.getBlackNum()<=P.MIN_BLACK_NUM){
				switchType = SWITCH_FINISH;
			}
		}
	}
	private void toGameView_Restart(ViewInterface ...views){
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
		pauseBtns.reset();
	}



}
