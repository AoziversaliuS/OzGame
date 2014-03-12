package g.button;

import g.refer.OzElement;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.type.ET;
import g.type.Rank;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class GameButton extends OzElement{

	private OzRect buttonLeft;
	private OzRect buttonRight;
	private OzRect buttonJump;
	private Sprite btnLeft;
	private Sprite btnLeftPress;
	private Sprite btnRight;
	private Sprite btnRightPress;
	private Sprite btnJump;
	private Sprite btnJumpPress;
	/**ö��ֵ��*/
	//orientation  ö��ֵ������ͬ��
	public static final int A_Left=1, A_Right=2,A_Else=3;
	//Skill
	public static final int S_Jump=4,S_Else=5;
	/**ö��ֵ��*/
	
	public  static int   Arrow;   //�����,������Ϣ
	public  static int   Skill;   //���ܼ�,������Ϣ
	
	public GameButton() {
		super("GameButton",Rank._9, ET.GameButton,null,null);
		btnLeft = new Sprite(P.Game_btnLeft.getSprite());
		btnRight = new Sprite(P.Game_btnRight.getSprite());
		btnJump = new Sprite(P.Game_btnJump.getSprite());
		btnLeftPress = new Sprite(P.Game_btnLeftPress.getSprite());
		btnRightPress = new Sprite(P.Game_btnRightPress.getSprite());
		btnJumpPress =  new Sprite(P.Game_btnJumpPress.getSprite());
		
		this.buttonLeft  = new OzRect(25, 25, 25+P.Game_btnLeft.getWidth(), 25+P.Game_btnLeft.getHeight());
		this.buttonRight = new OzRect(275, 25, 275+P.Game_btnRight.getWidth(), 25+P.Game_btnRight.getHeight());
		this.buttonJump = new OzRect(1000, 25, 1000+P.Game_btnJump.getWidth(), 25+P.Game_btnJump.getHeight());
		
		GameButton.Arrow = GameButton.A_Else;
		GameButton.Skill = GameButton.S_Else;
	}

	@Override
	public void reset() {
		GameButton.Arrow = GameButton.A_Else;   //�������һ֡�İ�����Ϣ
		GameButton.Skill = GameButton.S_Else;
	}
	public void logic(HashMap<String, OzPoint> points) {
		
		if(buttonLeft.insides(points)){
			Gdx.app.log("btn","�������ͷ");
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
		
	}
	@Override
	public void draw() {
		//���Ұ���
		if(Arrow == GameButton.A_Else){
			P.draw(buttonLeft.x, buttonLeft.y, btnLeft);
			P.draw(buttonRight.x, buttonRight.y, btnRight);
		}
		else if(Arrow == GameButton.A_Left){
			P.draw(buttonLeft.x,buttonLeft.y, btnLeftPress);
			P.draw(buttonRight.x, buttonRight.y, btnRight);
		}
		else if(Arrow == GameButton.A_Right){
			P.draw(buttonLeft.x,buttonLeft.y, btnLeft);
			P.draw(buttonRight.x, buttonRight.y, btnRightPress);
		}
		//��Ծ����
		if(Skill == GameButton.S_Else){
			P.draw(1000, 25, btnJump);
		}
		else if(Skill == GameButton.S_Jump){
			P.draw(1000, 25, btnJumpPress);
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
	public void impact( ) {
	}


	


}
