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
		
//		this.buttonLeft  = new OzRect(0, 600, P.Game_LarrowA.basicWidth, 600+P.Game_LarrowA.basicHeight);
//		this.buttonRight = new OzRect(250, 600, 250+P.Game_RarrowA.basicWidth, 600+P.Game_RarrowA.basicHeight);
//		this.buttonJump = new OzRect(1050, 580, 1050+P.Game_JumpA.basicWidth, 580+P.Game_JumpA.basicHeight);
		
		GameButton.Arrow = GameButton.A_Else;
		GameButton.Skill = GameButton.S_Else;
	}

	@Override
	public void reset() {
		GameButton.Arrow = GameButton.A_Else;   //�������һ֡�İ�����Ϣ
		GameButton.Skill = GameButton.S_Else;
	}
	public void logic(ArrayList<OzPoint> pressPoint) {
		
//		Log.v("TouchScreen", "������������"+pressPoint.size());
		
		//�д�����Ļ�������
		
	}
	@Override
	public void draw() {
		//���Ұ���
		if(Arrow == GameButton.A_Else){
			
		}
		else if(Arrow == GameButton.A_Left){
		}
		else if(Arrow == GameButton.A_Right){
		}
		//��Ծ����
		if(Skill == GameButton.S_Else){
		}
		else if(Skill == GameButton.S_Jump){
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
