package g.refer;

import g.type.Status;

public interface ViewInterface {
	
	public void init();
	
	public void engine();
	
	/**
	 * ���Ҫ������һ����ͼ��Ϊ������������Ӧ������ͼ��Ϊ��������
	 * */
	public void draw(ViewInterface ...viewInterfaces);
	
	/**
	 * �Ӵ���ͼ�л�������һ����ͼ
	 * */
	public void thisToView(Status status,ViewInterface ...viewInterfaces);
}