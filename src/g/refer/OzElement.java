package g.refer;



import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Plane;
import g.type.Vertical;



public abstract class OzElement implements Origin{
	
	public int rankNum = -1;  //��Ԫ�����ڵ�ͼ��
	public float angle = 0;//�Ƕȣ���֪�к���
	ET type = ET.Element;
	public OzPoint l;   //Ԫ�ص�����
	public OzRect  entityOffset; //entifyOffset ʵ�������l��ƫ����
	public OzRect  entity;//��Ԫ��ʵ��������ײ����ʵ�巽��    Ҫ��ͨ��OzElement.makeEntity(this); ����ʹ��
	public boolean planeLogicUsed = true;  //ʹ��ˮƽ�������
	public boolean verticalLogicUsed = true;  //ʹ��ˮƽ�������
	public String  Tag = "0-0-0";
	
	public Plane    planeT = Plane.Else;        //����ʶ���������������Լ����ĸ�λ��
	public Vertical verticalT = Vertical.Else;
	
	public static OzPoint refer = new OzPoint(); //��Ϸ�ƶ�����Ĳ������� ֻ����ReferPoint������´�����
	
	//���캯��
	public OzElement(String Tag,int Rank,ET type,OzPoint l,OzRect entityOffset){
		entity = new OzRect();
		this.Tag = Tag;
		this.rankNum = Rank;
		this.type = type;
		this.l = l;
		this.entityOffset = entityOffset;
	}
	
	
	//��Ԫ�ص��߼�����
	//��Ԫ�ص�ͼ����ʾ����
	//��Ԫ�ص���ײ��⺯��
	
	
	
	public OzElement() {
		super();
	}


	//���������ˮƽ�ƶ�����  ����������ƶ��������ƶ���            ʱ����˶�
	public void planeLogic(){
	}
	//��ֱ�ƶ����� ��ҡ���Ծ����׹��ʱ������˶�
	public void verticalLogic(){
	}
	/**
	 * ֻ�е� Player.getCondition()==Player.ALIVE ʱ���ܽ�������ƶ�
	 * */
	public void engine(){
		//ֻ�л���ʱ���ܽ�������ƶ�
		if(Player.getCondition()==Player.ALIVE){
			if(planeLogicUsed){
				planeLogic();     //��������ƶ��������ƶ�ʱ����˶�
			}
			if(verticalLogicUsed){
				verticalLogic();  // �����Ծ����׹ʱ������˶�
			}
//			logic();  
		}

		
		logic();              //Ԫ���������е��߼�����
	}
	
	
	public boolean hit(OzElement other){
		
		OzElement.makeEntity(this);   //Ϊ��������������ײ����ʵ��
		OzElement.makeEntity(other);  //Ϊ����һ����ײ�Ķ�������������ײ����ʵ��
		
		if(this.entity.intersect(other.entity)){
			//���������������򷵻�true
			return true;
		}
		else{
			//û��������false;
			return false;
		}
		
	}
	public static void makeEntity(OzElement element){
		element.entity.x     = element.l.x + element.entityOffset.x;
		element.entity.y    = element.l.y + element.entityOffset.y;
		element.entity.width = element.entityOffset.width;
		element.entity.height =  element.entityOffset.height;
//		element.entity.centerX()
	}
}
