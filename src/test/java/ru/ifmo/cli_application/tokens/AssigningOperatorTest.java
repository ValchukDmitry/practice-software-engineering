package ru.ifmo.cli_application.tokens;

import org.junit.Test;
import ru.ifmo.cli_application.Context;

import static org.junit.Assert.assertEquals;

public class AssigningOperatorTest {
    @Test
    public void testAssigning() {
        Context context = new Context();

        AssigningOperator operator = new AssigningOperator("h=5", context);
        operator.execute(null, null);
        assertEquals("5", context.getVariable("h"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssigningWithStringWithoutEquals() {
        Context context = new Context();
        AssigningOperator operator = new AssigningOperator("h5", context);
    }
}
