package io.nextweb.fn;

import io.nextweb.Nextweb;

public abstract class ResultCallback<ResultType> {

	public abstract void onSuccess(ResultType result);

	public void onFailure(Throwable t) {
		Nextweb.getEngine().getExceptionManager().onFailure(this, t);
	}

}
