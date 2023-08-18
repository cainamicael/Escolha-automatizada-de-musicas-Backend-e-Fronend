package io.github.cainamicael.musicas.models;

import java.util.Date;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_musicas")
@Data
public class Musicas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Enumerated(EnumType.STRING)
	private CategoriasEnum categoria;
	private Date dataUltimaVezTocada;

}
