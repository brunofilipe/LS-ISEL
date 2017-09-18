package pt.isel.ls.academicActivities.utils.json;

import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;

public class JsonText implements Writable {
	
	private Object _text;
	
	public JsonText(Object t){
		_text=t;
	}

	@Override
	public void writeTo(Writer w) throws IOException {
		if(_text instanceof String)
			w.write(String.format("\"%s\"",_text));
		else
			w.write(String.format(" %s",_text));
	}

}
