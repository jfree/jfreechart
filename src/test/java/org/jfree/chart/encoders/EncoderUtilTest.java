package org.jfree.chart.encoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EncoderUtilTest {
    private BufferedImage bufferedImage;
    private String defaultFormat = "jpeg";

    @BeforeEach
    void setUp() {
        bufferedImage = new BufferedImage(1, 1, TYPE_INT_RGB);
    }

    @Test()
    void throwExceptionWithNullInputs() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(null, null)
        );
    }

    @Test()
    void throwExceptionWhenImageFormatIsNotSpecified() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(bufferedImage, null)
        );
    }

    @Test
    void throwExceptionWithInvalidImageFormat() throws IOException {
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(bufferedImage, "pippo")
        );
    }

    @Test
    void throwExceptionWithAllNulls() throws IOException {
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(null, null, true));
    }

    @Test
    void throwExceptionWhenFormatIsNull() throws IOException {
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(bufferedImage, null, true)
        );
    }

    @Test
    void throwExceptionWhenAllNulls(){
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(null, null, 0f)
        );
    }

    @Test
    void canDecodeValidImageFormats() throws IOException {
        canEncode("jpg");
        canEncode("jpeg");
        canEncode("png");
    }

    private void canEncode(String format) throws IOException {
        byte[] result = EncoderUtil.encode(bufferedImage, format);
        assertNotNull(result);
    }

    @Test
    void canEncodeWithAlphaTransparency() throws IOException {
        byte[] encoded = EncoderUtil.encode(bufferedImage, "jpg", true);
        assertNotNull(encoded);
    }

    @Test
    void canEncodeWithoutAlphaTransparency() throws IOException {
        byte[] encoded = EncoderUtil.encode(bufferedImage, "jpg", false);
        assertNotNull(encoded);
    }

    @Test
    void canEncodeWithQualityLevel() throws IOException {
        byte[] encoded = EncoderUtil.encode(bufferedImage, "jpg", 0.4f);
        assertNotNull(encoded);
    }

    @Test
    void writeBufferedImage_throwExceptionWithAllNulls() throws IOException {
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(null, null, null));
    }

    @Test
    void writeBufferedImage_throwExceptionWithNullFormat(){
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(bufferedImage, null, null));
    }

    @Test
    void writeBufferedImage_throwExceptionWhenNullOutputStream() throws IOException {
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, null));
    }

    @Test
    void testWriteBufferedImage() throws IOException {
        EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, new ByteArrayOutputStream());
    }


}
