package g.refer;

import g.basis.GameView;
import g.button.GameButton;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Plane;
import g.type.Vertical;


public abstract class BasicBody extends OzElement {


	public BasicBody(String Tag, int Rank, ET type, OzPoint l,
			OzRect entityOffset) {
		super(Tag, Rank, type, l, entityOffset);
	}

	
	

	
	
	//���������ˮƽ�ƶ�����  ����������ƶ��������ƶ���            ʱ����˶�
	@Override
	public void planeLogic() {
		
		if(GameButton.getArrow() == GameButton.A_Left && Player.getPlaneT() != Plane.Right){
			l.x = l.x + Player.VALUE_MOVE;
		}
		else if(GameButton.getArrow() == GameButton.A_Right && Player.getPlaneT() != Plane.Left){
			l.x = l.x - Player.VALUE_MOVE;
		}
	}
	//��ֱ�ƶ����� ��ҡ���Ծ����׹��ʱ������˶�
	@Override
	public void verticalLogic() {
		
	    if( Player.isJump()==true &&  Player.getL().y>Player.limitUp){
			l.y = l.y - Player.VALUE_JUMP;
		}
		else if( (Player.getVerticalT()==Vertical.Else || Player.getVerticalT()==Vertical.Bottom) && Player.getL().y<Player.limitDown ){
			l.y = l.y + Player.VALUE_GRAVITY;
		}
		else if( Player.getVerticalT()==Vertical.Top ){
			//ֹͣ��׹,���겻�ı����ֹͣ��׹��״̬
		}
	    
	}

	@Override
	public void reset() {

	}

}
