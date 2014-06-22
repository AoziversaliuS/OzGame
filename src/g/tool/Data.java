package g.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Data {
	
	private static FileHandle saveFile;
	
	private static final String FOLDER_NAME = "OzGame";
	
	/**
	 * 初始化.
	 * */
	public static void init(){
		saveFile = Gdx.files.external(FOLDER_NAME+"/.save");
		
		
		create();
	}
	/**
	 * 若文件不存在，则自动创建文件
	 * */
	private static void create(){
		if(!saveFile.exists()){
			//初值为第一关的关卡id
			saveFile.writeString("0", false);
		}
	}
	
	
	/**
	 * 获取最后一个已解锁的关卡Id
	 * */
	public static int getLastUnlockChapterId(){
		int lastUnlockChapterId = Integer.parseInt(saveFile.readString());
		return lastUnlockChapterId;
	}
	
	/**
	 * 解锁某章节，若存档里的最后已解锁章节Id大于要解锁的章节,则不做任何操作(因为此章节已被解锁)此时返回false
	 * */
	public static boolean unLockChapter(int chapterId){
		int lastUnlockChapterId = getLastUnlockChapterId();
		if( chapterId>lastUnlockChapterId ){
			saveFile.writeString(chapterId+"", false);
			return true;
		}
		return false;
	}
	
	
	
	
	
	
}
