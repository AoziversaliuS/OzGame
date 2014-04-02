package g.button;

import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.type.ET;
import g.type.Rank;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


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
	Texture e;
	public  static int   Arrow;   //方向键,触碰信息
	public  static int   Skill;   //技能键,触碰信息
	
	public GameButton() {
		super("GameButton",Rank.SELF_CUSTOM, ET.GameButton,null,null);
		
		this.buttonLeft  = new OzRect(25, 15, P.Game_btnLeft.getWidth(),P.Game_btnLeft.getHeight());
		this.buttonRight = new OzRect(275, 15,P.Game_btnRight.getWidth(), P.Game_btnRight.getHeight());
		this.buttonJump = new OzRect(1100, 15, P.Game_btnJump.getWidth(), P.Game_btnJump.getHeight());
		
		GameButton.Arrow = GameButton.A_Else;
		GameButton.Skill = GameButton.S_Else;
	}

	@Override
	public void reset() {
		GameButton.Arrow = GameButton.A_Else;   //清除掉上一帧的按键信息
		GameButton.Skill = GameButton.S_Else;
	}
	public void logic(HashMap<String, OzPoint> points) {
		
		if(buttonLeft.insides(points) && buttonRight.insides(points)){
			//两个键都按下的情况下，保持上一次所具有的按键状态
		}
	    else if(buttonLeft.insides(points)){
			Arrow = GameButton.A_Left;
		}
		else if(buttonRight.insides(points)){
			Arrow = GameButton.A_Right;
		}
		else{
			Arrow = GameButton.A_Else;
		}
		
		
		if(buttonJump.insides(points)){
			Skill = GameButton.S_Jump;
		}
		else{
			Skill = GameButton.S_Else;
		}
		
//		Gdx.app.log("btn", "Arrow: "+Arrow);
		
	}
	@Override
	public void draw() {
		//左右按键
		if(Arrow == GameButton.A_Else){
			P.draw(buttonLeft.x, buttonLeft.y, P.Game_btnLeft);
			P.draw(buttonRight.x, buttonRight.y, P.Game_btnRight);
		}
		else if(Arrow == GameButton.A_Left){
			P.draw(buttonLeft.x,buttonLeft.y, P.Game_btnLeftPress);
			P.draw(buttonRight.x, buttonRight.y, P.Game_btnRight);
		}
		else if(Arrow == GameButton.A_Right){
			P.draw(buttonLeft.x,buttonLeft.y, P.Game_btnLeft);
			P.draw(buttonRight.x, buttonRight.y, P.Game_btnRightPress);
		}
		//跳跃按键
		if(Skill == GameButton.S_Else){
			P.draw(buttonJump.x, buttonJump.y, P.Game_btnJump);
		}
		else if(Skill == GameButton.S_Jump){
			P.draw(buttonJump.x, buttonJump.y, P.Game_btnJumpPress);
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
	public void impact(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean rollBack() {
		// TODO Auto-generated method stub
		return false;
	}


	


}
