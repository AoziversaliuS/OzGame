package g.view;

import java.util.HashMap;

import g.button.SelectButtons;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;
import g.type.Status;

public class SelectView implements ViewInterface,BtnMethods{

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
	public void btnEnter() {
	}

	@Override
	public void btnExit() {
	}

	@Override
	public void thisToView(Status status, ViewInterface... viewInterfaces) {
		// TODO Auto-generated method stub
		
	}
}
