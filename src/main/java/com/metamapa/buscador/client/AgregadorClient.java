package com.metamapa.buscador.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.metamapa.buscador.model.Resultados_Documento;

@Service
public class AgregadorClient {

    private final RestTemplate restTemplate;

    public AgregadorClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Resultados_Documento> obtenerHechos() {
        String url = "https://two025-tp-entrega-2-zoedominguez-bsuh.onrender.com/hechos";
        try {
            Resultados_Documento[] arr = restTemplate.getForObject(url, Resultados_Documento[].class);
            return arr != null ? Arrays.asList(arr) : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error obteniendo hechos: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<PdiFuenteDTO> obtenerPdis() {
        String url = "https://tpa-ddsi-2025-grupo-9-fuentes.onrender.com/api/pdis";
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                entity, 
                String.class
            );

            String jsonRaw = response.getBody();
            
            if (jsonRaw != null) {
                System.out.println(">>> JSON PDIS RECIBIDO (primeros 200 cars): " + 
                    jsonRaw.substring(0, Math.min(jsonRaw.length(), 200)));
            } else {
                System.out.println(">>> JSON PDIS es NULL");
                return Collections.emptyList();
            }

            PdiFuenteDTO[] arr = objectMapper.readValue(jsonRaw, PdiFuenteDTO[].class);
            return Arrays.asList(arr);

        } catch (Exception e) {
            System.err.println("!!! Error crítico parseando PDIs: " + e.getMessage());
            return Collections.emptyList();
        }
        Resultados_Documento[] arr = restTemplate.getForObject(url, Resultados_Documento[].class);
        return Arrays.asList(arr);
    }
}
