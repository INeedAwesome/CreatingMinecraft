package site.gr8.mattis.creatingminecraft.world;

public class Chunk {

    private final int CHUNK_WIDTH = 16;
	private final int CHUNK_HEIGHT = 256;
	private final int CHUNK_DEPTH = 16;
	private final int CHUCK_VOLUME = CHUNK_WIDTH * CHUNK_HEIGHT * CHUNK_DEPTH;

	public void someMethod(){}


	public int getCHUNK_WIDTH() {
		return CHUNK_WIDTH;
	}

	public int getCHUNK_HEIGHT() {
		return CHUNK_HEIGHT;
	}

	public int getCHUNK_DEPTH() {
		return CHUNK_DEPTH;
	}

	public int getCHUCK_VOLUME() {
		return CHUCK_VOLUME;
	}
}
