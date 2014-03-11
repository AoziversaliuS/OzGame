package g.tool;

public class OzPoint {
	private float x;
	private float y;
	
	public OzPoint(float x, float y,boolean fromScreen) {
		super();
		if(fromScreen){
			this.x = x/P.getRatioX() ;
			this.y = y/P.getRatioY() ;
		}
		else{
			this.x = x;
			this.y = y;
		}
	}
	public OzPoint() {
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void set_XY_fromScreen(float screenX,float screenY) {
		this.x = x/P.getRatioX() ;
		this.y = y/P.getRatioY() ;
	}
	
	
}
