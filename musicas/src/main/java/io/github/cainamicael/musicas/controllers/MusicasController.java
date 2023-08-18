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

import io.github.cainamicael.musicas.representations.MusicasDTO;
import io.github.cainamicael.musicas.services.MusicasService;

@RestController
@RequestMapping(value = "/musicas")
public class MusicasController {
	
	@Autowired
	private MusicasService service;
	
	/*Crud*/
	@PostMapping
	public ResponseEntity<?> criar(@RequestBody MusicasDTO musica) {
		return service.criar(musica);
	}

	@GetMapping
	public ResponseEntity<List<MusicasDTO>> listarTudo() {
		List<MusicasDTO> musicas = service.listarTudo();
		return ResponseEntity.ok(musicas);
	}
	
	@GetMapping(value = "/{id}")
	public MusicasDTO buscarPeloId(@PathVariable("id") Long id) {
		return service.buscarPeloId(id);
	}
	
	@GetMapping(params = "categoria")
	public List<MusicasDTO> listarPelaCategoria(@RequestParam("categoria") String categoria) {
		List<MusicasDTO> musicas = service.listarPelaCategoria(categoria);
		return musicas;
		
	}
}
