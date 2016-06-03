package com.sebuilder.interpreter;

/**
 * Stateful representation of the evaluation results of a conditional step.
 * @author Kingston Chan
 */
public class ConditionalStepResult {
    private ConditionalStep conditionalName;
    private boolean skip = false;
    private int index;
    private boolean conditionalResult;

    public ConditionalStepResult(ConditionalStep conditionalName, boolean skip, int index, boolean conditionalResult) {
        this.conditionalName = conditionalName;
        this. skip = skip;
        this.index = index;
        this.conditionalResult = conditionalResult;
    }

    /**
     * Gets the name of the conditional.
     * @return The name of the conditional.
     */
    public ConditionalStep getConditionalName() {
        return conditionalName;
    }

    /**
     * Whether to skip to the next <code>ConditionalStepType</code>.
     * @return true if is to skip till the next <code>ConditionStepType</code>, false for normal execution.
     */
    public boolean skip() {
        return skip;
    }


    /**
     * Sets whether to skip to the next <code>ConditionalStepType</code>.
     * @param skip Whether to skip till the next <code>ConditionalStepType</code>
     */
    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    /**
     * Gets the index this conditional on the main script
     * @return The index of this conditional on the main script
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index this conditional on the main script
     * @param index The index of the conditional on the main script
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Gets the executed conditional result that was evaluated when the step was triggered
     * @return The executed conditional result
     */
    public boolean getConditionalResult() {
        return conditionalResult;
    }

    /**
     * Sets the executed conditional result that was evaluated when the step was triggered
     * @param conditionalResult The executed conditional result
     */
    public void setConditionalResult(boolean conditionalResult) {
        this.conditionalResult = conditionalResult;
    }

    @Override
    public String toString() {
        return conditionalName.toString() + " - skip: " + skip + ", index: " + index + ", result: " + conditionalResult;
    }
}
