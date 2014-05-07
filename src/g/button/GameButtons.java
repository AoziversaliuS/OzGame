package g.button;

import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class GameButtons extends OzElement{

	private OzRect buttonLeft;
	private OzRect buttonRight;
	private OzRect buttonJump;
	private OzRect buttonAttack;
	
	/**枚举值↓*/
	//orientation  枚举值不能相同！
	public static final int A_Left=1, A_Right=2,A_Else=3;
	//Skill
	public static final int S_Jump=4,S_Attack=5,S_Else=6;
	/**枚举值↑*/
	Texture e;
	public  static int   Arrow;   //方向键,触碰信息
	public  static int   Skill;   //技能键,触碰信息
	
	public GameButtons() {
		super("GameButton",Rank.SELF_CUSTOM, ET.GameButtons,null,null);
		
		this.buttonLeft  = new OzRect(25, 10, Res.game_btnLeft[0].getWidth(),Res.game_btnLeft[0].getHeight());
		this.buttonRight = new OzRect(275, 10,Res.game_btnRight[0].getWidth(), Res.game_btnRight[0].getHeight());
		this.buttonJump = new OzRect(900, 10, Res.game_btnJump[0].getWidth(), Res.game_btnJump[0].getHeight());
		this.buttonAttack = new OzRect(1120, 10, Res.game_btnAttack[0].getWidth(), Res.game_btnAttack[0].getHeight());
		GameButtons.Arrow = GameButtons.A_Else;
		GameButtons.Skill = GameButtons.S_Else;
	}

	@Override
	public void reset() {
		GameButtons.Arrow = GameButtons.A_Else;   //清除掉上一帧的按键信息
		GameButtons.Skill = GameButtons.S_Else;
	}
	public void logic(HashMap<String, OzPoint> points) {
		
		if(buttonLeft.insides(points,P.AUTO_RATIO) && buttonRight.insides(points,P.AUTO_RATIO)){
			//两个键都按下的情况下，保持上一次所具有的按键状态
		}
	    else if(buttonLeft.insides(points,P.AUTO_RATIO)){
			Arrow = GameButtons.A_Left;
		}
		else if(buttonRight.insides(points,P.AUTO_RATIO)){
			Arrow = GameButtons.A_Right;
		}
		else{
			Arrow = GameButtons.A_Else;
		}
		
		if(buttonJump.insides(points,P.AUTO_RATIO) && buttonAttack.insides(points,P.AUTO_RATIO) ){
			
		}
		else if(buttonAttack.insides(points,P.AUTO_RATIO)){
			Skill = GameButtons.S_Attack;
		}
		else if(buttonJump.insides(points,P.AUTO_RATIO)){
			Skill = GameButtons.S_Jump;
		}
		else{
			Skill = GameButtons.S_Else;
		}
		
//		Gdx.app.log("btn", "Arrow: "+Arrow);
		
	}
	@Override
	public void draw() {
		//左右按键
		if(Arrow == GameButtons.A_Else){
			P.draw(buttonLeft.x, buttonLeft.y, Res.game_btnLeft[0]);
			P.draw(buttonRight.x, buttonRight.y, Res.game_btnRight[0]);
		}
		else if(Arrow == GameButtons.A_Left){
			P.draw(buttonLeft.x,buttonLeft.y, Res.game_btnLeft[1]);
			P.draw(buttonRight.x, buttonRight.y, Res.game_btnRight[0]);
		}
		else if(Arrow == GameButtons.A_Right){
			P.draw(buttonLeft.x,buttonLeft.y, Res.game_btnLeft[0]);
			P.draw(buttonRight.x, buttonRight.y, Res.game_btnRight[1]);
		}
		//跳跃按键
		if(Skill == GameButtons.S_Else){
			P.draw(buttonAttack.x, buttonAttack.y, Res.game_btnAttack[0]);
			P.draw(buttonJump.x, buttonJump.y, Res.game_btnJump[0]);
		}
		else if(Skill == GameButtons.S_Jump){
			P.draw(buttonAttack.x, buttonAttack.y, Res.game_btnAttack[0]);
			P.draw(buttonJump.x, buttonJump.y, Res.game_btnJump[1]);
		}
		else if(Skill == GameButtons.S_Attack){
			P.draw(buttonAttack.x, buttonAttack.y, Res.game_btnAttack[1]);
			P.draw(buttonJump.x, buttonJump.y, Res.game_btnJump[0]);
		}
	}

	
	public static int getArrow(){
		return GameButtons.Arrow;     //将Arrow的值传递给外界
	}
	public static int getSkill(){
		return GameButtons.Skill;     //将Skill的值传递给外界
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

	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}


	


}
