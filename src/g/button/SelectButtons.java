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
	private final float LIMIT_LEFT = 65;//��Ļ���Ͻǵ�һ����ť��x����
	
	private final float MIN_DRAG_RANGE = 50;//��С�ƶ����룬ֻ�г����������������ƶ�
	
	private float dragRange = 0;//һֻ��ָ�϶��ľ��룬�����볬����С�϶�����ʱ��Ļ�����϶�
	private static int pageNum = 0; //��ʾ��ǰ������һҳ����Сҳ��Ϊ��0ҳ
	private static final int MAX_PAGE_NUM = 2;//���ҳ��
	
	private static final int MOVE_QUIET=1/**��ֹ*/,MOVE_DRAG=2/**�϶�*/,MOVE_ADJUST=3/**����*/;
	private int moveMold = MOVE_QUIET;
	
	private int lastId = -1;//��һ�εĴ������Id
	private float x1 = -1;//��һ�εĴ�����ĺ�����
	private float x2 = -1;//��һ�εĴ�����ĺ�����
	private float dX = 0;//����֮��x��Ҫ�ƶ��ľ���
	
	
	
	
	private static int chapterId = -1;
	
	
	
	public SelectButtons(){
		super("SelectButtons", Rank.SELF_CUSTOM, ET.SelectButtons, null, null);
		btns = new ArrayList<OzRect>();
		this.addButtons();
		
	}
	
	private void addButtons(){
		
		for(int page=0;page<=MAX_PAGE_NUM;page++){
			//ÿһҳ����ʼ����
			float firstX = page*P.BASIC_SCREEN_WIDTH+LIMIT_LEFT;
			//��һ��
			btns.add(new OzRect(firstX, FIRST_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			for(int i=0;i<5;i++){
				float leftX = btns.get(btns.size()-1).getRight() + SPACE;
				btns.add(new OzRect(leftX, FIRST_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			}
			//�ڶ���
			btns.add(new OzRect(firstX, SECOND_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			for(int i=0;i<5;i++){
				float leftX = btns.get(btns.size()-1).getRight() + SPACE;
				btns.add(new OzRect(leftX, SECOND_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			}
		}
		System.out.println("�Ҽ��="+btns.get(btns.size()-1).getRight());
	}

	@Override
	public void reset() {
	}

	@Override
	public void logic() {
		
	}
	
	private void moveConfirm(){
		if( moveMold==MOVE_QUIET || moveMold==MOVE_ADJUST ){
			dragRange = dragRange + dX;
			if( Math.abs(dragRange)>MIN_DRAG_RANGE ){
				moveMold = MOVE_DRAG;
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
		else if( moveMold==MOVE_ADJUST ){
			

			
		}
		else{
			//������ָ�뿪֮��,���һ����ָ�뿪֮���ж����ָ�������,������Ϣ
			lastId = -1;
			dragRange = 0;
			moveMold = MOVE_QUIET;
		}
		l = points.get(""+lastId);
	}
	
	public void logic(HashMap<String, OzPoint> points){
		
		//��������㷨
		this.pointsArithmetic(points);

		
		boolean selected = false;

		if(l!=null){

			if( moveMold==MOVE_QUIET ){
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
			else if( moveMold==MOVE_DRAG ||  moveMold==MOVE_ADJUST ){
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
