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
	 * */
	public void thisToView(Status status,ViewInterface ...viewInterfaces);
}
