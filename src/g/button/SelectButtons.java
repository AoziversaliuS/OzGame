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
	private static final float LIMIT_CENTER = 90;//屏幕左上角第一个按钮的x坐标,同时也作为判断的基准中线
	private static final float LIMIT_RANGE = 200; //限制的大小
	private static final float ADJUST_SPEED = 30;
	
	private final float MIN_DRAG_RANGE = 50;//最小移动距离，只有超过了这个距离才能移动
	
	private float dragRange = 0;//一只手指拖动的距离，当距离超过最小拖动距离时屏幕将被拖动
	private static int currentPageId = 0; //表示当前处于哪一页，最小页数为第0页
	
	private static final int MAX_PAGE_NUM = 2;//最大页数
	private static final int MAX_BTN_NUM_ON_PAGE = 12;
	private static final int MOVE_QUIET=1/**静止*/,MOVE_DRAG=2/**拖动*/,MOVE_ADJUST=3/**调整*/;
	private static final int DIR_LEFT=4,DIR_RIGHT=5;
	private int moveMold = MOVE_QUIET;
	
	private int lastId = -1;//上一次的触摸点的Id
	private float x1 = -1;//上一次的触摸点的横坐标
	private float x2 = -1;//这一次的触摸点的横坐标
	private float dX = 0;//触摸之后x轴要移动的距离
	
	
	private static int chapterId = -1;
	
	
	
	public SelectButtons(){
		super("SelectButtons", Rank.SELF_CUSTOM, ET.SelectButtons, null, null);
		currentPageId = 0;//当前处于第0页
		btns = new ArrayList<OzRect>();
		this.addButtons();
		System.out.println("左边"+btns.get(0).x+"   右边"+(P.BASIC_SCREEN_WIDTH - btns.get(11).getRight()));
		
	}
	
	private void addButtons(){
		
		for(int page=0;page<=MAX_PAGE_NUM;page++){
			//每一页的起始坐标
			float firstX = page*P.BASIC_SCREEN_WIDTH+LIMIT_CENTER;
			//第一行
			btns.add(new OzRect(firstX, FIRST_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			for(int i=0;i<LINE_BTN_NUM-1;i++){
				float leftX = btns.get(btns.size()-1).getRight() + SPACE;
				btns.add(new OzRect(leftX, FIRST_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			}
			//第二行
			btns.add(new OzRect(firstX, SECOND_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			for(int i=0;i<LINE_BTN_NUM-1;i++){
				float leftX = btns.get(btns.size()-1).getRight() + SPACE;
				btns.add(new OzRect(leftX, SECOND_LINE, Res.selectBtn[0].getWidth(), Res.selectBtn[0].getHeight()));
			}
		}
		System.out.println("右间距="+btns.get(btns.size()-1).getRight());
	}

	@Override
	public void reset() {
		for(int page=0;page<=MAX_PAGE_NUM;page++){
			//每一页的起始坐标
			OzRect btnBuff;
			float firstX = page*P.BASIC_SCREEN_WIDTH+LIMIT_CENTER;
			//第一行
			OzRect btn = getSignBtn(page);
			int btnId = getBtnId(btn);
			btn.x = firstX;
			btnBuff = btn;
			for(int i=btnId+1;i<btnId+LINE_BTN_NUM;i++){
				float leftX = btnBuff.getRight() + SPACE;
				btns.get(i).x = leftX;
				btnBuff = btns.get(i);
			}
			//第二行
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
				OzRect leftBtn = getLeftSignBtn();//既然已经判定了方向为left，那么leftBtn不可能为null!
				OzRect rightBtn = getRightSignBtn();
				float moveRange = Math.abs( leftBtn.x-LIMIT_CENTER );
				
				if( moveRange<=LIMIT_RANGE || rightBtn==null ){
					//左还原  在标志按钮 [没超过限定范围] 或 [没有下一页] 的情况下进入
					if( moveRange>ADJUST_SPEED ){
						dX = ADJUST_SPEED;
					}
					else{
						dX = moveRange;
						moveMold = MOVE_QUIET;
					}
				}
				else{
					//移动到下一页
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
				OzRect leftBtn = getLeftSignBtn();//既然已经判定了方向为left，那么leftBtn不可能为null!
				OzRect rightBtn = getRightSignBtn();
				float moveRange = Math.abs( rightBtn.x-LIMIT_CENTER );
				
				if( moveRange<=LIMIT_RANGE || leftBtn==null ){
					//右还原  在标志按钮 [没超过限定范围] 或 [没有上一页] 的情况下进入
					if( moveRange>ADJUST_SPEED ){
						dX = -ADJUST_SPEED;
					}
					else{
						dX = -moveRange;
						moveMold = MOVE_QUIET;
					}
				}
				else{
					//移动到上一页
					if( LIMIT_CENTER-leftBtn.x >ADJUST_SPEED ){
						dX = ADJUST_SPEED;
					}
					else{
						dX = LIMIT_CENTER-leftBtn.x;
						moveMold = MOVE_QUIET;
					}	
					
				}
				
			}
			//每一帧的按钮移位操作
			for(OzRect btn:btns){
				btn.x = btn.x + dX;
			}
			if( moveMold==MOVE_QUIET ){
				currentPageId = getPageId();//重设当前页的Id
				dX = 0;
			}
		}
	}
	//此方法只有在屏幕上有触碰点的时候才调用
	public void logic(HashMap<String, OzPoint> points){
		
		//触摸点的算法
		this.pointsArithmetic(points);

		
		boolean selected = false;

		if(l!=null){

			if( moveMold==MOVE_QUIET ){
				//非移动状态判断是否按下了按钮
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
				//移动状态
				for(OzRect btn:btns){
					btn.x = btn.x + dX;
				}
			}
			
		}
		
		
		if( !selected ){
			if( GameView.switchFinished() ){//界面跳转完成后重设变量
				chapterId = -1;
			}
		}
		else{
			GameView.setToStatus(Status.Game);
			selected = false;
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
			if( moveMold==MOVE_DRAG ){
				moveMold = MOVE_ADJUST;  //如果是拖动状态就要变为调整状态
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
