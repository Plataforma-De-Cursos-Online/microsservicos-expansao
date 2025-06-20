package br.com.matricula.service.service;

import br.com.matricula.service.dto.CertificadoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class PdfMonkeyService {

    private final WebClient webClient;

//    @Value("${pdfmonkey.api.key}")
//    private String apiKey;

    @Value("${pdfmonkey.template.id}")
    private final String templateId = "6F893674-2471-4F5F-8E7C-9D6D15BDE90A";

    public PdfMonkeyService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.pdfmonkey.io").build();
    }

    public String gerarCertificado(CertificadoDto certificado) {
        String jsonData = """
            {
                "nome": "%s",
                "curso": "%s",
                "data": "%s",
                "_filename": "%s"
            }
            """.formatted(certificado.nome(), certificado.curso(), certificado.data(), certificado.filename());

        var response = webClient.post()
                .uri("/api/documents")
                .bodyValue(Map.of(
                        "document", Map.of(
                                "document_template_id", templateId,
                                "payload", jsonData
                        )
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        var attributes = (Map<String, Object>) response.get("data");
        var links = (Map<String, String>) attributes.get("attributes");
        return links.get("download_url");
    }
}