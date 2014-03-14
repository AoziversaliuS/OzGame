package g.tool;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OzPicture {
	private Sprite sprite;
	private float basisHeight;
	private float basisWidth;
	
	
	
	
	

	public OzPicture( float basisWidth , float basisHeight,Sprite sprite) {
		super();
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//针对手机屏幕来设定图片的大小
		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
	}
	public OzPicture( boolean planeFlip, boolean verticalFlip,float basisWidth , float basisHeight,Sprite sprite) {
		super();
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite.flip(planeFlip, verticalFlip);
		//针对手机屏幕来设定图片的大小
		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
	}
	public OzPicture(float basisWidth , float basisHeight,Sprite sprite,boolean isBackGround) {
		super();
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
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
