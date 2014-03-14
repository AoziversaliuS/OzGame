package g.tool;

public class OzPoint {
	
	
	
	public float x;
	public float y;
	
	public OzPoint(float x, float y,boolean fromScreen) {
		super();
		if(fromScreen){
			this.set_XY_fromScreen(x, y);
		}
		else{
			this.x = x;
			this.y = y;
		}
	}
	
	public OzPoint(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public OzPoint() {
		this.x = 0;
		this.y = 0;
	}
	public void set_XY_fromScreen(float screenX,float screenY) {
			this.x = screenX/P.getRatioX() ;
			this.y =(P.getScreenH() - screenY)/P.getRatioY() ;
	}
	
	
}
