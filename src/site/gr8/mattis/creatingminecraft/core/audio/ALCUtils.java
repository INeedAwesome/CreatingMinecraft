package site.gr8.mattis.creatingminecraft.core.audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ALCUtils {

    static ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) {
        ByteBuffer vorbis;
        try {
            vorbis = ioResourceToByteBuffer(resource, bufferSize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IntBuffer error = BufferUtils.createIntBuffer(1);
        long decoder = STBVorbis.stb_vorbis_open_memory(vorbis, error, null);
        if (decoder == MemoryUtil.NULL) {
            throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
        }

        STBVorbis.stb_vorbis_get_info(decoder, info);

        int channels = info.channels();

        ShortBuffer pcm = BufferUtils.createShortBuffer(STBVorbis.stb_vorbis_stream_length_in_samples(decoder) * channels);

        STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm);
        STBVorbis.stb_vorbis_close(decoder);

        return pcm;
    }

    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    ;
                }
            }
        } else {
            try (
                    InputStream source = ALCUtils.class.getClassLoader().getResourceAsStream(resource);
                    ReadableByteChannel rbc = Channels.newChannel(source)
            ) {
                buffer = BufferUtils.createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); // 50%
                    }
                }
            }
        }

        buffer.flip();
        return MemoryUtil.memSlice(buffer);
    }


    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }


}
