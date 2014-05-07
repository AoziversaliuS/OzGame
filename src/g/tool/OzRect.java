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
		//�ж�һ��[�㼯��]���Ƿ���һ������һ��������
		Set<String> keys = points.keySet();
		

		for(String key:keys){
			

			
			if(  inside(points.get(key),rationType)==true ){
//				Gdx.app.log("btn","�ж��ɹ�");
				return true;
			}else{
//				Gdx.app.log("btn","�ж�ʧ��:  point X:"+ points.get(key).x+" Y: "+points.get(key).y);
//				Gdx.app.log("btn","�ж�ʧ��:  AX:"+x+" AY: "+y+" WIDTH: "+width+" HEIGHT: "+height);
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
		//�ж����������Ƿ��ཻ
		if(this.getRight()<rect.x || this.getTop()<rect.y || y>rect.getTop() || x>rect.getRight()){
			//��rect�����                                                  ��rect�ĵײ�                                    ��rect������                                  ��rect���ұ�             �ѿ�������ϵ
			return false;
		}
		return true;
	}
	
	public boolean inside(OzPoint point,int rationType){
		float pX=-1f,pY=-1f;
		pX = get_pX_by(rationType, point);
		pY = get_pY_by(rationType, point);
		//�ж�һ�����Ƿ���һ��������
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
