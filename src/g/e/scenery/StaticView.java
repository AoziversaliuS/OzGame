package g.e.scenery;

import g.tool.OzPicture;

public class StaticView extends View{

	/**
	 * ��ȫ�̶�����Ļ�ϵľ���
	 * */
	public StaticView(String Tag, float x, float y, OzPicture viewPic) {
		super(Tag, x, y, viewPic);
		this.planeLogicUsed = false;
		this.verticalLogicUsed = false;
		this._pushBackAvailable = false;
	}

	@Override
	public boolean rollBack() {
		//��Ϊ staticView���μ�roolBack�������һֱ����false
		return false;
	}
	
	

}
