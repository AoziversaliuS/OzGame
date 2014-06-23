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
	public static final int STYLE_PLANE = 100,STYLE_VERTICAL=101;
	private int style = STYLE_PLANE;
	/**
	 * 单个的刺
	 * */
	public Thorn(String Tag, OzPoint l, OzRect entityOffset) {
		super(Tag, Rank.BUILD_1, ET.Thorn, l, entityOffset);
	}
	/**
	 * 单个的刺
	 * */
	public Thorn(String Tag,float x,float y){
		this(Tag,x,y,1,STYLE_PLANE);
//		super(Tag, Rank.BUILD, ET.Thorn, new OzPoint(x, y),new OzRect(0, 0, Res.thorn.getWidth(),Res.thorn.getHeight()));
	}
	/**
	 * 一排刺
	 * @param length 刺的个数
	 * */
	public Thorn(String Tag,float x,float y,int length,int style){
		super(
				Tag,
				Rank.BUILD_1,
				ET.Thorn,
				new OzPoint(x, y),
				new OzRect(
						0, 
						0,
						getWidth(Res.build_thorn, length, style),
						getHeight(Res.build_thorn, length, style)
						)
			);
		this.length = length;
		this.style  = style;
	}
	

	
	
	

	@Override
	public void logic() {
	}

	@Override
	public void draw() {
//		P.draw(l, Res.thorn);
		for(int i=0;i<length;i++){
			P.draw(l.x+Res.build_thorn.getWidth()*i,l.y, Res.build_thorn);
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
	@Override
	public void reset() {
	}
	/**
	 * 获取刺条的宽度
	 * */
	private static float getWidth(OzPicture pic,int length,int style){
		if(style==STYLE_PLANE){
			return pic.getWidth()*length;
		}
		return pic.getWidth();
	}
	/**
	 * 获取刺条的高度
	 * */
	private static float getHeight(OzPicture pic,int length,int style){
		if(style==STYLE_VERTICAL){
			return pic.getHeight()*length;
		}
		return pic.getHeight();
	}


}
