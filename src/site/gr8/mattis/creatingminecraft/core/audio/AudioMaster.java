package site.gr8.mattis.creatingminecraft.core.audio;

import org.joml.Vector3f;
import org.lwjgl.openal.*;
import org.lwjgl.stb.STBVorbisInfo;
import site.gr8.mattis.creatingminecraft.core.logger.Logger;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

public class AudioMaster {

    private static final Logger LOGGER = Logger.get();
    private static List<Integer> buffers = new ArrayList<>();

    private static long context;
    private static long device;

    public static void init() {
        LOGGER.info("Initializing OpenAL!");
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        if (device == 0)
            throw new IllegalStateException("Failed to open an openAL device!");
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        if (!deviceCaps.ALC_EXT_EFX) {
            ALC10.alcCloseDevice(device);
            throw new IllegalStateException("No EXTEfx supported by driver. ");
        }
        LOGGER.info("EXTEfx found.");
        context = ALC10.alcCreateContext(device, (IntBuffer) null);
        boolean useTLC = deviceCaps.ALC_EXT_thread_local_context && EXTThreadLocalContext.alcSetThreadContext(context);
        if (!useTLC)
            if (!ALC10.alcMakeContextCurrent(context))
                throw new IllegalStateException();
        AL.createCapabilities(deviceCaps);
    }

    public static void setListenerPosition(float x, float y, float z) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
    }

    public static void setListenerPosition(Vector3f vector) {
        AL10.alListener3f(AL10.AL_POSITION, vector.x, vector.y, vector.z);
    }

    public static void setOrientation(float x, float y, float z) {
        AL10.alListener3f(AL10.AL_ORIENTATION, x, y, z);
    }

    public static void setOrientation(Vector3f vector) {
        AL10.alListener3f(AL10.AL_ORIENTATION, vector.x, vector.y, vector.z);
    }
    public static int loadSound(String file) {
        int buffer = AL10.alGenBuffers();
        buffers.add(buffer);
        try (STBVorbisInfo info = STBVorbisInfo.malloc()) {
            ShortBuffer pcm = ALCUtils.readVorbis(file, 32 * 1024, info);
            AL10.alBufferData(buffer, info.channels() == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16, pcm, info.sample_rate());
        }
        return buffer;
    }

    public static void cleanUp() {
        for (int buffer : buffers) {
            AL10.alDeleteBuffers(buffer);
        }
        ALC10.alcDestroyContext(context);
        ALC10.alcCloseDevice(device);
    }

}
