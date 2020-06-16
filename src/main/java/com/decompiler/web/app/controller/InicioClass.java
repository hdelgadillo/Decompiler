package com.decompiler.web.app.controller;




import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		String resources =proyecto+File.separator+"src";
		String javaP = resources + File.separator+"main";
		String template = javaP + File.separator+"resources";
		String javaJ = javaP + File.separator+"java";
		String metaI = inicio + File.separator + "WEB-INF"+File.separator+"classes";
		String pom = inicio + File.separator + "META-INF";
		String org = inicio + File.separator+"com";
		String comando = "java -jar "+miDir.getCanonicalPath()+File.separator+"lib"+File.separator+"decompiler.jar -jar "+builder.toString()+" -o "+inicio;
		
		File ArchivoP = new File(proyecto);
		File ArchivoR = new File(resources);
		File ArchivoJ = new File(javaP);
		File ArchivoJC = new File(javaJ);
		File ArchivoT = new File(template);
		
		attributes.addFlashAttribute("message", "decompilacion exitosa  { " +comando);
		
		ArchivoP.mkdir();
		ArchivoR.mkdir();
		ArchivoJ.mkdir();
		ArchivoJC.mkdir();
		ArchivoT.mkdir();
		
		
		
		utilZip.decompiler(comando);
		utilZip.unzip(inicio,builder.toString());
		utilZip.BuscarR(metaI,template);
		utilZip.Bpom(pom, proyecto);
		utilZip.CopySRC(org,javaJ);
		
	

		return "redirect:/status";
	}
	
	@GetMapping("/status")
	public String status() {
		return "status";
	}
}