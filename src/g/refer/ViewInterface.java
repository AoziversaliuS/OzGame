package g.refer;

import g.type.Status;

public interface ViewInterface {
	
	public void init();
	
	public void engine();
	
	/**
	 * ���Ҫ������һ����ͼ��Ϊ������������Ӧ������ͼ��Ϊ��������  
	 * views[0,1,2,3,4] = { gameView,pauseView,selectView,startView,passView };
	 * */
	public void draw(ViewInterface ...views);
	
	/**
	 * �Ӵ���ͼ�л�������һ����ͼ
	 * views[0,1,2,3,4] = { gameView,pauseView,selectView,startView,passView };
	 * */
	public void thisToView(Status toStatus,ViewInterface ...views);
	
	public void reset();
}
