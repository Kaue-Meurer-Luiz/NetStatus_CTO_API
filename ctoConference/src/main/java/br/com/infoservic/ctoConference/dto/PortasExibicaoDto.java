package br.com.infoservic.ctoConference.dto;

import br.com.infoservic.ctoConference.model.Portas;

public record PortasExibicaoDto(
        Long portaId,
        Integer nrPorta,
        String cliente,
        String status,
        Boolean plotado,
        String observacao
) {
    public PortasExibicaoDto(Portas portas){
        this(
                portas.getPortaId(),
                portas.getNrPorta(),
                portas.getCliente(),
                portas.getStatus(),
                portas.getPlotado(),
                portas.getObservacao()
        );
    }
}
