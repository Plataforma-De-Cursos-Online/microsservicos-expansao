package br.com.matricula.service.service;

import br.com.matricula.service.config.RabbitMQConfig;
import br.com.matricula.service.dto.EmailComAnexoDto;
import br.com.matricula.service.dto.EmailUsuarioCursoDto;
import br.com.matricula.service.dto.UsuarioDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class EmailServiceMatricula {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarConfirmacaoTransacao(EmailUsuarioCursoDto dados) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dados.getLogin());
        message.setSubject("Matrícula Realizada com Sucesso");

        String corpo = String.format("""
        Olá %s,

        Sua matrícula no curso "%s" foi realizada com sucesso!

        Descrição do curso:
        %s

        Estamos felizes em tê-lo(a) conosco. Prepare-se para uma nova jornada de aprendizado!

        Qualquer dúvida, estamos à disposição.

        Atenciosamente,
        Mony courses
        """, dados.getNome(), dados.getTitulo(), dados.getDescricao());

        message.setText(corpo);
        mailSender.send(message);
        System.out.println("E-mail enviado com sucesso para " + dados.getLogin());
    }


    @RabbitListener(queues = RabbitMQConfig.FILA_CERTIFICADO)
    public void receberEmail(EmailComAnexoDto emailDto) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailDto.getDestinatario());
            helper.setSubject(emailDto.getAssunto());
            helper.setText(emailDto.getMensagem());


            //byte[] pdfBytes = new URL(emailDto.getUrlAnexo()).openStream().readAllBytes();

            //helper.addAttachment("certificado.pdf", new ByteArrayResource(pdfBytes));

            mailSender.send(message);

            System.out.println("Email enviado para " + emailDto.getDestinatario());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
