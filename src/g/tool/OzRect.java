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
		if( point.x>x && point.x<this.getRight() && point.y>y &&point.y<this.getTop() ){
			return true;
		}
		return false;
	}
	public boolean insides(HashMap<String, OzPoint> points){
		//�ж�һ��[�㼯��]���Ƿ���һ������һ��������
		Set<String> keys = points.keySet();
		for(String key:keys){
			if(        points.get(key).x>x && points.get(key).x<this.getRight() 
					&& points.get(key).y>y &&points.get(key).y<this.getTop() ){
				Gdx.app.log("btn","�ж��ɹ�");
				return true;
			}else{
				Gdx.app.log("btn","�ж�ʧ��:  point X:"+ points.get(key).x+" Y: "+points.get(key).y);
				Gdx.app.log("btn","�ж�ʧ��:  AX:"+x+" AY: "+y+" WIDTH: "+width+" HEIGHT: "+height);
			}

		}
		
		return false;
	}
	public boolean intersect(OzRect rect){
		//�ж����������Ƿ��ཻ
		if(this.getRight()<rect.x || this.getTop()<rect.y || y>rect.getTop() || x>rect.getRight()){
			//��rect�����                                                  ��rect�ĵײ�                                    ��rect������                                  ��rect���ұ�             �ѿ�������ϵ
//			Gdx.app.log(" impact", "land x "+x+" y "+y+" Right "+this.getRight()+" Top "+this.getTop());
//			Gdx.app.log(" impact", "player x "+rect.x+"  y "+rect.y+"  Right "+rect.getRight()+"  Top "+rect.getTop());
			return false;
		}
		return true;
	}
	public float centerX() {
//		Gdx.app.log("impact", ""+x+"   "+width+"   "+(x+width)/2);
		return (x+width/2);
	}
	public float centerY() {
//		Gdx.app.log("impact", ""+x+"   "+width+"   "+(x+width)/2);
		return (y+height/2);
	}
	
	public float getRight(){
		return (x+width);
	}
	public float getTop(){
		return (y+height);
	}
	
	
	
	
	
}
