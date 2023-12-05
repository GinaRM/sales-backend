package com.mitocode.mitosales.service.impl;

import com.mitocode.mitosales.dto.IProcedureDTO;
import com.mitocode.mitosales.dto.ProcedureDTO;
import com.mitocode.mitosales.model.Sale;
import com.mitocode.mitosales.model.SaleDetail;
import com.mitocode.mitosales.repo.IGenericRepo;
import com.mitocode.mitosales.repo.ISaleRepo;
import com.mitocode.mitosales.service.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

@RequiredArgsConstructor
@Service
public class SaleServiceImpl extends CRUDImpl<Sale, Integer> implements ISaleService {

    private final ISaleRepo repo;


    @Override
    protected IGenericRepo<Sale, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<ProcedureDTO> callProcedure1() {
        List<ProcedureDTO> procedureDTOList = new ArrayList<>();
        repo.callProcedure1().forEach(e -> {
            ProcedureDTO dto = new ProcedureDTO();
            dto.setQuantityfn((Integer) e[0]);
            dto.setDatetimefn((String) e[1]);
            procedureDTOList.add(dto);
        });
        return procedureDTOList;
    }

    @Override
    public List<IProcedureDTO> callProcedure2() {
        return repo.callProcedure2();
    }

    @Override
    public List<ProcedureDTO> callProcedure3() {
        return repo.callProcedure3();
    }

    @Override
    public void  callProcedure4() {
        repo.callProcedure4();
    }

    @Override
    public Sale getSaleMostExpensive() {
        return repo.findAll()
                .stream()
                .max(Comparator.comparing(Sale::getTotal))
                .orElse(new Sale());
    }

    @Override
    public String getBestSellerPerson() {
       Map<String, Double> byUser = repo.findAll()
                .stream()
               .collect(groupingBy(s -> s.getUser().getUsername(), summingDouble(Sale::getTotal)));

       return Collections.max(byUser.entrySet(), comparingDouble(Map.Entry::getValue)).getKey();
    }

    @Override
    public Map<String, Long> getSalesCountBySeller() {
        Map<String, Long> byUser = repo.findAll()
                .stream()
                .collect(groupingBy(s -> s.getUser().getUsername(), counting()));

        return byUser;
    }

    @Override
    public Map<String, Double> getMostSellerProduct() {
        Stream<Sale> saleStream = repo.findAll().stream();
        Stream<List<SaleDetail>> saleDetailStream = saleStream.map(Sale::getDetails);

        Stream<SaleDetail> streamDetail = saleDetailStream.flatMap(Collection::stream); //list -> list.stream()
        Map<String, Double> byProduct = streamDetail
                .collect(groupingBy(e -> e.getProduct().getName(), summingDouble(SaleDetail::getQuantity)));

        return byProduct.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(reverseOrder()))
                .collect(toMap(
                    Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new
                ));
    }
}
