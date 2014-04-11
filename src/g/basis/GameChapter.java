package g.basis;

import g.build.DeadLine;
import g.build.Land;
import g.build.MoveLand;
import g.build.ReferPoint;
import g.build.Thorn;
import g.refer.BackGround;
import g.refer.OzElement;
import g.tool.OzInt;
import g.type.Move;

import java.util.ArrayList;


public class GameChapter {
	
	//第一关地图
	static void A_01(ArrayList<OzElement> gateAtlas){
		
		gateAtlas.add(new BackGround("BG-1"));
		
		
		//以下皆为继承BasicBody的对象，只有BasicBody的子类才能达到伪静止状态
//		gateAtlas.add(new Land("L-1",500,300));
		gateAtlas.add(new MoveLand("ML-1", 400, 200, 400, 600, 3, Move.plane));
		gateAtlas.add(new MoveLand("ML-1", 400, 300, 400, 600, 2, Move.plane));
//		gateAtlas.add(new MoveLand("ML-2", 500, 200, 200, 400, 3, Move.vertical));
		gateAtlas.add(new MoveLand("ML-1", 1150, 300, 300, 600, 2, Move.vertical));
		gateAtlas.add(new MoveLand("ML-1", 1000, 200, 200, 400, 3, Move.vertical));
		gateAtlas.add(new Land("L-1",300,0));
//		gateAtlas.add(new Land("L-1",00,0));
		gateAtlas.add(new Land("L-1",500,-50));
		gateAtlas.add(new Land("L-1",700,-200));
		gateAtlas.add(new Thorn("T-1", 0, 100));
		gateAtlas.add(new Thorn("T-2", 400, 300));
//		gateAtlas.add(new Thorn("T-3", 800, 300));
		gateAtlas.add(new Thorn("T-3", 1200, 100));
		
		gateAtlas.add(new DeadLine(-300) );
		System.out.println("gateAtlasSize="+gateAtlas.size());
	}
	
	
	
	
	//第二关地图
	private static void A_02(ArrayList<OzElement> gateAtlas) {
		// TODO Auto-generated method stub
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//地图载入
	static void chapterLoad(ArrayList<OzElement> gateAtlas,ArrayList<OzInt> rankNum,int gateNum){
		initialise(gateAtlas,rankNum); //一定要写在第一行
		switch (gateNum){
		
		case 1:
			                    A_01(gateAtlas);
			break;
		case 2:
								A_02(gateAtlas);
			break;
			
			
		default:
			break;
		}
		makingRankArray(gateAtlas, rankNum);//一定要写在最后一行
	}
	/**切换地图时重设地图集和图层队列*/
	private static void initialise(ArrayList<OzElement> gateAtlas,ArrayList<OzInt> rankNum){
//		gateAtlas       = new ArrayList<OzElement>();   //定义地图集队列
//		rankNum         = new ArrayList<OzInt>();       //重设图层序号队列 
		rankNum.clear();
		gateAtlas.clear();
		
		
		gateAtlas.add(new ReferPoint());
		//每一关加载地图时提前加载参照点,由于是第一个加进去的元素，所以逻辑运算会先更新参照点然后再进行其它建筑的逻辑运算
		
	}
	/**使图层队列从小到大排序*/
	private static void makingRankArray(ArrayList<OzElement> gateAtlas,ArrayList<OzInt> rankNum){
		
		for(int i=0;i<gateAtlas.size();i++){
			
			boolean rankNumHaved = false;
			for(int j=0;j<rankNum.size();j++){
				if(rankNum.get(j).value == gateAtlas.get(i).rankNum){
					rankNumHaved = true;
					break;
				}
			}
			if( rankNumHaved==false ){
				rankNum.add(new OzInt(gateAtlas.get(i).rankNum));
			}
			
		}
		//拿到了已有的图层
//		String s = "图层有: ";
//		for(int j=0;j<rankNum.length;j++){
//			s = s + " "+rankNum[j]+" ";
//		}
//		Log.v("RankNum", s);
		//对图层序号进行排序
		int buffer = -2;
		for(int i=0;i<rankNum.size();i++){
			for(int j=i+1;j<rankNum.size();j++){
				if(rankNum.get(i).value>rankNum.get(j).value){
					buffer = rankNum.get(i).value;
					rankNum.get(i).value = rankNum.get(j).value;
					rankNum.get(j).value = buffer;
				}
			}
		}
		String s = "图层有: ";
		for(int j=0;j<rankNum.size();j++){
			s = s + " "+rankNum.get(j).value+" ";
		}
	}
}
