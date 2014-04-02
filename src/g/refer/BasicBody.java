package g.refer;

import g.button.GameButton;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Plane;
import g.type.Vertical;


public abstract class BasicBody extends OzElement {

    public OzPoint startPoint; //该物体最初所在的位置，用于重生时，地图位置还原
    public static final float rollBackRate = 100f;
    public float range;
    public boolean  selectedRange = false;
	
	public BasicBody(String Tag, int Rank, ET type, OzPoint l,OzRect entityOffset) {
		super(Tag, Rank, type, l, entityOffset);
		startPoint = new OzPoint(l.x, l.y);
	}

	
	

	
	
	//在这里进行水平移动运算  【玩家向左移动，向右移动】            时相对运动
	@Override
	public void planeLogic() {
		//玩家向左移动
		if(GameButton.getArrow() == GameButton.A_Left && Player.getPlaneT() != Plane.Right){
			l.x = l.x + Player.VALUE_MOVE;
		}
		//玩家向右移动
		else if(GameButton.getArrow() == GameButton.A_Right && Player.getPlaneT() != Plane.Left){
			l.x = l.x - Player.VALUE_MOVE;
		}
	}
	//垂直移动运算 玩家【跳跃，下坠】时的相对运动
	@Override
	public void verticalLogic() {
		//跳跃状态
		if( Player.isJump()==true ){
			l.y = l.y - Player.VALUE_JUMP;
		}
		//下坠状态
		else if( (Player.getVerticalT()==Vertical.Else || Player.getVerticalT()==Vertical.Bottom) ){
			l.y = l.y + Player.VALUE_GRAVITY;
		}
		//站在陆地状态
		else if( Player.getVerticalT()==Vertical.Top ){
			//停止下坠,坐标不改变就是停止下坠的状态
		}
	    
	}

	@Override
	public void reset() {
		//重置坐标
		l.x = startPoint.x;
		l.y = startPoint.y;
	}
	public boolean rollBack(){
		float a = startPoint.x - l.x;
		float b = startPoint.y - l.y;
		float c;
		float sinY;
		float cosX;
		
		c = (float) Math.sqrt( a*a+b*b );
		
		setRange(c);//只在复活移动最开头设置一次range。
		
		if( c<=range ){
			l.x = startPoint.x;
			l.y = startPoint.y;
			selectedRange = false; //复活移动完成后，selectedRange设为false;
			return true;
		}
		else{
			cosX = a/c;
			sinY = b/c;
			float dx = range*cosX;
			float dy = range*sinY;
			l.x = l.x+dx;
			l.y = l.y+dy;
		}
		return false;
	}
	
	public void setRange(float c){
		if(selectedRange==false){
			range = c/rollBackRate;
			selectedRange = true;  //复活移动完成后，selectedRange要重新设为false;
		}
	}

}
