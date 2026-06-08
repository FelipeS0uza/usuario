package com.felipesouza.usuario.business;

import com.felipesouza.usuario.infrastructure.client.ViaCepClient;
import com.felipesouza.usuario.infrastructure.client.ViaCepDTO;
import com.felipesouza.usuario.infrastructure.exceptions.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service    //Indica ao spring que é uma Service
@RequiredArgsConstructor    //Gera um construtor que inicializa apenas os campos PRIVATE FINAL
public class ViaCepService {

    //Injeção de dependências
    private final ViaCepClient client;

    //Metodo que acessa a api do ViaCep e busca os dados do endereço com o cep informado
    public ViaCepDTO buscarDadosEndereco(String cep) {
        return client.buscaDadosEndereco(processarCep(cep));
    }

    //Metodo que corrige o cep retirando espaços em branco e traços
    private String processarCep(String cep) {
        String cepFormatado = cep.replace(" ", "")
                                    .replace("-", "");

        //Cep com outro caracter que não seja número ou maior/menor que 8 digitos retorna erro
        if(!cepFormatado.matches("\\d+") || cepFormatado.length() != 8) {
            throw new IllegalArgumentException("O cep está invalido. Deve possuir exatamente 8 numeros, sem traço ou letra.");
        }

        return cepFormatado;
    }
}