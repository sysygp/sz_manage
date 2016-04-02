package com.zhrt.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class EU {

	private static EU instance = null;

    public static EU getInstance() {
		if (instance == null) {
			instance = new EU();
		}
		return instance;
	}

	private EU() {

	}

	public byte[] encode(byte[] data) {
		byte[] buf = null;
		int keyIndex = (int) (Math.random() * 15);
		int keyLen = 3 + (int) (Math.random() * 12);
		byte[] keys = generateKey(keyLen);

		int head = keyIndex << 4 | keyLen;

		byte[] encodeBodys = encodeBody(keys, data);
		byte[] encodedKeys = encodeKey(keys);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeByte(head);
			if (keyIndex > 0) {
				byte[] temp = new byte[keyIndex];
				temp[0] = 8;
				dos.write(temp);
			}
			dos.write(encodedKeys);
			dos.write(encodeBodys);
			buf = baos.toByteArray();

			dos.close();
			dos = null;
			baos.close();
			baos = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buf;
	}

	public byte[] decode(byte[] data) {
		int index = 0;
		int head = data[index++] & 0xff;
		int keyIndex = head >> 4;
		int keyLen = head & 0x0f;
		index += keyIndex;
		byte[] encodeKeys = new byte[keyLen];
		System.arraycopy(data, index, encodeKeys, 0, keyLen);
		index += keyLen;
		int bodySize = data.length - index;
		byte[] encodeBodys = new byte[bodySize];
		System.arraycopy(data, index, encodeBodys, 0, bodySize);
		index += bodySize;

		byte[] keys = decodeKey(encodeKeys);
		byte[] bodys = decodeBody(keys, encodeBodys);
		return bodys;
	}

	private byte[] generateKey(int keyLen) {
		byte[] keys = new byte[keyLen];
		for (int i = 0; i < keyLen; i++) {
			keys[i] = (byte) (Math.random() * 255);
		}
		return keys;
	}

	private byte[] encodeKey(byte[] bys) {
		int size = bys.length;
		byte[] temp = new byte[size];
		for (int i = 0; i < size; i++) {
			temp[i] = (byte) (((bys[i] & 0xff) >> 5) | ((bys[i] & 0xff) << 3));
		}
		return temp;
	}

	private byte[] decodeKey(byte[] bys) {
		int size = bys.length;
		byte[] temp = new byte[size];
		for (int i = 0; i < size; i++) {
			temp[i] = (byte) (((bys[i] & 0xff) >> 3) | ((bys[i] & 0xff) << 5));
		}
		return temp;
	}

	private byte[] encodeBody(byte[] keys, byte[] bodys) {
		int bodySize = bodys.length;
		int keySize = keys.length;
		byte[] temp = new byte[bodySize];
		for (int i = 0, j = 0; i < bodySize; i++) {
			temp[i] = (byte) ((bodys[i] & 0xff) ^ (keys[j] & 0xff));
			j++;
			if (j == keySize) {
				j = 0;
			}
		}
		return temp;
	}

	private byte[] decodeBody(byte[] keys, byte[] bodys) {
		int bodySize = bodys.length;
		int keySize = keys.length;
		byte[] temp = new byte[bodySize];
		for (int i = 0, j = 0; i < bodySize; i++) {
			temp[i] = (byte) ((bodys[i] & 0xff) ^ (keys[j] & 0xff));
			j++;
			if (j == keySize) {
				j = 0;
			}
		}
		return temp;
	}
}
