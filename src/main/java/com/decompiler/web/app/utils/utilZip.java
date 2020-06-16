package com.decompiler.web.app.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.PropertySource.Comparator;

import java.io.FilenameFilter;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class utilZip {

	public static void unzip(String path, String archivo ) throws ZipException{
	
		
		ZipFile zipFile =new ZipFile(archivo);
		zipFile.extractAll(path);

	}
	
	
	public static void BuscarR(String origen, String destino) throws IOException {
		
	
		
		File srcDir = new File(origen);
		File destDir = new File(destino);

		
	 FileUtils.copyDirectory(srcDir, destDir);
	 
	 
	
	 
		
	}
	
	public static void Bpom(String origen, String destino) throws IOException {
		
		File destpom = new File(destino+File.separator+"pom.xml");
		Files.walk(Paths.get(origen))
        .filter(Files::isRegularFile)
        .forEach((f)->{
            String file = f.toString();
            if( file.endsWith("pom.xml"))
                System.out.println(file + " found!");   
            File filesrc = new File(file);
            
            try {
				FileUtils.copyFile(filesrc,destpom);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		
	}
	
	
	public static void CopySRC(String org, String proyecto) throws IOException {
		
		File srcDir = new File(org);
		File destDir = new File(proyecto+File.separator+"com");
		System.out.println(proyecto+File.separator+"com");
		
	 FileUtils.copyDirectory(srcDir, destDir);
	}
	
	public static void decompiler(String comando) throws IOException {
		
		Runtime.getRuntime().exec(comando);
		
	}

	
}
