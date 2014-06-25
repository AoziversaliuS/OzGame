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

	
	/**
	 * ��ͼƬ��ԭ
	 * */
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
		//������ת�ͷŴ��õ����ĵ㣬����ΪͼƬ�����ĵ�
		this.sprite.setOrigin(this.sprite.getWidth()/2, this.sprite.getHeight()/2);
		//��ת�Ƕ�Ϊ0
		this.sprite.setRotation(0);
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
