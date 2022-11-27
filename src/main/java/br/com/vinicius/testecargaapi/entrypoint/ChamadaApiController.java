package br.com.vinicius.testecargaapi.entrypoint;

import br.com.vinicius.testecargaapi.dataprovider.RunThreadsGenerico;
import br.com.vinicius.testecargaapi.dataprovider.ThreadEncerradaException;
import br.com.vinicius.testecargaapi.dataprovider.model.Api;
import br.com.vinicius.testecargaapi.dataprovider.model.Request;
import br.com.vinicius.testecargaapi.dataprovider.repository.ApiRepository;
import br.com.vinicius.testecargaapi.dataprovider.repository.RequestRepository;
import br.com.vinicius.testecargaapi.entrypoint.model.request.Body;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@AllArgsConstructor
@Controller
@RequestMapping("/")
public class ChamadaApiController {

    private ApiRepository apiRepository;
    private RequestRepository requestRepository;

    private RestTemplate restTemplate;

    @GetMapping("/signup")
    public String showSignUpForm(Api api) {
        return "add-api";
    }

    @PostMapping("/addapi")
    public String addUser(Api api, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-api";
        }

        apiRepository.save(api);
        return "redirect:/index";
    }

    @PostMapping("**/add-request/{apiId}")
    public String addRequest(Request request, BindingResult result, Model model, @PathVariable("apiId") Integer apiId) {
        if (result.hasErrors()) {
            return "add-api";
        }
        if (request.getBody() == "") {
            request.setBody(null);
        }
        Api api = apiRepository.findById(apiId).orElse(null);
        request.setApi(api);
        requestRepository.save(request);

        return "redirect:/edit-api/{apiId}";
    }

    @PostMapping("**/update-request/{apiId}/request/{requestId}")
    public String updateRequest(Request request, BindingResult result, Model model, @PathVariable("apiId") Integer apiId, @PathVariable("requestId") Integer requestId) {
        if (result.hasErrors()) {
            return "api/update-api";
        }
        Api api = apiRepository.findById(apiId).orElse(null);
        Request requestBase = requestRepository.findById(requestId).orElse(null);
        request.setApi(api);

        if (request.getApi() == requestBase.getApi()) {
            requestBase.setNome(request.getNome());
            requestBase.setVerboHttp(request.getVerboHttp());
            requestBase.setQuantidadeThreads(request.getQuantidadeThreads());
            requestBase.setQuantidadeRequests(request.getQuantidadeRequests());
            requestBase.setPath(request.getPath());
            requestBase.setHeaders(request.getHeaders());
            if (request.getBody() == null) {
                request.setBody(null);
            }
            requestBase.setBody(request.getBody());
        }
        requestRepository.save(requestBase);

        return "redirect:/edit-api/{apiId}";
    }

    @GetMapping({"/","index"})
    public String showUserList(Model model) {
        model.addAttribute("apis", apiRepository.findAll());
        model.addAttribute("api", new Api());
        return "index";
    }

    @GetMapping("/edit-api/{id}")
    public String showApiUpdateForm(@PathVariable("id") Integer id, Model model) {
        Api api = apiRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("add", "display: block;");
        model.addAttribute("update", "display: none;");
        model.addAttribute("api", api);
        model.addAttribute("request", new Request());
        model.addAttribute("requests", requestRepository.findByApi_Id(id));
        return "api/update-api";
    }

    @GetMapping("/edit-request/{idApi}/request/{idRequest}")
    public String showRequestUpdateForm(@PathVariable("idApi") Integer idApi, @PathVariable("idRequest") Integer idRequest, Model model) {
        Api api = apiRepository.findById(idApi)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user IdApi:" + idApi));
        Request request = requestRepository.findById(idRequest)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user IdRequest:" + idRequest));

        model.addAttribute("api", api);
        model.addAttribute("add", "display: none;");
        model.addAttribute("update", "display: block;");
        model.addAttribute("request", request);
        model.addAttribute("requests", requestRepository.findByApi_Id(idApi));
        return "api/update-api";
    }

    @PostMapping("/update-api/{id}")
    public String updateApi(@PathVariable("id") Integer id, Api api,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            api.setId(id);
            return "api/update-api";
        }

        apiRepository.save(api);
        return "redirect:/edit-api/{id}";
    }

    @GetMapping("/delete-request/{idApi}/request/{idRequest}")
    public String deleteRequest(@PathVariable("idApi") Integer idApi, @PathVariable("idRequest") Integer idRequest, Model model) {
        Request request = requestRepository.findById(idRequest)
                .orElseThrow(() -> new IllegalArgumentException("Invalid request Id:" + idRequest));
        requestRepository.delete(request);
        return "redirect:/edit-api/{idApi}";
    }
}
