package g.view;

import java.util.HashMap;

import g.button.PauseButtons;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.OzPoint;
import g.tool.P;

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

}
