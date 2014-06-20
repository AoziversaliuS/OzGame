package g.refer;

import g.type.Status;

public interface ViewInterface {
	
	public void init();
	
	public void engine();
	
	/**
	 * 如果要把另外一个视图作为背景画出，则应将该视图作为参数传入
	 * */
	public void draw(ViewInterface ...viewInterfaces);
	
	/**
	 * 从此视图切换到另外一个视图
	 * views = { gameView,pauseView,selectView,startView };
	 * */
	public void thisToView(Status toStatus,ViewInterface ...views);
}
