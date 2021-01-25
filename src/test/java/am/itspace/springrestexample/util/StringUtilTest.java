package am.itspace.springrestexample.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    private StringUtil stringUtil = new StringUtil();

    @Test
    void trim_OK() {
        String value = "  asdf";
        String trim = stringUtil.trim(value);
        assertEquals(value.trim(), trim);
    }

    @Test
    void trim_OK_2() {
        String value = "  asdf   ";
        String trim = stringUtil.trim(value);
        assertEquals(value.trim(), trim);
    }

    @Test
    void trim_OK_3() {
        String value = "asdf";
        String trim = stringUtil.trim(value);
        assertEquals(value.trim(), trim);
    }

    @Test
    void trim_OK_Poxos() {
        String value = "poxos";
        String trim = stringUtil.trim(value);
        assertEquals("petros", trim);
    }

    @Test
    void trim_NullPointer() {
        assertThrows(NullPointerException.class, () -> stringUtil.trim(null));
    }
}