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
	
	public boolean inside(OzPoint point){
		//�ж�һ�����Ƿ���һ��������
		if( point.x>x && point.x<width && point.y>y &&point.y<height ){
			return true;
		}
		return false;
	}
	public boolean insides(HashMap<String, OzPoint> points){
		//�ж�һ��[�㼯��]���Ƿ���һ������һ��������
		Set<String> keys = points.keySet();
		for(String key:keys){
			if(        points.get(key).x>x && points.get(key).x<width 
					&& points.get(key).y>y &&points.get(key).y<height ){
				Gdx.app.log("btn","�ж��ɹ�");
				return true;
			}
		}
		return false;
	}
	public boolean intersect(OzRect rect){
		//�ж����������Ƿ��ཻ
		if(width<rect.x || height<rect.y || y>rect.height || x>rect.width){
			//��rect�����                  ��rect�ĵײ�                     ��rect������                          ��rect���ұ�         �ѿ�������ϵ
			return false;
		}
		return true;
	}
	
	
	
	
	
}
