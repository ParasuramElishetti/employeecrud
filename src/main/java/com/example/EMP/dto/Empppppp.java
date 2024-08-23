package com.example.EMP.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Empppppp {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@Size(min = 3, max = 20, message = "* Enter between 3~20 charecters")
private String name;
@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "* Enter 8 charecters with one lowercase, one uppercase, one number and one special charecter")
private String email;
@DecimalMin(value = "6000000000", message = "* Enter proper Mobile Number")
@DecimalMax(value = "9999999999", message = "* Enter proper Mobile Number")
private long mobile;
@NotNull(message = "* It is Compulsory Field")
private double sal;
@NotNull(message = "* It is Compulsory Field")
private String highestQualification;
@NotNull(message = "* It is Compulsory Field")
private String applyingPosition;
@NotNull(message = "* It is Compulsory Field")
int passingYear;
@Lob
@Column(columnDefinition = "LONGBLOB")
byte[] photo;
@Lob
@Column(columnDefinition = "LONGBLOB")
byte[] resume;

}
