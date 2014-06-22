package g.button;

import java.util.HashMap;

import g.refer.BtnMethods;
import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;

public class PassButtons extends OzElement implements BtnMethods{

	private OzRect btnNext;
	private OzRect btnRestart;
	
	private int selected;
	private final int NEXT = 1, RESTART = 2, ELSE = 3;
	
	public PassButtons() {
		super("PassButtons", Rank.SELF_CUSTOM, ET.PassButtons, null, null);
	
		float basicX = P.BASIC_SCREEN_WIDTH - Res.pass_btnNext[0].getWidth();
		btnRestart = new OzRect(basicX, 400, Res.pass_btnRestart[0].getWidth(),  Res.pass_btnRestart[0].getHeight());
		btnNext = new OzRect(basicX,200, Res.pass_btnNext[0].getWidth(), Res.pass_btnNext[0].getHeight());
		this.reset();
	}

	@Override
	public void reset() {
		selected = ELSE;
	}

	@Override
	public void logic() {
	}
	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		l = points.get("0");
		if( l!=null ){
			if( btnRestart.inside(l, P.FORCE_RATIO) ){
				selected = RESTART;
			}
			else if( btnNext.inside(l, P.FORCE_RATIO) ){
				selected = NEXT;
			}
			else{
				selected = ELSE;
			}
		}
		else{
			if( selected!=ELSE ){
				active();
			}
		}
	}
	private void active(){
		if( selected==RESTART ){
			
		}
		else if( selected==NEXT ){
			
		}
	}

	@Override
	public void draw() {
		if( selected==ELSE ){
			P.draw(btnRestart.x, btnRestart.y, Res.pass_btnRestart[0]);
			P.draw(btnNext.x,btnNext.y, Res.pass_btnNext[0]);
		}
		else if( selected==RESTART ){
			P.draw(btnRestart.x, btnRestart.y, Res.pass_btnRestart[1]);
			P.draw(btnNext.x,btnNext.y, Res.pass_btnNext[0]);
		}
		else if( selected==NEXT ){
			P.draw(btnRestart.x, btnRestart.y, Res.pass_btnRestart[0]);
			P.draw(btnNext.x,btnNext.y, Res.pass_btnNext[1]);
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
