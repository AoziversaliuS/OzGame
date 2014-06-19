package g.tool;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OzPicture {
	private Sprite sprite;
	private float basisHeight;
	private float basisWidth;
//	private boolean isBackGround = false;
	private int ratioType=P.AUTO_RATIO;
	
	
	
	
	public int getRatioType() {
		return ratioType;
	}
	//��Sprite���췽��
//	public OzPicture( float basisWidth , float basisHeight){
//		init(basisWidth, basisHeight, null);
//	}
	public OzPicture( float basisWidth , float basisHeight,int ratioType){
		init(basisWidth, basisHeight, null);
//		if( TYPE == P.BG_RATIO ){
//			ratioType = P.BG_RATIO;
//			this.isBackGround = true;
//		}
		this.ratioType = ratioType;
	}


	//��Sprite���췽��
//	public OzPicture( float basisWidth , float basisHeight,Sprite sprite) {
//		super();
//		//�������������
//		init(basisWidth, basisHeight, sprite);
//		//����ֻ���Ļ���趨ͼƬ�Ĵ�С
//		if( this.sprite!=null ){
//			this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
//		}
//	}
//	public OzPicture( boolean planeFlip, boolean verticalFlip,float basisWidth , float basisHeight,Sprite sprite) {
//		super();
//		//�������������
//		init(basisWidth, basisHeight, sprite);
//		if( this.sprite!=null ){
//			sprite.flip(planeFlip, verticalFlip);
//			//����ֻ���Ļ���趨ͼƬ�Ĵ�С
//			this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
//		}
//
//	}
	public OzPicture( boolean planeFlip, boolean verticalFlip,
			float basisWidth , float basisHeight,Sprite sprite,int ratioType){
		this(basisWidth,basisHeight,sprite,ratioType);
		sprite.flip(planeFlip,verticalFlip);
		
	}
	public OzPicture(float basisWidth , float basisHeight,Sprite sprite,int ratioType) {
		super();
		//�������������
		this.ratioType = ratioType;
		init(basisWidth, basisHeight, sprite);
		
		//����ֻ���Ļ���趨ͼƬ�Ĵ�С
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
				//����ͼƬԭ����С
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

	
	
	public void setDefault(){
		//���ݱ���������ͼƬ��С
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
		this.sprite.setScale(1f);
		this.sprite.setColor(Color.WHITE);
	}
	
	
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * ����basisHeight
	 */
	public float getHeight() {
		return basisHeight;
	}
	/**
	 * ����basisWidth
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
	
	
//	public void setSprite(Sprite sprite) {
//		this.sprite = sprite;
//		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
//	}
//	public void setSprite(boolean planeFlip, boolean verticalFlip,Sprite sprite) {
//		this.sprite = sprite;
//		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		sprite.flip(planeFlip, verticalFlip);
//		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
//	}
//	public void setSprite(boolean planeFlip, boolean verticalFlip,Sprite sprite,int ratioType) {
//		this.sprite = sprite;
//		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
//		sprite.flip(planeFlip, verticalFlip);
//		this.ratioType = ratioType;
//		if( ratioType == P.BG_RATIO ){
//			this.sprite.setSize( basisWidth * P.getBgRatioX() , basisHeight * P.getBgRatioY() );
//		}
//		else if( ratioType == P.AUTO_RATIO ){
//			this.sprite.setSize( basisWidth * P.getRatioX() , basisHeight * P.getRatioY() );
//		}
//		else if( ratioType == P.FORCE_RATIO ){
//			this.sprite.setSize( basisWidth * P.getForceRatioX() , basisHeight * P.getForceRatioY() );
//		}
//		else if( ratioType == P.NO_RATIO ){
//			//ͼƬ����ԭ���Ĵ�С
//		}
//	}
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
			//ͼƬ����ԭ���Ĵ�С
		}
	}
	
	
	
	
	
}
