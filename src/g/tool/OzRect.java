package g.tool;

import java.util.HashMap;
import java.util.Set;

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
	
	public boolean inside(OzPoint point){
		//判断一个点是否在一个矩形内
		if( point.x>x && point.x<width && point.y>y &&point.y<height ){
			return true;
		}
		return false;
	}
	public boolean inside(HashMap<String, OzPoint> points){
		//判断一个[点集合]里是否有一个点在一个矩形内
		Set<String> keys = points.keySet();
		for(String key:keys){
			if(        points.get(key).x>x && points.get(key).x<width 
					&& points.get(key).y>y &&points.get(key).y<height ){
				return true;
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
	
	
	
	
	
}
