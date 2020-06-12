package com.decompiler.web.app.controller;




import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class InicioClass {
	
	@GetMapping("/")
	public String index() {
		return "upload";
	}

	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes)
			throws IOException {

		if (file == null || file.isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo");
			return "redirect:status";
		}
		
		File miDir = new File (".");
		File archivo = new File(System.getProperty("user.home")+File.separator+"spring_upload_example");
		if (!archivo.exists()) {
		    archivo.mkdir();
		}
		
		
		
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.home"));
		builder.append(File.separator);
		builder.append("spring_upload_example");
		builder.append(File.separator);
		builder.append(file.getOriginalFilename());
		

		byte[] fileBytes = file.getBytes();
		Path path = Paths.get(builder.toString());
		Files.write(path, fileBytes);
		
		attributes.addFlashAttribute("message", "decompilacion exitosa  { " +miDir.getCanonicalPath());
		
		Runtime.getRuntime().exec("java -jar "+miDir.getCanonicalPath()+"/lib/decompiler.jar -jar "+builder.toString()+" -o "+builder.toString()+"OUT");

		return "redirect:/status";
	}
	
	@GetMapping("/status")
	public String status() {
		return "status";
	}
}