package g.e.item;

import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Rank;


public class ReferPoint extends BasicBody{

	public ReferPoint() {
		super("ReferPoint", Rank.BUILD_3, ET.ReferPoint, new OzPoint(0,0), new OzRect());
	}

	@Override
	public void logic() {
		refer.x = l.x;
		refer.y = l.y;
		//更新参照点
	}

	@Override
	public void draw() {
	}

	@Override
	public void impact(Player player) {
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
