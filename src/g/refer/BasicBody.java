package g.refer;

import g.build.MoveLand;
import g.button.GameButton;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Move;
import g.type.Plane;
import g.type.Vertical;


public abstract class BasicBody extends OzElement {

    public OzPoint startPoint; //该物体最初所在的位置，用于重生时，地图位置还原
    public static final float rollBackRate = 25f;
    public static final float deviation = 0.1f; //误差
    public float range = 5;
    public boolean  selected = false;
    
    
    public static MoveLand MoveLand_hitting;
    
    public float a;
    public float b;
    public float c;//回滚时用到的3个重要变量 勾股定理
    
    
	public BasicBody(String Tag, int Rank, ET type, OzPoint l,OzRect entityOffset) {
		super(Tag, Rank, type, l, entityOffset);
		startPoint = new OzPoint(l.x, l.y);
	}

	
	

	
	
	//在这里进行水平移动运算  【玩家向左移动，向右移动】            时相对运动
	@Override
	public void planeLogic() {
//		System.out.println("Player.getPlane_HitType()="+Player.getPlane_HitType());
		
//		if( Player.getPlane_HitType()==Player.HIT_BASIC || Player.getPlane_HitType()==Player.HIT_ELSE ){
//			//玩家向左移动
//			if(GameButton.getArrow() == GameButton.A_Left && Player.getPlaneT() != Plane.Right){
//				l.x = l.x + Player.VALUE_MOVE;
//			}
//			//玩家向右移动
//			else if(GameButton.getArrow() == GameButton.A_Right && Player.getPlaneT() != Plane.Left){
//				l.x = l.x - Player.VALUE_MOVE;
//			}
//		}
		
		//玩家向左移动
		if(GameButton.getArrow() == GameButton.A_Left && Player.getPlaneT() != Plane.Right){
			l.x = l.x + Player.VALUE_MOVE;
		}
		//玩家向右移动
		else if(GameButton.getArrow() == GameButton.A_Right && Player.getPlaneT() != Plane.Left){
			l.x = l.x - Player.VALUE_MOVE;
		}
		//水平方向上碰到移动方块
		if( Player.getPlane_HitType()==Player.HIT_Moving ){
			
			if( MoveLand_hitting.mT==Move.vertical ){
				//玩家向左移动
				if(GameButton.getArrow() == GameButton.A_Left && Player.getPlaneT() != Plane.Right){
					l.x = l.x + Player.VALUE_MOVE;
				}
				//玩家向右移动
				else if(GameButton.getArrow() == GameButton.A_Right && Player.getPlaneT() != Plane.Left){
					l.x = l.x - Player.VALUE_MOVE;
				}
			}
			else if( MoveLand_hitting.mT==Move.plane ){
//				if( Player.getPlaneT()==Plane.Left || Player.getPlaneT()==Plane.Right ){
//					l.x = l.x - MoveLand_hitting.speed;
//				}
//				if( Player.getPlaneT()==Plane.Left && MoveLand_hitting.speed>0 ){
//					l.x = l.x - Player.VALUE_MOVE;
//				}
//				else if( Player.getPlaneT()==Plane.Right && MoveLand_hitting.speed<0 ){
//					l.x = l.x - Player.VALUE_MOVE;
//				}
				System.out.println("MoveLand_hitting.speed= "+MoveLand_hitting.speed);
				if( Player.getPlaneT()==Plane.Left && MoveLand_hitting.speed>0 ){
					l.x = l.x - MoveLand_hitting.speed;
				}
				else if( Player.getPlaneT()==Plane.Right && MoveLand_hitting.speed<0 ){
					l.x = l.x - MoveLand_hitting.speed;
				}
			}
			
		}
		
	}
	//垂直移动运算 玩家【跳跃，下坠】时的相对运动
	@Override
	public void verticalLogic() {
		//跳跃状态
		if( Player.isJump()==true ){
			l.y = l.y - Player.VALUE_JUMP;
//			System.out.println("跳跃");
		}
		//下坠状态  碰到移动方块和静止方块底部都是这个设置
		else if( (Player.getVerticalT()==Vertical.Else || Player.getVerticalT()==Vertical.Bottom) ){
			l.y = l.y + Player.VALUE_GRAVITY;
//			System.out.println("下坠");
		}
		//站在陆地状态
		
		
		//到了这里就 只有 碰到 [移动方块top] 或碰到 [静止方块top] 两种情况
		else if( Player.getVerticalT()==Vertical.Top ){
			//碰到静止方块
			if( Player.getVertical_HitType()==Player.HIT_BASIC ){
				//停止下坠,坐标不改变就是停止下坠的状态
			}
			else if( Player.getVertical_HitType()==Player.HIT_Moving ){
				
					if( MoveLand_hitting.mT==Move.vertical ){
						l.y = l.y - MoveLand_hitting.speed;
					}
					else if( MoveLand_hitting.mT==Move.plane ){
						l.x = l.x - MoveLand_hitting.speed;
					}
			}
		}
	    
	}

	@Override
	public void reset() {
//		//重置坐标
//		l.x = startPoint.x;
//		l.y = startPoint.y;
	}
	
	//玩家死亡后回滚建筑坐标
	public boolean rollBack(){
		
		float sinY;
		float cosX;
		
		a = startPoint.x - l.x;
		b = startPoint.y - l.y;
		c = (float) Math.sqrt( a*a+b*b );
		
		setRange();//只在复活移动最开头设置一次range。
//		range = 5;
//		System.out.println("c="+c+"  range="+range + " selectedRange="+selected);
		if( Math.abs(c-range)<deviation || c<range ){
			l.x = startPoint.x;
			l.y = startPoint.y;
			selected = false; //复活移动完成后，selectedRange设为false;
//			System.out.println("-------------------------");
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
	
	public void setRange(){
		if(selected==false){
			range = c/rollBackRate;
			if( range<10f ){
				range = 10f;
			}
			selected = true;  //复活移动完成后，selectedRange要重新设为false;
		}
	}

}
