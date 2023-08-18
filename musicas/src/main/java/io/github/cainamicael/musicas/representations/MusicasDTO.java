package io.github.cainamicael.musicas.representations;

import java.util.Date;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.models.Musicas;
import lombok.Data;

@Data
public class MusicasDTO {
	
	private Long id; 
	private String nome;
	private CategoriasEnum categoria;
	private Date dataUltimaVezTocada;
	
	public MusicasDTO(Musicas musica) {
		this.id = musica.getId();
		this.nome = musica.getNome();
		this.categoria = musica.getCategoria();
		this.dataUltimaVezTocada = musica.getDataUltimaVezTocada();
	}

}
