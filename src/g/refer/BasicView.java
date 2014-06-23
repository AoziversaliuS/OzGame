package g.refer;

import g.basis.MainEntry;
import g.tool.P;
import g.type.Status;

public abstract class BasicView implements ViewInterface{
	
    public static final int SWITCH_PREPARE=1,SWITCH_LOADING=2,SWITCH_LOADED=3,SWITCH_FINISH=4;
	
    public static final float BTN_SPEED = 35f;
    /**每个继承BasicView的view都有个switchType属性*/
	public int switchType = SWITCH_PREPARE;
	
	/**
	 * 从此视图切换到另外一个视图
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
