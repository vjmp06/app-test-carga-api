package br.com.vinicius.testecargaapi.dataprovider.repository;

import br.com.vinicius.testecargaapi.dataprovider.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository <Request, Integer> {

    List<Request> findByApi_Id(Integer id);
}
