package g.button;

import g.refer.OzElement;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Rank;

import java.util.ArrayList;


public class GameButton extends OzElement{

	private OzRect buttonLeft;
	private OzRect buttonRight;
	private OzRect buttonJump;
	
	/**枚举值↓*/
	//orientation  枚举值不能相同！
	public static final int A_Left=1, A_Right=2,A_Else=3;
	//Skill
	public static final int S_Jump=4,S_Else=5;
	/**枚举值↑*/
	
	public  static int   Arrow;   //方向键,触碰信息
	public  static int   Skill;   //技能键,触碰信息
	
	public GameButton() {
		super("GameButton",Rank._9, ET.GameButton,null,null);
		
//		this.buttonLeft  = new OzRect(0, 600, P.Game_LarrowA.basicWidth, 600+P.Game_LarrowA.basicHeight);
//		this.buttonRight = new OzRect(250, 600, 250+P.Game_RarrowA.basicWidth, 600+P.Game_RarrowA.basicHeight);
//		this.buttonJump = new OzRect(1050, 580, 1050+P.Game_JumpA.basicWidth, 580+P.Game_JumpA.basicHeight);
		
		GameButton.Arrow = GameButton.A_Else;
		GameButton.Skill = GameButton.S_Else;
	}

	@Override
	public void reset() {
		GameButton.Arrow = GameButton.A_Else;   //清除掉上一帧的按键信息
		GameButton.Skill = GameButton.S_Else;
	}
	public void logic(ArrayList<OzPoint> pressPoint) {
		
//		Log.v("TouchScreen", "触碰点数量："+pressPoint.size());
		
		//有触碰屏幕的情况下
		
	}
	@Override
	public void draw() {
		//左右按键
		if(Arrow == GameButton.A_Else){
			
		}
		else if(Arrow == GameButton.A_Left){
		}
		else if(Arrow == GameButton.A_Right){
		}
		//跳跃按键
		if(Skill == GameButton.S_Else){
		}
		else if(Skill == GameButton.S_Jump){
		}
	}

	
	public static int getArrow(){
		return GameButton.Arrow;     //将Arrow的值传递给外界
	}
	public static int getSkill(){
		return GameButton.Skill;     //将Skill的值传递给外界
	}
	
	
	@Override
	public void logic() {
	}

	@Override
	public void impact( ) {
	}


	


}
