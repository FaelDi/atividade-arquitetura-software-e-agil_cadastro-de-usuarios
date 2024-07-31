package com.wordpress.faeldi.UserRegister.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wordpress.faeldi.UserRegister.service.BaseService;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class BaseController<T,S extends BaseService<T>> {
	
	protected S service;
	
	public BaseController(S service) {
		this.service = service;
	}

    @Value("AWS_ACCESS_KEY")
    private String accessKeyId;

    @Value("AWS_SECRET_KEY")
    private String secretAccessKey;
    @GetMapping
    public ResponseEntity<List<T>> buscarTodos() {
        return ResponseEntity.ok(service.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> buscarUm(@PathVariable("id") String id) {
        try {
        	
        	return ResponseEntity.ok(service.buscarUm(id));
        	
        }catch(EntityNotFoundException ex) {
        	
        	return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch(Exception ex) {
        	
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<T> criar(@RequestBody @Valid T entidade) {
        try {

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(service.criar(entidade));

        }catch(Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(value = "/{id}/profile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<T> createProfileImage(@PathVariable("id") String id,
                                                @RequestParam("file") MultipartFile file) {
        try{
            String absolutePath = "C:\\Users\\d_raf\\OneDrive\\Documentos\\repositorios\\atividade-arquitetura-software-e-agil_cadastro-de-usuarios\\";
            //absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
            T user =  service.buscarUm(id);
            Path filepath = null;
            if(Objects.nonNull(user)) {
                if (Objects.nonNull(file)) {
                    filepath = Paths.get(absolutePath, file.getOriginalFilename());
                    try (OutputStream os = Files.newOutputStream(filepath)) {
                        os.write(file.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                            Region region = Region.US_EAST_2;
                            S3Client s3 = S3Client.builder().region(region).credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId,secretAccessKey)))
                                    .build();
                            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket("users-profile-images-java-role")
                                    .key(file.getOriginalFilename()).build();
                            //PutObjectResponse response = s3.putObject(putObjectRequest,filepath);
                            log.info("File uploaded:"+file.getOriginalFilename());
                    }
                }
        }catch(Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> editar(@PathVariable("id") String id,
                                    @RequestBody @Valid T entidade) {
        try {

        	return ResponseEntity.ok(service.editar(id, entidade));

        }catch(EntityNotFoundException ex) {
        	
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception ex) {
        	
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remover(@PathVariable("id") String id) {
        try {
        	
        	service.excluir(id);
        	return ResponseEntity.status(HttpStatus.OK).build();
        	
        }catch(EntityNotFoundException ex) {
        	
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch(Exception ex) {
        	
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
