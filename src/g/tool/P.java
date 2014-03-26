package g.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class P {
	//Button
	public static OzPicture Game_btnLeft;
	public static OzPicture Game_btnRight;
	public static OzPicture Game_btnLeftPress;
	public static OzPicture Game_btnRightPress;
	public static OzPicture Game_btnJump;
	public static OzPicture Game_btnJumpPress;
	//gameSource
	public static OzPicture backGround;
	public static OzPicture land;
	public static OzPicture player;
	public static OzPicture playerLeft;
	public static OzPicture playerRight;
	
	private static void loadTexture() {
		setAtlas("Image/basis.atlas");
		
		backGround          = new OzPicture(1280, 720, mS("backGround"), true);
		Game_btnLeft        = new OzPicture(208, 125, mS("btnLeft"));
		Game_btnRight       = new OzPicture(true, false,208, 125, mS("btnLeft"));
		Game_btnLeftPress   = new OzPicture(208, 125, mS("btnLeftPress"));
		Game_btnRightPress  = new OzPicture(true, false, 208, 125, mS("btnLeftPress"));
		Game_btnJump        = new OzPicture(150, 150, mS("btnJump"));
		Game_btnJumpPress   = new OzPicture(150, 150, mS("btnJumpPress"));
		land                = new OzPicture(400, 200, mS("land"));
		player              = new OzPicture(60, 60, mS("player"));
		playerLeft          = new OzPicture(60, 60, mS("playerLeft"));
		playerRight         = new OzPicture(true, false,60,60, mS("playerLeft"));
	}
	
	
	
	private static void LogShow(OzPicture a,String s){
		Gdx.app.log("ratio","  "+s+"ͼƬ��: "+a.getSprite().getWidth()+" "+s+"ͼƬ��: "+a.getSprite().getHeight());
	}
	public static void begin(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
	}
	
	
	
	//��ͼ������
	
	public static void draw(float x,float y,OzPicture picture){
		picture.setDefault();
		//�˷��������ڻ����˱���ͼƬ֮���ͼƬ
		picture.getSprite().setPosition(x*P.ratioX, y*P.ratioY);
		picture.getSprite().draw(batch);
	}
	public static void draw(OzPoint point,OzPicture picture){
		picture.setDefault();
		//�˷��������ڻ����˱���ͼƬ֮���ͼƬ
		picture.getSprite().setPosition(point.x*P.ratioX, point.y*P.ratioY);
		picture.getSprite().draw(batch);
	}
	public static void drawBg(float x,float y,OzPicture picture){
		picture.setDefault();
		//�˷���ֻ������������ͼ��
		picture.getSprite().setPosition(x*P.bgRatioX, y*P.bgRatioY);
		picture.getSprite().draw(batch);
	}
	
	//��ͼ������
	
	
	public static void end(){
		batch.end();
	}
	
	
	
	private static float screenW;
	private static float screenH;
	private static float ratioX;
	private static float ratioY;
	private static float bgRatioX;
	private static float bgRatioY;
	private static SpriteBatch batch;
	private static TextureAtlas atlas;
	
	public static void init(float screenWidth,float screenHeight){
		
		batch = new SpriteBatch();
		P.screenW = screenWidth;
		P.screenH = screenHeight;
		Gdx.app.log("ratio", " ��Ļ�ֱ���Ϊ: "+P.screenW +" * "+P.screenH);
		//��Ļ��Ա�����ʼ��
		ratioInit();
		loadTexture();
		
	}
	private static Sprite mS(String pictureName){
		return atlas.createSprite(pictureName);
	}
	private static void ratioInit(){
		
		/**��1280*720�ֱ�����Ϊ��׼��Ļ*/
		
		float offsetHeight = P.screenW/1280f * 720f; //offsetHeight:��16:9��������֮��Ǳ���ͼ�ĸ߶�
		Gdx.app.log("ratio", " ���������ŵĸ߶�Ϊ: "+offsetHeight);
		if(offsetHeight < 720f){
			//�Ǳ���ͼ����
			ratioX = P.screenW/1280f;
			ratioY = offsetHeight/720f;
			//����ͼ������Ļ
			float bgOffsetWidth = P.screenH/720f * 1280f;
			bgRatioX = bgOffsetWidth/1280f;
			bgRatioY = P.screenH/720f;
			Gdx.app.log("ratio", "�������������ö�̬����!");
		}
		else{
			//�������������������ǿ�Ƹ���ȫ��Ļ����
			ratioX = P.screenW/1280f;
			ratioY = P.screenH/720f;
			bgRatioX = ratioX;
			bgRatioY = ratioY;
			Gdx.app.log("ratio", "����������������ǿ�Ʊ���!");
		}
	}
	private static void setAtlas(String path){
		atlas = new TextureAtlas(Gdx.files.internal(path));
	}
	public static void dispose(){
		batch.dispose();
	}
	public static float getScreenW() {
		return screenW;
	}
	public static float getScreenH() {
		return screenH;
	}
	public static float getRatioX() {
		return ratioX;
	}
	public static float getRatioY() {
		return ratioY;
	}
	public static float getBgRatioX() {
		return bgRatioX;
	}
	public static float getBgRatioY() {
		return bgRatioY;
	}
	
}
