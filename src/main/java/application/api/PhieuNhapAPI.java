package application.api;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import application.entity.PhieuNhap;
import application.service.PhieuNhapService;

@CrossOrigin
@RestController
public class PhieuNhapAPI {
	@Autowired
	private PhieuNhapService service;
	
	@GetMapping("/phieunhap")
	private List<PhieuNhap> listALL(){
		return service.list();
	}
	
	@GetMapping("/phieunhap/{idPN}")
	public ResponseEntity<PhieuNhap> get(@PathVariable Integer idPN) {
		try {
			PhieuNhap phieunhap = service.get(idPN);
			return new ResponseEntity<PhieuNhap>(phieunhap,HttpStatus.OK);
		} catch(NoSuchElementException e) {
			return new ResponseEntity<PhieuNhap>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/phieunhap")
	private void add(@RequestBody PhieuNhap phieunhap) {
		service.save(phieunhap);
	}	
}
