package com.example.EMP.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.EMP.dto.Empppppp;
import com.example.EMP.repository.EpmRepository;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;

@Controller
public class MyController {

	@Autowired
	EpmRepository repository;

	@GetMapping("/")
	// @ResponseBody
	public String Main() {
		return "main.html";
	}

	@GetMapping("/insert")
	public String insert() {
		return "insert.html";
	}

	@PostMapping("/insert")
	public String insertemp(@RequestParam String name, @RequestParam String email, @RequestParam long mobile,
			@RequestParam double sal, @RequestParam MultipartFile photo, @RequestParam MultipartFile resume,
			@RequestParam String highestQualification, @RequestParam String applyingPosition,
			@RequestParam int passingYear, ModelMap map, @Valid Empppppp emp1, BindingResult result)
			throws IOException {

		if (result.hasErrors()) {
			return "insert.html";
		} else {
			Empppppp emp = new Empppppp();
			emp.setEmail(email);
			emp.setName(name);
			emp.setMobile(mobile);
			emp.setSal(sal);
			emp.setApplyingPosition(applyingPosition);
			emp.setHighestQualification(highestQualification);
			emp.setPassingYear(passingYear);
			emp.setPhoto(photo.getBytes());
			emp.setResume(resume.getBytes());

			if (repository.existsByEmail(email)) {
				map.put("error", "Email already exists!");
				return "insert.html";
			}

			if (repository.existsByMobile(mobile)) {
				map.put("error", "mobile number already exists!");
				return "insert.html";
			}

			else {
				repository.save(emp);
				map.put("success", "data inserted successfully");
				return "main.html";
			}
		}
	}

	@GetMapping("/fetch")
	public String fetchAll(ModelMap map) {
		List<Empppppp> emp = repository.findAll();
		map.put("list", emp);
		return "fetch.html";
	}

	@GetMapping("/photo/{id}")
	@ResponseBody
	public byte[] getPhoto(@PathVariable int id) {
		Empppppp emp = repository.findById(id).orElseThrow();
		return emp.getPhoto();
	}

	@GetMapping("/resume/{id}")
	@ResponseBody
	public ResponseEntity<byte[]> getResume(@PathVariable int id) {
		Empppppp emp = repository.findById(id).orElseThrow();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=\"resume.pdf\"");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(emp.getResume());
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, Empppppp emp, ModelMap map) {
		repository.deleteById(id);
		return fetchAll(map);
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, ModelMap map) {
		Empppppp emp = repository.findById(id).orElseThrow();
		map.put("emp", emp);
		// map.put("update", "update");
		return "edit.html";
	}
	/*
	 * @PostMapping("/update") public String update(Empppppp emp, ModelMap map) {
	 * repository.save(emp); map.put("success", "recorded updated successfully");
	 * return fetchAll(map); }
	 */

	@PostMapping("/update")
	public String update(@RequestParam int id, @RequestParam String name, @RequestParam String email,
			@RequestParam long mobile, @RequestParam double sal, @RequestParam MultipartFile photo,
			@RequestParam MultipartFile resume, @RequestParam String highestQualification,
			@RequestParam String applyingPosition, @RequestParam int passingYear, ModelMap map) throws IOException {
		Empppppp emp = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

		emp.setName(name);
		if (repository.existsByEmail(email)) {
			map.put("erroremail", "email already exist");
		} else {
			emp.setEmail(email);
		}

		if (repository.existsByMobile(mobile)) {
			map.put("errormobile", "mobile number already exist");
		} else {
			emp.setMobile(mobile);
		}
		emp.setSal(sal);
		emp.setApplyingPosition(applyingPosition);
		emp.setHighestQualification(highestQualification);
		emp.setPassingYear(passingYear);

		if (photo != null && !photo.isEmpty()) {
			emp.setPhoto(photo.getBytes());
		}
		if (resume != null && !resume.isEmpty()) {
			emp.setResume(resume.getBytes());
		}

		repository.save(emp);
		map.put("success", "Record updated successfully");
		return fetchAll(map);
	}
}

//
//
//package com.example.EMP.Controller;
//
//import java.io.IOException;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import com.example.EMP.dto.Empppppp;
//import com.example.EMP.repository.EpmRepository;
//import jakarta.validation.Valid;
//
//@Controller
//public class MyController {
//
//    @Autowired
//    EpmRepository repository;
//
//    @GetMapping("/")
//    public String main() {
//        return "main";
//    }
//
//    @GetMapping("/insert")
//    public String insert(ModelMap map) {
//        map.put("emp", new Empppppp());
//        return "insert";
//    }
//
//    @PostMapping("/insert")
//    public String insertEmp(@Valid @ModelAttribute("emp") Empppppp emp, BindingResult result,
//                            @RequestParam MultipartFile photo, @RequestParam MultipartFile resume, ModelMap map) throws IOException {
//        if (result.hasErrors()) {
//            return "main";
//        }
//        if (repository.existsByEmail(emp.getEmail())) {
//            map.put("error", "Email already exists!");
//            return "insert";
//        }
//        if (repository.existsByMobile(emp.getMobile())) {
//            map.put("error", "Mobile number already exists!");
//            return "insert";
//        }
//        emp.setPhoto(photo.getBytes());
//        emp.setResume(resume.getBytes());
//        repository.save(emp);
//        map.put("success", "Data inserted successfully");
//        return "main";
//    }
//
//    @GetMapping("/fetch")
//    public String fetchAll(ModelMap map) {
//        List<Empppppp> empList = repository.findAll();
//        map.put("list", empList);
//        return "fetch";
//    }
//
//    @GetMapping("/photo/{id}")
//    @ResponseBody
//    public byte[] getPhoto(@PathVariable int id) {
//        Empppppp emp = repository.findById(id).orElseThrow();
//        return emp.getPhoto();
//    }
//
//    @GetMapping("/resume/{id}")
//    @ResponseBody
//    public ResponseEntity<byte[]> getResume(@PathVariable int id) {
//        Empppppp emp = repository.findById(id).orElseThrow();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "attachment; filename=\"resume.pdf\"");
//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(emp.getResume());
//    }
//
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable int id, ModelMap map) {
//        repository.deleteById(id);
//        return fetchAll(map);
//    }
//
//    @GetMapping("/edit/{id}")
//    public String edit(@PathVariable int id, ModelMap map) {
//        Empppppp emp = repository.findById(id).orElseThrow();
//        map.put("emp", emp);
//        return "edit";
//    }
//
//    @PostMapping("/update")
//    public String update(@RequestParam int id, @Valid @ModelAttribute("emp") Empppppp emp, BindingResult result,
//                         @RequestParam MultipartFile photo, @RequestParam MultipartFile resume, ModelMap map) throws IOException {
//        if (result.hasErrors()) {
//            return "edit";
//        }
//        Empppppp existingEmp = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
//        if (repository.existsByEmail(emp.getEmail()) && !existingEmp.getEmail().equals(emp.getEmail())) {
//            map.put("erroremail", "Email already exists!");
//            return "edit";
//        }
//        if (repository.existsByMobile(emp.getMobile()) && existingEmp.getMobile() != emp.getMobile()) {
//            map.put("errormobile", "Mobile number already exists!");
//            return "edit";
//        }
//        existingEmp.setName(emp.getName());
//        existingEmp.setEmail(emp.getEmail());
//        existingEmp.setMobile(emp.getMobile());
//        existingEmp.setSal(emp.getSal());
//        existingEmp.setHighestQualification(emp.getHighestQualification());
//        existingEmp.setApplyingPosition(emp.getApplyingPosition());
//        existingEmp.setPassingYear(emp.getPassingYear());
//        if (photo != null && !photo.isEmpty()) {
//            existingEmp.setPhoto(photo.getBytes());
//        }
//        if (resume != null && !resume.isEmpty()) {
//            existingEmp.setResume(resume.getBytes());
//        }
//        repository.save(existingEmp);
//        map.put("success", "Record updated successfully");
//        return fetchAll(map);
//    }
//}
