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
	
	//��һ�ص�ͼ
	static void A_00(ArrayList<OzElement> gateAtlas){
		System.out.println("����A_00000");
		gateAtlas.add(new BackGround("BG-1"));
		
		
		//���½�Ϊ�̳�BasicBody�Ķ���ֻ��BasicBody��������ܴﵽα��ֹ״̬
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
	
	
	
	
	//�ڶ��ص�ͼ
	private static void A_01(ArrayList<OzElement> gateAtlas) {
		gateAtlas.add(new BackGround("BG-1"));
		gateAtlas.add(new Land("l1", 300, -100, 10, 5));
		gateAtlas.add(new View("Tree", 400, 145, Res.tree[1]));
		gateAtlas.add(new DeadLine(-300) );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//��ͼ����
	public static void chapterLoad(ArrayList<OzElement> gateAtlas,ArrayList<OzInt> rankNum,int gateNum){
		initialise(gateAtlas,rankNum); //һ��Ҫд�ڵ�һ�У�������յ�ͼ��Ϣ
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
		makingRankArray(gateAtlas, rankNum);//һ��Ҫд�����һ�У��������ùؿ���Ϣ
	}
	/**�л���ͼʱ�����ͼ����ͼ�����*/
	private static void initialise(ArrayList<OzElement> gateAtlas,ArrayList<OzInt> rankNum){
//		gateAtlas       = new ArrayList<OzElement>();   //�����ͼ������
//		rankNum         = new ArrayList<OzInt>();       //����ͼ����Ŷ��� 
		rankNum.clear();
		gateAtlas.clear();
		
		
		gateAtlas.add(new ReferPoint());
		//ÿһ�ؼ��ص�ͼʱ��ǰ���ز��յ�,�����ǵ�һ���ӽ�ȥ��Ԫ�أ������߼�������ȸ��²��յ�Ȼ���ٽ��������������߼�����
		
	}
	/**ʹͼ����д�С��������*/
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
		//�õ������е�ͼ��
//		String s = "ͼ����: ";
//		for(int j=0;j<rankNum.length;j++){
//			s = s + " "+rankNum[j]+" ";
//		}
//		Log.v("RankNum", s);
		//��ͼ����Ž�������
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
		String s = "ͼ����: ";
		for(int j=0;j<rankNum.size();j++){
			s = s + " "+rankNum.get(j).value+" ";
		}
	}
}
