package g.refer;

import g.button.GameButtons;
import g.e.architecture.MoveLand;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Move;
import g.type.Plane;
import g.type.Vertical;


public abstract class BasicBody extends OzElement {

	/**
	 * 该物体最初所在的位置，用于重生时，地图位置还原
	 * */
    public OzPoint _startPoint; 
    /**
     * 复活回滚时的速度
     * */
    public static final float _rollBackRate = 25f;
    /**
     * rollBack时的误差范围限制
     * */
    public static final float _deviation = 0.1f; //误差
    /**
     * rollBack时每一瞬间应该移动的斜线的距离
     * */
    public float _range = 5;
    /**
     * 是否已经设定了rollback要用到的range
     * */
    public boolean  _rangeSelected = false;
    /**
     * 是否使用rollBack
     * */
    public boolean _pushBackAvailable = true;
    
    /**
     * 若返回false,此子类不需使用pushBack功能
     * */
    public boolean isPushBackAvailable() {
		return _pushBackAvailable;
	}

    /**玩家碰到的moveLand*/
	public static MoveLand MoveLand_hitting;
    
    public float a;
    public float b;
    public float c;//回滚时用到的3个重要变量 勾股定理
    
    
	public BasicBody(String Tag, int Rank, ET type, OzPoint l,OzRect entityOffset) {
		super(Tag, Rank, type, l, entityOffset);
		_startPoint = new OzPoint(l.x, l.y);
	}

	
	

	
	
	//在这里进行水平移动运算  【玩家向左移动，向右移动】            时相对运动
	@Override
	public void planeLogic() {
		
		if( Player.getPlane_HitType()==Player.HIT_BASIC || Player.getPlane_HitType()==Player.HIT_ELSE ){
			//玩家向左移动
			if(GameButtons.getArrow() == GameButtons.A_Left && Player.getPlaneT() != Plane.Right){
				l.x = l.x + Player.moveSpeed();
			}
			//玩家向右移动
			else if(GameButtons.getArrow() == GameButtons.A_Right && Player.getPlaneT() != Plane.Left){
				l.x = l.x - Player.moveSpeed();
			}
		}
		else if( Player.getPlane_HitType()==Player.HIT_Moving ){
			if( MoveLand_hitting.mT==Move.vertical ){
				//玩家向左移动
				if(GameButtons.getArrow() == GameButtons.A_Left && Player.getPlaneT() != Plane.Right){
					l.x = l.x + Player.moveSpeed();
				}
				//玩家向右移动
				else if(GameButtons.getArrow() == GameButtons.A_Right && Player.getPlaneT() != Plane.Left){
					l.x = l.x - Player.moveSpeed();
				}
			}
			else if( MoveLand_hitting.mT==Move.plane ){
				float mlSpeed = MoveLand_hitting.speed;
				//玩家向左移动
				if(GameButtons.getArrow()==GameButtons.A_Left && Player.getPlaneT()==Plane.Right && mlSpeed<0 ){
					l.x = l.x - mlSpeed;
				}
				//玩家向右移动
				else if(GameButtons.getArrow()==GameButtons.A_Right && Player.getPlaneT()==Plane.Left && mlSpeed>0 ){
					l.x = l.x - mlSpeed;
				}
				else if( GameButtons.getArrow()==GameButtons.A_Left && Player.getPlaneT()!=Plane.Right ){
					l.x = l.x + Player.moveSpeed();
				}
				else if( GameButtons.getArrow()==GameButtons.A_Right && Player.getPlaneT()!=Plane.Left ){
					l.x = l.x - Player.moveSpeed();
				}
			}
		}
		
		//水平方向上碰到移动方块
		if( Player.getPlane_HitType()==Player.HIT_Moving ){
			
			if( MoveLand_hitting.mT==Move.vertical ){
				//玩家向左移动
				if(GameButtons.getArrow() == GameButtons.A_Left && Player.getPlaneT() != Plane.Right){
					l.x = l.x + Player.moveSpeed();
				}
				//玩家向右移动
				else if(GameButtons.getArrow() == GameButtons.A_Right && Player.getPlaneT() != Plane.Left){
					l.x = l.x - Player.moveSpeed();
				}
			}
			else if( MoveLand_hitting.mT==Move.plane ){
				
				if( Player.getPlaneT()==Plane.Left && MoveLand_hitting.speed<0 ){
					l.x = l.x - MoveLand_hitting.speed;
				}
				else if( Player.getPlaneT()==Plane.Right && MoveLand_hitting.speed>0 ){
					l.x = l.x - MoveLand_hitting.speed;
//					System.out.println("MoveLand_hitting.speed= "+MoveLand_hitting.speed);
				}
			}
			
		}
		
	}
	//垂直移动运算 玩家【跳跃，下坠】时的相对运动
	@Override
	public void verticalLogic() {
		//跳跃状态
		if( Player.isJump()==true ){
			l.y = l.y - Player.jumpSpeed();
//			System.out.println("跳跃");
		}
		//下坠状态             碰到移动方块和静止方块底部都是这个设置
		else if( (Player.getVerticalT()==Vertical.Else || Player.getVerticalT()==Vertical.Bottom) ){
			l.y = l.y + Player.gravity();
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

	
	//玩家死亡后回滚建筑坐标
	public boolean rollBack(){
		
		float sinY;
		float cosX;
		
		a = _startPoint.x - l.x;
		b = _startPoint.y - l.y;
		c = (float) Math.sqrt( a*a+b*b );
		
		setRange();//只在复活移动最开头设置一次range。
		if( Math.abs(c-_range)<_deviation || c<_range ){
			l.x = _startPoint.x;
			l.y = _startPoint.y;
			_rangeSelected = false; //复活移动完成后，selectedRange设为false;
//			System.out.println("-------------------------");
			return true;
		}
		else{
			cosX = a/c;
			sinY = b/c;
			float dx = _range*cosX;
			float dy = _range*sinY;
			l.x = l.x+dx;
			l.y = l.y+dy;
		}
		return false;
	}
	
	public void setRange(){
		if(_rangeSelected==false){
			_range = c/_rollBackRate;
			if( _range<10f ){
				_range = 10f;
			}
			_rangeSelected = true;  //复活移动完成后，selectedRange要重新设为false;
		}
	}
	
	
}
