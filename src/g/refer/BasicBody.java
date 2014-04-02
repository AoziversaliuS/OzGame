package g.refer;

import g.button.GameButton;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Plane;
import g.type.Vertical;


public abstract class BasicBody extends OzElement {

    public OzPoint startPoint; //������������ڵ�λ�ã���������ʱ����ͼλ�û�ԭ
    public static final float rollBackRate = 100f;
    public float range;
    public boolean  selectedRange = false;
	
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
	public boolean rollBack(){
		float a = startPoint.x - l.x;
		float b = startPoint.y - l.y;
		float c;
		float sinY;
		float cosX;
		
		c = (float) Math.sqrt( a*a+b*b );
		
		setRange(c);//ֻ�ڸ����ƶ��ͷ����һ��range��
		
		if( c<=range ){
			l.x = startPoint.x;
			l.y = startPoint.y;
			selectedRange = false; //�����ƶ���ɺ�selectedRange��Ϊfalse;
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
	
	public void setRange(float c){
		if(selectedRange==false){
			range = c/rollBackRate;
			selectedRange = true;  //�����ƶ���ɺ�selectedRangeҪ������Ϊfalse;
		}
	}

}
