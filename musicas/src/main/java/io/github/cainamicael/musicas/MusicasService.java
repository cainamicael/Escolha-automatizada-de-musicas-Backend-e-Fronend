package io.github.cainamicael.musicas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.models.Musicas;
import io.github.cainamicael.musicas.repositories.MusicasRepository;
import io.github.cainamicael.musicas.representations.MusicasDTO;

@Service
public class MusicasService {

	@Autowired
	private MusicasRepository repository;
	
	public List<MusicasDTO> listarTudo() {
		List<Musicas> musicas =  repository.findAll();
		List<MusicasDTO> musicasDTO = musicas.stream().map(x -> new MusicasDTO(x)).toList();
		return musicasDTO;
	}
	
	public MusicasDTO buscarPeloId(Long id) {
		Musicas musica = repository.findById(id).get();
		return new MusicasDTO(musica);
	}
	
	public List<MusicasDTO> listarPelaCategoria(CategoriasEnum categoria) {
		List<Musicas> musicas = repository.findByCategoria(categoria);
		List<MusicasDTO> musicasDTO = musicas.stream().map(x -> new MusicasDTO(x)).toList();
		return musicasDTO;
	}
}
