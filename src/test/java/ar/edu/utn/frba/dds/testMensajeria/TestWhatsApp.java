package ar.edu.utn.frba.dds.testMensajeria;

import ar.edu.utn.frba.dds.mensajeria.WhatsAppSender;
import org.junit.jupiter.api.Test;

public class TestWhatsApp {
    @Test
    public void enviarWhatsApp(){

        WhatsAppSender whatsAppSender = new WhatsAppSender(
                "394679593732404",
                "EAAKQbkh4qmYBOyJiLWtbBanMzcJIgtplrcqTg0xqQZCY0yxZCIkk25jTKZCsg0nAjwf5jPXP59aGcm2DD9EboUnkpBZCzJPPVyPsUh7pvGuhtxemYvrGJKMwIItqicjIpln99wdd92X2ZAUISNFKiEp6jMe1Ppe0woBacUwE2GLX75irhDTuM7fcOjfQKZCvVQkVOwJLPZA1mhcibBhrVMZD");

        whatsAppSender.enviarMensaje("1132420699", "test", "Test - Grupo 22");
    }
}

// El Authorization Token se vence cada 24 hs, si veo que lo usamos lo configuro par aque sea permanente