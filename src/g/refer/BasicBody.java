package g.refer;

import g.button.GameButtons;
import g.e.architecture.MoveLand;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Move;
import g.type.Plane;
import g.type.Vertical;


public abstract class BasicBody extends OzElement {

	/**
	 * ������������ڵ�λ�ã���������ʱ����ͼλ�û�ԭ
	 * */
    public OzPoint _startPoint; 
    /**
     * ����ع�ʱ���ٶ�
     * */
    public static final float _rollBackRate = 25f;
    /**
     * rollBackʱ����Χ����
     * */
    public static final float _deviation = 0.1f; //���
    /**
     * rollBackʱÿһ˲��Ӧ���ƶ���б�ߵľ���
     * */
    public float _range = 5;
    /**
     * �Ƿ��Ѿ��趨��rollbackҪ�õ���range
     * */
    public boolean  _rangeSelected = false;
    /**
     * �Ƿ�ʹ��rollBack
     * */
    public boolean _pushBackAvailable = true;
    
    /**
     * ������false,�����಻��ʹ��pushBack����
     * */
    public boolean isPushBackAvailable() {
		return _pushBackAvailable;
	}

    /**���������moveLand*/
	public static MoveLand MoveLand_hitting;
    
    public float a;
    public float b;
    public float c;//�ع�ʱ�õ���3����Ҫ���� ���ɶ���
    
    
	public BasicBody(String Tag, int Rank, ET type, OzPoint l,OzRect entityOffset) {
		super(Tag, Rank, type, l, entityOffset);
		_startPoint = new OzPoint(l.x, l.y);
	}

	
	

	
	
	//���������ˮƽ�ƶ�����  ����������ƶ��������ƶ���            ʱ����˶�
	@Override
	public void planeLogic() {
		
		if( Player.getPlane_HitType()==Player.HIT_BASIC || Player.getPlane_HitType()==Player.HIT_ELSE ){
			//��������ƶ�
			if(GameButtons.getArrow() == GameButtons.A_Left && Player.getPlaneT() != Plane.Right){
				l.x = l.x + Player.moveSpeed();
			}
			//��������ƶ�
			else if(GameButtons.getArrow() == GameButtons.A_Right && Player.getPlaneT() != Plane.Left){
				l.x = l.x - Player.moveSpeed();
			}
		}
		else if( Player.getPlane_HitType()==Player.HIT_Moving ){
			if( MoveLand_hitting.mT==Move.vertical ){
				//��������ƶ�
				if(GameButtons.getArrow() == GameButtons.A_Left && Player.getPlaneT() != Plane.Right){
					l.x = l.x + Player.moveSpeed();
				}
				//��������ƶ�
				else if(GameButtons.getArrow() == GameButtons.A_Right && Player.getPlaneT() != Plane.Left){
					l.x = l.x - Player.moveSpeed();
				}
			}
			else if( MoveLand_hitting.mT==Move.plane ){
				float mlSpeed = MoveLand_hitting.speed;
				//��������ƶ�
				if(GameButtons.getArrow()==GameButtons.A_Left && Player.getPlaneT()==Plane.Right && mlSpeed<0 ){
					l.x = l.x - mlSpeed;
				}
				//��������ƶ�
				else if(GameButtons.getArrow()==GameButtons.A_Right && Player.getPlaneT()==Plane.Left && mlSpeed>0 ){
					l.x = l.x - mlSpeed;
				}
				else if( GameButtons.getArrow()==GameButtons.A_Left && Player.getPlaneT()!=Plane.Right ){
					l.x = l.x + Player.moveSpeed();
				}
				else if( GameButtons.getArrow()==GameButtons.A_Right && Player.getPlaneT()!=Plane.Left ){
					l.x = l.x - Player.moveSpeed();
				}
			}
		}
		
		//ˮƽ�����������ƶ�����
		if( Player.getPlane_HitType()==Player.HIT_Moving ){
			
			if( MoveLand_hitting.mT==Move.vertical ){
				//��������ƶ�
				if(GameButtons.getArrow() == GameButtons.A_Left && Player.getPlaneT() != Plane.Right){
					l.x = l.x + Player.moveSpeed();
				}
				//��������ƶ�
				else if(GameButtons.getArrow() == GameButtons.A_Right && Player.getPlaneT() != Plane.Left){
					l.x = l.x - Player.moveSpeed();
				}
			}
			else if( MoveLand_hitting.mT==Move.plane ){
				
				if( Player.getPlaneT()==Plane.Left && MoveLand_hitting.speed<0 ){
					l.x = l.x - MoveLand_hitting.speed;
				}
				else if( Player.getPlaneT()==Plane.Right && MoveLand_hitting.speed>0 ){
					l.x = l.x - MoveLand_hitting.speed;
//					System.out.println("MoveLand_hitting.speed= "+MoveLand_hitting.speed);
				}
			}
			
		}
		
	}
	//��ֱ�ƶ����� ��ҡ���Ծ����׹��ʱ������˶�
	@Override
	public void verticalLogic() {
		//��Ծ״̬
		if( Player.isJump()==true ){
			l.y = l.y - Player.jumpSpeed();
//			System.out.println("��Ծ");
		}
		//��׹״̬             �����ƶ�����;�ֹ����ײ������������
		else if( (Player.getVerticalT()==Vertical.Else || Player.getVerticalT()==Vertical.Bottom) ){
			l.y = l.y + Player.gravity();
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

	
	//���������ع���������
	public boolean rollBack(){
		
		float sinY;
		float cosX;
		
		a = _startPoint.x - l.x;
		b = _startPoint.y - l.y;
		c = (float) Math.sqrt( a*a+b*b );
		
		setRange();//ֻ�ڸ����ƶ��ͷ����һ��range��
		if( Math.abs(c-_range)<_deviation || c<_range ){
			l.x = _startPoint.x;
			l.y = _startPoint.y;
			_rangeSelected = false; //�����ƶ���ɺ�selectedRange��Ϊfalse;
//			System.out.println("-------------------------");
			return true;
		}
		else{
			cosX = a/c;
			sinY = b/c;
			float dx = _range*cosX;
			float dy = _range*sinY;
			l.x = l.x+dx;
			l.y = l.y+dy;
		}
		return false;
	}
	
	public void setRange(){
		if(_rangeSelected==false){
			_range = c/_rollBackRate;
			if( _range<10f ){
				_range = 10f;
			}
			_rangeSelected = true;  //�����ƶ���ɺ�selectedRangeҪ������Ϊfalse;
		}
	}
	
	
}
