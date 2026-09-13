package br.com.infoservic.ctoConference.dto;

public record PortasAtualizacaoDto(
        Long portaId,
        Integer nrPorta,
        String cliente,
        String status,
        Boolean plotado,
        String observacao
) {}