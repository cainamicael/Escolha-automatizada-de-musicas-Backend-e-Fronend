package io.github.cainamicael.musicas.representations;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.models.Musica;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MusicaMinDTO {

	private String nome;
	private String cantor;
	private CategoriasEnum categoria;

	
	public MusicaMinDTO(Musica musica) {
		this.nome = musica.getNome();
		this.cantor = musica.getCantor();
		this.categoria = musica.getCategoria();
	}
	
}
