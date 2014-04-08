package g.refer;



public interface Origin {
	
	
	   public abstract void reset();
	
		public abstract void logic();
		
		public abstract void draw();
		
		public abstract void impact(Player player);
		
		//玩家死亡后回滚坐标
		public abstract boolean rollBack();
}
