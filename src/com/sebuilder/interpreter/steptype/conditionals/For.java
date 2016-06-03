package com.sebuilder.interpreter.steptype.conditionals;

import com.sebuilder.interpreter.ConditionalStep;
import com.sebuilder.interpreter.ConditionalStepResult;
import com.sebuilder.interpreter.ConditionalStepType;
import com.sebuilder.interpreter.TestRun;
import com.sebuilder.interpreter.steptype.Eval;

/**
 * For conditional for Selenium Builder script.
 * The step accepts a variable <code>variable</code> that will be used as the counter reference.
 * If the reference exists, it will be overwritten. The counter will be initialized with the <code>initialValue</code>.
 * Both <code>comparator</code> and <code>incrementor</code> needs to implement the condition and afterthought in JS.
 * The <code>comparator</code> needs to evaluate to true at the start of the loop, while the <code>incrmentor</code>
 * will execute at the for block. The counter reference will be cleared when the block finishes.
 * Counter can be referenced via <code>${variableName}</code>. JS code needs to return a variable after evaluation.
 * Required to be terminated with a <code>EndFor</code> step.
 * @author Kingston Chan
 * @see com.sebuilder.interpreter.steptype.conditionals.EndFor
 */
public class For implements ConditionalStepType {
    @Override
    public boolean run(TestRun ctx) {
        //variable,initialValue,comparator,incrementor
        String variableName = ctx.string("variable");
        int index = ctx.getStepIndex() - 1;
        boolean skip;
        boolean conditionalResult;

        //If the variable is not found, instantiate it to the initial value
        if(ctx.vars().get(variableName) == null) {
            ctx.vars().put(variableName, ctx.string("initialValue"));
        } else {
            //Otherwise increment the variable
            ctx.currentStep().stringParams.put("script", ctx.string("incrementor"));
            ctx.vars().put(variableName, new Eval().get(ctx));
        }

        //Determine whether to execute the for block
        ctx.currentStep().stringParams.put("script", ctx.string("comparator"));
        String evalResult = new Eval().get(ctx);
        if(evalResult == null) {
            throw new RuntimeException("The evaluated While expression results in null which is unsupported");
        }
        ctx.log().debug("evalResult: " + evalResult);
        if(evalResult.equals("true")) {
            skip = false;
            conditionalResult = true;
        } else {
            skip = true;
            conditionalResult = false;
            ctx.vars().remove(variableName);
        }

        //Store the result
        ConditionalStepResult lastActiveConditional = ctx.getActiveConditionals().peekFirst();
        if(lastActiveConditional != null && lastActiveConditional.getIndex() == index) {
            lastActiveConditional.setSkip(skip);
            lastActiveConditional.setConditionalResult(conditionalResult);
        } else {
            ctx.getActiveConditionals().addFirst(
                new ConditionalStepResult(
                        ConditionalStep.For,
                        skip,
                        index,
                        conditionalResult
                )
            );
        }
        return true;
    }
}
