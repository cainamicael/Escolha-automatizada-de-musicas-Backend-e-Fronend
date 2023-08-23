package io.github.cainamicael.musicas.models;

import java.util.Date;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.representations.MusicaDTO;
import io.github.cainamicael.musicas.representations.MusicaMinDTO;
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
public class Musica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cantor;
	@Enumerated(EnumType.STRING)
	private CategoriasEnum categoria;
	private Date dataUltimaVezTocada;
	private Boolean pularMusica;
	
	public Musica(MusicaDTO musica) {
		this.id = musica.getId();
		this.nome = musica.getNome();
		this.cantor = musica.getCantor();
		this.categoria = musica.getCategoria();
		this.dataUltimaVezTocada = musica.getDataUltimaVezTocada();
		this.pularMusica = musica.getPularMusica();
	}
	
	public Musica (MusicaMinDTO musica) {
		this.nome = musica.getNome();
		this.cantor = musica.getCantor();
		this.categoria = musica.getCategoria();
		this.pularMusica = musica.getPularMusica();
	}
}
