package g.e.view;

import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPicture;
import g.tool.OzPoint;
import g.tool.P;
import g.type.ET;
import g.type.Rank;

public class AnimationView extends BasicBody{
	
	int timeCount;
	final int maxTime;//每一帧耗时约为 maxTime*0.016
	int index;        //要显示的那一帧的图片数组下标
	OzPicture[] pics;
	
	public AnimationView(String Tag,float x,float y,OzPicture[] pics,int maxTime) {
		super(Tag, Rank.VIEW_2, ET.View, new OzPoint(x, y), null);
		this.maxTime = maxTime;
		timeCount = 0;
		index = 0;
		this.pics = pics;
		//asd
	}

	@Override
	public void logic() {
		timeCount++;
		if( timeCount>=maxTime ){
			timeCount = 0;//重置计时器
			index++;
			if( index>=pics.length ){
				index = 0;//图片数组下标越界则重新回到0;
			}
		}
	}

	@Override
	public void draw() {
		P.draw(l, pics[index],P.AUTO_RATIO);
	}

	@Override
	public void impact(Player player) {
	}

	@Override
	public void prepare() {
	}

	@Override
	public void reset() {
	}

}
