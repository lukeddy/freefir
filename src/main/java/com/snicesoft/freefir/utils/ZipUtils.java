package com.snicesoft.freefir.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtils {
	@SuppressWarnings("resource")
	public static InputStream getFileInputStream(String zipPath, String entryPath) throws IOException {
		return new ZipFile(zipPath).getInputStream(new ZipEntry(entryPath));
	}

	@SuppressWarnings("resource")
	public static InputStream getFileInputStream(File zip, String entryPath) throws IOException {
		return new ZipFile(zip.getAbsolutePath()).getInputStream(new ZipEntry(entryPath));
	}

	public static byte[] getBytes(String zipPath, String entryPath) throws IOException {
		InputStream in = getFileInputStream(zipPath, entryPath);
		byte[] data = new byte[in.available()];
		in.read(data);
		in.close();
		return data;
	}

	public static byte[] getBytes(File zip, String entryPath) throws IOException {
		return getBytes(zip.getAbsolutePath(), entryPath);
	}
}
