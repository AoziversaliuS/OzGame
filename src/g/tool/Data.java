package g.tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Data {
	
	private static FileHandle saveFile;
	
	private static final String FOLDER_NAME = "OzGame";
	
	/**
	 * ��ʼ��.
	 * */
	public static void init(){
		saveFile = Gdx.files.external(FOLDER_NAME+"/.save");
		
		
		create();
	}
	/**
	 * ���ļ������ڣ����Զ������ļ�
	 * */
	private static void create(){
		if(!saveFile.exists()){
			//��ֵΪ��һ�صĹؿ�id
			saveFile.writeString("0", false);
		}
	}
	
	
	/**
	 * ��ȡ���һ���ѽ����Ĺؿ�Id
	 * */
	public static int getLastUnlockChapterId(){
		int lastUnlockChapterId = Integer.parseInt(saveFile.readString());
		return lastUnlockChapterId;
	}
	
	/**
	 * ����ĳ�½ڣ����浵�������ѽ����½�Id����Ҫ�������½�,�����κβ���(��Ϊ���½��ѱ�����)��ʱ����false
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
