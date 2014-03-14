package g.refer;

import g.basis.GameView;
import g.button.GameButton;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Plane;
import g.type.Vertical;


public abstract class BasicBody extends OzElement {


	public BasicBody(String Tag, int Rank, ET type, OzPoint l,
			OzRect entityOffset) {
		super(Tag, Rank, type, l, entityOffset);
	}

	
	

	
	
	//在这里进行水平移动运算  【玩家向左移动，向右移动】            时相对运动
	@Override
	public void planeLogic() {
		
		if(GameButton.getArrow() == GameButton.A_Left && Player.getPlaneT() != Plane.Right){
			l.x = l.x + Player.VALUE_MOVE;
		}
		else if(GameButton.getArrow() == GameButton.A_Right && Player.getPlaneT() != Plane.Left){
			l.x = l.x - Player.VALUE_MOVE;
		}
	}
	//垂直移动运算 玩家【跳跃，下坠】时的相对运动
	@Override
	public void verticalLogic() {
		
	    if( Player.isJump()==true &&  Player.getL().y>Player.limitUp){
			l.y = l.y - Player.VALUE_JUMP;
		}
		else if( (Player.getVerticalT()==Vertical.Else || Player.getVerticalT()==Vertical.Bottom) && Player.getL().y<Player.limitDown ){
			l.y = l.y + Player.VALUE_GRAVITY;
		}
		else if( Player.getVerticalT()==Vertical.Top ){
			//停止下坠,坐标不改变就是停止下坠的状态
		}
	    
	}

	@Override
	public void reset() {

	}

}
