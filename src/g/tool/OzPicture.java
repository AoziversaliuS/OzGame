package g.tool;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OzPicture {
	private Sprite sprite;
	private float basisHeight;
	private float basisWidth;
	private boolean isBackGround = false;
	private int ratioType=P.AUTO_RATIO;
	
	
	
	
	public int getRatioType() {
		return ratioType;
	}
	//��Sprite���췽��
	public OzPicture( float basisWidth , float basisHeight){
		init(basisWidth, basisHeight, null);
	}
	public OzPicture( float basisWidth , float basisHeight,int TYPE){
		init(basisWidth, basisHeight, null);
		if( TYPE == P.BG_RATIO ){
			ratioType = P.BG_RATIO;
			this.isBackGround = true;
		}
	}


	//��Sprite���췽��
	public OzPicture( float basisWidth , float basisHeight,Sprite sprite) {
		super();
		//�������������
		init(basisWidth, basisHeight, sprite);
		//����ֻ���Ļ���趨ͼƬ�Ĵ�С
		if( this.sprite!=null ){
			this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
		}
	}
	public OzPicture( boolean planeFlip, boolean verticalFlip,float basisWidth , float basisHeight,Sprite sprite) {
		super();
		//�������������
		init(basisWidth, basisHeight, sprite);
		if( this.sprite!=null ){
			sprite.flip(planeFlip, verticalFlip);
			//����ֻ���Ļ���趨ͼƬ�Ĵ�С
			this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
		}

	}
	public OzPicture(float basisWidth , float basisHeight,Sprite sprite,int TYPE) {
		super();
		//�������������
		if( TYPE == P.BG_RATIO ){
			this.isBackGround = true;
		}
		ratioType = TYPE;
		
		init(basisWidth, basisHeight, sprite);
		//����ֻ���Ļ���趨ͼƬ�Ĵ�С
		if( this.sprite!=null ){
			if( TYPE == P.BG_RATIO ){
				//����ͼƬ�����趨
				this.sprite.setSize( basisWidth * P.getBgRatioX() , basisHeight * P.getBgRatioY() );
			}
			else if( TYPE == P.AUTO_RATIO ){
				this.sprite.setSize( basisWidth * P.getRatioX() , basisHeight * P.getRatioY() );
			}
			else if( TYPE == P.FORCE_RATIO ){
				this.sprite.setSize( basisWidth * P.getForceRatioX() , basisHeight * P.getForceRatioY() );
			}
		}
	}
	private void init(float basisWidth , float basisHeight,Sprite sprite){
		this.basisHeight = basisHeight;
		this.basisWidth = basisWidth;
		this.sprite = sprite;
		if( this.sprite!=null ){
			sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
	}
	public OzPicture() {
		super();
	}
	
	
	public void setDefault(){
		//���ݱ���������ͼƬ��С
		if(isBackGround){
			this.sprite.setSize( this.basisWidth * P.getBgRatioX() , this.basisHeight * P.getBgRatioY() );
		}
		else if( ratioType==P.AUTO_RATIO ){
			this.sprite.setSize( this.basisWidth * P.getRatioX() , this.basisHeight * P.getRatioY() );
		}
		else if( ratioType==P.FORCE_RATIO ){
			this.sprite.setSize( this.basisWidth * P.getForceRatioX(), this.basisHeight * P.getForceRatioY() );
		}
		this.sprite.setScale(1f);
		this.sprite.setColor(Color.WHITE);
	}
	
	
	public Sprite getSprite() {
		return sprite;
	}
	public float getOriginHeight(){
		return sprite.getHeight();
	}
	public float getOriginWidth(){
		return sprite.getWidth();
	}
	public float getHeight() {
		return basisHeight;
	}
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
		
		return realWidth;
	}
	
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
	}
	public void setSprite(boolean planeFlip, boolean verticalFlip,Sprite sprite) {
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite.flip(planeFlip, verticalFlip);
		this.sprite.setSize(  basisWidth*P.getRatioX() , basisHeight*P.getRatioY() );
	}
	public void setSprite(boolean planeFlip, boolean verticalFlip,Sprite sprite,int TYPE) {
		this.sprite = sprite;
		sprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprite.flip(planeFlip, verticalFlip);
		ratioType = TYPE;
		if( TYPE == P.BG_RATIO ){
			//����ͼƬ�����趨
			this.sprite.setSize( basisWidth * P.getBgRatioX() , basisHeight * P.getBgRatioY() );
		}
		else if( TYPE == P.AUTO_RATIO ){
			this.sprite.setSize( basisWidth * P.getRatioX() , basisHeight * P.getRatioY() );
		}
		else if( TYPE == P.FORCE_RATIO ){
			this.sprite.setSize( basisWidth * P.getForceRatioX() , basisHeight * P.getForceRatioY() );
		}
	}
	
	
	
	
	
}
