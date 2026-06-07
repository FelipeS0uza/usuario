package com.felipesouza.usuario.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/* O OpenFeign é um cliente HTTP para a comunição entre microserviços.
   Ele reduz o código repetitivo e facilita a implementação não precisando escrever a implementação da chamada REST  */

//Anotação para indicar que aqui será feito a comunicação com outro microserviço
//O nome é para nomear a API e a url é aquela que será consumida (Definida como variável no Application.properties)
@FeignClient(name = "via-cep", url = "${viacep.url}")
public interface ViaCepClient {

    @GetMapping("/ws/{cep}/json/")   //Indica que o metodo é um GET
    //RequestParam indica que estou passando um parametro no corpo da requisição, nesse caso o email
    //Busca os dados somente do email informado
    ViaCepDTO buscaDadosEndereco(@PathVariable("cep") String cep);
}