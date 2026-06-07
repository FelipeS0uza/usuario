package com.felipesouza.usuario.business;

import com.felipesouza.usuario.infrastructure.client.ViaCepClient;
import com.felipesouza.usuario.infrastructure.client.ViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final ViaCepClient client;

    public ViaCepDTO buscarDadosEndereco(String cep) {
        return client.buscaDadosEndereco(processarCep(cep));
    }

    private String processarCep(String cep) {
        String cepFormatado = cep.replace(" ", "")
                                    .replace("-", "");

        if(!cepFormatado.matches("\\d+") || cepFormatado.length() != 8) {
            throw new IllegalArgumentException("O cep está invalido. Deve possuir exatamente 8 numeros, sem traço ou letra.");
        }

        return cepFormatado;
    }
}
