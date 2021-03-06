package com.sebuilder.interpreter.steptype.conditionals;

import com.sebuilder.interpreter.ConditionalStep;
import com.sebuilder.interpreter.ConditionalStepResult;
import com.sebuilder.interpreter.ConditionalStepType;
import com.sebuilder.interpreter.TestRun;

/**
 * EndFcr conditional for Selenium Builder script.
 * Required to close a <code>For</code> step.
 * @author Kingston Chan
 * @see com.sebuilder.interpreter.steptype.conditionals.For
 */
public class EndFor implements ConditionalStepType {
    @Override
    public boolean run(TestRun ctx) {
        ConditionalStepResult lastActiveConditional = ctx.getActiveConditionals().peekFirst();
        if(lastActiveConditional != null && lastActiveConditional.getConditionalName() == ConditionalStep.For) {
            //If the execution is skipping, it means that the block is completed and proceed to remove from active list
            if(lastActiveConditional.skip() && !lastActiveConditional.getConditionalResult()) {
                ctx.getActiveConditionals().removeFirst();
            } else {
                //Otherwise it should continue to loop around the block
                //Always reevaluate the conditional at the start of the loop
                ctx.setStepIndex(lastActiveConditional.getIndex());
            }
            return true;
        } else {
            throw new RuntimeException("No matching For step was found, terminating due to script error");
        }
    }
}
