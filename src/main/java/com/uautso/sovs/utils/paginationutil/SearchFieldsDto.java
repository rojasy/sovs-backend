package com.uautso.sovs.utils.paginationutil;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

/**
 * @author developers@PMO-Dashboard
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchFieldsDto {

	public enum SearchOperationType {
		Equals, NotEquals, Like, LessThan, GreaterThan, In
	}

	private String fieldName;
	private Object fieldValue;
	private List<Object> fieldValues;

	@Enumerated(EnumType.STRING)
	private SearchOperationType searchType;
}
