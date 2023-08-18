package io.github.cainamicael.musicas.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.cainamicael.musicas.enums.CategoriasEnum;
import io.github.cainamicael.musicas.models.Musica;
import io.github.cainamicael.musicas.repositories.MusicaRepository;
import io.github.cainamicael.musicas.representations.MusicaDTO;

@Service
public class MusicaService {

	@Autowired
	private MusicaRepository repository;
	
	/*Crud*/
	public ResponseEntity<?> criar(MusicaDTO musicaDTO) {
		Musica musica = new Musica(musicaDTO);
		repository.save(musica);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public List<MusicaDTO> listarTudo() {
		List<Musica> musicas =  repository.findAll();
		List<MusicaDTO> musicasDTO = musicas.stream().map(x -> new MusicaDTO(x)).toList();
		return musicasDTO;
	}
	
	public MusicaDTO buscarPeloId(Long id) {
		Musica musica = repository.findById(id).get();
		return new MusicaDTO(musica);
	}
	
	public List<MusicaDTO> listarPelaCategoria(String categoriaStr) {
		CategoriasEnum categoria = CategoriasEnum.valueOf(categoriaStr.toUpperCase());
		List<Musica> musicas = repository.findByCategoria(categoria);
		List<MusicaDTO> musicasDTO = musicas.stream().map(x -> new MusicaDTO(x)).toList();
		return musicasDTO;
	}
	
	/*Regras específicas*/
	
	//Escolher 2 musicas de adoração e 1 de celebração
	public List<MusicaDTO> musicasIndicadas() {
		List<MusicaDTO> indicadas = new ArrayList<>();
		
		CategoriasEnum adoracao = CategoriasEnum.ADORACAO;
		CategoriasEnum celebracao = CategoriasEnum.CELEBRACAO;
		
		for(int i = 0; i < 2; i++) {
			logicaDaEscolha(adoracao, indicadas);
		}
		
		logicaDaEscolha(celebracao, indicadas);
				
		return indicadas;
	}
	
	public void logicaDaEscolha(CategoriasEnum categoria, List<MusicaDTO> indicadas) {
		zerarAsDatas();
		
		Optional<Musica> optionalMusica = repository.findByCategoriaNotPlayedMusic(categoria.toString());
		
		if(optionalMusica.isPresent()) {
			Musica musicaEscolhida = optionalMusica.get();
			
			musicaEscolhida.setDataUltimaVezTocada(new Date());
			repository.save(musicaEscolhida);
			
			indicadas.add(new MusicaDTO(musicaEscolhida));
		} else {
			Musica musicaEscolhida = repository.findByCategoriaPlayedMusicOrderByData(categoria.toString()).get();
			indicadas.add(new MusicaDTO(musicaEscolhida));
			
			musicaEscolhida.setDataUltimaVezTocada(new Date());
			repository.save(musicaEscolhida);
		}	
	}
	
	public Long[] quantidadeDeSorteios() {
		Long[] numeros = new Long[2];
		
		numeros[0] = repository.count();
		numeros[1] = repository.countDatesNotNull();
		
		return numeros;
	}
	
	public void zerarAsDatas() {
		if(repository.count() == repository.countDatesNotNull()) {
			List<Musica> musicas = repository.findAll();
			
			for (Musica musica : musicas) {
				musica.setDataUltimaVezTocada(null);
				repository.save(musica);
			}
		}
	}
}
