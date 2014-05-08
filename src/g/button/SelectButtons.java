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
	
	private final float FIRST_LINE = 400;
	private final float SECOND_LINE = 200;
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
	
	private void moveConfirm(){
		if( !moveable ){
			dragRange = dragRange + dX;
			if( Math.abs(dragRange)>MIN_DRAG_RANGE ){
				moveable = true;
			}
		}
	}
	
	private void pointsArithmetic(HashMap<String, OzPoint> points){
	
		x1 = x2;//重置坐标信息，因为这时候x2保存的实际是上一次的X坐标。
		if( points.size()==1 ){
			//用于处理第一个手指碰上去，然后第二个手指碰上去，最后第一个手指离开，第二个手指还未离开的情况
			for(String key:points.keySet()){
				int id = Integer.parseInt(key);
				if( lastId==id ){
					x2 = points.get(id+"").x;
					dX = x2 - x1;
					this.moveConfirm();//判断是否需要移动屏幕
				}
				else{
					lastId = id;
					//更换触碰点时使触碰点坐标赋值给X1先;
					x1 = points.get(id+"").x;
					x2 = points.get(id+"").x;
				}
			}
		}
		else if(  points.get(lastId+"")!=null  ){
			//用于处理有很多个手指，但第一次碰上去的手指仍未离开的情况
					x2 = points.get(lastId+"").x;
					dX = x2 - x1;
					this.moveConfirm();//判断是否需要移动屏幕
		}
		else{
			//所有手指离开之后,或第一个手指离开之后还有多个手指的情况下,重置信息
			lastId = -1;
			dragRange = 0;
			moveable = false;
		}
		l = points.get(""+lastId);
	}
	
	public void logic(HashMap<String, OzPoint> points){
		
		//触摸点的算法
		this.pointsArithmetic(points);

		
		boolean selected = false;

		if(l!=null){

			if( !moveable ){
				//非移动状态判断是否按下了按钮
				for(int i=0;i<btns.size();i++){
					OzRect btn = btns.get(i);
					if(btn.inside(l, P.FORCE_RATIO)){
						chapterId = i + 1;
						selected = true;
						break;
					}
				}
			}
			else{
				//移动状态
				for(OzRect btn:btns){
					btn.x = btn.x + dX;
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
