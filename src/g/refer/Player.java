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
	
	public static final float VALUE_MOVE    = 7;                //���ˮƽ�ƶ��ٶ�
	public static final float VALUE_GRAVITY = 11;                //����
	public static final float limitUp = 520;
	public static final float limitDown = 200;
	
	public  static final float VALUE_JUMP    = 11;  //��Ծ���ٶ�
	public  static OzPoint L = new OzPoint();     //���⴫���������
	public  static final int JumpTimeMAX = 35;
	private static int JumpTimeCount = 0;  //��Ծ��ʱ��
	
	//planeTouch
	private static Plane     planeT    =  Plane.Else;
	//verticalTouch
	private static Vertical  verticalT =  Vertical.Else;
	private  float push_X = 0;  //��ײ�����λ���ƻص���ǽǰ
	private  float push_Y = 0;  //��ײ�����λ���ƻص���ǽǰ
	private  float dY = 2;      //�����ִ�ǽ״�������ֵ 
	private  float dX = 2;      //�����ִ�ǽ״�������ֵ 
	
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
			//ֹͣ��׹,���겻�ı����ֹͣ��׹��״̬
		}
	}



	@Override
	public void draw() {
		P.draw(l, sprite);
	}


	
	public void resetOnGameLogic(){
		//״̬���²���д�������������
		push_X = 0; 
		push_Y = 0;
		planeT = Plane.Else;
		verticalT = Vertical.Else;
		L = l; //���⴫���������
	}
	
	//���״̬����
	public void updateAction(){
		jumpAction();
	}
	public void jumpAction(){
		//�����վ��½�����Ұ�����Ծ����֮��ſ�����Ծ��verticalT
			if(GameButton.getSkill() == GameButton.S_Jump && verticalT == Vertical.Top){
				jump = true;
			}
			
			
			if(verticalT == Vertical.Bottom){  //����Ԫ�ض���������Ծ״̬Ϊfalse
				jump = false;
			}
			else if( jump == true && JumpTimeCount < JumpTimeMAX){
				JumpTimeCount++;
			}
			else{
				 jump = false;
				JumpTimeCount = 0;  //�����ҵ�ǰ״̬������Ծ״̬����������Ծʱ�������Ϊ�´���Ծ��׼����
			}
	}
	
	public void set_VerticalT_and_PlaneT( ArrayList<OzElement>  gateAtlas){
		for(int i=0;i<gateAtlas.size();i++){
			//ֻ������ĳԪ�ص�4���ߵ�����1��
			if(gateAtlas.get(i).planeT == Plane.Left){
				
				planeT = Plane.Left;
//				Gdx.app.log("impact", "������ "+planeT);
			}
			else if(gateAtlas.get(i).planeT == Plane.Right){
				
				planeT = Plane.Right;
//				Gdx.app.log("impact", "������ "+planeT);
				
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
//		Gdx.app.log("impact","����X"+ this.push_X );
	}
	public  void setPush_Y(float push_Y) {
		if(push_Y - this.dY > 0){
			this.push_Y = push_Y - this.dY;
			Gdx.app.log("impact", "���봹ֱλ��΢��   "+this.push_Y);
		}
	}
	public void pushBack_Y(){
		l.y = l.y + push_Y;
	}
	
	public  static OzPoint getL(){
		return L;
	}

	

}
