package io.github.cainamicael.musicas.representations;

import java.util.Date;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.models.Musica;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MusicaDTO {
	
	private Long id; 
	private String nome;
	private CategoriasEnum categoria;
	private Date dataUltimaVezTocada;
	
	public MusicaDTO(Musica musica) {
		this.id = musica.getId();
		this.nome = musica.getNome();
		this.categoria = musica.getCategoria();
		this.dataUltimaVezTocada = musica.getDataUltimaVezTocada();
	}
	
	public MusicaDTO(String nome, CategoriasEnum categoria) {
		this.nome = nome;
		this.categoria = categoria;
	}

}
