package kriptoborza;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DenarnicaTest {

    @Test
    void atributIdJePrivateInTipaString() throws NoSuchFieldException {
        Field id = Denarnica.class.getDeclaredField("id");

        assertEquals(String.class, id.getType());
        assertTrue(Modifier.isPrivate(id.getModifiers()));
    }

    @Test
    void atributStanjaJePrivateInTipaMap() throws NoSuchFieldException {
        Field stanja = Denarnica.class.getDeclaredField("stanja");

        assertEquals(Map.class, stanja.getType());
        assertTrue(Modifier.isPrivate(stanja.getModifiers()));
    }

    @Test
    void atributStanjaImaGenerikeStringInDouble() throws NoSuchFieldException {
        Field stanja = Denarnica.class.getDeclaredField("stanja");

        Type genericType = stanja.getGenericType();

        assertTrue(genericType instanceof ParameterizedType);

        ParameterizedType parameterizedType = (ParameterizedType) genericType;

        assertEquals(Map.class, parameterizedType.getRawType());

        Type[] tipiArgumentov = parameterizedType.getActualTypeArguments();

        assertEquals(String.class, tipiArgumentov[0]);
        assertEquals(Double.class, tipiArgumentov[1]);
    }

    @Test
    void konstruktorNastaviIdInInicializiraPrazenSlovar() throws Exception {
        Denarnica d = new Denarnica("ACC2002");

        Field id = Denarnica.class.getDeclaredField("id");
        id.setAccessible(true);

        Field stanja = Denarnica.class.getDeclaredField("stanja");
        stanja.setAccessible(true);

        assertEquals("ACC2002", id.get(d));

        Object vrednostStanja = stanja.get(d);

        assertNotNull(vrednostStanja);
        assertTrue(vrednostStanja instanceof Map);

        Map<?, ?> slovar = (Map<?, ?>) vrednostStanja;

        assertTrue(slovar.isEmpty());
    }

}
