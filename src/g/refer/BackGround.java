package g.refer;

import com.badlogic.gdx.graphics.g2d.Sprite;

import g.tool.OzPoint;
import g.tool.P;
import g.type.ET;
import g.type.Rank;


public class BackGround extends OzElement{

	private Sprite sprite;
	public BackGround(String Tag) {
		super(Tag,Rank.BACK_GROUND, ET.BackGround,new OzPoint(),null);
		sprite = new Sprite(P.backGround.getSprite());
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

	

}
