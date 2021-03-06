package g.refer;

import g.button.GameButtons;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Plane;
import g.type.Rank;
import g.type.Vertical;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;


public class Player extends OzElement{

	
	private static final float MAX_MOVE    = 7;                //玩家水平移动速度
	private static final float MAX_GRAVITY = 8;                //重力
	private  static final float MAX_JUMP    = 8;               //跳跃的速度
	
	private static float moveSpeed = MAX_MOVE;
	private static float gravitySpeed = MAX_GRAVITY;
	private static float jumpSpeed = MAX_JUMP;
	private static boolean gravityFinish = false; //用于解除BUG
	
	public  static final int JumpTimeMAX = 25;//跳跃的最大时间
	private static int JumpTimeCount = 0;  //跳跃的时间计数
	
	private  static OzPoint L = new OzPoint();     //对外传输玩家坐标
	
	public static final int ALIVE = 100 /**活着*/,
			                DEAD_START=101 , DEADING=102,  DEAD_END=103,    /**死亡开始和结束*/
			                REVIVE_START=104,REVIVEING=105 , REVIVE_END=106 /**复活开始和结束*/
			                ,STOPPING=107;
	private  static  int condition = Player.ALIVE;//玩家当前所处的状态
	
	public static final int HIT_BASIC = 70        /**碰到静止的物体*/, 
			                HIT_Moving = 71       /**碰到移动的物体*/, 
			                HIT_ELSE = 72;        /**其余情况(没碰到物体时的情况)*/
	private static  int plane_HitType = HIT_ELSE;    //水平碰撞类型
	private static int vertical_HitType = HIT_ELSE; //垂直碰撞类型
	
	public static final int DIR_LEFT=0,DIR_RIGHT=1;
	/**
	 * 玩家上一次的水平方向
	 * */
	private int lastDir = DIR_RIGHT;
	
	private static float scaleSize = 1f; //死亡和复活时的图片缩放参数
	
	public static float moveSpeed(){
		return moveSpeed;
	}
	public static float jumpSpeed(){
		return jumpSpeed;
	}
	public static float gravity(){
		return gravitySpeed;
	}
	
	
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
	/**
	 * 获得玩家所处的状态
	 * */
	public static int getCondition() {
		return condition;
	}
	/**
	 * condition==ALIVE 时返回true
	 * */
	public static boolean isAlive(){
		if( condition==ALIVE ){
			return true;
		}
		return false;
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
	private  static final float dY = 2;      //不出现穿墙状况的最低值 
	private  static final float dX = 2;      //不出现穿墙状况的最低值 
	
	private static boolean jump = false;
	
	public Player() {
		super(
				"Player",
				Rank.SELF_CUSTOM, 
				ET.Player,
				new OzPoint( P.BASIC_SCREEN_WIDTH/2-Res.player[0].getWidth()/2 , P.BASIC_SCREEN_HEIGHT/2-Res.player[0].getHeight()/2),
				new OzRect(0, 0,Res.player[0].getWidth(),Res.player[0].getHeight())
		);
		Gdx.app.log("show", "玩家坐标: "+(P.getRatioX()*l.x)+" , "+(P.getRatioY()*l.y));
		this.reset();
	}

	@Override
	public void reset() {
		jump = false;
		condition = Player.ALIVE;
		push_X = 0;
		push_Y = 0;
		verticalT =  Vertical.Else;
		planeT    =  Plane.Else;
		scaleSize = 1f;
		plane_HitType = HIT_ELSE;  
		vertical_HitType = HIT_ELSE;
		JumpTimeCount = 0;
		gravityFinish = false;
		moveSpeed = MAX_MOVE;
		gravitySpeed = MAX_GRAVITY;
		jumpSpeed = MAX_JUMP;
		lastDir = DIR_RIGHT;
	}
	
	@Override
	public void logic() {
		if( condition==DEAD_START ){
			condition = DEADING;
			System.out.println("正在死亡: "+condition);
		}
		else if( condition==DEADING ){
			scaleSize = scaleSize + 0.1f;
//			System.out.println("scaleSize = "+scaleSize);
			if( scaleSize>3 ){
				condition = DEAD_END;
			}
		}
		else if( condition==REVIVE_START ){
			//condition从 DEAD_END 切换到 REVIVE_START 是gameView里做的
			condition = REVIVEING;
		}
		else if( condition==REVIVEING ){
			scaleSize = scaleSize - 0.1f;
			if( scaleSize<=1 ){
				condition = REVIVE_END;
			}
		}
		else if( condition==REVIVE_END ){
//			this.reset();//复活之后重设状态
			condition = ALIVE;
		}
		
		
	}
	




	@Override
	public void draw() {
		if( condition==ALIVE ){
			if(GameButtons.getArrow()==GameButtons.A_Left){
				lastDir = DIR_LEFT;
				P.draw(l, Res.player[0] ,P.AUTO_RATIO);
			}
			else if(GameButtons.getArrow()==GameButtons.A_Right){
				lastDir = DIR_RIGHT;
				P.draw(l, Res.player[1] ,P.AUTO_RATIO);
			}
			else{
				P.draw(l, Res.player[lastDir] ,P.AUTO_RATIO);
			}
		}
		else if( condition==DEADING ){
			P.draw(scaleSize, l,  Res.player[lastDir]);
		}
		else if( condition==REVIVEING ){
			P.draw(scaleSize, l,  Res.player[lastDir]);
		}
		else if( condition==STOPPING ){
			P.draw(scaleSize, l,Res.player[lastDir]);
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
			if(GameButtons.getSkill() == GameButtons.S_Jump && verticalT == Vertical.Top){
				jump = true;
			}
			if( verticalT == Vertical.Top ){
				gravityFinish = false;
			}
			if( gravitySpeed<MAX_GRAVITY ){
				gravitySpeed++;
//				System.out.println("gravitySpeed="+gravitySpeed);
			}
			if(verticalT == Vertical.Bottom){  //碰到元素顶部则设跳跃状态为false
				jump = false;
				jumpSpeed = MAX_JUMP;
				if( gravityFinish ){//如果碰到底部后，第一次重力移动若不能使小球离开底部，则会再次进入这里，为避免重置重力，故加判断
					gravitySpeed = 0;
				}
			}
			else if( jump == true && JumpTimeCount < JumpTimeMAX){
				JumpTimeCount++;
				if( JumpTimeCount>JumpTimeMAX-MAX_JUMP ){
					jumpSpeed--;
				}
			}
			else if( JumpTimeCount == JumpTimeMAX ){
				jumpSpeed = MAX_JUMP;
				gravitySpeed = 0;
				 jump = false;
				JumpTimeCount = 0;  //如果玩家当前状态不是跳跃状态，则重置跳跃时间计数，为下次跳跃做准备。
			}
			else if(jump==false){
				JumpTimeCount = 0;
			}
//			System.out.println("gravitySpeed="+gravitySpeed);
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
	
	public static void setScaleSize(float scaleSize) {
		Player.scaleSize = scaleSize;
	}
	
	
	

}
