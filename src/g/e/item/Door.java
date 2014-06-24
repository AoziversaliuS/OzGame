package g.e.item;

import g.basis.MainEntry;
import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;
import g.type.Status;
import g.view.SelectView;

public class Door extends BasicBody{

	
	private static final int ACTIVE_NOT=0,ACTIVE_PLAYER=1,ACTIVE_DOOR=2,ACTIVE_FINISH=3;
	
	private int activeType = ACTIVE_NOT;
	/**玩家图片的大小*/
	private float playerSize;
	private float doorSize;
	private static final float dSize = 0.1f;
	
	public Door(String Tag,float x,float y) {
		super(Tag, Rank.ITEM, ET.Door, new OzPoint(x, y), new OzRect(23, 0, 20, Res.item_door.getHeight()-30));
		this.reset();
	}

	@Override
	public void reset() {
		playerSize = 1f;
		doorSize = 1f;
		activeType = ACTIVE_NOT;
	}

	@Override
	public void logic() {
		if( activeType==ACTIVE_PLAYER ){
			playerSize = playerSize - dSize;
			if( playerSize<0f ){
				playerSize = 0f;
				activeType = ACTIVE_DOOR;
			}
			Player.setScaleSize(playerSize);
		}
		else if( activeType==ACTIVE_DOOR ){
			doorSize =  doorSize - dSize;
			if( doorSize<0f ){
				doorSize = 0;
				activeType = ACTIVE_FINISH;
			}
		}
		else if( activeType==ACTIVE_FINISH ){
			//解锁下一关卡
			SelectView.unlockNextChapter(false);
			MainEntry.setToStatus(Status.Pass);
		}
	}

	@Override
	public void draw() {
		P.draw(1f, doorSize,l, Res.item_door);
	}

	@Override
	public void impact(Player player) {
		if( hit(player) ){
			if( activeType==ACTIVE_NOT && Player.isAlive() ){
				//让小球处于无法操控状态
				Player.setCondition(Player.STOPPING);
				activeType = ACTIVE_PLAYER;
			}

		}
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

}
