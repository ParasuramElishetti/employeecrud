package com.example.EMP.repository;

import java.util.List;
import java.util.Optional;

import org.jsp.crud_demo.dto.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EMP.dto.Empppppp;

public interface EpmRepository extends JpaRepository<Empppppp, Integer>{



	boolean existsByEmail(String email);

	boolean existsByMobile(long mobile);

	
	
	
}
