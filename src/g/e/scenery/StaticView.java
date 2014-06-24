package g.e.scenery;

import g.tool.OzPicture;

public class StaticView extends View{

	/**
	 * 完全固定在屏幕上的景物
	 * */
	public StaticView(String Tag, float x, float y, OzPicture viewPic) {
		super(Tag, x, y, viewPic);
		this.planeLogicUsed = false;
		this.verticalLogicUsed = false;
		this._pushBackAvailable = false;
	}

	@Override
	public boolean rollBack() {
		//因为 staticView不参加roolBack活动，所以一直返回false
		return false;
	}
	
	

}
