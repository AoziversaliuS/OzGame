package g.refer;

import g.button.GameButton;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.type.ET;
import g.type.Plane;
import g.type.Rank;
import g.type.Vertical;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;


public class Player extends OzElement{

	
	public static final float VALUE_MOVE    = 7;                //玩家水平移动速度
	public static final float VALUE_GRAVITY = 8;                //重力
	
	public  static final float VALUE_JUMP    = 8;               //跳跃的速度
	public  static final int JumpTimeMAX = 25;//跳跃的最大时间
	private static int JumpTimeCount = 0;  //跳跃的时间计数
	
	private  static OzPoint L = new OzPoint();     //对外传输玩家坐标
	
	public static final int ALIVE = 0 /**活着*/,
			                DEAD_START=1 , DEADING=2,  DEAD_END=3,    /**死亡开始和结束*/
			                REVIVE_START=4,REVIVEING=5 , REVIVE_END=6 /**复活开始和结束*/;
	private  static  int condition = Player.ALIVE;//玩家当前所处的状态
	
	public static final int HIT_BASIC = 7        /**碰到静止的物体*/, 
			                HIT_Moving = 8       /**碰到移动的物体*/, 
			                HIT_ELSE = 9;        /**其余情况(没碰到物体时的情况)*/
	private static int plane_HitType = HIT_ELSE;    //水平碰撞类型
	private static int vertical_HitType = HIT_ELSE; //垂直碰撞类型
	//不必要？

	private float scaleSize = 1f; //死亡和复活时的图片缩放参数
	
	
	public static int getPlane_HitType() {
		return plane_HitType;
	}

	public static void setPlane_HitType(int plane_HitType) {
		Player.plane_HitType = plane_HitType;
	}

	public static int getVertical_HitType() {
		return vertical_HitType;
	}

	public static void setVertical_HitType(int vertical_HitType) {
		Player.vertical_HitType = vertical_HitType;
	}

	public static int getCondition() {
		return condition;
	}

	public static void setCondition(int condition) {
		Player.condition = condition;
	}

	//planeTouch
	private static Plane     planeT    =  Plane.Else;
	//verticalTouch
	private static Vertical  verticalT =  Vertical.Else;
	private  float push_X = 0;  //碰撞后将玩家位置推回到穿墙前
	private  float push_Y = 0;  //碰撞后将玩家位置推回到穿墙前
	private  float dY = 2;      //不出现穿墙状况的最低值 
	private  float dX = 2;      //不出现穿墙状况的最低值 
	
	private static boolean jump = false;
	
	public Player() {
		super(
				"Player",
				Rank.SELF_CUSTOM, 
				ET.Player,
				new OzPoint( P.BASIC_SCREEN_WIDTH/2-P.player.getWidth()/2 , P.BASIC_SCREEN_HEIGHT/2-P.player.getHeight()/2),
				new OzRect(0, 0,P.player.getWidth(),P.player.getHeight())
		);
		System.out.println("");
//		System.out.println(P.getRatioX()+"   "+P.getRatioY());
		Gdx.app.log("show", "玩家坐标: "+(P.getRatioX()*l.x)+" , "+(P.getRatioY()*l.y));
		jump = false;
		condition = Player.ALIVE;
	}

	@Override
	public void reset() {
	}
	
	@Override
	public void logic() {
		if( condition==DEAD_START ){
			condition = DEADING;
			System.out.println("正在死亡: "+condition);
		}
		else if( condition==REVIVE_START ){
			condition = REVIVEING;
		}
		else if( condition==REVIVE_END ){
			condition = ALIVE;
		}
	}
	




	@Override
	public void draw() {
		if( condition==ALIVE ){
			P.draw(l, P.player);
		}
		else if( condition==DEADING ){
			P.drawScale(scaleSize, l,  P.player);
			scaleSize = scaleSize + 0.1f;
			if( scaleSize>3 ){
				condition = DEAD_END;
			}
		}
		else if( condition==REVIVEING ){
			P.drawScale(scaleSize, l,  P.player);
			scaleSize = scaleSize - 0.1f;
			if( scaleSize<=1 ){
				condition = REVIVE_END;
			}
		}
		

	}


	
	public void resetOnBegin(){
		//状态更新不能写在重设变量这里
		push_X = 0; 
		push_Y = 0;
		
		//重置为没有碰到任何方块的状态
		planeT = Plane.Else;
		verticalT = Vertical.Else;
		
		//重设碰撞状态
		plane_HitType = HIT_ELSE;  
		vertical_HitType = HIT_ELSE;
		
		L = l; //对外传输玩家坐标
	}
	
	//玩家状态更新
	public void updateAction(){
		jumpAction();
	}
	public void jumpAction(){
		//当玩家站在陆地上且按下跳跃按键之后才可以跳跃。verticalT
			if(GameButton.getSkill() == GameButton.S_Jump && verticalT == Vertical.Top){
				jump = true;
			}
			
			
			if(verticalT == Vertical.Bottom){  //碰到元素顶部则设跳跃状态为false
				jump = false;
			}
			else if( jump == true && JumpTimeCount < JumpTimeMAX){
				JumpTimeCount++;
			}
			else{
				 jump = false;
				JumpTimeCount = 0;  //如果玩家当前状态不是跳跃状态，则重置跳跃时间计数，为下次跳跃做准备。
			}
	}
	
	public void set_VerticalT_and_PlaneT( ArrayList<OzElement>  gateAtlas){
		for(int i=0;i<gateAtlas.size();i++){
			//只能碰到某元素的4条边的其中1条
			if(gateAtlas.get(i).planeT == Plane.Left){
				
				planeT = Plane.Left;
//				Gdx.app.log("impact", "碰到了 "+planeT);
			}
			else if(gateAtlas.get(i).planeT == Plane.Right){
				
				planeT = Plane.Right;
//				Gdx.app.log("impact", "碰到了 "+planeT);
				
			}
			else if(gateAtlas.get(i).verticalT == Vertical.Top){
				
				verticalT = Vertical.Top;
				
			}
			else if(gateAtlas.get(i).verticalT == Vertical.Bottom){
				
				verticalT = Vertical.Bottom;
			}
		}
	}
	@Override
	public void impact(Player player) {
	}

	public static boolean isJump() {
		return jump;
	}
	public static Plane getPlaneT() {
		return planeT;
	}
	public static Vertical getVerticalT() {
		return verticalT;
	}

	public  float getPush_X() {
		return push_X;
	}

	public  float getPush_Y() {
		return push_Y;
	}

	public  void setPush_X(float push_X,boolean isLeftHit) {
		if(push_X - dX > 0 && isLeftHit){
			this.push_X = push_X - dX;
		}
		else if(dX + push_X<0 && !isLeftHit){
			this.push_X = push_X + dX;
		}
//		Gdx.app.log("impact", "进入水平位置微调   "+this.push_X);
//		Gdx.app.log("impact","逆推X"+ this.push_X );
	}
	public  void setPush_Y(float push_Y) {
		if(push_Y - this.dY > 0){
			this.push_Y = push_Y - this.dY;
//			Gdx.app.log("impact", "进入垂直位置微调   "+this.push_Y);
		}
	}
	public void pushBack_Y(){
		l.y = l.y + push_Y;
	}
	
	public  static OzPoint getL(){
		return L;
	}

	@Override
	public boolean rollBack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}
	

	

}
