package pt.isel.ls.academicActivities.engine;

import pt.isel.ls.academicActivities.exceptions.ParameterException;
import pt.isel.ls.academicActivities.utils.Pair;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parameters {
    private Map<String, List<String>> paramsMap;

    private Parameters(String params) {
        paramsMap = new HashMap<>();
        if (params.contains(":"))
            addHeadersToMap(params);
        else
            addParametersToMap(params);
    }

    private Parameters(String headers, String parameters) {
        paramsMap = new HashMap<>();
        addHeadersToMap(headers);
        addParametersToMap(parameters);
    }

    private Parameters() {
        paramsMap = new HashMap<>();
    }

    public void addParam(String key, String singleValue) {
        List<String> valueList = paramsMap.get(key);
        if (valueList != null) {
            valueList.add(singleValue);
            return;
        }
        valueList = new ArrayList<>();
        valueList.add(singleValue);
        paramsMap.put(key, valueList);
    }

    public void addParams(List<Pair<String, String>> varPathList) {
        for (Pair<String, String> pair : varPathList) {
            addParam(pair.getKey(), pair.getValue());
        }
    }

    public Optional<List<String>> getListOfStrings(String paramName) {
        return Optional.ofNullable(getValues(paramName));
    }

    public Optional<String> getString(String paramName) {
        List<String> stringValues = getValues(paramName);
        if (stringValues != null)
            return Optional.of(stringValues.get(0));
        return Optional.empty();
    }

    public Optional<List<Integer>> getListOfInts(String paramName) throws ParameterException {
        List<String> intValues = getValues(paramName);
        try {
            if (intValues != null)
                return Optional.of(intValues.stream().map(Integer::parseInt).collect(Collectors.toList()));
            return Optional.empty();
        } catch (NumberFormatException e) {
            throw new ParameterException("One of the values specified in the parameter '" + paramName + "' is invalid! Make sure you're writing valid numbers only.");
        }
    }

    public Optional<Integer> getInt(String paramName) throws ParameterException {
        List<String> intValues = getValues(paramName);
        try {
            if (intValues != null)
                return Optional.of(Integer.parseInt(intValues.get(0)));
            return Optional.empty();
        } catch (NumberFormatException e) {
            throw new ParameterException("The value specified in the parameter '" + paramName + "' is invalid! Make sure you're writing a valid number.");
        }
    }

    public Optional<List<Boolean>> getListOfBools(String paramName) throws ParameterException {
        List<String> stringList = getValues(paramName);
        if (stringList != null) {
            List<Boolean> boolList = stringList.stream()
                    .filter(value -> value.equals("true") || value.equals("false"))
                    .map(Boolean::parseBoolean)
                    .collect(Collectors.toList());
            if (boolList.size() != stringList.size())
                throw new ParameterException("One of the values specified in the parameter '" + paramName + "' isn't valid! Make sure you're only writing either 'true' or 'false'.");
            return Optional.of(boolList);
        }
        return Optional.empty();
    }

    public Optional<Boolean> getBool(String paramName) throws ParameterException {
        List<String> boolList = getValues(paramName);
        if (boolList != null) {
            String bool = boolList.get(0).toLowerCase();
            if (bool.equals("true") || bool.equals("false"))
                return Optional.of(Boolean.parseBoolean(bool));
            else
                throw new ParameterException("The value specified in the parameter '" + paramName + "' isn't valid! Make sure you're only writing either 'true' or 'false'.");
        }
        return Optional.empty();
    }

    private List<String> getValues(String paramKey) {
        return paramsMap.get(paramKey);
    }

    private void addParametersToMap(String parameters) {
        String[] paramsArray = parameters.split("&");
        for (String param : paramsArray) {
            String[] pair = param.split("=");
            if(pair.length != 2)
                continue;
            String key = pair[0];
            String value = pair[1].replaceAll(Pattern.quote("+"), " ");
            addParam(key, value);
        }
    }

    private void addHeadersToMap(String headers) {
        String[] headersArray = headers.split(Pattern.quote("|"));
        for (String header : headersArray) {
            String[] pair = header.split(":");
            String key = pair[0];
            String value = pair[1];
            addParam(key, value);
        }
    }

    private static Parameters consoleCreate(String[] arguments) {
        if (arguments.length > 3)
            return new Parameters(arguments[2], arguments[3]);
        else {
            if (arguments.length > 2)
                return new Parameters(arguments[2]);
            else
                return new Parameters();
        }
    }

    public static Parameters create(String... arguments) {
        if(arguments[0].isEmpty() && arguments.length == 1)
            return new Parameters();
        else if (arguments.length == 1)
            return new Parameters(arguments[0]);
        return consoleCreate(arguments);
    }
}
