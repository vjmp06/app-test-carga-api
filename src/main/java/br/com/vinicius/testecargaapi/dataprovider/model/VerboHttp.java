package br.com.vinicius.testecargaapi.dataprovider.model;


import org.springframework.http.HttpMethod;

public enum VerboHttp {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE;

    public static HttpMethod getInstanceHttMethod(VerboHttp verboHttp) {
        HttpMethod httpMethod = null;
        switch (verboHttp) {
            case GET:
                httpMethod = HttpMethod.GET;
                break;
            case POST:
                httpMethod = HttpMethod.POST;
                break;
            case PUT:
                httpMethod = HttpMethod.PUT;
                break;
            case PATCH:
                httpMethod = HttpMethod.PATCH;
                break;
            case DELETE:
                httpMethod = HttpMethod.DELETE;
                break;
        }
        return httpMethod;



    }
}
