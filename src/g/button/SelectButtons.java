package g.button;

import java.util.ArrayList;
import java.util.HashMap;

import g.basis.GameView;
import g.refer.OzElement;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;
import g.type.Status;

public class SelectButtons extends OzElement{

	private ArrayList<OzRect> btns;
	
	private static final float FIRST_LINE = 400;
	private static final float SECOND_LINE = 200;
	private static final int   LINE_BTN_NUM = 6;
	private static final float SPACE = 40;
	private static final float LIMIT_CENTER = 90;//��Ļ���Ͻǵ�һ����ť��x����,ͬʱҲ��Ϊ�жϵĻ�׼����
	private static final float LIMIT_RANGE = 200; //���ƵĴ�С
	private static final float ADJUST_SPEED = 30;
	
	private final float MIN_DRAG_RANGE = 50;//��С�ƶ����룬ֻ�г����������������ƶ�
	
	private float dragRange = 0;//һֻ��ָ�϶��ľ��룬�����볬����С�϶�����ʱ��Ļ�����϶�
	private static int currentPageId = 0; //��ʾ��ǰ������һҳ����Сҳ��Ϊ��0ҳ
	
	private static final int MAX_PAGE_NUM = 2;//���ҳ��
	private static final int MAX_BTN_NUM_ON_PAGE = 12;
	private static final int MOVE_QUIET=1/**��ֹ*/,MOVE_DRAG=2/**�϶�*/,MOVE_ADJUST=3/**����*/;
	private static final int DIR_LEFT=4,DIR_RIGHT=5;
	private int moveMold = MOVE_QUIET;
	
	private int lastId = -1;//��һ�εĴ������Id
	private float x1 = -1;//��һ�εĴ�����ĺ�����
	private float x2 = -1;//��һ�εĴ�����ĺ�����
	private float dX = 0;//����֮��x��Ҫ�ƶ��ľ���
	
	
	private static int chapterId = -1;
	
	
	
	public SelectButtons(){
		super("SelectButtons", Rank.SELF_CUSTOM, ET.SelectButtons, null, null);
		currentPageId = 0;//��ǰ���ڵ�0ҳ
		btns = new ArrayList<OzRect>();
		this.addButtons();
		System.out.println("���"+btns.get(0).x+"   �ұ�"+(P.BASIC_SCREEN_WIDTH - btns.get(11).getRight()));
		
	}
	
	private void addButtons(){
		
		for(int page=0;page<=MAX_PAGE_NUM;page++){
			//ÿһҳ����ʼ����
			float firstX = page*P.BASIC_SCREEN_WIDTH+LIMIT_CENTER;
			//��һ��
			btns.add(new OzRect(firstX, FIRST_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			for(int i=0;i<LINE_BTN_NUM-1;i++){
				float leftX = btns.get(btns.size()-1).getRight() + SPACE;
				btns.add(new OzRect(leftX, FIRST_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			}
			//�ڶ���
			btns.add(new OzRect(firstX, SECOND_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			for(int i=0;i<LINE_BTN_NUM-1;i++){
				float leftX = btns.get(btns.size()-1).getRight() + SPACE;
				btns.add(new OzRect(leftX, SECOND_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			}
		}
		System.out.println("�Ҽ��="+btns.get(btns.size()-1).getRight());
	}

	@Override
	public void reset() {
		for(int page=0;page<=MAX_PAGE_NUM;page++){
			//ÿһҳ����ʼ����
			OzRect btnBuff;
			float firstX = page*P.BASIC_SCREEN_WIDTH+LIMIT_CENTER;
			//��һ��
			OzRect btn = getSignBtn(page);
			int btnId = getBtnId(btn);
			btn.x = firstX;
			btnBuff = btn;
			for(int i=btnId+1;i<btnId+LINE_BTN_NUM;i++){
				float leftX = btnBuff.getRight() + SPACE;
				btns.get(i).x = leftX;
				btnBuff = btns.get(i);
			}
			//�ڶ���
		    btn = btns.get(btnId+LINE_BTN_NUM);
		    btnId = getBtnId(btn);
		    btn.x = firstX;
			btnBuff = btn;
			for(int i=btnId+1;i<btnId+LINE_BTN_NUM;i++){
				float leftX = btnBuff.getRight() + SPACE;
				btns.get(i).x = leftX;
				btnBuff = btns.get(i);
			}
		}
	}

	@Override
	public void logic() {
		this.adjust();
	}
	
	private void moveConfirm(){
		if( moveMold==MOVE_QUIET || moveMold==MOVE_ADJUST ){
			dragRange = dragRange + dX;
			if( Math.abs(dragRange)>MIN_DRAG_RANGE ){
				moveMold = MOVE_DRAG;
			}
		}
	}
	

	private void adjust(){
		if( moveMold==MOVE_ADJUST ){
			int dir = getDir();
			if( dir==DIR_LEFT ){
				OzRect leftBtn = getLeftSignBtn();//��Ȼ�Ѿ��ж��˷���Ϊleft����ôleftBtn������Ϊnull!
				OzRect rightBtn = getRightSignBtn();
				float moveRange = Math.abs( leftBtn.x-LIMIT_CENTER );
				
				if( moveRange<=LIMIT_RANGE || rightBtn==null ){
					//��ԭ  �ڱ�־��ť [û�����޶���Χ] �� [û����һҳ] ������½���
					if( moveRange>ADJUST_SPEED ){
						dX = ADJUST_SPEED;
					}
					else{
						dX = moveRange;
						moveMold = MOVE_QUIET;
					}
				}
				else{
					//�ƶ�����һҳ
					if( rightBtn.x-LIMIT_CENTER>ADJUST_SPEED ){
						dX = -ADJUST_SPEED;
					}
					else{
						dX = -(rightBtn.x-LIMIT_CENTER);
						moveMold = MOVE_QUIET;
					}	
					
				}
			}
			else if( dir==DIR_RIGHT ){
				OzRect leftBtn = getLeftSignBtn();//��Ȼ�Ѿ��ж��˷���Ϊleft����ôleftBtn������Ϊnull!
				OzRect rightBtn = getRightSignBtn();
				float moveRange = Math.abs( rightBtn.x-LIMIT_CENTER );
				
				if( moveRange<=LIMIT_RANGE || leftBtn==null ){
					//�һ�ԭ  �ڱ�־��ť [û�����޶���Χ] �� [û����һҳ] ������½���
					if( moveRange>ADJUST_SPEED ){
						dX = -ADJUST_SPEED;
					}
					else{
						dX = -moveRange;
						moveMold = MOVE_QUIET;
					}
				}
				else{
					//�ƶ�����һҳ
					if( LIMIT_CENTER-leftBtn.x >ADJUST_SPEED ){
						dX = ADJUST_SPEED;
					}
					else{
						dX = LIMIT_CENTER-leftBtn.x;
						moveMold = MOVE_QUIET;
					}	
					
				}
				
			}
			//ÿһ֡�İ�ť��λ����
			for(OzRect btn:btns){
				btn.x = btn.x + dX;
			}
			if( moveMold==MOVE_QUIET ){
				currentPageId = getPageId();//���赱ǰҳ��Id
				dX = 0;
			}
		}
	}
	//�˷���ֻ������Ļ���д������ʱ��ŵ���
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
						chapterId = i;
						selected = true;
						break;
					}
				}
			}
			else if( moveMold==MOVE_DRAG ){
				//�ƶ�״̬
				for(OzRect btn:btns){
					btn.x = btn.x + dX;
				}
			}
			
		}
		
		
		if( !selected ){
			if( GameView.switchFinished() ){//������ת��ɺ��������
				chapterId = -1;
			}
		}
		else{
			GameView.setToStatus(Status.Game);
			selected = false;
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
			if( moveMold==MOVE_DRAG ){
				moveMold = MOVE_ADJUST;  //������϶�״̬��Ҫ��Ϊ����״̬
			}
		}
		l = points.get(""+lastId);
	
	}

	@Override
	public void draw() {
		
		for(int i=0;i<btns.size();i++){
			OzRect btn = btns.get(i);
			if( i==chapterId ){
				P.drawForce(btn.x, btn.y, Res.selectBtn[1]);
			}
			else{
				P.drawForce(btn.x, btn.y, Res.selectBtn[0]);
			}
		}
		
	}

	private int getDir(){
		
		OzRect signBtn = getSignBtn(currentPageId);
		
		if( signBtn.x <= LIMIT_CENTER ){
			return DIR_LEFT;
		}
		else if( signBtn.x >= LIMIT_CENTER ){
			return DIR_RIGHT;
		}
		
		return -1;
		
	}
	
	private OzRect getSignBtn(int pId){
		int btnId = pId*MAX_BTN_NUM_ON_PAGE;
		if( btnId<btns.size() ){
			return btns.get(btnId);
		}
		return null;
		
	}
	private int getBtnId(OzRect btn){
		for(int i=0;i<btns.size();i++){
			if( btns.get(i)==btn ){
				return i;
			}
		}
		return -1;
	}
	private int getPageId(){
		int pId = -1;
		float range = -1;
		for(int i=0;i<=MAX_PAGE_NUM;i++){
			OzRect signBtn = getSignBtn(i);
			if(i==0){
				pId = i;
				range = Math.abs(signBtn.x-LIMIT_CENTER);
			}
			else{
				if( Math.abs(signBtn.x-LIMIT_CENTER)<range ){
					pId = i;
				}
			}
			
		}
		return pId;
	}
	
	private int getPageId(OzRect signBtn){
		int btnId=0;
		int pId = -1;
		for(;btnId<btns.size();btnId++){
			if( btns.get(btnId)==signBtn ){
				break;
			}
		}
		pId = btnId/MAX_BTN_NUM_ON_PAGE;
		return pId;
	}
	private OzRect getLeftSignBtn(){
		OzRect leftBtn = null;
		
		for(int i=0;i<=MAX_PAGE_NUM;i++){
			OzRect btn = getSignBtn(i);
			if( btn.x<=LIMIT_CENTER ){
				leftBtn = btn;
			}
		}
		return leftBtn;
	}
	private OzRect getRightSignBtn(){
		OzRect rightBtn = null;
		for(int i=0;i<=MAX_PAGE_NUM;i++){
			OzRect btn = getSignBtn(i);
			if( btn.x>=LIMIT_CENTER ){
				rightBtn = btn;
			}
		}
		return rightBtn;
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
