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
	public void logic(HashMap<String, OzPoint> points){
//		l = points.get("0");
		x1 = x2;//����������Ϣ����Ϊ��ʱ��x2�����ʵ������һ�ε�X���ꡣ
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
