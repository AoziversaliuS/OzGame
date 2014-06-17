package g.e.architecture;

import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;

public class Thorn extends BasicBody {

	public Thorn(String Tag, OzPoint l, OzRect entityOffset) {
		super(Tag, Rank.BUILD, ET.Thorn, l, entityOffset);
	}
	public Thorn(String Tag,float x,float y){
		super(Tag, Rank.BUILD, ET.Thorn, new OzPoint(x, y),new OzRect(0, 0, Res.thorn.getWidth(),Res.thorn.getHeight()));
	}
	
	

	@Override
	public void logic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		P.draw(l, Res.thorn);
	}

	@Override
	public void impact(Player player) {
		if(hit(player) && Player.getCondition()==Player.ALIVE ){
			Player.setCondition(Player.DEAD_START);
		}
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
