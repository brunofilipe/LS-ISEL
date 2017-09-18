package pt.isel.ls.academicActivities.utils.json;



import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class JsonPairKeyValue implements Writable {
	
	private final Map<String,Writable> _content = new HashMap<String,Writable>();
	
	public JsonPairKeyValue(String name, Writable o){
		if(name != null){
			if(o==null)
				_content.put(name, null);
			else
				_content.put(name, o);
        }
    }
	
	public final JsonPairKeyValue withContent(String name, Writable w) {
        _content.put(name, w);
        return this;
    }

	@Override
	public void writeTo(Writer w) throws IOException {
		for(Map.Entry<String,Writable> entry : _content.entrySet()) {
            w.write(String.format("  \"%s\":",entry.getKey() ));
            if(entry.getValue()== null) w.write("null");
            else
                entry.getValue().writeTo(w);
        }
		
	}

}
