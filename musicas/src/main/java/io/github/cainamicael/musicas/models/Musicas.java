package io.github.cainamicael.musicas.models;

import java.util.Date;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.representations.MusicasDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_musicas")
@Data
@NoArgsConstructor
public class Musicas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Enumerated(EnumType.STRING)
	private CategoriasEnum categoria;
	private Date dataUltimaVezTocada;
	
	public Musicas(MusicasDTO musica) {
		this.id = musica.getId();
		this.nome = musica.getNome();
		this.categoria = musica.getCategoria();
		this.dataUltimaVezTocada = musica.getDataUltimaVezTocada();
	}

}
