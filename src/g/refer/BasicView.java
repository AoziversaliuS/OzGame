package g.refer;

import g.basis.MainEntry;
import g.tool.P;
import g.type.Status;

public abstract class BasicView implements ViewInterface{
	
    public static final int SWITCH_PREPARE=1,SWITCH_LOADING=2,SWITCH_LOADED=3,SWITCH_FINISH=4;
	
    public static final float BTN_SPEED = 35f;
    /**ÿ���̳�BasicView��view���и�switchType����*/
	public int switchType = SWITCH_PREPARE;
	
	/**
	 * �Ӵ���ͼ�л�������һ����ͼ
	 * */
	public void toView(Status status,ViewInterface ...views){
		
		thisToView(status, views);
		
		if( switchType==SWITCH_FINISH ){
			switchType = SWITCH_PREPARE;
			MainEntry.finishSwitch();
			P.setDarkness(0);
		}
	}
	
	
}
