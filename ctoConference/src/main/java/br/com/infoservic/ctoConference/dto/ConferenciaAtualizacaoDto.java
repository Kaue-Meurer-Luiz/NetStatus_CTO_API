package br.com.infoservic.ctoConference.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record ConferenciaAtualizacaoDto(
        @NotNull(message = "É obrigatório informar o ID da conferência")
        Long idConferencia,

        @NotBlank(message = "É obrigatorio definir qual caixa está conferindo")
        String caixa,

        @NotBlank(message = "É obrigatorio definir qual a cidade que está realizando a conferencia!")
        String cidade,

        @NotNull(message = "É obrigatorio definir qual a data que está realizando a conferencia!")
        LocalDateTime dataConferencia,

        String observacao,

        @NotNull(message = "É obrigatorio definir qual Tecnico interno está realizando a conferencia!")
        Long tecInternoId,

        @NotNull(message = "É obrigatorio definir qual Tecnico Externo está realizando a conferencia!")
        Long tecExternoId,

        @Valid
        List<PortasAtualizacaoDto> portas
) {}