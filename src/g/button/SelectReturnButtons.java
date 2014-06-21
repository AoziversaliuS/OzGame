package g.button;

import java.util.HashMap;

import g.basis.MainEntry;
import g.refer.BtnMethods;
import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;
import g.type.Status;

public class SelectReturnButtons extends OzElement implements BtnMethods{

	private OzRect toMainBtn;
	private boolean seleted;
	
	public SelectReturnButtons() {
		super("SelectReturnButtons", Rank.SELF_CUSTOM, ET.SelectButtons, null, null);
		float x = P.BASIC_SCREEN_WIDTH-Res.selectToMainBtn[0].getWidth();
		float y = P.BASIC_SCREEN_HEIGHT-Res.selectToMainBtn[0].getHeight();
		toMainBtn = new OzRect(x, y, Res.selectToMainBtn[0].getWidth(), Res.selectToMainBtn[0].getHeight());
		this.reset();
	}

	@Override
	public void reset() {
		seleted = false;
	}

	@Override
	public void logic() {
	}

	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		l = points.get("0");
		if(l!=null && toMainBtn.inside(l, P.FORCE_RATIO)){
			seleted = true;
		}
		else{
			if(seleted){
				seleted = false;
				MainEntry.setToStatus(Status.Start);
			}
		}
	}

	@Override
	public void draw() {
		if(!seleted){
			P.draw(toMainBtn.x, toMainBtn.y, Res.selectToMainBtn[0]);
		}
		else{
			P.draw(toMainBtn.x, toMainBtn.y, Res.selectToMainBtn[1]);
		}
	}

	@Override
	public void impact(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean rollBack() {
		// TODO Auto-generated method stub
		return false;
	}


}
