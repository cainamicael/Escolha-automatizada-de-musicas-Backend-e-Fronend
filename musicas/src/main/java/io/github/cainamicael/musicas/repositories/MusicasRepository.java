package io.github.cainamicael.musicas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.models.Musicas;

public interface MusicasRepository extends JpaRepository<Musicas, Long> {
	
	//Buscar música pela categoria
	List<Musicas> findByCategoria(CategoriasEnum categoria);
	
}
