package g.button;

import java.util.ArrayList;
import java.util.HashMap;

import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;

public class SelectButtons extends OzElement{

	private ArrayList<OzRect> btns;
	
	private final float FIRST_LINE = 500;
	private final float SECOND_LINE = 300;
	private final float SPACE = 50;
	private final float LIMIT_LEFT = 100;
	
	private final float MIN_DRAG_RANGE = 50;//最小移动距离，只有超过了这个距离才能移动
	
	private float dragRange = 0;//一只手指拖动的距离，当距离超过最小拖动距离时屏幕将被拖动
	
	private boolean moveable = false;//屏幕是否允许被拖动
	
	private int lastId = -1;//上一次的触摸点的Id
	private float x1 = -1;//上一次的触摸点的横坐标
	private float x2 = -1;//这一次的触摸点的横坐标
	private float dX = 0;//触摸之后x轴要移动的距离
	
	
	
	
	private static int chapterId = -1;
	
	
	
	public SelectButtons() {
		super("SelectButtons", Rank.SELF_CUSTOM, ET.SelectButtons, null, null);
		btns = new ArrayList<OzRect>();
		btns.add(new OzRect(LIMIT_LEFT, FIRST_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
	}

	@Override
	public void reset() {
	}

	@Override
	public void logic() {
		
	}
	public void logic(HashMap<String, OzPoint> points){
//		l = points.get("0");
		x1 = x2;//重置坐标信息，因为这时候x2保存的实际是上一次的X坐标。
		if( points.size()==1 ){
			for(String key:points.keySet()){
				int id = Integer.parseInt(key);
				if( lastId==id ){
					x2 = points.get(key).x;
					System.out.println("last="+x1);
					System.out.println("now="+x2);
				}
				else{
					lastId = id;
				}
			}
		}
		l = points.get(""+lastId);
//		System.out.println("l="+l+" lastId="+lastId);
		
		boolean selected = false;
		if( l!=null ){
			for(int i=0;i<btns.size();i++){
				OzRect btn = btns.get(i);
				if(btn.inside(l, P.FORCE_RATIO)){
					chapterId = i + 1;
					selected = true;
					break;
				}
			}
		}
		if( !selected ){
			chapterId = -1;
		}
		
		
	}

	@Override
	public void draw() {
		
		for(int i=0;i<btns.size();i++){
			OzRect btn = btns.get(i);
			if( i+1==chapterId ){
				P.drawForce(btn.x, btn.y, Res.selectBtn[1]);
			}
			else{
				P.drawForce(btn.x, btn.y, Res.selectBtn[0]);
			}
		}
		
	}


	public static int getChapterId() {
		return chapterId;
	}

	@Override
	public void impact(Player player) {
	}

	@Override
	public void prepare() {
	}

	@Override
	public boolean rollBack() {
		return false;
	}

}
