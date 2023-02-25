package org.jfree.chart;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ChartFactoryReflection extends ChartFactory {
    ChartFactoryReflection() {

    }

    public JFreeChart getChartReflection(String chartType, Map<String, List<Object>> params)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> classObj = Class.forName(chartType);
        Constructor<?> chartConstructor = classObj.getConstructor();

        String chart = classObj.getSimpleName();

        if (chart == null || chart == "") {
            return null;
        }

        Object chartObj = chartConstructor.newInstance();

        return getChartObject(chart, classObj, chartObj, params.get(chart));

    }

    public JFreeChart getChartObject(String simpleClassName, Class<?> classObj, Object chartObj, List<Object> params)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        System.out.println("Called getChartObject with parameters: \n" + simpleClassName + "\n" + classObj.getName()
                + "\n" + chartObj.getClass().getName() + "\n" + params);
        Method createMethod;
        Class<?>[] parameterTypes = new Class<?>[params.size()];

        for (int i = 0; i < parameterTypes.length; i++) {
            var objectType = params.get(i).getClass();
            parameterTypes[i] = objectType;
        }

        System.out.println("Parameters in parameterTypes are: \n");

        for (var param : parameterTypes) {
            System.out.println(param);
        }

        Object[] inputParams = new Object[params.size()];

        for (int i = 0; i < params.size(); i++) {
            inputParams[i] = params.get(i);
        }

        createMethod = classObj.getDeclaredMethod("createChart", parameterTypes);
        return (JFreeChart) createMethod.invoke(chartObj, inputParams);

    }
}
