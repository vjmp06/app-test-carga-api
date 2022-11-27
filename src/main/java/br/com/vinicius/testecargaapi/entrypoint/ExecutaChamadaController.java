package br.com.vinicius.testecargaapi.entrypoint;

import br.com.vinicius.testecargaapi.dataprovider.RequestGenericoThread;
import br.com.vinicius.testecargaapi.dataprovider.RunThreadsGenerico;
import br.com.vinicius.testecargaapi.dataprovider.ThreadEncerradaException;
import br.com.vinicius.testecargaapi.dataprovider.model.Request;
import br.com.vinicius.testecargaapi.entrypoint.model.request.Body;
import br.com.vinicius.testecargaapi.dataprovider.repository.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/teste")
@AllArgsConstructor
public class ExecutaChamadaController {

    RestTemplate restTemplate = new RestTemplate();
    RequestRepository requestRepository;

    @GetMapping(value = "/run-requests/{idRequest}", produces = "text/plain")
    public String runCallRequest(@PathVariable("idRequest") Integer idRequest) {
        Request request = requestRepository.findById(idRequest).orElse(null);
        String resultado = null;

        try {
            RunThreadsGenerico runGenericoThread = new RunThreadsGenerico(request, restTemplate);
            resultado = runGenericoThread.getLogs();
        }catch (ThreadEncerradaException ex) {
            resultado = ex.getMessage();
        }
        return resultado;
    }

    @GetMapping
    public String get(){
        return "hello world";
    }

    @PostMapping
    public ResponseEntity<Body> post(@RequestBody Body body) {
        return ResponseEntity.ok(body);
    }
}
