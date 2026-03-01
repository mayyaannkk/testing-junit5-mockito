package guru.springframework;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InlineMockTest {
    @Test
    void testInlineMock() {
        Map map = mock(Map.class);
        assertEquals(0, map.size());
    }
}
