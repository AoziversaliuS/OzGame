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
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Player extends OzElement{

	private Sprite sprite;
	
	public static final float VALUE_MOVE    = 7;                //玩家水平移动速度
	public static final float VALUE_GRAVITY = 11;                //重力
	public static final float limitUp = 520;
	public static final float limitDown = 200;
	
	public  static final float VALUE_JUMP    = 11;  //跳跃的速度
	public  static OzPoint L = new OzPoint();     //对外传输玩家坐标
	public  static final int JumpTimeMAX = 35;
	private static int JumpTimeCount = 0;  //跳跃的时间
	
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
				new OzPoint(400, 400),
//				new RectF(0, 0,P.Game_Player.basicWidth,P.Game_Player.basicHeight)
				new OzRect(0, 0,P.player.getWidth(),P.player.getHeight())
		);
//		Gdx.app.log("impact", this.entity);
		sprite = new Sprite(P.player.getSprite());
		jump = false;
	}

	@Override
	public void reset() {
	}
	
	@Override
	public void logic() {
		
	}
	

	@Override
	public void planeLogic() {
//		if(GameButton.getArrow() == GameButton.A_Left){
//			l.x = l.x - MOVE_SPEED;
//		}
//		else if(GameButton.getPressO() == GamePressO.Right){
//			l.x = l.x + MOVE_SPEED;
//		}
	}

	@Override
	public void verticalLogic() {
	    if( Player.isJump()==true && l.y<Player.limitUp){
			l.y = l.y + Player.VALUE_JUMP;
		}
		else if(Player.isJump()==false && (Player.getVerticalT()==Vertical.Else || Player.getVerticalT()==Vertical.Bottom) && l.y>Player.limitDown){
			l.y = l.y - Player.VALUE_GRAVITY;
		}
		else if( Player.getVerticalT()==Vertical.Top ){
//			this.fakeMove = false;
			//停止下坠,坐标不改变就是停止下坠的状态
		}
	}



	@Override
	public void draw() {
		P.draw(l, sprite);
	}


	
	public void resetOnGameLogic(){
		//状态更新不能写在重设变量这里
		push_X = 0; 
		push_Y = 0;
		planeT = Plane.Else;
		verticalT = Vertical.Else;
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
//		Gdx.app.log("impact","逆推X"+ this.push_X );
	}
	public  void setPush_Y(float push_Y) {
		if(push_Y - this.dY > 0){
			this.push_Y = push_Y - this.dY;
			Gdx.app.log("impact", "进入垂直位置微调   "+this.push_Y);
		}
	}
	public void pushBack_Y(){
		l.y = l.y + push_Y;
	}
	
	public  static OzPoint getL(){
		return L;
	}

	

}
