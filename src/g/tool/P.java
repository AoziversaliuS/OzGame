package g.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class P {
	
	
	public static OzPicture backGround;
	public static OzPicture Game_btnLeft;
	public static OzPicture Game_btnRight;
	public static OzPicture Game_btnLeftPress;
	public static OzPicture Game_btnRightPress;
	
	private static void loadTexture() {
		setAtlas("Image/basis.atlas");
		
		backGround = new OzPicture(1280, 720, get("backGround"), true);
		Game_btnLeft = new OzPicture(208, 125, get("btnLeft"));
		Game_btnRight = new OzPicture(true, false,208, 125, get("btnLeft"));
		Game_btnLeftPress = new OzPicture(208, 125, get("btnLeftPress"));
		Game_btnRightPress = new OzPicture(true, false, 208, 125, get("btnLeftPress"));
	}
	
	
	
	private static void LogShow(OzPicture a,String s){
		Gdx.app.log("ratio","  "+s+"图片宽: "+a.getSprite().getWidth()+" "+s+"图片高: "+a.getSprite().getHeight());
	}
	public static void begin(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
	}
	public static void draw(float x,float y,Sprite sprite){
		//此方法适用于画除了背景图片之外的图片
		sprite.setPosition(x*P.ratioX, y*P.ratioY);
		sprite.draw(batch);
	}
	public static void drawBg(float x,float y,Sprite sprite){
		//此方法只能用来画背景图！
		sprite.setPosition(x*P.bgRatioX, y*P.bgRatioY);
		sprite.draw(batch);
	}
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
		Gdx.app.log("ratio", " 屏幕分辨率为: "+P.screenW +" * "+P.screenH);
		//屏幕相对比例初始化
		ratioInit();
		loadTexture();
		
	}
	private static TextureRegion get(String regionName){
		return atlas.findRegion(regionName);
	}
	private static void ratioInit(){
		
		/**以1280*720分辨率作为基准屏幕*/
		
		float offsetHeight = P.screenW/1280f * 720f; //offsetHeight:按16:9比例缩放之后非背景图的高度
		Gdx.app.log("ratio", " 按比例缩放的高度为: "+offsetHeight);
		if(offsetHeight < 720f){
			//非背景图比例
			ratioX = P.screenW/1280f;
			ratioY = offsetHeight/720f;
			//背景图覆盖屏幕
			float bgOffsetWidth = P.screenH/720f * 1280f;
			bgRatioX = bgOffsetWidth/1280f;
			bgRatioY = P.screenH/720f;
			Gdx.app.log("ratio", "符合条件，采用动态比例!");
		}
		else{
			//不符合上述条件则采用强制覆盖全屏幕比例
			ratioX = P.screenW/1280f;
			ratioY = P.screenH/720f;
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
