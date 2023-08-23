package io.github.cainamicael.musicas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.cainamicael.musicas.models.Musica;
import io.github.cainamicael.musicas.representations.MusicaDTO;
import io.github.cainamicael.musicas.representations.MusicaMinDTO;
import io.github.cainamicael.musicas.representations.QuantidadeDTO;
import io.github.cainamicael.musicas.services.MusicaService;

@RestController
@RequestMapping(value = "api")
public class MusicaController {
	
	@Autowired
	private MusicaService service;
	
	@PostMapping(value = "musica")
	public ResponseEntity<Musica> salvarMusica(@RequestBody MusicaMinDTO musica) {
		return service.salvarMusica(musica);
	}
	
	@GetMapping(value = "musicas")
	public List<MusicaDTO> listarTudo() {
		return service.listarTudo();
	}
	
	@GetMapping(value = "musicas/tocadas")
	public List<MusicaDTO> listarTocadas() {
		return service.listarTocadas();
	}
	
	@GetMapping("quantidades")
	public QuantidadeDTO quantidades() {
		return service.quantidades();
	}
	
	@GetMapping(value = "musica", params = "categoria")
	public MusicaDTO mostrmusicaSorteada(@RequestParam("categoria") String categoria) {
		return service.musicaSorteada(categoria.toUpperCase());
	}
	
	@GetMapping(value = "musica/pular/{id}")
	public MusicaDTO pularPeloIdESortearNovaMusica(@PathVariable("id") Long id) {
		return service.pularPeloIdESortearNovaMusica(id);
	}
	
	@PostMapping(value = "musicas/confirmar")
	public ResponseEntity<String> confirmarMusicas(@RequestBody List<MusicaDTO> musicas) {
		return service.confirmarMusicas(musicas);
	}
	
	@GetMapping(value = "musicas/cancelar")
	public ResponseEntity<String> cancelar() {
		return service.cancelar();
	}
}
