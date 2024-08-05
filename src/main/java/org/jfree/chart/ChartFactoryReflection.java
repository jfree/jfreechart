package org.jfree.chart;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ChartFactoryReflection extends ChartFactory {
    ChartFactoryReflection() {

    }

    public JFreeChart getChartReflection(String chartType, List<Object> params)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> classObj = Class.forName(chartType);
        Constructor<?> chartConstructor = classObj.getConstructor();

        Object chartObj = chartConstructor.newInstance();

        return getChartObject(classObj, chartObj, params);

    }

    public JFreeChart getChartObject(Class<?> classObj, Object chartObj, List<Object> params)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        Method createMethod;
        Class<?>[] parameterTypes = new Class<?>[params.size()];

        for (int i = 0; i < parameterTypes.length; i++) {
            var objectType = params.get(i).getClass();
            parameterTypes[i] = objectType;
        }

        Object[] inputParams = new Object[params.size()];

        for (int i = 0; i < params.size(); i++) {
            inputParams[i] = params.get(i);
        }

        createMethod = classObj.getMethod("createChart", parameterTypes);
        return (JFreeChart) createMethod.invoke(chartObj, inputParams);

    }
}
