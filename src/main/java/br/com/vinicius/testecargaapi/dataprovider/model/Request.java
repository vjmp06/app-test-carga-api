package br.com.vinicius.testecargaapi.dataprovider.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private VerboHttp verboHttp;

    private Integer quantidadeThreads;

    private Integer quantidadeRequests;

    private String path;

    private String headers;

    private String body;

    @ManyToOne
    @JoinColumn(name="api_id", nullable=false)
    private Api api;
}
