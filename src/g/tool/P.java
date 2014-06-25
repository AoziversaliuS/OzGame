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
		Gdx.app.log("ratio", " ��Ļ�ֱ���Ϊ: "+P.screenW +" * "+P.screenH);
		//��Ļ��Ա�����ʼ��
		ratioInit();
		makeBlackPicture();
		
	}
	
	//������Ļ����
//	public static void setlight(float light){
//		black.setColor(0, 0, 0, light);
//		black.draw(batch);
//	}
	/**
	 * ʹ�ò���ֵ���趨��Ļ���ȣ�0.00f<=blackNum<=1f
	 * */
	public static void useDarkness(float blackNum){
		black.setColor(0, 0, 0, blackNum);
		black.draw(batch);
	}
	/**
	 * ͨ��P.setDarkness(float blackNum)���õ�ֵ��������Ļ����
	 * */
	public static void useDarkness(){
		black.setColor(0, 0, 0, blackNum);
		black.draw(batch);
	}
	/**
	 *  ����P.useDarkness()���õ�����Ļ����ֵ��С
	 * */
	public static void increaseDarkness(){
		blackNum = blackNum + D_BLACK_NUM;
	}
	/**
	 * ����P.useDarkness()���õ�����Ļ����ֵ��С
	 * */
	public static void decreaseDarkness(){
		blackNum = blackNum - D_BLACK_NUM;
	}
	/**
	 * ����P.useDarkness()���õ�����Ļ����ֵ��С
	 * */
	public static void setDarkness(float blackNum) {
		P.blackNum = blackNum;
	}
	/**
	 * ��ȡ��Ļ����ֵ
	 * */
	public static float getBlackNum() {
		return blackNum;
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
	/**����ͼƬ����ı�������*/
	public static void draw(OzPoint point,OzPicture picture){
//		draw(p.x, p.y, picture);
		draw(
				point.x, 
				point.y, 
				picture, picture.getRatioType(), false, 0, false, 0, 0);
	}
	/**����ͼƬ����ı������� */
	public static void draw(float x,float y,OzPicture picture){
//		draw(x, y, picture,picture.getRatioType());
		draw(
				x, 
				y, 
				picture, picture.getRatioType(), false, 0, false, 0, 0);
	}
	/** ��ͼʱ�������ñ���*/
	public static void draw(OzPoint point,OzPicture picture,int ratioType){
		draw(
				point.x, 
				point.y, 
				picture,ratioType, false, 0, false, 0, 0);
	}
	/** ��ͼʱ�������ñ���*/
	public static void draw(float x,float y,OzPicture picture,int ratioType){
		draw(
				x, 
				y, 
				picture,ratioType, false, 0, false, 0, 0);
	}
//	public static void draw(float x,float y,OzPicture picture,int ratioType){
//		if( ratioType == NO_RATIO ){
//			//ֱ�ӻ�
//			drawAtPosition(x, y, picture);
//		}
//		else if( ratioType==AUTO_RATIO ){
//			//�Զ�����
//			drawAtPosition(x*P.autoRatioX,   y*P.autoRatioY, picture);
//		}
//		else if( ratioType==FORCE_RATIO ){
//			//ǿ�Ʊ���
//			drawAtPosition(x*P.forceRatioX,  y*P.forceRatioY, picture);
//		}
//		else if( ratioType==BG_RATIO ){
//			//��������
//			drawAtPosition(x*P.bgRatioX,  y*P.bgRatioY, picture);
//		}
//	}
	/** ����ͼƬ,ʹ��picture�Դ��ı�������*/
	public static void draw(float scaleXY,OzPoint point,OzPicture picture){
//		draw(scaleXY, scaleXY, point, picture,picture.getRatioType());
		draw(
				point.x, 
				point.y, 
				picture, picture.getRatioType(), false, 0, true, scaleXY, scaleXY);
	}
	/**����ͼƬ,ʹ��picture�Դ��ı������� */
	public static void draw(float scaleX,float scaleY,OzPoint point,OzPicture picture){
//		draw(scaleX, scaleY, point, picture, picture.getRatioType());
		draw(
				point.x, 
				point.y, 
				picture, picture.getRatioType(), false, 0, true, scaleX, scaleY);
	}
	/**����ͼƬ��ʹ���Զ���ı�������*/
	public static void draw(float scaleX,float scaleY,OzPoint point,OzPicture picture,int ratioType){
		draw(point.x, point.y, picture, ratioType, false, 0, true, scaleX, scaleY);
	}
	/** ��תͼƬ,ʹ��picture�Դ��ı������� */
	public static void draw(float degrees,float pX,float pY,OzPicture picture){
		draw(pX, pY, picture, picture.getRatioType(), true, degrees, false, 0, 0);
	}
	/** ��תͼƬ��ʹ���Զ���ı�������*/
	public static void draw(float degrees,float pX,float pY,OzPicture picture,int ratioType){
		draw(pX, pY, picture,ratioType, true, degrees, false, 0, 0);
	}
	/** ��ת������ͼƬ,ʹ��picture�Դ��ı������� */
	public static void draw(float scaleX,float scaleY,float degrees,float pX,float pY,OzPicture picture){
		draw(pX, pY, picture, picture.getRatioType(), true, degrees, true,scaleX,scaleY);
	}
	/** ��ת������ͼƬ,ʹ��picture�Դ��ı������� */
	public static void draw(float scaleXY,float degrees,float pX,float pY,OzPicture picture){
		draw(pX, pY, picture, picture.getRatioType(), true, degrees, true,scaleXY,scaleXY);
	}
	/** ��ת������ͼƬ,ʹ���Զ���ı������� */
	public static void draw(float scaleX,float scaleY,float degrees,float pX,float pY,OzPicture picture,int ratioType){
		draw(pX, pY, picture,ratioType, true, degrees, true,scaleX,scaleY);
	}
	/** ��ת������ͼƬ,ʹ���Զ���ı������� */
	public static void draw(float scaleXY,float degrees,float pX,float pY,OzPicture picture,int ratioType){
		draw(pX, pY, picture,ratioType, true, degrees, true,scaleXY,scaleXY);
	}
//	/**
//	 * ����ͼƬ��ʹ���Զ���ı�������
//	 * */
//	public static void draw(float scaleX,float scaleY,OzPoint point,OzPicture picture,int ratioType){
//		picture.getSprite().setScale(scaleX, scaleY);
//		if( ratioType == NO_RATIO ){
//			//ֱ�ӻ�
//			drawAtPosition(point.x, point.y, picture);
//		}
//		else if( ratioType==AUTO_RATIO ){
//			//�Զ�����
//			drawAtPosition(point.x*P.autoRatioX,point.y*P.autoRatioY, picture);
//		}
//		else if( ratioType==FORCE_RATIO ){
//			//ǿ�Ʊ���
//			drawAtPosition(point.x*P.forceRatioX,  point.y*P.forceRatioY, picture);
//		}
//		else if( ratioType==BG_RATIO ){
//			//��������
//			drawAtPosition(point.x*P.bgRatioX,  point.y*P.bgRatioY, picture);
//		}
//	}
	
	private static void draw(float pX,float pY,OzPicture picture,int ratioType,boolean rotation,float degrees,boolean scale,float scaleX,float scaleY){
	
		picture.setDefault();
		
		if(scale) picture.getSprite().setScale(scaleX, scaleY);
		
		if(rotation) picture.getSprite().setRotation(degrees);
	
		if( ratioType == NO_RATIO ){
			//ֱ�ӻ�
			drawAtPosition(pX,pY, picture);
		}
		else if( ratioType==AUTO_RATIO ){
			//�Զ�����
			drawAtPosition(pX*P.autoRatioX,pY*P.autoRatioY, picture);
		}
		else if( ratioType==FORCE_RATIO ){
			//ǿ�Ʊ���
			drawAtPosition(pX*P.forceRatioX,pY*P.forceRatioY, picture);
		}
		else if( ratioType==BG_RATIO ){
			//��������
			drawAtPosition(pX*P.bgRatioX,pY*P.bgRatioY, picture);
		}
	}
	
	
	private static void drawAtPosition(float positionX,float positionY,OzPicture picture){
		picture.getSprite().setPosition(positionX,positionY);//�Ѿ������ñ���֮���XYֵ
		float left = picture.getSprite().getX();
		float right = picture.getSprite().getX()+picture.getSprite().getWidth();
		float top = picture.getSprite().getY()+picture.getSprite().getHeight();
		float bottom = picture.getSprite().getY();
		
		
		if( right<0 || left>P.screenW || top<0 || bottom>P.screenH ){
			//����������Ļ��Χ�Ķ���
		}
		else{
			picture.getSprite().draw(batch);
		}
	}
	
	

	//��ͼ������
	

	
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
	
	private static float forceRatioX; //ǿ�Ʊ���
	private static float forceRatioY;
	
	private static SpriteBatch batch;



	private static void ratioInit(){
		
		/**��1280*720�ֱ�����Ϊ��׼��Ļ*/
		
		float offsetHeight = P.screenW/BASIC_SCREEN_WIDTH * BASIC_SCREEN_HEIGHT; //offsetHeight:��16:9��������֮��Ǳ���ͼ�ĸ߶�
		Gdx.app.log("ratio", " ���������ŵĸ߶�Ϊ: "+offsetHeight);
		if(offsetHeight < BASIC_SCREEN_HEIGHT){
			//�Ǳ���ͼ����
			autoRatioX = P.screenW/BASIC_SCREEN_WIDTH;
			autoRatioY = offsetHeight/BASIC_SCREEN_HEIGHT;
			//����ͼ������Ļ
			float bgOffsetWidth = P.screenH/BASIC_SCREEN_HEIGHT * BASIC_SCREEN_WIDTH;
			bgRatioX = bgOffsetWidth/BASIC_SCREEN_WIDTH;
			bgRatioY = P.screenH/BASIC_SCREEN_HEIGHT;
			Gdx.app.log("ratio", "�������������ö�̬����!");
		}
		else{
			//�������������������ǿ�Ƹ���ȫ��Ļ����
			autoRatioX = P.screenW/BASIC_SCREEN_WIDTH;
			autoRatioY = P.screenH/BASIC_SCREEN_HEIGHT;
			bgRatioX = autoRatioX;
			bgRatioY = autoRatioY;
			Gdx.app.log("ratio", "����������������ǿ�Ʊ���!");
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
