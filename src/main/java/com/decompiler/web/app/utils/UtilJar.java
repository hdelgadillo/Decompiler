package com.decompiler.web.app.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class UtilJar {

	
	public static void jarUnzip(String path, String archivo) throws ZipException{
		

		ZipFile zipFile =new ZipFile(archivo);
		zipFile.extractAll(path);

		
	}
}
