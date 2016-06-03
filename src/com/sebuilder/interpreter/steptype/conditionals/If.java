package com.sebuilder.interpreter.steptype.conditionals;

import com.sebuilder.interpreter.ConditionalStep;
import com.sebuilder.interpreter.ConditionalStepResult;
import com.sebuilder.interpreter.ConditionalStepType;
import com.sebuilder.interpreter.TestRun;
import com.sebuilder.interpreter.steptype.Eval;

/**
 * If conditional for Selenium Builder script.
 * The step accepts a variable <code>script</code> that will be evaluated as JS and equated to true.
 * JS code needs to return a variable after evaluation.
 * Required to be terminated with a <code>EndIf</code> step.
 * Can be used in conjunction with a <code>Else</code> step.
 * @author Kingston Chan
 * @see com.sebuilder.interpreter.steptype.conditionals.EndIf
 * @see com.sebuilder.interpreter.steptype.conditionals.Else
 */
public class If implements ConditionalStepType {
    @Override
    public boolean run(TestRun ctx) {
        int index = ctx.getStepIndex() - 1;
        boolean skip;
        boolean conditionalResult;
        String evalResult = new Eval().get(ctx);
        if(evalResult.equals("true")) {
            skip = false;
            conditionalResult = true;
        } else {
            skip = true;
            conditionalResult = false;
        }
        ctx.getActiveConditionals().addFirst(
            new ConditionalStepResult(
                    ConditionalStep.If,
                    skip,
                    index,
                    conditionalResult
            )
        );
        return true;
    }
}
