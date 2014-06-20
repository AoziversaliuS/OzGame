package g.button;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;

import g.basis.MainEntry;
import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;
import g.type.Status;

public class PauseButtons extends OzElement{

	private OzRect btnResume;
	private OzRect btnToSelect;
	private OzRect btnToMain;
	private OzRect btnExit;
	
	private boolean submit;
	private int selected;
	private static final int RESUME=1,TO_SELECT=2,TO_MAIN=3,EXIT=4,ELSE=5;

	public PauseButtons() {
		super("PauseButtons", Rank.SELF_CUSTOM, ET.PauseButtons,null, null);
		btnResume = new OzRect(0,600, Res.pause_btnResume[0].getWidth(), Res.pause_btnResume[0].getHeight());
		btnToSelect = new OzRect(0,480,Res.pause_btnToSelect[0].getWidth(),Res.pause_btnToSelect[0].getHeight());
		btnToMain = new OzRect(0,360, Res.pause_btnToMain[0].getWidth(), Res.pause_btnToMain[0].getHeight());
		btnExit = new OzRect(0,240, Res.pause_btnExit[0].getWidth(), Res.pause_btnExit[0].getHeight());
		
		this.reset();
	}

	@Override
	public void reset() {
		selected = ELSE;
		submit = false;
	}

	@Override
	public void logic() {
	}
	public void logic(HashMap<String, OzPoint> points){
		l = points.get("0");
		if(l!=null){
			if( btnResume.inside(l, P.FORCE_RATIO) ){
				selected = RESUME;
			}
			else if( btnToSelect.inside(l, P.FORCE_RATIO) ){
				selected = TO_SELECT;
			}
			else if( btnToMain.inside(l, P.FORCE_RATIO) ){
				selected = TO_MAIN;
			}
			else if( btnExit.inside(l, P.FORCE_RATIO) ){
				selected = EXIT;
			}
			else{
				selected = ELSE;
			}
		}
		else{
			if(selected!=ELSE){
				submit = true;
			}
		}
		
		if(submit){
			active();
		}
	}
	private void active(){
		if( selected==RESUME ){
			MainEntry.setToStatus(Status.Game);
		}
		else if( selected==EXIT ){
			Gdx.app.exit();
		}
		submit = false;
		selected = ELSE;
	}

	@Override
	public void draw() {
		
		if( selected==ELSE ){
			P.draw(btnResume.x, btnResume.y, Res.pause_btnResume[0]);
			P.draw(btnToSelect.x, btnToSelect.y, Res.pause_btnToSelect[0]);
			P.draw(btnToMain.x, btnToMain.y, Res.pause_btnToMain[0]);
			P.draw(btnExit.x, btnExit.y, Res.pause_btnExit[0]);
		}
		else if( selected==RESUME ){
			P.draw(btnResume.x, btnResume.y, Res.pause_btnResume[1]);
			P.draw(btnToSelect.x, btnToSelect.y, Res.pause_btnToSelect[0]);
			P.draw(btnToMain.x, btnToMain.y, Res.pause_btnToMain[0]);
			P.draw(btnExit.x, btnExit.y, Res.pause_btnExit[0]);
		}
		else if( selected==TO_SELECT ){
			P.draw(btnResume.x, btnResume.y, Res.pause_btnResume[0]);
			P.draw(btnToSelect.x, btnToSelect.y, Res.pause_btnToSelect[1]);
			P.draw(btnToMain.x, btnToMain.y, Res.pause_btnToMain[0]);
			P.draw(btnExit.x, btnExit.y, Res.pause_btnExit[0]);
		}
		else if( selected==TO_MAIN ){
			P.draw(btnResume.x, btnResume.y, Res.pause_btnResume[0]);
			P.draw(btnToSelect.x, btnToSelect.y, Res.pause_btnToSelect[0]);
			P.draw(btnToMain.x, btnToMain.y, Res.pause_btnToMain[1]);
			P.draw(btnExit.x, btnExit.y, Res.pause_btnExit[0]);
		}
		else if( selected==EXIT ){
			P.draw(btnResume.x, btnResume.y, Res.pause_btnResume[0]);
			P.draw(btnToSelect.x, btnToSelect.y, Res.pause_btnToSelect[0]);
			P.draw(btnToMain.x, btnToMain.y, Res.pause_btnToMain[0]);
			P.draw(btnExit.x, btnExit.y, Res.pause_btnExit[1]);
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
