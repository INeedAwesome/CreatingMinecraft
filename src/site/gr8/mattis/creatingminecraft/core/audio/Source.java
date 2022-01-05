package site.gr8.mattis.creatingminecraft.core.audio;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

public class Source {

    private int sourceID;

    public Source() {
        sourceID = AL10.alGenSources();
        AL10.alDistanceModel(AL11.AL_EXPONENT_DISTANCE);
        AL10.alSourcef(sourceID, AL10.AL_REFERENCE_DISTANCE, 2);
        AL10.alSourcef(sourceID, AL10.AL_ROLLOFF_FACTOR, 4);
        AL10.alSourcef(sourceID, AL10.AL_MAX_DISTANCE, 15);
    }

    public void pause() {
        AL10.alSourcePause(sourceID);
    }

    public void continuePlaying() {
        AL10.alSourcePlay(sourceID);
    }

    public void stop() {
        AL10.alSourceStop(sourceID);
    }

    public void play(int buffer) {
        stop();
        AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer);
        AL10.alSourcePlay(sourceID);
    }
    public void delete() {
        stop();
        AL10.alDeleteSources(sourceID);
    }

    public boolean isPlaying() {
        return AL10.alGetSourcei(sourceID, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public void setVelocity(float x, float y, float z) {
        AL10.alSource3f(sourceID, AL10.AL_VELOCITY, x, y, z);
    }

    public void setLooping(boolean loop) {
        AL10.alSourcei(sourceID, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public void setVolume(float volume) {
       AL10.alSourcef(sourceID, AL10.AL_GAIN, volume);
    }

    public void setPitch(float pitch) {
        AL10.alSourcef(sourceID, AL10.AL_PITCH, pitch);
    }

    public void setPosition(float x, float y, float z) {
        AL10.alSource3f(sourceID, AL10.AL_POSITION, x, y, z);
    }

}
