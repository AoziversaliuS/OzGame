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


public class GameButton extends OzElement{

	private OzRect buttonLeft;
	private OzRect buttonRight;
	private OzRect buttonJump;
	private OzRect buttonAttack;
	
	/**ö��ֵ��*/
	//orientation  ö��ֵ������ͬ��
	public static final int A_Left=1, A_Right=2,A_Else=3;
	//Skill
	public static final int S_Jump=4,S_Attack=5,S_Else=6;
	/**ö��ֵ��*/
	Texture e;
	public  static int   Arrow;   //�����,������Ϣ
	public  static int   Skill;   //���ܼ�,������Ϣ
	
	public GameButton() {
		super("GameButton",Rank.SELF_CUSTOM, ET.GameButton,null,null);
		
		this.buttonLeft  = new OzRect(25, 10, Res.game_btnLeft[0].getWidth(),Res.game_btnLeft[0].getHeight());
		this.buttonRight = new OzRect(275, 10,Res.game_btnRight[0].getWidth(), Res.game_btnRight[0].getHeight());
		this.buttonJump = new OzRect(900, 10, Res.game_btnJump[0].getWidth(), Res.game_btnJump[0].getHeight());
		this.buttonAttack = new OzRect(1120, 10, Res.game_btnAttack[0].getWidth(), Res.game_btnAttack[0].getHeight());
		GameButton.Arrow = GameButton.A_Else;
		GameButton.Skill = GameButton.S_Else;
	}

	@Override
	public void reset() {
		GameButton.Arrow = GameButton.A_Else;   //�������һ֡�İ�����Ϣ
		GameButton.Skill = GameButton.S_Else;
	}
	public void logic(HashMap<String, OzPoint> points) {
		
		if(buttonLeft.insides(points) && buttonRight.insides(points)){
			//�����������µ�����£�������һ�������еİ���״̬
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
		
		if(buttonJump.insides(points) && buttonAttack.insides(points) ){
			
		}
		else if(buttonAttack.insides(points)){
			Skill = GameButton.S_Attack;
		}
		else if(buttonJump.insides(points)){
			Skill = GameButton.S_Jump;
		}
		else{
			Skill = GameButton.S_Else;
		}
		
//		Gdx.app.log("btn", "Arrow: "+Arrow);
		
	}
	@Override
	public void draw() {
		//���Ұ���
		if(Arrow == GameButton.A_Else){
			P.draw(buttonLeft.x, buttonLeft.y, Res.game_btnLeft[0]);
			P.draw(buttonRight.x, buttonRight.y, Res.game_btnRight[0]);
		}
		else if(Arrow == GameButton.A_Left){
			P.draw(buttonLeft.x,buttonLeft.y, Res.game_btnLeft[1]);
			P.draw(buttonRight.x, buttonRight.y, Res.game_btnRight[0]);
		}
		else if(Arrow == GameButton.A_Right){
			P.draw(buttonLeft.x,buttonLeft.y, Res.game_btnLeft[0]);
			P.draw(buttonRight.x, buttonRight.y, Res.game_btnRight[1]);
		}
		//��Ծ����
		if(Skill == GameButton.S_Else){
			P.draw(buttonAttack.x, buttonAttack.y, Res.game_btnAttack[0]);
			P.draw(buttonJump.x, buttonJump.y, Res.game_btnJump[0]);
		}
		else if(Skill == GameButton.S_Jump){
			P.draw(buttonAttack.x, buttonAttack.y, Res.game_btnAttack[0]);
			P.draw(buttonJump.x, buttonJump.y, Res.game_btnJump[1]);
		}
		else if(Skill == GameButton.S_Attack){
			P.draw(buttonAttack.x, buttonAttack.y, Res.game_btnAttack[1]);
			P.draw(buttonJump.x, buttonJump.y, Res.game_btnJump[0]);
		}
	}

	
	public static int getArrow(){
		return GameButton.Arrow;     //��Arrow��ֵ���ݸ����
	}
	public static int getSkill(){
		return GameButton.Skill;     //��Skill��ֵ���ݸ����
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
