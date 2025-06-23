package br.com.matricula.service.service;

import br.com.matricula.service.dto.CertificadoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;
import java.util.UUID;

@Service
public class PdfMonkeyService {

    private final WebClient webClient;

    @Value("${pdfmonkey.api.key}")
    private String apiKey;

    @Value("${pdfmonkey.template.id}")
    private String templateId;

    public PdfMonkeyService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.pdfmonkey.io/api/v1")
                .build();
    }

    public String gerarCertificado(CertificadoDto certificado) {
        Map<String, Object> payload = Map.of(
                "nome", certificado.nome(),
                "curso", certificado.curso(),
                "data", certificado.data(),
                "_filename", certificado.filename()
        );

        Map<String, Object> request = Map.of(
                "document", Map.of(
                        "document_template_id", templateId,
                        "payload", payload
                )
        );

        try {
            Map response = webClient.post()
                    .uri("/documents")
                    .header("Authorization", "Bearer " + apiKey)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            System.out.println("Resposta do PDFMonkey: " + response);

            if (response == null) {
                throw new RuntimeException("Resposta nula do PDFMonkey");
            }

            if (response.containsKey("errors")) {
                throw new RuntimeException("Erro no PDFMonkey: " + response.get("errors"));
            }

            Map data = (Map) response.get("data");
            if (data == null) {
                throw new RuntimeException("Resposta inválida do PDFMonkey: 'data' está null");
            }

            Map attributes = (Map) data.get("attributes");
            String downloadUrl = (String) attributes.get("download_url");

            if (downloadUrl == null) {
                throw new RuntimeException("URL de download não encontrada no PDFMonkey");
            }

            return downloadUrl;

        } catch (WebClientResponseException e) {
            System.err.println("Erro PDFMonkey: " + e.getResponseBodyAsString());
            throw new RuntimeException("Erro ao gerar PDF: " + e.getResponseBodyAsString(), e);
        }
    }
}