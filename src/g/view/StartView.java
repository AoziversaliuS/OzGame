package g.view;

import java.util.HashMap;

import g.button.StartButtons;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;

public class StartView implements ViewInterface,BtnMethods{

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


}
