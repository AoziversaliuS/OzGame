package g.basis;

import g.e.build.DeadLine;
import g.e.build.Land;
import g.e.build.MoveLand;
import g.e.build.ReferPoint;
import g.e.build.Thorn;
import g.e.view.View;
import g.refer.BackGround;
import g.refer.OzElement;
import g.tool.OzInt;
import g.tool.Res;
import g.type.Move;

import java.util.ArrayList;


public class GameChapter {
	
	//��һ�ص�ͼ
	static void A_01(ArrayList<OzElement> gateAtlas){
		
		gateAtlas.add(new BackGround("BG-1"));
		
		
		//���½�Ϊ�̳�BasicBody�Ķ���ֻ��BasicBody��������ܴﵽα��ֹ״̬
//		gateAtlas.add(new Land("L-1",500,300));
//		new MoveLand(Tag, x, y, widthNum, heightNum, A, B, speed, mT)
//		gateAtlas.add(new MoveLand("ML-1", 400, 200,3, 400, 600, 3, Move.plane));
		gateAtlas.add(new MoveLand("ML-1", 400, 300,4, 400, 600, 2, Move.plane));
//		gateAtlas.add(new MoveLand("ML-2", 500, 200, 200, 400, 3, Move.vertical));
		gateAtlas.add(new MoveLand("ML-1", 1150, 300,3, 300, 600, 2, Move.vertical));
		gateAtlas.add(new MoveLand("ML-1", 1000, 200,3, 200, 400, 3, Move.vertical));
		gateAtlas.add(new Land("L-1",300,0,3,2));
		gateAtlas.add(new Land("L-1",-200,-50,5,2));
		gateAtlas.add(new Land("L-1",500,-50,3,2));
		gateAtlas.add(new Land("L-1",700,-200,3,2));
		gateAtlas.add(new Land("L-1",1050,-200,3,3));
		gateAtlas.add(new Thorn("T-1",-200, 150));
		gateAtlas.add(new Thorn("T-1",-150, 150));
		gateAtlas.add(new Thorn("T-1",-100, 150));
		gateAtlas.add(new Thorn("T-1",-50, 150));
		gateAtlas.add(new View("signTower", 0, 150, Res.signTower));
		
		gateAtlas.add(new DeadLine(-300) );
		System.out.println("gateAtlasSize="+gateAtlas.size());
	}
	
	
	
	
	//�ڶ��ص�ͼ
	private static void A_02(ArrayList<OzElement> gateAtlas) {
		// TODO Auto-generated method stub
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//��ͼ����
	static void chapterLoad(ArrayList<OzElement> gateAtlas,ArrayList<OzInt> rankNum,int gateNum){
		initialise(gateAtlas,rankNum); //һ��Ҫд�ڵ�һ��
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
		makingRankArray(gateAtlas, rankNum);//һ��Ҫд�����һ��
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
