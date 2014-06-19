package g.tool;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Res {
	private static final int LOADING=1,LOAD_FINISH=2,UN_LOAD=3; 
	private static int loadStatus = LOADING;
	private static TextureAtlas atlas;
	
	private static AssetManager manager;
	
	private static String atlasPath;
	

	
	public static final int GAME_A=201,GAME_B=202,GAME_C=203;
	public static final int PAUSE_SOURCE = 101;
	static int sourceId = GAME_A;
	
	
	/**startSource*/
	public static OzPicture startBg;
	public static OzPicture startBtnA;
	public static OzPicture startBtnB;
	/**selectSource**/
	public static OzPicture selectBg;
	public static OzPicture selectBtn[] = new OzPicture[2];
	/**pauseSource**/
	public static OzPicture pause_btnResume[] = new OzPicture[2];
	public static OzPicture pause_btnToSelect[] = new OzPicture[2];
	public static OzPicture pause_btnToMain[] = new OzPicture[2];
	public static OzPicture pause_btnExit[] = new OzPicture[2];
	
	/**gameSource*/
	//button
	public static OzPicture[] game_btnLeft = new OzPicture[2];
	public static OzPicture[] game_btnRight = new OzPicture[2];
	public static OzPicture[] game_btnJump = new OzPicture[2];
	public static OzPicture[] game_btnAttack = new OzPicture[2];
	public static OzPicture[] game_btnPass = new OzPicture[2];
	//basic
	public static OzPicture[] player = new OzPicture[1];
	public static OzPicture backGround;
	//build�� 
	public static OzPicture[] land = new OzPicture[9];
	public static OzPicture thorn;
	public static OzPicture[] moveLand = new OzPicture[3];
	//view��
	public static OzPicture[] tree = new OzPicture[2];

	

	public static void init(){
		manager = new AssetManager();
		NecessaryResLoad();
		initPic();
	}
	private static void NecessaryResLoad(){
		loadAtlas("Image/start/start.atlas");
			startBg = new OzPicture(1280, 720, lS("startBg"), P.FORCE_RATIO);
			startBtnA = new OzPicture(334, 135, lS("startBtnA"), P.FORCE_RATIO);
			startBtnB = new OzPicture(334, 135, lS("startBtnB"), P.FORCE_RATIO);
		loadAtlas("Image/select/select.atlas");
			selectBg = new OzPicture(1280, 720, lS("bG"), P.FORCE_RATIO);
			selectBtn[0] = new OzPicture(150, 150, lS("btn",0), P.FORCE_RATIO);
			selectBtn[1] = new OzPicture(150, 150, lS("btn",1), P.FORCE_RATIO);
	}
	//ͼƬ�����ʼ��
	private static void initPic() {
		//��ͣ�˵������ʼ��
		setPicGroupData(283, 105, pause_btnResume, P.FORCE_RATIO);
		setPicGroupData(283, 105,  pause_btnToSelect,  P.FORCE_RATIO);
		setPicGroupData(283, 105,  pause_btnToMain,  P.FORCE_RATIO);
		setPicGroupData(283, 105,  pause_btnExit,  P.FORCE_RATIO);
		
		//��ϷͼƬ�����ʼ��
		setPicGroupData(208, 125, game_btnLeft,P.AUTO_RATIO);
		setPicGroupData(208, 125, game_btnRight,P.AUTO_RATIO);
		setPicGroupData(150, 150, game_btnJump,P.AUTO_RATIO);
		setPicGroupData(150, 150, game_btnAttack,P.AUTO_RATIO);
		setPicGroupData(100, 100, game_btnPass,P.AUTO_RATIO);
		setPicGroupData(45, 45, player,P.AUTO_RATIO);
		//build��
		setPicGroupData(50, 50, land,P.AUTO_RATIO);
		thorn        = new OzPicture(50, 50,P.AUTO_RATIO);
		moveLand[0]  = new OzPicture(50, 50,P.AUTO_RATIO);
		moveLand[1]  = new OzPicture(50, 50,P.AUTO_RATIO);
		moveLand[2]  = new OzPicture(50, 50,P.AUTO_RATIO);
		backGround   = new OzPicture(1280, 720, P.BG_RATIO);
		//view��
		setPicGroupData(195, 254, tree,P.AUTO_RATIO);
	}
	
	public static void prepare(int sourceId){
		//�˷���ֻ�ܽ���һ�Σ�,����Ԥ������Դ
		loadStatus = LOADING;
		//resourceNum��ʾ��Ҫ������һ����Դ
		Res.sourceId = sourceId;
		useSource();
	}
	public static boolean update(){
		boolean isFinish = manager.update();
		if(isFinish){
			loadStatus=LOAD_FINISH;
			useSource();
		}
		return isFinish;
	}
	private static void useSource(){
		switch (sourceId) {
				
				case GAME_A: { gA(); break; }
		}
		switch (sourceId){
		
				case PAUSE_SOURCE: { pauseSource();  break;}
		}
	}
	private static void pauseSource(){
		if( loadStatus==LOADING ){
			//������ͣ�˵���ͼƬ��Դ
			manager.load("Image/pause/pause.atlas", TextureAtlas.class);
		}
		else if( loadStatus==UN_LOAD ){//ж����Դ
			manager.unload("Image/pause/pause.atlas");
		}
		else if( loadStatus==LOAD_FINISH ){
			setAtlas("Image/pause/pause.atlas");
			loadPicGroup(pause_btnResume, "btnResume", P.FORCE_RATIO);
			loadPicGroup(pause_btnToSelect, "btnToSelect", P.FORCE_RATIO);
			loadPicGroup(pause_btnToMain, "btnToMain", P.FORCE_RATIO);
			loadPicGroup(pause_btnExit, "btnExit", P.FORCE_RATIO);
		}
		
	}
	
	private static void gA(){
		//����Դ�ŵ�Ԥ���ض�����
		if( loadStatus==LOADING ){
			manager.load("Image/button/button.atlas", TextureAtlas.class);
			manager.load("Image/player/player.atlas", TextureAtlas.class);
			manager.load("Image/build/build.atlas", TextureAtlas.class);
			manager.load("Image/backGround/backGround.atlas", TextureAtlas.class);
			manager.load("Image/view/view.atlas", TextureAtlas.class);
		}
		else if( loadStatus==UN_LOAD ){
			manager.unload("Image/button/button.atlas");
			manager.unload("Image/player/player.atlas");
			manager.unload("Image/build/build.atlas");
			manager.unload("Image/backGround/backGround.atlas");
			manager.unload("Image/view/view.atlas");
		}
		else if( loadStatus==LOAD_FINISH ){
			//��Դ������ɺ�����Դ������
			setAtlas("Image/button/button.atlas");
			loadPicGroup(game_btnLeft, "btnLeft",P.AUTO_RATIO);
			loadPicGroup(true, false, game_btnRight, "btnLeft",P.AUTO_RATIO);
			loadPicGroup(game_btnJump, "btnJump",P.AUTO_RATIO);
			loadPicGroup( game_btnAttack, "btnAttack",P.AUTO_RATIO);
			//��ͣ��ťʹ��ǿ�Ʊ���
			loadPicGroup(game_btnPass, "btnPass",P.FORCE_RATIO);
			
			setAtlas("Image/player/player.atlas");
			player[0].setSprite(mS("player",0),P.AUTO_RATIO);
			
			setAtlas("Image/backGround/backGround.atlas");
			backGround.setSprite(false, false, mS("backGround"), P.BG_RATIO);
			
			setAtlas("Image/build/build.atlas");
			loadPicGroup(land, "land",P.AUTO_RATIO);
			thorn.setSprite(mS("thorn"),P.AUTO_RATIO);
			moveLand[0].setSprite( mS("moveLand",0),P.AUTO_RATIO);
			moveLand[1].setSprite(mS("moveLand",1),P.AUTO_RATIO);
			moveLand[2].setSprite(true,false,mS("moveLand",0),P.AUTO_RATIO);
		
			setAtlas("Image/view/view.atlas");
			loadPicGroup( tree, "tree",P.AUTO_RATIO);
		}
	}
	
	
	
	private static void setPicGroupData(float width,float height,OzPicture[] picGroup,int ratioType){
		for(int i=0;i<picGroup.length;i++){
			picGroup[i] = new OzPicture(width, height,ratioType);
		}
	}
	private static void loadPicGroup(OzPicture[] picGroup,String resName,int ratioType){
//		for(int i=0;i<picGroup.length;i++){
////			picGroup[i] = new OzPicture(width, height, mS(resName,i));
//			picGroup[i].setSprite(mS(resName,i));
//		}
		loadPicGroup(false, false, picGroup, resName,ratioType);
	}
	private static void loadPicGroup(boolean planeFlip, boolean verticalFlip,OzPicture[] picGroup,String resName,int ratioType){
		for(int i=0;i<picGroup.length;i++){
//			picGroup[i] = new OzPicture(planeFlip,verticalFlip,width, height, mS(resName,i));
			picGroup[i].setSprite(planeFlip, verticalFlip, mS(resName,i),ratioType);
		}
	}
	
	
	
	
	private static void setAtlas(String aP){
		atlasPath = aP;
	}
	private static void loadAtlas(String atlasPath){
		atlas = new TextureAtlas(atlasPath);
	}
	private static Sprite lS(String picName){
		return atlas.createSprite(picName);
	}
	private static Sprite lS(String picName,int index){
		return atlas.createSprite(picName, index);
	}
	private static Sprite mS(String picName){
		return manager.get(atlasPath, TextureAtlas.class).createSprite(picName);
	}
	private static Sprite mS(String picName,int index){
		return manager.get(atlasPath, TextureAtlas.class).createSprite(picName,index);
	}
	/**
	 * ��ȡ���ؽ���
	 * */
	public static float getProgress(){
		return manager.getProgress();
	}
	public static void clear(){
		manager.clear();
	}
	/**
	 * ж��sourceId��Ӧ����Դ
	 * */
	public static void unload(int sourceId){
		Res.sourceId = sourceId;
		loadStatus = UN_LOAD;
		useSource();
	}
	
	

	
	

}
