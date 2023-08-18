package io.github.cainamicael.musicas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.models.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
	
	//Buscar música pela categoria
	List<Musica> findByCategoria(CategoriasEnum categoria);
	
	//Sortear a música pela categoria e sem repetição
	@Query(value = "SELECT * FROM tb_musicas WHERE categoria = :categoria AND data_ultima_vez_tocada IS NULL ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Optional<Musica> findByCategoriaNotPlayedMusic(String categoria);
	
	//Quando não houver música sem ter sido tocada, vai escolher a que foi tocada a mais tempo
	@Query(value = "SELECT * FROM tb_musicas WHERE categoria = :categoria AND data_ultima_vez_tocada IS NOT NULL ORDER BY data_ultima_vez_tocada ASC LIMIT 1", nativeQuery = true)
	Optional<Musica> findByCategoriaPlayedMusicOrderByData(String categoria);
}
	