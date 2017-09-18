package pt.isel.ls.academicActivities.utils.json;



import pt.isel.ls.academicActivities.utils.Writable;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonObj implements Writable {
	
	private final List<Writable> _content = new ArrayList<Writable>();
	public JsonObj(Writable...cs){
        _content.addAll(Arrays.asList(cs));
	}
	
	public final JsonObj withContent(Writable w) {
        _content.add(w);
        return this;
    }
	

	@Override
	public void writeTo(Writer w) throws IOException {
		int i=_content.size();
		w.write(" {\n");
		for(Writable c : _content) {
			if(c!=null) {
				c.writeTo(w);
				if(i>1)	w.write(",\n");
				i--;
			}
			
        }
		w.write("\n }\n");
	}

}
