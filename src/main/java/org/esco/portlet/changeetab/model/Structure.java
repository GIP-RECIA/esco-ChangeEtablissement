package org.esco.portlet.changeetab.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Created by jgribonvald on 27/03/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Structure implements Serializable {
	/** Bean Id. */
	@NonNull
	protected String id;
	/** Struct name. */
	@NonNull
	protected String name;
	/** Struct displayName. */
	@NonNull
	protected String displayName;
	/** Struct description. */
	protected String description;

	private Map<String, List<String>> otherAttributes;

}
