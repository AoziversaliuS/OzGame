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
	
	//实体与图片大小不同时要自定义实体偏移值
	public Land(String Tag, OzPoint l,OzRect entityOffset) {
		super(Tag, Rank.BUILD_3, ET.Land, l, entityOffset);
	}
	//实体与图片大小相同使用默认偏移值
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
		
		float left = P.getRealValue(l.x, P.AUTO_RATIO, true);
		float down = P.getRealValue(l.y, P.AUTO_RATIO, false);
		float right = P.getRealValue(this.width + l.x, P.AUTO_RATIO, true);
		float top = P.getRealValue(this.height + l.y, P.AUTO_RATIO, false);
		
		//判断此大方块整体有无在屏幕之外，此处采用基准屏幕 1280 * 720p 来判定
		if( right<0 || left>P.BASIC_SCREEN_WIDTH || top<0 || down>P.BASIC_SCREEN_HEIGHT ){
			//不画处于屏幕之外的大方块
			System.out.println("超出了屏幕。。。");
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
//			Gdx.app.log("impact", "碰到了");
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
					Player.setPlane_HitType(Player.HIT_BASIC); //设置碰撞类型
					
				}
				else if( entity.getRight()-pX<=Player.moveSpeed() ){
					//RIGHT
					this.planeT = Plane.Right;
					player.setPush_X(pX - this.entity.getRight(),false);
					Player.setPlane_HitType(Player.HIT_BASIC); //设置碰撞类型
					
				}
				else{
					if( pCenterY>entity.getTop() ){
						//TOP 只有不处于跳跃状态下玩家才能踩到陆地
						this.verticalT = Vertical.Top;
						player.setPush_Y(this.entity.getTop() - pY);
						Player.setVertical_HitType(Player.HIT_BASIC); //设置碰撞类型
						
					}
					else if( pCenterY<entity.y ){
						//BOTTOM
						this.verticalT = Vertical.Bottom;
						Player.setVertical_HitType(Player.HIT_BASIC); //设置碰撞类型
						
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
