package com.mitocode.mitosales.controller;

import com.mitocode.mitosales.dto.ProviderDTO;
import com.mitocode.mitosales.model.Provider;
import com.mitocode.mitosales.service.IProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final IProviderService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProviderDTO>>  readAll() throws Exception {
        List<ProviderDTO> list = service.readAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO>  readById(@PathVariable("id") Integer id) throws Exception {
        Provider obj = service.readById(id);
       return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProviderDTO> create(@Valid @RequestBody ProviderDTO dto) throws Exception{
        Provider obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProviderDTO> update(@Valid @RequestBody ProviderDTO dto, @PathVariable("id") Integer id) throws Exception{
        Provider obj =  service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //////////////utils/////////////
    private  ProviderDTO convertToDto(Provider obj) {
        return mapper.map(obj, ProviderDTO.class);
    }
    private  Provider convertToEntity(ProviderDTO dto) {
        return mapper.map(dto, Provider.class);
    }

}
