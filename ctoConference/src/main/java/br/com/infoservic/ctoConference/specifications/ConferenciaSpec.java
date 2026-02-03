package br.com.infoservic.ctoConference.specifications;

import br.com.infoservic.ctoConference.model.Conferencia;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class ConferenciaSpec {

    public static Specification<Conferencia> ContemCaixa(String caixa){
        return ((root, query, builder) -> {
            //System.out.println("VALOR DE CAIXA: [" + caixa + "]");
            if (caixa == null || caixa.isBlank()) {
                //System.out.println("SPEC IGNORADA");
                return null;
            }
            //System.out.println("SPEC APLICADA");
            return builder.like(builder.lower(root.get("caixa")), "%" + caixa.trim().toLowerCase() + "%");
        });
    }
}
