package g.view;

import java.util.HashMap;

import g.button.PassButtons;
import g.refer.BasicView;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.OzPoint;
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
	public void draw(ViewInterface... viewInterfaces) {
		passButtons.draw();
	}

	@Override
	public void thisToView(Status toStatus, ViewInterface... views) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		passButtons.btnLogic(points);
	}

}
