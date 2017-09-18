package pt.isel.ls.academicActivities.utils.json;


import pt.isel.ls.academicActivities.utils.*;

import java.io.IOException;
import java.io.Writer;

public class Json implements Writable  {
	
	private Writable _content;
	
	public Json(Writable... cs) {
        _content = new CompositeWritable(cs);
    }

	@Override
	public void writeTo(Writer w) throws IOException {
		_content.writeTo(w);
		
	}


}
