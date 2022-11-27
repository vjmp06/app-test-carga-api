package br.com.vinicius.testecargaapi.dataprovider.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Api {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String urlBase;

    @OneToMany(mappedBy="api")
    private List<Request> requests;

}
