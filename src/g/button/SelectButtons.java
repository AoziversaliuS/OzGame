package g.button;

import java.util.ArrayList;
import java.util.HashMap;

import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;

public class SelectButtons extends OzElement{

	private ArrayList<OzRect> btns;
	
	private final float FIRST_LINE = 500;
	private final float SECOND_LINE = 300;
	private final float SPACE = 50;
	private final float LIMIT_LEFT = 100;
	
	private float selectNum = -1;
	
	public SelectButtons() {
		super("SelectButtons", Rank.SELF_CUSTOM, ET.SelectButtons, null, null);
		btns = new ArrayList<OzRect>();
		btns.add(new OzRect(LIMIT_LEFT, FIRST_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
	}

	@Override
	public void reset() {
	}

	@Override
	public void logic() {
		
	}
	public void logic(HashMap<String, OzPoint> points){
		l = points.get("0");
		boolean selected = false;
		if( l!=null ){
			for(int i=0;i<btns.size();i++){
				OzRect btn = btns.get(i);
				if(btn.inside(l, P.FORCE_RATIO)){
					selectNum = i + 1;
					selected = true;
					break;
				}
			}
		}
		if( !selected ){
			selectNum = -1;
		}
		
		
	}

	@Override
	public void draw() {
		
		for(int i=0;i<btns.size();i++){
			OzRect btn = btns.get(i);
			if( i+1==selectNum ){
				P.drawForce(btn.x, btn.y, Res.selectBtn[1]);
			}
			else{
				P.drawForce(btn.x, btn.y, Res.selectBtn[0]);
			}
		}
		
	}

	@Override
	public void impact(Player player) {
	}

	@Override
	public void prepare() {
	}

	@Override
	public boolean rollBack() {
		return false;
	}

}
