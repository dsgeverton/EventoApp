package com.example.demo;

import javax.activation.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.example.demo.repository.EventoRepository;

@SpringBootApplication
@EnableJpaRepositories("com.example.demo.repository")
@EntityScan("com.example.demo.model")
public class MysqlJdbcDriverApplication implements CommandLineRunner { 

    DataSource dataSource;

    @Autowired
    EventoRepository eventoRepository;

    public static void main(String[] args) {
        SpringApplication.run(MysqlJdbcDriverApplication.class, args);
    } 

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Our DataSource is = " + dataSource);
        Iterable<com.example.demo.model.Evento> systemlist = eventoRepository.findAll();
        for(com.example.demo.model.Evento systemmodel:systemlist){
            System.out.println("Evento Cadastrado: " + systemmodel.getNome());
        }
    }
}