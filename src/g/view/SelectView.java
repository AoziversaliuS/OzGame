package g.view;

import java.util.HashMap;

import g.basis.GameChapter;
import g.button.SelectButtons;
import g.refer.BasicView;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;
import g.type.Status;

public class SelectView extends BasicView implements BtnMethods{

	private SelectButtons selectBtns;
	
	public SelectView() {
		super();
		this.init();
	}

	@Override
	public void init() {
		selectBtns = new SelectButtons();
	}

	@Override
	public void engine() {
		selectBtns.logic();
	}

	@Override
	public void draw(ViewInterface ...viewInterfaces) {
		P.draw(0, 0, Res.selectBg, P.FORCE_RATIO);
		selectBtns.draw();
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
	}


	@Override
	public void thisToView(Status toStatus, ViewInterface... viewInterfaces) {
		switch (toStatus) {
		
		case Game:{ toGameView(viewInterfaces); break; }
			

		}
	}
	private void toGameView( ViewInterface... viewInterfaces){
			GameView gameView = (GameView) viewInterfaces[0];
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
}
