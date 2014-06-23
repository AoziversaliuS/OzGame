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
	private OzRect btnRestart;
	private OzRect btnToSelect;
	private OzRect btnToMain;
	
	private int selected;
	private static final int RESUME=1,RESTART=2,TO_SELECT=3,TO_MAIN=4,ELSE=5;

	public PauseButtons() {
		super("PauseButtons", Rank.SELF_CUSTOM, ET.PauseButtons,null, null);
		btnResume = new OzRect(0,550, Res.pause_btnResume[0].getWidth(), Res.pause_btnResume[0].getHeight());
		btnRestart = new OzRect(0, 400, Res.pass_btnRestart[0].getWidth(), Res.pass_btnRestart[0].getHeight());
		btnToSelect = new OzRect(0,250,Res.pause_btnToSelect[0].getWidth(),Res.pause_btnToSelect[0].getHeight());
		btnToMain = new OzRect(0,100, Res.pause_btnToMain[0].getWidth(), Res.pause_btnToMain[0].getHeight());
		
		this.reset();
	}

	@Override
	public void reset() {
		selected = ELSE;
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
			else if( btnRestart.inside(l, P.FORCE_RATIO) ){
				selected = RESTART;
			}
			else if( btnToSelect.inside(l, P.FORCE_RATIO) ){
				selected = TO_SELECT;
			}
			else if( btnToMain.inside(l, P.FORCE_RATIO) ){
				selected = TO_MAIN;
			}
			else{
				selected = ELSE;
			}
		}
		else{
			if(selected!=ELSE){
				if( selected==RESUME ){
					MainEntry.setToStatus(Status.Game);
				}
				else if( selected==RESTART ){
					
				}
				else if( selected==TO_SELECT ){
					MainEntry.setToStatus(Status.Select);
				}
				else if( selected==TO_MAIN ){
					MainEntry.setToStatus(Status.Start);
				}
				selected = ELSE;
			}
		}
	}

	@Override
	public void draw() {
		
		if( selected==ELSE ){
			P.draw(btnResume.x, btnResume.y, Res.pause_btnResume[0]);
			P.draw(btnRestart.x, btnRestart.y, Res.pause_btnRestart[0]);
			P.draw(btnToSelect.x, btnToSelect.y, Res.pause_btnToSelect[0]);
			P.draw(btnToMain.x, btnToMain.y, Res.pause_btnToMain[0]);
		}
		else if( selected==RESUME ){
			P.draw(btnResume.x, btnResume.y, Res.pause_btnResume[1]);
			P.draw(btnRestart.x, btnRestart.y, Res.pause_btnRestart[0]);
			P.draw(btnToSelect.x, btnToSelect.y, Res.pause_btnToSelect[0]);
			P.draw(btnToMain.x, btnToMain.y, Res.pause_btnToMain[0]);
		}
		else if( selected==RESTART ){
			P.draw(btnResume.x, btnResume.y, Res.pause_btnResume[0]);
			P.draw(btnRestart.x, btnRestart.y, Res.pause_btnRestart[1]);
			P.draw(btnToSelect.x, btnToSelect.y, Res.pause_btnToSelect[0]);
			P.draw(btnToMain.x, btnToMain.y, Res.pause_btnToMain[0]);
		}
		else if( selected==TO_SELECT ){
			P.draw(btnResume.x, btnResume.y, Res.pause_btnResume[0]);
			P.draw(btnRestart.x, btnRestart.y, Res.pause_btnRestart[0]);
			P.draw(btnToSelect.x, btnToSelect.y, Res.pause_btnToSelect[1]);
			P.draw(btnToMain.x, btnToMain.y, Res.pause_btnToMain[0]);
		}
		else if( selected==TO_MAIN ){
			P.draw(btnResume.x, btnResume.y, Res.pause_btnResume[0]);
			P.draw(btnRestart.x, btnRestart.y, Res.pause_btnRestart[0]);
			P.draw(btnToSelect.x, btnToSelect.y, Res.pause_btnToSelect[0]);
			P.draw(btnToMain.x, btnToMain.y, Res.pause_btnToMain[1]);
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
