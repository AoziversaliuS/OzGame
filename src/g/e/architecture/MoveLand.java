package g.e.architecture;



import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Move;
import g.type.Plane;
import g.type.Rank;
import g.type.Vertical;

public class MoveLand extends BasicBody{

	public  float speed=0;
	
	public float limitA;
	public float limitB;
	public float moveDistance; //����ع�����ʱҪ�õ��˲���
	
	public int width;
//	public int height;
	
	public Move mT;
	//ʵ����ͼƬ��С��ͬʹ��Ĭ��ƫ��ֵ
	public MoveLand(String Tag,float x,float y,float widthNum,float A,float B,float speed,Move mT) {
		super(Tag, Rank.BUILD, ET.MoveLand, new OzPoint(x, y),new OzRect(0, 0, widthNum*Res.moveLand[0].getWidth(),Res.moveLand[0].getHeight()));
		this.speed = speed;
		this.mT = mT;
		limitA = A;
		limitB = B;
		this.width = (int) (widthNum*Res.moveLand[0].getWidth());
//		this.height = (int) (heightNum*Res.moveLand[0].getHeight());
	}
	
	
	@Override
	public void prepare() {
		
		if(mT == Move.plane){
			float left = refer.x + limitA; 
			float right = refer.x + limitB;
			if(l.x<left){
				speed = Math.abs(speed);//ʹ������������ƶ�
			}
			else if(l.x>right){
				speed = -Math.abs(speed);//ʹ������������ƶ�
			}
		}
		else if(mT == Move.vertical){
			float top = refer.y + limitB;
			float down =  refer.y + limitA;
			if(l.y>top){
				speed = -Math.abs(speed);//ʹ������������ƶ�
			}
			else if(l.y<down){
				speed = Math.abs(speed);//ʹ������������ƶ�
			}
		}
	}
	
	@Override
	public void logic() {
		
		//�����ƶ�
		if(Player.getCondition() == Player.ALIVE){
			if(mT == Move.plane){
				l.x = l.x + speed;
			}
			else if(mT == Move.vertical){
				l.y = l.y + speed;
			}
		}
	
		
	
	}
	@Override
	public void draw() {
		P.draw(l, Res.moveLand[0]);
		int dW = (int) Res.moveLand[0].getWidth();
		int dNum = 0;
		for(;dNum<width;dNum=dNum+dW){
			if( dNum==0 ){
				P.draw(l.x+dNum, l.y, Res.moveLand[0]);
			}
			else if( dNum==width-dW ){
				P.draw(l.x+dNum, l.y, Res.moveLand[2]);
			}
			else{
				P.draw(l.x+dNum, l.y, Res.moveLand[1]);
			}
		}
	}
	
	
	
	@Override
	public void impact(Player player) {
		
		
		
		if(this.hit(player) && Player.getCondition()==Player.ALIVE  ){
//			Gdx.app.log("impact", "������");
			float pX = player.entity.x;
			float pY = player.entity.y;
			float pTop = player.entity.getTop();
			float pRight = player.entity.getRight();
			float pCenterX = player.entity.centerX(); 
			float pCenterY = player.entity.centerY();
			
			BasicBody.MoveLand_hitting = this;//���汻�������ƶ�������Ϣ
			
			if( mT==Move.vertical ){
				if( pRight-entity.x<=Player.moveSpeed() ){
					//LEFT
					this.planeT = Plane.Left;
					player.setPush_X(pRight - this.entity.x,true);
					Player.setPlane_HitType(Player.HIT_Moving); //������ײ����
					
//					if(this.Tag.equals("ML-2"))
//					Gdx.app.log("MoveLand", "LEFT");
				}
				else if( entity.getRight()-pX<=Player.moveSpeed() ){
					//RIGHT
					this.planeT = Plane.Right;
					player.setPush_X(pX - this.entity.getRight(),false);
					
					Player.setPlane_HitType(Player.HIT_Moving); //������ײ����
					
//					if(this.Tag.equals("ML-2"))
//					Gdx.app.log("MoveLand", "RIGHT");
				}
				else{
					if( pCenterY>entity.getTop() ){
						//TOP ֻ�в�������Ծ״̬����Ҳ��ܲȵ�½��
						this.verticalT = Vertical.Top;
						player.setPush_Y(this.entity.getTop() - pY);
						
						Player.setVertical_HitType(Player.HIT_Moving); //������ײ����
						
//						if(this.Tag.equals("ML-2"))
//						Gdx.app.log("MoveLand", "TOP");
					}
					else if( pCenterY<entity.y ){
						//BOTTOM
						this.verticalT = Vertical.Bottom;
						Player.setVertical_HitType(Player.HIT_Moving); //������ײ����
						
//						if(this.Tag.equals("ML-2"))
//						Gdx.app.log("MoveLand", "BOTTOM");
					}
				}
			}
			else if( mT==Move.plane ){
				if(this.entity.getTop() - pY <= Player.gravity() && Player.isJump() == false){
						//TOP ֻ�в�������Ծ״̬����Ҳ��ܲȵ�½��
						this.verticalT = Vertical.Top;
						player.setPush_Y(this.entity.getTop() - pY);
						Player.setVertical_HitType(Player.HIT_Moving); //������ײ����
						
//						System.out.println("TOP");
						
				}
				else if( pY < this.entity.getTop() && pTop - this.entity.y>Player.jumpSpeed() ){
					if(pCenterX < this.entity.x){
						//LEFT
						this.planeT = Plane.Left;
						player.setPush_X(pRight - this.entity.x,true);
						Player.setPlane_HitType(Player.HIT_Moving); //������ײ����
						
//						System.out.println("LEFT");
						
					}
					else if(pCenterX > this.entity.getRight()){
						//RIGHT
						this.planeT = Plane.Right;
						player.setPush_X(pX - this.entity.getRight(),false);
						Player.setPlane_HitType(Player.HIT_Moving); //������ײ����
						
//						System.out.println("RIGHT");
						
					}
				}
				else if(pTop - this.entity.y <= Player.jumpSpeed()){
					//BOTTOM
					this.verticalT = Vertical.Bottom;
					Player.setVertical_HitType(Player.HIT_Moving); //������ײ����
					
//					System.out.println("BOTTOM");
				}
			}
			
		}
		else{
			this.planeT = Plane.Else;
			this.verticalT = Vertical.Else;
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean rollBack() {

		float sinY;
		float cosX;

		setMoveDistanceAndC();
		
		if( Math.abs(c-range)<deviation || c<range ){
			if(mT == Move.plane){
				l.x = startPoint.x + moveDistance;
				l.y = startPoint.y;
			}
			else if(mT == Move.vertical){
				l.x = startPoint.x;
				l.y = startPoint.y + moveDistance;
			}

			System.out.println("�������");
			selected = false; //�����ƶ���ɺ�selected��Ϊfalse;
			return true;
		}
		else{
			cosX = a/c;
			sinY = b/c;
			float dx = range*cosX;
			float dy = range*sinY;
			l.x = l.x+dx;
			l.y = l.y+dy;
		}
		return false;
	}
	
	public void setMoveDistanceAndC(){
		if( selected==false ){
			if(mT == Move.plane){
				moveDistance =l.x - (refer.x + limitA); 
			}
			else if(mT == Move.vertical){
				moveDistance =l.y - (refer.y + limitA); 
			}
		}
		
		if(mT == Move.plane){
			a = startPoint.x+moveDistance - l.x;
			b = startPoint.y - l.y;
		}
		else if(mT == Move.vertical){
			a = startPoint.x - l.x;
			b = startPoint.y + moveDistance  - l.y;
		}
		
	    c = (float) Math.sqrt( a*a+b*b );
		setRange();//ֻ�ڸ����ƶ��ͷ����һ��range��
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
