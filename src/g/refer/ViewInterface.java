package g.refer;

import g.type.Status;

public interface ViewInterface {
	
	
	public void init();
	
	public void engine();
	
	/**
	 * 如果要把另外一个视图作为背景画出，则应将该视图作为参数传入  
	 * views[0,1,2,3,4] = { gameView,pauseView,selectView,startView,passView };
	 * */
	public void draw(ViewInterface ...views);
	
	/**
	 * 此视图进入时的动画效果逻辑
	 * */
	public boolean enter();
	
	/**
	 * 此视图离开时的动画效果逻辑
	 * */
	public boolean exit();
	
	/**
	 * 从此视图切换到另外一个视图
	 * views[0,1,2,3,4] = { gameView,pauseView,selectView,startView,passView };
	 * */
	public void thisToView(Status toStatus,ViewInterface ...views);
	
	public void reset();
}
