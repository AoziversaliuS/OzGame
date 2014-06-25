package g.refer;



import g.tool.OzPoint;
import g.tool.OzRect;
import g.type.ET;
import g.type.Plane;
import g.type.Vertical;



public abstract class OzElement implements Origin{
	
	public int rankNum = -1;  //该元素所在的图层
	public float angle = 0;//角度？不知有何用
	ET type = ET.Element;
	public OzPoint l;   //元素的坐标
	public OzRect  entityOffset; //entifyOffset 实体相对于l的偏移量
	public OzRect  entity;//此元素实际用于碰撞检测的实体方块    要先通过OzElement.makeEntity(this); 才能使用
	public boolean planeLogicUsed = true;  //使用水平相关运算
	public boolean verticalLogicUsed = true;  //使用水平相关运算
	public String  Tag = "0-0-0";
	
	public Plane    planeT = Plane.Else;        //用来识别其它物体碰到自己的哪个位置
	public Vertical verticalT = Vertical.Else;
	
	public static OzPoint refer = new OzPoint(); //游戏移动物体的参照坐标 只能在ReferPoint类里更新此坐标
	
	//构造函数
	public OzElement(String Tag,int Rank,ET type,OzPoint l,OzRect entityOffset){
		entity = new OzRect();
		this.Tag = Tag;
		this.rankNum = Rank;
		this.type = type;
		this.l = l;
		this.entityOffset = entityOffset;
	}
	
	
	//该元素的逻辑运算
	//该元素的图形显示函数
	//该元素的碰撞检测函数
	
	
	
	public OzElement() {
		super();
	}


	//在这里进行水平移动运算  【玩家向左移动，向右移动】            时相对运动
	public void planeLogic(){
	}
	//垂直移动运算 玩家【跳跃，下坠】时的相对运动
	public void verticalLogic(){
	}
	/**
	 * 只有当 Player.getCondition()==Player.ALIVE 时才能进行相对移动
	 * */
	public void engine(){
		//只有活着时才能进行相对移动
		if(Player.getCondition()==Player.ALIVE){
			if(planeLogicUsed){
				planeLogic();     //玩家向左移动，向右移动时相对运动
			}
			if(verticalLogicUsed){
				verticalLogic();  // 玩家跳跃，下坠时的相对运动
			}
//			logic();  
		}

		
		logic();              //元素自身特有的逻辑运算
	}
	
	
	public boolean hit(OzElement other){
		
		OzElement.makeEntity(this);   //为自身制造用于碰撞检测的实体
		OzElement.makeEntity(other);  //为另外一个碰撞的对象制造用于碰撞检测的实体
		
		if(this.entity.intersect(other.entity)){
			//与其它物体相碰则返回true
			return true;
		}
		else{
			//没碰到返回false;
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
