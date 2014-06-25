package g.tool;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OzPicture {
	private Sprite sprite;
	private float basisHeight;
	private float basisWidth;
	private int ratioType=P.AUTO_RATIO;
	
	
	
	
	public int getRatioType() {
		return ratioType;
	}
	public OzPicture( float basisWidth , float basisHeight,int ratioType){
		init(basisWidth, basisHeight, null);
		this.ratioType = ratioType;
	}


	public OzPicture( boolean planeFlip, boolean verticalFlip,
			float basisWidth , float basisHeight,Sprite sprite,int ratioType){
		this(basisWidth,basisHeight,sprite,ratioType);
		sprite.flip(planeFlip,verticalFlip);
		
	}
	public OzPicture(float basisWidth , float basisHeight,Sprite sprite,int ratioType) {
		super();
		//保存参数等属性
		this.ratioType = ratioType;
		init(basisWidth, basisHeight, sprite);
		
		//针对手机屏幕来设定图片的大小
		if( this.sprite!=null ){
			if( ratioType == P.BG_RATIO ){
				this.sprite.setSize( basisWidth * P.getBgRatioX() , basisHeight * P.getBgRatioY() );
			}
			else if( ratioType == P.AUTO_RATIO ){
				this.sprite.setSize( basisWidth * P.getRatioX() , basisHeight * P.getRatioY() );
			}
			else if( ratioType == P.FORCE_RATIO ){
				this.sprite.setSize( basisWidth * P.getForceRatioX() , basisHeight * P.getForceRatioY() );
			}
			else if( ratioType == P.NO_RATIO ){
				//保持图片原本大小
			}
		}
	}
	public OzPicture() {
		super();
	}
	
	private void init(float basisWidth , float basisHeight,Sprite sprite){
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
		this.sprite = sprite;
		if( this.sprite!=null ){
			sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
	}

	
	/**
	 * 将图片还原
	 * */
	public void setDefault(){
		//根据比例来设置图片大小
		if( ratioType==P.BG_RATIO ){
			this.sprite.setSize( this.basisWidth * P.getBgRatioX() , this.basisHeight * P.getBgRatioY() );
		}
		else if( ratioType==P.AUTO_RATIO ){
			this.sprite.setSize( this.basisWidth * P.getRatioX() , this.basisHeight * P.getRatioY() );
		}
		else if( ratioType==P.FORCE_RATIO ){
			this.sprite.setSize( this.basisWidth * P.getForceRatioX(), this.basisHeight * P.getForceRatioY() );
		}
		else if( ratioType==P.NO_RATIO ){
			this.sprite.setSize(basisWidth,basisHeight);
		}
		//设置旋转和放大用的中心点，现设为图片的中心点
		this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
		//旋转角度为0
		this.sprite.setRotation(0);
		this.sprite.setScale(1f);
		this.sprite.setColor(Color.WHITE);
	}
	
	
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * 返回basisHeight
	 */
	public float getHeight() {
		return basisHeight;
	}
	/**
	 * 返回basisWidth
	 */
	public float getWidth() {
		return basisWidth;
	}
	
	
	public float getRealHeight(){
		float realHeight = 0;
		
		if( ratioType == P.AUTO_RATIO ){
			realHeight = basisHeight * P.getRatioY();
		}
		else if( ratioType == P.FORCE_RATIO ){
			realHeight = basisHeight * P.getForceRatioY();
		}
		else if( ratioType == P.BG_RATIO ){
			realHeight = basisHeight * P.getBgRatioY();
		}
		else if( ratioType == P.NO_RATIO ){
			realHeight = basisHeight;
		}
		
		return realHeight;
	}

	public float getRealWidth(){
		float realWidth = 0;
		
		if( ratioType == P.AUTO_RATIO ){
			realWidth = basisWidth * P.getRatioX();
		}
		else if( ratioType == P.FORCE_RATIO ){
			realWidth = basisWidth * P.getForceRatioX();
		}
		else if( ratioType == P.BG_RATIO ){
			realWidth = basisWidth * P.getBgRatioX();
		}
		else if( ratioType == P.NO_RATIO ){
			realWidth = basisWidth;
		}
		
		return realWidth;
	}
	
	
	public void setSprite(boolean planeFlip, boolean verticalFlip,Sprite sprite,int ratioType) {
		sprite.flip(planeFlip, verticalFlip);
		this.setSprite(sprite, ratioType);
	}
	public void setSprite(Sprite sprite,int ratioType){
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.ratioType = ratioType;
		if( ratioType == P.BG_RATIO ){
			this.sprite.setSize( basisWidth * P.getBgRatioX() , basisHeight * P.getBgRatioY() );
		}
		else if( ratioType == P.AUTO_RATIO ){
			this.sprite.setSize( basisWidth * P.getRatioX() , basisHeight * P.getRatioY() );
		}
		else if( ratioType == P.FORCE_RATIO ){
			this.sprite.setSize( basisWidth * P.getForceRatioX() , basisHeight * P.getForceRatioY() );
		}
		else if( ratioType == P.NO_RATIO ){
			//图片保持原来的大小
		}
	}
	
	
	
	
	
}
