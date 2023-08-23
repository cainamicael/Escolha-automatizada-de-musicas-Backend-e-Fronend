package io.github.cainamicael.musicas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.models.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
	
	//Onde vão passar em branco as músicas com o parâmetro pular
	@Query(value = "SELECT * FROM tb_musicas WHERE categoria = :categoria AND data_ultima_vez_tocada IS NULL AND pular_musica IS FALSE ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Optional<Musica> findOneRandomUnplayedMusicByCategory(String categoria);
	
	//Selecionar músicas não tocadas sem considerar o pular
	@Query(value = "SELECT * FROM tb_musicas WHERE categoria = :categoria AND data_ultima_vez_tocada IS NULL ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Optional<Musica> findOneRandomUnplayedMusicByCategoryIgnorePular(String categoria);
	
	//Para quando acabarem as músicas que não foram tocadas, colocar as mais antigas
	@Query(value = "SELECT * FROM tb_musicas WHERE categoria = :categoria AND data_ultima_vez_tocada IS NOT NULL AND pular_musica IS FALSE ORDER BY data_ultima_vez_tocada ASC LIMIT 1", nativeQuery = true)
	Optional<Musica> findByCategoriaPlayedMusicOrderByData(String categoria);
	
	//Para quando acabarem as músicas que não foram tocadas, colocar as mais antigas sem considerar o pular
	@Query(value = "SELECT * FROM tb_musicas WHERE categoria = :categoria AND data_ultima_vez_tocada IS NOT NULL ORDER BY data_ultima_vez_tocada ASC LIMIT 1", nativeQuery = true)
	Optional<Musica> findByCategoriaPlayedMusicOrderByDataIgnorePular(String categoria);
	
	//Para sabermos a quantidade de músicas que foram tocadas, para quando todas forem tocadas, resetarmos as musicas tocadas
	@Query(value = "SELECT COUNT(*) FROM tb_musicas WHERE data_ultima_vez_tocada IS NOT NULL;", nativeQuery = true)
	Long countPlayedMusics();
	
	//Retornar as músicas tocadas
	Optional<List<Musica>> findByDataUltimaVezTocadaNotNull();
}
