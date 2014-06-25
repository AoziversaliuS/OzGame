package g.e.architecture;


import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Plane;
import g.type.Rank;
import g.type.Vertical;


public class Land extends BasicBody {
	
	private int width;
	private int height;
	
	//ʵ����ͼƬ��С��ͬʱҪ�Զ���ʵ��ƫ��ֵ
	public Land(String Tag, OzPoint l,OzRect entityOffset) {
		super(Tag, Rank.BUILD_3, ET.Land, l, entityOffset);
	}
	//ʵ����ͼƬ��С��ͬʹ��Ĭ��ƫ��ֵ
	public Land(String Tag,float x,float y,int widthNum,int heightNum) {
		
		super(Tag, Rank.BUILD_3, ET.Land, new OzPoint(x, y),
				new OzRect(0, 0, Res.build_land[0].getWidth()*widthNum,Res.build_land[0].getHeight()*heightNum));
		this.width = (int) (widthNum*Res.build_land[0].getWidth());
		this.height = (int) (heightNum*Res.build_land[0].getHeight());
		
	}
	
	@Override
	public void draw() {
		int dW = (int) Res.build_land[0].getWidth();
		int dH = (int) Res.build_land[0].getHeight();
		
		int xNum = 0;
		int yNum = 0;
		
		float right = this.width + l.x;
		float top = this.height + l.y;
		
		//�жϴ˴󷽿�������������Ļ֮�⣬�˴����û�׼��Ļ 1280 * 720p ���ж�
		if( right<0 || l.x>P.BASIC_SCREEN_WIDTH || top<0 || l.y>P.BASIC_SCREEN_HEIGHT ){
			//����������Ļ֮��Ĵ󷽿�
			System.out.println("��������Ļ������");
		}
		else{
			for(;  yNum<height; yNum=yNum+dH ){
				xNum = 0;
				for(; xNum<width; xNum=xNum+dW ){
					if( yNum==0 ){
						if( xNum==0 ){
							P.draw(l.x+xNum, l.y+yNum, Res.build_land[0],P.AUTO_RATIO);
						}
						else if( xNum==(width-dW) ){
							P.draw(l.x+xNum, l.y+yNum, Res.build_land[2],P.AUTO_RATIO);
						}
						else{
							P.draw(l.x+xNum, l.y+yNum, Res.build_land[1],P.AUTO_RATIO);
						}
					}
					else if( yNum==(height-dH) ){
						if( xNum==0 ){
							P.draw(l.x+xNum, l.y+yNum, Res.build_land[6],P.AUTO_RATIO);
						}
						else if( xNum==(width-dW) ){
							P.draw(l.x+xNum, l.y+yNum, Res.build_land[8],P.AUTO_RATIO);
						}
						else{
							P.draw(l.x+xNum, l.y+yNum, Res.build_land[7],P.AUTO_RATIO);
						}
					}
					else{
						if( xNum==0 ){
							P.draw(l.x+xNum, l.y+yNum, Res.build_land[3],P.AUTO_RATIO);
						}
						else if( xNum==(width-dW) ){
							P.draw(l.x+xNum, l.y+yNum, Res.build_land[5],P.AUTO_RATIO);
						}
						else{
							P.draw(l.x+xNum, l.y+yNum, Res.build_land[4],P.AUTO_RATIO);
						}
					}
				}
			}
		}
		
		

	}
	@Override
	public void logic() {
		
	}
	@Override
	public void impact(Player player) {
		
		
		if(this.hit(player) && Player.getCondition()==Player.ALIVE  ){
//			Gdx.app.log("impact", "������");
			float pX = player.entity.x;
			float pY = player.entity.y;
//			float pTop = player.entity.getTop();
			float pRight = player.entity.getRight();
//			float pCenterX = player.entity.centerX(); 
			float pCenterY = player.entity.centerY();
			
			
				if( pRight-entity.x<=Player.moveSpeed() ){
					//LEFT
					this.planeT = Plane.Left;
					player.setPush_X(pRight - this.entity.x,true);
					Player.setPlane_HitType(Player.HIT_BASIC); //������ײ����
					
				}
				else if( entity.getRight()-pX<=Player.moveSpeed() ){
					//RIGHT
					this.planeT = Plane.Right;
					player.setPush_X(pX - this.entity.getRight(),false);
					Player.setPlane_HitType(Player.HIT_BASIC); //������ײ����
					
				}
				else{
					if( pCenterY>entity.getTop() ){
						//TOP ֻ�в�������Ծ״̬����Ҳ��ܲȵ�½��
						this.verticalT = Vertical.Top;
						player.setPush_Y(this.entity.getTop() - pY);
						Player.setVertical_HitType(Player.HIT_BASIC); //������ײ����
						
					}
					else if( pCenterY<entity.y ){
						//BOTTOM
						this.verticalT = Vertical.Bottom;
						Player.setVertical_HitType(Player.HIT_BASIC); //������ײ����
						
					}
				}
		}
		else{
			this.planeT = Plane.Else;
			this.verticalT = Vertical.Else;
		}
		
	}
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	

	
	

}
