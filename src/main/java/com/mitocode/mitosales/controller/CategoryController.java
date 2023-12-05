package com.mitocode.mitosales.controller;
import com.mitocode.mitosales.dto.CategoryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.mitocode.mitosales.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.mitocode.mitosales.service.ICategoryService;

import java.util.List;


@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService service;
    @Qualifier("categoryMapper")
    private final ModelMapper mapper;
    @PreAuthorize("@authServiceImpl.hasAccess('/readAll')")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CategoryDTO>>  readAll() throws Exception {
        List<CategoryDTO> list = service.readAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO>  readById(@PathVariable("id") Integer id) throws Exception {
        Category obj = service.readById(id);
       return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO dto) throws Exception{
        Category obj =  service.save(convertToEntity(dto)) ;
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO dto, @PathVariable("id") Integer id) throws Exception{
        Category obj =  service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //////////////queries////////////

    @GetMapping("/find/name/{name}")
    public ResponseEntity<List<CategoryDTO>> findByName(@PathVariable("name") String name){
        List<CategoryDTO> categoryDTOList = service.findByName(name).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }

    @GetMapping("/find/name/like/{name}")
    public ResponseEntity<List<CategoryDTO>> findByNameLike(@PathVariable("name") String name){
        List<CategoryDTO> categoryDTOList = service.findByNameLikeIgnoreCase(name).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }
    @GetMapping("/find/name/{name}/{enabled}")
    public ResponseEntity<List<CategoryDTO>> findByNameEnabled(@PathVariable("name") String name, @PathVariable("enabled") boolean enabled){
        List<CategoryDTO> categoryDTOList = service.findByNameAndEnabled(name, enabled).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }
    @GetMapping("/get/name/description")
    public ResponseEntity<List<CategoryDTO>> getNameAndDescription2(@RequestParam("name") String name, @RequestParam("description") String description){
        List<CategoryDTO> categoryDTOList = service.getNameAndDescription2(name, description).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }

    @GetMapping("/get/name/sql/{name}")
    public ResponseEntity<List<CategoryDTO>> getNameSQL(@PathVariable("name") String name){
        List<CategoryDTO> categoryDTOList = service.getNameSQL(name).stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }
    @GetMapping("/pagination")
    public ResponseEntity<Page<CategoryDTO>> findPage(Pageable pageable) {
        Page<CategoryDTO> categoryDTOPage = service.findPage(pageable).map(this::convertToDto);
        return new ResponseEntity<>(categoryDTOPage, HttpStatus.OK);
    }
    @GetMapping("/pagination2")
    public ResponseEntity<Page<CategoryDTO>> findPage(
            @RequestParam(name = "p", defaultValue = "0") int page,
            @RequestParam(name = "s", defaultValue = "3") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CategoryDTO> categoryDTOPage = service.findPage(pageRequest).map(this::convertToDto);
        return new ResponseEntity<>(categoryDTOPage, HttpStatus.OK);
    }
    @GetMapping("order")
    public ResponseEntity<List<CategoryDTO>> findAllOrder(
            @RequestParam(name = "param", defaultValue = "ASC") String param
    ){
       List<CategoryDTO> categoryDTOList = service.findAllOrder(param).stream().map(this::convertToDto).toList();
       return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }



    //////////////utils/////////////
    private  CategoryDTO convertToDto(Category obj) {
        return mapper.map(obj, CategoryDTO.class);
    }
    private  Category convertToEntity(CategoryDTO dto) {
        return mapper.map(dto, Category.class);
    }

}
