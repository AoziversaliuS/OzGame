package g.button;

import java.util.HashMap;

import g.refer.BtnMethods;
import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Rank;

public class SelectReturnButtons extends OzElement implements BtnMethods{

//	OzRect toMain
	
	public SelectReturnButtons() {
		super("SelectReturnButtons", Rank.SELF_CUSTOM, ET.SelectButtons, null, null);
		
	}

	@Override
	public void reset() {
	}

	@Override
	public void logic() {
	}

	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
	}

	@Override
	public void draw() {
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
