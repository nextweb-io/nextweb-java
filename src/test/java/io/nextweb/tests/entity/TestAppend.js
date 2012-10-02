


var TestAppend = function(onSuccess) {
	var session = Nextweb.createSession();
	var node = session.seed("local");
												
	node.append("Hello, World!");
												
	session.commit().get(function(success) {
		session.close.get(function(success) {
			onSuccess();											
		}
	}
};

