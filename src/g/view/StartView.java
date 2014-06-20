package g.view;

import java.util.HashMap;

import g.button.StartButtons;
import g.refer.BasicView;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;
import g.type.Status;

public class StartView extends BasicView implements BtnMethods{

	private StartButtons startBtns;

	public StartView() {
		super();
		this.init();
	}

	@Override
	public void init() {
		startBtns = new StartButtons();
	}

	@Override
	public void engine() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(ViewInterface... viewInterfaces) {
		P.draw(0, 0, Res.startBg, P.FORCE_RATIO);
		startBtns.draw();
	}
	
	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		startBtns.logic(points);
	}

	@Override
	public void btnEnter() {
	}

	@Override
	public void btnExit() {
	}

	@Override
	public void thisToView(Status toStatus, ViewInterface... viewInterfaces) {
		switch (toStatus) {
		
		case Select: { toSelectView(viewInterfaces); break; }
			

		}
	}
	private void toSelectView( ViewInterface... viewInterfaces ){
		SelectView selectView = (SelectView) viewInterfaces[2];
		if( switchType==SWITCH_PREPARE ){
			P.increaseDarkness();
			this.draw();
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
			}
		}
		else if( switchType==SWITCH_LOADING ){
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


}
