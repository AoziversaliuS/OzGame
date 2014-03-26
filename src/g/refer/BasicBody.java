package g.refer;

import g.button.GameButton;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Plane;
import g.type.Vertical;


public abstract class BasicBody extends OzElement {

    public OzPoint startPoint; //������������ڵ�λ�ã���������ʱ����ͼλ�û�ԭ
	
	public BasicBody(String Tag, int Rank, ET type, OzPoint l,OzRect entityOffset) {
		super(Tag, Rank, type, l, entityOffset);
		startPoint = new OzPoint(l.x, l.y);
	}

	
	

	
	
	//���������ˮƽ�ƶ�����  ����������ƶ��������ƶ���            ʱ����˶�
	@Override
	public void planeLogic() {
		//��������ƶ�
		if(GameButton.getArrow() == GameButton.A_Left && Player.getPlaneT() != Plane.Right){
			l.x = l.x + Player.VALUE_MOVE;
		}
		//��������ƶ�
		else if(GameButton.getArrow() == GameButton.A_Right && Player.getPlaneT() != Plane.Left){
			l.x = l.x - Player.VALUE_MOVE;
		}
	}
	//��ֱ�ƶ����� ��ҡ���Ծ����׹��ʱ������˶�
	@Override
	public void verticalLogic() {
		//��Ծ״̬
		if( Player.isJump()==true ){
			l.y = l.y - Player.VALUE_JUMP;
		}
		//��׹״̬
		else if( (Player.getVerticalT()==Vertical.Else || Player.getVerticalT()==Vertical.Bottom) ){
			l.y = l.y + Player.VALUE_GRAVITY;
		}
		//վ��½��״̬
		else if( Player.getVerticalT()==Vertical.Top ){
			//ֹͣ��׹,���겻�ı����ֹͣ��׹��״̬
		}
	    
	}

	@Override
	public void reset() {
		//��������
		l.x = startPoint.x;
		l.y = startPoint.y;
	}

}
