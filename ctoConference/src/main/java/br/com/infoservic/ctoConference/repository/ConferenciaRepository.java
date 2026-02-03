package br.com.infoservic.ctoConference.repository;

import br.com.infoservic.ctoConference.model.Conferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConferenciaRepository extends
        JpaRepository<Conferencia, Long>, JpaSpecificationExecutor<Conferencia>  {

    @Query("SELECT c FROM Conferencia c WHERE c.dataConferencia BETWEEN :dataInicial AND :dataFinal")
    List<Conferencia> listarConferenciasPorPeriodo(
            @Param("dataInicial") LocalDate dataInicial,
            @Param("dataFinal") LocalDate dataFinal
    );

    List<Conferencia> findTop5ByOrderByDataConferenciaDesc();

    List<Conferencia> findAllByOrderByDataConferenciaDesc();

}
