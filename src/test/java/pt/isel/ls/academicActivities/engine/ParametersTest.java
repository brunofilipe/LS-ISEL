package pt.isel.ls.academicActivities.engine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import pt.isel.ls.academicActivities.engine.Parameters;
import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class ParametersTest {
    private Parameters parameters;

    @Before
    public void init() {
        parameters = Parameters.create(new String[]{"","","acr=M1&mandatory=true&semesters=1"});
    }

    @Test
    public void getString_return_string_with_specific_name() throws Exception {
        String expected = "M1";

        String result = parameters.getString("acr").orElseThrow(ParameterException::new);

        assertEquals(expected, result);
    }

    @Test
    public void getInt_return_int_with_specific_name() throws Exception {
        int expected = 1;

        int result = parameters.getInt("semesters").orElseThrow(ParameterException::new);

        assertEquals(expected, result);
    }

    @Test
    public void getBool_return_bool_with_specific_name() throws Exception {
        boolean expected = true;

        boolean result = parameters.getBool("mandatory").orElseThrow(ParameterException::new);

        assertEquals(expected, result);
    }

    @Test
    public void addParams_add_list_of_extra_parameters() {
        List<Pair<String, String>> expected = new ArrayList<>();
        expected.add(new Pair<>("pid", "LEIC"));
        expected.add(new Pair<>("description", "software+laboratory"));
        parameters.addParams(expected);
        expected.forEach((pair) -> {
            try {
                assertEquals(parameters.getString(pair.getKey()).orElseThrow(ParameterException::new), pair.getValue());
            } catch (ParameterException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void addParam_add_single_param_to_map() throws ParameterException {
        String key = "name";
        String value = "LS";

        parameters.addParam(key, value);

        assertEquals(parameters.getString(key).orElseThrow(ParameterException::new), value);
    }

    @Test
    public void addParam_add_multipleValues_to_one_parameter(){
        List<Pair<String, String>> expected = new ArrayList<>();
        expected.add(new Pair<>("pid", "LEIC"));
        expected.add(new Pair<>("pid", "LEIM"));
        parameters.addParams(expected);
        int []idx = new int[]{0};
        expected.forEach((pair) -> {
            try {
                assertEquals(parameters.getListOfStrings(pair.getKey()).orElseThrow(ParameterException::new).get(idx[0]++), pair.getValue());
            } catch (ParameterException e) {
                e.printStackTrace();
            }
        });
    }
}
