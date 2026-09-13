package br.com.infoservic.ctoConference.controller;

import br.com.infoservic.ctoConference.dto.ConferenciaAtualizacaoDto;
import br.com.infoservic.ctoConference.dto.ConferenciaCadastroDto;
import br.com.infoservic.ctoConference.dto.ConferenciaExibicaoDto;
import br.com.infoservic.ctoConference.model.Conferencia;
import br.com.infoservic.ctoConference.service.ConferenciaService;
import br.com.infoservic.ctoConference.specifications.ConferenciaSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConferenciaController {

    @Autowired
    private ConferenciaService service;


    public ConferenciaController(ConferenciaService conferenciaService){
        this.service = conferenciaService;
    }

    @PostMapping("/conferencias")
    @ResponseStatus(HttpStatus.CREATED)
    public ConferenciaExibicaoDto gravar(@RequestBody ConferenciaCadastroDto conferenciaCadastroDto){
        return service.gravar(conferenciaCadastroDto);
    }


    @GetMapping(value = "/conferencias", params = {"dataInicio", "dataFinal"})
    @ResponseStatus(HttpStatus.OK)
    public List<ConferenciaExibicaoDto> listarConferenciasPorPeriodo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFinal
    ) {
        return service.listarConferenciasPorPeriodo(dataInicio, dataFinal);
    }

    @GetMapping("/conferencias/listar")
    @ResponseStatus(HttpStatus.OK)
    public Page<ConferenciaExibicaoDto> listarTodasAsConferencias(@RequestParam(required = false) String caixa, @PageableDefault(size = 10, page = 0, sort = "dataConferencia", direction = Sort.Direction.DESC) Pageable paginacaoTodasConferencias){
        return service.listarTodasAsConferencias(paginacaoTodasConferencias, ConferenciaSpec.ContemCaixa(caixa));
    }


    @GetMapping("/conferencias/ultimas")
    @ResponseStatus(HttpStatus.OK)
    public List<ConferenciaExibicaoDto> buscarUltimasCincoConferencias(){
        return service.listarUltimasCinco();
    }


    @DeleteMapping("/conferencias/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){
        service.excluir(id);
    }

    @PutMapping("/conferencias")
    @ResponseStatus(HttpStatus.OK)
    public ConferenciaExibicaoDto atualizar(@RequestBody ConferenciaAtualizacaoDto conferencia){
        return service.atualizarConferencia(conferencia);
    }


}
