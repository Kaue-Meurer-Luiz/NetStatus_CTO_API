package br.com.infoservic.ctoConference.service;

import br.com.infoservic.ctoConference.dto.ConferenciaCadastroDto;
import br.com.infoservic.ctoConference.dto.ConferenciaExibicaoDto;
import br.com.infoservic.ctoConference.dto.UsuarioExibicaoDto;
import br.com.infoservic.ctoConference.model.Conferencia;
import br.com.infoservic.ctoConference.model.Portas;
import br.com.infoservic.ctoConference.model.Usuario;
import br.com.infoservic.ctoConference.repository.ConferenciaRepository;
import br.com.infoservic.ctoConference.repository.PortasRepository;
import br.com.infoservic.ctoConference.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConferenciaService {

    private final ConferenciaRepository conferenciaRepository;
    private final UsuarioRepository usuarioRepository;

    public ConferenciaService(ConferenciaRepository conferenciaRepository, UsuarioRepository usuarioRepository) {
        this.conferenciaRepository = conferenciaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ConferenciaExibicaoDto gravar(ConferenciaCadastroDto conferenciaCadastroDto) {
        // Busca os técnicos
        Usuario tecInterno = usuarioRepository.findById(conferenciaCadastroDto.tecInterno_id())
                .orElseThrow(() -> new RuntimeException("Usuário de Tec. Interno não encontrado"));

        Usuario tecExterno = usuarioRepository.findById(conferenciaCadastroDto.tecExterno_id())
                .orElseThrow(() -> new RuntimeException("Usuário de Tec. Externo não encontrado"));

        // Cria a conferência
        Conferencia conferencia = new Conferencia();
        conferencia.setCaixa(conferenciaCadastroDto.caixa());
        conferencia.setCidade(conferenciaCadastroDto.cidade());
        conferencia.setDataConferencia(conferenciaCadastroDto.dataConferencia());
        conferencia.setObservacao(conferenciaCadastroDto.observacao());
        conferencia.setTecInternoId(tecInterno);
        conferencia.setTecExternoId(tecExterno);

        // Adiciona portas usando o helper da entidade
        conferenciaCadastroDto.portas().forEach(p -> {
            Portas portas = new Portas();
            portas.setNrPorta(p.nrPorta());
            portas.setCliente(p.cliente());
            portas.setStatus(p.status());
            portas.setPlotado(p.plotado());
            portas.setObservacao(p.observacao());
            conferencia.addPorta(portas); // associa a conferência automaticamente
        });

        // salva a conferência e automaticamente as portas
        Conferencia conferenciaSalva = conferenciaRepository.save(conferencia);

// retorna o DTO de exibição
        return new ConferenciaExibicaoDto(conferenciaSalva);
    }



// Listar conferencias por Periodo
    public List<ConferenciaExibicaoDto> listarConferenciasPorPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        return conferenciaRepository
                .listarConferenciasPorPeriodo(dataInicial, dataFinal)
                .stream()
                .map(ConferenciaExibicaoDto::new)
                .toList();

    }

    // Listar todas as conferencias
    public Page<ConferenciaExibicaoDto> listarTodasAsConferencias(Pageable paginacaoTodasConferencias){
        return conferenciaRepository
                .findAll(paginacaoTodasConferencias)
                .map(ConferenciaExibicaoDto::new);

    }

    //Lista ultimas 5 conferencias
    public List<ConferenciaExibicaoDto> listarUltimasCinco(){
        return conferenciaRepository.findTop5ByOrderByDataConferenciaDesc()
                .stream()
                .map(ConferenciaExibicaoDto::new)
                .toList();
    }
}
