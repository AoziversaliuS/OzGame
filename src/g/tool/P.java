package g.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

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
	
	//Land↓ 
	public static OzPicture land_1;
	public static OzPicture land_2;
	public static OzPicture land_3;
	public static OzPicture land_4;
	public static OzPicture land_5;
	public static OzPicture land_6;
	public static OzPicture land_7;
	public static OzPicture land_8;
	public static OzPicture land_9;
	//Land↑
	
	public static OzPicture player;
	public static OzPicture playerLeft;
	public static OzPicture playerRight;
	public static OzPicture Thorn;
	public static OzPicture MoveLand;
	
	private static void loadTexture() {
		
		
		setAtlas("Image/player/player.atlas");
		player              = new OzPicture(45, 45, mS("player"));
		playerLeft          = new OzPicture(45, 45, mS("playerLeft"));
		playerRight         = new OzPicture(true, false,45,45, mS("playerLeft"));
		
		setAtlas("Image/button/button.atlas");
		Game_btnLeft        = new OzPicture(208, 125, mS("btnLeft"));
		Game_btnRight       = new OzPicture(true, false,208, 125, mS("btnLeft"));
		Game_btnLeftPress   = new OzPicture(208, 125, mS("btnLeftPress"));
		Game_btnRightPress  = new OzPicture(true, false, 208, 125, mS("btnLeftPress"));
		Game_btnJump        = new OzPicture(150, 150, mS("btnJump"));
		Game_btnJumpPress   = new OzPicture(150, 150, mS("btnJumpPress"));
		
		setAtlas("Image/backGround/backGround.atlas");
		backGround          = new OzPicture(1280, 720, mS("backGround"), true);
		
		setAtlas("Image/build/build.atlas");
		//Land
		land_1 = new OzPicture(50, 50, mS("land",1));
		land_2 = new OzPicture(50, 50, mS("land",2));
		land_3 = new OzPicture(50, 50, mS("land",3));
		land_4 = new OzPicture(50, 50, mS("land",4));
		land_5 = new OzPicture(50, 50, mS("land",5));
		land_6 = new OzPicture(50, 50, mS("land",6));
		land_7 = new OzPicture(50, 50, mS("land",7));
		land_8 = new OzPicture(50, 50, mS("land",8));
		land_9 = new OzPicture(50, 50, mS("land",9));
		
		//Thorn
		Thorn               = new OzPicture(50, 50, mS("thorn"));
		MoveLand            = new OzPicture(100, 50, mS("moveLand"));
		
		
	
		
	}
	
	
	
	private static void LogShow(OzPicture a,String s){
		Gdx.app.log("ratio","  "+s+"图片宽: "+a.getSprite().getWidth()+" "+s+"图片高: "+a.getSprite().getHeight());
	}
	public static void begin(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
	}
	
	
	
	//绘图方法↓
	
	public static void draw(float x,float y,OzPicture picture){
		picture.setDefault();
		//此方法适用于画除了背景图片之外的图片
		picture.getSprite().setPosition(x*P.ratioX, y*P.ratioY);
		picture.getSprite().draw(batch);
	}
	public static void draw(OzPoint point,OzPicture picture){
		picture.setDefault();
		//此方法适用于画除了背景图片之外的图片
		picture.getSprite().setPosition(point.x*P.ratioX, point.y*P.ratioY);
		picture.getSprite().draw(batch);
	}
	public static void drawScale(float scaleXY,OzPoint point,OzPicture picture){
		picture.setDefault();
		//此方法适用于画除了背景图片之外的图片
		picture.getSprite().setColor(Color.RED);
		picture.getSprite().setScale(scaleXY);
		picture.getSprite().setPosition(point.x*P.ratioX, point.y*P.ratioY);
		picture.getSprite().draw(batch);
	}
	public static void drawBg(float x,float y,OzPicture picture){
		picture.setDefault();
		//此方法只能用来画背景图！
		picture.getSprite().setPosition(x*P.bgRatioX, y*P.bgRatioY);
		picture.getSprite().draw(batch);
	}
	
	//绘图方法↑
	
	
	public static void end(){
		batch.end();
	}
	
	
	
	public static final float BASIC_SCREEN_WIDTH = 1280f;
	public static final float BASIC_SCREEN_HEIGHT = 720f;
	
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
		Gdx.app.log("ratio", " 屏幕分辨率为: "+P.screenW +" * "+P.screenH);
		//屏幕相对比例初始化
		ratioInit();
		loadTexture();
		
	}
	private static Sprite mS(String pictureName){
		return atlas.createSprite(pictureName);
	}
	private static Sprite mS(String pictureName,int index){
		return atlas.createSprite(pictureName,index);
	}
	private static void ratioInit(){
		
		/**以1280*720分辨率作为基准屏幕*/
		
		float offsetHeight = P.screenW/BASIC_SCREEN_WIDTH * BASIC_SCREEN_HEIGHT; //offsetHeight:按16:9比例缩放之后非背景图的高度
		Gdx.app.log("ratio", " 按比例缩放的高度为: "+offsetHeight);
		if(offsetHeight < BASIC_SCREEN_HEIGHT){
			//非背景图比例
			ratioX = P.screenW/BASIC_SCREEN_WIDTH;
			ratioY = offsetHeight/BASIC_SCREEN_HEIGHT;
			//背景图覆盖屏幕
			float bgOffsetWidth = P.screenH/BASIC_SCREEN_HEIGHT * BASIC_SCREEN_WIDTH;
			bgRatioX = bgOffsetWidth/BASIC_SCREEN_WIDTH;
			bgRatioY = P.screenH/BASIC_SCREEN_HEIGHT;
			Gdx.app.log("ratio", "符合条件，采用动态比例!");
		}
		else{
			//不符合上述条件则采用强制覆盖全屏幕比例
			ratioX = P.screenW/BASIC_SCREEN_WIDTH;
			ratioY = P.screenH/BASIC_SCREEN_HEIGHT;
			bgRatioX = ratioX;
			bgRatioY = ratioY;
			Gdx.app.log("ratio", "不符合条件，采用强制比例!");
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
