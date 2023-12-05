package com.mitocode.mitosales.controller;

import com.mitocode.mitosales.dto.IProcedureDTO;
import com.mitocode.mitosales.dto.ProcedureDTO;
import com.mitocode.mitosales.dto.SaleDTO;
import com.mitocode.mitosales.model.Sale;
import com.mitocode.mitosales.service.ISaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final ISaleService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<SaleDTO>>  readAll() throws Exception {
        List<SaleDTO> list = service.readAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO>  readById(@PathVariable("id") Integer id) throws Exception {
        Sale obj = service.readById(id);
       return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SaleDTO> create(@Valid @RequestBody SaleDTO dto) throws Exception{
        Sale obj = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> update(@Valid @RequestBody SaleDTO dto, @PathVariable("id") Integer id) throws Exception{
        Sale obj =  service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    ////////////////////queries//////
    @GetMapping("/resume")
    public ResponseEntity<List<ProcedureDTO>> getSaleResume1(){
        return new ResponseEntity<>(service.callProcedure1(), HttpStatus.OK);
    }

    @GetMapping("/resume2")
    public ResponseEntity<List<IProcedureDTO>> getSaleResume2(){
        return new ResponseEntity<>(service.callProcedure2(), HttpStatus.OK);
    }

    @GetMapping("/resume3")
    public ResponseEntity<List<ProcedureDTO>> getSaleResume3(){
        return new ResponseEntity<>(service.callProcedure3(), HttpStatus.OK);
    }

    @GetMapping("/resume4")
    public ResponseEntity<Void> getSaleResume4(){
        service.callProcedure4();
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/mostExpensive")
    public ResponseEntity<SaleDTO> getMostExpensive(){
        SaleDTO dto = convertToDto(service.getSaleMostExpensive());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/bestSeller")
    public ResponseEntity<String> getBestSellerPerson(){
        String user = service.getBestSellerPerson();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/sellerCount")
    public ResponseEntity<Map<String, Long>> getSellerCount(){
        Map<String, Long> byUser = service.getSalesCountBySeller();
        return new ResponseEntity<>(byUser, HttpStatus.OK);
    }

    @GetMapping("/bestProduct")
    public ResponseEntity<Map<String, Double>> getBestProduct(){
        Map<String, Double> byUser = service.getMostSellerProduct();
        return new ResponseEntity<>(byUser, HttpStatus.OK);
    }




    //////////////utils/////////////
    private  SaleDTO convertToDto(Sale obj) {
        return mapper.map(obj, SaleDTO.class);
    }
    private  Sale convertToEntity(SaleDTO dto) {
        return mapper.map(dto, Sale.class);
    }

}
