package com.wordpress.faeldi.UserRegister.repository;


import com.wordpress.faeldi.UserRegister.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{

}
