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
		super(Tag, Rank.BUILD, ET.Land, l, entityOffset);
	}
	//实体与图片大小相同使用默认偏移值
	public Land(String Tag,float x,float y,int widthNum,int heightNum) {
		
		super(Tag, Rank.BUILD, ET.Land, new OzPoint(x, y),
				new OzRect(0, 0, Res.land[0].getWidth()*widthNum,Res.land[0].getHeight()*heightNum));
		this.width = (int) (widthNum*Res.land[0].getWidth());
		this.height = (int) (heightNum*Res.land[0].getHeight());
		
	}
	
	@Override
	public void draw() {
		int dW = (int) Res.land[0].getWidth();
		int dH = (int) Res.land[0].getHeight();
		
		int xNum = 0;
		int yNum = 0;
		
		float right = this.width + l.x;
		float top = this.height + l.y;
		
		//判断此大方块整体有无在屏幕之外，此处采用基准屏幕 1280 * 720p 来判定
		if( right<0 || l.x>P.BASIC_SCREEN_WIDTH || top<0 || l.y>P.BASIC_SCREEN_HEIGHT ){
			//不画处于屏幕之外的大方块
		}
		else{
			for(;  yNum<height; yNum=yNum+dH ){
				xNum = 0;
				for(; xNum<width; xNum=xNum+dW ){
					if( yNum==0 ){
						if( xNum==0 ){
							P.draw(l.x+xNum, l.y+yNum, Res.land[0]);
						}
						else if( xNum==(width-dW) ){
							P.draw(l.x+xNum, l.y+yNum, Res.land[2]);
						}
						else{
							P.draw(l.x+xNum, l.y+yNum, Res.land[1]);
						}
					}
					else if( yNum==(height-dH) ){
						if( xNum==0 ){
							P.draw(l.x+xNum, l.y+yNum, Res.land[6]);
						}
						else if( xNum==(width-dW) ){
							P.draw(l.x+xNum, l.y+yNum, Res.land[8]);
						}
						else{
							P.draw(l.x+xNum, l.y+yNum, Res.land[7]);
						}
					}
					else{
						if( xNum==0 ){
							P.draw(l.x+xNum, l.y+yNum, Res.land[3]);
						}
						else if( xNum==(width-dW) ){
							P.draw(l.x+xNum, l.y+yNum, Res.land[5]);
						}
						else{
							P.draw(l.x+xNum, l.y+yNum, Res.land[4]);
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
