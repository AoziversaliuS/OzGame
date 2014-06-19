package g.button;

import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;


public class GameButtons extends OzElement{

	private OzRect buttonLeft;
	private OzRect buttonRight;
	private OzRect buttonJump;
	private OzRect buttonAttack;
	private OzRect buttonPass;
	/**枚举值↓*/
	//orientation  枚举值不能相同！
	public static final int A_Left=1, A_Right=2,A_Else=3;
	//Skill
	public static final int S_Jump=4,S_Attack=5,S_Else=6;
	/**枚举值↑*/
	Texture e;
	public  static int   Arrow;   //方向键,触碰信息
	public  static int   Skill;   //技能键,触碰信息
	public static  boolean Playing ;//游戏处于暂停状态时Playing为false
	
	public GameButtons() {
		super("GameButton",Rank.SELF_CUSTOM, ET.GameButtons,null,null);
		buttonLeft  = new OzRect(25, 10, Res.game_btnLeft[0].getWidth(),Res.game_btnLeft[0].getHeight());
		buttonRight = new OzRect(275, 10,Res.game_btnRight[0].getWidth(), Res.game_btnRight[0].getHeight());
		buttonJump = new OzRect(900, 10, Res.game_btnJump[0].getWidth(), Res.game_btnJump[0].getHeight());
		buttonAttack = new OzRect(1120, 10, Res.game_btnAttack[0].getWidth(), Res.game_btnAttack[0].getHeight());
		
		float passX = P.BASIC_SCREEN_WIDTH-Res.game_btnPass[0].getWidth();//相对坐标，屏幕大小不同，位置也会调整
		float passY = P.BASIC_SCREEN_HEIGHT-Res.game_btnPass[0].getHeight();
		buttonPass = new OzRect(passX, passY, Res.game_btnPass[0].getWidth(), Res.game_btnPass[0].getHeight());
		
		GameButtons.Arrow = GameButtons.A_Else;
		GameButtons.Skill = GameButtons.S_Else;
		Playing = true;
	}

	@Override
	public void reset() {
		GameButtons.Arrow = GameButtons.A_Else;   //清除掉上一帧的按键信息
		GameButtons.Skill = GameButtons.S_Else;
		GameButtons.Playing = false;
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
		//
		if(buttonPass.insides(points, P.FORCE_RATIO)){
			Playing = false;
		}
		else{
			Playing = true;
		}
		
//		Gdx.app.log("btn", "Arrow: "+Arrow);
		
	}
	@Override
	public void draw() {
		//左右按键
		if(Arrow == GameButtons.A_Else){
			P.draw(buttonLeft.x, buttonLeft.y, Res.game_btnLeft[0],P.AUTO_RATIO);
			P.draw(buttonRight.x, buttonRight.y, Res.game_btnRight[0],P.AUTO_RATIO);
		}
		else if(Arrow == GameButtons.A_Left){
			P.draw(buttonLeft.x,buttonLeft.y, Res.game_btnLeft[1],P.AUTO_RATIO);
			P.draw(buttonRight.x, buttonRight.y, Res.game_btnRight[0],P.AUTO_RATIO);
		}
		else if(Arrow == GameButtons.A_Right){
			P.draw(buttonLeft.x,buttonLeft.y, Res.game_btnLeft[0],P.AUTO_RATIO);
			P.draw(buttonRight.x, buttonRight.y, Res.game_btnRight[1],P.AUTO_RATIO);
		}
		//跳跃按键
		if(Skill == GameButtons.S_Else){
			P.draw(buttonAttack.x, buttonAttack.y, Res.game_btnAttack[0],P.AUTO_RATIO);
			P.draw(buttonJump.x, buttonJump.y, Res.game_btnJump[0],P.AUTO_RATIO);
		}
		else if(Skill == GameButtons.S_Jump){
			P.draw(buttonAttack.x, buttonAttack.y, Res.game_btnAttack[0],P.AUTO_RATIO);
			P.draw(buttonJump.x, buttonJump.y, Res.game_btnJump[1],P.AUTO_RATIO);
		}
		else if(Skill == GameButtons.S_Attack){
			P.draw(buttonAttack.x, buttonAttack.y, Res.game_btnAttack[1],P.AUTO_RATIO);
			P.draw(buttonJump.x, buttonJump.y, Res.game_btnJump[0],P.AUTO_RATIO);
		}
		//暂停按键
		if(Playing){
			P.draw(buttonPass.x, buttonPass.y, Res.game_btnPass[0]);
		}
		else{
			P.draw(buttonPass.x, buttonPass.y, Res.game_btnPass[1]);
		}
	}

	
	public static int getArrow(){
		return GameButtons.Arrow;     //将Arrow的值传递给外界
	}
	public static int getSkill(){
		return GameButtons.Skill;     //将Skill的值传递给外界
	}
	/**
	 * 玩家在游戏中按下暂停按键则会返回false
	 */
	public static boolean isPlaying() {
		return Playing;
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
