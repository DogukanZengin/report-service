package com.dz.financial.reporting.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("ModelConverter Utility Class Tests")
public class ModelConverterTests {

    @Test
    @DisplayName("Could not be instantiated")
    public void modelConverterCannotBeCreated() throws NoSuchMethodException {
        Constructor<ModelConverter> declaredConstructor = ModelConverter.class.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, () -> {
            declaredConstructor.newInstance();
        });
    }

    @Test
    @DisplayName("CustomerInfoResponse should be empty with null input")
    public void shouldReturnNullCustomerInfoWhenInputNull() {
        assertFalse(ModelConverter.convertToQueryCustomerInfoResponse(null).isPresent());
    }

    @Test
    @DisplayName("QueryTransactionResponse should empty null with null input")
    public void shouldReturnNullQueryTransactionResponseWhenInputNull() {
        assertFalse(ModelConverter.convertToQueryTransactionResponse(null).isPresent());
    }
}
