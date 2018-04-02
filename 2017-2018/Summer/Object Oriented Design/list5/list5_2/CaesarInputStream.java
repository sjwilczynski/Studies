package list5.list5_2;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CaesarInputStream {
    private InputStream is;
    private int shift;

    CaesarInputStream(InputStream is, int shift) {
        this.is = is;
        this.shift = shift;
    }

    public int read(byte[] bytes, int offset, int count) throws IOException {
        byte[] tmp = new byte[count];
        int bytesRead = is.read(tmp, 0, count);
        for (int j = 0; j < bytesRead; j++) {
            bytes[offset + j] = (byte) (tmp[j] + shift);
        }
        return bytesRead;
    }
}

class CaesarOutputStream {
    private OutputStream os;
    private int shift;

    CaesarOutputStream(OutputStream os, int shift) {
        this.os = os;
        this.shift = shift;
    }

    public void write(byte[] bytes, int offset, int count) throws IOException {
        byte[] tmp = new byte[count];
        for (int j = 0; j < count; j++) {
            tmp[j] = (byte) (bytes[offset + j] + shift);
        }
        os.write(tmp, 0, count);
    }
}


