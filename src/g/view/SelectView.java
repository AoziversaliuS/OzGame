package g.view;

import java.util.HashMap;

import g.basis.GameChapter;
import g.button.SelectButtons;
import g.button.SelectReturnButtons;
import g.refer.BasicView;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;
import g.type.Status;

public class SelectView extends BasicView implements BtnMethods{

	private SelectButtons selectBtns;
	private SelectReturnButtons toMainBtn;
	public SelectView() {
		super();
		this.init();
	}

	@Override
	public void init() {
		selectBtns = new SelectButtons();
		toMainBtn = new SelectReturnButtons();
	}

	@Override
	public void engine() {
		selectBtns.logic();
	}

	@Override
	public void draw(ViewInterface ...viewInterfaces) {
		P.draw(0, 0, Res.selectBg, P.FORCE_RATIO);
		selectBtns.draw();
		toMainBtn.draw();
	}
	/**
	 * 获取当前选中的关卡Id
	 * */
	public int getChapterId(){
		return selectBtns.getChapterId();
	}

	
	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		selectBtns.logic(points);
		toMainBtn.btnLogic(points);
	}
	
	public void toNextChapter(){
		selectBtns.toNextChapter();
	}


	@Override
	public void thisToView(Status toStatus, ViewInterface... views) {
		switch (toStatus) {
		
		case Game:{ toGameView(views); break; }
			
		case Start:{ toStartView(views); break; }

		}
	}
	private void toStartView(ViewInterface... views){
		StartView startView = (StartView) views[3];
		if( switchType==SWITCH_PREPARE ){
			P.increaseDarkness();
			this.draw();
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
			}
		}
		else if( switchType==SWITCH_LOADING ){
			//释放资源
//			Res.unload(Res.GAME_A);
			selectBtns.reset();
			switchType = SWITCH_LOADED;
		}
		else if( switchType==SWITCH_LOADED ){
			P.decreaseDarkness();
			startView.draw();
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				switchType = SWITCH_FINISH;
			}
		}
	}
	private void toGameView( ViewInterface... views){
			GameView gameView = (GameView) views[0];
		if( switchType==SWITCH_PREPARE ){
			P.increaseDarkness();
			this.draw();
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
				Res.prepare(Res.GAME_A);
			}
		}
		else if( switchType==SWITCH_LOADING ){
			this.draw();
			if(Res.update()){
				//加载完图片之后载入地图
				GameChapter.chapterLoad(
						gameView.getGateAtlas(),
						gameView.getRankNum(),
						this.getChapterId()
						); 
				switchType = SWITCH_LOADED;
			}
		}
		else if( switchType==SWITCH_LOADED ){
			P.decreaseDarkness();
			gameView.draw();
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				switchType = SWITCH_FINISH;
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
