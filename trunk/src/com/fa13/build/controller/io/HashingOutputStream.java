package com.fa13.build.controller.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class HashingOutputStream extends BufferedOutputStream {
	private byte hash = 0;
	public static Charset WIN_CHARSET = Charset.forName("Cp1251");
	public static byte[] WIN_LINE_BREAK = "\r\n".getBytes(WIN_CHARSET);
	

	public HashingOutputStream(OutputStream out) {
		super(out);
	}
	
	public HashingOutputStream(OutputStream out, int size) {
		super(out, size);
	}
	
	public synchronized void write(int b) throws IOException {
		super.write(b);
		hash ^= (byte)b;
	}
	
	public synchronized void write(byte[] b, int off, int len) throws IOException {
		super.write(b, off, len);
		for (int i = off, j = 0; j < len; i++, j++) {
			hash ^= b[i];
		}
	}		

	public void write(byte[] b) throws IOException {		
		super.write(b);
		for (byte curr: b) {
			hash ^= curr;
		}
	}

	public void close() throws IOException {		
		close(false);
	}
	
	public void close(boolean writeHash) throws IOException {
		if (writeHash) {
			super.write(hash);
		}
		super.close();
	}
}
