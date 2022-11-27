package br.com.vinicius.testecargaapi.dataprovider;

import br.com.vinicius.testecargaapi.dataprovider.model.Request;
import br.com.vinicius.testecargaapi.dataprovider.model.VerboHttp;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Getter
@Setter
public class RequestGenericoThread implements Runnable{

    private String threadName;
    private Thread thread;
    private Request request;
    private RestTemplate restTemplate;
    private StringBuilder sb = new StringBuilder();

    DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public RequestGenericoThread(String threadName, Request request, RestTemplate restTemplate) {
        this.threadName = threadName;
        this.request = request;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run() {

            sb.append("<br>").append(threadName).append("<br>");

        for (int i = 1; i <= request.getQuantidadeRequests(); i++) {
            try {

                HttpHeaders headers = new HttpHeaders();
                if(StringUtils.isNotBlank(request.getHeaders())) {
                    Arrays.asList(request.getHeaders()
                            .replaceAll("\n","")
                            .replaceAll("\r", "")
                            .split(";")).stream().forEach( h -> {
                        List<String> chaveValor = Arrays.asList(h.split(":"));
                        headers.set(chaveValor.get(0), chaveValor.get(1));
                    });
                }
                HttpEntity httpEntity = new HttpEntity(null ,headers);
                if (StringUtils.isNotBlank(request.getBody()) && ("application/json".equals(headers.get("Content-Type")) || headers.get("Content-Type") == null)) {
                    httpEntity = new HttpEntity(new JSONObject(request.getBody()).toMap(), headers);
                } if (StringUtils.isNotBlank(request.getBody())) {
                    httpEntity = new HttpEntity(null, headers);
                }

                sb.append("<br>").append("Chamada ").append(i).append(" ").append(LocalDateTime.now().format(sdf).toString())
                        .append("<br>").append(
                                restTemplate.exchange(
                                        request.getApi().getUrlBase() + request.getPath(),
                                        VerboHttp.getInstanceHttMethod(request.getVerboHttp()),
                                        httpEntity,
                                        String.class)
                        );

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        sb.append("<br>");
        System.out.println(sb.toString());
    }

    public void start() {
        if (thread == null) {
            this.thread = new Thread(this, threadName);
            thread.start();
        }
    }
}
