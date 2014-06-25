package g.e.architecture;

import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPicture;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.tool.Res;
import g.type.ET;
import g.type.Rank;

public class Thorn extends BasicBody {

	private int length;
	public static final int STYLE_PLANE_UP = 100,
			                STYLE_PLANE_DOWN=101,
							STYLE_VERTICAL_LEFT=102,
							STYLE_VERTICAL_RIGHT=103;
	
	/**刺的类型*/
	private int style;
	
	/**图片的旋转角度   STYLE_PLANE_UP的角度为0度*/
	private float degrees;
//	/**
//	 * 单个的刺
//	 * */
//	public Thorn(String Tag, OzPoint l, OzRect entityOffset) {
//		super(Tag, Rank.BUILD_1, ET.Thorn, l, entityOffset);
//	}
	
	/**
	 * 单个的刺
	 * */
	public Thorn(String Tag,float x,float y,int style){
		this(Tag,x,y,1,style);
//		super(Tag, Rank.BUILD, ET.Thorn, new OzPoint(x, y),new OzRect(0, 0, Res.thorn.getWidth(),Res.thorn.getHeight()));
	}
	/**
	 * 一排刺
	 * @param length 刺的个数
	 * @param style  STYLE_PLANE_UP, STYLE_PLANE_DOWN, STYLE_VERTICAL_LEFT, STYLE_VERTICAL_RIGHT
	 * */
	public Thorn(String Tag,float x,float y,int length,int style){
		
		float width = getWidth(Res.build_thorn, length, style);
		float height = getHeight(Res.build_thorn, length, style);
		
		init(Tag, Rank.BUILD_1, ET.Thorn,new OzPoint(x, y),
				new OzRect(0, 0,width,height));
		
		this.length = length;
		this.style  = style;
		this.setDegrees(style);
	}
	
	@Override
	public void reset() {
	}
	
	
	

	@Override
	public void logic() {
	}

	@Override
	public void draw() {
		
		if(style==STYLE_PLANE_UP || style==STYLE_PLANE_DOWN){
			for(int i=0;i<length;i++){
				P.draw(degrees,Res.build_thorn.getWidth()*i+l.x,l.y,  Res.build_thorn);
			}
		}
		else if(style==STYLE_VERTICAL_LEFT || style==STYLE_VERTICAL_RIGHT){
			for(int i=0;i<length;i++){
				P.draw(degrees, l.x,Res.build_thorn.getHeight()*i+l.y,  Res.build_thorn);
			}
		}
	}

	@Override
	public void impact(Player player) {
		if(hit(player) && Player.getCondition()==Player.ALIVE ){
			Player.setCondition(Player.DEAD_START);
		}
	}
	
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 获取刺条的宽度
	 * */
	private static float getWidth(OzPicture pic,int length,int style){
		if(style==STYLE_PLANE_UP || style==STYLE_PLANE_DOWN){
			return pic.getWidth()*length;
		}
		return pic.getWidth();
	}
	/**
	 * 获取刺条的高度
	 * */
	private static float getHeight(OzPicture pic,int length,int style){
		if(style==STYLE_VERTICAL_LEFT || style==STYLE_VERTICAL_RIGHT){
			return pic.getHeight()*length;
		}
		return pic.getHeight();
	}
	/**
	 * 设置刺的图片的旋转角度
	 * STYLE_PLANE_UP,STYLE_PLANE_DOWN,STYLE_VERTICAL_LEFT,STYLE_VERTICAL_RIGHT*/
	private void setDegrees(int style) {
		switch (style) {
		
		case       STYLE_PLANE_UP: this.degrees =   0f; break;
		case  STYLE_VERTICAL_LEFT: this.degrees =  90f; break;
		case     STYLE_PLANE_DOWN: this.degrees = 180f; break;
		case STYLE_VERTICAL_RIGHT: this.degrees = 270f; break;

		}
	}
	

}
