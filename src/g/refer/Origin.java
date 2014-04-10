package g.refer;



public interface Origin {
	
		//重置所有元素信息
	   public abstract void reset();
	
		public abstract void logic();
		
		public abstract void draw();
		
		public abstract void impact(Player player);
		//元素执行prepare之后才会执行engine
		public abstract void prepare();
		//玩家死亡后回滚坐标
		public abstract boolean rollBack();
}
