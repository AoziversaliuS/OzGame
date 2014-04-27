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
	static int resourceNum;
	
	
	//startSource
	public static OzPicture startBg;
	public static OzPicture startBtnA;
	public static OzPicture startBtnB;
	
	//Button
	public static OzPicture[] Game_btnLeft = new OzPicture[2];
	public static OzPicture[] Game_btnRight = new OzPicture[2];
	public static OzPicture[] Game_btnJump = new OzPicture[2];
	public static OzPicture[] Game_btnAttack = new OzPicture[2];
	
	//gameSource
	public static OzPicture backGround;
	
	//Land↓ 
	public static OzPicture[] land = new OzPicture[9];
	//Land↑
	
	public static OzPicture[] player = new OzPicture[3];
	public static OzPicture Thorn;
	public static OzPicture[] MoveLand = new OzPicture[3];
	
	public static void init(){
		manager = new AssetManager();
		NecessaryResLoad();
	}
	public static void NecessaryResLoad(){
		loadAtlas("Image/start/start.atlas");
		startBg = new OzPicture(1280, 720, lS("startBg"), P.FORCE_RATIO);
		startBtnA = new OzPicture(334, 135, lS("startBtnA"));
		startBtnB = new OzPicture(334, 135, lS("startBtnB"));
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
	
	private static void gA(){
		//将资源放到预加载队列中
		if( loadStatus==LOADING ){
			manager.load("Image/button/button.atlas", TextureAtlas.class);
			manager.load("Image/player/player.atlas", TextureAtlas.class);
			manager.load("Image/build/build.atlas", TextureAtlas.class);
			manager.load("Image/backGround/backGround.atlas", TextureAtlas.class);
		}
		//资源加载完成后建立资源的引用
		else if( loadStatus==LOAD_FINISH ){
			
			setAtlas("Image/button/button.atlas");
				loadPicGroup(208, 125, Game_btnLeft, "btnLeft");
				loadPicGroup(true, false, 208, 125, Game_btnRight, "btnLeft");
				loadPicGroup(150, 150, Game_btnJump, "btnJump");
				loadPicGroup(150, 150, Game_btnAttack, "btnAttack");
				
			setAtlas("Image/player/player.atlas");
				player[0]    = new OzPicture(45, 45, mS("player"));
				player[1]    = new OzPicture(45, 45, mS("playerLeft"));
				player[2]    = new OzPicture(true, false,45,45, mS("playerLeft"));
				
			setAtlas("Image/build/build.atlas");
				loadPicGroup(50, 50, land, "land");
				Thorn        = new OzPicture(50, 50, mS("thorn"));
				MoveLand[0]  = new OzPicture(50, 50, mS("moveLand",0));
				MoveLand[1]  = new OzPicture(50, 50, mS("moveLand",1));
				MoveLand[2]  = new OzPicture(true, false, 50, 50, mS("moveLand",0));
				
			setAtlas("Image/backGround/backGround.atlas");
				backGround          = new OzPicture(1280, 720, mS("backGround"), P.BG_RATIO);
		}

	}
	private static void loadPicGroup(float width,float height,OzPicture[] picGroup,String resName){
		for(int i=0;i<picGroup.length;i++){
			picGroup[i] = new OzPicture(width, height, mS(resName,i));
		}
	}
	private static void loadPicGroup(boolean planeFlip, boolean verticalFlip,float width,float height,OzPicture[] picGroup,String resName){
		for(int i=0;i<picGroup.length;i++){
			picGroup[i] = new OzPicture(planeFlip,verticalFlip,width, height, mS(resName,i));
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
