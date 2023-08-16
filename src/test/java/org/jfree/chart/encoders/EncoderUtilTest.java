package org.jfree.chart.encoders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.junit.jupiter.api.Assertions.*;

class EncoderUtilTest {
    private BufferedImage bufferedImage;
    private ByteArrayOutputStream baos;
    private final String defaultBufferValue = "test";
    private final String defaultFormat = "jpeg";
    private final String defaultInvalidFormat = "test";
    private final float defaultQualityLevel = 0.4f;

    @BeforeEach
    void setUp() throws IOException {
        bufferedImage = new BufferedImage(1, 1, TYPE_INT_RGB);
        baos = new ByteArrayOutputStream();
        baos.write(defaultBufferValue.getBytes());
    }

    @Test
    void encodeThrowExceptionWhenRequiredInputsAreNull(){
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(null, null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(bufferedImage, null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(bufferedImage, "pippo")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(null, null, 0f)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(null, null, true));

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(bufferedImage, null, true)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(null, null, defaultQualityLevel, true)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(null, null, defaultQualityLevel, false)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(bufferedImage, null, defaultQualityLevel, true)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(bufferedImage, null, defaultQualityLevel, false)
        );
    }

    @Test
    void throwsExceptionIfFormatIsInvalid(){
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.encode(bufferedImage, defaultInvalidFormat, defaultQualityLevel, false)
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
        byte[] encoded = EncoderUtil.encode(bufferedImage, defaultFormat, true);
        assertNotNull(encoded);
    }

    @Test
    void canEncodeWithoutAlphaTransparency() throws IOException {
        byte[] encoded = EncoderUtil.encode(bufferedImage, defaultFormat, false);
        assertNotNull(encoded);
    }

    @Test
    void canEncodeWithQualityLevel() throws IOException {
        byte[] encoded = EncoderUtil.encode(bufferedImage, defaultFormat, defaultQualityLevel);
        assertNotNull(encoded);
    }

    @Test
    void canEncodeWithQualityAndTransparency() throws IOException {
        byte[] encoded = EncoderUtil.encode(bufferedImage, defaultFormat, defaultQualityLevel, true);
        assertNotNull(encoded);
    }

    @Test
    void canEncodeWithQualityButWithoutTransparency() throws IOException {
        byte[] encoded = EncoderUtil.encode(bufferedImage, defaultFormat, defaultQualityLevel, false);
        assertNotNull(encoded);
    }

    @Test
    void writeBufferedImage_throwsExceptionWithAllNulls(){
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(null, null, null));

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(null, null, null, defaultQualityLevel)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(null, null, null, true)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(null, null, null, true)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(null, null, null,defaultQualityLevel, true)
        );
    }

    @Test
    void writeBufferedImage_throwsExceptionWhenFormatIsNull(){
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(bufferedImage, null, null));

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(bufferedImage, null, null, defaultQualityLevel)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(bufferedImage, null, null,defaultQualityLevel, true)
        );
    }

    @Test
    void writeBufferedImage_throwsExceptionWhenOutputStreamIsNull(){
        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, null, defaultQualityLevel)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, null));

        assertThrows(
                IllegalArgumentException.class,
                () -> EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, null,defaultQualityLevel, true)
        );
    }

    @Test
    void testWriteBufferedImage() throws IOException {
        EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, baos);
        assertTrue(baos.toString().contains(defaultBufferValue));
    }

    @Test
    void canWriteBufferedImageWithQuality() throws IOException {
        EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, baos, defaultQualityLevel);
        assertTrue(baos.toString().contains(defaultBufferValue));
    }

    @Test
    void canWriteBufferedImageWithTransparency() throws IOException {
        EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, baos, true);
        assertTrue(baos.toString().contains(defaultBufferValue));
    }

    @Test
    void canWriteBufferedImageWithoutTransparency() throws IOException {
        EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, baos, false);
        assertTrue(baos.toString().contains(defaultBufferValue));
    }

    @Test
    void canWriteBufferedImageWithQualityAndTransparency() throws IOException {
        EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, baos, defaultQualityLevel, true);
        assertTrue(baos.toString().contains(defaultBufferValue));
    }

    @Test
    void canWriteBufferedImageWithQualityButWithoutTransparency() throws IOException {
        EncoderUtil.writeBufferedImage(bufferedImage, defaultFormat, baos, defaultQualityLevel, false);
        assertTrue(baos.toString().contains(defaultBufferValue));
    }
}
