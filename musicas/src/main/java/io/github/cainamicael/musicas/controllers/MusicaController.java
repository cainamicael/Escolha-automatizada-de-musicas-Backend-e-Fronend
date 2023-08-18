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

import io.github.cainamicael.musicas.representations.MusicaDTO;
import io.github.cainamicael.musicas.services.MusicaService;

@RestController
@RequestMapping(value = "/musicas")
public class MusicaController {
	
	@Autowired
	private MusicaService service;
	
	/*Crud*/
	@PostMapping
	public ResponseEntity<?> criar(@RequestBody MusicaDTO musica) {
		return service.criar(musica);
	}

	@GetMapping
	public ResponseEntity<List<MusicaDTO>> listarTudo() {
		List<MusicaDTO> musicas = service.listarTudo();
		return ResponseEntity.ok(musicas);
	}
	
	@GetMapping(value = "/{id}")
	public MusicaDTO buscarPeloId(@PathVariable("id") Long id) {
		return service.buscarPeloId(id);
	}
	
	@GetMapping(params = "categoria")
	public List<MusicaDTO> listarPelaCategoria(@RequestParam("categoria") String categoria) {
		List<MusicaDTO> musicas = service.listarPelaCategoria(categoria);
		return musicas;
		
	}
	
	/*Regras específicas*/
	@GetMapping(value = "/indicadas")
	public List<MusicaDTO> musicasIndicadas() {
		return service.musicasIndicadas();
	}
	
}
