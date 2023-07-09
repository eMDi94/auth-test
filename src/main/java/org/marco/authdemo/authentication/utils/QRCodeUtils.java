package org.marco.authdemo.authentication.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class QRCodeUtils {

    public static byte[] generateQRCode(@NonNull String content, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter
                .encode(content, BarcodeFormat.QR_CODE,
                        width, height);
        ByteArrayOutputStream pngOutputStream =
                new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig();
        MatrixToImageWriter.writeToStream(bitMatrix,
                "PNG", pngOutputStream, con);
        byte[] pngData = pngOutputStream.toByteArray();

        return pngData;
    }

    public static byte[] generateQRCode(byte @NonNull [] content, int width, int height) throws WriterException, IOException {
        String contentString = new String(content, StandardCharsets.UTF_8);
        return generateQRCode(contentString, width, height);
    }

}
