package com.decompiler.web.app.controller;




import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.decompiler.web.app.utils.UtilJar;
import com.decompiler.web.app.utils.utilZip;

import net.lingala.zip4j.exception.ZipException;


@Controller
public class InicioClass {
	int hora, minutos, segundos, anio,dia,mes;
	@GetMapping("/")
	public String index() {
		return "upload";
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes)
			throws IOException, ZipException {

		if (file == null || file.isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo");
			return "redirect:status";
		}
		String inicio = System.getProperty("user.home")+File.separator+"spring_upload_example";
		File miDir = new File (".");
		File archivo = new File(inicio);
		if (!archivo.exists()) {
		    archivo.mkdir();
		}
		
		Calendar calendario = Calendar.getInstance();
		dia =calendario.get(Calendar.DAY_OF_MONTH);
		mes =calendario.get(Calendar.MONTH);
		anio = calendario.get(Calendar.YEAR);
		hora =calendario.get(Calendar.HOUR_OF_DAY);
		minutos = calendario.get(Calendar.MINUTE);
		segundos = calendario.get(Calendar.SECOND);
		
		
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(System.getProperty("user.home"));
		builder.append(File.separator);
		builder.append("spring_upload_example");
		builder.append(File.separator);
		builder.append(file.getOriginalFilename());
		

		byte[] fileBytes = file.getBytes();
		Path path = Paths.get(builder.toString());
		Files.write(path, fileBytes);
		
		String proyecto = inicio+File.separator+anio+"-"+mes+"-"+dia+"-"+hora+"-"+minutos+"-"+segundos;
		String tmp = proyecto+"tmp";
		String resources =proyecto+File.separator+"src";
		String javaP = resources + File.separator+"main";
		String template = javaP + File.separator+"resources";
		String javaJ = javaP + File.separator+"java";
		String javaJ1 = javaP + File.separator+"java"+File.separator+"com";
		String metaI = tmp + File.separator + "WEB-INF";
		String tmpJar = tmp+ File.separator+"com";
		String Classes = metaI+File.separator+"classes";
		String pom = tmp + File.separator + "META-INF";
		String pom2 = template+File.separator+"META-INF";
		String jarDeco=tmp+File.separator+file.getOriginalFilename();
		
		String comando = "java -jar "+miDir.getCanonicalPath()+File.separator+"lib"+File.separator+"proc.jar -jar "+builder.toString()+" -o "+tmp;
		
		
		
		
		String comando2= "java -jar "+miDir.getCanonicalPath()+File.separator+"lib"+File.separator+"decompiler.jar  "+tmpJar+"   "+javaJ;
		
		File ArchivoP = new File(proyecto);
		File ArchivoR = new File(resources);
		File ArchivoJ = new File(javaP);
		File ArchivoJC = new File(javaJ);
		File ArchivoT = new File(template);
		File tmpA = new File(tmp);
		
		String ext2 = FilenameUtils.getExtension(builder.toString()); // returns "exe"
		System.out.println(ext2);
		ArchivoP.mkdir();
		ArchivoR.mkdir();
		ArchivoJ.mkdir();
		ArchivoJC.mkdir();
		ArchivoT.mkdir();
		tmpA.mkdir();
		
		
		
		if(ext2.equals("war")) {
		
	
		
		utilZip.decompiler(comando);
		
		utilZip.unzip(tmp,builder.toString());
		
		
		utilZip.BuscarR(Classes,template);
		utilZip.Bpom(pom, proyecto);
		utilZip.CopySRC(tmpJar, javaJ);
		
		
	

		
		}else if(ext2.equals("jar")) {
				//utilZip.decompiler(comando);
				

				utilZip.unzip(tmp,builder.toString());
				
				//UtilJar.jarUnzip(template, builder.toString());
			    utilZip.BuscarR(tmp,template);
				utilZip.Bpom(pom, proyecto);
				utilZip.decompiler(comando2);
			}	
	
		else if(ext2.equals("ear")) {
				
			}
		else {
			System.out.println("ear");
		}
attributes.addFlashAttribute("message", "decompilacion exitosa  { " +jarDeco);
		
		return "redirect:/status";
		
	}
	
	@GetMapping("/status")
	public String status() {
		return "status";
	}
}