package g.refer;

import g.type.Status;

public abstract class BasicView implements ViewInterface{
	
    public static final int SWITCH_PREPARE=1,SWITCH_LOADING=2,SWITCH_LOADED=3,SWITCH_FINISH=4;
	
    //每个view都有个switchType
	public int switchType = SWITCH_PREPARE;
	
	public void toView(Status status,ViewInterface ...viewInterfaces){
		thisToView(status, viewInterfaces);
		if( switchType==SWITCH_FINISH ){
			
		}
	}
	
}
