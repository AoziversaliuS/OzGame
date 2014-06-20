package g.e.architecture;

import g.refer.BasicBody;
import g.refer.Player;
import g.tool.OzPoint;
import g.tool.OzRect;
import g.tool.P;
import g.type.ET;
import g.type.Rank;

public class DeadLine extends BasicBody{
		
	public DeadLine(float y) {
		super("DeadLine", Rank.SELF_CUSTOM, ET.DeadLine, new OzPoint(0, y),new OzRect(0, 0, 0,0));
	}
	@Override
	public void logic() {
	}
	@Override
	public void draw() {
		
	}
	@Override
	public void impact(Player player) {
		if(l.y > Player.getL().y ){
			//���������߱��������״̬Ϊ����
			if( Player.getCondition()==Player.ALIVE ){
				//��Ϊ��2����ײ���,
				//��1����ײ�����������״̬������ʼ,Ȼ����ҽ���������,��2����ײ������������״̬Ϊ������ʼ,�Դ˽�������ѭ����
				Player.setCondition(Player.DEAD_START);
			}
		}
	}

	@Override
	public void prepare() {
	}
	@Override
	public void reset() {
	}
}
