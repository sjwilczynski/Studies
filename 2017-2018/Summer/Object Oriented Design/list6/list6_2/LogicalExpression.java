package list6.list6_2;

import java.util.HashMap;
import java.util.Map;

class Context {
    private Map<String, Boolean> context = new HashMap<>();

    public boolean getValue(String variableName) {
        Boolean value = context.get(variableName);
        if(value == null){
            throw new IllegalArgumentException();
        }
        return value;
    }

    public void setValue(String variableName, boolean value) {
        context.put(variableName, value);
    }
}

public abstract class LogicalExpression {
    public abstract boolean interpret(Context context);
}

class ConstExpression extends LogicalExpression {

    private boolean value;
    private String variable;

    ConstExpression(boolean value) {
        this.value = value;
    }

    ConstExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public boolean interpret(Context context) {
        if (variable != null) {
            return context.getValue(variable);
        } else {
            return value;
        }
    }
}

abstract class UnaryExpression extends LogicalExpression {
    LogicalExpression exp;

    UnaryExpression(LogicalExpression exp) {
        this.exp = exp;
    }
}

class Negation extends UnaryExpression {

    Negation(LogicalExpression exp) {
        super(exp);
    }

    @Override
    public boolean interpret(Context context) {
        return !exp.interpret(context);
    }
}

abstract class BinaryExpression extends LogicalExpression {
    LogicalExpression exp1;
    LogicalExpression exp2;

    BinaryExpression(LogicalExpression exp1, LogicalExpression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
}

class Disjunction extends BinaryExpression {

    Disjunction(LogicalExpression exp1, LogicalExpression exp2) {
        super(exp1, exp2);
    }

    @Override
    public boolean interpret(Context context) {
        return exp1.interpret(context) || exp2.interpret(context);
    }
}

class Conjuction extends BinaryExpression {


    Conjuction(LogicalExpression exp1, LogicalExpression exp2) {
        super(exp1, exp2);
    }

    @Override
    public boolean interpret(Context context) {
        return exp1.interpret(context) && exp2.interpret(context);
    }
}

