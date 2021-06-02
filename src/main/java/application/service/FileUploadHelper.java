package application.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {
	
	public final String UPLOAD_DIR=new ClassPathResource("/static/image/").getFile().getAbsolutePath();
	
	public FileUploadHelper() throws IOException {
	}


	public int uploadFile(MultipartFile multipartFile)
	{
		int f=0;
		try {
//			InputStream is = multipartFile.getInputStream();
//			byte data[] = new byte[is.available()];
//			is.read(data);
//			
//			FileOutputStream fos = new FileOutputStream(UPLOAD_DIR+"\\"+multipartFile.getOriginalFilename());
//			fos.write(data);
//			fos.flush();
//			fos.close();
			
			if(new File(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename()).exists()) {
				f = 2;
			} else {
				Files.copy(multipartFile.getInputStream(),Paths.get(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
				f=1;
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}
