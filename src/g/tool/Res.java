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
	

	
	public static final int gA=1,gB=2,gC=3;
	static int resourceNum = gA;
	
	
	//startSource
	public static OzPicture startBg;
	public static OzPicture startBtnA;
	public static OzPicture startBtnB;
	
	//Button
	public static OzPicture[] game_btnLeft = new OzPicture[2];
	public static OzPicture[] game_btnRight = new OzPicture[2];
	public static OzPicture[] game_btnJump = new OzPicture[2];
	public static OzPicture[] game_btnAttack = new OzPicture[2];
	
	//gameSource
	public static OzPicture[] player = new OzPicture[3];
	public static OzPicture backGround;
	//build↓ 
	public static OzPicture[] land = new OzPicture[9];
	public static OzPicture thorn;
	public static OzPicture[] moveLand = new OzPicture[3];
	//view↓
	public static OzPicture signTower;
	public static OzPicture[] signTowerLight = new OzPicture[9];

	
	
	public static void init(){
		manager = new AssetManager();
		NecessaryResLoad();
		loadPicData();
	}
	private static void NecessaryResLoad(){
		loadAtlas("Image/start/start.atlas");
		startBg = new OzPicture(1280, 720, lS("startBg"), P.FORCE_RATIO);
		startBtnA = new OzPicture(334, 135, lS("startBtnA"), P.FORCE_RATIO);
		startBtnB = new OzPicture(334, 135, lS("startBtnB"), P.FORCE_RATIO);
	}
	
	
	public static void prepare(int rNum){
		//此方法只能进入一次！,用来预加载资源
		loadStatus = LOADING;
		//resourceNum表示需要加载哪一份资源
		resourceNum = rNum;
		makeResource();
	}
	public static boolean update(){
		boolean isFinish = manager.update();
		if(isFinish){
			loadStatus=LOAD_FINISH;
			makeResource();
		}
		return isFinish;
	}
	private static void makeResource(){
		switch (resourceNum) {
				
				case gA: { gA(); break; }
					
		
		}
	}
	private static void loadPicData() {
		setPicGroupData(208, 125, game_btnLeft);
		setPicGroupData(208, 125, game_btnRight);
		setPicGroupData(150, 150, game_btnJump);
		setPicGroupData(150, 150, game_btnAttack);
		setPicGroupData(45, 45, player);
		//build↓
		setPicGroupData(100, 100, land);
		thorn        = new OzPicture(50, 50);
		moveLand[0]  = new OzPicture(50, 50);
		moveLand[1]  = new OzPicture(50, 50);
		moveLand[2]  = new OzPicture(50, 50);
		backGround   = new OzPicture(1280, 720, P.BG_RATIO);
		//view↓
		signTower = new OzPicture(255, 283);
		setPicGroupData(76, 76, signTowerLight);
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
			loadPicGroup(game_btnLeft, "btnLeft");
			loadPicGroup(true, false, game_btnRight, "btnLeft");
			loadPicGroup(game_btnJump, "btnJump");
			loadPicGroup( game_btnAttack, "btnAttack");
			
		setAtlas("Image/player/player.atlas");
			player[0].setSprite(mS("player",0));
			player[1].setSprite(mS("player",1));
			player[2].setSprite(true, false,mS("player",1));
		setAtlas("Image/backGround/backGround.atlas");
			backGround.setSprite(false, false, mS("backGround"), P.BG_RATIO);
			
		setAtlas("Image/build/build.atlas");
			loadPicGroup(land, "land");
			thorn.setSprite(mS("thorn"));
			moveLand[0].setSprite( mS("moveLand",0));
			moveLand[1].setSprite(mS("moveLand",1));
			moveLand[2].setSprite(true,false,mS("moveLand",0));
		
		setAtlas("Image/view/view.atlas");
			signTower.setSprite(mS("signTower"));
			loadPicGroup( signTowerLight, "signLight");
			
		}

	}
	private static void setPicGroupData(float width,float height,OzPicture[] picGroup){
		for(int i=0;i<picGroup.length;i++){
			picGroup[i] = new OzPicture(width, height);
		}
	}
	private static void loadPicGroup(OzPicture[] picGroup,String resName){
		for(int i=0;i<picGroup.length;i++){
//			picGroup[i] = new OzPicture(width, height, mS(resName,i));
			picGroup[i].setSprite(mS(resName,i));
		}
	}
	private static void loadPicGroup(boolean planeFlip, boolean verticalFlip,OzPicture[] picGroup,String resName){
		for(int i=0;i<picGroup.length;i++){
//			picGroup[i] = new OzPicture(planeFlip,verticalFlip,width, height, mS(resName,i));
			picGroup[i].setSprite(planeFlip, verticalFlip, mS(resName,i));
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
