package com.platform.util;

import java.awt.image.BufferedImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * @author Muhil
 */
public class BarCodeUtil {

	public static BufferedImage generateEAN13BarcodeImage(String barcodeText) throws Exception {
		EAN13Writer barcodeWriter = new EAN13Writer();
		BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_13, 300, 150);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	public static BufferedImage generateEAN8BarcodeImage(String barcodeText) throws Exception {
		EAN13Writer barcodeWriter = new EAN13Writer();
		BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_8, 300, 150);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	public static BufferedImage generateBarcodeImage(String barcodeText) throws Exception {
		EAN13Writer barcodeWriter = new EAN13Writer();
		BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, 300, 150);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
		QRCodeWriter barcodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

}
