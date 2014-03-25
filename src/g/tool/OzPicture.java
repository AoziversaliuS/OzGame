package g.tool;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OzPicture {
	private Sprite sprite;
	private float basisHeight;
	private float basisWidth;
	private boolean isBackGround = false;
	
	
	
	
	

	public OzPicture( float basisWidth , float basisHeight,Sprite sprite) {
		super();
		//保存参数等属性
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//针对手机屏幕来设定图片的大小
		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );

	}
	public OzPicture( boolean planeFlip, boolean verticalFlip,float basisWidth , float basisHeight,Sprite sprite) {
		super();
		//保存参数等属性
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite.flip(planeFlip, verticalFlip);
		//针对手机屏幕来设定图片的大小
		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );

	}
	public OzPicture(float basisWidth , float basisHeight,Sprite sprite,boolean isBackGround) {
		super();
		//保存参数等属性
		this.isBackGround = isBackGround;
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
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
	}
	public OzPicture() {
		super();
	}
	
	
	public void setDefault(){
		if(isBackGround){
			//背景图片特殊设定
			this.sprite.setSize( this.basisWidth * P.getBgRatioX() , this.basisHeight * P.getBgRatioY() );
		}
		else{
			this.sprite.setSize( this.basisWidth * P.getRatioX() , this.basisHeight * P.getRatioY() );
		}
		this.sprite.setScale(1f);
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
