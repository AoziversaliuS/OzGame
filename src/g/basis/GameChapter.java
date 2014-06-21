package g.basis;

import g.e.architecture.DeadLine;
import g.e.architecture.Land;
import g.e.architecture.MoveLand;
import g.e.architecture.ReferPoint;
import g.e.architecture.Thorn;
import g.e.scenery.AnimationView;
import g.e.scenery.View;
import g.refer.BackGround;
import g.refer.OzElement;
import g.tool.OzInt;
import g.tool.Res;
import g.type.Move;

import java.util.ArrayList;


public class GameChapter {
	
	//第一关地图
	static void A_00(ArrayList<OzElement> gateAtlas){
		System.out.println("进入A_00000");
		gateAtlas.add(new BackGround("BG-1"));
		
		
		//以下皆为继承BasicBody的对象，只有BasicBody的子类才能达到伪静止状态
//		gateAtlas.add(new Land("L-1",500,300));
//		new MoveLand(Tag, x, y, widthNum, heightNum, A, B, speed, mT)
//		gateAtlas.add(new MoveLand("ML-1", 400, 200,3, 400, 600, 3, Move.plane));
//		gateAtlas.add(new MoveLand("ML-1", 400, 300,4, 400, 600, 2, Move.plane));
//		gateAtlas.add(new MoveLand("ML-2", 500, 200, 200, 400, 3, Move.vertical));
		gateAtlas.add(new MoveLand("ML-1", 1150, 300,3, 300, 600, 2, Move.vertical));
		gateAtlas.add(new MoveLand("ML-1", 1000, 200,3, 200, 400, 3, Move.vertical));
		gateAtlas.add(new Land("L-1",-200,-50,            25,4));
//		gateAtlas.add(new Land("L-1",-200,-50,         10,4));
//		gateAtlas.add(new Land("L-1",500,-50,          6,4));
//		gateAtlas.add(new Land("L-1",700,-200,         6,4));
//		gateAtlas.add(new Land("L-1",1050,-200,        9,9));
		gateAtlas.add(new Thorn("T-1",-200, 150));
		gateAtlas.add(new Thorn("T-1",-150, 150));
		gateAtlas.add(new Thorn("T-1",-100, 150));
		gateAtlas.add(new Thorn("T-1",-50, 150));
		
		gateAtlas.add(new View("Tree", 500, 145, Res.tree[0]));
		
		gateAtlas.add(new DeadLine(-300) );
//		System.out.println("gateAtlasSize="+gateAtlas.size());
	}
	
	
	
	
	//第二关地图
	private static void A_01(ArrayList<OzElement> gateAtlas) {
		gateAtlas.add(new BackGround("BG-1"));
		gateAtlas.add(new Land("l1", 300, -100, 10, 5));
		gateAtlas.add(new View("Tree", 400, 145, Res.tree[1]));
		gateAtlas.add(new DeadLine(-300) );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//地图载入
	public static void chapterLoad(ArrayList<OzElement> gateAtlas,ArrayList<OzInt> rankNum,int gateNum){
		initialise(gateAtlas,rankNum); //一定要写在第一行，用来清空地图信息
		switch (gateNum){
		
		case 0:
			                    A_00(gateAtlas);
			break;
		case 1:
								A_01(gateAtlas);
			break;
			
			
		default:
			break;
		}
		makingRankArray(gateAtlas, rankNum);//一定要写在最后一行，用来设置关卡信息
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
