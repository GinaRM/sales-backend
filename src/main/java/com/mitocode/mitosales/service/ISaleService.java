package com.mitocode.mitosales.service;

import com.mitocode.mitosales.dto.IProcedureDTO;
import com.mitocode.mitosales.dto.ProcedureDTO;
import com.mitocode.mitosales.model.Sale;

import java.util.List;
import java.util.Map;

public interface ISaleService extends ICRUD<Sale, Integer> {
    List<ProcedureDTO> callProcedure1();
    List<IProcedureDTO> callProcedure2();
    List<ProcedureDTO> callProcedure3();
    void  callProcedure4();


    Sale getSaleMostExpensive();
    String getBestSellerPerson();
    Map<String, Long> getSalesCountBySeller();

    Map<String, Double> getMostSellerProduct();
}

