package ru.ezhov.common.objects.ujacs.tools;

import java.util.logging.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ru.ezhov.common.objects.ujacs.client.ClientConfig;

/**
 *
 * @author ezhov_da
 */
public class JsonConverterTest {

    private static final Logger LOG = Logger.getLogger(JsonConverterTest.class.getName());

    public JsonConverterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testGetInstance() {
        JsonConverter expResult = null;
        JsonConverter result = JsonConverter.getInstance();
        assertFalse(expResult == result);
    }

    @Test
    public void testConvertFromJSonClass() {
        JsonConverter converter = JsonConverter.getInstance();
        String clientConfigString = "{\"host\":\"123.23234.52236236\",\"port\":9894,\"nameApp\":\"test\"}";
        ClientConfig clientConfig1 = (ClientConfig) converter.convertFromJsonClass(clientConfigString, ClientConfig.class);
        assertFalse(clientConfig1 == null);
        LOG.info(clientConfig1.toString());

    }

    @Test
    public void testConvertToJsonObject() {
        JsonConverter converter = JsonConverter.getInstance();
        ClientConfig clientConfig1 = new ClientConfig();
        clientConfig1.setHost("test host");
        clientConfig1.setPort(124);
        clientConfig1.setNameApp("test name app");
        String objectJson = converter.convertToJsonObject(clientConfig1);
        assertFalse("".equals(objectJson));
        LOG.info(objectJson);
    }

}
