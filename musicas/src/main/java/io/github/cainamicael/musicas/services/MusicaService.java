package io.github.cainamicael.musicas.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.cainamicael.musicas.models.Musica;
import io.github.cainamicael.musicas.repositories.MusicaRepository;
import io.github.cainamicael.musicas.representations.MusicaDTO;
import io.github.cainamicael.musicas.representations.MusicaMinDTO;
import io.github.cainamicael.musicas.representations.QuantidadeDTO;

@Service
public class MusicaService {

	@Autowired
	private MusicaRepository repository;
	
	public void zerarTocadasQuandoTodasForemTocadas() {
		Long totalRegistros = repository.count();
		Long totalMusicasTocadas = repository.countPlayedMusics();
		
		if(totalRegistros == totalMusicasTocadas) {
			List<Musica> musicas = repository.findAll();
			
			for(Musica musica : musicas) {
				musica.setDataUltimaVezTocada(null);
				repository.save(musica);
			}
		}
	}
	
	public void removerPularMusica() {
		List<Musica> listaMusicas = repository.findAll();
		for (Musica musica : listaMusicas) {
			if(musica.getPularMusica()) {
				musica.setPularMusica(false);
				repository.save(musica);
			}
		}
	}
	
	public ResponseEntity<Musica> salvarMusica(MusicaMinDTO musica) {
		Musica musicaSalva = repository.save(new Musica(musica));
		return ResponseEntity.ok(musicaSalva);
	}
	
	public List<MusicaDTO> listarTudo() {
		List<Musica> musicas = repository.findAll();
		List<MusicaDTO> musicasDTO = musicas.stream().map(x -> new MusicaDTO(x)).toList();
		return musicasDTO;
	}
	
	public List<MusicaDTO> listarTocadas() {
		Optional<List<Musica>> musicasTocadasOptional = repository.findByDataUltimaVezTocadaNotNull();
		
		if(musicasTocadasOptional.isPresent()) {
			List<MusicaDTO> musicasDTO = musicasTocadasOptional.get().stream().map(x -> new MusicaDTO(x)).toList();
			return musicasDTO;
		} else {
			return null;
		}
	}
	
	public QuantidadeDTO quantidades() {
		Long quantidadeRegistros = repository.count();
		Long quantidadeMusicasTocadas = repository.countPlayedMusics();
		
		return new QuantidadeDTO(quantidadeRegistros, quantidadeMusicasTocadas);
	}
	
	public MusicaDTO musicaSorteada(String categoria) {
		Optional<Musica> musicaOptionalNaoRepetida = repository.findOneRandomUnplayedMusicByCategory(categoria);
		Optional<Musica> musicaOptionalRepetida = repository.findByCategoriaPlayedMusicOrderByData(categoria);

		Optional<Musica> musicaOptionalNaoRepetidaIgnorePular = repository.findOneRandomUnplayedMusicByCategoryIgnorePular(categoria);
		Optional<Musica> musicaOptionalRepetidaIgnorePular = repository.findByCategoriaPlayedMusicOrderByDataIgnorePular(categoria);
		
		zerarTocadasQuandoTodasForemTocadas();
		
		if (musicaOptionalNaoRepetida.isPresent()) {
			Musica musica = musicaOptionalNaoRepetida.get();
			musica.setPularMusica(true);
			repository.save(musica);
			
			return new MusicaDTO(musica);
		} else if(musicaOptionalRepetida.isPresent()){
			Musica musica = musicaOptionalRepetida.get();
			musica.setPularMusica(true);
			repository.save(musica);
			
			return new MusicaDTO(musica);
		} else if(musicaOptionalNaoRepetidaIgnorePular.isPresent()) {
			Musica musica = musicaOptionalNaoRepetidaIgnorePular.get();
			musica.setPularMusica(true);
			repository.save(musica);
			
			return new MusicaDTO(musica);
		} else {
			Musica musica = musicaOptionalRepetidaIgnorePular.get();
			musica.setPularMusica(true);
			repository.save(musica);
			
			return new MusicaDTO(musica);
		}
	}
	
	public MusicaDTO pularPeloIdESortearNovaMusica(Long id) {
		Optional<Musica> musicaOptional = repository.findById(id);
		
		Musica musica = musicaOptional.get();
		musica.setPularMusica(true);
		repository.save(musica);
		
		MusicaDTO novaMusicaSorteada = musicaSorteada(musica.getCategoria().toString());
		
		return novaMusicaSorteada;
	}

	public ResponseEntity<String> confirmarMusicas(List<MusicaDTO> musicas) {
		//Setar a data
		for (MusicaDTO musica : musicas) {
			musica.setDataUltimaVezTocada(new Date());
			repository.save(new Musica(musica));
		}
		
		//Tirar o pular de todos os registros
		removerPularMusica();
		
		return ResponseEntity.ok("Músicas marcadas para não repetir");
	}

	public ResponseEntity<String> cancelar() {
		//Tirar o pular de todos os registros
		removerPularMusica();
		
		return ResponseEntity.ok("Nenhuma alteração foi salva");
	}
}
