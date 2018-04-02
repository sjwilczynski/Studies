package list6.list6_2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LogicalExpressionTest {

    @Test
    void testInterpret(){
        Context context = new Context();
        context.setValue("x", true);
        context.setValue("y", false);
        context.setValue("z", true);
        ConstExpression x = new ConstExpression("x");
        ConstExpression y = new ConstExpression("y");
        ConstExpression z = new ConstExpression("z");

        LogicalExpression exp1 = new Negation(y);
        LogicalExpression exp2 = new Disjunction(x, y);
        LogicalExpression exp3 = new Conjuction(x,y);
        LogicalExpression exp4 = new Conjuction(new Disjunction(new Negation(x), z), new Disjunction(x, new Negation(z)));
        assertTrue(exp1.interpret(context));
        assertTrue(exp2.interpret(context));
        assertTrue(!exp3.interpret(context));
        assertTrue(exp4.interpret(context));
    }

}