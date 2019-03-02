package list5.list5_4;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonRegistryTest {

    @Test
    void testBridges() {
        XMLLoaderPersonRegistry smsXml1 = new XMLLoaderPersonRegistry(new SMSNotifier());
        SMSNotifierPersonRegistry smsXml2 = new SMSNotifierPersonRegistry(new XMLLoader());
        MailNotifierPersonRegistry mailDb1 = new MailNotifierPersonRegistry(new DBLoader());
        DBLoaderPersonRegistry mailDb2 = new DBLoaderPersonRegistry(new MailNotifier());
        ArrayList<PersonRegistry> registries = new ArrayList<>(Arrays.asList(smsXml1, smsXml2, mailDb1, mailDb2));
        for (PersonRegistry registry : registries) {
            registry.loadPeople();
        }
        assertEquals(smsXml1.notifyPeople(), smsXml2.notifyPeople());
        assertEquals(mailDb1.notifyPeople(), mailDb2.notifyPeople());
    }

}