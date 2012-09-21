package io.nextweb.fn;

public interface Calculation<Input, Output> {

	public Output apply(Input input);

}
