package g.refer;



public interface Origin {
	
		//��������Ԫ����Ϣ
	   public abstract void reset();
	
		public abstract void logic();
		
		public abstract void draw();
		
		public abstract void impact(Player player);
		//Ԫ��ִ��prepare֮��Ż�ִ��engine
		public abstract void prepare();
		//���������ع�����
		public abstract boolean rollBack();
}
