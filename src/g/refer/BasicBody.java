package g.refer;

import g.build.MoveLand;
import g.button.GameButton;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Move;
import g.type.Plane;
import g.type.Vertical;


public abstract class BasicBody extends OzElement {

    public OzPoint startPoint; //������������ڵ�λ�ã���������ʱ����ͼλ�û�ԭ
    public static final float rollBackRate = 25f;
    public static final float deviation = 0.1f; //���
    public float range = 5;
    public boolean  selected = false;
    
    
    public static MoveLand MoveLand_hitting;
    
    public float a;
    public float b;
    public float c;//�ع�ʱ�õ���3����Ҫ���� ���ɶ���
    
    
	public BasicBody(String Tag, int Rank, ET type, OzPoint l,OzRect entityOffset) {
		super(Tag, Rank, type, l, entityOffset);
		startPoint = new OzPoint(l.x, l.y);
	}

	
	

	
	
	//���������ˮƽ�ƶ�����  ����������ƶ��������ƶ���            ʱ����˶�
	@Override
	public void planeLogic() {
//		System.out.println("Player.getPlane_HitType()="+Player.getPlane_HitType());
		
//		if( Player.getPlane_HitType()==Player.HIT_BASIC || Player.getPlane_HitType()==Player.HIT_ELSE ){
//			//��������ƶ�
//			if(GameButton.getArrow() == GameButton.A_Left && Player.getPlaneT() != Plane.Right){
//				l.x = l.x + Player.VALUE_MOVE;
//			}
//			//��������ƶ�
//			else if(GameButton.getArrow() == GameButton.A_Right && Player.getPlaneT() != Plane.Left){
//				l.x = l.x - Player.VALUE_MOVE;
//			}
//		}
		
		//��������ƶ�
		if(GameButton.getArrow() == GameButton.A_Left && Player.getPlaneT() != Plane.Right){
			l.x = l.x + Player.VALUE_MOVE;
		}
		//��������ƶ�
		else if(GameButton.getArrow() == GameButton.A_Right && Player.getPlaneT() != Plane.Left){
			l.x = l.x - Player.VALUE_MOVE;
		}
		//ˮƽ�����������ƶ�����
		if( Player.getPlane_HitType()==Player.HIT_Moving ){
			
			if( MoveLand_hitting.mT==Move.vertical ){
				//��������ƶ�
				if(GameButton.getArrow() == GameButton.A_Left && Player.getPlaneT() != Plane.Right){
					l.x = l.x + Player.VALUE_MOVE;
				}
				//��������ƶ�
				else if(GameButton.getArrow() == GameButton.A_Right && Player.getPlaneT() != Plane.Left){
					l.x = l.x - Player.VALUE_MOVE;
				}
			}
			else if( MoveLand_hitting.mT==Move.plane ){
//				if( Player.getPlaneT()==Plane.Left || Player.getPlaneT()==Plane.Right ){
//					l.x = l.x - MoveLand_hitting.speed;
//				}
//				if( Player.getPlaneT()==Plane.Left && MoveLand_hitting.speed>0 ){
//					l.x = l.x - Player.VALUE_MOVE;
//				}
//				else if( Player.getPlaneT()==Plane.Right && MoveLand_hitting.speed<0 ){
//					l.x = l.x - Player.VALUE_MOVE;
//				}
				System.out.println("MoveLand_hitting.speed= "+MoveLand_hitting.speed);
				if( Player.getPlaneT()==Plane.Left && MoveLand_hitting.speed>0 ){
					l.x = l.x - MoveLand_hitting.speed;
				}
				else if( Player.getPlaneT()==Plane.Right && MoveLand_hitting.speed<0 ){
					l.x = l.x - MoveLand_hitting.speed;
				}
			}
			
		}
		
	}
	//��ֱ�ƶ����� ��ҡ���Ծ����׹��ʱ������˶�
	@Override
	public void verticalLogic() {
		//��Ծ״̬
		if( Player.isJump()==true ){
			l.y = l.y - Player.VALUE_JUMP;
//			System.out.println("��Ծ");
		}
		//��׹״̬  �����ƶ�����;�ֹ����ײ������������
		else if( (Player.getVerticalT()==Vertical.Else || Player.getVerticalT()==Vertical.Bottom) ){
			l.y = l.y + Player.VALUE_GRAVITY;
//			System.out.println("��׹");
		}
		//վ��½��״̬
		
		
		//��������� ֻ�� ���� [�ƶ�����top] ������ [��ֹ����top] �������
		else if( Player.getVerticalT()==Vertical.Top ){
			//������ֹ����
			if( Player.getVertical_HitType()==Player.HIT_BASIC ){
				//ֹͣ��׹,���겻�ı����ֹͣ��׹��״̬
			}
			else if( Player.getVertical_HitType()==Player.HIT_Moving ){
				
					if( MoveLand_hitting.mT==Move.vertical ){
						l.y = l.y - MoveLand_hitting.speed;
					}
					else if( MoveLand_hitting.mT==Move.plane ){
						l.x = l.x - MoveLand_hitting.speed;
					}
			}
		}
	    
	}

	@Override
	public void reset() {
//		//��������
//		l.x = startPoint.x;
//		l.y = startPoint.y;
	}
	
	//���������ع���������
	public boolean rollBack(){
		
		float sinY;
		float cosX;
		
		a = startPoint.x - l.x;
		b = startPoint.y - l.y;
		c = (float) Math.sqrt( a*a+b*b );
		
		setRange();//ֻ�ڸ����ƶ��ͷ����һ��range��
//		range = 5;
//		System.out.println("c="+c+"  range="+range + " selectedRange="+selected);
		if( Math.abs(c-range)<deviation || c<range ){
			l.x = startPoint.x;
			l.y = startPoint.y;
			selected = false; //�����ƶ���ɺ�selectedRange��Ϊfalse;
//			System.out.println("-------------------------");
			return true;
		}
		else{
			cosX = a/c;
			sinY = b/c;
			float dx = range*cosX;
			float dy = range*sinY;
			l.x = l.x+dx;
			l.y = l.y+dy;
		}
		return false;
	}
	
	public void setRange(){
		if(selected==false){
			range = c/rollBackRate;
			if( range<10f ){
				range = 10f;
			}
			selected = true;  //�����ƶ���ɺ�selectedRangeҪ������Ϊfalse;
		}
	}

}
