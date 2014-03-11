package g.tool;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class OzPicture {
	private Sprite sprite;
	private float basisHeight;
	private float basisWidth;
	
	
	
	
	

	public OzPicture( float basisWidth , float basisHeight,TextureRegion region) {
		super();
		this.sprite = new Sprite(region);
		//针对手机屏幕来设定图片的大小
		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
	}
	public OzPicture(float basisWidth , float basisHeight,TextureRegion region,boolean isBackGround) {
		super();
		this.sprite = new Sprite(region);
		//针对手机屏幕来设定图片的大小
		if(isBackGround){
			//背景图片特殊设定
			this.sprite.setSize( basisWidth * P.getBgRatioX() , basisHeight * P.getBgRatioY() );
		}
		else{
			this.sprite.setSize( basisWidth * P.getRatioX() , basisHeight * P.getRatioY() );
		}
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
	}
	public OzPicture() {
		super();
	}
	
	
	public Sprite getSprite() {
		return sprite;
	}
	public float getHeight() {
		return basisHeight;
	}
	public float getWidth() {
		return basisWidth;
	}
	
	
	
}
