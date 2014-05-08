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
	
	private final float MIN_DRAG_RANGE = 50;//��С�ƶ����룬ֻ�г����������������ƶ�
	
	private float dragRange = 0;//һֻ��ָ�϶��ľ��룬�����볬����С�϶�����ʱ��Ļ�����϶�
	
	private boolean moveable = false;//��Ļ�Ƿ������϶�
	
	private int lastId = -1;//��һ�εĴ������Id
	private float x1 = -1;//��һ�εĴ�����ĺ�����
	private float x2 = -1;//��һ�εĴ�����ĺ�����
	private float dX = 0;//����֮��x��Ҫ�ƶ��ľ���
	
	
	
	
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
	
		x1 = x2;//����������Ϣ����Ϊ��ʱ��x2�����ʵ������һ�ε�X���ꡣ
		if( points.size()==1 ){
			//���ڴ����һ����ָ����ȥ��Ȼ��ڶ�����ָ����ȥ������һ����ָ�뿪���ڶ�����ָ��δ�뿪�����
			for(String key:points.keySet()){
				int id = Integer.parseInt(key);
				if( lastId==id ){
					x2 = points.get(id+"").x;
					dX = x2 - x1;
					this.moveConfirm();//�ж��Ƿ���Ҫ�ƶ���Ļ
				}
				else{
					lastId = id;
					//����������ʱʹ���������긳ֵ��X1��;
					x1 = points.get(id+"").x;
					x2 = points.get(id+"").x;
				}
			}
		}
		else if(  points.get(lastId+"")!=null  ){
			//���ڴ����кܶ����ָ������һ������ȥ����ָ��δ�뿪�����
					x2 = points.get(lastId+"").x;
					dX = x2 - x1;
					this.moveConfirm();//�ж��Ƿ���Ҫ�ƶ���Ļ
		}
		else{
			//������ָ�뿪֮��,���һ����ָ�뿪֮���ж����ָ�������,������Ϣ
			lastId = -1;
			dragRange = 0;
			moveable = false;
		}
		l = points.get(""+lastId);
	}
	
	public void logic(HashMap<String, OzPoint> points){
		
		//��������㷨
		this.pointsArithmetic(points);

		
		boolean selected = false;

		if(l!=null){

			if( !moveable ){
				//���ƶ�״̬�ж��Ƿ����˰�ť
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
				//�ƶ�״̬
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
