package g.tool;

import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.Gdx;

public class OzRect {
	public float x;
	public float y;
	public float width;
	public float height;
	public OzRect(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	public OzRect() {
		super();
	}
	

	public boolean insides(HashMap<String, OzPoint> points,int rationType){
		//判断一个[点集合]里是否有一个点在一个矩形内
		Set<String> keys = points.keySet();
		

		for(String key:keys){
			

			
			if(  inside(points.get(key),rationType)==true ){
//				Gdx.app.log("btn","判定成功");
				return true;
			}else{
//				Gdx.app.log("btn","判定失败:  point X:"+ points.get(key).x+" Y: "+points.get(key).y);
//				Gdx.app.log("btn","判定失败:  AX:"+x+" AY: "+y+" WIDTH: "+width+" HEIGHT: "+height);
			}

		}
		
		return false;
	}
	private float get_pX_by(int rationType,OzPoint point){
		if( rationType==P.AUTO_RATIO ){
			return point.x/P.getRatioX();
		}
		else if( rationType==P.FORCE_RATIO ){
			return point.x/P.getForceRatioX();
		}
		else if( rationType==P.BG_RATIO ){
			return point.x/P.getBgRatioX();
		}
		return -1f;
	}
	private float get_pY_by(int rationType,OzPoint point){
		if( rationType==P.AUTO_RATIO ){
			return point.y/P.getRatioY();
		}
		else if( rationType==P.FORCE_RATIO ){
			return point.y/P.getForceRatioY();
		}
		else if( rationType==P.BG_RATIO ){
			return point.y/P.getBgRatioY();
		}
		return -1f;
	}
	public boolean intersect(OzRect rect){
		//判断两个矩形是否相交
		if(this.getRight()<rect.x || this.getTop()<rect.y || y>rect.getTop() || x>rect.getRight()){
			//在rect的左边                                                  在rect的底部                                    在rect的上面                                  在rect的右边             笛卡尔坐标系
			return false;
		}
		return true;
	}
	
	public boolean inside(OzPoint point,int rationType){
		float pX=-1f,pY=-1f;
		pX = get_pX_by(rationType, point);
		pY = get_pY_by(rationType, point);
		//判断一个点是否在一个矩形内
		if( pX>x && pX<this.getRight() && pY>y && pY<this.getTop() ){
			return true;
		}
		return false;
	}
	
	public float centerX() {
		return (x+width/2);
	}
	public float centerY() {
		return (y+height/2);
	}
	
	public float getRight(){
		return (x+width);
	}
	public float getTop(){
		return (y+height);
	}
	
	
	
	
	
}
