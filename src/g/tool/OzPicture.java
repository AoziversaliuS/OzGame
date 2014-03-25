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
		//�������������
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//����ֻ���Ļ���趨ͼƬ�Ĵ�С
		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );

	}
	public OzPicture( boolean planeFlip, boolean verticalFlip,float basisWidth , float basisHeight,Sprite sprite) {
		super();
		//�������������
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite.flip(planeFlip, verticalFlip);
		//����ֻ���Ļ���趨ͼƬ�Ĵ�С
		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );

	}
	public OzPicture(float basisWidth , float basisHeight,Sprite sprite,boolean isBackGround) {
		super();
		//�������������
		this.isBackGround = isBackGround;
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//����ֻ���Ļ���趨ͼƬ�Ĵ�С
		if(isBackGround){
			//����ͼƬ�����趨
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
			//����ͼƬ�����趨
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
