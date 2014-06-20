package g.e.architecture;

import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.type.ET;
import g.type.Rank;

public class DeadLine extends BasicBody{
		
	public DeadLine(float y) {
		super("DeadLine", Rank.SELF_CUSTOM, ET.DeadLine, new OzPoint(0, y),new OzRect(0, 0, 0,0));
	}
	@Override
	public void logic() {
	}
	@Override
	public void draw() {
		
	}
	@Override
	public void impact(Player player) {
		if(l.y > Player.getL().y ){
			//超过这条线便设置玩家状态为死亡
			if( Player.getCondition()==Player.ALIVE ){
				//因为有2次碰撞检测,
				//第1次碰撞检测后设置玩家状态死亡开始,然后玩家进行死亡中,第2次碰撞检测再设置玩家状态为死亡开始,自此进入无线循环。
				Player.setCondition(Player.DEAD_START);
			}
		}
	}

	@Override
	public void prepare() {
	}
	@Override
	public void reset() {
	}
}
