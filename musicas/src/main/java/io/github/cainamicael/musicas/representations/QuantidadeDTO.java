package io.github.cainamicael.musicas.representations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantidadeDTO {
	private Long quantidadeRegistros;
	private Long quantidadeMusicasTocadas;
}
