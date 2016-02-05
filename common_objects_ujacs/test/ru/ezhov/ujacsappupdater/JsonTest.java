package ru.ezhov.ujacsappupdater;

import com.google.gson.Gson;
import java.util.logging.Logger;
import org.junit.Test;
import ru.ezhov.common.objects.ujacs.Commands;
import ru.ezhov.common.objects.ujacs.InformationClass;

/**
 *
 * @author ezhov_da
 */
public class JsonTest {

    private static final Logger LOG = Logger.getLogger(JsonTest.class.getName());

    @Test
    public void jsonTest() {
        Gson gson = new Gson();
        InformationClass informationClassToJson = new InformationClass("test JSON", Commands.NEW_LOAD);
        String string = gson.toJson(informationClassToJson);
        LOG.info(string);

        InformationClass informationClassFromJson = gson.fromJson(string, InformationClass.class);
        LOG.info(informationClassFromJson.toString());
    }
}
