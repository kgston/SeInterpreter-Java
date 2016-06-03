package com.sebuilder.interpreter.steptype.conditionals;

import com.sebuilder.interpreter.ConditionalStep;
import com.sebuilder.interpreter.ConditionalStepResult;
import com.sebuilder.interpreter.ConditionalStepType;
import com.sebuilder.interpreter.TestRun;

/**
 * EndIf conditional for Selenium Builder script.
 * Required to close an <code>If</code> step.
 * @author Kingston Chan
 * @see com.sebuilder.interpreter.steptype.conditionals.If
 */
public class EndIf implements ConditionalStepType {
    @Override
    public boolean run(TestRun ctx) {
        ConditionalStepResult lastActiveConditional = ctx.getActiveConditionals().peekFirst();
        if(lastActiveConditional != null && lastActiveConditional.getConditionalName() == ConditionalStep.If) {
            lastActiveConditional.setSkip(false);
            ctx.getActiveConditionals().removeFirst();
            return true;
        } else {
            throw new RuntimeException("No matching If step was found, terminating due to script error");
        }
    }
}
