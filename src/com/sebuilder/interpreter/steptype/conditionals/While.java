package com.sebuilder.interpreter.steptype.conditionals;

import com.sebuilder.interpreter.ConditionalStep;
import com.sebuilder.interpreter.ConditionalStepResult;
import com.sebuilder.interpreter.ConditionalStepType;
import com.sebuilder.interpreter.TestRun;
import com.sebuilder.interpreter.steptype.Eval;

/**
 * While conditional for Selenium Builder script.
 * The step accepts a variable <code>script</code> that will be evaluated as JS and equated to true.
 * JS code needs to return a variable after evaluation.
 * Required to be terminated with a <code>EndWhile</code> step.
 * @author Kingston Chan
 * @see com.sebuilder.interpreter.steptype.conditionals.EndWhile
 */
public class While implements ConditionalStepType {
    @Override
    public boolean run(TestRun ctx) {
        int index = ctx.getStepIndex() - 1;
        boolean skip;
        boolean conditionalResult;

        String evalResult = new Eval().get(ctx);
        if(evalResult == null) {
            throw new RuntimeException("The evaluated While expression results in null which is unsupported");
        }
        System.out.println("evalResult: " + evalResult);
        if(evalResult.equals("true")) {
            skip = false;
            conditionalResult = true;
        } else {
            skip = true;
            conditionalResult = false;
        }

        ConditionalStepResult lastActiveConditional = ctx.getActiveConditionals().peekFirst();
        if(lastActiveConditional != null && lastActiveConditional.getIndex() == index) {
            lastActiveConditional.setSkip(skip);
            lastActiveConditional.setConditionalResult(conditionalResult);
        } else {
            ctx.getActiveConditionals().addFirst(
                new ConditionalStepResult(
                        ConditionalStep.While,
                        skip,
                        index,
                        conditionalResult
                )
            );
        }
        return true;
    }
}
