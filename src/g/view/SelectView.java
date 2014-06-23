package g.view;

import java.util.HashMap;

import g.basis.GameChapter;
import g.button.SelectButtons;
import g.button.SelectReturnButtons;
import g.refer.BasicView;
import g.refer.BtnMethods;
import g.refer.ViewInterface;
import g.tool.Data;
import g.tool.OzPoint;
import g.tool.P;
import g.tool.Res;
import g.type.Status;

public class SelectView extends BasicView implements BtnMethods{

	private static SelectButtons  selectBtns;
	private SelectReturnButtons toMainBtn;
	
	private static final float FIRST_PAGE_POINT_X = 370;
	private static final float PAGE_POINT_Y = 70;
	private static final float SPACE = 100;//pagePoint֮��ļ��
	
	public SelectView() {
		super();
		this.init();
	}

	@Override
	public void init() {
		selectBtns = new SelectButtons();
		toMainBtn = new SelectReturnButtons();
	}

	@Override
	public void engine() {
		selectBtns.logic();
	}

	@Override
	public void draw(ViewInterface ...viewInterfaces) {
		P.draw(0, 0, Res.selectBg, P.FORCE_RATIO);
		selectBtns.draw();
		toMainBtn.draw();
		for(int i=0;i<=SelectButtons.MAX_PAGE_NUM;i++){
			if( i!=selectBtns.getPageId() ){
				P.draw(FIRST_PAGE_POINT_X+i*SPACE, PAGE_POINT_Y, Res.selectPagePoint[0]);
			}
			else{
				P.draw(FIRST_PAGE_POINT_X+i*SPACE, PAGE_POINT_Y, Res.selectPagePoint[1]);
			}
		}
	}
	/**
	 * ��ȡ��ǰѡ�еĹؿ�Id
	 * */
	public static int getChapterId(){
		return selectBtns.getChapterId();
	}

	
	@Override
	public void btnLogic(HashMap<String, OzPoint> points) {
		selectBtns.logic(points);
		toMainBtn.btnLogic(points);
	}
	
	/**
	 * ���� chapterId+1 �Ĺؿ�,�����ݲ������ж��Ƿ���ҪʹchapterId����
	 * (���������ĵ�������door������)
	 * */
	public static void unlockNextChapter(boolean increaseChapterId){
		
		Data.unLockChapter(selectBtns.getChapterId()+1);
		if(increaseChapterId){
			selectBtns.increaseChapterId();
		}
//		//ȥ����ǰ�ؿ����ڵ�ҳ��
		selectBtns.toCurrentChapterPage();
//		//������һ�ؿ�
		selectBtns.refreshUnlockId();
	}
	
		


	@Override
	public void thisToView(Status toStatus, ViewInterface... views) {
		switch (toStatus) {
		
		case Game:{ toGameView(views); break; }
			
		case Start:{ toStartView(views); break; }

		}
	}
	private void toStartView(ViewInterface... views){
		StartView startView = (StartView) views[3];
		if( switchType==SWITCH_PREPARE ){
			P.increaseDarkness();
			this.draw(views);
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
			}
		}
		else if( switchType==SWITCH_LOADING ){
			//�ͷ���Դ
//			Res.unload(Res.GAME_A);
			this.reset();
			switchType = SWITCH_LOADED;
		}
		else if( switchType==SWITCH_LOADED ){
			P.decreaseDarkness();
			startView.draw(views);
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				switchType = SWITCH_FINISH;
			}
		}
	}
	private void toGameView( ViewInterface... views){
			GameView gameView = (GameView) views[0];
		if( switchType==SWITCH_PREPARE ){
			P.increaseDarkness();
			this.draw(views);
			if( P.getBlackNum()>=P.MAX_BLACK_NUM ){
				switchType = SWITCH_LOADING;
				Res.prepare(Res.GAME_A);
			}
		}
		else if( switchType==SWITCH_LOADING ){
			this.draw(views);
			if(Res.update()){
				//������ͼƬ֮�������ͼ
				System.out.println(" C chapterId = "+SelectView.getChapterId());
				GameChapter.chapterLoad(
						gameView.getGateAtlas(),
						gameView.getRankNum(),
						SelectView.getChapterId()
						); 
				gameView.reset();
				switchType = SWITCH_LOADED;
			}
		}
		else if( switchType==SWITCH_LOADED ){
			P.decreaseDarkness();
			gameView.draw(views);
			if( P.getBlackNum()<=P.MIN_BLACK_NUM ){
				switchType = SWITCH_FINISH;
			}
		}
	}

	private void toPage(int pId){
		selectBtns.toPage(pId);
	}
	@Override
	public void reset() {
		selectBtns.reset();
	}

	@Override
	public boolean enter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exit() {
		// TODO Auto-generated method stub
		return false;
	}
}
