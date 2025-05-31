package com.tenpo.challenge.percentagecalcapi.util;

import org.springframework.data.domain.Sort;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

public class SortUtils {

    public static Set<String> getValidFields(Class<?> clazz) {
        try {
            return Arrays.stream(Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors())
                    .map(PropertyDescriptor::getName)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las propiedades de clase para validación", e);
        }
    }

    public static Sort filterValidSort(Sort originalSort, Class<?> targetClass, Sort fallbackSort) {
        Set<String> validFields = getValidFields(targetClass);

        Sort cleanedSort = originalSort.stream()
                .filter(order -> validFields.contains(order.getProperty()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Sort::by));

        return cleanedSort.isSorted() ? cleanedSort : fallbackSort;
    }
}
