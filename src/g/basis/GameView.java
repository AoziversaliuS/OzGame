package g.basis;

import g.tool.P;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameView implements ApplicationListener {
	
	Sprite sprite;
	@Override
	public void create() {	
		P.init(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //资源初始化
		TextureAtlas atlas= new TextureAtlas(Gdx.files.internal("data/Oz.atlas"));
		sprite = new Sprite( atlas.findRegion("A"));
	}



	@Override
	public void render() {	
		Gdx.app.log("FPS", " FPS:  "+Gdx.graphics.getFramesPerSecond());
		
		engine();
		showGraphic();
	}
	public void engine(){
		
	}
	public void showGraphic(){
		P.begin();
		
		P.draw(0, 0, sprite);
		
		P.end();
	}

	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	public void dispose() {
		P.dispose();
	}
}
