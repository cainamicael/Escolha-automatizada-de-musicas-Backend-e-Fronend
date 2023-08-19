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
	private String cantor;
	private CategoriasEnum categoria;
	private Date dataUltimaVezTocada;
	private Boolean pularMusica;
	
	public MusicaDTO(Musica musica) {
		this.id = musica.getId();
		this.nome = musica.getNome();
		this.cantor = musica.getCantor();
		this.categoria = musica.getCategoria();
		this.dataUltimaVezTocada = musica.getDataUltimaVezTocada();
		this.pularMusica = musica.getPularMusica();
	}
}
