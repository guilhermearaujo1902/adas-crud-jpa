package com.adas.crud_jpa;

import java.util.HashMap;
import java.util.Map;

public class ExemploMap {

    public static void main(String[] args) {

        // Exemplo de declaração de um HashMap
        Map<String, Object> objetoGenerico = new HashMap<>();


        // Inserindo valores no HashMap "PUT"
        objetoGenerico.put("ID", 7);
        objetoGenerico.put("NOME", "Harry");
        objetoGenerico.put("STATUS", true);


        // Exibindo os valors do HashMap "GET"
        System.out.println("Id: " + objetoGenerico.get("ID"));
        System.out.println("Nome: " + objetoGenerico.get("NOME"));
        System.out.println("Status: " + objetoGenerico.get("STATUS"));
        System.out.println("Idade: " + objetoGenerico.get("IDADE"));

    }

}
