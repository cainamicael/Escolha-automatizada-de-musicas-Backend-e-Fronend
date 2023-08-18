package io.github.cainamicael.musicas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.models.Musicas;

public interface MusicasRepository extends JpaRepository<Musicas, Long> {
	
	//Buscar música pela categoria
	List<Musicas> findByCategoria(CategoriasEnum categoria);
	
	@Query(value = "SELECT * FROM tb_musicas WHERE categoria = :categoria AND data_ultima_vez_tocada IS NULL ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Optional<Musicas> findByCategoriaNotPlayedMusic(String categoria);
	
	 @Query(value = "SELECT * FROM tb_musicas WHERE categoria = :categoria AND data_ultima_vez_tocada IS NOT NULL ORDER BY data_ultima_vez_tocada ASC LIMIT 1", nativeQuery = true)
	 Optional<Musicas> findByCategoriaPlayedMusicOrderByData(String categoria);
}

/*
 @Query(value = "SELECT * FROM tb_musicas WHERE categoria = :categoria AND data_ultima_vez_tocada IS NULL ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    
 @Query(value = "SELECT * FROM tb_musicas WHERE categoria = :categoria AND data_ultima_vez_tocada IS NOT NULL ORDER BY data_ultima_vez_tocada ASC LIMIT 1", nativeQuery = true)
 
 * */
	

