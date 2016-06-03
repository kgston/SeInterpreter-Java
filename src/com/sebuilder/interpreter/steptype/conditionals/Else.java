package com.sebuilder.interpreter.steptype.conditionals;

import com.sebuilder.interpreter.ConditionalStepResult;
import com.sebuilder.interpreter.ConditionalStepType;
import com.sebuilder.interpreter.TestRun;

/**
 * Else conditional for Selenium Builder script.
 * Required to be contained within an <code>If</code> and <code>EndIf</code> step.
 * @author Kingston Chan
 * @see com.sebuilder.interpreter.steptype.conditionals.If
 * @see com.sebuilder.interpreter.steptype.conditionals.EndIf
 */
public class Else implements ConditionalStepType {
    @Override
    public boolean run(TestRun ctx) {
        ConditionalStepResult lastActiveConditional = ctx.getActiveConditionals().peekFirst();
        if(lastActiveConditional == null) {
            throw new RuntimeException("No matching If step was found, terminating due to script error");
        }
        boolean executeIf = lastActiveConditional.getConditionalResult();
        ctx.log().trace("executeIf: " + executeIf);
        ctx.log().trace("skip mode before: "+ lastActiveConditional.skip());
        if(executeIf) {
            lastActiveConditional.setSkip(true);
        } else {
            lastActiveConditional.setSkip(false);
        }
        ctx.log().trace("skip mode after: "+ lastActiveConditional.skip());
        return true;
    }
}
