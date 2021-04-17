package cat.udl.eps.softarch.academicrecruit;

import cat.udl.eps.softarch.academicrecruit.service.FileStorageService;
import cat.udl.eps.softarch.academicrecruit.service.IFileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class AcademicRecruitApplication implements CommandLineRunner {

	@Resource
	FileStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(AcademicRecruitApplication.class, args);
	}

	@Override
	public void run(String... arg) {
		storageService.init();
	}

}
