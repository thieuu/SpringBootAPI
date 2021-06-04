package application.api;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import application.dao.AddKhachHang;
import application.entity.KhachHang;
import application.entity.SanPham;
import application.entity.User;
import application.service.KhacHangService;
import application.service.UserService;

@CrossOrigin
@RestController
public class KhachHangAPI {
	@Autowired
	private KhacHangService service;
	
	@Autowired
	private UserService userservice;
	
	@GetMapping("/khachhang")
	private List<KhachHang> list(){
		return service.listAll();
	}
	
	@GetMapping("/khachhang/{sdt}")
	private ResponseEntity<KhachHang> get(@PathVariable String sdt){
		try {
			KhachHang khachhang= service.get(sdt);
			return new ResponseEntity<KhachHang>(khachhang,HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<KhachHang>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@PostMapping("/khachhang")
	private ResponseEntity<?> add(@RequestBody AddKhachHang addKhachHang) {
		try {
			KhachHang exKhachHang = service.get(addKhachHang.getSdt());
			if(exKhachHang == null) {
				service.save(new KhachHang(addKhachHang.getSdt(),addKhachHang.getTenKH(),addKhachHang.getDiaChi(),addKhachHang.getEmail()));
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				User user = new User(addKhachHang.getSdt(),encoder.encode(addKhachHang.getMatKhau()),"USER");
				userservice.save(user);
				return ResponseEntity.ok(1);
			}
			return ResponseEntity.ok(2);
		}catch(NoSuchElementException e) {
	//		return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			return ResponseEntity.ok(0);
		}
	}
	
	@PutMapping("/khachhang/{sdt}")
	private ResponseEntity<?> update(@RequestBody KhachHang khachhang,@PathVariable String sdt){
		try {
			KhachHang exKhachHang = service.get(sdt);
			service.save(khachhang);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/khachhang/{sdt}")
	private ResponseEntity<?> delete(@PathVariable String sdt){
		try {
			KhachHang exKhachHang = service.get(sdt);
			service.delete(sdt);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
