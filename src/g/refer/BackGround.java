package g.refer;


import g.tool.OzPoint;
import g.tool.P;
import g.type.ET;
import g.type.Rank;


public class BackGround extends OzElement{

	public BackGround(String Tag) {
		super(Tag,Rank.BACK_GROUND, ET.BackGround,new OzPoint(),null);
	}
	

	@Override
	public void reset() {
	}


	@Override
	public void logic() {
		
	}
	
	@Override
	public void draw() {
		P.drawBg(l.x, l.y , P.backGround);
	}


	@Override
	public void impact(Player player) {
	}


	@Override
	public boolean rollBack() {
		// TODO Auto-generated method stub
		return false;
	}

	

}
