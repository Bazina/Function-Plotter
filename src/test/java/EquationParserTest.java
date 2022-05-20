import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task1.functionplotter.Parser.EquationParser;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EquationParserTest {

    @BeforeEach
    public void setup() throws IOException {
        EquationParser.SetEquation("x^2+2*x+1");
    }

    @Test
    void testEvaluate() {
        assertEquals(1, EquationParser.Evaluate(BigDecimal.ZERO));
    }

    @Test
    void testEvaluateDerivative() {
        assertEquals(2, EquationParser.EvaluateDerivative(BigDecimal.ZERO));
    }

    @Test
    void testValidate1() throws IOException {
        EquationParser.SetEquation("x^2+2x");
        Assertions.assertThrows(RuntimeException.class, () -> EquationParser.Evaluate(BigDecimal.ONE));
    }

    @Test
    void testValidate2() throws IOException {
        EquationParser.SetEquation("x^2+2*x");
        Assertions.assertDoesNotThrow(() -> EquationParser.Evaluate(BigDecimal.ONE));
    }

    @Test
    void testValidate3() throws IOException {
        EquationParser.SetEquation("xx");
        Assertions.assertThrows(RuntimeException.class, () -> EquationParser.Evaluate(BigDecimal.ONE));
    }

    @Test
    void testValidate4() throws IOException {
        EquationParser.SetEquation("x^2+sin(x) / (x - 1)");
        Assertions.assertDoesNotThrow(() -> EquationParser.Evaluate(BigDecimal.ONE));
    }

    @Test
    void testValidate5() throws IOException {
        EquationParser.SetEquation("x^2+con(x) / (x * 10)");
        Assertions.assertThrows(RuntimeException.class, () -> EquationParser.Evaluate(BigDecimal.ONE));
    }

}