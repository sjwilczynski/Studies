package list5.list5_2;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CaesarStringTest {
    @Test
    void caesarStringTest() throws IOException {
        String original = "tekst";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        CaesarOutputStream cos = new CaesarOutputStream(os, 4);
        byte[] toWrite = original.getBytes();
        cos.write(toWrite, 0, toWrite.length);

        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        CaesarInputStream cis = new CaesarInputStream(is, -4);
        byte[] toRead = new byte[toWrite.length];
        cis.read(toRead, 0, toWrite.length);

        String res = new String(toRead, "UTF-8");
        assertEquals(res, original);

    }
}