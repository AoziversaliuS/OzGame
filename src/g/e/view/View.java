package g.e.view;

import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPicture;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.type.ET;
import g.type.Rank;

public class View extends BasicBody{

	OzPicture viewPic;
	
	public View(String Tag,float x,float y,OzPicture viewPic) {
		super(Tag, Rank.VIEW_1, ET.View, new OzPoint(x, y), null);
		this.viewPic = viewPic;
	}

	@Override
	public void logic() {
	}

	@Override
	public void draw() {
		P.draw(l, viewPic,P.AUTO_RATIO);
	}

	@Override
	public void impact(Player player) {
	}

	@Override
	public void prepare() {
	}

	@Override
	public void reset() {
	}

}
