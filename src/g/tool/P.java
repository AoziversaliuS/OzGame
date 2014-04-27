package g.tool;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class P {
	public static float getForceRatioX() {
		return forceRatioX;
	}



	public static float getForceRatioY() {
		return forceRatioY;
	}



	
	
	
	
	
	public static Sprite black;
	
	private static void loadTexture() {
		//制作黑色图片↓
		makeBlackPicture();
		//制作黑色图片↑
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
	
	//强制比例
	public static void drawFr(OzPoint point,OzPicture picture){
		picture.getSprite().setPosition(point.x*P.forceRatioX, point.y*P.forceRatioY);
		picture.getSprite().draw(batch);
	}
	public static void drawFr(float x,float y,OzPicture picture){
		picture.getSprite().setPosition(x*P.forceRatioX, y*P.forceRatioY);
		picture.getSprite().draw(batch);
	}
	
	

	//绘图方法↑
	
	//设置屏幕亮度
	public static void setlight(float light){
		black.setColor(0, 0, 0, light);
		black.draw(batch);
	}
	
	public static void end(){
		batch.end();
	}
	
	
	public static final int RATIO = 1,BG_RATIO = 2, FORCE_RATIO = 3;
	public static final float BASIC_SCREEN_WIDTH = 1280f;
	public static final float BASIC_SCREEN_HEIGHT = 720f;
	
	private static float screenW;
	private static float screenH;
	private static float ratioX;
	private static float ratioY;
	private static float bgRatioX;
	private static float bgRatioY;
	
	private static float forceRatioX; //强制比例
	private static float forceRatioY;
	
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
		
		forceRatioX = P.screenW/BASIC_SCREEN_WIDTH;
		forceRatioY = P.screenH/BASIC_SCREEN_HEIGHT;
		
	}
	private static void makeBlackPicture(){
		Pixmap p = new Pixmap((int)P.screenW, (int)P.screenH,  Format.RGBA8888);
		p.setColor(Color.BLACK);
		p.fill();
		Texture texture = new Texture(p);
		black = new Sprite(texture);
		texture.dispose();
		p.dispose();
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
	public static SpriteBatch getBatch() {
		return batch;
	}

	
}
