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
	public static float getForceRatioX() {
		return forceRatioX;
	}



	public static float getForceRatioY() {
		return forceRatioY;
	}

	

	
	
	
	
	
	public static Sprite black;
	
	public static void init(float screenWidth,float screenHeight){
		
		batch = new SpriteBatch();
		P.screenW = screenWidth;
		P.screenH = screenHeight;
		Gdx.app.log("ratio", " ��Ļ�ֱ���Ϊ: "+P.screenW +" * "+P.screenH);
		//��Ļ��Ա�����ʼ��
		ratioInit();
		makeBlackPicture();
		
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
	public static void draw(OzPoint p,OzPicture picture,int ratioType){
		draw(p.x, p.y, picture, ratioType);
	}
	public static void draw(float x,float y,OzPicture picture,int ratioType){
		if( ratioType == NO_RATIO ){
			//ֱ�ӻ�
			picture.setDefault();
			drawAtPosition(x, y, picture);
		}
		else if( ratioType==AUTO_RATIO ){
			//�Զ�����
			picture.setDefault();
			drawAtPosition(x*P.autoRatioX,   y*P.autoRatioY, picture);
		}
		else if( ratioType==FORCE_RATIO ){
			//ǿ�Ʊ���
			picture.setDefault();
			drawAtPosition(x*P.forceRatioX,  y*P.forceRatioY, picture);
		}
		else if( ratioType==BG_RATIO ){
			//��������
			drawAtPosition(x*P.bgRatioX,  y*P.bgRatioY, picture);
		}
	}
	public static void drawScale(float scaleXY,OzPoint point,OzPicture picture){
		picture.setDefault();
		//�˷��������ڻ����˱���ͼƬ֮���ͼƬ
		//������֮��ɾ
		picture.getSprite().setColor(Color.RED);
		//��
		picture.getSprite().setScale(scaleXY);
		drawAtPosition(point.x*P.autoRatioX, point.y*P.autoRatioY, picture);
	}
//	//ֱ�ӻ�����ת������ı���
//	public static void drawRaw(OzPoint p,OzPicture picture){
//		drawRaw(p.x, p.y, picture);
//	}
//	public static void drawRaw(float x,float y,OzPicture picture){
//		picture.setDefault();
//		drawAtPosition(x, y, picture);
//	}

//	//��������
//	public static void drawBg(float x,float y,OzPicture picture){
//		//�˷���ֻ������������ͼ��
//		drawAtPosition(x*P.bgRatioX,  y*P.bgRatioY, picture);
//	}
//	//�Զ�����
//	public static void draw(OzPoint point,OzPicture picture){
//		draw(point.x, point.y, picture);
//	}
//	public static void draw(float x,float y,OzPicture picture){
//		picture.setDefault();
//		drawAtPosition(x*P.autoRatioX,   y*P.autoRatioY, picture);
//	}
//	//ǿ�Ʊ���
//	public static void drawForce(OzPoint point,OzPicture picture){
//		drawForce(point.x, point.y, picture);
//	}
//	public static void drawForce(float x,float y,OzPicture picture){
//		picture.setDefault();
//		drawAtPosition(x*P.forceRatioX,  y*P.forceRatioY, picture);
//	}
	
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
//		picture.getSprite().draw(batch);
	}
	
	

	//��ͼ������
	
	//������Ļ����
	public static void setlight(float light){
		black.setColor(0, 0, 0, light);
		black.draw(batch);
	}
	
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



//	private static TextureAtlas atlas;
	

//	private static Sprite mS(String pictureName){
//		
//		return atlas.createSprite(pictureName);
//	}
//	private static Sprite mS(String pictureName,int index){
//		return atlas.createSprite(pictureName,index);
//	}
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
	public static SpriteBatch getBatch() {
		return batch;
	}

	
}
