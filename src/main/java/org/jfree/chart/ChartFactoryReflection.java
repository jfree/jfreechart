package org.jfree.chart;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Method createMethod;
        Class<?>[] parameterTypes = new Class<?>[params.size()];

        for (int i = 0; i < parameterTypes.length; i++) {
            var objectType = params.get(i).getClass();
            parameterTypes[i] = objectType;
        }

        createMethod = classObj.getMethod("createChart", parameterTypes);

        return (JFreeChart) createMethod.invoke(chartObj, params);

    }
}
