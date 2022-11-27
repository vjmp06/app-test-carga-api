package br.com.vinicius.testecargaapi.dataprovider.repository;

import br.com.vinicius.testecargaapi.dataprovider.model.Api;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiRepository extends JpaRepository<Api, Integer> {
}
