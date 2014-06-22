package g.e.item;

import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;

public class Door extends BasicBody{

	public Door(String Tag,float x,float y) {
		super(Tag, Rank.ITEM, ET.Door, new OzPoint(x, y), new OzRect(23, 0, 20, Res.door.getHeight()-30));
	}

	@Override
	public void reset() {
	}

	@Override
	public void logic() {
	}

	@Override
	public void draw() {
		P.draw(1f, l, Res.door);
	}

	@Override
	public void impact(Player player) {
		if( hit(player) ){
		}
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

}
