package g.tool;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Res {
	private static final int LOADING=1,LOAD_FINISH=2; 
	private static int loadStatus = LOADING;
	private static TextureAtlas atlas;
	
	private static AssetManager manager;
	
	private static String atlasPath;
	

	
	public static final int resourceA=1,resourceB=2,resourceC=3;
	static int resourceNum = resourceA;
	
	
	//startSource
	public static OzPicture startBg;
	public static OzPicture startBtnA;
	public static OzPicture startBtnB;
	//selectSource
	public static OzPicture selectBg;
	public static OzPicture selectBtn[] = new OzPicture[2];
	
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
	//build↓ 
	public static OzPicture[] land = new OzPicture[9];
	public static OzPicture thorn;
	public static OzPicture[] moveLand = new OzPicture[3];
	//view↓
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
	//图片对象初始化
	private static void initPic() {
		setPicGroupData(208, 125, game_btnLeft,P.AUTO_RATIO);
		setPicGroupData(208, 125, game_btnRight,P.AUTO_RATIO);
		setPicGroupData(150, 150, game_btnJump,P.AUTO_RATIO);
		setPicGroupData(150, 150, game_btnAttack,P.AUTO_RATIO);
		setPicGroupData(100, 100, game_btnPass,P.AUTO_RATIO);
		setPicGroupData(45, 45, player,P.AUTO_RATIO);
		//build↓
		setPicGroupData(50, 50, land,P.AUTO_RATIO);
		thorn        = new OzPicture(50, 50,P.AUTO_RATIO);
		moveLand[0]  = new OzPicture(50, 50,P.AUTO_RATIO);
		moveLand[1]  = new OzPicture(50, 50,P.AUTO_RATIO);
		moveLand[2]  = new OzPicture(50, 50,P.AUTO_RATIO);
		backGround   = new OzPicture(1280, 720, P.BG_RATIO);
		//view↓
		setPicGroupData(195, 254, tree,P.AUTO_RATIO);
	}
	
	public static void prepare(int rNum){
		//此方法只能进入一次！,用来预加载资源
		loadStatus = LOADING;
		//resourceNum表示需要加载哪一份资源
		resourceNum = rNum;
		useResource();
	}
	public static boolean update(){
		boolean isFinish = manager.update();
		if(isFinish){
			loadStatus=LOAD_FINISH;
			useResource();
		}
		return isFinish;
	}
	private static void useResource(){
		switch (resourceNum) {
				
				case resourceA: { gA(); break; }
					
		
		}
	}
	
	private static void gA(){
		//将资源放到预加载队列中
		if( loadStatus==LOADING ){
			manager.load("Image/button/button.atlas", TextureAtlas.class);
			manager.load("Image/player/player.atlas", TextureAtlas.class);
			manager.load("Image/build/build.atlas", TextureAtlas.class);
			manager.load("Image/backGround/backGround.atlas", TextureAtlas.class);
			manager.load("Image/view/view.atlas", TextureAtlas.class);
		
		}
		//资源加载完成后建立资源的引用
		else if( loadStatus==LOAD_FINISH ){
			
			setAtlas("Image/button/button.atlas");
			loadPicGroup(game_btnLeft, "btnLeft",P.AUTO_RATIO);
			loadPicGroup(true, false, game_btnRight, "btnLeft",P.AUTO_RATIO);
			loadPicGroup(game_btnJump, "btnJump",P.AUTO_RATIO);
			loadPicGroup( game_btnAttack, "btnAttack",P.AUTO_RATIO);
			loadPicGroup(game_btnPass, "btnPass",P.AUTO_RATIO);
			
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

	public static float getProgress(){
		return manager.getProgress();
	}
	public static void clear(){
		manager.clear();
	}
	
	

	
	

}
