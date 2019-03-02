package list5.list5_1;

import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

class SMTPFacadeTest {

    @Test
    void testSend() throws MessagingException, IOException {
        SMTPFacade facade = new SMTPFacade();
        ByteArrayInputStream attachmentContent = new ByteArrayInputStream(new byte[]{1, 2, 3, 4, 0xF});
        facade.send("sender_mail", "sender_password", "recipient_name",
                "test mail with attachment", "test", attachmentContent, "application/pdf");
    }
}