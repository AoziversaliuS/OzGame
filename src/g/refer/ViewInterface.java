package g.refer;

public interface ViewInterface {
	
	public void init();
	
	public void engine();
	
	/**
	 * ���Ҫ������һ����ͼ��Ϊ������������Ӧ������ͼ��Ϊ��������
	 * 
	 * */
	public void draw(ViewInterface ...viewInterfaces);
	
}
