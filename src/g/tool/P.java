package g.tool;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class P {
	

	public static Sprite black;
	public static float blackNum = 0f;
	public static final float D_BLACK_NUM = 0.05f;
	public static final float  MAX_BLACK_NUM = 0.98f,MIN_BLACK_NUM = 0.01f;
	
	public static void init(float screenWidth,float screenHeight){
		
		batch = new SpriteBatch();
		P.screenW = screenWidth;
		P.screenH = screenHeight;
		Gdx.app.log("ratio", " 屏幕分辨率为: "+P.screenW +" * "+P.screenH);
		//屏幕相对比例初始化
		ratioInit();
		makeBlackPicture();
		
	}
	
	//设置屏幕亮度
//	public static void setlight(float light){
//		black.setColor(0, 0, 0, light);
//		black.draw(batch);
//	}
	/**
	 * 使用参数值来设定屏幕暗度，0.00f<=blackNum<=1f
	 * */
	public static void useDarkness(float blackNum){
		black.setColor(0, 0, 0, blackNum);
		black.draw(batch);
	}
	/**
	 * 通过P.setDarkness(float blackNum)设置的值来调整屏幕暗度
	 * */
	public static void useDarkness(){
		black.setColor(0, 0, 0, blackNum);
		black.draw(batch);
	}
	/**
	 *  增加P.useDarkness()所用到的屏幕暗度值大小
	 * */
	public static void increaseDarkness(){
		blackNum = blackNum + D_BLACK_NUM;
	}
	/**
	 * 减少P.useDarkness()所用到的屏幕暗度值大小
	 * */
	public static void decreaseDarkness(){
		blackNum = blackNum - D_BLACK_NUM;
	}
	/**
	 * 设置P.useDarkness()所用到的屏幕暗度值大小
	 * */
	public static void setDarkness(float blackNum) {
		P.blackNum = blackNum;
	}
	/**
	 * 获取屏幕暗度值
	 * */
	public static float getBlackNum() {
		return blackNum;
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
	/**根据图片本身的比例来画*/
	public static void draw(OzPoint point,OzPicture picture){
//		draw(p.x, p.y, picture);
		draw(
				point.x, 
				point.y, 
				picture, picture.getRatioType(), false, 0, false, 0, 0);
	}
	/**根据图片本身的比例来画 */
	public static void draw(float x,float y,OzPicture picture){
//		draw(x, y, picture,picture.getRatioType());
		draw(
				x, 
				y, 
				picture, picture.getRatioType(), false, 0, false, 0, 0);
	}
	/** 画图时自行设置比例*/
	public static void draw(OzPoint point,OzPicture picture,int ratioType){
		draw(
				point.x, 
				point.y, 
				picture,ratioType, false, 0, false, 0, 0);
	}
	/** 画图时自行设置比例*/
	public static void draw(float x,float y,OzPicture picture,int ratioType){
		draw(
				x, 
				y, 
				picture,ratioType, false, 0, false, 0, 0);
	}
//	public static void draw(float x,float y,OzPicture picture,int ratioType){
//		if( ratioType == NO_RATIO ){
//			//直接画
//			drawAtPosition(x, y, picture);
//		}
//		else if( ratioType==AUTO_RATIO ){
//			//自动比例
//			drawAtPosition(x*P.autoRatioX,   y*P.autoRatioY, picture);
//		}
//		else if( ratioType==FORCE_RATIO ){
//			//强制比例
//			drawAtPosition(x*P.forceRatioX,  y*P.forceRatioY, picture);
//		}
//		else if( ratioType==BG_RATIO ){
//			//背景比例
//			drawAtPosition(x*P.bgRatioX,  y*P.bgRatioY, picture);
//		}
//	}
	/** 缩放图片,使用picture自带的比例来画*/
	public static void draw(float scaleXY,OzPoint point,OzPicture picture){
//		draw(scaleXY, scaleXY, point, picture,picture.getRatioType());
		draw(
				point.x, 
				point.y, 
				picture, picture.getRatioType(), false, 0, true, scaleXY, scaleXY);
	}
	/**缩放图片,使用picture自带的比例来画 */
	public static void draw(float scaleX,float scaleY,OzPoint point,OzPicture picture){
//		draw(scaleX, scaleY, point, picture, picture.getRatioType());
		draw(
				point.x, 
				point.y, 
				picture, picture.getRatioType(), false, 0, true, scaleX, scaleY);
	}
	/**缩放图片，使用自定义的比例来画*/
	public static void draw(float scaleX,float scaleY,OzPoint point,OzPicture picture,int ratioType){
		draw(point.x, point.y, picture, ratioType, false, 0, true, scaleX, scaleY);
	}
	/** 旋转图片,使用picture自带的比例来画 */
	public static void draw(float degrees,float pX,float pY,OzPicture picture){
		draw(pX, pY, picture, picture.getRatioType(), true, degrees, false, 0, 0);
	}
	/** 旋转图片，使用自定义的比例来画*/
	public static void draw(float degrees,float pX,float pY,OzPicture picture,int ratioType){
		draw(pX, pY, picture,ratioType, true, degrees, false, 0, 0);
	}
	/** 旋转并缩放图片,使用picture自带的比例来画 */
	public static void draw(float scaleX,float scaleY,float degrees,float pX,float pY,OzPicture picture){
		draw(pX, pY, picture, picture.getRatioType(), true, degrees, true,scaleX,scaleY);
	}
	/** 旋转并缩放图片,使用picture自带的比例来画 */
	public static void draw(float scaleXY,float degrees,float pX,float pY,OzPicture picture){
		draw(pX, pY, picture, picture.getRatioType(), true, degrees, true,scaleXY,scaleXY);
	}
	/** 旋转并缩放图片,使用自定义的比例来画 */
	public static void draw(float scaleX,float scaleY,float degrees,float pX,float pY,OzPicture picture,int ratioType){
		draw(pX, pY, picture,ratioType, true, degrees, true,scaleX,scaleY);
	}
	/** 旋转并缩放图片,使用自定义的比例来画 */
	public static void draw(float scaleXY,float degrees,float pX,float pY,OzPicture picture,int ratioType){
		draw(pX, pY, picture,ratioType, true, degrees, true,scaleXY,scaleXY);
	}
//	/**
//	 * 缩放图片，使用自定义的比例来画
//	 * */
//	public static void draw(float scaleX,float scaleY,OzPoint point,OzPicture picture,int ratioType){
//		picture.getSprite().setScale(scaleX, scaleY);
//		if( ratioType == NO_RATIO ){
//			//直接画
//			drawAtPosition(point.x, point.y, picture);
//		}
//		else if( ratioType==AUTO_RATIO ){
//			//自动比例
//			drawAtPosition(point.x*P.autoRatioX,point.y*P.autoRatioY, picture);
//		}
//		else if( ratioType==FORCE_RATIO ){
//			//强制比例
//			drawAtPosition(point.x*P.forceRatioX,  point.y*P.forceRatioY, picture);
//		}
//		else if( ratioType==BG_RATIO ){
//			//背景比例
//			drawAtPosition(point.x*P.bgRatioX,  point.y*P.bgRatioY, picture);
//		}
//	}
	
	private static void draw(float pX,float pY,OzPicture picture,int ratioType,boolean rotation,float degrees,boolean scale,float scaleX,float scaleY){
	
		picture.setDefault();
		
		if(scale) picture.getSprite().setScale(scaleX, scaleY);
		
		if(rotation) picture.getSprite().setRotation(degrees);
	
		if( ratioType == NO_RATIO ){
			//直接画
			drawAtPosition(pX,pY, picture);
		}
		else if( ratioType==AUTO_RATIO ){
			//自动比例
			drawAtPosition(pX*P.autoRatioX,pY*P.autoRatioY, picture);
		}
		else if( ratioType==FORCE_RATIO ){
			//强制比例
			drawAtPosition(pX*P.forceRatioX,pY*P.forceRatioY, picture);
		}
		else if( ratioType==BG_RATIO ){
			//背景比例
			drawAtPosition(pX*P.bgRatioX,pY*P.bgRatioY, picture);
		}
	}
	
	
	private static void drawAtPosition(float positionX,float positionY,OzPicture picture){
		picture.getSprite().setPosition(positionX,positionY);//已经调整好比例之后的XY值
		float left = picture.getSprite().getX();
		float right = picture.getSprite().getX()+picture.getSprite().getWidth();
		float top = picture.getSprite().getY()+picture.getSprite().getHeight();
		float bottom = picture.getSprite().getY();
		
		
		if( right<0 || left>P.screenW || top<0 || bottom>P.screenH ){
			//不画超出屏幕范围的东西
		}
		else{
			picture.getSprite().draw(batch);
		}
	}
	
	

	//绘图方法↑
	

	
	public static void end(){
		batch.end();
	}
	
	
	public static final int AUTO_RATIO = 1,BG_RATIO = 2, FORCE_RATIO = 3,NO_RATIO=4;
	public static final float BASIC_SCREEN_WIDTH = 1280f;
	public static final float BASIC_SCREEN_HEIGHT = 720f;
	
	private static float screenW;
	private static float screenH;
	private static float autoRatioX;
	private static float autoRatioY;
	private static float bgRatioX;
	private static float bgRatioY;
	
	private static float forceRatioX; //强制比例
	private static float forceRatioY;
	
	private static SpriteBatch batch;



	private static void ratioInit(){
		
		/**以1280*720分辨率作为基准屏幕*/
		
		float offsetHeight = P.screenW/BASIC_SCREEN_WIDTH * BASIC_SCREEN_HEIGHT; //offsetHeight:按16:9比例缩放之后非背景图的高度
		Gdx.app.log("ratio", " 按比例缩放的高度为: "+offsetHeight);
		if(offsetHeight < BASIC_SCREEN_HEIGHT){
			//非背景图比例
			autoRatioX = P.screenW/BASIC_SCREEN_WIDTH;
			autoRatioY = offsetHeight/BASIC_SCREEN_HEIGHT;
			//背景图覆盖屏幕
			float bgOffsetWidth = P.screenH/BASIC_SCREEN_HEIGHT * BASIC_SCREEN_WIDTH;
			bgRatioX = bgOffsetWidth/BASIC_SCREEN_WIDTH;
			bgRatioY = P.screenH/BASIC_SCREEN_HEIGHT;
			Gdx.app.log("ratio", "符合条件，采用动态比例!");
		}
		else{
			//不符合上述条件则采用强制覆盖全屏幕比例
			autoRatioX = P.screenW/BASIC_SCREEN_WIDTH;
			autoRatioY = P.screenH/BASIC_SCREEN_HEIGHT;
			bgRatioX = autoRatioX;
			bgRatioY = autoRatioY;
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
//	private static void setAtlas(String path){
//		atlas = new TextureAtlas(Gdx.files.internal(path));
//	}
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
		return autoRatioX;
	}
	public static float getRatioY() {
		return autoRatioY;
	}
	public static float getBgRatioX() {
		return bgRatioX;
	}
	public static float getBgRatioY() {
		return bgRatioY;
	}

	public static float getForceRatioX() {
		return forceRatioX;
	}
	public static float getForceRatioY() {
		return forceRatioY;
	}
//	private static SpriteBatch getBatch() {
//		return batch;
//	}
	
}
