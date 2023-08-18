package io.github.cainamicael.musicas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.models.Musicas;
import io.github.cainamicael.musicas.repositories.MusicasRepository;
import io.github.cainamicael.musicas.representations.MusicasDTO;

@Service
public class MusicasService {

	@Autowired
	private MusicasRepository repository;
	
	/*Crud*/
	public ResponseEntity<?> criar(MusicasDTO musicaDTO) {
		Musicas musica = new Musicas(musicaDTO);
		repository.save(musica);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public List<MusicasDTO> listarTudo() {
		List<Musicas> musicas =  repository.findAll();
		List<MusicasDTO> musicasDTO = musicas.stream().map(x -> new MusicasDTO(x)).toList();
		return musicasDTO;
	}
	
	public MusicasDTO buscarPeloId(Long id) {
		Musicas musica = repository.findById(id).get();
		return new MusicasDTO(musica);
	}
	
	public List<MusicasDTO> listarPelaCategoria(String categoriaStr) {
		CategoriasEnum categoria = CategoriasEnum.valueOf(categoriaStr.toUpperCase());
		List<Musicas> musicas = repository.findByCategoria(categoria);
		List<MusicasDTO> musicasDTO = musicas.stream().map(x -> new MusicasDTO(x)).toList();
		return musicasDTO;
	}
	
	/*Regras específicas*/
	
}
