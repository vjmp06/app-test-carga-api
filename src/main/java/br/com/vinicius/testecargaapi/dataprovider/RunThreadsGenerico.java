package br.com.vinicius.testecargaapi.dataprovider;

import br.com.vinicius.testecargaapi.dataprovider.model.Request;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RunThreadsGenerico {

    private List<RequestGenericoThread> threads = new ArrayList<>();
    private Request request;
    private RestTemplate restTemplate;

    public RunThreadsGenerico(Request request, RestTemplate restTemplate) {
        for (int i = 1; i <= request.getQuantidadeThreads(); i++) {
            RequestGenericoThread thread = new RequestGenericoThread(
                    "Thread " + i + " " + request.getVerboHttp() + ": " + request.getApi().getUrlBase() + request.getPath(),
                    request, restTemplate);
            thread.start();
            threads.add(thread);
        }
    }

    public String getLogs() {
        try {
            Thread.sleep(1000);
            StringBuilder sb = new StringBuilder();
            threads.forEach(t -> {
                sb.append(t.getSb().toString());
            });
            return sb.toString();
        } catch (InterruptedException ex) {
            return ex.getMessage();
        }

    }

}
