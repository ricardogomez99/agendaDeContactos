package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Contacto;

public interface ContacoRepositorio extends JpaRepository<Contacto,Integer>{
    
}
