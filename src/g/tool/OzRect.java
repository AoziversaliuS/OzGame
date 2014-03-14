package g.tool;

import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.Gdx;

public class OzRect {
	public float x;
	public float y;
	public float width;
	public float height;
	public OzRect(float x, float y, float width, float height,boolean isOffset) {
		this.x = x;
		this.y = y;
		if(isOffset){
			this.width = width;
			this.height = height;
		}
		else{
			this.width = x + width;
			this.height = y + height;
		}
		
	}
	public OzRect() {
		super();
	}
	
	public boolean inside(OzPoint point){
		//判断一个点是否在一个矩形内
		if( point.x>x && point.x<width && point.y>y &&point.y<height ){
			return true;
		}
		return false;
	}
	public boolean insides(HashMap<String, OzPoint> points){
		//判断一个[点集合]里是否有一个点在一个矩形内
		Set<String> keys = points.keySet();
		for(String key:keys){
			if(        points.get(key).x>x && points.get(key).x<width 
					&& points.get(key).y>y &&points.get(key).y<height ){
				Gdx.app.log("btn","判定成功");
				return true;
			}else{
				Gdx.app.log("btn","判定失败:  point X:"+ points.get(key).x+" Y: "+points.get(key).y);
				Gdx.app.log("btn","判定失败:  AX:"+x+" AY: "+y+" WIDTH: "+width+" HEIGHT: "+height);
			}

		}
		
		return false;
	}
	public boolean intersect(OzRect rect){
		//判断两个矩形是否相交
		if(width<rect.x || height<rect.y || y>rect.height || x>rect.width){
			//在rect的左边                  在rect的底部                     在rect的上面                          在rect的右边         笛卡尔坐标系
			return false;
		}
		return true;
	}
	public float centerX() {
		return (x+width)/2;
	}
	
	
	
	
	
}
