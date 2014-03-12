package g.tool;

public class OzPoint {
	
	
	
	public float x;
	public float y;
	
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
	public void set_XY_fromScreen(float screenX,float screenY) {
			this.x = x/P.getRatioX() ;
			this.y = y/P.getRatioY() ;
	}
	
	
}
