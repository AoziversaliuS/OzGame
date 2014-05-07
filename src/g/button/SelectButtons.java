package g.button;

import g.refer.OzElement;
import g.refer.Player;
import g.type.ET;
import g.type.Rank;

public class SelectButtons extends OzElement{

	public SelectButtons() {
		super("SelectButtons", Rank.SELF_CUSTOM, ET.SelectButtons, null, null);
	}

	@Override
	public void reset() {
	}

	@Override
	public void logic() {
	}

	@Override
	public void draw() {
	}

	@Override
	public void impact(Player player) {
	}

	@Override
	public void prepare() {
	}

	@Override
	public boolean rollBack() {
		return false;
	}

}
